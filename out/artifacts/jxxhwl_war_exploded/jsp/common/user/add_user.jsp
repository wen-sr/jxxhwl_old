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

<title>用户管理</title>

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
	src="${pageContext.request.contextPath}/js/common/add_user.js"></script>
</head>

<body>
	<div align="center">
		<h2 style="color:#0078CA">
			用户管理&nbsp;<span style="font-size:20px;color:#0078CA"><s:property
					value='#session.name' />
			</span>
		</h2>

		工号：<input class="easyui-textbox" id="uid" style="width:100px" />
		姓名：<input class="easyui-textbox" id="uname" style="width:100px" />
		部门：<input class="easyui-combobox" id="department"style="width:100px" data-options="url: 'department_loadDepartment.action',method: 'post',valueField: 'id',textField: 'name'"/>
		状态：<select id="status" class="easyui-combobox"  style="width:80px;">
			    <option value="">所有</option>
			    <option value="1">已激活</option>
			    <option value="0">未激活</option>
			</select>
		<a class="easyui-linkbutton" id='go' onclick="go()">&nbsp;查询&nbsp;</a>
		<div>
			<table class="easyui-datagrid" width="100%" id="data">
			</table>
		</div>
	</div>
	<div id="tb" style="text-align: center;display: none;">
		<a class="easyui-linkbutton" iconCls="icon-add" onclick="tool.add()">添加</a>
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.edit()">修改</a>
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.editAuth()">角色修改</a>
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.getAuth()">查看角色</a>
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.edit()">修改</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="tool.remove()">删除</a>
	</div>
	<div id="w_addInfo" class="easyui-window" title="添加"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 350px; padding: 10px;">
		<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>添加员工</h2>
			工号：<input class="easyui-textbox" type="text" id="add_id"/>
			<br>
			姓名：<input class="easyui-textbox" type="text" id="add_username"/>
			<br>
			拼音首字母：<input class="easyui-textbox" type="text" id="add_py"/>
			<br>
			部门：<input class="easyui-combobox" id="add_departId"style="width:150px" data-options="url: 'department_loadDepartment.action',method: 'post',valueField: 'id',textField: 'name'"/>
			<br>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" id="addInfo" onclick="addInfo()">确认</a>
		</form>
	</div>
	<div id="w_editInfo" class="easyui-window" title="修改"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 350px; padding: 10px;">
		<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>修改员工</h2>
			工号：<input class="easyui-textbox" type="text" id="edit_id" data-options="readonly:true"/>
			<br>
			姓名：<input class="easyui-textbox" type="text" id="edit_username"/>
			<br>
			拼音首字母：<input class="easyui-textbox" type="text" id="edit_py"/>
			<br>
			部门：<input class="easyui-combobox" id="edit_departId"style="width:150px" data-options="url: 'department_loadDepartment.action',method: 'post',valueField: 'id',textField: 'name'"/>
			<br>
			状态：<select id="edit_status" class="easyui-combobox" name="edit_status" style="width:150px;">
				    <option value="1">已激活</option>
				    <option value="0">未激活</option>
				</select>
			<br>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="editInfo()">确认</a>
		</form>
	</div>
	<div id="auth" class="easyui-window" title="角色修改"
			data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 350px; padding: 10px;">
		<span style="font-size:20px">角色修改：</span><input id = "role_id" class="easyui-combobox" data-options="url:'role_loadRole.action',
					method:'get',
					valueField:'id',
					textField:'name',
					multiple:true,
					panelHeight:'200px'" style="width:300px;">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="confirmRole()">确认</a>
	</div>
	<div id="getAuth" class="easyui-window" title="查看角色"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 350px; padding: 10px;">
		<span style="font-size:20px">当前拥有的角色：</span><div id="my_auth" style="font-size:20px;"></div>
	</div>
</body>
</html>
