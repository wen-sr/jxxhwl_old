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
		width:700px;
	}
	.t_dd td{
		font-size:15px;
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
    			<table border='0px' cellspacing='0' cellpadding='0' class="t_dd" height='45mm' >
		    		<tr height="35px">
		    			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td colspan='5'>${shortname }</td><td colspan='9'>&nbsp;</td>
		    		</tr>
		    		<tr height="35px"><td>&nbsp;</td><td colspan='10'>${descr }</td><td>&nbsp;</td><td colspan='3' align='right'>${publisher }</td></tr>
		    		<tr height="35px"><td>&nbsp;</td><td colspan='5'>${price }</td><td colspan='6'>&nbsp;</td><td colspan='3' align='right'>${issuenumber}${subcode }</td></tr>
		    		<tr height="35px"><td>&nbsp;</td><td>${qtyallocated }</td><td>&nbsp;&nbsp;&nbsp;</td><td  align='center'>&nbsp;${caseqty }</td><td colspan='3'>&nbsp;</td><td colspan='3' align='right'>&nbsp;&nbsp;&nbsp;${pack*2 }</td><td>&nbsp;</td><td colspan='5' align='right'>${oddpack }捆零${odd }本</td></tr>
		    		<tr height="35px"><td>&nbsp;</td><td>${shipno }</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>${subcode }</td><td colspan='4'>&nbsp;</td></tr>
		    	</table>
    		</td>
    		<td >
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
    		</td>
    	</s:if>
    	<s:else>
    		<td>
    			<table border='0px' cellspacing='0' cellpadding='0' class="t_dd">
		    		<tr height="35px">
		    			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td colspan='5'>${shortname }</td><td colspan='9'>&nbsp;</td>
		    		</tr>
		    		<tr height="35px"><td>&nbsp;</td><td colspan='10'>${descr }</td><td>&nbsp;</td><td colspan='3' align='right'>${publisher }</td></tr>
		    		<tr height="35px"><td>&nbsp;</td><td colspan='5'>${price }</td><td colspan='6'>&nbsp;</td><td colspan='3' align='right'>${issuenumber}${subcode }</td></tr>
		    		<tr height="35px"><td>&nbsp;</td><td>${qtyallocated }</td><td>&nbsp;&nbsp;&nbsp;</td><td  align='center'>&nbsp;${caseqty }</td><td colspan='3'>&nbsp;</td><td colspan='3' align='right'>&nbsp;&nbsp;&nbsp;${pack*2 }</td><td>&nbsp;</td><td colspan='5' align='right'>${oddpack }捆零${odd }本</td></tr>
		    		<tr height="35px"><td>&nbsp;</td><td>${shipno }</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>${subcode }</td><td colspan='4'>&nbsp;</td></tr>
		    	</table>
    		</td>
    		</tr>
    		</table>
    		<table border='0'>
   				<tr style='height:40px;'><td colspan='10' >&nbsp;</td></tr>
    		</table>
    	</s:else>
    </s:iterator>
    </div>
    <br>
    
  </body>
</html>
