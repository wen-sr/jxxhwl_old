<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>教材生产计划</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/productionPlan.js"></script>
  </head>
  
  <body>
  	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
    <div align="center" style="height:7%">
		<h2 style="color:#0078CA">教材生产计划&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
		发货批次：<input type="text" id="batchno" class="easyui-textbox" />
		征订代码：<input class="easyui-textbox" id="subcode" style="width:150px"  />
		发货起始日期：<input  id="deliverDate" class= "easyui-datebox"/>
		截止日期：<input  id="deliverDateEnd" class= "easyui-datebox"/>
		<input class="easyui-switchbutton" data-options="onText:'显示全部',offText:'分页显示',width:'100px'" id="switch">
		<a class="easyui-linkbutton" onclick="bb()">查询</a>
	</div>
	<div align="center" style="padding-top:5px;height:91%" >
		<table id="data" class="easyui-datagrid" width='100%' height="100%" data-options="fit:true,collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
		border:true,pagination:true,pageSize:20,pageList:[10,15,20,1000],toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'id',width:50,checkbox:true">编号</th>
					<th data-options="field:'plantingName',width:100">印刷厂名称</th>
					<th data-options="field:'subcode',width:50">征订代码</th>
					<th data-options="field:'price',width:30">定价</th>
					<th data-options="field:'descr',width:120">书名</th>
					<th data-options="field:'publisher',width:80">出版社</th>
					<th data-options="field:'qtyPerItem',width:30">每条册数</th>
					<th data-options="field:'qtyDeliver',width:30">发运册数</th>
					<th data-options="field:'qtyCase',width:30">件数</th>
					<th data-options="field:'batchno',width:80">发货批次号</th>
					<th data-options="field:'deliverDate',width:50">发运日期</th>
					<th data-options="field:'handBagDate',width:50">交包日期</th>
					<th data-options="field:'type',width:30">类型</th>
					<th data-options="field:'status',width:30">状态</th>
					<th data-options="field:'remark',width:30">备注</th>
					<th data-options="field:'addwho',width:50">添加人</th>
					<th data-options="field:'adddate',width:50">添加时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="text-align:center">
    	<a class="easyui-linkbutton" iconCls="icon-search" onClick="tool.query();">查询</a>
    	<a class="easyui-linkbutton" iconCls="icon-edit" onClick="tool.edit();">修改</a>
    </div>
   
	 <!-- 修改书号信息窗口 -->
    <div id="w-editInfo" class="easyui-window" title="修改资料"
			data-options="modal:false,closed:true,iconCls:'icon-edit'"
			style="width: 700px; height: 400px; padding: 0px;display:none">
			<form id="editform" align="center" style="text-align:center;margin: 0px;line-height: 41px;">
				<h2>修改资料</h2>
				<input type="hidden" id="oldId">
				<input type="hidden" id="edit_status">
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>印刷厂名称:</td><td><select class="easyui-textbox" id="edit_plantingName" data-options="readonly:true" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="edit_subcode"  data-options="readonly:true"/></td>
				</tr>
				<tr>
					<td>定价：</td><td><input class="easyui-textbox" type="text" id="edit_price"  data-options="readonly:true" /></td>
					<td>书名：</td><td><input class="easyui-textbox" type="text" id="edit_descr"  data-options="readonly:true" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-textbox" id="edit_publisher" data-options="readonly:true" ></select></td>
					<td>发货批次：</td><td><input class="easyui-textbox" type="text" id="edit_batchno" data-options="readonly:true" /></td>
				</tr>
				<tr>
					<td>交包日期：</td><td><input class="easyui-datebox" type="text" id="edit_handBagDate" /></td>
					<td>类型：</td><td><select class="easyui-combobox" id="edit_type">
						<option>请选择</option>   
						<option>半成品</option>   
					    <option>成品</option>   
					    <option>成品（市店半）</option>   
					    <option>成品（市店捆）</option>   
					    <option>教参</option> 
					    <option>捆扎</option> 
					    <option>捆扎（库存）</option> 
					</select></td>
				</tr>
				<tr>
					<td>备注：</td><td><input class="easyui-textbox" type="text" id="edit_remark" /></td>
					<td>修改人：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="edit_addwho" value="<s:property value='#session.id'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" onclick="editInfo()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
		
	<!-- 查询书号信息窗口 -->
    <div id="w-queryInfo" class="easyui-window" title="查询资料"
			data-options="modal:false,closed:true,iconCls:'icon-search'"
			style="width: 700px; height: 500px; padding:0px;display: none;">
		<form id="queryform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>查询资料</h2>
			<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>印刷厂名称:</td><td><select class="easyui-textbox" id="query_plantingName" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="query_subcode" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-textbox" id="query_publisher" ></select></td>
					<td>发货批次：</td><td><input class="easyui-textbox" type="text" id="query_batchno"/></td>
				</tr>
				<tr>
					<td>发运日期开始：</td><td><select class="easyui-datebox" id="query_deliverDate"  ></select></td>
					<td>截止日期：</td><td><input class="easyui-datebox" type="text" id="query_deliverDateEnd" /></td>
				</tr>
				<tr>
					<td>交包日期开始：</td><td><select class="easyui-datebox" id="query_handBagDate"  ></select></td>
					<td>截止日期：</td><td><input class="easyui-datebox" type="text" id="query_handBagDateEnd" /></td>
				</tr>
				<tr>
					<td>状态：</td><td><select class="easyui-combobox" id="query_status">
						<option>请选择</option>   
					    <option value='1'>已交包</option> 
					    <option value="0">未交包</option> 
					</select></td>
					<td>类型：</td><td><select class="easyui-combobox" id="query_type">
						<option>请选择</option>   
						<option>半成品</option>   
					    <option>成品</option>   
					    <option>成品（市店半）</option>   
					    <option>成品（市店捆）</option>   
					    <option>教参</option> 
					    <option>捆扎</option> 
					    <option>捆扎（库存）</option> 
					</select></td>
				</tr>
				<tr>
					<td>备注：</td><td><input class="easyui-textbox" type="text" id="query_remark" /></td>
					<td>添加人：</td><td><input class="easyui-combobox" data-options="url:'jc/pp_getAddwho.action',method:'get',valueField:'id',textField:'name',panelHeight:'200'" id="query_addwho" /></td>
				</tr>
				<tr>
					<td colspan='4' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" id="submitgo" onclick="queryInfo()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	
  </body>
</html>
