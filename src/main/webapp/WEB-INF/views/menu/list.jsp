<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/static/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/static/css/public.css">

    <%@include file="/static/js/common.jsp"%>
    <script src="/static/js/menu.js"></script>
</head>
<body>

    <table id="menu_datagrid"></table>
    <div id="menu_datagrid_tb">
        <a class="easyui-linkbutton" data-cmd="add" data-options="iconCls:'icon-add',plain:true,text:'添加菜单'"></a>
        <a class="easyui-linkbutton" data-cmd="edit" data-options="iconCls:'icon-edit',plain:true,text:'编辑'"></a>
        <a class="easyui-linkbutton" data-cmd="remove" data-options="iconCls:'icon-remove',plain:true,text:'删除'"></a>
        <a class="easyui-linkbutton" data-cmd="reload" data-options="iconCls:'icon-reload',plain:true,text:'刷新'"></a>
        <a class="easyui-linkbutton" iconCls="icon-sigOut" plain="true" data-cmd="backMenu">返回顶级菜单</a>

    </div>
    <!--会话框底部工具栏-->
    <div id="menu_dlg_btn">
        <a class="easyui-linkbutton"  data-cmd="save" data-options="iconCls:'icon-save',plain:true,text:'保存'"></a>
        <a class="easyui-linkbutton" data-cmd="cancel" data-options="iconCls:'icon-cancel',plain:true,text:'取消'"></a>
    </div>
    <div id="menu_dlg">
        <form id="menu_form">
            <input type="hidden" name="id"/>
            <div style=" margin-bottom: 20px; margin-left: 40px;margin-top: 20px">
                上级菜单
                <select id="parent.id" name="parent.id" class="easyui-combotree" style="width:150px;"
                data-options="url:'/menu/listAll.do'"></select>
            </div>
            <div style=" margin-bottom: 20px; margin-left: 40px">
                <input name="text" class="easyui-textbox"
                       style="width:200px" data-options="label:'菜单名称',labelWith:60">
            </div>
            <div style=" margin-bottom: 20px; margin-left: 40px">
                <input name="url" class="easyui-textbox"
                       style="width:200px" data-options="label:'菜单url',labelWith:60" >
            </div>
        </form>
    </div>



</body>
</html>
