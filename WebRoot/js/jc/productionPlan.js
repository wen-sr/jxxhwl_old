/**
 * 加载数据
 */
function loadData(){
	$("#data").datagrid({
		url:'jc/pp_findAll.action'
	});
}

function bb(){
	var batchno = $.trim($("#batchno").textbox('getValue'));
	var subcode = $.trim($("#subcode").textbox('getValue'));
	var deliverDate = $("#deliverDate").datebox('getValue');
	var deliverDateEnd = $("#deliverDateEnd").datebox('getValue');
	var switchButton = $("#switch").switchbutton("options").checked;
	$("#data").datagrid({
		url:'jc/pp_findAll.action',
		method:'post',
		queryParams:{"batchno" :  batchno , "subcode" :  subcode , "deliverDate" :  deliverDate, "deliverDateEnd" : deliverDateEnd ,"switchButton" : switchButton }
	});
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
		$("#edit_plantingName").textbox("setValue",row.plantingName);
		$("#edit_subcode").textbox('setValue',row.subcode);
		$("#edit_price").textbox('setValue',row.price);
		$("#edit_descr").textbox('setValue',row.descr);
		$("#edit_publisher").textbox('setValue',row.publisher);
		$("#edit_batchno").textbox('setValue',row.batchno);
		$("#edit_handBagDate").datebox('setValue',row.handBagDate);
		$("#edit_type").combobox('setValue',row.type);
		$("#edit_remark").textbox('setValue',row.remark);
		$("#edit_status").val(row.status);
	}
}
/**
 * 提交修改的信息
 * @returns
 */
function editInfo(){
	var id = $("#oldId").val();
	var status = $("#edit_status").val();
	
	var batchno = $.trim($("#edit_batchno").textbox('getValue'));
	var subcode = $.trim($("#edit_subcode").textbox('getValue'));
	var plantingName = $.trim($("#edit_plantingName").textbox('getValue'));
	var publisher = $.trim($("#edit_publisher").textbox('getValue'));
	var handBagDate = $.trim($("#edit_handBagDate").datebox('getValue'));
	var type = $.trim($("#edit_type").textbox('getValue'));
	if(type == "请选择") type = "";
	var remark = $.trim($("#edit_remark").textbox('getValue'));
	var addwho = $.trim($("#edit_addwho").textbox('getValue'));
	if(addwho == ""){
		$.messager.alert("操作提示","您还未登录，无法提交","error");
		$("#w-editInfo").window("close");
		return;
	}
	if(status == 1 ){
		if(addwho.toUpperCase() != "LH07003"){
			$.messager.alert("操作提示","您没有权限修改已经交包的记录","error");
			$("#w-editInfo").window("close");
			return;
		}
	}
	var param = "id=" + id + "&batchno=" + batchno + "&subcode=" + subcode + "&plantingName=" + plantingName + "&publisher=" + publisher + "&handBagDate=" + handBagDate + "&type=" + type + "&addwho=" + addwho + "&remark=" + remark;      
	$.ajax({
		type:"post",
		url:"jc/pp_update.action",
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
	var batchno = $.trim($("#query_batchno").textbox('getValue'));
	var subcode = $.trim($("#query_subcode").textbox('getValue'));
	var deliverDate = $("#query_deliverDate").datebox('getValue');
	var deliverDateEnd = $("#query_deliverDateEnd").datebox('getValue');
	var plantingName = $.trim($("#query_plantingName").textbox('getValue'));
	var publisher = $.trim($("#query_publisher").textbox('getValue'));
	var handBagDate = $.trim($("#query_handBagDate").datebox('getValue'));
	var handBagDateEnd = $.trim($("#query_handBagDateEnd").datebox('getValue'));
	var status = $.trim($("#query_status").textbox('getValue'));
	if(status == "请选择") status = "";
	var type = $.trim($("#query_type").textbox('getValue'));
	if(type == "请选择") type = "";
	var remark = $.trim($("#query_remark").textbox('getValue'));
	var addwho = $.trim($("#query_addwho").combobox('getValue'));
	
	
	if(batchno == "" && subcode == "" && deliverDate == "" && deliverDateEnd == "" && plantingName == "" && publisher =="" && handBagDate == "" 
		&& handBagDateEnd =="" && status == "" && type =="" && remark == "" && addwho ==""){
		$("#w-queryInfo").window("close");
		return;
	}
	
	$("#data").datagrid({
		url:'jc/pp_findAll.action',
		method:'post',
		queryParams:{"batchno" :  batchno , "subcode" :  subcode , "deliverDate" :  deliverDate 
			, "deliverDateEnd" :  deliverDateEnd , "plantingName" :  plantingName , "publisher" 
			: publisher , "handBagDate" :  handBagDate , "handBagDateEnd" :  handBagDateEnd 
			, "status" :  status , "type" :  type , "remark" :  remark , "addwho" :  addwho}
	});
	
	$("#w-queryInfo").window("close");
	
}

/**
 * 操作工具
 */
tool = {
		query : function (){
			$("#w-queryInfo").window("open");
		},
		edit : function(){
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#w-editInfo").window("open");
			setRowValue();
		}
	}

$(function(){
	loadData();
	$('#data').datagrid({
		//双击事件
		onDblClickRow : function(index,row){
			$("#w-editInfo").window("open");
			setRowValue();
		}
	});
});