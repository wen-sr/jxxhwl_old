/**
 * 加载库存分发信息
 * @returns
 */
function loadWaitDistributionData(){

	$("#data").datagrid({
		url:'jc/distribution_loadWaitDistributionData.action',
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:true,
        pageSize:20,
        pageList:[10,15,20],
		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"id",
		    title:"编号",
		    checkbox:true,
		    width:50
		},{
			field:"issuenumber",
			title:"期号",
		    width:60
		},{
			field:"subcode",
			title:"征订代码",
		    width:100
		},{
			field:"barcode",
			title:"条码",
		    width:100
		},{
			field:"descr",
			title:"书名",
		    width:100
		},{
			field:"price",
			title:"定价",
		    width:50
		},{
			field:"publisher",
			title:"出版社代码",
		    width:60
		},{
			field:"shortname",
			title:"出版社",
		    width:60
		},{
			field:"qtyfree",
			title:"可用库存",
		    width:50
		},{
			field:"qtyallocated",
			title:"已分发数量",
		    width:50
		}]]
	});
	$("#currentType").val("0");

}

/**
 * 提交库存分发记录
 * @returns
 */
function addInfo(){
	var issuenumber = $.trim($("#add_issuenumber").combobox('getValue'));
	if(issuenumber == ""){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var subcode = $.trim($("#add_subcode").val());
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var addwho = $.trim($("#add_addwho").val());
	if(addwho == null || addwho == ""){
		$.messager.alert("操作提示","修改人不能为空！","error");
		return;
	}
	var code = $.trim($("#add_code").combobox('getValue'));
	if(code == null || code == "" ){
		$.messager.alert("操作提示","分发店不能为空！","error");
		return;
	}
	var qtyallocated = $.trim($("#add_qtyallocated").val());
	if(qtyallocated == null || qtyallocated == "" || qtyallocated == 0 ){
		$.messager.alert("操作提示","分发数量不能为空！","error");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(qtyallocated)){
		$.messager.alert("操作提示","分发数量只能输入数字！","error");
		return;
	}
	var param = "subcode=" + subcode + "&qtyallocated=" + qtyallocated + "&addwho=" + addwho + "&issuenumber=" + issuenumber + "&code=" + code + "&type=1";
	$.ajax({
		type:'post',
		url:'jc/distribution_submitInventoryDistributionData.action',
		data:param,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data["msg"],"info");
				if(data["qtyfree"] || data["qtyfree"] == 0){
					$("#add_qtyfree").textbox('setValue',data["qtyfree"]);
				}
			}
			$("#add_qtyallocated").textbox('setValue','');
			$("#add_code").combobox('setValue','');
			loadDistributedData();
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 提交修改后的库存分发记录
 * @returns
 */
function editInfo(){
	var issuenumber = $.trim($("#edit_issuenumber").combobox('getValue'));
	var id = $("#edit_id").val();
	if(issuenumber == ""){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var subcode = $.trim($("#edit_subcode").val());
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var addwho = $.trim($("#edit_addwho").val());
	if(addwho == null || addwho == ""){
		$.messager.alert("操作提示","修改人不能为空！","error");
		return;
	}
	var code = $.trim($("#edit_code").combobox('getValue'));
	if(code == null || code == "" ){
		$.messager.alert("操作提示","分发店不能为空！","error");
		return;
	}
	var qtyallocated = $.trim($("#edit_qtyallocated").val());
	if(qtyallocated == null || qtyallocated == "" || qtyallocated == 0 ){
		$.messager.alert("操作提示","分发数量不能为空！","error");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(qtyallocated)){
		$.messager.alert("操作提示","分发数量只能输入数字！","error");
		return;
	}
	var param = "id=" + id + "&subcode=" + subcode + "&qtyallocated=" + qtyallocated + "&addwho=" + addwho + "&issuenumber=" + issuenumber + "&code=" + code + "&type=1";
	$.ajax({
		type:'post',
		url:'jc/distribution_submitEditInventoryDistributionData.action',
		data:param,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info");
			}
			$("#w-editInfo").window("close");
			$("#data").datagrid('reload');
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 查询
 */
function go(){
	var type = $("#type").combobox("getValue");
	var issuenumber = $("#issuenumber").combobox("getValue");
	var subcode = $("#subcode").textbox('getValue');
	var barcode = $("#barcode").textbox('getValue');
	var param = "type=" + type + "&issuenumber=" + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode;
	if("0" == type ){
		$("#data").datagrid({
			url:"jc/distribution_loadWaitDistributionData.action?type=" + type + "&issuenumber=" + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode,
			height:'auto',	
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			singleSelect:true,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20],
			showFooter: true,
			toolbar:'#tb',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:50
			},{
				field:"issuenumber",
				title:"期号",
			    width:60
			},{
				field:"subcode",
				title:"征订代码",
			    width:100
			},{
				field:"barcode",
				title:"条码",
			    width:100
			},{
				field:"descr",
				title:"书名",
			    width:100
			},{
				field:"price",
				title:"定价",
			    width:50
			},{
				field:"publisher",
				title:"出版社代码",
			    width:60
			},{
				field:"shortname",
				title:"出版社",
			    width:60
			},{
				field:"qtyfree",
				title:"可用库存",
			    width:50
			},{
				field:"qtyallocated",
				title:"已分发数量",
			    width:50
			}]]
		});
		$("#currentType").val("0");
	}else {
		$("#data").datagrid({
			url:"jc/distribution_loadDistributedData.action?&issuenumber=" + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode + "&type=1",
			height:'auto',	
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			singleSelect:true,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20],
			showFooter: true,
			toolbar:'#tb',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:50
			},{
				field:"issuenumber",
				title:"期号",
			    width:50
			},{
				field:"subcode",
				title:"征订代码",
			    width:50
			},{
				field:"barcode",
				title:"条码",
			    width:50
			},{
				field:"descr",
				title:"书名",
			    width:50
			},{
				field:"price",
				title:"定价",
			    width:50
			},{
				field:"storerkey",
				title:"出版社代码",
			    width:30
			},{
				field:"publisher",
				title:"出版社",
			    width:30
			},{
				field:"code",
				title:"分发店代码",
			    width:50
			},{
				field:"shortname",
				title:"分发店",
			    width:30
			},{
				field:"qtyallocated",
				title:"已分发数量",
			    width:50
			},{
				field:"amt",
				title:"已分发码洋",
			    width:50
			}]]
		});
		$("#currentType").val("1");
	}
}

/**
 * 工具栏
 */
tool = {
		add : function (){
			var currentType = $("#currentType").val();
			if(currentType != "0"){
				$.messager.alert("操作提示","请先切换到等待分发界面才能进行分发","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(row == null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#add_issuenumber").combobox("setValue",row.issuenumber);
			$("#add_subcode").textbox('setValue',row.subcode);
			$("#add_barcode").textbox('setValue',row.barcode);
			$("#add_descr").textbox('setValue',row.descr);
			$("#add_publisher").combobox('setValue',row.publisher);
			$("#add_price").textbox('setValue',row.price);
			$("#add_qtyfree").textbox('setValue',row.qtyfree);
			$("#w-addInfo").window("open");
		},
		edit : function (){
			var currentType = $("#currentType").val();
			if(currentType != "1"){
				$.messager.alert("操作提示","请先切换到已分发界面才能进行修改","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#edit_id").val(row.id);
			$("#edit_issuenumber").combobox("setValue",row.issuenumber);
			$("#edit_subcode").textbox('setValue',row.subcode);
			$("#edit_barcode").textbox('setValue',row.barcode);
			$("#edit_descr").textbox('setValue',row.descr);
			$("#edit_publisher").combobox('setValue',row.storerkey);
			$("#edit_price").textbox('setValue',row.price);
			$("#edit_code").combobox('setValue',row.code);
			$("#edit_qtyallocated").textbox('setValue',row.qtyallocated);
			$("#w-editInfo").window("open");
			
		},
		remove : function(){
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			if(row.code == undefined || row.code == ""){
				$.messager.alert("操作提示","只能删除已分发的数据，请先切换到已分发界面","error");
				return;
			}
			$.messager.confirm("操作提示", "您确定要删除这条记录吗？", function (data) {
		         if (data) {
		        	 removeInfo();
		         }else {
			         return;
			     }
		     });
		},
		yes : function (){
			var row = $("#c_subcode").datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#subcode").textbox('setValue',row.subcode);
			$("#showSubcode").window("close");
		},
		no : function (){
			$("#showSubcode").window("close");
		}
	}

/**
 * 加载库存已分发信息
 * @returns
 */
function loadDistributedData(){

	$("#data").datagrid({
		url:'jc/distribution_loadDistributedData.action?type=1',
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:true,
        pageSize:20,
        pageList:[10,15,20],
		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"id",
		    title:"编号",
		    checkbox:true,
		    width:50
		},{
			field:"issuenumber",
			title:"期号",
		    width:50
		},{
			field:"subcode",
			title:"征订代码",
		    width:50
		},{
			field:"barcode",
			title:"条码",
		    width:50
		},{
			field:"descr",
			title:"书名",
		    width:50
		},{
			field:"price",
			title:"定价",
		    width:50
		},{
			field:"storerkey",
			title:"出版社代码",
		    width:30
		},{
			field:"publisher",
			title:"出版社",
		    width:30
		},{
			field:"code",
			title:"分发店代码",
		    width:50
		},{
			field:"shortname",
			title:"分发店",
		    width:30
		},{
			field:"qtyallocated",
			title:"已分发数量",
		    width:50
		},{
			field:"amt",
			title:"已分发码洋",
		    width:50
		}]]
	});
	$("#currentType").val("1");
}
/**
 * 根据期号选择征订代码
 * @returns
 */
function chooseSubcode(){
	var type = $("#type").combobox("getValue");
	var issuenumber = $("#issuenumber").combobox("getValue");
	if("0" == type) {
		$("#c_subcode").datagrid({
			url:'jc/distribution_loadDistributionSubcodeByIssuenumber.action?issuenumber=' + issuenumber,
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			singleSelect:true,
			showFooter: true,
			toolbar:'#tb2',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:50
			},{
				field:"issuenumber",
				title:"期号",
			    width:50
			},{
				field:"subcode",
				title:"征订代码",
			    width:50
			},{
				field:"barcode",
				title:"条码",
			    width:50
			},{
				field:"descr",
				title:"书名",
			    width:50
			},{
				field:"price",
				title:"定价",
			    width:50
			},{
				field:"storerkey",
				title:"出版社代码",
			    width:30
			},{
				field:"publisher",
				title:"出版社",
			    width:30
			},{
				field:"qtyallocated",
				title:"已分发数量",
			    width:50
			},{
				field:"qtyfree",
				title:"可用库存",
			    width:50
			}]]
		});
		$("#showSubcode").window("open");
		
	}else {
		$("#c_subcode").datagrid({
			url:'jc/distribution_loadDistributedSubcodeByIssuenumber.action?issuenumber=' + issuenumber + "&type=1",
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			singleSelect:true,
			showFooter: true,
			toolbar:'#tb2',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:50
			},{
				field:"issuenumber",
				title:"期号",
			    width:50
			},{
				field:"subcode",
				title:"征订代码",
			    width:50
			},{
				field:"barcode",
				title:"条码",
			    width:50
			},{
				field:"descr",
				title:"书名",
			    width:50
			},{
				field:"price",
				title:"定价",
			    width:50
			},{
				field:"publisher",
				title:"出版社代码",
			    width:30
			},{
				field:"shortname",
				title:"出版社",
			    width:30
			},{
				field:"qtyallocated",
				title:"已分发数量",
			    width:50
			}]]
		});
		$("#showSubcode").window("open");
	}
}
/**
 * 删除分发记录
 * @returns
 */
function removeInfo(){
	var row = $("#data").datagrid('getSelected');
	if(row){
		$.ajax({
			url:'jc/distribution_delete_1.action',
			data:'id=' + row.id,
			dataType:'json',
			success:function(data){
				$.messager.alert("操作提示",data,"info");
				$("#data").datagrid('reload');
			},
			error:function(){
				$.messager.alert("提示","数据错误，联系管理员","info");
			}
		});
	}else{
		$.messager.alert("操作提示","没有选中的记录","error");
		return;
	}
	
}

$(function(){
	loadWaitDistributionData();
});