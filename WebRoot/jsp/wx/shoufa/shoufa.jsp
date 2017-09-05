<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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

<title>收发货查询</title>

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
		$("#customer").on('input',function(){
			var value = $.trim($("#customer").val());
			if("" != value && value.length > 0 ){
				//storer();
			}
		});
		
		$(".form_date").datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0

		});
		
		$(".clocebtn").click(function() {
			$("#shouform")[0].reset();
		});
		
		$("#submitbtn").click(function(){
			$("#g1").removeClass("has-error");
			$("#g2").removeClass("has-error");
			$("#g3").removeClass("has-error");
			$("#codespan").removeClass("glyphicon-remove");
			$("#beginspan").removeClass("glyphicon-remove");
			$("#endspan").removeClass("glyphicon-remove");
			var code = $("#customer").val();
			var begin = $.trim($("#begin").val());
			var end = $.trim($("#end").val());
			if($.trim(code) == ""){
				$("#g1").addClass("has-error");
				$("#codespan").addClass("glyphicon-remove");
				return;
			}else if(begin == ""){
				$("#g2").addClass("has-error");
				$("#beginspan").addClass("glyphicon-remove");
				return;
			}else if(end == ""){
				$("#g3").addClass("has-error");
				$("#endspan").addClass("glyphicon-remove");
				return;
			}else if((end-begin) < 0){
				alert("温馨提示：结束日期必须大于开始日期！");
			}
			else{
				$("#shouform").submit();
			}
		});
	});
	
	function storer() {
		$('.autocompleteInput').autocomplete({
			scroll: true,
			source : function(query, process) {
				var matchCount = 20;//this.options.items;//返回结果集最大数量
				$.post("wx/get_storer", {
					"matchInfo" : query,
					"minChars" : 1,
					"matchCount" : matchCount
					
				}, function(respData) {
					return process(respData);
				});
			},
			formatItem : function(item) {
				return item["storerkey"] + "(" + item["description"] + ")";
			},
			setValue : function(item) {
				return {
					'data-value' : item["description"],
					'real-value' : item["storerkey"]
				};
			},
			updater: function (dataVal, realVal) {
            	$(".autocompleteInput").val(realVal);
                return dataVal;
            }
		});
	}
</script>
</head>

<body>
	<!-- 头部 -->
	<header>
		<div class="hd"> <span class="clocebtn">清空</span> 
			<a href="javascript:;" class="logo" onclick='location.reload();'><span class="logo">新华物流</span> </a>
			<h1>收发货查询</h1> 
		</div>
	</header>
	<div class="container">
		<h2 class="title">江西新华物流收发货查询</h2>
		<form class="form-horizontal" role="form" action="wx/get_shou.action" id="shouform" method="post">
			<div class="form-group">
				<label class="col-xs-3 control-label">查询类型:</label>
				<div class="col-xs-9">
					<select id="type" name="type" class="form-control">
					  <option value="0">物流收货</option>
					  <option value="1">物流发货</option>
					</select>
				</div>
			</div>
			<div id="g1" class="form-group has-feedback">
				<label for="customer" class="col-xs-3 control-label">代码：</label>
				<div class="col-xs-9">
					<input type="text" class="form-control autocompleteInput" id="customer" name="code"
						placeholder="请输入客户代码或名称">
					<span id="codespan" class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div id="g2" class="form-group">
				<label for="begin" class="col-xs-3 control-label" >时间开始：</label>
				<div class="col-xs-9">
					<input type="text" class="form-control form_date" readonly  data-date-format="yyyymmdd" id="begin" name="begin"
						placeholder="请选择开始时间">
					<span id="beginspan" class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div id="g3" class="form-group">
				<label for="end" class="col-xs-3 control-label">时间结束：</label>
				<div class="col-xs-9">
					<input type="text" class="form-control form_date" readonly data-date-format="yyyymmdd" id="end" name="end"
						placeholder="请选择结束时间">
					<span id="endspan" class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div class="form-group btndiv">
				<div class="col-xs-9 col-xs-offset-3">
					<button type="button" id="submitbtn" class="btn btn-primary btn-block">查询</button>
				</div>
			</div>
		</form>
		<div class="shuoming">
			<p class="h3">
				说明：
			</p>
			<p class="h4">1:该系统供江西新华物流的所有客户使用，包括所有供应商和门市店 </p>
			<p class="h4">2:该系统可根据所有客户的ERP代码或ERP简称查询到江西新华物流的收发货情况，包括供用商到货，门市店退货，门市店发货，供用商退货等</p>
			<p class="h4">3:操作举例：如查询文化广场的发货情况，查询类型选择<strong>"发货"</strong>，输入<strong>"文化广场"</strong>或者代码<strong>"360016-6"</strong>，选择需要查询的时间段，点击查询即可。如：查询人民出版社的到货情况，查询类型选择<strong>"收货"</strong>，输入<strong>"人民出版社"</strong>或代码<strong>"7-01"</strong>，选择需要查询的时间段，点击查询即可。</p>
		</div>
	</div>
</body>
</html>
