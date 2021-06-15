<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/static/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/static/css/public.css">
    <%@include file="/static/js/common.jsp"%>
    <script src="/static/js/employee.js"></script>
    <script type="text/javascript" src="/static/plugins/form/jQueryForm.js"></script>
    <script type="text/javascript">
        $(function () {
            //把表单改成ajax提交
            $("#submitForm").ajaxForm(function (data) {
                if (data.success) { //登录成功
                    $.messager.alert("温馨提示", "上传成功", "info",function () {
                        location.reload();
                    });
                } else { //登录失败
                    $("#login_err").html(data.msg);
                }
            });

            //给登录按钮绑定点击事件
            $("#suBtn").click(function () {
                $("#submitForm").submit();
            });
        });
    </script>
</head>
<body>

    <table id="employee_datagrid"></table>
    <div id="employee_datagrid_tb">
        <a class="easyui-linkbutton" onclick="addUser();" data-options="iconCls:'icon-add',plain:true,text:'新增'"></a>
        <a class="easyui-linkbutton" onclick="editUser();" data-options="iconCls:'icon-edit',plain:true,text:'编辑'"></a>
        <%--<shiro:hasPermission name="employee:changeState">--%>
            <a id="btn_changeState" class="easyui-linkbutton" onclick="changeState()" data-options="iconCls:'icon-no',plain:true,text:'离职'"></a>
        <%--</shiro:hasPermission>--%>
        <a class="easyui-linkbutton" onclick="reloadUser();" data-options="iconCls:'icon-reload',plain:true,text:'刷新'"></a>
        <a class="easyui-linkbutton" onclick="resetPassword();" data-options="iconCls:'icon-tip',plain:true,text:'密码重置'"></a>
        <input class="easyui-textbox" name="keyword" style="width: 120px">
        <a class="easyui-linkbutton" onclick="searchForm();" data-options="iconCls:'icon-search',plain:true,text:'查询'"></a>
        <a class="easyui-linkbutton" onclick="exportData();" data-options="iconCls:'icon-redo',plain:true,text:'导出'"></a>
        <form id="submitForm" action="/employee/importData.do" method="post" enctype="multipart/form-data">
            <input type="file" name="file">
            <input id="suBtn" type="button" value="上传">
        </form>
    </div>
    <!--会话框底部工具栏-->
    <div id="employee_dlg_btn">
        <a class="easyui-linkbutton"  onclick="saveUser();" data-options="iconCls:'icon-save',plain:true,text:'保存'"></a>
        <a class="easyui-linkbutton" onclick="cancelUser();" data-options="iconCls:'icon-cancel',plain:true,text:'取消'"></a>
    </div>
    <div id="employee_dlg" align="center">
        <form id="employee_form">
            <table align="center" style="margin-top: 15px">
                <input type="hidden" name="id" id="emp_id"/>
                <tr >
                    <td>账户名</td>
                    <td>
                        <input id="usernamebox" name="username" class="easyui-textbox" style="width:150px"
                               validType="remote['/employee/getByUsername.do?empId='+emp_id.value,'username']"
                        data-options="invalidMessage:'该用户已存在'">
                    </td>
                </tr>
                <tr >
                    <td>真实姓名</td>
                    <td>
                        <input name="realname" class="easyui-textbox" style="width:150px">
                    </td>
                </tr>
                <tr id="passwordDiv" >
                    <td>密码</td>
                    <td>
                        <input class="easyui-passwordbox" name="password" style="width:150px">
                    </td>
                </tr>
                <tr >
                    <td>电话</td>
                    <td>
                        <input name="tel" class="easyui-textbox" style="width:150px">
                    </td>
                </tr>
                <tr >
                    <td>邮箱</td>
                    <td>
                        <input name="email" class="easyui-textbox" style="width:150px" >
                    </td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td>
                        <input name="dept.id" class="easyui-combobox" style="width:150px"
                               data-options="valueField:'id',textField:'name',
                                url:'/department/listAll.do',panelHeight: 'auto'">
                    </td>
                </tr>
                <tr >
                    <td>录入时间</td>
                    <td>
                        <input name="inputTime" class="easyui-datebox" style="width:150px">
                    </td>
                </tr>
                <tr >
                    <td>状态</td>
                    <td>
                        <input name="state" class="easyui-combobox" style="width:150px" data-options="
                           panelHeight: 'auto',
                           valueField: 'value',
                           textField: 'text',
                           data:[{
                                text:'在职',
                                value: true
                           },{
                                text:'离职',
                                value: false
                           }]"/>
                    </td>
                </tr>
                <tr >
                    <td>管理员</td>
                    <td>
                        <input name="admin" class="easyui-combobox" style="width:150px" data-options="
                           panelHeight: 'auto',
                           valueField: 'value',
                           textField: 'text',
                           data:[{
                                text:'是',
                                value: true
                           },{
                                text:'否',
                                value: false
                           }]"/>
                    </td>
                </tr>
                <tr>
                    <td>角色</td>
                    <td>
                        <input id="roleId" class="easyui-combobox" style="width:150px"
                               data-options="valueField:'id',textField:'name',
                                url:'/role/listAll.do',panelHeight: 'auto',multiple:true">
                    </td>
                </tr>

            </table>
        </form>
    </div>



</body>
</html>
