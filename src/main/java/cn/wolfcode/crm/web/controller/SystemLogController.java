package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemLogService;
import cn.wolfcode.crm.util.JSONResult;
import cn.wolfcode.crm.util.PageResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("systemLog")
public class SystemLogController {

    @Autowired
    private ISystemLogService systemLogService;



    //通过这个资源才能访问WEB-INF下面的资源
    @RequestMapping("view")
    public String view() {
        return "systemLog/list";
    }


    @RequestMapping("query")
    @ResponseBody //转换成Json
//    @RequiresPermissions(value = {"systemLog:query", "日志列表"}, logical = Logical.OR)
    public PageResult query(QueryObject qo){
        PageResult result = systemLogService.query(qo);
        return result;
    }

   @RequestMapping("deleteAll")
    @ResponseBody //转换成Json
    public JSONResult deleteAll(){
        systemLogService.deleteAll();
        return new JSONResult();
    }




}
