/**
 * 载入员工信息
 */
function loadUserInfo(){
	$("#data").datagrid({
		url:'user_loadUser.action',
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
			field:"",
		    title:"编号",
		    checkbox:true,
		    width:50
		},{
			field:"id",
			title:"工号",
		    width:50
		},{
			field:"username",
			title:"姓名",
		    width:50
		},{
			field:"departname",
			title:"部门",
		    width:50
		},{
			field:"py",
			title:"拼音",
		    width:50
		},{
			field:"status",
			title:"状态",
		    width:50
		}]]
	});
}
/**
 * 查询
 */
function go(){
	var id = $("#uid").textbox('getValue');
	var username=$("#uname").textbox('getValue');
	var departId = $("#department").combobox('getValue');
	var status = $("#status").combobox('getValue');
	$("#data").datagrid({
		url:'user_loadUser.action',
		queryParams:{
			"id":id,
			"username":username,
			"departId":departId,
			"status":status
		},
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:true,
        pageSize:20,
        pageList:[10,15,20,1000],
		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"",
		    title:"编号",
		    checkbox:true,
		    width:50
		},{
			field:"id",
			title:"工号",
		    width:50
		},{
			field:"username",
			title:"姓名",
		    width:50
		},{
			field:"departname",
			title:"部门",
		    width:50
		},{
			field:"py",
			title:"拼音",
		    width:50
		},{
			field:"status",
			title:"状态",
		    width:50
		}]]
	});
}
/**
 * 添加
 */
function addInfo(){
	var id = $("#add_id").textbox('getValue');
	var username=$("#add_username").textbox('getValue');
	var py=$("#add_py").textbox('getValue');
	var departId = $("#add_departId").combobox('getValue');
	var departname = $("#add_departId").combobox('getText');
	if(id == ""){
		$.messager.alert("操作提示","工号不能为空","error");
		return;
	}
	if(username == ""){
		$.messager.alert("操作提示","姓名不能为空","error");
		return;
	}
	if(departId == ""){
		$.messager.alert("操作提示","部门不能为空","error");
		return;
	}
	if(py == ""){
		$.messager.alert("操作提示","拼音首字母不能为空","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'user_addInfo.action',
		data:'id=' + id + "&username=" + username + "&py=" + py + "&departId=" + departId + "&departname=" + departname,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info");
				var id = $("#add_id").textbox('setValue','');
				var username=$("#add_username").textbox('setValue','');
				var py=$("#add_py").textbox('setValue','');
				var departId = $("#add_departId").combobox('setValue','');
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
	var id = $("#edit_id").textbox('getValue');
	var username=$("#edit_username").textbox('getValue');
	var py=$("#edit_py").textbox('getValue');
	var departId = $("#edit_departId").combobox('getValue');
	var departname = $("#edit_departId").combobox('getText');
	var status = $("#edit_status").combobox('getValue');
	if(id == ""){
		$.messager.alert("操作提示","工号不能为空","error");
		return;
	}
	if(username == ""){
		$.messager.alert("操作提示","姓名不能为空","error");
		return;
	}
	if(departId == ""){
		$.messager.alert("操作提示","部门不能为空","error");
		return;
	}
	if(py == ""){
		$.messager.alert("操作提示","拼音首字母不能为空","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'user_editInfo.action',
		data:'id=' + id + "&username=" + username + "&py=" + py + "&departId=" + departId + "&status=" + status + "&departname=" + departname,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info");
				var id = $("#edit_id").textbox('setValue','');
				var username=$("#edit_username").textbox('setValue','');
				var py=$("#edit_py").textbox('setValue','');
				var departId = $("#edit_departId").combobox('setValue','');
				var edit_status = $("#edit_status").combobox('setValue','');
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
		url:'user_deleteInfo.action',
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
/**
 * 查看当前拥有的角色
 */
function getAuth(){
	var row = $("#data").datagrid('getSelected');
	$.ajax({
		type:'post',
		url:'user_getAuth.action',
		data:'id='+ row.id,
		dataType:'json',
		success:function(data){
			$("#my_auth").empty();
			$("#getAuth").window("open");
			if(data){
				var html = "";
				for(var i in data){
					html += data[i];
					html += "<br/>"
				}
				$("#my_auth").append(html);
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 提交角色
 */
function confirmRole(){
	var role_id = $("#role_id").combobox('getValues');
	var row = $("#data").datagrid('getSelected');
	$.ajax({
		type:'post',
		url:'role_updateRole.action',
		data:'login_id=' + row.id + "&name=" + role_id,
		dataType:'json',
		success:function (data){
			$("#auth").window("close");
			$.messager.alert("操作提示",data,"info");
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
			var id = $("#edit_id").textbox('setValue',row.id);
			var username=$("#edit_username").textbox('setValue',row.username);
			var py=$("#edit_py").textbox('setValue',row.py);
			var departId = $("#edit_departId").combobox('setValue',row.departId);
			if(row.status == "已激活"){
				$("#edit_status").combobox('setValue','1');
			}else{
				$("#edit_status").combobox('setValue','0');
			}
			$("#w_editInfo").window("open");
		},
		remove : function(){
			var row = $("#data").datagrid('getSelected');
			if(!row){
				$.messager.alert("操作提示","没有选中需要删除的数据","error");
				return;
			}
			$.messager.confirm("操作提示", "您确定要删除这个员工吗？", function (data) {  
	            if (data) {  
	            	remove();
	            }  
	            else {  
	                return; 
	            }  
	        });
		},
		getAuth : function (){
			var row = $("#data").datagrid('getSelected');
			if(!row){
				$.messager.alert("操作提示","没有选中数据","error");
				return;
			}
			getAuth();
		},
		editAuth : function (){
			var row = $("#data").datagrid('getSelected');
			if(!row){
				$.messager.alert("操作提示","没有选中数据","error");
				return;
			}
			$("#auth").window("open");
		}
		
}

$(function(){
	loadUserInfo();
});