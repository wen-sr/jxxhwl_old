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
    
    <title>手工打包</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/yb/bz/pack.js"></script>
	
  </head>
  
  <body>
	<div align="center">
		<h2 style="color:#0078CA">手工打包&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<div align="center">
		<table>
			<tr>
				<td>
					客户代码：
				</td>
				<td>
					<select id="code" class="easyui-combobox"  data-options="url:'yb/pack_findByCode.action',method:'get',valueField:'shortname',textField:'code'" style="width:155px;"></select>
				</td>
				<td>
					客户名称：
				</td>
				<td>
					<input type="text" id="shortname" class="easyui-textbox" data-options="readonly:true"/>
				</td>
				<td><a class="easyui-linkbutton" id='addBatchno' onclick="addBatchno()">&nbsp;生成新批次&nbsp;</a></td>
				<td><a class="easyui-linkbutton" id='endBatchno' onclick="endBatchno()">&nbsp;结束批次&nbsp;</a></td>
			</tr>
			<tr>
				<td>
					条形码:
				</td>
				<td>
					<input type="text" id='barcode' class="easyui-textbox" />
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
					数量：
				</td>
				<td>
					<input type="text" id="qty" class="easyui-textbox" />
					<input type="hidden" id="pack_id" class="easyui-textbox"/>
				</td>
				<td>
					未确认数量：
				</td>
				<td>
					<input type="text" id="qty_wait" class="easyui-textbox"  data-options="readonly:true" />
				</td>
				<td align="center">
					<a class="easyui-linkbutton" id="go" iconCls="icon-edit" onclick="packConfirm()">确定</a>
				</td>
			</tr>
		</table>
	</div>
	<!-- 未刷品种 -->
	<div>
		<table id="data1" class="easyui-datagrid" data-options="title:'未刷品种'" style="height:175px"></table>
	</div>
	<hr style="width:100%,height:5px;">
	<!-- 已刷品种 -->
	<div>
		<table id="data2" class="easyui-datagrid" data-options="title:'已刷品种',toolbar:'#tb'"  style="height:175px"></table>
	</div>
	<div id="tb" style="text-align: center">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="addCaseid()">封包</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="removePack()">取消</a>
		批次号：<input class="easyui-textbox" id="batchno" style="width:100px" />
		<a class="easyui-linkbutton" iconCls="icon-print" onclick="oddCaseList()">清单</a>
		<a class="easyui-linkbutton" iconCls="icon-print" onclick="oddTicketList()">票签</a>
	</div>
<!-- 	<div id="tb1" style="text-align: center"> -->
<!-- 		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="addPack()">确定</a> -->
<!-- 	</div> -->
	<div id="tb2" style="text-align: center">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="choose()">确定</a>
		<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()">取消</a>
	</div>
	<!-- 一号多书时选择打包信息 -->
	<div id="showSubcode" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 450px; height: 300px; padding: 10px;">
		<table id="c_barcode" class="easyui-datagrid" data-options="title:'选择品种',toolbar:'#tb2',fit:true,fitColumns: true,singleSelect:true">
			<thead>
				<th data-options="field:'id',width:100,checkbox:true">编号</th>
				<th data-options="field:'barcode',width:100">条码</th>
				<th data-options="field:'descr',width:150">书名</th>
				<th data-options="field:'price',width:50">定价</th>
				<th data-options="field:'qty',width:50">未确认数量</th>
			</thead>
		</table>
	</div>
  </body>
</html>
