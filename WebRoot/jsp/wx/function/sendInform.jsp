<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>发送通知</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/wx/sendInform.js"></script>

  </head>
  
  <body>
     <div align="center">
		<h2 style="color:#0078CA">微信发送通知&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<div>
		<form id="f">
			<table>
				<tr>
					<td>通知类型：</td><td>仓储温馨提醒</td>
				</tr>
				<tr>
					<td>通知内容：</td><td><textarea id="content" name="content" cols="40" rows="6" placeholder="" required ></textarea></td>
				</tr>
				<tr><td>通知人员：</td><td><ul name="member" id="member" class="easyui-tree" data-options="animate:true,checkbox:true" ></ul></td></tr>
				<tr><td colspan='2'><a href="javascript:;" class="easyui-linkbutton" style="margin-left:40px;margin-top: 15px;padding:5px;" iconCls="icon-edit" onclick="go()">确认</a></td></tr>						
			</table>
		</form>
	</div>
  </body>
</html>
