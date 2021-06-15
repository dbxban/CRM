package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.util.JSONResult;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;


    //通过这个资源才能访问WEB-INF下面的资源
    @RequestMapping("view")
    public String view() {
        return "permission/list";
    }

    @ResponseBody
    @RequestMapping("listAll") //需要
    public List<?> listAll(){
        List<?> list =  permissionService.listAll();
        return   list;
    }

    @ResponseBody
    @RequestMapping("getByRoleId") //需要
    public List<?> getByRoleId(Long roleId){
        List<?> list =  permissionService.getByRoleId(roleId);
        return   list;
    }

    @RequestMapping("query")
    @ResponseBody //转换成Json
    public PageResult query(QueryObject qo){
        PageResult result = permissionService.query(qo);
        return result;
    }


    @RequestMapping("delete")
    @ResponseBody //转换成Json
    public JSONResult delete(Long permissionId){
        permissionService.delete(permissionId);
        return JSONResult.success();
    }

    @RequestMapping("reload")
    @ResponseBody //转换成Json
    public JSONResult reload(){
        try {
            permissionService.reload();
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("加载失败!");
        }
        return JSONResult.success();
    }


}
