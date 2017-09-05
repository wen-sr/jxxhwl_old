<%@ page language="java" import="java.util.*, java.text.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>零件清单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script language="javascript" src="js/jquery.jqprint.js"></script>
	<style type="text/css">
		#t1 td{
			height:35px;
		}
	</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%
		Date d = new Date();
  		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  		String dt = df.format(d); 
  		String batchno= request.getParameter("batchno");
	 %>
	 <script type="text/javascript">
	 	$(function(){
	 		var batchno = $("#batchno").val();
	 		$.ajax({
			type:'POST',
			url:'jcqd0.action',
			data:'batchno=' + batchno,
			dataType:'json',
			success:function(data){
				var tab = $("#t1");
				var html = "";
				var sumqty = 0;
				for (var i in data){
					html += "<tr><td>"+ data[i].subcode +"</td><td>"+ data[i].descr +"</td><td>"+ data[i].publisher +"</td><td>"+ data[i].price +"</td><td>"+ data[i].qtyallocated +"</td><td>"+ data[i].pack +"</td></tr>";
					sumqty += data[i].qtyallocated;
				}
				html += "<tr><td>合计</td><td>"+ data.length +"</td><td></td><td></td><td>"+ sumqty +"</td><td></td></tr>"
				tab.append(html);
			},
			error:function(){
				alert("数据错误，联系管理员");
			}
		});
	 	});
	 </script>
	 <script type="text/javascript">
		function doJqprint(){
			$("#d").jqprint();
		}
	 </script>
  </head>
  
  <body>
  	<input type="hidden" id="batchno" value="<%=batchno %>" />
    <div align="center" id='d'>
    	<div align="center">
    		<h1>江西新华物流有限公司</h1>
    		<h1>零件储运发货清单</h1>
    	</div>
    	<div align="center" >
    		<table width='90%' style="border:0px;" >
    			<tr>
    				<td colspan="5">调度批号：<%=batchno %></td><td>列印时间：<%= dt %></td>
    			</tr>
    		</table>
    		<table id="t1" width="90%" frame=hsides rules='rows'>
    			<tr>
    				<td width='10%'>征订号</td><td  width='20%'>书名</td><td  width='10%'>出版社</td><td  width='10%'>价格</td><td  width='10%'>数量</td><td  width='10%'>捆扎信息</td>
    			</tr>
    		</table>
    	</div>
    </div>
    	
    <div align="center" style="margin-top:50px;">
		<input type="button" align="middle" value="打印" onclick="doJqprint();"/>
	</div>
  </body>
</html>
