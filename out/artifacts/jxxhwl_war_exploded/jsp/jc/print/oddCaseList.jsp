<%@ page language="java" import="java.util.*, java.text.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>零件清单</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.4.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/oddCaseList.js"></script>
	<style type="text/css">
		tr{
			height:35px;
		}
		.td1{
			height:1px;
			border:none;
			border-top:1px solid #555555;
			border-bottom:1px solid #555555;
		}
		.td2{
			height:1px;
			border:none;
			border-bottom:1px solid #555555;
		}
	</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%
		Date d = new Date();
  		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  		String dt = df.format(d); 
	 %>
  </head>
  
  <body>
  	<input type="hidden" id="batchno" value="${batchno }" />
    <div align="left" id='d' style="font-size:20px;width:770px;margin-left:40px;">
    	<div align="center" >
    		<h3>江西新华物流有限公司</h3>
    		<h3>零件储运发货清单</h3>
    	</div>
    	<div align="left" >
    		<table width='90%' style="border:0px;" frame=hsides rules='rows'>
    			<tr>
    				<td colspan="5">调度批号：${batchno }</td><td>列印时间：<%= dt %></td>
    			</tr>
    		</table>
    		<table id="t1" width="90%" frame=hsides rules='rows'>
    			<tr>
    				<td width='10%'>征订号</td><td  width='20%'>书名</td><td  width='10%'>出版社</td><td  width='10%'>价格</td><td  width='10%'>数量</td><td  width='10%'>捆扎信息</td>
    			</tr>
    		</table>
    	</div>
    	<div align="left" style="padding:50px 0px" >
    		<table id="t2" width="90%" cellspacing=0 frame=hsides rules='rows'>
    			<tr><td class="td1" width='10%'>客户代码</td><td class="td1" width='10%'>客户名称</td><td class="td1" width='10%'>运号</td><td  class="td1" width='10%'>数量</td><td class="td1" width='10%'>捆扎</td><td  class="td1" width='10%'>包件</td></tr>
    		</table>
    	</div>
    </div>
    	
    <div align="center" style="margin-top:50px;">
		<a class="easyui-linkbutton" onclick="doJqprint()">打印</a>
	</div>
  </body>
</html>
