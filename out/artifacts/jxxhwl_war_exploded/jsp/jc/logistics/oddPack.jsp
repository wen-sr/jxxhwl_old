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
    
    <title>零件打包</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/oddpack.js"></script>
	
  </head>
  
  <body>
	<div align="center">
		<h2 style="color:#0078CA">零件打包&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<div align="center">
		<table>
			<tr>
				<td>
					选择期号：
				</td>
				<td>
					<input type="text" id="issuenumber" class="easyui-combobox"
						data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:200" />
				</td>
				<td>
					选择客户：
				</td>
				<td>
					<select id="code" class="easyui-combobox" style="width: 155px"
						data-options="url:'jc/oddPack_loadCustomer.action',method:'get',valueField:'storerkey',textField:'storerkey',panelHeight:120" />
				</td>
				<td><a class="easyui-linkbutton" id='beginPack' onclick="beginPack()">&nbsp;开始打包&nbsp;</a></td>
			</tr>
			<tr>
				<td>
					征订代码:
				</td>
				<td>
					<input type="text" id='subcode' class="easyui-textbox" />
				</td>
				<td>
					书名：
				</td>
				<td>
					<input type="text" id='descr' class="easyui-textbox" data-options="readonly:true"/>
				</td>
				<td>
					定价：
				</td>
				<td>
					<input type="text" id='price' class="easyui-textbox" data-options="readonly:true" />
				</td>
			</tr>
			</tr>
				
				<td>
					出版社：
				</td>
				<td>
					<input class="easyui-combobox" id="publisher" data-options="url:'jc/storer_getSupplier.action',method:'get',valueField:'storerkey',textField:'shortname',readonly:true"/>
				</td>
				<td>
					数量：
				</td>
				<td>
					<input type="text" id="qty" class="easyui-textbox"  data-options="readonly:true"/>
					<input type="hidden" id="pack_id" class="easyui-textbox"/>
				</td>
				<td colspan="2" align="center">
					<a class="easyui-linkbutton" iconCls="icon-edit" onclick="packConfirm()">确定</a>
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<!-- 已刷品种 -->
	<table id="data2" class="easyui-datagrid" data-options="title:'已刷品种',toolbar:'#tb'"></table>
	<hr style="width:100%;height:5px;">
	<!-- 未刷品种 -->
	<table id="data1" class="easyui-datagrid" data-options="title:'未刷品种',toolbar:'#tb1'"></table>

	<div id="tb" style="text-align: center">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="addBatchno()">生成批次</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="removePack()">取消</a>
		批次号：<input class="easyui-textbox" id="batchno" style="width:100px" />
		<a class="easyui-linkbutton" iconCls="icon-print" onclick="oddCaseList()">清单</a>
		<a class="easyui-linkbutton" iconCls="icon-print" onclick="oddTicketList()">拼包票签</a>
	</div>
	<div id="tb1" style="text-align: center">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="addPack()">确定</a>
	</div>
	<!-- 一号多书时选择打包信息 -->
	<div id="showSubcode" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 800px; height: 350px; padding: 10px;">
		<table id="c_subcode" >
		</table>
	</div>
  </body>
</html>
