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

<title>部门管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jc/predistribution.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/add_department.js"></script>
</head>

<body>
	<div align="center">
		<h2 style="color:#0078CA">
			部门管理&nbsp;<span style="font-size:20px;color:#0078CA"><s:property
					value='#session.name' />
			</span>
		</h2>
		<div>
			<table class="easyui-datagrid" width="100%" id="data">
			</table>
		</div>
	</div>
	<div id="tb" style="text-align: center;display: none;">
		<a class="easyui-linkbutton" iconCls="icon-add" onclick="tool.add()">添加</a>
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.edit()">修改</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="tool.remove()">删除</a>
	</div>
	<div id="w_addInfo" class="easyui-window" title="添加"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 350px; padding: 10px;">
		<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>添加部门</h2>
			部门：<input class="easyui-combobox" id="add_name"style="width:150px" data-options="url: 'department_loadDepartment.action',method: 'post',valueField: 'id',textField: 'name'"/>
			<br>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" id="addInfo" onclick="addInfo()">确认</a>
		</form>
	</div>
	<div id="w_editInfo" class="easyui-window" title="修改"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 350px; padding: 10px;">
		<form id="editform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>修改部门</h2>
			<input type="hidden" id="edit_id" >
			部门：<input class="easyui-combobox" id="edit_name"style="width:150px" data-options="url: 'department_loadDepartment.action',method: 'post',valueField: 'id',textField: 'name'"/>
			<br>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="editInfo()">确认</a>
		</form>
	</div>
</body>
</html>
