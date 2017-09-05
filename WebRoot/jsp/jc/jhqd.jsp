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
    
    <title>手拣单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<STYLE type="text/css">
		td{
			text-align: center;
		}
		#tt td,th{
			height:30px;
		}
	</STYLE>
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<script language="javascript" src="js/jquery.jqprint.js"></script>
	<script type="text/javascript" src="js/jcdelivery0.js"></script>
	<script type="text/javascript">
		function doJqprint(){
			$("#d").jqprint();
		}
	 </script>
  </head>
  
  <body>
  	<% 
  		String pickno= request.getParameter("pickno"); 
	 %>
  	<input type="hidden" id="pick" name='pick' value="<%=pickno %>" />
  	<div id="d">
  	<h2 align="center">江西新华物流有限公司</h2>
  	<h3 align="center">拣货清单</h3>
  	</div>
  	<br/>
  	<div align="center">
  		<input type="button" align="middle" value="打印" onclick="doJqprint();"/>
  	</div>
  </body>
  <script type="text/javascript">
  	$(function(){
  		var pickno =  $("#pick").val();
  		$.ajax({
  			type:'Post',
  			url:'jcbdjhqd.action',
  			data:'pickno=' + pickno,
  			dataType:'json',
  			success:function(data){
  				if(data){
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
        			var dt = year + "-" + month + "-" + strDate ;
  					var div = $("#d");
  					var html="<table frame=hsides width='90%' rules='rows' align='center' height=80px><tr><td>征订代码</td><td>商品编号</td><td>版别</td><td>定价</td><td>书名</td><td>列印日期</td></tr>" + 
  					
  					"<tr><td>"+ data['dt'].subcode +"</td><td>"+ data['dt'].barcode +"</td><td>"+ data['dt'].publisher +"</td><td>"+ data['dt'].price +"</td><td>"+ data['dt'].descr +"</td><td>"+ dt +"</td></tr>"+
  					"</table>";
  					html += "<br/><br/><br/><br/>"
  					var sumqty = 0; 
  					html += "<table id='tt' frame=hsides width='90%' rules='rows' align='center' padding-top=50px><tr><td>拣货流水号</td><td>客户代码</td><td>客户名称</td><td>配发数量</td><td>捆扎</td></tr>";
  					for(var i in data['mx']){
  						html += "<tr><td>"+ data['mx'][i].pickno +"</td><td>"+ data['mx'][i].customerCode +"</td><td>"+ data['mx'][i].customer +"</td><td>"+ data['mx'][i].qtyallocated +"</td><td>"+ data['mx'][i].pack +"</td></tr>"
  						sumqty += data['mx'][i].qtyallocated;
  					}
  					html += "<tr><th>总数量："+ sumqty +"</th><th colspan='4'></th></tr></table>"
  					html += "<br/><br/><br/><br/><div style='height:50px,padding-top:25px;' align='center'>拣货人签字：______________&nbsp;&nbsp;&nbsp;&nbsp;完成时间：_______________</div>"
					div.append(html);
  				}
  			},
  			error:function(){
  				alert("数据错误，请联系管理员");
  			}
  		}); 
  	});	
  </script>
</html>
