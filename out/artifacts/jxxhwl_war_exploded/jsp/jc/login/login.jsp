<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<META content="IE=11.0000" http-equiv="X-UA-Compatible">

<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<TITLE>登录页面</TITLE>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jc/login.css">
<SCRIPT src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></SCRIPT>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/login.js"></script>
<SCRIPT type="text/javascript">
	$(function() {
		//得到焦点
		$("#password").focus(function() {
			$("#left_hand").animate({
				left : "150",
				top : " -38"
			}, {
				step : function() {
					if (parseInt($("#left_hand").css("left")) > 140) {
						$("#left_hand").attr("class", "left_hand");
					}
				}
			}, 2000);
			$("#right_hand").animate({
				right : "-64",
				top : "-38px"
			}, {
				step : function() {
					if (parseInt($("#right_hand").css("right")) > -70) {
						$("#right_hand").attr("class", "right_hand");
					}
				}
			}, 2000);
		});
		//失去焦点
		$("#password").blur(function() {
			$("#left_hand").attr("class", "initial_left_hand");
			$("#left_hand").attr("style", "left:100px;top:-12px;");
			$("#right_hand").attr("class", "initial_right_hand");
			$("#right_hand").attr("style", "right:-112px;top:-12px");
		});
	});
</SCRIPT>

<META name="GENERATOR" content="MSHTML 11.00.9600.17496">
</HEAD>
<BODY>
	<DIV class="top_div">
		<img src="${pageContext.request.contextPath}/images/xh.png">
	</DIV>
	<form id="lg" method="post" action="user_login.action">
	<DIV style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
		<DIV style="width: 165px; height: 96px; position: absolute;">
			<DIV class="tou"></DIV>
			<DIV class="initial_left_hand" id="left_hand"></DIV>
			<DIV class="initial_right_hand" id="right_hand"></DIV>
		</DIV>
		<P style="padding: 30px 0px 10px; position: relative;">
			<SPAN class="u_logo"></SPAN> 
			<INPUT name="id" class="ipt" type="text" placeholder="请输入工号" value="">
		</P>
		<P style="position: relative;">
			<SPAN class="p_logo"></SPAN> 
			<INPUT name="password" class="ipt" id="password" type="password" placeholder="请输入密码" value="">
		</P>
		<DIV style="height: 43px; line-height: 24px; margin-top: 0px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
			
				<SPAN>
					<font color="red"><s:actionerror/></font><br>
				</SPAN> 
				<SPAN>
					<A style="background: rgb(0, 142, 173); padding: 8px 50px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;" href="javascript:;" onclick="login()">登录</A> 
				</SPAN>
		</DIV>
	</DIV>
	</form>
</BODY>
</HTML>
