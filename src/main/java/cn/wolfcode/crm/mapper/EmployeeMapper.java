package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    int select4Count(QueryObject qo);

    List<Employee> select4List(EmployeeQueryObject qo);

    void changeState(Long empId);

    void insertRoelRelation(@Param("empId") Long empId,
                            @Param("roleId") Long roleId);

    void deleteRoleRealtion(Long empId);

    List<Long> selectRoleIdByEmpId(Long empId);

    Employee getByUsername(String username);

    void resetPassword(Long empId);

}