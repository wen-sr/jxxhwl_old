<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工号绑定</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/public.css" rel="stylesheet">
	<link href="css/shou.css" rel="stylesheet">
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-migrate-1.4.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".clocebtn").click(function() {
				$("#shouform")[0].reset();
			});
			$("#submitbtn").click(function(){
				$("#g1").removeClass("has-error");
				$("#g2").removeClass("has-error");
				$("#codespan").removeClass("glyphicon-remove");
				$("#codespan2").removeClass("glyphicon-remove");
				var login_id = $("#login_id").val();
				var nickname = $.trim($("#nickname").val());
				if($.trim(login_id) == ""){
					$("#g1").addClass("has-error");
					$("#codespan").addClass("glyphicon-remove");
					return;
				}else if(nickname == ""){
					$("#g1").removeClass("has-error");
					$("#codespan").removeClass("glyphicon-remove");
					$("#g2").addClass("has-error");
					$("#codespan2").addClass("glyphicon-remove");
					return;
				}else{
					$("#shouform").submit();
				}
			});
		});
	</script>
  </head>
  
  <body>
    <header>
		<div class="hd"> <span class="clocebtn">清空</span> 
			<a href="javascript:;" class="logo" onclick='location.reload();'><span class="logo">新华物流</span> </a>
			<h1>工号绑定</h1> 
		</div>
	</header>
	<div class="container">
		<h2 class="title">江西蓝海物流工号绑定</h2>
		<form class="form-horizontal" role="form" action="wx/userInfo_subscribe.action" id="shouform" method="post">
			<div id="g2" class="form-group has-feedback">
				<label class="col-xs-3 control-label">输入您的工号:</label>
				<div class="col-xs-9">
					<input type="text" class="form-control autocompleteInput" id="login_id" name="login_id"
						placeholder="请输入您的工号">
					<span id="codespan" class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div id="g2" class="form-group has-feedback">
				<label for="customer" class="col-xs-3 control-label">请输入您的姓名：</label>
				<div class="col-xs-9">
					<input type="text" class="form-control autocompleteInput" id="nickname" name="nickname"
						placeholder="请输入你的姓名">
					<span id="codespan2" class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div class="form-group btndiv">
				<div class="col-xs-9 col-xs-offset-3">
					<button type="button" id="submitbtn" class="btn btn-primary btn-block">绑定</button>
				</div>
			</div>
		</form>
		<div class="shuoming">
			<p class="h3">
				说明：
			</p>
			<p class="h4">该系统供江西蓝海物流的所有员工使用，请输入正确的工号和姓名，否则无法绑定成功 </p>
		</div>
	</div>
  </body>
</html>
