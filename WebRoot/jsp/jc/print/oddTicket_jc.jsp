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
    
    <title>票签</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jc/predistribution.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.4.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint.js"></script>
	<STYLE type="text/css">
		.f{
			font-size:25px;font-weight:bold;
		}
	</STYLE>
	<script type="text/javascript">
		function doJqprint(){
			$("#d").jqprint();
		}
	</script>
  </head>
  
  <body>
  	<a class="easyui-linkbutton" style="margin: 10px 6%;" data-options="iconCls:'icon-print',size:'small'" onclick="doJqprint()">打印</a>
  	<div id="d">
    	<table>
    		<tr><td style="font-size:30px;font-weight:bold;"align='center'>中小学教材<td></tr>
    		<tr>
    			<td class='f' style="font-size:22px;font-weight:bold;">批次号：${ d.batchno }</td>
    		<tr/>
    		<tr>	
    			<td class='f' style="font-size:22px;font-weight:bold;">运输号：${ d.shipno }</td>
    		<tr/>
    		<tr>	
    			<td style="font-family:Code39QuarterInchTT-Regular;font-size:50px">${ d.shipno }</td>
    		<tr/>
    		<tr>	
    			<td class='f' style="font-size:30px;font-weight:bold;">收件方：${ d.shortname }</td>
    		<tr/>
    		<tr>
    			<td class='f' style="font-size:22px;font-weight:bold;">包件数：1 / 1 &nbsp;&nbsp;&nbsp;
    			<span style="font-size:25px;font-weight:bold;padding:3px;border:1px solid #000;">拼</span></td> 
    		</tr>
    		<tr><td><h3 align="center">江西新华物流有限公司</h3></td></tr>
    	</table>
    </div>
  </body>
</html>
