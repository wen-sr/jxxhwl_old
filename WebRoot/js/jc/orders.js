/**
 * 加载数据
 */
function loadData(){
	$("#data").datagrid({
		url:'jc/orders_info.action',
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
			field:"orderqty",
			title:"采购数量",
		    width:30
		},{
			field:"addwho",
			title:"采购人",
		    width:30
		},{
			field:"adddate",
			title:"采购时间",
		    width:30
		}]]
	});
	 $("#currentType").val("1");
}
/**
 * 设置分页属性
 */
function fenye(){
	var p = $('#data').datagrid('getPager');  
    $(p).pagination({  
        beforePageText: '第',  
        afterPageText: '页    共 {pages} 页',  
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
        onBeforeRefresh:function(){ 
            $(this).pagination('loading'); 
            $(this).pagination('loaded'); 
        } 
    }); 
}

/**
 * 添加
 */
function addInfo(){	
	var issuenumber = $.trim($("#add_issuenumber").combobox('getValue'));
	if(issuenumber == ""){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var subcode = $.trim($("#add_subcode").textbox('getValue'));
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var barcode = $.trim($("#add_barcode").val());
	if(barcode == null || barcode == "" ){
		$.messager.alert("操作提示","条码不能为空！","error");
		return;
	}
	var descr = $.trim($("#add_descr").val());
	if(descr == null || descr == "" ){
		$.messager.alert("操作提示","书名不能为空！","error");
		return;
	}
	var orderqty = $.trim($("#add_orderqty").val());
	if(orderqty == null || orderqty == ""){
		$.messager.alert("操作提示","采购数量不能为空！","error");
		return;
	}
	var addwho = $.trim($("#add_addwho").val());
	if(addwho == null || addwho == ""){
		$.messager.alert("操作提示","添加人不能为空！","error");
		return;
	}
	var publisher = $.trim($("#add_publisher").combobox('getValue'));
	if(publisher == null || publisher == "" ){
		$.messager.alert("操作提示","出版社不能为空！","error");
		return;
	}
	var price = $.trim($("#add_price").val());
	if(price == null || price == "" ){
		price = 0;
	}
	var reg = new RegExp("^[0-9]*$");
	var reg2 = new RegExp("^\\d+(\\.\\d+)?$");
	if(!reg2.test(price)){
		$.messager.alert("操作提示","定价只能输入数字！","error");
		return;
	}
	if(!reg.test(orderqty)){
		$.messager.alert("操作提示","采购数量只能输入数字！","error");
		return;
	}
	$.ajax({
		type:'Post',
		url:'jc/orders_save.action',
		data:'issuenumber=' + issuenumber + "&subcode=" + subcode + "&orderqty=" + orderqty + "&addwho=" + addwho,
		dataType:'json',
		success:function(data){
			$.messager.alert("提示",data,"info",function(){
				$("#add_issuenumber").combobox('setValue','');
				$("#add_subcode").textbox('setValue','');
				$("#add_barcode").textbox('setValue','');
				$("#add_descr").textbox('setValue','');
				$("#add_publisher").combobox('setValue','');
				$("#add_price").textbox('setValue','');
				$("#w-addInfo").window("close");
//				$("#data").datagrid('reload');
				loadData();
			});
		},
		error:function(){
			$.messager.alert("提示","数据错误，联系管理员","info");
		}
	});
}
/**
 * 删除期号信息
 */
function removeInfo(){
	var row = $('#data').datagrid('getSelected');
	if(row){
		$.ajax({
			url:'jc/orders_delete.action',
			data:'id=' + row.id,
			dataType:'json',
			success:function(data){
				if(data){
					$.messager.alert("操作提示",data,"info",function(){
						$("#data").datagrid('reload');
					});
				}
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
function queryInfo(){
	var storerkey = $("#query_storerkey").textbox('getValue');
	var shortname = $("#query_shortname").textbox('getValue');
	var type = $("#query_type").combobox('getValue');
	if(storerkey == "" && shortname == "" && type == "" ){
		$.messager.alert("操作提示","必须选择一项再查询","error");
		return;
	}
	$("#data").datagrid({
		url:'jc/storer_queryInfo.action',
		method:'POST',
		queryParams: { 'storerkey': storerkey,'shortname': shortname,'type': type }
	});
	var p = $('#data').datagrid('getPager');  
    $(p).pagination({  
        beforePageText: '第',//页数文本框前显示的汉字  
        afterPageText: '页    共 {pages} 页',  
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
        onBeforeRefresh:function(){ 
            $(this).pagination('loading'); 
            $(this).pagination('loaded'); 
        } 
    });
	$("#w-queryInfo").window("close");
}
/**
 * 修改
 */
function editInfo(){
	var id = $("#edit_id").val();
	var orderqty = $.trim($("#edit_orderqty").val());
	if(orderqty == null || orderqty == ""){
		$.messager.alert("操作提示","采购数量不能为空！","error");
		return;
	}
	var addwho = $.trim($("#edit_addwho").val());
	if(addwho == null || addwho == ""){
		$.messager.alert("操作提示","添加人不能为空！","error");
		return;
	}
	$.ajax({
		type:'Post',
		url:'jc/orders_edit.action',
		data:'id=' + id + "&orderqty=" + orderqty + "&addwho=" + addwho,
		dataType:'json',
		success:function(data){
			$.messager.alert("提示",data,"info",function(){
				$("#edit_id").val("");
				$("#edit_issuenumber").combobox('setValue','');
				$("#edit_subcode").textbox('setValue','');
				$("#edit_barcode").textbox('setValue','');
				$("#edit_descr").textbox('setValue','');
				$("#edit_publisher").combobox('setValue','');
				$("#edit_price").textbox('setValue','');
				$("#edit_orderqty").textbox('setValue','');
				$("#w-editInfo").window("close");
				$("#data").datagrid('reload');
				
			});
		},
		error:function(){
			$.messager.alert("提示","数据错误，联系管理员","info");
		}
	});
	
}
/**
 * 根据期号选择征订代码
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
			url:'jc/orders_waitPurchaseSku.action?issuenumber=' + issuenumber,
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
			url:'jc/orders_loadOrdersSku.action?issuenumber=' + issuenumber,
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
 * 
 */
function go (){
	var type = $("#type").combobox("getValue");
	var issuenumber = $("#issuenumber").combobox("getValue");
	var subcode = $("#subcode").textbox('getValue');
	var param = "type=" + type + "&issuenumber=" + issuenumber + "&subcode=" + subcode;
	if(type == 0){
		$("#data").datagrid({
			url:'jc/orders_loadWaitPurchaseSkuInfo.action?issuenumber=' + issuenumber + "&subcode=" + subcode,
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20],
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
			url:'jc/orders_info.action?issuenumber=' + issuenumber + "&subcode=" + subcode,
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20],
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
				field:"orderqty",
				title:"采购数量",
			    width:30
			},{
				field:"addwho",
				title:"采购人",
			    width:30
			},{
				field:"adddate",
				title:"采购时间",
			    width:30
			}]]
		});
		 $("#currentType").val("1");
	}
}

/**
 * 根据期号和征订代码查询资料
 * 
 */
function querySku(){
	$("#barcode").textbox('setValue','');
	$("#descr").textbox('setValue','');
	$("#publisher").combobox('setValue','');
	$("#price").textbox('setValue','');
	var issuenumber = $.trim($("#issuenumber").combobox('getValue'));
	if(issuenumber == null || issuenumber == "" ){
		$.messager.alert("操作提示","请先选择期号","error");
		return;
	}
	var subcode = $.trim($("#subcode").combobox('getValue'));
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

/**
 * 操作工具
 */
tool = {
		query : function (){
			$("#w-queryInfo").window("open");
		},
		add : function (){
			var row = $('#data').datagrid('getSelected');
			if(!row || row.length == 0) {
				$.messager.alert("操作提示","没有选中记录","error");
				return;
			}
			$("#add_issuenumber").combobox('setValue',row.issuenumber);
			$("#add_subcode").textbox('setValue',row.subcode);
			$("#add_barcode").textbox('setValue',row.barcode);
			$("#add_descr").textbox('setValue',row.descr);
			$("#add_publisher").combobox('setValue',row.publisher);
			$("#add_price").textbox('setValue',row.price);
			$("#add_orderqty").textbox('setValue','');
			$("#w-addInfo").window("open");
		},
		edit : function (){
			var currentType = $("#currentType").val();
			if(currentType != 1) {
				$.messager.alert("操作提示","请先切换到已采购界面再修改","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(!row || row.length == 0) {
				$.messager.alert("操作提示","没有选中记录","error");
				return;
			}
			$("#edit_id").val(row.id);
			$("#edit_issuenumber").combobox('setValue',row.issuenumber);
			$("#edit_subcode").textbox('setValue',row.subcode);
			$("#edit_barcode").textbox('setValue',row.barcode);
			$("#edit_descr").textbox('setValue',row.descr);
			$("#edit_publisher").combobox('setValue',row.publisher);
			$("#edit_price").textbox('setValue',row.price);
			$("#edit_orderqty").textbox('setValue',row.orderqty);
			$("#w-editInfo").window("open");
		},
		remove : function(){
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


$(function(){
	loadData();
	$('#subcode').next('.combo').find('input').blur(function (){
		querySku();
	});
});