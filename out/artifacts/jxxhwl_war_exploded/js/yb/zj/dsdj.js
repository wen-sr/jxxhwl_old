/**
 * 添加未打包数据
 * @param data
 */
function loadData(code){
	$("#data").datagrid({
		url:'yb/zj_loadData.action',
		height:'auto',
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:false,
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
			field:"sku",
			title:"书号",
		    width:30
		},{
			field:"tray",
			title:"托盘号",
		    width:30
		},{
			field:"addwho",
			title:"添加人",
		    width:30
		},{
			field:"adddate",
			title:"添加时间",
		    width:30
		}]]
	});
	
    
}

/**
 * 根据条码得到信息
 */
function loadInfoByBarcode(){
	var barcode = $.trim($("#barcode").textbox('getValue'));
	if(barcode == ""){
		return;
	}
	$.ajax({
		type:'post',
		url:'yb/zj_findByBarcode.action',
		data:'barcode=' + barcode,
		dataType:'json',
		success:function(data){
			if(data.length == 1){
				var p = data[0];
				if(p){
					$("#price").textbox('setValue', p.price);
					$("#descr").textbox('setValue', p.descr);
					$("#sku").textbox('setValue', p.sku);
				}else{
					$("#price").textbox('setValue', '');
					$("#descr").textbox('setValue', '');
					$("#sku").textbox('setValue', '');
				}
			}else{
				$('#c_barcode').datagrid('loadData', { total: 0, rows: [] });
				for(var i in data) {
					$('#c_barcode').datagrid('insertRow',{
					    index: i+1,  
					    row: {
					        barcode: data[i].barcode,
					        descr: data[i].descr,
					        price: data[i].price,
					        sku:data[i].sku
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
 * 回车事件
 */
function enterKey(){
	$('#barcode').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	loadInfoByBarcode();
        	$('#qty').textbox('textbox').focus();
        }
    });
	$('#qty').textbox('textbox').keydown(function (e) {
		if (e.keyCode == 13) {
			$('#tray').textbox('textbox').focus();
		}
	});
	$('#tray').textbox('textbox').keydown(function (e) {
		if (e.keyCode == 13) {
			  $.messager.confirm("操作提示", "您确定提交？", function (data) {  
		            if (data) {  
		               confirm();
		            }  
		            else {  
		               return;
		            }  
		        });  
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
	$("#sku").textbox('setValue', row.sku);
	$("#showSubcode").window("close");
	$('#qty').textbox('textbox').focus();
}
/**
 * 取消选择图书信息
 */
function cancel(){
	$("#showSubcode").window("close");
}
/**
 * 提交
 */
function confirm(){
	var barcode = $("#barcode").textbox('getValue');
	var sku = $("#sku").textbox('getValue');
	var descr = $("#descr").textbox('getValue');
	var price = $("#price").textbox('getValue');
	var qty = $("#qty").textbox('getValue');
	var tray = $("#tray").textbox('getValue');
	var addwho = $("#userid").val();
	if(barcode == '' || barcode == null ){
		$.messager.alert("操作提示","条码不能为空","error");
		return;
	}
	if(sku == '' || sku == null ){
		$.messager.alert("操作提示","书号不能为空","error");
		return;
	}
	if(price == '' || price == null ){
		$.messager.alert("操作提示","定价不能为空","error");
		return;
	}
	if(qty == '' || qty == null ){
		$.messager.alert("操作提示","数量不能为空","error");
		return;
	}
	if(descr == '' || descr == null ){
		$.messager.alert("操作提示","书名不能为空","error");
		return;
	}
	if(tray == '' || tray == null ){
		$.messager.alert("操作提示","托盘号不能为空","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'yb/zj_add.action',
		data:'barcode=' + barcode + '&sku=' + sku + '&descr=' + descr + '&price=' + price + '&qty=' + qty + '&tray=' + tray + '&addwho=' + addwho,
		dataType:'json',
		success:function(data){
			if("ok" == data){
				$("#data").datagrid('reload');
				$("#price").textbox('setValue', '');
				$("#descr").textbox('setValue', '');
				$("#sku").textbox('setValue', '');
				$("#barcode").textbox('setValue', '');
				$("#qty").textbox('setValue', '');
				$('#barcode').textbox('textbox').focus();
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
/**
 * 删除
 */
function deleteDetail(){
	var row = $('#data').datagrid('getSelected');
	if(row){
		$.ajax({
			type:'post',
			url:'yb/zj_delete.action',
			data:'id=' + row.id,
			dataType:'json',
			success:function(data){
				if(data == "ok"){
					$("#data").datagrid('reload');
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
$(function(){
	loadData();
	enterKey();
});

