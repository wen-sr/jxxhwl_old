<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>手拣单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<STYLE type="text/css">
		td{
			text-align: center;
		}
		#tt td,th{
			height:30px;
		}
	</STYLE>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jc/predistribution.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.4.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/pickList.js"></script>
  </head>
  
  <body>
  	<input type="hidden" id="pick" name='pick' value="${pickno }" />
  	<div id="d">
  	<h2 align="center">江西新华物流有限公司</h2>
  	<h3 align="center">拣货清单</h3>
  	</div>
  	<br/>
  	<div align="center" style="margin-top:50px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-print',size:'small'" onclick="doJqprint();">打印</a>
	</div>
  </body>
</html>
