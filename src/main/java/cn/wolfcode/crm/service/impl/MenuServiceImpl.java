package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.mapper.MenuMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IMenuService;
import cn.wolfcode.crm.util.PageResult;
import cn.wolfcode.crm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public void save(Menu d) {
        //先保存基本信息,得到id
        menuMapper.insert(d);
    }


    @Override
    public void update(Menu d) {
        menuMapper.updateByPrimaryKey(d);
    }

    @Override
    public void delete(Long id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Menu get(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<?> listAll() {
        return menuMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int tatal = menuMapper.select4Count(qo);
        if (tatal == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        List<?> list = menuMapper.select4List(qo);

        return new PageResult(tatal, list);
    }

    @Override
    public List<?> selectRootMenus() {
        return menuMapper.selectRootMenus();
    }

    @Override
    public List<?> selectEmployeeMenus() {
        //所以的菜单
        List<?> rootMenus = selectRootMenus();
        //1.获取当前用户
        Employee currentUser = UserContext.getCurrentUser();
        //##判断当前用户是否是管理员
        if (currentUser.isAdmin()) {
            return rootMenus;
        }
        //2.根据用户id查询对应菜单id的集合
        List<Long> menuIds = menuMapper.selectMenusByEmpId(currentUser.getId());
        //3.检查菜单,没哟的就删除
        checkMenu(rootMenus, menuIds);

        return rootMenus;
    }

    //
    private void checkMenu(List<?> rootMenus, List<Long> menuIds) {
        //对应集合要使用迭代器来删除
        Iterator<?> it = rootMenus.iterator();
        while (it.hasNext()) {
            Menu menu = (Menu) it.next();
            if (!menuIds.contains(menu.getId())) {
                it.remove();
            }
            //如果存在子节点,继续遍历子节点.递归
            if (menu.getChildren() != null) {
                checkMenu(menu.getChildren(),menuIds);
            }

        }
    }


    @Override
    public List<?> selectChildMenus(Long parentId) {
        return menuMapper.selectChildMenus(parentId);
    }



}
