<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/static/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/static/css/public.css">

    <%@include file="/static/js/common.jsp"%>
    <script src="/static/js/role.js"></script>
</head>
<body>

    <table id="role_datagrid"></table>
    <div id="role_datagrid_tb">
        <a class="easyui-linkbutton" data-cmd="add" data-options="iconCls:'icon-add',plain:true,text:'新增'"></a>
        <a class="easyui-linkbutton" data-cmd="edit" data-options="iconCls:'icon-edit',plain:true,text:'编辑'"></a>
        <a class="easyui-linkbutton" data-cmd="remove" data-options="iconCls:'icon-remove',plain:true,text:'删除'"></a>
        <a class="easyui-linkbutton" data-cmd="batchRemove" data-options="iconCls:'icon-remove',plain:true,text:'批量删除'"></a>
        <a class="easyui-linkbutton" data-cmd="reload" data-options="iconCls:'icon-reload',plain:true,text:'刷新'"></a>
    </div>
    <!--会话框底部工具栏-->
    <div id="role_dlg_btn">
        <a class="easyui-linkbutton"  data-cmd="save" data-options="iconCls:'icon-save',plain:true,text:'保存'"></a>
        <a class="easyui-linkbutton" data-cmd="cancel" data-options="iconCls:'icon-cancel',plain:true,text:'取消'"></a>
    </div>
    <div id="role_dlg">
        <form id="role_form">
            <input type="hidden" name="id"/>
            <div style="float: left; margin-bottom: 20px; margin-left: 20px;margin-top: 10px">
                <input name="name" class="easyui-textbox"
                       style="width:150px" data-options="label:'角色名称',labelWith:60">
            </div>
            <div style="float: left; margin-bottom: 20px; margin-left: 40px;margin-top: 10px">
                <input name="sn" class="easyui-textbox"
                       style="width:150px" data-options="label:'编码',labelWith:60" >
            </div>
            <div style=" margin-bottom: 20px; margin-left: 40px;margin-top: 10px">
                <input id="menu-combotree" class="easyui-combotree"
                       style="width:300px" data-options="label:'拥有菜单',labelWith:60" >
            </div>
            <div>
               <div style="float: left">
                   <table id="allPermissions"></table>
               </div>
               <div style="float: right">
                   <table id="selectedPermissions"></table>
               </div>
           </div>
        </form>
    </div>



</body>
</html>
