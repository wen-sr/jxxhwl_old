<%@ page language="java" import="java.util.*,xhwl.entity.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>小票</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<STYLE type="text/css">
		.f{
			font-size:25px;font-weight:bold;
		}
	</STYLE>
  </head>
  
  <body>
  	<div align="center">
  	<s:iterator value="list">
  	<table>
  		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
  			<tr><td colspan='2' align="center"></td></tr>
  			<tr><td colspan='2' align="center">${subcode }</td></tr>
  			<tr><td>&nbsp;</td><td>&nbsp;${descr }</td></tr>
  			<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
  			<tr><td colspan='2' align="center">${customer }</td></tr>
  			<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
  			<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
  			<tr><td colspan='2' align="center">${customer }</td></tr>
  			<tr><td>&nbsp;${caseqty }</td><td>&nbsp;${shipno }</td></tr>
  			<tr><td></td><td></td></tr>
  	</table>
  	</s:iterator>
  	</div>
  	
  </body>
</html>
