<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
		<title>库存分发</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jc/predistribution.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/inventoryDistribution.js"></script>
	</head>

	<body>
		<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
		<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
		<input type="hidden" id="currentType"/>
		<div align="center">
			<h2 style="color: #0078CA">
				库存分发&nbsp;
				<span style="font-size: 20px; color: #0078CA"><s:property
						value='#session.name' />
				</span>
			</h2>
		</div>

		<form action='' id="inputform" align="center">
			<select id="type" class="easyui-combobox" style="width:90px;lineheight:20px;">
				<option value="0" selected="selected">等待分发</option>
				<option value="1" >已分发</option>
			</select>
			<input type='hidden' id="oldid">
			期号：
			<input type="text" id="issuenumber" class="easyui-combobox"
				data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:200" />
			<input class="easyui-textbox" id="subcode" style="width:150px" ><a class="easyui-linkbutton" id="choosesubcode" onclick="chooseSubcode()">选择征订代码</a>
			条码：<input class="easyui-textbox" id="barcode" style="width:150px"  />
			<a class="easyui-linkbutton" id='go' onclick="go()">&nbsp;查询&nbsp;</a>
		</form>
		<!-- 数据显示 -->
		<DIV ID="show" align="center">
			<table id="data"></table>
		</DIV>
		<!-- 分发窗口 -->
		<div id="w-addInfo" class="easyui-window" title="进行分发"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 700px; height: 400px; padding: 10px;">
			<form id="form1">
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%" >
				<tr>
					<td>期号:</td><td><select class="easyui-combobox" id="add_issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200',readonly:true" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="add_subcode" /></td>
				</tr>
				<tr>
					<td>条码：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="add_barcode"/></td>
					<td>书名：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="add_descr"  /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-combobox" id="add_publisher" data-options="url:'jc/storer_getSupplier.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200',readonly:true" ></select></td>
					<td>定价：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="add_price"/></td>
				</tr>
				<tr>
					<td>可分发数</td><td><select class="easyui-textbox" id="add_qtyfree" data-options="readonly:true" ></select></td>
					<td>分发数量：</td><td><input class="easyui-textbox" type="text" id="add_qtyallocated"/></td>
				</tr>
				<tr>
					<td>分发店：</td><td><select class="easyui-combobox" id="add_code" data-options="url:'jc/storer_getCustomer.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200'" ></select></td>
					<td>分发人：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="add_addwho" value="<s:property value='#session.id'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" id="submitgo" onclick="addInfo()">提交</a>
					</td>
				</tr>
			</table>
			</form>
		</div>

		<!-- 修改窗口 -->
		<div id="w-editInfo" class="easyui-window" title="修改分发信息"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 700px; height: 400px; padding: 10px;">
			<form id="form1">
				<input type="hidden" id="edit_id" />
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%" >
				<tr>
					<td>期号:</td><td><select class="easyui-combobox" id="edit_issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200',readonly:true" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="edit_subcode" /></td>
				</tr>
				<tr>
					<td>条码：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="edit_barcode"/></td>
					<td>书名：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="edit_descr" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-combobox" id="edit_publisher" data-options="url:'jc/storer_getSupplier.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200',readonly:true" ></select></td>
					<td>定价：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="edit_price" /></td>
				</tr>
				<tr>
					<td>分发店：</td><td><select class="easyui-combobox" id="edit_code" data-options="url:'jc/storer_getCustomer.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200'" ></select></td>
					<td>分发数量：</td><td><input class="easyui-textbox" type="text" id="edit_qtyallocated"/></td>
				</tr>
				<tr>
					<td>分发人：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="edit_addwho" value="<s:property value='#session.id'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" id="submitgo" onclick="editInfo()">提交</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<div id="tb" style="text-align: center">
			<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.add()">进行分发</a>
			<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.edit()">修改</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" onclick="tool.remove()">删除</a>
		</div>
		<div id="tb2" style="text-align: center">
			<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.yes()">确定</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" onclick="tool.no()">取消</a>
		</div>
		<div id="dlg" class="easyui-dialog" title="提示"
			data-options="iconCls:'icon-save',closed:true,modal: true"
			style="width: 400px; height: 200px; padding: 10px">
			正在执行,请稍候...
		</div>
		<div id="w2"class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
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
				<th data-options="field:'qtyfree',width:100">
					可用量
				</th>
				</tr>
				</thead>
			</table>
		</div>
		<!-- 选择征订代码界面 -->
		<div id="showSubcode" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 800px; height: 350px; padding: 10px;">
			<table id="c_subcode">
			</table>
		</div>
	</body>
</html>
