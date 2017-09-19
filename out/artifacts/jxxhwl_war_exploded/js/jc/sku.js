/**
 * 加载数据
 */
function loadData(){
	$("#data").datagrid({
		url:'jc/sku_info.action'
	});
}
/**
 * 添加
 */
function addInfo(){
	var id = $("#add_id").val();
	var issuenumber = $.trim($("#issuenumber").combobox('getValue'));
	if(issuenumber == ""){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var subcode = $("#subcode").textbox('getValue');
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var barcode = $.trim($("#barcode").val());
	if(barcode == null || barcode == "" ){
		$.messager.alert("操作提示","条码不能为空！","error");
		return;
	}
	var descr = $.trim($("#descr").val());
	if(descr == null || descr == "" ){
		$.messager.alert("操作提示","书名不能为空！","error");
		return;
	}
	var addwho = $.trim($("#addwho").val());
	if(addwho == null || addwho == ""){
		$.messager.alert("操作提示","添加人不能为空！","error");
		return;
	}
	var publisher = $.trim($("#publisher").combobox('getValue'));
	if(publisher == null || publisher == "" ){
		$.messager.alert("操作提示","出版社不能为空！","error");
		return;
	}
	var price = $.trim($("#price").val());
	if(price == null || price == "" ){
		price = 0;
	}
	var reg = new RegExp("^[0-9]*$");
	var reg2 = new RegExp("^\\d+(\\.\\d+)?$");
	if(!reg2.test(price)){
		$.messager.alert("操作提示","定价只能输入数字！","error");
		return;
	}
	var param = "issuenumber=" + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode + "&descr=" + descr + "&price=" + price + "&pack=0" + "&publisher=" + publisher + "&addwho=" + addwho + "&editwho=" + addwho + "&bundle=2" + "&id=" + id;      
	$.ajax({
		type:"post",
		url:"jc/sku_save.action",
		data:param,
		dataType:'json',
		success:function(data){
			$.messager.alert("操作提示",data,"info");
			$("#data").datagrid('reload');
			$("#add_id").val("");
			$("#subcode").textbox('setValue','');
			$("#barcode").textbox('setValue','');
			$("#descr").textbox('setValue','');
			$("#publisher").combobox('setValue','');
			$("#price").textbox('setValue','');
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
function removeInfo(){
	var row = $('#data').datagrid('getSelected');
	if(row){
		$.ajax({
			url:'jc/sku_delete.action',
			data:'id=' + row.id,
			dataType:'json',
			success:function(data){
				if(data){
					$.messager.alert("操作提示",data,"info");
					$("#data").datagrid('reload');
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
 * 设置修改的值
 */
function setRowValue(){
	var row = $('#data').datagrid('getSelected');
	if(row){
		$("#oldId").val(row.id);
		$("#issuenumber1").combobox("setValue",row.issuenumber);
		$("#subcode1").textbox('setValue',row.subcode);
		$("#barcode1").textbox('setValue',row.barcode);
		$("#descr1").textbox('setValue',row.descr);
		$("#publisher1").combobox('setValue',row.publisher);
		$("#price1").textbox('setValue',row.price);
		$("#pack1").textbox('setValue',row.pack);
		$("#edit_bundle").textbox('setValue',row.bundle);
	}
}
/**
 * 提交修改的信息
 * @returns
 */
function editInfo(){
	var id = $("#oldId").val();
	var issuenumber = $.trim($("#issuenumber1").combobox('getValue'));
	if(issuenumber == ""){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var subcode = $.trim($("#subcode1").val());
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var barcode = $.trim($("#barcode1").val());
	if(barcode == null || barcode == "" ){
		$.messager.alert("操作提示","条码不能为空！","error");
		return;
	}
	var descr = $.trim($("#descr1").val());
	if(descr == null || descr == "" ){
		$.messager.alert("操作提示","书名不能为空！","error");
		return;
	}
	var pack = $.trim($("#pack1").val());
	if(pack == null || pack == ""){
		pack = 9999;
	}
	var addwho = $.trim($("#addwho1").val());
	if(addwho == null || addwho == ""){
		$.messager.alert("操作提示","修改人不能为空！","error");
		return;
	}
	var publisher = $.trim($("#publisher1").combobox('getValue'));
	if(publisher == null || publisher == "" ){
		$.messager.alert("操作提示","出版社不能为空！","error");
		return;
	}
	var price = $.trim($("#price1").val());
	if(price == null || price == "" ){
		price = 0;
	}
	var reg = new RegExp("^[0-9]*$");
	var reg2 = new RegExp("^\\d+(\\.\\d+)?$");
	if(!reg2.test(price)){
		$.messager.alert("操作提示","定价只能输入数字！","error");
		return;
	}
	if(!reg.test(pack)){
		$.messager.alert("操作提示","捆扎只能输入数字！","error");
		return;
	}
	var bundle = $.trim($("#edit_bundle").val());
	if(bundle == null || bundle == ""){
		$.messager.alert("操作提示","每包捆数不能为空！","error");
		return;
	}
	if(!reg.test(bundle)){
		$.messager.alert("操作提示","每包捆数只能输入数字！","error");
		return;
	}
	var param = "id=" + id + "&issuenumber=" + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode + "&descr=" + descr + "&price=" + price + "&pack=" + pack+ "&publisher=" + publisher + "&addwho=" + addwho + "&editwho=" + addwho +"&bundle=" + bundle ;      
	$.ajax({
		type:"post",
		url:"jc/sku_update.action",
		data:param,
		dataType:'json',
		success:function(data){
			$.messager.alert("操作提示",data,"info");
			$("#w-editInfo").window("close");
			loadData();
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
function queryInfo(){
	var issuenumber = $.trim($("#query_issuenumber").combobox('getValue'));
	var subcode = $.trim($("#query_subcode").val());
	var barcode = $.trim($("#query_barcode").val());
	var descr = $.trim($("#query_descr").val());
	var addwho = $.trim($("#query_addwho").val());
	var publisher = $.trim($("#query_publisher").combobox('getValue'));
	var adddate = $("#query_adddate").datebox('getValue');
	if(publisher == "" && subcode == "" && barcode == "" && descr == "" && issuenumber == "" && addwho =="" && adddate == ""){
		loadData();
		$("#w-queryInfo").window("close");
		return;
	}
	var param = "issuenumber=" + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode + "&descr=" + descr + "&publisher=" + publisher + "&addwho=" + addwho + "&adddate=" + adddate;      
	
	$("#data").datagrid({
		url:'jc/sku_query.action',
		method:'POST',
		queryParams: { 'issuenumber': issuenumber,'subcode': subcode,'barcode': barcode,'publisher': publisher,'descr': descr,'adddate':adddate,'addwho':addwho}
	});
	
	$("#w-queryInfo").window("close");
	
}
/**
 * 添加捆扎信息
 */
function addPackInfo(){
	var issuenumber = $("#addPack_issuenumber").combobox("getValue");
	var subcode = $("#addPack_subcode").textbox("getValue");
	var pack = $("#addPack_pack").textbox("getValue");
	var addwho = $("#addPack_addwho").textbox("getValue");
	if(pack == ''){
		$.messager.alert("操作提示","捆扎数不能为空！","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'jc/pack_save.action',
		data:"issuenumber=" + issuenumber + "&subcode=" + subcode + "&pack=" + pack + "&addwho=" + addwho,
		dataType:'json',
		success:function(data){
			$.messager.alert("操作提示",data,"info");
			$("#w-addPackInfo").window("close");
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 查询捆扎信息
 */
function queryPack(){
	var row = $('#data').datagrid('getSelected');
	var issuenumber = row.issuenumber;
	var subcode = row.subcode;
	$(".showPackTab").datagrid({
		url:'jc/pack_query.action?issuenumber=' + issuenumber + "&subcode=" + subcode,
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		singleSelect:true,
		border:true,
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
			field:"pack",
			title:"捆扎",
		    width:30
		}]]
	});
	$("#w-showPackInfo").window("open");
}
/**
 * 删除捆扎
 */
function removePack(){
	var row = $('.showPackTab').datagrid('getSelected');
	$.ajax({
		type:'post',
		url:'jc/pack_delete.action',
		data:'issuenumber=' + row.issuenumber + '&subcode=' + row.subcode + '&pack=' + row.pack,
		dataType:'json',
		success:function(data){
			$.messager.alert("操作提示",data,"info");
			$(".showPackTab").datagrid('reload');
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 根据期号和征订代码带出信息
 */
function loadInfoByBarcode(){
	var issuenumber = $("#issuenumber").textbox('getValue');
	var subcode = $("#subcode").textbox('getValue');
	if(subcode == ""){
		return;
	}
	$.ajax({
		type:'post',
		url:'jc/sku_query.action',
		data:'issuenumber=' + issuenumber + '&subcode=' + subcode,
		dataType:'json',
		success:function(data){
			if(data){
				var a = data["rows"][0];
				if(a != null){
					$("#add_id").val(a.id);
					$("#issuenumber").textbox('setValue',a.issuenumber);
					$("#subcode").textbox('setValue',a.subcode);
					$("#barcode").textbox('setValue',a.barcode);
					$("#descr").textbox('setValue',a.descr);
					$("#publisher").combobox('setValue',a.publisher);
					$("#price").textbox('setValue',a.price);
				}else{
					$("#add_id").val('');
					$("#barcode").textbox('setValue','');
					$("#descr").textbox('setValue','');
					$("#publisher").combobox('setValue','');
					$("#price").textbox('setValue','');
				}
			}else{
				$("#add_id").val('');
				$("#barcode").textbox('setValue','');
				$("#descr").textbox('setValue','');
				$("#publisher").combobox('setValue','');
				$("#price").textbox('setValue','');
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 修改捆扎
 */
function updatePackInfo(){
	var id = $("#editpack_id").val();
	var oldpack = $("#oldpack").val();
	var issuenumber = $("#updatepack_issuenumber").textbox('getValue');
	var subcode =$("#updatepack_subcode").textbox('getValue');
	var pack =$("#updatepack_pack").textbox('getValue');
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(pack)){
		$.messager.alert("操作提示","捆扎只能输入数字！","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'jc/pack_updatePack.action',
		data:'issuenumber=' + issuenumber + '&subcode=' + subcode + '&pack=' + pack + "&id=" +id + "&oldpack=" + oldpack,
		dataType:'json',
		success:function(data){
			$.messager.alert("操作提示",data,"info");
			$("#w_updatePackInfo").window('close');
			$("#data").datagrid('reload');
			$(".showPackTab").datagrid('reload');
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
			$("#add_id").val('');
			$("#issuenumber").val('setValue','');
			$("#subcode").textbox('setValue','');
			$("#barcode").textbox('setValue','');
			$("#descr").textbox('setValue','');
			$("#publisher").combobox('setValue','');
			$("#price").textbox('setValue','');
			$("#w-addInfo").window("open");
		},
		updatePack : function (){
			var row = $('.showPackTab').datagrid('getSelected');
			$("#updatepack_issuenumber").textbox('setValue',row.issuenumber);
			$("#updatepack_subcode").textbox('setValue',row.subcode);
			$("#updatepack_pack").textbox('setValue',row.pack);
			$("#editpack_id").val(row.id);
			$("#oldpack").val(row.pack);
			$("#w_updatePackInfo").window('open');
		},
		remove : function(){
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
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#w-editInfo").window("open");
			setRowValue();
		},
		addPack : function (){
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#addPack_issuenumber").combobox("setValue",row.issuenumber);
			$("#addPack_subcode").textbox('setValue',row.subcode);
			$("#addPack_barcode").textbox('setValue',row.barcode);
			$("#addPack_descr").textbox('setValue',row.descr);
			$("#w-addPackInfo").window("open");
		},
		searchPack : function (){
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			queryPack();
		},
		removePack : function (){
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			removePack();
		}
	}

$(function(){
	loadData();
	$("input",$("#subcode").next("span")).blur(function(){  
		loadInfoByBarcode();
	});
	$('#subcode').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	loadInfoByBarcode();
        }
    });
});