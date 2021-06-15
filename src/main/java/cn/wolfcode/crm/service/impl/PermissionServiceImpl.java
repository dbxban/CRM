package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.util.PageResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RequestMappingHandlerMapping rmhp; //专门处理请求映射

    @Override
    public void save(Permission d) {
        permissionMapper.insert(d);
    }

    @Override
    public void update(Permission d) {
        permissionMapper.updateByPrimaryKey(d);

    }

    @Override
    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<?> listAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int tatal = permissionMapper.select4Count(qo);
        if (tatal == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        List<?> list = permissionMapper.select4List(qo);

        return new PageResult(tatal, list);
    }

    @Override
    public List<?> getByRoleId(Long roleId) {
        return permissionMapper.getByRoleId(roleId);
    }

    @Override
    public void reload() {
        //先查询出所有权限表达式
       List<String> resources = permissionMapper.selectAllResources();
        //先引入
        //先获取所有贴了该注解的方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhp.getHandlerMethods();
        Collection<HandlerMethod> ms = handlerMethods.values();
        //遍历
        for (HandlerMethod m : ms) {
            //获取指定注解
            RequiresPermissions anno = m.getMethodAnnotation(RequiresPermissions.class);
            if (anno != null) { //注解存在
                String[] exps = anno.value();
                String resouce = exps[0];
                String name = exps[1];
                if (!resources.contains(resouce)) {
                    Permission permission = new Permission();
                    permission.setResource(resouce);
                    permission.setName(name);
                    permissionMapper.insert(permission);
                }
            }
        }
    }


}
