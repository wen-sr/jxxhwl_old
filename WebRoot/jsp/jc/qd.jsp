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
    
    <title>整件清单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<style type="text/css">
		td{
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
			url:'jcqd.action',
			data:'batchno=' + batchno,
			dataType:'json',
			success:function(data){
				var tab = document.getElementById("t1");
				var row = tab.rows[1];
				row.cells[0].innerHTML = data["dt"].subcode;
				row.cells[1].innerHTML = data["dt"].descr;
				row.cells[2].innerHTML = data["dt"].publisher;
				row.cells[3].innerHTML = data["dt"].price;
				row.cells[4].innerHTML = data["dt"].qtyallocated;
				row.cells[5].innerHTML = data["dt"].pack;
				row.cells[6].innerHTML = "共" + data["dt"].caseqty + "捆零 " + data["dt"].odd + "本";
				
				var tab2 = document.getElementById("t2");
				var html = "";
				var sumqty = 0;
				var sumcase = 0;
				for (var i in data["mx"]){
					html += "<tr><td>" + data["mx"][i].customerCode + "</td><td>"+ data["mx"][i].customer +"</td><td>"+ data["mx"][i].shipno +"</td><td>"+ data["mx"][i].qtyallocated +"</td><td>"+ data["mx"][i].caseqty +"</td></tr>";
					sumqty += data["mx"][i].qtyallocated;
					sumcase += data["mx"][i].caseqty;
				}
				html += "<tr><td>合计:</td><td></td><td></td><td>"+ sumqty +"</td><td>"+ sumcase +"</td></tr>"
				$("#t2").append(html);
			},
			error:function(){
				alert("数据错误，联系管理员");
			}
		});
	 	});
	 </script>
	 <script type="text/javascript">
	 	function doPrint(){
			bdhtml = window.document.body.innerHTML;//获取当前页面的html代码
			sprnstr="<!--startprint-->";//设置打印开始区域
			eprnstr="<!--endprint-->";//设置打印结束区域
			prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr+17));//从开始代码向后取html
			prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));// 从结束代码向前取html
			window.document.body.innerHTML=prnhtml;
			window.print();
			window.document.body.innerHTML=bdhtml;
		}
	 </script>
  </head>
  
  <body>
  	<input type="hidden" id="batchno" value="<%=batchno %>" />
  	<!--startprint-->
    <div align="center">
    	<div align="center">
    		<h1>江西新华物流有限公司</h1>
    		<h1>整件储运发货清单</h1>
    	</div>
    	<div align="center" >
    		<table width='90%' style="border:0px;"  >
    			<tr>
    				<td colspan="5">调度批号：<%=batchno %></td><td>列印时间：<%= dt %></td>
    			</tr>
    		</table>
    		<table id="t1" width="90%" frame=hsides rules='rows'>
    			<tr>
    				<td width='10%'>征订号</td><td  width='20%'>书名</td><td  width='10%'>出版社</td><td  width='10%'>价格</td><td  width='10%'>数量</td><td  width='10%'>捆扎数</td><td width='20%'>应发数</td>
    			</tr>
    			<tr>
    				<td>${batchno }</td><td>${ descr }</td><td>${ publisher }</td><td>${ price }</td><td>${ qtyfa }</td><td>${ qtyper }</td><td>共${ qtyfaa }捆零${ qtya }本</td>
    			</tr>
    		</table>
    	</div>
    	<div align="center" style="padding:50px 0px" >
    		<table id="t2" width="90%" frame=hsides rules='rows'>
    			<tr><td  width='20%'>客户代码</td><td  width='20%'>客户名称</td><td  width='20%'>运号</td><td  width='10%'>数量</td><td  width='10%'>包件</td></tr>
    		</table>
    	</div>
    <!--endprint-->
    	<input type="button" value="打印" onclick="doPrint();"/>
    </div>
  </body>
</html>
