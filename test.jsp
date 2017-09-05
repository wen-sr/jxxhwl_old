<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		.div_box{
			width:7cm;
			height:12cm;
			border:1px solid #fff;
			margin-left:2cm;
			margin-top:2cm;
		}
		.div_subcode{
			text-align:center;
			font-size:20px;
			font-weight:bold;
			margin-top:1.5cm;
		}
		.div_descr{
			text-align:center;
			font-size:20px;
			font-weight:bold;
			margin-top:1.0cm;
		}
		.div_customer{
			text-align:center;
			font-size:20px;
			font-weight:bold;
			margin-top:1.0cm;
		}
		.div_station{
			text-align:center;
			font-size:30px;
			font-weight:bold;
			margin-top:1.0cm;
		}
		.div_caseqty{
			text-align:center;
			font-size:30px;
			font-weight:bold;
			margin-top:1.0cm;
			float:left;
			margin-left:1cm;
		}
		.div_shipno{
			text-align:center;
			font-size:30px;
			font-weight:bold;
			margin-top:1.0cm;
			float:right;
			margin-right:1cm;
		}
	</style>
  </head>
  
  <body>
    <div class='div_box'>
    	<div class='div_subcode'>054017260577</div>
    	<div class='div_descr'>高中历史地图册 必修2 配人教版（内页）</div>
    	<div class='div_customer'>南昌市</div>
    	<div class='div_station'>南昌市</div>
    	<div class='div_caseqty'>5</div>
    	<div class='div_shipno'>10005</div>
    </div>
  </body>
</html>
