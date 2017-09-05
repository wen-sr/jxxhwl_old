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
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/jc.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jclogin.js"></script>	
	<script type="text/javascript" src="js/pack0.js"></script>
	
  </head>
  
  <body>
     <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">零件打包&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span>&nbsp;<input type="button" value="返回" onclick="location.href='jc.action'"/></h2>
	</div>
	<input type="hidden" value="<s:property value='#session.username'/>" id="username"/>
	<input type="hidden" value="<s:property value='#session.password'/>" id="password"/>
	<div align="center">
			<table>
				<tr>
					<td>
						选择期号：
					</td>
					<td>
						<input type="text" id="issuenumber" class="easyui-combobox"
							data-options="url:'loadfenfaissuenumber.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:200" />
					</td>
					<td>
						选择客户：
					</td>
					<td>
						<input type="text" id="customer" class="easyui-combobox"
							data-options="url:'loadcustomer.action',method:'get',valueField:'customer',textField:'customer',panelHeight:120" />
					</td>
					<td>
						条码：
					</td>
					<td>
						<input type="text" id="barcode" />
					</td>
				</tr>
				<tr>
					<td>
						征订代码:
					</td>
					<td>
						<input type="text" id='subcode' />
					</td>
					<td>
						定价：
					</td>
					<td>
						<input type="text" id='price' />
					</td>
					<td>
						数量：
					</td>
					<td>
						<input type="text" id="qty" />
						<input type="hidden" id="idnumber" />
					</td>

				</tr>
				<tr>
					<td colspan='6' align="center">
						<input type="button" value="确定" id="go" />
						<input type="button" value="查看已刷品种" id="check" />
						<input type="button" value="查看未刷品种" id="check1" />
						<input type="button" value="打印" id="print" onclick="window.open('fhprint.action')"/>
					</td>
				</tr>
			</table>
		</div>
		<br/>
	<div align="center">
		<table class="easyui-datagrid" width="98%"  id="t" data-options="collapsible:false,height:'auto',singleSelect:false,fitColumns: true,striped:true,rownumbers:true,
			border:true,toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'id',width:20,checkbox:true">编号</th>
					<th data-options="field:'issuenumber',width:20">期号</th>
					<th data-options="field:'subcode',width:50">征订代码</th>
					<th data-options="field:'barcode',width:50">条码</th>
					<th data-options="field:'descr',width:80">书名</th>
					<th data-options="field:'price',width:30">定价</th>
					<th data-options="field:'customer',width:30">店名</th>
					<th data-options="field:'qtyallocated',width:30">刷书数量</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="tb" style="text-align: center">
			<a class="easyui-linkbutton" iconCls="icon-edit" id="batchno">生成批次</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" id="remove">取消</a>
	</div>
  </body>
</html>
