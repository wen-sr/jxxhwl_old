<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>会员登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    TOKEN:
    accessToken:<s:property value="token.accessToken"/><br>
    openId:<s:property value="token.openId"/><br>
    code:<s:property value="code"/>
	<br>
    UserInfo：
    openid:${userInfo.openId }<br>
    nickname:${userInfo.nickname }<br>
    sex:${userInfo.sex }<br>
    country:${userInfo.country }<br>
    province:${userInfo.province }<br>
    city:${userInfo.city }<br>
    headImgUrl:${userInfo.headImgUrl }<br>
    privilegeList:${privilegeList[0] }<br>
    
    <br>
    wxUserInfo：
    openid:${wxuser.openId }<br>
    nickname:${wxuser.nickname }<br>
    sex:${wxuser.sex }<br>
    country:${wxuser.country }<br>
    province:${wxuser.province }<br>
    city:${wxuser.city }<br>
    headImgUrl:${wxuser.headImgUrl }<br>
    groupid:${wxuser.groupid }<br>
    remark:${wxuser.remark }<br>
    language:${wxuser.language }<br>
  </body>
</html>
