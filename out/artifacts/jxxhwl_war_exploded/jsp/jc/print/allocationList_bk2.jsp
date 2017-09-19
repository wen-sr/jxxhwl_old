<%@ page language="java" import="java.util.*, java.text.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>调拨单</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/allocationList.js"></script>
	<style type="text/css">
	table {
		width:600px;
	}
	.t_dd td{
		font-size:8px;
	}
	</style>
	
  </head>
  	
  <body>
  	<% 
		Date d = new Date(); 
  		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日"); 
  		String dt = df.format(d);  
	 %>
	<input type="hidden" id="batchno" value="<s:property value='%{map.batchno}'/>" />
	<a class="easyui-linkbutton" style="margin: 10px 20%;" data-options="iconCls:'icon-print',size:'small'" onclick="doJqprint()">打印</a>
	<div id="d">
    <s:iterator value="map.list"  status="st">
    	<s:if test="(#st.index)%2 == 0">
	    <table>
    	<tr>
    		<td>
   				<h4 align='center'>江西新华发行集团有限公司调拨单</h4>
   				<h6 align='right' style='padding-right:50px;'>(二)随货同行联</h6>
    			<table border='1px' cellspacing='0' cellpadding='0' class="t_dd" height='7cm' style='font-size:13px'>
		    		<tr height="30px">
		    			<td>店名</td><td colspan='10' align='center'>${shortname }</td>
		    		</tr>
		    		<tr height="30px"><td>书名</td><td colspan='5'>${descr }</td><td>版别</td><td>${publisher }</td></tr>
		    		<tr height="30px"><td>定价</td><td>${price }</td><td>征订代码</td><td colspan='5' align='center'>${issuenumber}${subcode }</td></tr>
		    		<tr height="30px"><td>数量</td><td>${qtyallocated }</td><td>整包</td><td align='center'>${caseqty }</td><td>每包</td><td>${pack*2 }本</td><td>尾包数</td><td>${oddpack }捆零${odd }本</td></tr>
		    		<tr height="30px"><td>运号</td><td>${shipno }</td><td>书号</td><td>${subcode }</td><td>备注</td><td colspan='5'>&nbsp;</td></tr>
		    	</table>
    		</td>
    		<td >
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
    		</td>
    	</s:if>
    	<s:else>
    		<td>
    			<h4 align='center'>江西新华发行集团有限公司调拨单</h4>
   				<h5 align='right' style='padding-right:50px;'>(二)随货同行联</h5>
    			<table border='1px' cellspacing='0' cellpadding='0' class="t_dd" height='7cm' style='font-size:13px'>
		    		<tr height="30px">
		    			<td>店名</td><td colspan='10' align='center'>${shortname }</td>
		    		</tr>
		    		<tr height="30px"><td>书名</td><td colspan='5'>${descr }</td><td>版别</td><td>${publisher }</td></tr>
		    		<tr height="30px"><td>定价</td><td>${price }</td><td>征订代码</td><td colspan='5' align='center'>${issuenumber}${subcode }</td></tr>
		    		<tr height="30px"><td>数量</td><td>${qtyallocated }</td><td>整包</td><td align='center'>${caseqty }</td><td>每包</td><td>${pack*2 }本</td><td>尾包数</td><td>${oddpack }捆零${odd }本</td></tr>
		    		<tr height="30px"><td>运号</td><td>${shipno }</td><td>书号</td><td>${subcode }</td><td>备注</td><td colspan='5'>&nbsp;</td></tr>
		    	</table>
    		</td>
    		</tr>
    		</table>
    		
    		<table border='0'>
   				<tr style='height:45px;'><td colspan='10' >&nbsp;</td></tr>
    		</table>
    	</s:else>
    </s:iterator>
    </div>
    <br>
    
  </body>
</html>
