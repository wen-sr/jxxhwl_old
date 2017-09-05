<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>收货确认</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/jc.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jcreceipt.js"></script>

  </head>
  
  <body>
    <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">收货确认</h2>
	</div>
	<div align="center" id="d1">
		征订代码：<input type="text" id="subcode" />&nbsp;<input type="button" value="查询" id="bb"/><br/><br/>
		<form id="f1">
			<input type="hidden" id="oldid"/>
			<table>
			<tr>
				<td>期号:</td><td><input type="text" id="issuenumber" /></td><td>书名：</td><td><input type="text" id="descr" /></td><td>出版社：</td><td><input type="text" id="publisher"/></td>
			
				<td>定价：</td><td><input type="text" id="price" /></td>
				</tr>
			<tr>
				<td>捆扎：</td><td><input type="text" id="qtyper" /></td>
				<td>收货数量：</td><td><input type="text" id="qtyshou"/></td><td>收货人：</td><td><input type="text" id="addwho"/></td><td></td><td></td>
			</tr>
			<tr><td colspan='8' align="center">&nbsp;&nbsp;&nbsp;<input type="button" id="go" value="提交"/><input type="button" value="返回" onclick="location.href='jc.action'"/></td></tr>
			</table>
		</form>
 	</div>
 	<div style="text-align:center;"><div align='center' class='between' ></div></div>
 	<DIV ID="show" align="center">
			<input type="hidden" id="currentpage" />
			<table id="t2" border='1px' cellspacing='0' cellpadding='0' width='90%'>
				<tr><td>编号</td><td>期号</td><td>征订代码</td><td>出版社</td><td>书名</td><td>定价</td><td>包册数</td><td>收货数量</td><td>已分配数量</td><td>可用库存</td><td>收货人</td><td>收货时间</td><td>操作</td></tr>
				<tr></tr>
			</table>
	</DIV>
  </body>
</html>
