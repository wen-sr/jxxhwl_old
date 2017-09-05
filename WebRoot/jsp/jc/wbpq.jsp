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
    	<div style="align:center;width:300px;height:300px;padding:20px 5px;" align="center">
    		<div align="center"><span style="font-size:30px;font-weight:bold;">${subcode }</span></div>
    		<br/>
    		<div align="left">
    			<span class='f'>批次号：${ batchno }</span>
    			<br/>
    			<span class='f'>运输号：${ shipno }</span>
    			<br/>
    			<span style="font-family:Code39QuarterInchTT-Regular;font-size:50px">${ shipno }</span>
    			<br/>
    			<span class='f'>收件方：${ customer }</span>
    			<br/>
    			<span class='f'>包件数：${ caseqty } / ${ caseqty }</span>
    			<span style="font-size:25px;font-weight:bold;padding:10px;border:1px;">教材</span> 
    		</div>
    		<div align="center"><h2>江西新华物流有限公司</h2></div>
    	</div>
    </s:iterator>
    </div>
  </body>
</html>
