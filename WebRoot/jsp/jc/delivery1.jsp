<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>发货</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/table_jcdelivery.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jcdelivery.js"></script>

  </head>
  
  <body>
     <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">发货&nbsp;<input type="button" value="返回" onclick="location.href='jc.action'"/></h2>
	</div>
	<div align="center">
		<table style = "border:0px;width:50%;">
			<tr>
				<th>整件发货：</th><th>征订代码：<input type="text" id="subcode" /><input type="button" value="查询" id="zj"/></th>
			</tr>
			<tr>
				<th>零件发货：</th><th>店名：<input type="text" id="customer" /><input type="button" value="查询" id="lj"/></th>
			</tr>
		</table>
	</div>
	<div align="center" id="show">
		
	</div>
  </body>
</html>
