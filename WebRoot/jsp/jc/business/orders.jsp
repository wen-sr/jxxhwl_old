<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>采购单录入</title>
    
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
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jc/predistribution.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/orders.js"></script>	

  </head>
  
  <body>
	<div align="center">
		<h2 style="color:#0078CA">采购单录入&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<input type="hidden" id="currentType">
	<div align="center">
		<select id="type" class="easyui-combobox" style="width:100px;">
			<option value="0">可采购品种</option>
			<option value="1">已采购品种</option>
		</select>
		期号：<input type="text" id="issuenumber" class="easyui-combobox" data-options="url: 'jc/issuenumber_info.action',method: 'get',valueField: 'issuenumber',textField: 'issuenumber',
				panelWidth: 150,panelHeight: '100' "/>
		<input class="easyui-textbox" id="subcode" style="width:150px" ><a class="easyui-linkbutton" id="choosesubcode"  onclick="chooseSubcode()">选择征订代码</a>
		<a class="easyui-linkbutton" id='go' onclick="go()">&nbsp;查询&nbsp;</a>
	</div>
	<!-- 数据显示 -->
	<div align="center" style="padding-top:5px;">
		<table id="data" class="easyui-datagrid" width='100%' data-options="collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
		border:true,pagination:true,pageSize:20,pageList:[10,15,20],toolbar:'#tb'">
		</table>
	</div>
	<!-- 选择征订代码 -->
	<div id="showSubcode" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 800px; height: 350px; padding: 10px;">
		<table id="c_subcode" >
		</table>
	</div>
	<!-- 增加 -->
    <div id="w-addInfo" class="easyui-window" title="添加采购资料"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 700px; height: 400px; padding: 10px;">
		<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>添加采购单</h2>
			<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>期号:</td><td><select class="easyui-combobox" id="add_issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200',readonly:true" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="add_subcode" data-options="readonly:true"  /></td>
				</tr>
				<tr>
					<td>条码：</td><td><input class="easyui-textbox" type="text" id="add_barcode" data-options="readonly:true" /></td>
					<td>书名：</td><td><input class="easyui-textbox" type="text" id="add_descr" data-options="readonly:true" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-combobox" id="add_publisher" data-options="url:'jc/storer_getSupplier.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200',readonly:true" ></select></td>
					<td>定价：</td><td><input class="easyui-textbox" type="text" id="add_price" data-options="readonly:true"  /></td>
				</tr>
				<tr>
					<td>采购数量：</td><td><input class="easyui-textbox" type="text" id="add_orderqty" /></td>
					<td>添加人：</td><td><input class="easyui-textbox"  type="text" id="add_addwho" value="<s:property value='#session.id'/>"  data-options="readonly:true" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" id="submitgo" onclick="addInfo()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 修改 -->
    <div id="w-editInfo" class="easyui-window" title="修改采购信息"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 700px; height: 400px; padding: 10px;">
		<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<input type="hidden" id="edit_id" />
			<h2>修改采购单</h2>
			<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>期号:</td><td><select class="easyui-combobox" id="edit_issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200',readonly:true" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="edit_subcode" data-options="readonly:true"  /></td>
				</tr>
				<tr>
					<td>条码：</td><td><input class="easyui-textbox" type="text" id="edit_barcode" data-options="readonly:true" /></td>
					<td>书名：</td><td><input class="easyui-textbox" type="text" id="edit_descr" data-options="readonly:true" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-combobox" id="edit_publisher" data-options="url:'jc/storer_getSupplier.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200',readonly:true" ></select></td>
					<td>定价：</td><td><input class="easyui-textbox" type="text" id="edit_price" data-options="readonly:true"  /></td>
				</tr>
				<tr>
					<td>采购数量：</td><td><input class="easyui-textbox" type="text" id="edit_orderqty" /></td>
					<td>添加人：</td><td><input class="easyui-textbox"  type="text" id="edit_addwho" value="<s:property value='#session.id'/>"  data-options="readonly:true" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" id="submitgo" onclick="editInfo()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="text-align:center">
    	<a class="easyui-linkbutton" iconCls="icon-add" onClick="tool.add();">添加</a>
    	<a class="easyui-linkbutton" iconCls="icon-edit" onClick="tool.edit();">修改</a>
    	<a class="easyui-linkbutton" iconCls="icon-remove" onClick="tool.remove();">删除</a>
    </div>
    <div id="tb2" style="text-align: center;display: none;" data-options="closed:true">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.yes()">确定</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="tool.no()">取消</a>
	</div>
  </body>
</html>
