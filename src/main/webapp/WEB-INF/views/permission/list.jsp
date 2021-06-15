<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/static/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/static/css/public.css">

    <%@include file="/static/js/common.jsp"%>
    <script src="/static/js/permission.js"></script>
</head>
<body>

    <table id="permission_datagrid"></table>
    <%--会话框顶部工具栏--%>
    <div id="permission_datagrid_tb">
        <a class="easyui-linkbutton" data-cmd="remove" data-options="iconCls:'icon-remove',plain:true,text:'删除'"></a>
        <a class="easyui-linkbutton" data-cmd="reload" data-options="iconCls:'icon-reload',plain:true,text:'加载权限'"></a>
    </div>


</body>
</html>
