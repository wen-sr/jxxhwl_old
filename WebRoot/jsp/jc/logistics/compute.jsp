<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>配发</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/compute.js"></script>

  </head>
  
  <body>
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<input type="hidden" id="currentType"/>
	<div align="center">
		<h2 style="color:#0078CA">配发&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<div align="center">
		<select id="type" class="easyui-combobox" style="width:100px;">
			<option value="0">需配发品种</option>
			<option value="1">已配发品种</option>
		</select>
		期号：<input type="text" id="issuenumber" class="easyui-combobox" data-options="url: 'jc/issuenumber_info.action',method: 'get',valueField: 'issuenumber',textField: 'issuenumber',
				panelWidth: 150,panelHeight: '100' "/>
		<input class="easyui-textbox" id="subcode" style="width:150px" ><a class="easyui-linkbutton" id="choosesubcode"  onclick="chooseSubcode()">选择征订代码</a>
		条码：<input class="easyui-textbox" id="barcode" style="width:150px"  />
		<a class="easyui-linkbutton" id='go' onclick="go()">&nbsp;查询&nbsp;</a>
	</div>
	<br/>
	<div>
		<table class="easyui-datagrid" width="100%"  id="data">
		</table>
	</div>
	<div id="tb3" style="text-align:left;display: none;padding-left:5%;" >
		可用库存：<input class="easyui-textbox" id="t_qtyfree" style="width:80px" data-options="readonly:true" />
		剩余库存：<input class="easyui-textbox" id="t_qty" style="width:80px" data-options="readonly:true" />
		占用库存：<input class="easyui-textbox" id="t_qtyoccupied" style="width:80px" data-options="readonly:true" />
		每包捆数：<input class="easyui-textbox" id="t_bundle" style="width:80px" data-options="readonly:true" />
		捆扎：<input class="easyui-textbox" id="t_pack"style="width:80px" />
	    <a class="easyui-linkbutton" iconCls="icon-edit" id="editpack" onclick="tool.editpack()" >选择捆扎数</a>
	    <a class="easyui-linkbutton" iconCls="icon-edit" id="split" onclick="tool.split()" >分书拆分</a>
	    <a class="easyui-linkbutton" iconCls="icon-edit" id="compute" onclick="tool.compute()">配发计算</a>
    </div>
    <div id="tb2" style="text-align: center;display: none;">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.yes()">确定</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="tool.no()">取消</a>
	</div>
    <div id="tb4" style="text-align: center;display: none;">
		<a class="easyui-linkbutton" iconCls="icon-edit" onclick="tool.yes2()">确定</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="tool.no2()">取消</a>
	</div>
	<div id="tb" style="text-align: center;display: none;">
		<a class="easyui-linkbutton" iconCls="icon-edit" id="abcd" onclick="tool.detail()">分书未发</a>
		<a class="easyui-linkbutton" iconCls="icon-edit" id="cancelcompute" onclick="tool.cancelcompute()">取消计算</a>
	</div>
    <div id="w_choosePack" class="easyui-window" title="选择捆扎" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:300px;padding:10px;">
		<input type="hidden" id="span_issuenumber" >
		<input type="hidden" id="span_subcode" >
		<table id="t_choosepack">
		</table>
    </div>
   	<div id="showSubcode" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 800px; height: 350px; padding: 10px;">
		<table id="c_subcode" >
		</table>
	</div>
	<div id="w-detail" class="easyui-window" title="分书未发" data-options="modal:true,closed:true,iconCls:'icon-save',maximized:true,minimizable: false, maximizable: false" >
		<table id="data-detail"></table>
	</div>
	<div id="w-detail-split" class="easyui-window" title="分书拆分" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:650px;height:300px;padding:10px;" >
		<input type="hidden"  id="split_id"/>
		<h2 align="center">分书拆分</h2>
		<table id="data-detail-split" align="center">
			<tr>
				<td>期号:</td><td><input type="text" class="easyui-combobox" id="split_issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200',readonly:true" /></td>
				<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="split_subcode" data-options="readonly:true" /></td>
			</tr>
			<tr>
				<td>可拆分数量：</td><td><input class="easyui-textbox" type="text" id="split_qtyallocated" data-options="readonly:true"  /></td>
				<td>拆分数量：</td><td><input class="easyui-textbox" type="text" id="split_qtysplit"/></td>
			</tr>
			<tr>
				<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" id="splitInfo" onclick="splitInfo()">提交</a>
				</td>
			</tr>
		</table>
	</div>
	
  </body>
</html>
