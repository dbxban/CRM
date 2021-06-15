package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface IMenuService {

    List<?> selectRootMenus();

    void save(Menu d);

    void update(Menu d);

    void delete(Long id);

    Menu get(Long id);


    List<?> listAll();

    PageResult query(QueryObject qo);


    /**
     * 查询对应的子菜单
     * @return
     * @param qo
     */
    List<?> selectChildMenus(Long parentId);

    /**
     * 获取当前用户对应的菜单,不需要传参数,在登录的时候已经存入subject了
     * @return
     */
    List<?> selectEmployeeMenus();

}
