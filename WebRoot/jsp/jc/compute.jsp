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
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/jc.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jclogin.js"></script>	
	<script type="text/javascript" src="js/jccompute.js"></script>

  </head>
  
  <body>
     <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<input type="hidden" value="<s:property value='#session.username'/>" id="username"/>
	<input type="hidden" value="<s:property value='#session.password'/>" id="password"/>
	<div align="center">
		<h2 style="color:#0078CA">配发&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<div align="center">
		<select id="type"><option value="0">需配发品种</option><option value="1">已配发品种</option></select>
		期号：<input type="text" id="issuenumber" class="easyui-combobox" data-options="url: 'loadpeifaissuenumber.action',method: 'get',valueField: 'issuenumber',textField: 'issuenumber',
				panelWidth: 150,panelHeight: '100' "/>
		<!--
		征订代码：<input class="easyui-combobox" id="subcode" data-options="
				url: 'loadpeifasubcode.action',
				method: 'get',
				valueField: 'subcode',
				textField: 'subcode',
				panelWidth: 350,
				panelHeight: '250',
				formatter: formatItem">
		-->
		<a class="easyui-linkbutton" id="choosesubcode">选择征订代码</a><input class="easyui-textbox" id="subcode" style="width:150px" >
		<input type="button" value="查询" id="bb"/>
		<input type="button" value="返回" onclick="location.href='jc.action'"/>
	</div>
	<br/>
	<div>
		<table class="easyui-datagrid" width="98%"  id="t1" data-options="collapsible:false,height:'auto',singleSelect:false,fitColumns: true,striped:true,rownumbers:true,
			border:true,pagination:false,pageSize:20,pageList:[10,15,20],toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'id',width:20,checkbox:true">编号</th>
					<th data-options="field:'issuenumber',width:20">期号</th>
					<th data-options="field:'subcode',width:50">征订代码</th>
					<th data-options="field:'descr',width:80">书名</th>
					<th data-options="field:'price',width:30">定价</th>
					<th data-options="field:'pack',width:30">包册数</th>
					<th data-options="field:'publisher',width:50">出版社</th>
					<th data-options="field:'customer',width:10">店名</th>
					<th data-options="field:'customer',width:10">到站</th>
					<th data-options="field:'qtyfree',width:30">可用库存</th>
					<th data-options="field:'qtyallocated',width:30">分配数量</th>
					<th data-options="field:'caseqty',width:30">整包件数</th>
					<th data-options="field:'oddpack',width:30">零捆</th>
					<th data-options="field:'odd',width:30">零册</th>
					<th data-options="field:'addwho',width:30">分发/配发人</th>
					<th data-options="field:'adddate',width:30">分发/配发时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="tb" style="text-align:center">
	    	<a class="easyui-linkbutton" iconCls="icon-edit" id="editpack" >修改包册数</a>
	    	<a class="easyui-linkbutton" iconCls="icon-edit" id="compute" >配发计算</a>
    </div>
    <div id="w" class="easyui-window" title="修改包册数" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:250px;padding:10px;">
			<form id="form1">
				<input type="hidden" id="distributeid" >
				<table cellpadding="5">
					<tr>
						<th align="right">征订代码：</th><th align="left"><span id="spansubcode"></span></th>
					</tr>
					<tr>
						<th align="right">书名：</th><th align="left"><span id="spandescr"></span></th>
					</tr>
					<tr>
						<th align="right">原包册数：</th><th align="left"><span id="spanpack"></span></th>
					</tr>
					<tr>
						<th align="right">选择包册数：</th><th align="left"><select id="newpack" style="width:100" ></select></th>
					</tr>
					<tr>
						<th colspan="2"><input type="button" class="easyui-linkbutton" value="确认" id="commitpack" style="width:100;height:30"/></th>
					</tr>
				</table>
			</form>
    	</div>
    	<div id="w2" class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
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
				<th data-options="field:'totalAllocatedqty',width:100">
					需配发量
				</th>
				<th data-options="field:'totalcomputeqty',width:100">
					已配发量
				</th>
				<th data-options="field:'qtyfree',width:100">
					可用量
				</th>
				</tr>
				</thead>
			</table>
		</div>
		<div id="tb2" style="text-align: center">
			<a class="easyui-linkbutton" iconCls="icon-edit" id="yes">确定</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" id="no">取消</a>
		</div>
  </body>
</html>
