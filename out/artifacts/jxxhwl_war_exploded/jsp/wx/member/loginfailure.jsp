<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录失败</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- 页面不允许缩放 -->
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/public.css" rel="stylesheet">
	<link href="css/shou.css" rel="stylesheet">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

  </head>
  
  <body>
  	<header>
		<div class="hd"> <span class="clocebtn"></span> 
			<a href="javascript:;" class="logo" onclick='location.reload();'><span class="logo">新华物流</span> </a>
			<h1>登录失败</h1> 
		</div>
	</header>
	<div class="container">
		<p class="h1">对不起，您还不是我们的会员</p>
	</div>
  </body>
</html>
