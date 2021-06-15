<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>狼码客户关系管理系统</title>
<link rel="stylesheet" href="/static/css/style.css">
<script type="text/javascript" src="/static/js/jquery/jquery.js"></script>
<script type="text/javascript" src="/static/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/static/plugins/form/jQueryForm.js"></script>
<script type="text/javascript">
    $(function () {
        //把表单改成ajax提交
        $("#submitForm").ajaxForm(function (data) {
            if (data.success) { //登录成功
                location.href = "/index.do";
            } else { //登录失败
                $("#login_err").html(data.msg);
            }
        });

        //给登录按钮绑定点击事件
        $("#login_sub").click(function () {
            $("#submitForm").submit();
        });
    });
    <shiro:authenticated>
        location.href = "/index.do";
    </shiro:authenticated>
</script>
</head>
<body>
  <section class="container">
    <div class="login">
      <h1>用户登录</h1>
      <form id="submitForm" action="/login.do" method="post">
        <p><span id="login_err" style="color: red"></span></p>
        <p><input type="text" name="username" value="" placeholder="账号"></p>
        <p><input type="password" name="password" value="" placeholder="密码"></p>
        <p class="submit">
        	<input id="login_sub" type="button" value="登录">
        	<input type="button" value="重置">
        </p>
      </form>
    </div>
  </section>
<div style="text-align:center;" class="login-help">
	<p>Copyright ©2017 狼码教育科技有限公司</p>
</div>
</html>