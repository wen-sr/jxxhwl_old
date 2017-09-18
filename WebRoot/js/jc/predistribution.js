$(function(){
	loadWaitPreDistributionData();
//	load();
})

//提交表单
function submitgo(){
	var issuenumber = $.trim($("#add_issuenumber").combobox('getValue'));
	if(issuenumber == null || issuenumber == "" ){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var subcode = $.trim($("#add_subcode").val());
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var code = $.trim($("#add_code").combobox('getValue'));
	if(code == null || code == "" ){
		$.messager.alert("操作提示","分发店不能为空！","error");
		return;
	}
	var qtyallocated = $.trim($("#add_qtyallocated").val());
	if(qtyallocated == null || qtyallocated == "" ){
		$.messager.alert("操作提示","分发数量不能为空！","error");
		return;
	}
	var addwho = $.trim($("#addwho").val());
	if(addwho == null || addwho == "" ){
		$.messager.alert("操作提示","分发人不能为空！","error");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(qtyallocated)){
		$.messager.alert("操作提示","分发数量只能输入数字！","error");
		return;
	}
	var qtyfree = $.trim($("#add_qtyfree").textbox('getValue'));
	if(parseInt(qtyallocated) > parseInt(qtyfree)){
		$.messager.alert("操作提示","分发数量不能大于可分发数！","error");
		return;
	}
	var param = "subcode=" + subcode + "&code=" + code + "&qtyallocated=" + qtyallocated + "&addwho=" + addwho + "&issuenumber=" + issuenumber;
	$.ajax({
		type:'POST',
		url:'jc/distribution_predistribution.action',
		data:param,
		dataType:'json',
		success:function(data){
			if(data){
				if(data["msg"] == "保存成功"){
					$.messager.alert("操作提示",data["msg"],"info",function(){
						if(data["qtyfree"] || data["qtyfree"] == 0){
							$("#add_qtyfree").textbox('setValue',data["qtyfree"]);
						}
						$("#add_qtyallocated").textbox('setValue','');
						$("#add_code").combobox('setValue','');
						load();
					});
				}else {
					$.messager.alert("操作提示",data["msg"],"error");
				}
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}

/**
 * 加载可预分发数据
 */
function loadWaitPreDistributionData(){
	$("#data").datagrid({
		url:'jc/distribution_waitPreDistributionData.action',
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:true,
        pageSize:20,
        pageList:[10,15,20, 100000000],
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
			field:"qtyfree",
			title:"可分发数",
		    width:30
		},{
			field:"publisher",
			title:"出版社代码",
		    width:30
		},{
			field:"shortname",
			title:"出版社",
		    width:30
		}]]
	});
	$("#currentType").val("0");
}
/**
 * 加载已分发数据
 */
function load(){
	$("#data").datagrid({
		url:'jc/distribution_loadDistributedData.action?type=0',
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:true,
        pageSize:20,
        pageList:[10,15,20,100000000],
		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"id",
		    title:"编号",
		    checkbox:true,
		    width:30
		},{
			field:"issuenumber",
			title:"期号",
		    width:30
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
		    width:30
		},{
			field:"code",
			title:"客户代码",
		    width:30
		},{
			field:"shortname",
			title:"客户名称",
		    width:30
		},{
			field:"qtyallocated",
			title:"分发数",
		    width:30
		},{
			field:"storerkey",
			title:"出版社代码",
		    width:30
		},{
			field:"publisher",
			title:"出版社名称",
		    width:30
		},{
			field:"addwho",
			title:"分发人",
		    width:30
		},{
			field:"adddate",
			title:"分发时间",
		    width:30
		}]]
	});
	$("#currentType").val("1");
	
}
/**
 * 选择征订代码
 */
function chooseSubcode(){
	var type = $("#type").combobox("getValue");
	var issuenumber = $("#issuenumber").combobox("getValue");
	if(issuenumber == "") {
		$.messager.alert("操作提示","请先选择期号","error");
		return;
	}
	if(type == "0") {
		$("#c_subcode").datagrid({
			url:'jc/distribution_waitPreDistributionData.action?issuenumber=' + issuenumber,
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
			}]]
		});
		$("#showSubcode").window("open");
	}else if(type == "1"){
		$("#c_subcode").datagrid({
			url:'jc/distribution_loadDistributedSubcodeByIssuenumber.action?issuenumber=' + issuenumber + "&type=0",
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
			}]]
		});
		$("#showSubcode").window("open");
		
	}
	
}

/**
 * 分页设置
 */
function fenye(){
	var p = $('#data').datagrid('getPager');  
    $(p).pagination({  
//        pageSize: 20,//每页显示的记录条数，默认为10  
//        pageList: [10,15,20],//可以设置每页记录条数的列表  
        beforePageText: '第',//页数文本框前显示的汉字  
        afterPageText: '页    共 {pages} 页',  
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
        onBeforeRefresh:function(){ 
            $(this).pagination('loading'); 
            $(this).pagination('loaded'); 
        } 
    }); 
}
/**
 * 删除
 */
function removeInfo(){

	var row = $('#data').datagrid('getSelected');
	if(row){
		$.ajax({
			url:'jc/distribution_delete.action',
			data:'id=' + row.id,
			dataType:'json',
			success:function(data){
				$.messager.alert("操作提示",data,"info");
				load();
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
/**
 * 查询
 */
function go(){
	var type = $("#type").combobox("getValue");
	var issuenumber = $("#issuenumber").combobox("getValue");
	var subcode = $("#subcode").textbox('getValue');
	var param = "type=" + type + "&issuenumber=" + issuenumber + "&subcode=" + subcode;
	if(type == 0){
		$("#data").datagrid({
			url:'jc/distribution_waitPreDistributionData.action?issuenumber=' + issuenumber + "&subcode=" + subcode,
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20,100000000],
			singleSelect:true,
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
				field:"qtyfree",
				title:"可分发数",
			    width:30
			},{
				field:"publisher",
				title:"出版社代码",
			    width:30
			},{
				field:"shortname",
				title:"出版社",
			    width:30
			}]]
		});
		$("#currentType").val("0");
	}else if(type == 1){
		$("#data").datagrid({
			url:'jc/distribution_loadDistributedData.action?issuenumber=' + issuenumber + "&subcode=" + subcode + "&type=0",
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20,100000000],
			singleSelect:true,
			showFooter: true,
			toolbar:'#tb',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:30
			},{
				field:"issuenumber",
				title:"期号",
			    width:30
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
			    width:30
			},{
				field:"code",
				title:"客户代码",
			    width:30
			},{
				field:"shortname",
				title:"客户名称",
			    width:30
			},{
				field:"qtyallocated",
				title:"分发数",
			    width:30
			},{
				field:"storerkey",
				title:"出版社代码",
			    width:30
			},{
				field:"publisher",
				title:"出版社名称",
			    width:30
			},{
				field:"addwho",
				title:"分发人",
			    width:30
			},{
				field:"adddate",
				title:"分发时间",
			    width:30
			}]]
		});
		 $("#currentType").val("1");
	}
}


/**
 * 操作工具
 */
tool = {
		add : function (){
			var currentType = $("#currentType").val();
			if(currentType != "0"){
				$.messager.alert("操作提示","请先切换到可分发界面才能新增分发单","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(!row || row.length == 0) {
				$.messager.alert("操作提示","没有选中记录","error");
				return;
			}
			$("#add_id").val(row.id);
			$("#add_issuenumber").combobox('setValue',row.issuenumber);
			$("#add_subcode").textbox('setValue',row.subcode);
			$("#add_barcode").textbox('setValue',row.barcode);
			$("#add_descr").textbox('setValue',row.descr);
			$("#add_publisher").combobox('setValue',row.publisher);
			$("#add_price").textbox('setValue',row.price);
			$("#add_qtyfree").textbox('setValue',row.qtyfree);
			$("#w-addInfo").window("open");
		},
		remove : function(){
			var currentType = $("#currentType").val();
			if(currentType != "1"){
				$.messager.alert("操作提示","请先切换到已分发界面才能修改","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
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
		edit : function(){
			var currentType = $("#currentType").val();
			if(currentType != "1"){
				$.messager.alert("操作提示","请先切换到已分发界面才能修改","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			setRowValue();
			$("#w-editInfo").window("open");
		},
		yes : function (){
			var row = $("#c_subcode").datagrid('getSelected');
			if(row == null){
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
 * 设置修改信息
 */
function setRowValue() {
	var row = $('#data').datagrid('getSelected');
	if(row){
		$("#edit_id").val(row.id);
		$("#edit_issuenumber").combobox("setValue",row.issuenumber);
		$("#edit_subcode").textbox('setValue',row.subcode);
		$("#edit_barcode").textbox('setValue',row.barcode);
		$("#edit_descr").textbox('setValue',row.descr);
		$("#edit_publisher").combobox('setValue',row.storerkey);
		$("#edit_price").textbox('setValue',row.price);
		$("#edit_customer").combobox('setValue',row.code);
		$("#edit_qtyfenfa").textbox('setValue',row.qtyallocated);
	}
}
/**
 * 提交修改的信息
 * @returns
 */
function editInfo(){
	var id = $("#edit_id").val();
	var issuenumber = $.trim($("#edit_issuenumber").combobox('getValue'));
	if(issuenumber == ""){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var subcode = $.trim($("#edit_subcode").val());
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var barcode = $.trim($("#edit_barcode").val());
	if(barcode == null || barcode == "" ){
		$.messager.alert("操作提示","条码不能为空！","error");
		return;
	}
	var descr = $.trim($("#edit_descr").val());
	if(descr == null || descr == "" ){
		$.messager.alert("操作提示","书名不能为空！","error");
		return;
	}
	var addwho = $.trim($("#edit_addwho").val());
	if(addwho == null || addwho == ""){
		$.messager.alert("操作提示","修改人不能为空！","error");
		return;
	}
	var publisher = $.trim($("#edit_publisher").combobox('getValue'));
	if(publisher == null || publisher == "" ){
		$.messager.alert("操作提示","出版社不能为空！","error");
		return;
	}
	var code = $.trim($("#edit_customer").combobox('getValue'));
	if(code == null || code == "" ){
		$.messager.alert("操作提示","分发店不能为空！","error");
		return;
	}
	var price = $.trim($("#edit_price").val());
	if(price == null || price == "" ){
		price = 0;
	}
	var qtyallocated = $.trim($("#edit_qtyfenfa").val());
	if(qtyallocated == null || qtyallocated == "" ){
		$.messager.alert("操作提示","分发数量不能为空！","error");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	var reg2 = new RegExp("^\\d+(\\.\\d+)?$");
	if(!reg2.test(price)){
		$.messager.alert("操作提示","定价只能输入数字！","error");
		return;
	}
	var param = "id=" + id + "&subcode=" + subcode + "&code=" + code + "&qtyallocated=" + qtyallocated + "&addwho=" + addwho + "&issuenumber=" + issuenumber + "&type=0";      
	$.ajax({
		type:"post",
		url:"jc/distribution_update.action",
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
 * 根据期号和征订代码查询资料
 * 
 */
function querySku(){
	var issuenumber = $.trim($("#issuenumber").combobox('getText'));
	if(issuenumber == null || issuenumber == "" ){
		return;
	}
	var subcode = $.trim($("#subcode1").textbox('getText'));
	if(subcode == null || subcode == "" ){
		return;
	}
	$.ajax({
		type:"post",
		url:"jc/sku_queryByIssuenumberAndSubcode.action",
		data:"issuenumber=" + issuenumber + "&subcode=" + subcode,
		dataType:"json",
		success:function(data){
			if(data){
				$("#barcode").textbox('setValue',data.barcode);
				$("#descr").textbox('setValue',data.descr);
				$("#publisher").combobox('setValue',data.publisher);
				$("#price").textbox('setValue',data.price);
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
