/**
 * 开始打包
 */
function beginPack(){
	var issuenumber = $("#issuenumber").combobox("getValue");
	var code = $("#code").combobox("getValue");
	if(code == "") {
		$.messager.alert("操作提示","请先选择需要打包的客户","error");
		return;
	}
	addUnpackData(issuenumber,code);
	addPackedData(issuenumber,code);
}
/**
 * 添加未打包数据
 * @param data
 */
function addUnpackData(issuenumber,code){
	$("#data1").datagrid({
		url:'jc/oddPack_loadUnpackData.action?issuenumber=' + issuenumber + '&code=' + code,
		height:'auto',
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:false,
		pagination:true,
        pageSize:10,
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
			title:"分发数量",
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
    //0 : 表示需发界面   1 ： 表示已发界面
    // $("#currentType").val("0");

}
/**
 * 添加已刷未封数据
 * @param data
 */
function addPackedData(issuenumber,code) {
	$("#data2").datagrid({
		url:'jc/oddPack_loadPackedData.action?issuenumber=' + issuenumber + '&code=' + code,
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:false,
		pagination:true,
        pageSize:10,
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
			title:"分发数量",
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
    //0 : 表示需发界面   1 ： 表示已发界面
    // $("#currentType").val("0");
}
/**
 * 根据条码得到信息
 */
function loadInfoByBarcode(){
	var issuenumber = $.trim($("#issuenumber").combobox('getValue'));
	var code = $.trim($("#code").combobox('getValue'));
	if(code == ""){
		$.messager.alert("操作提示","客户不能为空！","error");
		return;
	}
	var subcode = $.trim($("#subcode").textbox('getValue'));
	if(subcode == ""){
		return;
	}
	$.ajax({
		type:'post',
		url:'jc/oddPack_findByBarcode.action',
		data:'issuenumber=' + issuenumber + '&code=' + code + '&barcode=' + subcode,
		dataType:'json',
		success:function(data){
			if(data){
				var p = data[0];
				if(p){
					$("#price").textbox('setValue', p.price);
					$("#qty").textbox('setValue', p.qtyallocated);
					$("#descr").textbox('setValue', p.descr);
					$("#pack_id").textbox('setValue', p.id);
					$("#publisher").combobox('setValue', p.publisher);

                    $('#qty').next('span').find('input').focus();

				}else{
					$("#price").textbox('setValue', '');
					$("#qty").textbox('setValue', '');
					$("#descr").textbox('setValue', '');
					$("#pack_id").textbox('setValue','');
					$("#publisher").combobox('setValue', '');
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
 * 打包确认
 */
function packConfirm(){
	var issuenumber = $("#issuenumber").combobox('getValue');
	var id = $("#pack_id").textbox('getValue');
	var packwho = $("#userid").val();
	if(addwho = "" ){
		isLogin();
		return;
	}
	if(id != "") {
		$.ajax({
			type:'post',
			url:'jc/oddPack_confirmPack.action',
			data:'id=' + id + '&packwho=' + packwho,
			dataType:'json',
			success:function(data){
				// $.messager.alert("操作提示",data,"info");
				beginPack();
				$("#subcode").textbox('setValue', '');
				$("#price").textbox('setValue', '');
				$("#qty").textbox('setValue', '');
				$("#descr").textbox('setValue', '');
				$("#pid").textbox('setValue', '');
				$("#publisher").combobox('setValue', '');

                $('#subcode').next('span').find('input').focus();
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
	var rows = $('#data2').datagrid('getSelections');
	if(!rows || rows.length == 0) {
		$.messager.alert("操作提示","没有选中记录","error");
		return;
	}
	var ids = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ids.push(row.id);
	}
	$.ajax({
		type:'post',
		url:'jc/oddPack_cancelPack.action',
		data:'id=' + ids,
		dataType:'json',
		success:function(data){
			$.messager.alert("操作提示",data,"info");
			beginPack();
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
	var rows = $('#data1').datagrid('getSelections');
	if(!rows || rows.length == 0) {
		$.messager.alert("操作提示","没有选中记录","error");
		return;
	}
	if(rows.length > 1) {
		$.messager.alert("操作提示","一次只能修改一条记录","error");
		return;
	}
	var id = rows[0].id;
	var packwho = $("#userid").val();
	if(addwho = "" ){
		isLogin();
		return;
	}
	if(id != "") {
		$.ajax({
			type:'post',
			url:'jc/oddPack_confirmPack.action',
			data:'id=' + id + '&packwho=' + packwho,
			dataType:'json',
			success:function(data){
				$.messager.alert("操作提示",data,"info");
				beginPack();
			},
			error:function(){
				$.messager.alert("操作提示","数据错误，联系管理员！","error");
				return;
			}
		});
	}
}
/**
 * 生成批次
 */
function addBatchno(){
	var rows = $("#data2").datagrid('getRows');
	if(!rows || rows.length == 0) {
		$.messager.alert("操作提示","没有选中记录","error");
		return;
	}
	var ids = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ids.push(row.id);
	}
	$.ajax({
		type:'post',
		url:'jc/oddPack_addBatchno.action',
		data:'id=' + ids,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data["msg"],"info",function(){
					$("#batchno").textbox('setValue',data["batchno"]);
					beginPack();
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

$(function(){
	$("input",$("#subcode").next("span")).blur(function(){  
		loadInfoByBarcode();
	});
	$('#subcode').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            loadInfoByBarcode();
        }
    });

    $('#qty').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            packConfirm();
        }
    });

});
