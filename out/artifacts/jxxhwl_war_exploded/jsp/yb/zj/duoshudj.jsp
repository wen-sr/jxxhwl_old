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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/yb/zj/dsdj.js"></script>
	
  </head>
  
  <body>
	<div align="center">
		<h2 style="color:#0078CA">多书登记&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<div align="center">
		<table>
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
					书号：
				</td>
				<td>
					<input type="text" id='sku' class="easyui-textbox" data-options="readonly:true"/>
				</td>
				
			</tr>
			</tr>
				<td>
					定价：
				</td>
				<td>
					<input type="text" id='price' class="easyui-textbox" data-options="readonly:true" />
				</td>
				<td>
					数量：
				</td>
				<td>
					<input type="text" id="qty" class="easyui-textbox" />
				</td>
				<td>
					托盘号：
				</td>
				<td>
					<input type="text" id="tray" class="easyui-textbox" />
				</td>
				<td align="center">
					<a class="easyui-linkbutton" id="go" iconCls="icon-edit" onclick="confirm()">确定</a>
				</td>
			</tr>
		</table>
	</div>
	<div>
		<table id="data" class="easyui-datagrid" data-options="title:'已登记品种'" ></table>
	</div>
	<hr style="width:100%,height:5px;">
	<div id="tb" style="text-align: center">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="deleteDetail()">删除</a>
	</div>
	<div id="tb2" style="text-align: center">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="choose()">确定</a>
		<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()">取消</a>
	</div>
	<div id="showSubcode" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 300px; padding: 10px;">
		<table id="c_barcode" class="easyui-datagrid" data-options="title:'选择品种',toolbar:'#tb2',fit:true,fitColumns: true,singleSelect:true">
			<thead>
				<th data-options="field:'id',width:100,checkbox:true">编号</th>
				<th data-options="field:'storerkey',width:100">货主</th>
				<th data-options="field:'barcode',width:100">条码</th>
				<th data-options="field:'descr',width:150">书名</th>
				<th data-options="field:'price',width:50">定价</th>
				<th data-options="field:'sku',width:50">书号</th>
			</thead>
		</table>
	</div>
  </body>
</html>
