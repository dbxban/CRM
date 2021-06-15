package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface IEmployeeService {
    void save(Employee d);

    void update(Employee d);

    void delete(Long id);

    Employee get(Long id);


    /**
     *
     * @param qo 分页查询封装的对象
     * @return
     */
    PageResult query(EmployeeQueryObject qo);

    void changeState(Long empId);

    List<Long> selectRoleIdByEmpId(Long empId);

    Employee getByUsername(String username);

    void resetPassword(Long empId);

    List<Employee> selectAll();

}
