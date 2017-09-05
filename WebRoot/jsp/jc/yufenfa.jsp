<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>业务预分发</title>
    
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
	<script type="text/javascript" src="js/jclogin.js"></script>	
	<script type="text/javascript" src="js/jcyufenfa.js"></script>	
	<script type="text/javascript" src="js/jcinput.js"></script>	

  </head>
  
  <body>
    <DIV align="center" style="vertical-align: middle;"> 
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">业务预分发&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.username'/>" id="username"/>
	<input type="hidden" value="<s:property value='#session.password'/>" id="password"/>
	<form id="f1" name="f1">
			<input type="hidden" id="oldid"/>
			<table border="0px" align="center">
				<tr>
					<td>期号:</td><td><input type="text" id="issuenumber" /></td>
					<td>征订代码：</td><td><input type="text" id="subcode1" /></td>
					<td>条码：</td><td><input type="text" id="barcode" /></td>
				</tr>
				<tr>
					<td>书名：</td><td><input type="text" id="descr" /></td>
					<td>出版社：</td><td><input type="text" id="publisher"/></td>
					<td>定价：</td><td><input type="text" id="price" /></td>
				</tr>
				<tr>
					<td>分发店：</td><td><select class="easyui-combobox" style="width:172" id="customer" data-options="url:'loadjiaocaistorer.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'300' " ></select></td>
					<td>分发数量：</td><td><input type="text" id="qtyfenfa"/></td>
					<td>分发人：</td><td><input type="text" id="addwho" value="<s:property value='#session.username'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
					<input class="easyui-linkbutton" type="button" id="submitgo" value="提交"/>
					<input class="easyui-linkbutton" type="reset" value="重置"/>
					<input class="easyui-linkbutton" type="button" value="返回" onclick="location.href='jc.action'"/>
					<input class="easyui-linkbutton" type="button" value="退出系统" onclick="exit();"/>
					</td>
				</tr>
			</table>
	</form>
	<div align="center">
		<form>
			<table id="t" class="easyui-datagrid" width='90%' data-options="collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
			border:true,pagination:true,pageSize:20,pageList:[10,15,20],toolbar:'#tb'">
				<thead>
					<tr>
						<th data-options="field:'id',width:80,checkbox:true">编号</th>
						<th data-options="field:'issuenumber',width:50">期号</th>
						<th data-options="field:'subcode',width:80">征订代码</th>
						<th data-options="field:'publisher',width:50">出版社</th>
						<th data-options="field:'descr',width:100">书名</th>
						<th data-options="field:'price',width:50">定价</th>
						<th data-options="field:'pack',width:50">包册数</th>
						<th data-options="field:'qtyshou',width:80">收货数量 </th>
						<th data-options="field:'qtyallocated',width:80">已分发数量 </th>
						<th data-options="field:'qtyfree',width:80">可用库存 </th>
						<th data-options="field:'customer',width:50">客户 </th>
						<th data-options="field:'addwho',width:50">分发人 </th>
						<th data-options="field:'adddate',width:50">分发时间</th>
					</tr>
				</thead>
			</table>
		</form>
	</div>	
  </body>
</html>
