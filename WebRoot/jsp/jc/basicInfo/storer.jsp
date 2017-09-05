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
    
    <title>客户供商信息维护</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/storer.js"></script>
  </head>
  
  <body>
    <div align="center">
		<h2 style="color:#0078CA">客户供商信息维护&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<div align="center" style="padding-top:5px;">
		<table id="data" class="easyui-datagrid" width='100%' data-options="collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
		border:true,pagination:true,pageSize:20,pageList:[10,15,20],toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'id',width:80,checkbox:true">编号</th>
					<th data-options="field:'storerkey',width:50">代码</th>
					<th data-options="field:'shortname',width:80">名称</th>
					<th data-options="field:'contact',width:80">联系人</th>
					<th data-options="field:'phone',width:80">联系电话</th>
					<th data-options="field:'address',width:80">地址</th>
					<th data-options="field:'type',width:80">类型</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="text-align:center">
    	<a class="easyui-linkbutton" iconCls="icon-search" onClick="tool.query();">查询</a>
    	<a class="easyui-linkbutton" iconCls="icon-add" onClick="tool.add();">添加</a>
    	<a class="easyui-linkbutton" iconCls="icon-edit" onClick="tool.edit();">修改</a>
    	<a class="easyui-linkbutton" iconCls="icon-remove" onClick="tool.remove();">删除</a>
    </div>
    <!-- 增加期号信息窗口 -->
    <div id="w-addInfo" class="easyui-window" title="添加"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 400px; padding: 10px;">
		<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>添加</h2>
			代码：<input class="easyui-textbox" type="text" id="storerkey"/><br>
			名称：<input class="easyui-textbox" type="text" id="shortname"/></br>
			联系人：<input class="easyui-textbox" type="text" id="contact"/></br>
			联系电话：<input class="easyui-textbox" type="text" id="phone"/></br>
			地址：<input class="easyui-textbox" type="text" id="address"/></br>
			类型：<select id="type" class="easyui-combobox" style="width:100px;" data-options="editable:false">
					<option value="0">客户</option>
					<option value="1">供商</option>
				</select>
			<br>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" id="addInfo" onclick="addInfo()">确认</a>
		</form>
	</div>
    <!-- 查询期号信息窗口 -->
    <div id="w-queryInfo" class="easyui-window" title="查询"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 280px; padding: 10px;">
		<form id="queryform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>查询</h2>
			代码：<input class="easyui-textbox" type="text" id="query_storerkey"/><br>
			名称：<input class="easyui-textbox" type="text" id="query_shortname"/></br>
			类型：<select id="query_type" class="easyui-combobox" style="width:100px;" data-options="editable:false">
					<option value="0">客户</option>
					<option value="1">供商</option>
				</select>
			<br>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" id="queryInfo" onclick="queryInfo()">查询</a>
		</form>
	</div>
    <!-- 修改期号信息窗口 -->
    <div id="w-editInfo" class="easyui-window" title="修改"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 400px; padding: 10px;">
		<form id="editform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>查询</h2>
			<input type="hidden" id="edit_id">
			代码：<input class="easyui-textbox" type="text" id="edit_storerkey"/><br>
			名称：<input class="easyui-textbox" type="text" id="edit_shortname"/></br>
			联系人：<input class="easyui-textbox" type="text" id="edit_contact"/></br>
			联系电话：<input class="easyui-textbox" type="text" id="edit_phone"/></br>
			地址：<input class="easyui-textbox" type="text" id="edit_address"/></br>
			类型：<select id="edit_type" class="easyui-combobox" style="width:100px;" data-options="editable:false">
					<option value="0">客户</option>
					<option value="1">供商</option>
				</select>
			<br>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" id="queryInfo" onclick="editInfo()">确定</a>
		</form>
	</div>
  </body>
</html>
