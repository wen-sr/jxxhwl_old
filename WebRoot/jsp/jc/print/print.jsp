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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jc/predistribution.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/print.js"></script>

  </head>
  
  <body>
	<div align="center">
		<h2 style="color:#0078CA">&nbsp;打印&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<input type="hidden" id="currentType"/>
	<div align="center">
		选择批次号：<input type="text" id="batchno" class="easyui-combobox"
				data-options="url:'jc/print_loadBatchno.action',method:'get',valueField:'batchno',textField:'batchno',panelHeight:120" />
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="wholeCaseList()">预览整件清单</a>
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="oddCaseList()">预览零件清单</a>
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="allocationList()">调拨单</a>
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="s_ticketList()">预览整包票签</a>
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="t_ticketList()">预览尾包票签</a>
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="oddTicketList()">预览拼包票签</a>
	<br/>
	<br/>
	补打拣货清单：选择拣货流水号：<input type="text" id="pickno" class="easyui-combobox"
				data-options="url:'jc/print_loadPickno.action',method:'get',valueField:'pickno',textField:'pickno',panelHeight:120" />
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="pickList()">预览拣货清单</a>			
	</div>
  </body>
</html>
