/**
 * 加载数据
 */
function loadData(){
	$("#data").datagrid({
		url:'jc/issuenumber_info.action'
	});
}
/**
 * 添加期号
 */
function addInfo(){
	var issuenumber = $.trim($("#issuenumber").val());
	if(issuenumber == ""){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var note = $.trim($("#note").val());
	if(note == ""){
		$.messager.alert("操作提示","说明不能为空！","error");
		return;
	}
	$.ajax({
		type:"post",
		url:"jc/issuenumber_save.action",
		data:"issuenumber=" + issuenumber + "&note=" + note,
		dataType:'json',
		success:function(data){
			if(data == "ok"){
				$.messager.alert("操作提示","期号添加成功","info");
			}
			loadData();
			$("#issuenumber").textbox('setValue','')
			$("#note").textbox('setValue','')
		},
		error:function(){
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
			url:'jc/issuenumber_delete.action',
			data:'id=' + row.id,
			dataType:'json',
			success:function(data){
				if("ok" == data){
					$.messager.alert("操作提示","期号删除成功","info");
				}
				loadData();
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
 * 操作工具
 */
tool = {
		add : function (){
			$("#w-addInfo").window("open");
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