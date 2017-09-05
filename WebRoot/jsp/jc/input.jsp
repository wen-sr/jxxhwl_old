<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
		<s:if test="null==#session.username || #session.username.isEmpty()">
			<meta http-equiv="refresh" content="0; url=jclogin.action" />
		</s:if>
		<title>库存分发</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css"
			href="easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="css/jc.css">
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="js/jclogin.js"></script>
		<script type="text/javascript" src="js/jcinput.js"></script>
	</head>

	<body>
		<DIV align="center" style="vertical-align: middle;">
			<img alt="新华物流教材部" src="images/jc/xhwl-jc2.gif" style="height: 47px;"
				onclick='location.reload();'>
		</div>
		<div align="center">
			<h2 style="color: #0078CA">
				库存分发&nbsp;
				<span style="font-size: 20px; color: #0078CA"><s:property
						value='#session.name' />
				</span>
			</h2>
		</div>
		<input type="hidden" value="<s:property value='#session.username'/>"
			id="username" />
		<input type="hidden" value="<s:property value='#session.password'/>"
			id="password" />

		<form action='' id="inputform" align="center">
			<select id="type" >
				<option value="0">
					等待分发
				</option>
				<option value="1">
					已分发
				</option>
			</select>
			<input type='hidden' id="oldid">
			期号：
			<input type="text" id="issuenumber1" class="easyui-combobox"
				data-options="url:'loadfenfaissuenumber.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:200" />
			<a class="easyui-linkbutton" id="choosesubcode">选择征订代码</a><input class="easyui-textbox" id="subcode" style="width:150px"  >
			
			
			<!--
			征订代码：
			<input type='text' id='subcode' class="easyui-combobox"
				data-options="url:'loadfenfasubcode.action',method:'get',valueField:'subcode',textField:'subcode',panelHeight:200" />
			-->
			<input type='button' id='go' value='查询' />
			<input type="button" value="返回" onclick="location.href='jc.action'" />
			<input type="hidden" id="oldid" />
		</form>
		<DIV ID="show" align="center">
			<input type="hidden" id="currentpage" />
			<table id="t2" class="easyui-datagrid" width='90%'
				data-options="collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
			border:true,pagination:false,pageSize:20,pageList:[10,15,20],toolbar:'#tb'">
				<thead>
					<tr>
						<th data-options="field:'id',width:80,checkbox:true">
							编号
						</th>
						<th data-options="field:'issuenumber',width:50">
							期号
						</th>
						<th data-options="field:'subcode',width:80">
							征订代码
						</th>
						<th data-options="field:'publisher',width:50">
							出版社
						</th>
						<th data-options="field:'descr',width:100">
							书名
						</th>
						<th data-options="field:'price',width:50">
							定价
						</th>
						<th data-options="field:'pack',width:50">
							包册数
						</th>
						<th data-options="field:'qtyshou',width:80">
							收货数量
						</th>
						<th data-options="field:'qtyallocated',width:80">
							已分发数量
						</th>
						<th data-options="field:'qtyfree',width:80">
							可用库存
						</th>
						<th data-options="field:'customer',width:50">
							客户
						</th>
						<th data-options="field:'addwho',width:50">
							收货/分发人
						</th>
						<th data-options="field:'adddate',width:50">
							收货/分发时间
						</th>
					</tr>
				</thead>
			</table>
		</DIV>

		<div id="w" class="easyui-window" title="进行分发"
			data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 250px; padding: 10px;">
			<form id="form1">
				<input type="hidden" id="chosedId" />
				<input type="hidden" id="spanpack" />
				<input type="hidden" id="spanissuenumber" />
				<table cellpadding="5">
					<tr>
						<th>
							征订代码：
						</th>
						<th>
							<span id="spansubcode"></span>
						</th>
					</tr>
					<tr>
						<th>
							书名：
						</th>
						<th><span id="spandescr"></span>
						</th>
					</tr>
					<!--<tr>
						<th>可分数量：</th><th><span id="spanqtyfree"></span></th>
					</tr>
					-->
					<tr>
						<th>
							选择店名：
						</th>
						<th>
							<select class="easyui-combobox" style="width: 200" id="fenfadian"
								data-options="url:'loadjiaocaistorer.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200' ">
							</select>
						</th>
					</tr>
					<tr>
						<th>
							分发数量：
						</th>
						<th>
							<input type="text" id="fenfaqty" style="width: 200" />
						</th>
					</tr>
					<tr>
						<th colspan="2">
							<input type="button" class="easyui-linkbutton" value="确认分发"
								id="fenfacommit" />
						</th>
					</tr>
				</table>
			</form>
		</div>

		<div id="tb" style="text-align: center">
			<a class="easyui-linkbutton" iconCls="icon-edit" id="fenfa">进行分发</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" id="remove">删除</a>
		</div>
		<div id="dlg" class="easyui-dialog" title="提示"
			data-options="iconCls:'icon-save',modal: true"
			style="width: 400px; height: 200px; padding: 10px">
			正在执行,请稍候...
		</div>
		<div id="w2"class="easyui-window" title="选择征订代码" data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 800px; height: 350px; padding: 10px;">
			<table id="t_subcode" class="easyui-datagrid" data-options="collapsible:false,height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
			toolbar:'#tb2'" width='90%'>
				<thead>
				<tr>
				<th data-options="field:'issuenumber',width:80">
					期号
				</th>
				<th data-options="field:'subcode',width:80">
					征订代码
				</th>
				<th data-options="field:'descr',width:200">
					书名
				</th>
				<th data-options="field:'qtyfree',width:100">
					可用量
				</th>
				</tr>
				</thead>
			</table>
		</div>
		<div id="tb2" style="text-align: center">
			<a class="easyui-linkbutton" iconCls="icon-edit" id="yes">确定</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" id="no">取消</a>
		</div>
	</body>
</html>
