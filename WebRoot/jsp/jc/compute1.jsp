<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>配发</title>
    
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
	<link rel="stylesheet" type="text/css" href="css/table_jccompute.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/drop_table_tr.js"></script>
	<script type="text/javascript" src="js/jccompute.js"></script>

  </head>
  
  <body>
     <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">配发</h2>
	</div>
	<div align="center">
		期号：<input type="text" id="issuenumber" />
		征订代码：<input type="text" id="subcode" />
		<select id="type"><option value="0">需分品种</option><option value="1">所有品种</option></select>
		<input type="button" value="查询" id="bb"/>&nbsp;
		<input type="button" value="配发计算" id="compute" />
		<input type="button" value="返回" onclick="location.href='jc.action'"/>
	</div>
	<div style="text-align:center;"><div align='center' class='between' ></div></div>
	<div align="center" id="d1">
		<table align="center" id="t1">
			<tr>
				<td align='center'><input type='checkbox' id='btnCheckAll'/></td><td>类型</td><td>期号</td><td>征订代码</td><td>书名</td><td>定价</td><td>包册数</td><td>出版社</td><td>店名</td><td>到站</td><td>发货数量</td><td>分配数量</td><td>运输数量</td><td>可用量</td><td>收货人</td>
			</tr>
		</table>
	</div>
  </body>
</html>
