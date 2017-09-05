<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>教材手工发货</title>
    
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
	<link rel="stylesheet" type="text/css" href="css/jc.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jc.js"></script>

  </head>
  
  <body>
    <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	
	<div class="d1" align="center">
		<table id = 't'>
				<tr>
					<th align="left" width='20px'>
						<span class="item">1</span>
					</th>
					<th>
						<a href="input.action" class="easyui-linkbutton"
							data-options="iconCls:'icon-add'">信息录入
						</a>
					</th>
				</tr>
				<tr><th align="left"><span class="item">2</span></th><th><a href="receipt.action"><span class="item">收货确认</span></a></th></tr>
			<tr><th align="left"><span class="item">3</span></th><th align="left"><a href="compute.action"><span class="item">配发</span></a></th></tr>
			<tr><th align="left"><span class="item">4</span></th><th align="left"><a href="delivery.action"><span class="item">发货</span></a></th></tr>
		</table>
	</div>
  </body>
</html>
