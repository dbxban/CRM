package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface ISystemLogService {
    /**
     * 写入日志
     * @param systemLog
     */
    void write(SystemLog systemLog);

    PageResult query(QueryObject qo);


    void deleteAll();

}
