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
    <s:if test="null==#session.username || #session.username.isEmpty()">
  		<meta http-equiv="refresh" content="0; url=jclogin.action"/>
  	</s:if>
    <title>收货确认</title>
    
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
	<script type="text/javascript" src="js/jcreceipt.js"></script>
	<script type="text/javascript" src="js/jcexit.js"></script>
  </head>
  
  <body>
    <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<div align="center">
		<h2 style="color:#0078CA">收货确认&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	
	<input type="hidden" value="<s:property value='#session.username'/>" id="username"/>
	<input type="hidden" value="<s:property value='#session.password'/>" id="password"/>
	
	<div align="center" id="d1">
		<form id="f1" name="f1">
			<input type="hidden" id="oldid"/>
			<table border="0px">
				<tr>
					<td>期号:</td><td><input type="text" id="issuenumber" /></td>
					<td>征订代码：</td><td><input type="text" id="subcode" /></td>
					<td>条码：</td><td><input type="text" id="barcode" /></td>
				</tr>
				<tr>
					<td>书名：</td><td><input type="text" id="descr" /></td>
					<td>出版社：</td><td><input type="text" id="publisher"/></td>
					<td>捆扎：</td><td><input type="text" id="pack" /></td>
				</tr>
				<tr>
					<td>定价：</td><td><input type="text" id="price" /></td>
					<td>收货数量：</td><td><input type="text" id="qtyshou"/></td>
					<td>收货人：</td><td><input type="text" id="addwho" value="<s:property value='#session.username'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
					<input class="easyui-linkbutton" type="button" id="go" value="提交"/>
					<input class="easyui-linkbutton" type="reset" value="重置"/>
					<input class="easyui-linkbutton" type="button" value="返回" onclick="location.href='jc.action'"/>
					<input class="easyui-linkbutton" type="button" value="退出系统" onclick="exit();"/>
					</td>
				</tr>
			</table>
		</form>
 	</div>
 	
 	<DIV ID="show" align="center">
		<input type="hidden" id="currentpage" />
		<table id="t" border='1px'  width='90%'></table>
	</DIV>
	
	<div id="tb" style="text-align:center">
    	<a class="easyui-linkbutton" iconCls="icon-edit" onClick="tool.edit();">修改</a>
    	<a class="easyui-linkbutton" iconCls="icon-remove" onClick="tool.remove();">删除</a>
    </div>
   
  </body>
</html>
