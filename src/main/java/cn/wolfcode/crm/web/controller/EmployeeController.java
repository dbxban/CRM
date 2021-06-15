package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.JSONResult;
import cn.wolfcode.crm.util.PageResult;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    //通过这个资源才能访问WEB-INF下面的资源
    @RequestMapping("view")
    public String view() {
        return "employee/list";
    }

    @RequestMapping("query")
    @ResponseBody //转换成Json
    @RequiresPermissions(value = {"employee:query", "员工列表"}, logical = Logical.OR)
    public PageResult query(EmployeeQueryObject qo){
        PageResult result = employeeService.query(qo);
        return result;
    }

    //根据员工id修改状态
    @RequestMapping("changeState")
    @ResponseBody
    @RequiresPermissions(value = {"employee:changeState", "改变员工状态"}, logical = Logical.OR)
    public JSONResult changeState(Long empId) {
        try {
            employeeService.changeState(empId);
            return JSONResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
    }

    //员工的修改和保存操作
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiresPermissions(value = {"employee:saveOrUpdate", "新增/编辑员工"}, logical = Logical.OR)
    public JSONResult saveOrUpdate(Employee employee){
        try{
            //判断员工id是否存在,如果不存在,就新增,否则,编辑员工
            if(employee.getId()==null){
                employeeService.save(employee);
            }else{
                employeeService.update(employee);
            }
            return JSONResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.mark("保存失败");
        }
    }

    @RequestMapping("selectRoleIdByEmpId")
    @ResponseBody //转换成Json
    public List<Long> selectRoleIdByEmpId(Long  empId){
        return employeeService.selectRoleIdByEmpId(empId);

    }

    //验证用户是否存在,如果不存在返回true,不显示信息,否则返回false,无效,才显示信息
    @RequestMapping("getByUsername")
    @ResponseBody //转换成Json
    public boolean getByUsername(String username, Long empId){
        Employee employee = employeeService.getByUsername(username);
        if (employee == null) {
            return true;
        } else if (empId != null && employee.getId().equals(empId)) {
            return true;
        } else {
             return false;
        }

    }

    //重置员工密码
    //根据员工id修改状态
    @RequestMapping("resetPassword")
    @ResponseBody
    public JSONResult resetPassword(Long empId) {
        try {
            employeeService.resetPassword(empId);
            return JSONResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
    }

    //数据导出
    @RequestMapping("exportData")
    @RequiresPermissions(value = {"employee:exportData", "导出文件"}, logical = Logical.OR)
    public void exportData(HttpServletResponse response) throws Exception {
        //1.设置下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=employee.xls");
        //2.查询所有用户信息
        List<Employee> employeeList = employeeService.selectAll();
        //3.创建一个excel表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //4.创建一个工作页签
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow header = sheet.createRow(0);
        //4.创建单元格信息
        header.createCell(0).setCellValue("用户名");
        header.createCell(1).setCellValue("真实姓名");
        header.createCell(2).setCellValue("邮箱");
        header.createCell(3).setCellValue("电话");
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //5.创建单元格信息
            currentRow.createCell(0).setCellValue(employee.getUsername());
            currentRow.createCell(1).setCellValue(employee.getRealname());
            currentRow.createCell(2).setCellValue(employee.getEmail());
            currentRow.createCell(3).setCellValue(employee.getTel());
        }
        workbook.write(response.getOutputStream());
        //关闭流########
        workbook.close();

    }

    //数据导入
    @RequestMapping("importData")
    @ResponseBody
    @RequiresPermissions(value = {"employee:importData", "导入文件"}, logical = Logical.OR)
    public JSONResult importData(MultipartFile file) throws Exception {
        //2.查询所有用户信息
        List<Employee> employeeList = employeeService.selectAll();
        //3.创建一个excel表
        HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream()); //传入文件流
        //4.获取第一个工作页签
        HSSFSheet sheet = workbook.getSheetAt(0);
        //获取sheet中的总行数
        int num = sheet.getLastRowNum();
        for (int i = 1; i <= num; i++) {
            //获取当前行
            HSSFRow currentRow = sheet.getRow(i);
            String username = currentRow.getCell(0).getStringCellValue();
            String realname = currentRow.getCell(1).getStringCellValue();
            String email = currentRow.getCell(2).getStringCellValue();
            String tel = currentRow.getCell(3).getStringCellValue();
            Employee employee = new Employee();
            employee.setUsername(username);
            employee.setRealname(realname);
            employee.setEmail(email);
            employee.setTel(tel);
            employee.setAdmin(false);
            employee.setPassword("1");
            employee.setState(true);

           employeeService.save(employee);
        }
        //关闭流########
        workbook.close();
        return JSONResult.success();
    }


}
