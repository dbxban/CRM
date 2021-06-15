package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.mapper.DepartmentMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void save(Department d) {
        departmentMapper.insert(d);
    }

    @Override
    public void update(Department d) {
        departmentMapper.updateByPrimaryKey(d);

    }

    @Override
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<?> listAll() {
        return departmentMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int tatal = departmentMapper.select4Count(qo);
        if (tatal == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        List<?> list = departmentMapper.select4List(qo);

        return new PageResult(tatal, list);
    }

    @Override
    public void changeState(Long deptId) {
        departmentMapper.changeState( deptId);
    }

}
