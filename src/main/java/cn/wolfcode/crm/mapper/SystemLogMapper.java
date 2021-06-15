package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface SystemLogMapper {

    int insert(SystemLog record);


    int select4Count(QueryObject qo);

    List<SystemLog> select4List(QueryObject qo);


    void deleteAll();

}