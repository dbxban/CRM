package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IMenuService;
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
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    //通过这个资源才能访问WEB-INF下面的资源
    @RequestMapping("view")
    public String view() {
        return "menu/list";
    }

    //获取当前用户对应的菜单
    @RequestMapping("selectEmployeeMenus")
    @ResponseBody //转换成Json
    public List<?> selectEmployeeMenus(){
        return menuService.selectEmployeeMenus();
    }


    //
    @RequestMapping("selectRootMenus")
    @ResponseBody //转换成Json
    public List<?> selectRootMenus(QueryObject qo){
        return menuService.selectRootMenus();
    }

    @RequestMapping("selectChildMenus")
    @ResponseBody //转换成Json
    public List<?> selectChildMenus(Long parentId){
        return menuService.selectChildMenus(parentId);
    }

    @ResponseBody
    @RequestMapping("listAll")
    public List<?> listAll(){
        List<?> list =  menuService.listAll();
        return   list;
    }

    @RequestMapping("query")
    @ResponseBody //转换成Json
    public PageResult query(QueryObject qo){
        PageResult result = menuService.query(qo);
        return result;
    }


    //菜单的修改和保存操作
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(Menu menu){
        try{
            //判断菜单id是否存在,如果不存在,就新增,否则,编辑菜单
            if(menu.getId()==null){
                menuService.save(menu);
            }else{
                menuService.update(menu);
            }
            return JSONResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.mark("保存失败");
        }
    }

    @RequestMapping("delete")
    @ResponseBody //转换成Json
    public JSONResult delete(Long menuId){
        menuService.delete(menuId);
        return JSONResult.success();
    }
}
