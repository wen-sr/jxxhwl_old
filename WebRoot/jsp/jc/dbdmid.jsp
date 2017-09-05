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
	<script language="javascript" src="js/jquery.jqprint.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<STYLE type="text/css">
		table[name="tt"] th{
			height:52px;
		}
	</STYLE>
  </head>
  	
  <body>
  	<%
  		String batchno= request.getParameter("batchno");
	 %>
	 <form action="jcdbd.action" id="f">
	 	<input type="hidden" id="batchno" name='batchno' value="<%=batchno %>" />
	 </form>
	<div align="center" style="top: 3">
		<table id="t">
		</table>
	</div>
	<div align="center">
		<input type="button" align="middle" value="打印" onclick="oJqprint();"/>
	</div>
</body>
  <SCRIPT type="text/javascript">
		$(function(){
			var batchno = $("#batchno").val();
			$.ajax({
				type:'post',
				url:'jcdbd.action',
				data:'batchno=' + batchno,
				dataType:'json',
				success:function(data){
					var tab = $("#t");
					var html = "<tr>";
					var date = (new Date());
					var month = date.getMonth() + 1;
					var strDate = date.getDate();
					var year = date.getFullYear();
					if (month >= 1 && month <= 9) {
            			month = "0" + month;
        			}
			        if (strDate >= 0 && strDate <= 9) {
			            strDate = "0" + strDate;
			        }
        			var dt = year + "年" + month + "月" + strDate + "日";
					for (var i in data){
							html += "<th><table name='tt' width='620px' > " +
							"<tr><th colspan='3' align='center'>&nbsp;&nbsp;"+ dt +"</th><th colspan='2' align='right'>"+data[i].shipno+"</th></tr>"+
							"<tr><th colspan='4'>"+ data[i].customerCode + data[i].customer +"</th><th  align='right'>"+ data[i].customer +"</th></tr>" + 
							"<tr><th colspan='4'>"+ data[i].descr +"</th><th align='right'>"+ data[i].publisher +"</th></tr>"+
							"<tr><th align='right'>&nbsp;"+ data[i].price +"</th><th>&nbsp;</th><th>&nbsp;</th><th colspan='2'  align='right'>" + data[i].subcode + "</th></tr>" +
							"<tr><th align='right'>&nbsp;"+ data[i].qtyallocated +"</th><th align='right'>"+ data[i].caseqty +"</th><th align='right'>" + data[i].pack*2 + "</th><th colspan='2' align='right'>" + data[i].oddpack + "捆零" + data[i].odd + "本" + "</th></tr>" +
							"<tr><th align='right'>&nbsp;&nbsp;"+ data[i].shipno +"</th><th colspan='2' align='right'>"+ data[i].barcode +"</th><th colspan='2'>&nbsp;</th></tr>" +
							"</table><th>";
							if(i%2 == 1){
								if(i < data.length){
									//html += "</tr><tr><th height='53px'></th></tr><tr>";
									html += "</tr><tr><th height='56px'></th></tr><tr>";
								}else{
									html += "</tr></table>";
								}
							}
					}
					tab.append(html);
				},
				error:function(){
					alert('数据错误，请联系管理员');
				}
			});
		});
	</SCRIPT>
	<script type="text/javascript">
		function doJqprint(){
			$("#t").jqprint();
		}
	 </script>
</html>

