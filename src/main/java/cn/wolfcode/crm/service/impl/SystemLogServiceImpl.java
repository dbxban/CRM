package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.mapper.SystemLogMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemLogService;
import cn.wolfcode.crm.util.PageResult;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SystemLogServiceImpl implements ISystemLogService {
    @Autowired
    private SystemLogMapper systemLogMapper;

    @Override
    public void write(SystemLog systemLog) {
        systemLogMapper.insert(systemLog);
    }

    @Override
    public PageResult query(QueryObject qo) {
        int tatal = systemLogMapper.select4Count(qo);
        if (tatal == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        List<?> list = systemLogMapper.select4List(qo);

        return new PageResult(tatal, list);
    }

    @Override
    public void deleteAll() {
        systemLogMapper.deleteAll();
    }

}
