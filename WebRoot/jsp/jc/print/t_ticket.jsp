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
    
    <title>尾包票签</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jc/t_ticket.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.4.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint.js"></script>
	<STYLE type="text/css">
		.f{
			font-size:25px;
			font-weight:bold;
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
    <s:iterator value="map.list" status="st" >
    	<div class="data">
			<div class='d_subcode'>${issuenumber}${subcode }</div>
			<div class='d_batchno'>批次号：${ batchno }</div>
			<div class='d_shipno'>运输号：${ shipno }</div>
			<div class='d_shipno_1'>${ shipno }</div>
			<div class='d_shortname'>收件方：${shortname }</div>
			<div class='d_caseqty'>包件数：${ caseqty } / ${ caseqty }</div>
			<div class='d_note'>
				<span style='font-size:25px;font-weight:bold;padding:1px;border:1px solid #000;'>教材</span>
			</div>
			<div class='d_buttom'>
				<h2>江西新华物流有限公司</h2>
			</div>
		</div>
    	
    	
    	
    	
    	
    	
    	
<!--     	<table> -->
<!--     		<tr><td>&nbsp;</td><td style=font-size:30px;font-weight:bold; align="center">${issuenumber}${subcode }<td></tr> -->
<!--     		<tr> -->
<!--     			<td>&nbsp;</td><td class='f' style="font-size:22px;font-weight:bold;">批次号：${ batchno }</td> -->
<!--     		<tr/> -->
<!--     		<tr>	 -->
<!--     			<td>&nbsp;</td><td class='f' style="font-size:22px;font-weight:bold;">运输号：${ shipno }</td> -->
<!--     		<tr/> -->
<!--     		<tr>	 -->
<!--     			<td>&nbsp;</td><td style="font-family:Code39QuarterInchTT-Regular;font-size:50px;">${ shipno }</td> -->
<!--     		<tr/> -->
<!--     		<tr>	 -->
<!--     			<td>&nbsp;</td><td class='f' style="font-size:22px;font-weight:bold;">收件方：${ shortname }</td> -->
<!--     		<tr/> -->
<!--     		<tr> -->
<!--     			<td>&nbsp;</td><td style="font-size:22px;font-weight:bold;">包件数：${ caseqty } / ${ caseqty } &nbsp;&nbsp;&nbsp;&nbsp; -->
<!--     			<span style="font-size:25px;font-weight:bold;padding:3px;border:1px solid #000;">教材</span></td>  -->
<!--     		</tr> -->
<!--     		<tr><td colspan='2' align='center'><h2>江西新华物流有限公司</h2></td></tr> -->
<!--     	</table> -->
    	
    </s:iterator>
    </div>
  </body>
</html>
