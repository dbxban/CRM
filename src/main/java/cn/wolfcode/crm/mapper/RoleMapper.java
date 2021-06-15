package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    int select4Count(QueryObject qo);

    List<Role> select4List(QueryObject qo);

    void insertPermissionRelation(@Param("roleId") Long roleId,
                                  @Param("permissionId") Long permissionId);

    void deletePermisionRelation(Long roleId);


    void insertMenuRelation(@Param("roleId") Long roleId,
                            @Param("menuId") Long menuId);

    void deleteMenuRelation(Long roleId);

    List<Long> selectMenuIdByRoleId(Long roleId);

    /**
     * 根据员工id查询对应的角色
     * @param empId
     * @return
     */
    List<Role> selectRoleByEmpId(Long empId);

}