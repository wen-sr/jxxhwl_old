/**
 * 加载需配发计算信息
 * @returns
 */
function loadWaitComputeData(){

	$("#data").datagrid({
		url:'jc/compute_loadWaitComputeData.action',
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
			field:"qtyallocated",
			title:"分发数量",
		    width:50
		},{
			field:"qtyfree",
			title:"可用库存",
		    width:50
		},{
			field:"amt",
			title:"分发码洋",
		    width:50
		}]]
	});
    //0 : 表示需配发界面   1 ： 表示已配发界面
    $("#currentType").val("0");
    $('#cancelcompute').linkbutton('disable');
    $('#abcd').linkbutton('enable');
}
/**
 * 工具栏
 */
tool = {
		compute : function (){
			var rows = $('#data-detail').datagrid('getSelections');
			var currentType = $("#currentType").val();
			if("1" == currentType){
				$.messager.alert("操作提示","请先切换到需配发计算界面才能计算","error");
				return;
			}
			var pack = $("#t_pack").textbox('getValue');
			if(pack == ""){
				$.messager.alert("操作提示","请先选择捆扎才能配发计算","error");
				return;
			}
			var t_qty = $("#t_qty").textbox('getValue');
			if(parseInt(t_qty) < 0 || t_qty == ''){
				$.messager.alert("操作提示","库存不足，无法计算","error");
				return;
			}
			if(!rows || rows.length == 0) {
				$.messager.alert("操作提示","没有选中记录","error");
				return;
			}
			compute();
			
		},
		editpack : function (){
			var currentType = $("#currentType").val();
			if("0" != currentType){
				$.messager.alert("操作提示","请先切换到需配发计算界面才能选择捆扎数","error");
				return;
			}
			var issuenumber = $("#span_issuenumber").val();
			var subcode = $("#span_subcode").val();
			$("#t_choosepack").datagrid({
				url:'jc/inventory_queryInventory.action?issuenumber=' + issuenumber + "&subcode=" + subcode,
				height:'auto',	
				fitColumns: true,
				fit: true,
				striped:true,
				rownumbers:true,
				border:true,
				singleSelect:true,
				showFooter: true,
				toolbar:'#tb4',
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
					field:"pack",
					title:"捆扎",
				    width:30
				},{
					field:"qtyfree",
					title:"可用库存",
				    width:50
				}]]
			});
			
			$("#w_choosePack").window("open");
		},
		cancelcompute : function (){
			var currentType = $("#currentType").val();
			var rows = $('#data').datagrid('getSelections');
			if(!rows || rows.length == 0) {
				$.messager.alert("操作提示","没有选中记录","error");
				return;
			}
			$.messager.confirm("操作提示", "您确定要取消这  "+ rows.length +" 条记录的计算吗？", function (data) {  
	            if (data) {  
	            	cancelcompute();
	            }  
	            else {  
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
		},
		yes2 : function (){
			var row = $("#t_choosepack").datagrid('getSelected');
			if(row == null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#t_qtyfree").textbox('setValue',row.qtyfree);
			$("#t_qty").textbox('setValue',row.qtyfree);
			$("#t_pack").combobox('setValue',row.pack);
			$("#w_choosePack").window("close");
			$('#data-detail').datagrid('reload');
		},
		no2 : function (){
			$("#showSubcode").window("close");
		},
		detail : function (){
			var currentType = $("#currentType").val();
			if(currentType != "0"){
				$.messager.alert("操作提示","请先切换到需配发界面才能进行分书未发","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(row== null) {
				$.messager.alert("操作提示","没有选中记录","error");
				return;
			}
			dataDetail();
			$("#w-detail").window("open");
		},
		split : function(){
			var currentType = $("#currentType").val();
			if(currentType != "0"){
				$.messager.alert("操作提示","请先切换到需配发界面才能进行分书未发","error");
				return;
			}
			var row = $('#data-detail').datagrid('getSelected');
			if(row== null) {
				$.messager.alert("操作提示","没有选中记录","error");
				return;
			}
			$("#split_id").val(row.id);
			$("#split_issuenumber").textbox('setValue',row.issuenumber);
			$("#split_subcode").textbox('setValue',row.subcode);
			$("#split_qtyallocated").textbox('setValue',row.qtyallocated);
			$("#split_qtysplit").textbox('setValue',"");
			$("#w-detail-split").window("open");
		}
	}
/**
 * 计算
 */
function compute(){
	var rows = $('#data-detail').datagrid('getSelections');
	var addwho = $("#userid").val();
	if(addwho == ""){
		$.messager.alert("提示","登录信息失效，请重新登录","info",function(){
			getTopWinow().location.href = 'login.action';
		});
	}
	var ids = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ids.push(row.id);
	}
	var pack = $("#t_pack").textbox('getValue');
	$.ajax({
		type:'post',
		url:'jc/compute_compute.action',
		data:"id=" + ids + "&pack=" + pack + "&addwho=" + addwho,
		dataType:'json',
		success:function (data){
			$.messager.alert("操作提示",data,"info");
//			$('#data-detail').datagrid('reload');
			dataDetail();
			
		},
		error:function(){
			$.messager.alert("提示","数据错误，联系管理员","info");
		}
	});
}
/**
 * 取消计算
 */
function cancelcompute(){
	var rows = $('#data').datagrid('getSelections');
	var ids = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ids.push(row.id);
	}
	$.ajax({
		type:'post',
		url:'jc/compute_cancelCompute.action',
		data:'id=' + ids,
		dataType:'json',
		success : function(data){
			$.messager.alert("操作提示",data,"info");
			$('#data').datagrid('reload');
		},
		error:function(){
			$.messager.alert("提示","数据错误，联系管理员","info");
		}
	});
}

/**
 * 分书拆分
 */
function splitInfo(){
	var id = $("#split_id").val();
	var issuenumber = $("#split_issuenumber").textbox('getValue');
	var subcode = $("#split_subcode").textbox('getValue');
	var qtysplit = $("#split_qtysplit").textbox('getValue');
	var addwho = $("#userid").val();
	if(qtysplit == ""){
		$.messager.alert("操作提示","拆分数量不能为空","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'jc/compute_split.action',
		data:'id=' + id + "&qtyallocated=" + qtysplit + "&addwho=" + addwho,
		dataType:'json',
		success : function(data){
			$.messager.alert("操作提示",data,"info");
			$("#w-detail-split").window("close");
			$('#data-detail').datagrid('reload');
		},
		error:function(){
			$.messager.alert("提示","数据错误，联系管理员","info");
		}
	});
}

/**
 * 分书未发
 */
function dataDetail(){
	var row = $('#data').datagrid('getSelected');
	$("#span_issuenumber").val(row.issuenumber);
	$("#span_subcode").val(row.subcode);
	
	$("#data-detail").datagrid({
		url:'jc/compute_loadDataDetail.action?issuenumber=' + row.issuenumber + "&subcode=" + row.subcode,
		height:'auto',	
		fitColumns: true,
		fit: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:false,
		pagination:true,
        pageSize:1000,
        pageList:[10,15,20,1000],
		showFooter: true,
		toolbar:'#tb3',
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
			field:"pack",
			title:"捆扎",
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
			title:"分发数量",
		    width:50
		},{
			field:"amt",
			title:"分发码洋",
		    width:50
		},{
			field:"type",
			title:"类型",
		    width:50
		}]],
		onSelect : function (index, row) {
			var t_qtyoccupied = 0;
			var t_qtyfree = $("#t_qtyfree").textbox('getValue');
			var t_qty = $("#t_qty").textbox('getValue');
			if(t_qtyfree == ""){
				return;
			}
			var rows = $('#data-detail').datagrid('getSelections');
			for(var i in rows ){
				t_qtyoccupied += parseInt(rows[i].qtyallocated);
			}
			$("#t_qtyoccupied").textbox('setValue',t_qtyoccupied);
			$("#t_qty").textbox('setValue',parseInt(t_qtyfree) - t_qtyoccupied);
			$("#t_qtyoccupied").css('color','red')
		},
		onUnselect : function (index, row) {
			var t_qtyoccupied = 0;
			var t_qtyfree = $("#t_qtyfree").textbox('getValue');
			var t_qty = $("#t_qty").textbox('getValue');
			if(t_qtyfree == ""){
				return;
			}
			var rows = $('#data-detail').datagrid('getSelections');
			for(var i in rows ){
				t_qtyoccupied += parseInt(rows[i].qtyallocated);
			}
			$("#t_qtyoccupied").textbox('setValue',t_qtyoccupied);
			$("#t_qty").textbox('setValue',parseInt(t_qtyfree) - t_qtyoccupied);
			$("#t_qtyoccupied").css('color','red')
		},
		onSelectAll : function (rows){
			var t_qtyoccupied = 0;
			var t_qtyfree = $("#t_qtyfree").textbox('getValue');
			var t_qty = $("#t_qty").textbox('getValue');
			if(t_qtyfree == ""){
				return;
			}
			var rows = $('#data-detail').datagrid('getSelections');
			for(var i in rows ){
				t_qtyoccupied += parseInt(rows[i].qtyallocated);
			}
			$("#t_qtyoccupied").textbox('setValue',t_qtyoccupied);
			$("#t_qty").textbox('setValue',parseInt(t_qtyfree) - t_qtyoccupied);
		},
		onUnselectAll : function (rows){
			var t_qtyoccupied = 0;
			var t_qtyfree = $("#t_qtyfree").textbox('getValue');
			var t_qty = $("#t_qty").textbox('getValue');
			if(t_qtyfree == ""){
				return;
			}
			var rows = $('#data-detail').datagrid('getSelections');
			for(var i in rows ){
				t_qtyoccupied += parseInt(rows[i].qtyallocated);
			}
			$("#t_qtyoccupied").textbox('setValue',t_qtyoccupied);
			$("#t_qty").textbox('setValue',parseInt(t_qtyfree) - t_qtyoccupied);
		}
	});
	$('#editpack').linkbutton('disable');
	$.ajax({
		type:"post",
		url:"jc/inventory_queryInventoryAndBundle.action",
		data:"issuenumber=" + row.issuenumber + "&subcode=" + row.subcode,
		dataType:"json",
		success:function(data){
			if(data){
				$("#t_qtyfree").textbox('setValue',data.qtyfree);
				$("#t_qty").textbox('setValue',data.qtyfree);
				$("#t_bundle").textbox('setValue',data.bundle);
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
	
	$("#t_pack").combobox({
		url:'jc/pack_query.action?issuenumber=' + row.issuenumber + "&subcode=" + row.subcode,
		valueField:'pack',
		textField:'pack',
		panelHeight:'auto',
		editable:false,
		readonly:true,
		onLoadSuccess: function () { //加载完成后,设置选中第一项
            var val = $(this).combobox("getData");
            if(val.length == 1){
            	$("#t_pack").combobox('setValue',val[0]['pack']);
            }else{
            	$('#editpack').linkbutton('enable');
            	$("#t_qtyfree").textbox('setValue','');
				$("#t_qty").textbox('setValue','');
				$("#t_qtyoccupied").textbox('setValue','');
            }
        }
	});
	
	$("#span_pack").combobox({
		url:'jc/pack_query.action?issuenumber=' + row.issuenumber + "&subcode=" + row.subcode,
		valueField:'pack',
		textField:'pack',
		panelHeight:'auto',
		editable:false
	});
	
}

function go(){
	var type = $("#type").combobox("getValue");
	var issuenumber = $("#issuenumber").combobox("getValue");
	var subcode = $("#subcode").textbox('getValue');
	var barcode = $("#barcode").textbox('getValue');
	var param = "type=" + type + "&issuenumber=" + issuenumber + "&subcode=" + subcode;
	if("0" == type ){
		$("#data").datagrid({
			url:"jc/compute_loadWaitComputeData.action?type=" + type + "&issuenumber=" + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode,
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
				field:"qtyallocated",
				title:"已分发数量",
			    width:50
			},{
				field:"qtyfree",
				title:"可用库存",
			    width:50
			},{
				field:"amt",
				title:"已分发码洋",
			    width:50
			}]]
		});
	    $("#currentType").val("0");
	    $('#cancelcompute').linkbutton('disable');
	    $('#abcd').linkbutton('enable');
	}else {
		$("#data").datagrid({
			url:"jc/compute_loadComputedData.action?type=" + type + "&issuenumber=" + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode,
			height:'auto',	
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			singleSelect:false,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20],
			showFooter: true,
			toolbar:'#tb5',
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
				field:"pack",
				title:"包册数",
			    width:30
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
	    $('#cancelcompute').linkbutton('enable');
	    $('#abcd').linkbutton('disable');
	}
}

/**
 * 加载已配发信息
 * @returns
 */
function loadComputedData(){

	$("#data").datagrid({
		url:'jc/compute_loadComputedData.action',
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:false,
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
			field:"pack",
			title:"包册数",
		    width:30
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
			field:"caseqty",
			title:"整件",
		    width:50
		},{
			field:"oddpack",
			title:"零捆",
		    width:50
		},{
			field:"odd",
			title:"零册",
		    width:50
		},{
			field:"amt",
			title:"总码洋",
		    width:50
		}]]
	});
    
    $("#currentType").val("1");
    $('#cancelcompute').linkbutton('enable');
    $('#abcd').linkbutton('disable');

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
			url:'jc/compute_loadWaitComputeData.action?issuenumber=' + issuenumber,
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
			url:'jc/compute_loadComputedSubcodeByIssuenumber.action?issuenumber=' + issuenumber,
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
				title:"已配发数量",
			    width:50
			}]]
		});
		$("#showSubcode").window("open");
	}
}

$(function(){
	loadWaitComputeData();
});


