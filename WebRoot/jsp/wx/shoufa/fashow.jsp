<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>江西新华物流发货查询结果</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-1.11.1.js"></script>
<script src="js/bootstrap.min.js"></script>
<style type="text/css">
body {
	font: 13px/26px "微软雅黑";
}

h2 {
	align: center;
}
</style>

</head>

<body>
	<div class="container">
		<div class="table-responsive">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>装车日期</th><th>到站</th><th>单号</th><th>实发数量</th><th>车牌号</th><th>司机</th><th>发货方式</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="list" var="fh">
						<tr><td>${fh.shipdate }</td><td>${fh.station }</td><td>${fh.no }</td><td>${fh.qty }</td><td>${fh.carno }</td><td>${fh.siji }</td><td>${fh.fangshi }</td></tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
