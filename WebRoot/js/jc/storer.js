/**
 * 加载数据
 */
function loadData(){
	$("#data").datagrid({
		url:'jc/storer_info.action'
	});
}
/**
 * 添加
 */
function addInfo(){
	var storerkey = $("#storerkey").textbox('getValue');
	var shortname = $("#shortname").textbox('getValue');
	var contact = $("#contact").textbox('getValue');
	var phone = $("#phone").textbox('getValue');
	var address = $("#address").textbox('getValue');
	var type = $("#type").combobox('getValue');
	if(storerkey == ""){
		$.messager.alert("操作提示","代码不能为空！","error");
		return;
	}
	if(shortname == ""){
		$.messager.alert("操作提示","名称不能为空！","error");
		return;
	}
	if(type == ""){
		$.messager.alert("操作提示","类型不能为空！","error");
		return;
	}
	$.ajax({
		type:"post",
		url:"jc/storer_save.action",
		data:"storerkey=" + storerkey + "&shortname=" + shortname + "&type=" + type + "&contact=" + contact + "&phone=" + phone + "&address=" + address,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info",function(){
					loadData();
					$("#storerkey").textbox('setValue','');
					$("#shortname").textbox('setValue','');
					$("#contact").textbox('setValue','');
					$("#phone").textbox('setValue','');
					$("#address").textbox('setValue','');
					$("#type").textbox('setValue','');
				});
			}
		},
		error:function(){
			$('#dlg').dialog('close');
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
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
			url:'jc/storer_delete.action',
			data:'id=' + row.id,
			dataType:'json',
			success:function(data){
				if(data){
					$.messager.alert("操作提示",data,"info",function(){
						loadData();
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
	var storerkey = $("#edit_storerkey").textbox('getValue');
	var shortname = $("#edit_shortname").textbox('getValue');
	var contact = $("#edit_contact").textbox('getValue');
	var phone = $("#edit_phone").textbox('getValue');
	var address = $("#edit_address").textbox('getValue');
	var type = $("#edit_type").combobox('getValue');
	if(storerkey == ""){
		$.messager.alert("操作提示","代码不能为空！","error");
		return;
	}
	if(shortname == ""){
		$.messager.alert("操作提示","名称不能为空！","error");
		return;
	}
	if(type == ""){
		$.messager.alert("操作提示","类型不能为空！","error");
		return;
	}
	$.ajax({
		type:"post",
		url:"jc/storer_edit.action",
		data:"storerkey=" + storerkey + "&shortname=" + shortname + "&type=" + type + "&id=" + id + "&contact=" + contact + "&phone=" + phone + "&address=" + address,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info",function(){
					$("#w-editInfo").window("close");
					$("#data").datagrid('reload');
				});
			}
		},
		error:function(){
			$('#dlg').dialog('close');
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
			$("#w-addInfo").window("open");
		},
		edit : function (){
			var row = $('#data').datagrid('getSelected');
			if(!row || row.length == 0) {
				$.messager.alert("操作提示","没有选中记录","error");
				return;
			}
			$("#w-editInfo").window("open");
			$("#edit_id").val(row.id);
			$("#edit_storerkey").textbox('setValue',row.storerkey);
			$("#edit_shortname").textbox('setValue',row.shortname);
			$("#edit_contact").textbox('setValue',row.contact);
			$("#edit_phone").textbox('setValue',row.phone);
			$("#edit_address").textbox('setValue',row.address);
			if(row.type == "客户"){
				$("#edit_type").combobox('setValue',0);
			}
			else if(row.type == "供商"){
				$("#edit_type").combobox('setValue',1);
			}
		},
		remove : function(){
			$.messager.confirm("操作提示", "您确定要删除这条记录吗？", function (data) {
		         if (data) {
		        	 removeInfo();
		         }else {
			         return;
			     }
		     });
		}
	}


$(function(){
	loadData();
});