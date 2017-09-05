/**
 * 载入员工信息
 */
function loadUserInfo(){
	$("#data").datagrid({
		url:'role_loadRole.action',
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
			title:"角色编号",
		    width:50
		},{
			field:"name",
			title:"角色名称 ",
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
		url:'role_addInfo.action',
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
		url:'role_editInfo.action',
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
		url:'role_deleteInfo.action',
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
			$.messager.confirm("操作提示", "您确定要删除这个角色吗？", function (data) {  
	            if (data) {  
	            	remove();
	            }  
	            else {  
	                return; 
	            }  
	        });
		},
		auth : function (){
			var row = $("#data").datagrid('getSelected');
			if(!row){
				$.messager.alert("操作提示","没有选中数据","error");
				return;
			}
			auth();
			$("#w_auth").window("open");
		}
		
		
}

$(function(){
	loadUserInfo();
});
/**
 * 显示权限树
 */
function auth(){
	var row = $("#data").datagrid('getSelected');
	$("#cc").tree({
		url:'role_getAuth.action?id=' + row.id
	});
	
}
/**
 * 提交权限
 */
function comfirmAuth(){
	var row = $("#data").datagrid('getSelected');
	var nodes = $('#cc').tree('getChecked');
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if (s != '') s += ',';
		s += nodes[i].id;
	}
	$.ajax({
		type:'post',
		url:'role_comfirmAuth.action',
		data:'name=' + s + "&id=" + row.id,
		dataType:'json',
		success:function(data){
			if(data){
				$("#w_auth").window("close");
				$.messager.alert("操作提示",data,"info");
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	
	});
}
