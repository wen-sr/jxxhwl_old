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
    
    <title>包装出货登记</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/yb/bz/bzchdj.js"></script>
  </head>
  
  <body>
  	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
    <div align="center">
		<h2 style="color:#0078CA">包装出货登记&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	
	<div align="center">
		日期：<input class="easyui-datebox" id="q_dd" style="width:150px"  />
		<a class="easyui-linkbutton" id='go' onclick="go()">&nbsp;查询&nbsp;</a>
	</div>
	
	<div align="center" style="padding-top:5px;">
		<table id="data" class="easyui-datagrid" width='100%' height="100%" data-options="collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
		border:true,pagination:true,pageSize:20,pageList:[10,15,20],toolbar:'#tb'">
		</table>
	</div>
	
	<!-- 工具栏 -->
	<div id="tb" style="text-align:center">
    	<a class="easyui-linkbutton" iconCls="icon-add" onClick="add();">添加</a>
    	<a class="easyui-linkbutton" iconCls="icon-edit" onClick="edit();">修改</a>
    </div>
   
    <div id="w_editInfo" class="easyui-window" title="修改"
			data-options="modal:false,closed:true,iconCls:'icon-edit'"
			style="width: 700px; height: 400px; padding: 0px;display:none">
			<form id="editform" align="center" style="text-align:center;margin: 0px;line-height: 41px;">
				<h2>修改</h2>
				<input type="hidden" id="oldId">
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>日期:</td><td><select class="easyui-datebox" id="edit_dd" data-options="panelWidth:'200',panelHeight:'200',required:true" ></td>
					<td>发货件数：</td><td><input class="easyui-textbox" type="text" id="edit_faCase" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>退货件数：</td><td><input class="easyui-textbox" type="text" id="edit_tuiCase" data-options="required:true"/></td>
					<td>主配拒收件数：</td><td><input class="easyui-textbox" type="text" id="edit_zpRej" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>退货拒收件数：</td><td><input class="easyui-textbox" type = 'text' id="edit_tuiRej" data-options="required:true" /></td>
					<td>手工发货件数：</td><td><input class="easyui-textbox" type="text" id="edit_sgFa" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>大中专：</td><td><input class="easyui-textbox" type="text" id="edit_dzz" data-options="required:true"/></td>
					<td>备注：</td><td><input class="easyui-textbox" type="text" id="edit_note"/></td>
				</tr>
				<tr>
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
    <div id="w_addInfo" class="easyui-window" title="添加"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 700px; height: 400px; padding: 0px;">
			<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
				<input type="hidden" id="add_id" />
				<h2>添加出货记录</h2>
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>日期:</td><td><select class="easyui-datebox" id="dd" data-options="panelWidth:'200',panelHeight:'200',required:true" ></td>
					<td>发货件数：</td><td><input class="easyui-textbox" type="text" id="faCase" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>退货件数：</td><td><input class="easyui-textbox" type="text" id="tuiCase" data-options="required:true"/></td>
					<td>主配拒收件数：</td><td><input class="easyui-textbox" type="text" id="zpRej" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>退货拒收件数：</td><td><input class="easyui-textbox" id="tuiRej" data-options="required:true"></td>
					<td>手工发货件数：</td><td><input class="easyui-textbox" type="text" id="sgFa" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>大中专：</td><td><input class="easyui-textbox" type="text" id="dzz" data-options="required:true"/></td>
					<td>合计：</td><td><input class="easyui-textbox" type="text" id="total" data-options="readonly:true"/></td>
				</tr>
				<tr>
					<td>备注：</td><td><input class="easyui-textbox" type="text" id="note"/></td>
					<td>添加人：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="addwho" value="<s:property value='#session.id'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" id="submitgo" onclick="addInfo()">提交</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
  </body>
</html>
