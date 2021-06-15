$(function () {

    //
    $('#employee_datagrid').datagrid({
        fit:true,
        fitColumns: true,
        singleSelect: true,
        sortName:"inputTime",
        sortOrder:"desc",
        url:'/employee/query.do',
        rownumbers: true,
        columns:[[
            {field:'id',title:'',width:6,hidden:true},
            {field:'',title:'',width:10,checkbox:true},
            {field:'username',title:'员工姓名',width:100},
            {field:'realname',title:'真实姓名',width:100},
            {field:'tel',title:'联系电话',width:100},
            {field:'email',title:'邮箱',width:100},
            {field:'dept',title:'部门',width:100 , formatter: function (value, row, index) {
                    if (value) {
                        return value.name;
                    } else {
                        return "";
                    }
                }},
            {field:'inputTime',title:'录入时间',width:100,sortable:true,order:"desc"},
            {field:'state',title:'状态',width:100, formatter: function (value, row, index) {
                    if (value) {
                        return "在职";
                    } else {
                        return "<span style='color: red'>离职</span>";
                    }
                }},
            {field:'admin',title:'管理员',width:100, formatter: function (value, row, index) {
                    if (value) {
                        return "是";
                    } else {
                        return "否";
                    }
                }}
        ]],
        toolbar: '#employee_datagrid_tb',
        pagination: true ,//分页工具栏

        onClickRow: function (index,row) {
            if (row.state) {
                $("#btn_changeState").linkbutton({
                    "text": "离职",
                    iconCls: 'icon-no'
                });
            } else {
                $("#btn_changeState").linkbutton({
                    "text": "复职",
                    iconCls: 'icon-ok'
                });
            }
        }
    });
    //先初始化dialog在调用它的方法###################
    $("#employee_dlg").dialog({
        width: 300,
        height: 350,
        closed:true,//定义是否在初始化的时候关闭面板。
        buttons:'#employee_dlg_btn'
    });

});

//修改员工状态
function changeState() {
    //1.判断是否选中需要操作的记录
    var currentRow = $("#employee_datagrid").datagrid("getSelected");
    if (!currentRow) {
        $.messager.alert("温馨提示", "请选择需要操作的员工", "info");
        return;
    }
    //2.往后台发送ajax,不需要参数
    $.post("/employee/changeState.do", {empId: currentRow.id}, function (data) {
        if (data.success) {
            $.messager.alert("温馨提示", "操作成功", "info", function () {
                //重新加载页面
                $("#employee_datagrid").datagrid("reload");
            });
        } else {
            $.messager.alert("温馨提示", data.msg, "error");
        }
    });
}
//高级查询
function searchForm() {
    //1.拿到表单的数据
    var keyword = $("input[name='keyword']").val();
    //loda方法
    $("#employee_datagrid").datagrid("load",{
        keyword:keyword
    })
}

//新增员工
function addUser() {
    //1
    $("#employee_dlg").dialog("setTitle", "员工编辑");
    //2.
    $("#employee_form").form("clear");
    //3.
    $("#employee_dlg").dialog("open");
}
//保存用户,提交保单
function saveUser() {
    $('#employee_form').form('submit', {

        url: "/employee/saveOrUpdate.do",
        onSubmit: function(param){
            var roleIds = $("#roleId").combobox("getValues");
            console.log(roleIds);
            for (i = 0; i<roleIds.length; i++) {
                param["roles["+i+"].id"] = roleIds[i];
            }
        },
        success: function(data){
            var data = $.parseJSON(data);
            if (data.success) {

                $.messager.alert("温馨提示", "保存成功", "info", function () {
                    $("#employee_dlg").dialog("close");//关闭对话框
                    $("#employee_datagrid").datagrid("reload");//刷新当前页
                })
            } else {
                $.messager.alert('温馨提示', data.msg, 'error');
            }
        }
    });
}
//编辑
/*员工编辑*/
function editUser() {
    //1 判断 是否选中需要操作的记录
    var currentRow = $("#employee_datagrid").datagrid("getSelected");
    if (!currentRow) {//没有选择记录
        $.messager.alert("温馨提示", "亲,请选择需要操作的员工", "info");
        return;
    }
    //在打开会话框之前,把对应员工的角色查询出来,然后设置到下拉框中
    $.post("/employee/selectRoleIdByEmpId.do", {empId: currentRow.id}, function (data) {
            $("#roleId").combobox("setValues",data);
    });
    //1 设置表头为员工编辑
    $("#employee_dlg").dialog("setTitle", "员工编辑");
    //2 打开对话对话框
    $("#employee_dlg").dialog("open");
    //3 清空表单里面的数据
    $("#employee_form").form("clear");
    //4 数据的回显操作
    currentRow["dept.id"] = currentRow.dept.id;//回显的时候,根据同名匹配规则
    $("#employee_form").form("load", currentRow);
    //5 隐藏密码框
    $("#passwordDiv").css("display","none");

}
//取消
function cancelUser() {
    $("#employee_dlg").dialog("close");

}
//刷新
function reloadUser() {
    $("#employee_datagrid").datagrid("reload");//刷新当前页
}

//密码重置
function resetPassword() {
    //获取被选中的行
    var currentRow = $("#employee_datagrid").datagrid("getSelected");
    if (!currentRow) { //若没有选中
        $.messager.alert("提示:", "亲,请至少选中一条记录", "info");
        return;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
    //向后台发送ajax 请求
    $.post("/employee/resetPassword.do", {empId: currentRow.id}, function (data) {
        if (data.success) {
            $.messager.alert("提示:", "操作成功", "info");

        } else {
            $.messager.alert("提示:", data.msg, "error");
        }
        $("#employee_datagrid").datagrid("reload");
    });

}

function exportData() {
    window.location.href = "/employee/exportData.do";

}