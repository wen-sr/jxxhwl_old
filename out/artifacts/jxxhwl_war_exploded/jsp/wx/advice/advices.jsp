<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>江西新华物流微信公众平台意见征集</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
<!-- 页面不允许缩放 -->
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!--ie8需要引入的文件-->
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="css/bootstrap-combobox.css" rel="stylesheet">
<link href="css/public.css" rel="stylesheet">
<link href="css/shou.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="js/bootstrap-datetimepicker.min.js"></script>
<script src="js/bootstrap.autocomplete.js"></script>
<script type="text/javascript">
	$(function(){
		$(".clocebtn").click(function() {
			$("#shouform")[0].reset();
		});
	});
</script>
</head>
<body>
	<!-- 头部 -->
	<header>
		<div class="hd"> <span class="clocebtn">清空</span> 
			<a href="javascript:;" class="logo" onclick='location.reload();'><span class="logo">新华物流</span> </a>
			<h1>意见反馈</h1> 
		</div>
	</header>
	<div class="container">
		<h2 class="title">意见反馈</h2>
		<form class="form-horizontal" role="form" action="wx/advice.action" id="advice" method="post">
			<div class="form-group">
				<label class="control-label">您的建议：</label>
				<textarea class="form-control" rows="8"></textarea>
			</div>
			<div>
				<button type="submit" class="btn btn-primary btn-block">提交</button>
			</div>
		</form>	
	</div>
</body>
</html>
