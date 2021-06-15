package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
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
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @ResponseBody
    @RequestMapping("listAll")
    public List<?> listAll(){
      List<?> list =  departmentService.listAll();
      return   list;
    }

    //通过这个资源才能访问WEB-INF下面的资源
    @RequestMapping("view")
    public String view() {
        return "department/list";
    }

    @RequestMapping("query")
    @ResponseBody //转换成Json
    @RequiresPermissions(value = {"department:query", "部门列表"}, logical = Logical.OR)
    public PageResult query(QueryObject qo){
        PageResult result = departmentService.query(qo);
        return result;
    }

    //根据员工id修改状态
    @RequestMapping("changeState")
    @ResponseBody
    public JSONResult changeState(Long deptId) {
        try {
            departmentService.changeState(deptId);
            return JSONResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
    }

    //员工的修改和保存操作
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiresPermissions(value = {"department:saveOrUpdate", "新增/更新部门"}, logical = Logical.OR)
    public JSONResult saveOrUpdate(Department department){
        try{
            //判断员工id是否存在,如果不存在,就新增,否则,编辑员工
            if(department.getId()==null){
                departmentService.save(department);
            }else{
                departmentService.update(department);
            }
            return JSONResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.mark("保存失败");
        }
    } 
    

}
