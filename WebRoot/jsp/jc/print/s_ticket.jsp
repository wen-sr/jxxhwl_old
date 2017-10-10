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
    
    <title>票签</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jc/predistribution.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jc/s_ticket.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.4.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jc/s_ticket.js"></script>
	
	<script type="text/javascript">
		function doJqprint(){
			$("#d").jqprint();
		}
	</script>
  </head>
  
  <body>
  	<input type="hidden" id="batchno" value="${batchno}"/>
 	<a class="easyui-linkbutton" style="margin-left:30%;" data-options="iconCls:'icon-print',size:'small'" onclick="doJqprint()">打印</a>
  	<div id="d" style="width:32.6cm">
	  	<%-- <s:iterator value="map.list"  status="st">
	  		<table>
	  		<s:iterator begin="1" end="caseqty-1" status="stt">
	  		<s:if test="#stt.index % 5 == 0">
	  			<tr>
	  			<td>
				  	<table width="300px">
				  		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center"></td></tr>
						<tr><td colspan='2' align="center">${subcode }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;${descr }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center">${shortname }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center">${shortname }</td></tr>
						<tr><td align="center">&nbsp;${caseqty }</td><td align="center" >&nbsp;${shipno }</td></tr>
						<tr><td></td><td></td></tr>
				  	</table>
			  	<td>
			</s:if>
			<s:elseif test="#stt.index % 5 == 4">
	  			<td>
				  	<table  width="300px">
				  		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center"></td></tr>
						<tr><td colspan='2' align="center">${subcode }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;${descr }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center">${shortname }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center">${shortname }</td></tr>
						<tr><td align="center">&nbsp;${caseqty }</td><td align="center" >&nbsp;${shipno }</td></tr>
						<tr><td></td><td></td></tr>
				  	</table>
			  	<td>
			  	<tr>
			</s:elseif>
			<s:else>
				<td>
				  	<table  width="300px">
				  		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center"></td></tr>
						<tr><td colspan='2' align="center">${subcode }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;${descr }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center">${shortname }</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td colspan='2' align="center">${shortname }</td></tr>
						<tr><td align="center">&nbsp;${caseqty }</td><td align="center" >&nbsp;${shipno }</td></tr>
						<tr><td></td><td></td></tr>
				  	</table>
			  	<td>
			</s:else>  	
		  	</s:iterator>
		  	</table>
	  	</s:iterator> --%>
  	</div>
  	
  </body>
</html>
