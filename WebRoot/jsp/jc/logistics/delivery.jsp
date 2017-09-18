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
    
    <title>发货</title>
    
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
	<script type="text/javascript" src="js/jcdelivery.js"></script>

  </head>
  
  <body>
     <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">发货&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span>&nbsp;<input type="button" value="返回" onclick="location.href='jc.action'"/></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.username'/>" id="username"/>
	<input type="hidden" value="<s:property value='#session.password'/>" id="password"/>
	<div align="center">
		<form id="f">
			<select id="type"><option value="0">整件发货</option></select>
			期号：
			<input type="text" id="issuenumber" class="easyui-combobox"
				data-options="url:'loadfenfaissuenumber.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:200" />
			<a class="easyui-linkbutton" id="choosesubcode">选择征订代码</a><input class="easyui-textbox" id="subcode" style="width:150px"  >
			<input type="button" value="查询" id="bb"/>
			<input type="button" value="打印" id="print" onclick="window.open('fhprint.action')"/>
		</form>
	</div>
	<div style="height:93%;">
		<table class="easyui-datagrid" width="98%"  id="t1" data-options="collapsible:false,height:'auto',singleSelect:false,fit: true, fitColumns: true,striped:true,rownumbers:true,
			border:true,toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'id',width:20,checkbox:true">编号</th>
					<th data-options="field:'issuenumber',width:20">期号</th>
					<th data-options="field:'subcode',width:50">征订代码</th>
					<th data-options="field:'descr',width:80">书名</th>
					<th data-options="field:'price',width:30">定价</th>
					<th data-options="field:'pack',width:30">包册数</th>
					<th data-options="field:'customer',width:30">店名</th>
					<th data-options="field:'qtyallocated',width:30">分配数量</th>
					<th data-options="field:'caseqty',width:30">整包件数</th>
					<th data-options="field:'oddpack',width:30">零捆</th>
					<th data-options="field:'odd',width:30">零册</th>
					<th data-options="field:'addwho',width:30">分发/配发人</th>
					<th data-options="field:'adddate',width:30">分发/配发时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<div align="center" id="show">
		
	</div>
	<div id="w2" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 800px; height: 350px; padding: 10px;">
			<table id="t_subcode" class="easyui-datagrid" data-options="collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
			toolbar:'#tb2'" width='90%'>
				<thead>
				<tr>
				<th data-options="field:'issuenumber',width:80">
					期号
				</th>
				<th data-options="field:'subcode',width:80">
					征订代码
				</th>
				<th data-options="field:'descr',width:200">
					书名
				</th>
				<th data-options="field:'totalAllocatedqty',width:100">
					需配发量
				</th>
				<th data-options="field:'totalcomputeqty',width:100">
					已配发量
				</th>
				<th data-options="field:'qtyfree',width:100">
					可用量
				</th>
				</tr>
				</thead>
			</table>
		</div>
		<div id="tb" style="text-align: center">
			<a class="easyui-linkbutton" iconCls="icon-edit" id="batchno">生成批次</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" id="">取消</a>
		</div>
		<div id="tb2" style="text-align: center">
			<a class="easyui-linkbutton" iconCls="icon-edit" id="yes">确定</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" id="no">取消</a>
		</div>
  </body>
</html>
