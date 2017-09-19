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
    
    <title>教材退货图书信息维护</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/returnSku.js"></script>
  </head>
  
  <body>
  	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
    <div align="center">
		<h2 style="color:#0078CA">教材退货图书信息维护&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	
    <div id="w-addInfo" class="easyui-window" title="添加图书资料"
			data-options="modal:false,iconCls:'icon-save'"
			style="width: 700px; height: 400px; padding: 0px;">
			<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
				<input type="hidden" id="add_id" />
				<h2>添加图书资料</h2>
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="subcode" /></td>
					<td>书名：</td><td><input class="easyui-textbox" type="text" id="descr" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><input class="easyui-textbox" type="text" id="shortname" /></td>
					<td>定价：</td><td><input class="easyui-textbox" type="text" id="price" /></td>
				</tr>
				<tr>
					<td>捆扎：</td><td><input class="easyui-textbox" type="text" id="pack" /></td>
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
