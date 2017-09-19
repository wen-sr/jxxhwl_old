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
    
    <title>整件发货</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/wholeship.js"></script>
	
  </head>
  
  <body>
	<div align="center">
		<h2 style="color:#0078CA">整件发货&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<input type="hidden" id="currentType" />
	<input type="hidden" id="span_issuenumber" />
	<input type="hidden" id="span_subcode" />
	<div align="center">
		<form id="f">
			<select id="type" class="easyui-combobox" style="width:100px;">
				<option value="0">需发品种</option>
				<option value="1">已发品种</option>
			</select>
			期号：<input type="text" id="issuenumber" class="easyui-combobox"
				data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:200" />
		<input class="easyui-textbox" id="subcode" style="width:150px" ><a class="easyui-linkbutton" id="choosesubcode"  onclick="chooseSubcode()">选择征订代码</a>
		条码：<input class="easyui-textbox" id="barcode" style="width:150px" />
		批次号：<input class="easyui-textbox" id="batchno" style="width:150px" />
		<a class="easyui-linkbutton" id='go' onclick="go()">&nbsp;查询&nbsp;</a>
		</form>
	</div>
	<div style="height: 93%">
		<table id="data">
		</table>
	</div>
	<div id="showSubcode" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 800px; height: 350px; padding: 10px;">
		<table id="c_subcode" class="easyui-datagrid" data-options="collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
			toolbar:'#tb2'" width='100%'>
		</table>
	</div>
	<div id="w_detail" class="easyui-window" title="发货确认" data-options="modal:true,closed:true,iconCls:'icon-save',maximized:true,minimizable: false, maximizable: false" >
		<table id="data_detail"></table>
	</div>
	<div id="tb" style="text-align: center">
		<a class="easyui-linkbutton" iconCls="icon-edit" id="delivery" onclick="tool.delivery()">发货确认</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" id="remove_delivery" onclick="tool.remove_delivery()">取消批次</a>
	</div>
	<div id="tb2" style="text-align: center">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.yes()">确定</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="tool.no()">取消</a>
	</div>
	<div id="tb3" style="text-align: center;display:none">
		总件数：<input class="easyui-textbox" id="t_caseqty" style="width:80px" data-options="readonly:true" />
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="addBatchno()">生成批次</a>
		<input class="easyui-textbox" id="t_batchno" style="width:100px" />
		<a class="easyui-linkbutton" iconCls="icon-print" onclick="wholeCaseList()">清单</a>
		<a class="easyui-linkbutton" iconCls="icon-print" onclick="allocationList()">调拨单</a>
		<a class="easyui-linkbutton" iconCls="icon-print" onclick="s_ticketList()">票签</a>
		<a class="easyui-linkbutton" iconCls="icon-print" onclick="t_ticketList()">尾包票签</a>
	</div>
  </body>
</html>
