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
		div{
			padding:0;
			margin:0;
		}
	</STYLE>
	<script type="text/javascript">
		function doJqprint(){
			$("#d").jqprint();
		}
	</script>
  </head>
  
  <body>
  	<%--<a class="easyui-linkbutton" style="margin: 0 6%;" data-options="iconCls:'icon-print',size:'small'" onclick="doJqprint()">打印</a>--%>
  	<div id="d">
    <s:iterator value="map.list" status="st" >
    	<div class="data" style="width:79mm; height: 89mm;">
			<div class='d_subcode'>${issuenumber}${subcode }</div>
			<div class='d_batchno'>批次号：${ batchno }</div>
			<div class='d_shipno'>运输号：${ shipno }</div>
			<div class='d_shipno_1'>${ shipno }</div>
			<div class='d_shortname'>收件方：${shortname }</div>
			<div class='d_caseqty'>包件数：${ caseqty } / ${ caseqty }</div>
			<div class='d_note'>
				<span style='font-size:25px;font-weight:bold;padding:1px;border:1px solid #000;margin-left: 5mm;'>农家</span>
			</div>
			<div class='d_buttom'>
				<h2>江西新华物流有限公司</h2>
			</div>
		</div>
    </s:iterator>
    </div>
  </body>
</html>
