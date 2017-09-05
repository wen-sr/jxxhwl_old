<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>信息录入</title>
    
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
	<script type="text/javascript" src="js/jcinput.js"></script>	
  </head>
  
  <body>
  	<DIV align="center" style="vertical-align: middle;"> 
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">信息录入</h2>
	</div>
		<form action='' id="inputform">
			<input type='hidden' id="oldid">
			<table align="center" id='t1' >
				<tr>
					<th>
						类型
					</th>
					<th>
						<input type='text' id='type' />
					</th>
					<th>
						期号
					</th>
					<th>
						<input type='text' id='issuenumber' />
					</th>
					<th>
						征订代码
					</th>
					<th>
						<input type='text' id='subcode' />
					</th>
					<th>
						书名
					</th>
					<th>
						<input type='text' id='descr' />
					</th>
					<th>
						店名
					</th>
					<th>
						<input type='text' id='customer' />
					</th>
				</tr>
				<tr>
					<th>
						发货数量
					</th>
					<th>
						<input type='text' id='qtyfa' />
					</th>
					<th>
						到站
					</th>
					<th>
						<input type='text' id='station' />
					</th>
					<th>
						出版社
					</th>
					<th>
						<input type='text' id='publisher' />
					</th>
					<th>
						联系人
					</th>
					<th>
						<input type='text' id='receiveuser' />
					</th>
					<th>
						录单人
					</th>
					<th>
						<input type='text' id='addwho' />
					</th>
				</tr>
			</table>
			<div id='btn'>
				<h5 align='center'>
					<input type='button' id='sv' value='保存' />
					<input type="reset" value="清空" />
					<input type="button" value="返回" onclick="location.href='jc.action'"/>
					<input type="hidden" id="oldid" />
				</h5>
			</div>
		</form>
		<div style="text-align:center;"><div align='center' class='between' ></div></div>
		<DIV ID="show" align="center">
			<input type="hidden" id="currentpage" />
			<table id="t2" border='1px' cellspacing='0' cellpadding='0' width='90%'>
				<tr><td>编号</td><td>类型</td><td>期号</td><td>征订代码</td><td>书名</td><td>店名</td><td>发货数量</td><td>到站</td><td>出版社</td><td>联系人</td><td>录单人</td><td>状态</td><td>操作</td></tr>
			</table>
		</DIV>
	</body>
</html>
