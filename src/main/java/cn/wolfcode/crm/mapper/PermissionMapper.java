package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    int select4Count(QueryObject qo);

    List<Permission> select4List(QueryObject qo);


    List<Permission> getByRoleId(Long roleId);

    List<String> selectAllResources();

    List<Permission> selectPermissionByEmpId(Long empId);
}