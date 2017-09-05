<%@ page language="java" import="java.util.*, java.text.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>调拨单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<SCRIPT type="text/javascript">
		/*$(function(){
			var batchno = $("#batchno").val();
			$.ajax({
				type:'Post',
				url:'jcdbd.action',
				data:'batchno=' + batchno,
				dataType:'json',
				success:function(data){
					
				},
				error:function(){
					alert("数据错误，联系管理员");
				}
				
			});
		});*/
	</SCRIPT>
  </head>
  	
  <body>
  	<% 
		Date d = new Date(); 
  		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日"); 
  		String dt = df.format(d);  
  		//String batchno= request.getParameter(batchno); 
	 %>
	 
    <s:iterator value="list"  status="st">
    <table>
    	<s:if test="(#st.index+1)%2 == 1">
    	<tr>
    		<td>
    			<table border='0px' cellspacing='0' cellpadding='0'>
		    		<tr><td colspan='3' align="center"><%=dt %></td><td colspan='2' width='50%' align="center">${shipno }</td></tr>
		    		<tr>
		    			<td width='5%'>&nbsp;</td><td colspan='5' width='50%'>${shortname }</td><td width='5%'>&nbsp;</td><td>${shortname }</td>
		    		</tr>
		    		<tr><td><s:property value="(#st.index+1)%2"/>&nbsp;</td><td colspan='5'>${descr }</td><td>&nbsp;</td><td>&nbsp;</td></tr>
		    		<tr><td>&nbsp;</td></tr>
		    		<tr><td>&nbsp;</td><td colspan='5'>${price }</td><td>&nbsp;</td><td>&nbsp;</td></tr>
		    		<tr><td>&nbsp;</td></tr>
		    		<tr><td>&nbsp;</td><td>${qtyallocated }</td><td>&nbsp;</td><td>${caseqty }</td><td>&nbsp;</td><td>${pack*2 }</td><td>&nbsp;</td><td>${oddpack }捆零${odd }本</td></tr>
		    		<tr><td>&nbsp;</td></tr>
		    		<tr><td>&nbsp;</td><td>${shipno }</td><td>&nbsp;</td><td>${subcode }</td><td colspan='4'>&nbsp;</td></tr>
		    	</table>
    		</td>
    	</s:if>
    	
    </table>
    </s:iterator>
  </body>
</html>
