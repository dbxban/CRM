package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface IDepartmentService {
    void save(Department d);

    void update(Department d);

    void delete(Long id);

    Department get(Long id);


    List<?> listAll();

    PageResult query(QueryObject qo);

    void changeState(Long deptId);

}
