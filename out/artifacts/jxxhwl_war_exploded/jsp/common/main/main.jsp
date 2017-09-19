<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>江西新华物流</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/main.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/outlook2.js"></script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
	<noscript>
		<div
			style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
			<img src="${pageContext.request.contextPath}/images/noscript.gif"
				alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px;
        background: url(${pageContext.request.contextPath}/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
		<span style="float:right; padding-right:20px;" class="head">欢迎：${name }
			<a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a>
		</span> 
		<span style="padding-left:10px; font-size: 22px; ">
		<img src="${pageContext.request.contextPath}/images/blocks.gif" width="20" height="20" align="absmiddle" />
			<span style="padding-top:2px;">江西新华物流</span>
		</span>
	</div>
	<div region="south" split="true"
		style="height: 30px; background: #D2E0F2; ">
		<div class="footer">Copyright&nbsp;©&nbsp;2016-2017&nbsp;&nbsp;江西新华物流信息技术部&nbsp;&nbsp;</div>
	</div>
	<div region="west" hide="true" split="true" title="导航菜单"
		style="width:180px;" id="west">
		<div id="nav" class="easyui-accordion" fit="true" border="false">
			<s:iterator value="map">
				<div title="${value.accordion_name }" data-options="">
					<ul>
						<s:iterator value="value.accordions">
							<li><div><a ref="${accordion_id }" href="#" rel="${accordion_url }" ><span class="icon ${accordion_icon }" >&nbsp;</span><span class="nav">${accordion_name }</span></a></div></li>
						</s:iterator>
					</ul>
				</div>
			</s:iterator>
		</div>

	</div>
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y:hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; ">
			</div>
		</div>
	</div>


	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false"
		minimizable="false" maximizable="false" icon="icon-save"
		style="width: 300px; padding: 5px;height:100px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"	style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" type="Password" class="txt01" />
						</td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="Password" class="txt01" />
						</td>
					</tr>
				</table>
			</div>
			<div region="south" border="false"
				style="text-align: center; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:void(0)"> 确定</a> <a id="btnCancel"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0)">取消</a>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>


</body>
<link href="${pageContext.request.contextPath}/css/jc/main.css"	rel="stylesheet" type="text/css" />
</html>