/**
 * 载入员工信息
 */
function loadUserInfo(){
	$("#data").datagrid({
		url:'department_loadDepartment.action',
		height:'auto',
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"",
		    title:"编号",
		    checkbox:true,
		    width:50
		},{
			field:"id",
			title:"部门编号",
		    width:50
		},{
			field:"name",
			title:"部门名称 ",
		    width:50
		}]]
	});
}

/**
 * 添加
 */
function addInfo(){
	var name = $("#add_name").combobox('getText');
	if(name == ""){
		$.messager.alert("操作提示","部门不能为空","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'department_addInfo.action',
		data:'name=' + name,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info");
				var departId = $("#add_name").combobox('setValue','');
				$("#w_addInfo").window("close");
				$("#data").datagrid('reload');
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 修改
 */
function editInfo(){
	var id = $("#edit_id").val();
	var name = $("#edit_name").combobox('getText');
	$.ajax({
		type:'post',
		url:'department_editInfo.action',
		data:'id=' + id + "&name=" + name,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info");
				$("#edit_id").val('');
				$("#edit_name").combobox('setValue','');
				$("#w_editInfo").window("close");
				$("#data").datagrid('reload');
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
function remove(){
	var row = $("#data").datagrid('getSelected');
	$.ajax({
		type:'post',
		url:'department_deleteInfo.action',
		data:'id=' + row.id,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info");
				$("#data").datagrid('reload');
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}

tool = {
		add : function(){
			$("#w_addInfo").window("open");
		},
		edit: function (){
			var row = $("#data").datagrid('getSelected');
			if(!row){
				$.messager.alert("操作提示","没有选中需要修改的数据","error");
				return;
			}
			$("#edit_id").val(row.id);
			$("#edit_name").combobox('setValue',row.id);
			$("#w_editInfo").window("open");
		},
		remove : function(){
			var row = $("#data").datagrid('getSelected');
			if(!row){
				$.messager.alert("操作提示","没有选中需要删除的数据","error");
				return;
			}
			$.messager.confirm("操作提示", "您确定要删除这个部门吗？", function (data) {  
	            if (data) {  
	            	remove();
	            }  
	            else {  
	                return; 
	            }  
	        });
		}
}

$(function(){
	loadUserInfo();
});