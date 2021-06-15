<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/static/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/static/css/public.css">
    <%@include file="/static/js/common.jsp"%>
    <script src="/static/js/index.js"></script>
</head>
<body>

<div id="cc" class="easyui-layout" data-options="fit:true">

    <div data-options="region:'north',height:70,split:true" >
        <%-- <h1 align="center">叩丁狼员工管理系统</h1>--%>
        <div class="public-header-warrp">
            <div class="public-header">
                <div class="content">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img  src="/static/images/logo.png"/>
                    <div class="public-header-admin fr">
                        <p class="admin-name"><font  color ="green">您好！</font>
                            <span><shiro:principal property="username"></shiro:principal> </span>
                        </p>
                        <div class="public-header-fun fr">
                            <a href="/logout.do" class="public-header-loginout">安全退出</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div data-options="region:'south'" style="height:40px;" >
        <p align="center">版权所有:朗玛科技教育有限公司</p>
    </div>
    <div data-options="region:'west',split:true" style="width:130px;">
        <div id="aa" class="easyui-accordion" data-options="fit:true" >
            <div title="系统管理" >
                <ul id="index_tree"></ul>
            </div>
            <div title="代办事项">

            </div>
            <div title="公司公告">

            </div>
        </div>
    </div>
    <div data-options="region:'center'">
        <div id="indext_tabs" class="easyui-tabs" data-options="fit:true,border:false">
            <div title="欢迎界面"  style="padding:20px;display:none;">

            </div>

        </div>
    </div>
</div>



</body>
</html>