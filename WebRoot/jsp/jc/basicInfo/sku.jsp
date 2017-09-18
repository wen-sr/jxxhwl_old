<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>图书信息维护</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jc/sku.js"></script>
  </head>
  
  <body>
  	<input type="hidden" value="<s:property value='#session.id'/>" id="userid"/>
	<input type="hidden" value="<s:property value='#session.name'/>" id="username"/>
    <div align="center">
		<h2 style="color:#0078CA">图书信息维护&nbsp;<span style="font-size:20px;color:#0078CA"><s:property value='#session.name'/></span></h2>
	</div>
	<div align="center" style="padding-top:5px;height:95%">
		<table id="data" class="easyui-datagrid" width='100%' height="100%" data-options="collapsible:false,fit:true, height:'auto',singleSelect:true,fitColumns: true,striped:true,rownumbers:true,
		border:true,pagination:true,pageSize:20,pageList:[10,15,20,100000000],toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'id',width:80,checkbox:true">编号</th>
					<th data-options="field:'issuenumber',width:50">期号</th>
					<th data-options="field:'subcode',width:80">征订代码</th>
					<th data-options="field:'barcode',width:80">条码</th>
					<th data-options="field:'descr',width:80">书名</th>
					<th data-options="field:'price',width:80">定价</th>
					<th data-options="field:'pack',width:80">捆扎</th>
					<th data-options="field:'bundle',width:80">每包捆数</th>
					<th data-options="field:'publisher',width:80">出版社代码</th>
					<th data-options="field:'shortname',width:80">出版社</th>
					<th data-options="field:'addwho',width:80">添加人</th>
					<th data-options="field:'adddate',width:80">添加时间</th>
					<th data-options="field:'editwho',width:80">修订人</th>
					<th data-options="field:'editdate',width:80">修订时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 工具栏 -->
	<div id="tb" style="text-align:center">
    	<a class="easyui-linkbutton" iconCls="icon-search" onClick="tool.query();">查询</a>
    	<a class="easyui-linkbutton" iconCls="icon-add" onClick="tool.add();">添加</a>
    	<a class="easyui-linkbutton" iconCls="icon-add" onClick="tool.addPack();">添加捆扎</a>
    	<a class="easyui-linkbutton" iconCls="icon-add" onClick="tool.searchPack();">查看捆扎</a>
    	<a class="easyui-linkbutton" iconCls="icon-edit" onClick="tool.edit();">修改</a>
    	<a class="easyui-linkbutton" iconCls="icon-remove" onClick="tool.remove();">删除</a>
    </div>
    <div id="tb2" style="text-align:center;display:none">
    	<a class="easyui-linkbutton" iconCls="icon-remove" onClick="tool.updatePack();">修改</a>
    	<a class="easyui-linkbutton" iconCls="icon-remove" onClick="tool.removePack();">删除</a>
    </div>
   
	 <!-- 修改书号信息窗口 -->
    <div id="w-editInfo" class="easyui-window" title="修改图书资料"
			data-options="modal:false,closed:true,iconCls:'icon-edit'"
			style="width: 700px; height: 400px; padding: 0px;display:none">
			<form id="editform" align="center" style="text-align:center;margin: 0px;line-height: 41px;">
				<h2>修改图书资料</h2>
				<input type="hidden" id="oldId">
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>期号:</td><td><select class="easyui-combobox" id="issuenumber1" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200',readonly:true" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="subcode1"  data-options="readonly:true"/></td>
				</tr>
				<tr>
					<td>条码：</td><td><input class="easyui-textbox" type="text" id="barcode1" /></td>
					<td>书名：</td><td><input class="easyui-textbox" type="text" id="descr1" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-combobox" id="publisher1" data-options="url:'jc/storer_getSupplier.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200',readonly:true" ></select></td>
					<td>定价：</td><td><input class="easyui-textbox" type="text" id="price1" /></td>
				</tr>
				<tr>
					<td>捆扎：</td><td><input class="easyui-textbox" type="text" id="pack1" /></td>
					<td>每包捆数：</td><td><input class="easyui-textbox" type="text" id="edit_bundle"/></td>
				</tr>
				<tr>
					<td>修改人：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="addwho1" value="<s:property value='#session.id'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" onclick="editInfo()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
		
	 <!-- 添加捆扎信息窗口 -->
    <div id="w-addPackInfo" class="easyui-window" title="添加捆扎信息"
			data-options="modal:false,closed:true,iconCls:'icon-edit'"
			style="width: 700px; height: 400px; padding: 0px;">
			<form id="editform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
				<h2>添加捆扎信息</h2>
				<input type="hidden" id="oldId">
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>期号:</td><td><select class="easyui-combobox" id="addPack_issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200',readonly:true" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="addPack_subcode"  data-options="readonly:true"/></td>
				</tr>
				<tr>
					<td>条码：</td><td><input class="easyui-textbox" type="text" id="addPack_barcode" data-options="readonly:true"/></td>
					<td>书名：</td><td><input class="easyui-textbox" type="text" id="addPack_descr" data-options="readonly:true"/></td>
				</tr>
				<tr>
					<td>捆扎：</td><td><input class="easyui-textbox" type="text" id="addPack_pack" /></td>
					<td>添加人：</td><td><input class="easyui-textbox" data-options="readonly:true" type="text" id="addPack_addwho" value="<s:property value='#session.id'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" onclick="addPackInfo()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	 <!-- 显示捆扎信息窗口 -->
    <div id="w-showPackInfo" class="easyui-window" title="查看捆扎信息"
			data-options="modal:false,closed:true,iconCls:'icon-edit'"
			style="width: 700px; height: 400px; padding: 0px;">
			<form id="editform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
				<h2>查看捆扎信息</h2>
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%" class="showPackTab">
					<tr>
						<th data-options="field:'id',width:80,checkbox:true">编号</th>
						<th data-options="field:'issuenumber',width:50">期号</th>
						<th data-options="field:'subcode',width:80">征订代码</th>
						<th data-options="field:'pack',width:80">捆扎</th>
					</tr>
				</table>
			</form>
	</div>
	 <!-- 修改捆扎信息窗口 -->
    <div id="w_updatePackInfo" class="easyui-window" title="修改捆扎信息"
			data-options="modal:false,closed:true,iconCls:'icon-edit'"
			style="width: 700px; height: 400px; padding: 0px;">
			<form id="editform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
				<input type="hidden" id='editpack_id'> 
				<input type="hidden" id='oldpack'> 
				<h2>修改捆扎信息</h2>
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%" class="updatePackTab">
					<tr>
						<td>期号:</td><td><select class="easyui-combobox" id="updatepack_issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200',readonly:true" ></td>
						<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="updatepack_subcode" data-options="readonly:true" /></td>
					</tr>
					<tr>
						<td>捆扎：</td><td><input class="easyui-textbox" type="text" id="updatepack_pack" /></td>
					</tr>
					<td colspan="4" align="center"><a class="easyui-linkbutton" onclick="updatePackInfo()">提交</a></td>
				</table>
			</form>
	</div>
	
	<!-- 查询书号信息窗口 -->
    <div id="w-queryInfo" class="easyui-window" title="查询图书资料"
			data-options="modal:false,closed:true,iconCls:'icon-search'"
			style="width: 700px; height: 400px; padding:0px;display: none;">
		<form id="queryform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
			<h2>查询图书资料</h2>
			<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>期号:</td><td><select class="easyui-combobox" id="query_issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200'" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="query_subcode" /></td>
				</tr>
				<tr>
					<td>条码：</td><td><input class="easyui-textbox" type="text" id="query_barcode" /></td>
					<td>书名：</td><td><input class="easyui-textbox" type="text" id="query_descr" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-combobox" id="query_publisher" data-options="url:'jc/storer_getSupplier.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200'" ></select></td>
					<td>添加人：</td><td><input class="easyui-textbox" type="text" id="query_addwho" /></td>
				</tr>
				<tr>
					<td>添加时间：</td><td><input class="easyui-datebox" type="text" id="query_adddate" /></td>
				</tr>
				<tr>
					<td colspan='4' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" id="submitgo" onclick="queryInfo()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	
	 <!-- 增加书号信息窗口 -->
    <div id="w-addInfo" class="easyui-window" title="添加图书资料"
			data-options="modal:false,closed:true,iconCls:'icon-save'"
			style="width: 700px; height: 400px; padding: 0px;">
			<form id="addform" align="center" style="text-align:center;margin: 10px;line-height: 41px;">
				<input type="hidden" id="add_id" />
				<h2>添加图书资料</h2>
				<table id="formtable" border="0px" align="center" cellpadding='2'width="100%">
				<tr>
					<td>期号:</td><td><select class="easyui-combobox" id="issuenumber" data-options="url:'jc/issuenumber_info.action',method:'get',valueField:'issuenumber',textField:'issuenumber',panelHeight:'200'" ></td>
					<td>征订代码：</td><td><input class="easyui-textbox" type="text" id="subcode" /></td>
				</tr>
				<tr>
					<td>条码：</td><td><input class="easyui-textbox" type="text" id="barcode" /></td>
					<td>书名：</td><td><input class="easyui-textbox" type="text" id="descr" /></td>
				</tr>
				<tr>
					<td>出版社：</td><td><select class="easyui-combobox" id="publisher" data-options="url:'jc/storer_getSupplier.action',method:'get',valueField:'storerkey',textField:'shortname',panelHeight:'200'" ></select></td>
					<td>定价：</td><td><input class="easyui-textbox" type="text" id="price" /></td>
				</tr>
				<tr>
					<td>添加人：</td><td><input class="easyui-textbox"  type="text" id="addwho" value="<s:property value='#session.id'/>" /></td>
				</tr>
				<tr>
					<td colspan='8' align="center">&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton" id="submitgo" onclick="addInfo()">提交</a>
						<a class="easyui-linkbutton" id="resetform" onclick="resetform()">重置</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
  </body>
</html>
