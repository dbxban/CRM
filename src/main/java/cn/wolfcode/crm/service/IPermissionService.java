package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface IPermissionService {
    void save(Permission d);

    void update(Permission d);

    void delete(Long id);

    Permission get(Long id);


    List<?> listAll();

    PageResult query(QueryObject qo);


    List<?> getByRoleId(Long roleId);

    /**
     * 加载权限
     */
    void reload();
}
