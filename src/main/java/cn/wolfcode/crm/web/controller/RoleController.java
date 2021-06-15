package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRoleService;
import cn.wolfcode.crm.util.JSONResult;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ResponseBody
    @RequestMapping("listAll")
    public List<?> listAll(){
      List<?> list =  roleService.listAll();
      return   list;
    }

    //通过这个资源才能访问WEB-INF下面的资源
    @RequestMapping("view")
    public String view() {
        return "role/list";
    }

    @RequestMapping("query")
    @ResponseBody //转换成Json
    public PageResult query(QueryObject qo){
        PageResult result = roleService.query(qo);
        return result;
    }


    //员工的修改和保存操作
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(Role role){
        try{
            //判断员工id是否存在,如果不存在,就新增,否则,编辑员工
            if(role.getId()==null){
                roleService.save(role);
            }else{
                roleService.update(role);
            }
            return JSONResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.mark("保存失败");
        }
    }

    @RequestMapping("delete")
    @ResponseBody //转换成Json
    public JSONResult delete(Long roleId){
        roleService.delete(roleId);
        return JSONResult.success();
    }

    @RequestMapping("batchRemove")
    @ResponseBody //转换成Json
    public JSONResult batchRemove(Long[] roleIds){
        roleService.batchRemove(roleIds);
        return JSONResult.success();
    }

    @RequestMapping("selectMenuIdByRoleId")
    @ResponseBody //转换成Json
    public List<Long> selectMenuIdByRoleId(Long roleId){
        return roleService.selectMenuIdByRoleId( roleId);
    }

}
