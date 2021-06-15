<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/static/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/static/css/public.css">

    <%@include file="/static/js/common.jsp"%>
    <script src="/static/js/systemLog.js"></script>
</head>
<body>

    <table id="systemLog_datagrid"></table>
    <div id="systemLog_datagrid_tb">
        <a id="btn_changeState" class="easyui-linkbutton" data-cmd="deleteAll" data-options="iconCls:'icon-remove',plain:true,text:'清空日志'"></a>
    </div>


</body>
</html>
