<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>发货打印</title>
    
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
	<script type="text/javascript" src="js/jclogin.js"></script>	
	<script type="text/javascript" src="js/fhprint.js"></script>

  </head>
  
  <body>
     <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">打印&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span>&nbsp;<input type="button" value="返回" onclick="location.href='jc.action'"/></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.username'/>" id="username"/>
	<input type="hidden" value="<s:property value='#session.password'/>" id="password"/>
	<div align="center">
		选择批次号：<input type="text" id="batchno" class="easyui-combobox"
				data-options="url:'loadbatchno.action',method:'get',valueField:'batchno',textField:'batchno',panelHeight:120" />
				<input type="button" id="qd" value="预览整件清单">
				<input type="button" id="qd0" value="预览零件清单">
				<input type="button" id="dbd" value="预览调拨单">
				<input type="button" id="pq" value="预览票签">
				<input type="button" id="wbpq" value="预览尾包票签">
	<br/>
	<br/>
				
	补打拣货清单：选择拣货流水号：<input type="text" id="pickno" class="easyui-combobox"
				data-options="url:'loadpickno.action',method:'get',valueField:'pickno',textField:'pickno',panelHeight:120" />
				<input type="button" id="jhqd" value="预览拣货清单"/>				
	</div>
  </body>
</html>
