<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/static/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/static/css/public.css">

    <%@include file="/static/js/common.jsp"%>
    <script src="/static/js/department.js"></script>
</head>
<body>

    <table id="department_datagrid"></table>
    <div id="department_datagrid_tb">
        <a class="easyui-linkbutton" data-cmd="add" data-options="iconCls:'icon-add',plain:true,text:'新增'"></a>
        <a class="easyui-linkbutton" data-cmd="edit" data-options="iconCls:'icon-edit',plain:true,text:'编辑'"></a>
        <a id="btn_changeState" class="easyui-linkbutton" data-cmd="changeState" data-options="iconCls:'icon-remove',plain:true"></a>
        <a class="easyui-linkbutton" data-cmd="reload" data-options="iconCls:'icon-reload',plain:true,text:'刷新'"></a>
    </div>
    <!--会话框底部工具栏-->
    <div id="department_dlg_btn">
        <a class="easyui-linkbutton"  data-cmd="save" data-options="iconCls:'icon-save',plain:true,text:'保存'"></a>
        <a class="easyui-linkbutton" data-cmd="cancel" data-options="iconCls:'icon-cancel',plain:true,text:'取消'"></a>
    </div>
    <div id="department_dlg" align="center">
        <form id="department_form">
            <table align="center" style="margin-top: 15px">
                <input type="hidden" name="id"/>
                <tr >
                    <td>部门名称</td>
                    <td>
                        <input name="name" class="easyui-textbox" style="width:150px">
                    </td>
                </tr>
                <tr >
                    <td>编码</td>
                    <td>
                        <input name="sn" class="easyui-textbox" style="width:150px">
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
                                text:'启用',
                                value: true
                           },{
                                text:'关闭',
                                value: false
                           }]"/>
                    </td>
                </tr>


            </table>
        </form>
    </div>



</body>
</html>
