/**
 * 添加未打包数据
 * @param data
 */
function addUnpackData(code){
	$("#data1").datagrid({
		url:'yb/pack_loadUnpackData.action?code=' + code,
		height:'auto',
		fit:true,
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:true,
        pageSize:200,
        pageList:[10,15,20,200],
		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"id",
		    title:"编号",
		    checkbox:true,
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
			field:"qty",
			title:"册数",
		    width:30
		},{
			field:"code",
			title:"出版社代码",
		    width:30
		},{
			field:"shortname",
			title:"出版社",
		    width:30
		}]],
		onDblClickRow: function (rowIndex,rowData) {  
			$("#barcode").textbox('setValue', rowData.barcode);
			$("#price").textbox('setValue', rowData.price);
			$("#descr").textbox('setValue', rowData.descr);
			$("#pack_id").textbox('setValue', rowData.id);
			$("#qty_wait").textbox('setValue', rowData.qty);
			$('#qty').textbox('textbox').focus();
	    }
	});
	
    
}
/**
 * 添加已刷未封数据
 * @param data
 */
function addPackedData(code) {
	$("#data2").datagrid({
		url:'yb/pack_loadPackedData.action?code=' + code,
		height:'auto',	
		fit:true,
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:true,
        pageSize:200,
        pageList:[10,15,20,200],
		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"id",
		    title:"编号",
		    checkbox:true,
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
			field:"qty",
			title:"册数",
		    width:30
		},{
			field:"code",
			title:"出版社代码",
		    width:30
		},{
			field:"shortname",
			title:"出版社",
		    width:30
		}]],
		onDblClickRow: function (rowIndex,rowData) {
			$.ajax({
				type:'post',
				url:'yb/pack_cancelPack.action',
				data:'id=' + rowData.id + '&qty=' + rowData.qty,
				dataType:'json',
				success:function(data){
					$("#data1").datagrid('reload');
					$("#data2").datagrid('reload');
				},
				error:function(){
					$.messager.alert("操作提示","数据错误，联系管理员！","error");
					return;
				}
			});
	    }
	});
}
/**
 * 根据条码得到信息
 */
function loadInfoByBarcode(){
	var code = $.trim($("#code").combobox('getText'));
	if(code == ""){
		$.messager.alert("操作提示","客户不能为空！","error");
		return;
	}
	var barcode = $.trim($("#barcode").textbox('getValue'));
	if(barcode == ""){
		return;
	}
	$.ajax({
		type:'post',
		url:'yb/pack_findByBarcode.action',
		data:'code=' + code + '&barcode=' + barcode,
		dataType:'json',
		success:function(data){
			if(data.length == 1){
				var p = data[0];
				if(p){
					$("#price").textbox('setValue', p.price);
					$("#descr").textbox('setValue', p.descr);
					$("#pack_id").textbox('setValue', p.id);
					$("#qty_wait").textbox('setValue', p.qty);
				}else{
					$("#price").textbox('setValue', '');
					$("#descr").textbox('setValue', '');
					$("#pack_id").textbox('setValue','');
					$("#qty_wait").textbox('setValue','');
				}
			}else{
				for(var i in data) {
					$('#c_barcode').datagrid('insertRow',{
					    index: i+1,  
					    row: {
					        barcode: data[i].barcode,
					        descr: data[i].descr,
					        price: data[i].price,
					        qty:data[i].qty
					    }
					});
				}
				$("#showSubcode").window("open");
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 打包确认
 */
function packConfirm(){
	var code = $.trim($("#code").combobox('getText'));
	var id = $("#pack_id").textbox('getValue');
	var packwho = $("#userid").val();
	var qty = $("#qty").textbox('getValue');
	var reg = new RegExp("^[0-9]*$");
	if(addwho = "" ){
		isLogin();
		return;
	}
	if(!reg.test(qty)){
		$.messager.alert("操作提示","数量必须输入大于0的数字！","error");
		$('#qty').textbox('textbox').focus();
	}
	if(qty == "" || qty <= 0 ){
		$.messager.alert("操作提示","数量必须输入大于0的数字！","error");
		$('#qty').textbox('textbox').focus();
	}
	if(id != "") {
		$.ajax({
			type:'post',
			url:'yb/pack_confirmPack.action',
			data:'id=' + id + '&packwho=' + packwho + '&qty=' + qty,
			dataType:'json',
			success:function(data){
				if(data == "ok"){
					$("#price").textbox('setValue', '');
					$("#descr").textbox('setValue', '');
					$("#pack_id").textbox('setValue','');
					$("#barcode").textbox('setValue','');
					$("#qty").textbox('setValue','');
					addUnpackData(code);
	        		addPackedData(code);
					
				}else{
					$.messager.alert("操作提示",data,"error");
				}
			},
			error:function(){
				$.messager.alert("操作提示","数据错误，联系管理员！","error");
				return;
			}
		});
	}
}
/**
 * 取消打包
 */
function removePack(){
	var row = $('#data2').datagrid('getSelected');
	if(!row) {
		$.messager.alert("操作提示","没有选中记录","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'yb/pack_cancelPack.action',
		data:'id=' + row.id + '&qty=' + row.qty,
		dataType:'json',
		success:function(data){
			$("#data1").datagrid('reload');
			$("#data2").datagrid('reload');
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 选择表格数据确认打包
 */
function addPack(){
	var row = $('#data1').datagrid('getSelected');
	if(!row) {
		$.messager.alert("操作提示","没有选中记录","error");
		return;
	}
	var id = row.id;
	var packwho = $("#userid").val();
	if(addwho = "" ){
		isLogin();
		return;
	}
	if(id != "") {
		$("#barcode").textbox('setValue', row.barcode);
		$("#price").textbox('setValue', row.price);
		$("#descr").textbox('setValue', row.descr);
		$("#pack_id").textbox('setValue', row.id);
		$("#qty_wait").textbox('setValue', row.qty);
		$('#qty').textbox('textbox').focus();
	}
}
/**
 * 增加新批次
 */
function addBatchno(){
	$.ajax({
		type:'post',
		url:'yb/pack_getBatchno.action',
		dataType:'json',
		success:function(data){
			if(data){
				$("#addBatchno").linkbutton({disabled:true,text:data});
				$("#endBatchno").linkbutton({disabled:false});
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 结束批次
 */
function endBatchno(){
	alert($("#addBatchno").linkbutton('options').text);
	$("#addBatchno").linkbutton({disabled:false,text:'生成新批次'});
}
/**
 * 封包
 */
function addCaseid(){
	var rows = $("#data2").datagrid('getRows');
	if(!rows || rows.length == 0) {
		$.messager.alert("操作提示","没有记录","error");
		return;
	}
	var ids = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ids.push(row.id);
	}
	$.ajax({
		type:'post',
		url:'yb/pack_addCaseid.action',
		data:'id=' + ids,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data["msg"],"info",function(){
					$("#batchno").textbox('setValue',data["batchno"]);
					$("#data1").datagrid('reload');
					$("#data2").datagrid('reload');
				});
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}

/**
 * 零件清单
 */
function oddCaseList(){
	var batchno = $("#batchno").textbox('getValue');
	if(batchno != ''){
		window.open('jc/print_oddCaseListMid.action?batchno='+batchno,'_blank');
	}else{
		$.messager.alert("操作提示","请先选择批次号","error");
	}
}
/**
 * 拼包票签
 */
function oddTicketList(){
	var batchno = $("#batchno").textbox('getValue');
	if(batchno != ''){
		window.open('jc/print_oddTicketList.action?batchno='+batchno,'_blank');
	}else{
		$.messager.alert("操作提示","请先选择批次号","error");
	}
}
/**
 * 回车事件
 */
function enterKey(){
	$('#code').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	isLogin();
        	var shortname = $("#code").combobox('getValue');
        	if(shortname != ''){
        		$("#shortname").textbox('setValue',shortname);
        		$('#barcode').textbox('textbox').focus();
        		var code = $("#code").combobox('getText');
        		addUnpackData(code);
        		addPackedData(code);
        	}else{
        		$("#shortname").textbox('setValue','');
        		$("#code").combobox('setValue',' ');
        	}
        }
    });
	$('#barcode').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	loadInfoByBarcode();
        	$('#qty').textbox('textbox').focus();
        }
    });
//	$("input",$("#barcode").next("span")).blur(function(){  
//		loadInfoByBarcode();
//	}) 
	$('#qty').textbox('textbox').keydown(function (e) {
		if (e.keyCode == 13) {
			packConfirm();
		}
	});
}
/**
 * 选择图书信息
 */
function choose(){
	var row = $('#c_barcode').datagrid('getSelected');
	$("#barcode").textbox('setValue', row.barcode);
	$("#price").textbox('setValue', row.price);
	$("#descr").textbox('setValue', row.descr);
	$("#pack_id").textbox('setValue', row.id);
	$("#qty_wait").textbox('setValue', row.qty);
	$("#showSubcode").window("close");
	$('#qty').textbox('textbox').focus();
}
/**
 * 取消选择图书信息
 */
function cancel(){
	$("#showSubcode").window("close");
}
$(function(){
	$("#code").combobox('setValue',' ');
	enterKey();
	$("#endBatchno").linkbutton({disabled:true});
});

