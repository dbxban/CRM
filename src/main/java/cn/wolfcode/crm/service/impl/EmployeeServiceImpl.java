package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.PageResult;
import cn.wolfcode.crm.util.UserContext;
import com.alibaba.druid.sql.visitor.functions.If;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void save(Employee d) {
        employeeMapper.insert(d);
        //1.
        saveRoleRelation(d);
    }

    //保存关系
    private void saveRoleRelation(Employee d) {
        List<Role> roles = d.getRoles();
        for (Role role : roles) {
            employeeMapper.insertRoelRelation(d.getId(),role.getId());
        }
    }


    @Override
    public void update(Employee d) {
        employeeMapper.updateByPrimaryKey(d);
        //1.先删除关系
        deleteRoleRealtion(d);
        //2.保存关系
        saveRoleRelation(d);
    }

    //删除关系
    private void deleteRoleRealtion(Employee d) {
        employeeMapper.deleteRoleRealtion(d.getId());
    }

    @Override
    public void delete(Long id) {
        employeeMapper.deleteByPrimaryKey(id);
        //删除关系
        employeeMapper.deleteRoleRealtion(id);
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult query(EmployeeQueryObject qo) {
        //获取当前用户
        Employee currentUser = UserContext.getCurrentUser();
        //判断是否是管理员和HR
        if (!currentUser.isAdmin() && !SecurityUtils.getSubject().hasRole("HR")) {
            qo.setDeptId(currentUser.getDept().getId());
        }
        int tatal = employeeMapper.select4Count(qo);
        if (tatal == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        List<?> list = employeeMapper.select4List(qo);

        return new PageResult(tatal, list);
    }

    @Override
    public void changeState(Long empId) {
        employeeMapper.changeState( empId);
    }

    @Override
    public List<Long> selectRoleIdByEmpId(Long empId) {
        return employeeMapper.selectRoleIdByEmpId(empId);
    }

    @Override
    public Employee getByUsername(String username) {
        return employeeMapper.getByUsername(username);
    }

    @Override
    public void resetPassword(Long empId) {
        employeeMapper.resetPassword( empId);
    }

    @Override
    public List<Employee> selectAll() {
        return employeeMapper.selectAll();
    }

}
