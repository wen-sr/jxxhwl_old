<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>小票</title>
    
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
  </head>
  
  <body>
   <%
  		String batchno= request.getParameter("batchno");
	 %>
	 <form id="f">
	 	<input type="hidden" id="batchno" name='batchno' value="<%=batchno %>" />
	 </form>
	<div align="center" style="top: 3">
		<table id="t">
		</table>
	</div>
	<div align="center">
		<input type="button" align="middle" value="打印" onclick="doJqprint();" />
	</div>
	</body>
  <SCRIPT type="text/javascript">
		$(function(){
			var batchno = $("#batchno").val();
			$.ajax({
				type:'POST',
				url:'jcpq.action',
				data:'batchno=' + batchno,
				dataType:'json',
				success:function(data){
					var tab = $("#t");
					var html = "<tr>";
					var count = 0;
					for (var i in data){
						for ( var j=0;j<data[i].caseqty-1;j++){
							html += "<th><table width='245'><tr><th colspan='2' height='30px'>"+ data[i].subcode +"</th></tr><tr><th colspan='2' height='30px'>"+ data[i].descr +"</th></tr><tr><th colspan='2'>&nbsp;</th></tr><tr><th>&nbsp;</th><th>"+ data[i].customer +"</th></tr><tr><th colspan='2'>&nbsp;</th></tr><tr><th colspan='2'>&nbsp;</th></tr><tr><th>&nbsp;</th><th height='20'>"+ data[i].customer +"</th></tr><tr><th colspan='2'>&nbsp;</th></tr><tr><th colspan='2'>&nbsp;</th></tr><tr><th>"+ data[i].caseqty +"</th><th>"+ data[i].shipno +"</th></tr></table></th>";
							count++;
							if(count%5 == 0){
								if(i <  data.length){
									html += "</tr><tr><th height='56px'></th></tr><tr>";
								}else{
									html += "</tr></table>";
								}
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
