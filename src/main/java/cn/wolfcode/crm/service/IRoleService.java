package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface IRoleService {
    void save(Role d);

    void update(Role d);

    void delete(Long id);

    Role get(Long id);


    List<?> listAll();

    PageResult query(QueryObject qo);


    void batchRemove(Long[] roleIds);

    List<Long> selectMenuIdByRoleId(Long roleId);

}
