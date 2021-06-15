package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.RoleMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRoleService;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void save(Role d) {
        //先保存基本信息,得到id
        roleMapper.insert(d);
        saveRelation(d);
    }


    //保存关系
    private void saveRelation(Role d) {
        //角色和权限的关系
        List<Permission> permissions = d.getPermissions();
        for (Permission permission : permissions) {
            roleMapper.insertPermissionRelation(d.getId(), permission.getId());
        }
        //角色和菜单的关系
        List<Menu> menus = d.getMenus();
        for (Menu menu : menus) {
            roleMapper.insertMenuRelation(d.getId(), menu.getId());
        }

    }
    //删除旧的关系
    private void deletRelation(Role d) {
        roleMapper.deletePermisionRelation(d.getId());
        roleMapper.deleteMenuRelation(d.getId());
    }

    @Override
    public void update(Role d) {
        roleMapper.updateByPrimaryKey(d);
        //1.先删除旧的关系
        deletRelation(d);
        //2.保存新关系
        saveRelation(d);
    }

    @Override
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
        //删除关系
        roleMapper.deletePermisionRelation(id);
        roleMapper.deleteMenuRelation(id);

    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<?> listAll() {
        return roleMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int tatal = roleMapper.select4Count(qo);
        if (tatal == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        List<?> list = roleMapper.select4List(qo);

        return new PageResult(tatal, list);
    }

    @Override
    public void batchRemove(Long[] roleIds) {

    }

    @Override
    public List<Long> selectMenuIdByRoleId(Long roleId) {
        return roleMapper.selectMenuIdByRoleId(roleId);
    }


}
