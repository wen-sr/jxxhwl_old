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
    <title>教材手工发货</title>
    
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
	<script type="text/javascript" src="js/jc.js"></script>
	
  </head>
  
  <body>
  	
    <DIV align="center" style="vertical-align: middle;">
		<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;" onclick='location.reload();'>
	</div>
	<input type="hidden" value="<s:property value='#session.username'/>" id="username"/>
	<input type="hidden" value="<s:property value='#session.password'/>" id="password"/>
	<h2 align="center"><span style="font-size:20px;color:#0078CA">当前操作人：<s:property value='#session.name'/></span></h2>
			<table id='t' width="50%" align="center">
				<tr>
					<th align="center" style="width:80%">
						<a id="yufenfa" href="yufenfa.action" ><span class="item">业务预分发</span>
						</a>
					</th>
				</tr>
				<tr>
					<th align="center">
						<a id="receipt" href="javascript:void();"
						><span class="item">收货确认</span>
						</a>
					</th>
				</tr>
				<tr>
					<th align="center">
						<a href="input.action"  ><span class="item">库存分发</span></a>
					</th>
				</tr>
				<tr>
					<th align="center">
						<a href="compute.action" ><span class="item">配发</span>
						</a>
					</th>
				</tr>
				<tr>
					<th align="center">
						<a href="delivery.action"  ><span class="item">整件发货</span>
						</a>
					</th>
				</tr>
				<tr>
					<th align="center">
						<a href="delivery0.action"  ><span class="item">零件拣货</span>
						</a>
					</th>
				</tr>
				<tr>
					<th align="center">
						<a href="pack0.action"  ><span class="item">零件打包</span>
						</a>
					</th>
				</tr>
				<tr>
					<th align="center">
						<a href="fhprint.action"  ><span class="item">打印</span>
						</a>
					</th>
				</tr>
				
				<tr>
					<th align="center">
						<a href="jiaocaisku.action"  ><span class="item">资料维护</span>
						</a>
					</th>
				</tr>
			</table>
  </body>
</html>
