/**
 * 载入已登记的信息
 */
function loadData(){
	$("#data").datagrid({
		url:'yb/bZChuHuo_loadData.action',
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
			field:"id",
		    title:"编号",
		    checkbox:true,
		    width:50
		},{
			field:"faCase",
			title:"发货件数",
		    width:50
		},{
			field:"tuiCase",
			title:"退货件数",
		    width:50
		},{
			field:"zpRej",
			title:"主配拒收件数",
		    width:50
		},{
			field:"tuiRej",
			title:"退货拒收件数",
		    width:50
		},{
			field:"dzz",
			title:"大中专",
		    width:50
		},{
			field:"sgFa",
			title:"手工发货件数",
		    width:50
		},{
			field:"note",
			title:"备注",
		    width:30
		},{
			field:"dd",
			title:"日期",
		    width:30
		},{
			field:"addwho",
			title:"登记人",
		    width:30
		}]]
	});
}
/**
 * 添加
 */
function add(){
	$("#w_addInfo").window("open");
}
/**
 * 保存添加信息
 */
function addInfo(){
	var dd = $("#dd").datebox('getValue');
	var faCase = $("#faCase").textbox("getValue");
	var tuiCase = $("#tuiCase").textbox("getValue");
	var zpRej = $("#zpRej").textbox("getValue");
	var tuiRej = $("#tuiRej").textbox("getValue");
	var sgFa = $("#sgFa").textbox("getValue");
	var dzz = $("#dzz").textbox("getValue");
	var addwho = $("#addwho").textbox("getValue");
	var note = $("#note").textbox("getValue");
	var reg = new RegExp("^[0-9]*$");
	if('' == dd) {
		$.messager.alert("操作提示","日期不能为空！","error");
		return;
	}
	if('' == faCase || !reg.test(faCase)){
		$.messager.alert("操作提示","发货件数只能输入数字！","error");
		return;
	}
	if('' == tuiCase || !reg.test(tuiCase)){
		$.messager.alert("操作提示","退货件数只能输入数字！","error");
		return;
	}
	if('' == zpRej || !reg.test(zpRej)){
		$.messager.alert("操作提示","主配拒收件数只能输入数字！","error");
		return;
	}
	if('' == tuiRej || !reg.test(tuiRej)){
		$.messager.alert("操作提示","退货拒收件数只能输入数字！","error");
		return;
	}
	if('' == sgFa || !reg.test(sgFa)){
		$.messager.alert("操作提示","手工发货件数只能输入数字！","error");
		return;
	}
	if('' == dzz || !reg.test(dzz)){
		$.messager.alert("操作提示","大中专只能输入数字！","error");
		return;
	}
	if('' == addwho){
		$.messager.alert("操作提示","添加人不能为空！","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'yb/bZChuHuo_add.action',
		data:'dd=' + dd + '&faCase=' + faCase + '&tuiCase=' + tuiCase + '&zpRej=' + zpRej + '&tuiRej=' + tuiRej + '&sgFa=' + sgFa + '&dzz=' + dzz +'&addwho=' + addwho + '&note=' + note,
		dataType:'json',
		success : function (data){
			$("#data").datagrid('reload');
			$("#w_addInfo").window("close");
			$.messager.alert("操作提示",data,"info",function(){
				$.ajax({
					type:'post',
					url:'wx/wXDispatcher_executePerDay.action'
				});
			});
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
function edit(){
	var row = $("#data").datagrid('getSelected');
	if(!row){
		$.messager.alert("操作提示","必须先选中需要修改的记录！","error");
		return;
	}
	$("#oldId").val(row.id);
	var dd = $("#edit_dd").datebox('setValue',row.dd);
	var faCase = $("#edit_faCase").textbox("setValue",row.faCase);
	var tuiCase = $("#edit_tuiCase").textbox("setValue",row.tuiCase);
	var zpRej = $("#edit_zpRej").textbox("setValue",row.zpRej);
	var tuiRej = $("#edit_tuiRej").textbox("setValue",row.tuiRej);
	var sgFa = $("#edit_sgFa").textbox("setValue",row.sgFa);
	var dzz = $("#edit_dzz").textbox("setValue",row.dzz);
	var dzz = $("#edit_note").textbox("setValue",row.note);
	$("#w_editInfo").window("open");
}
/**
 * 提交修改后的信息
 */
function editInfo(){
	var id = $("#oldId").val();
	var dd = $("#edit_dd").datebox('getValue');
	var faCase = $("#edit_faCase").textbox("getValue");
	var tuiCase = $("#edit_tuiCase").textbox("getValue");
	var zpRej = $("#edit_zpRej").textbox("getValue");
	var tuiRej = $("#edit_tuiRej").textbox("getValue");
	var sgFa = $("#edit_sgFa").textbox("getValue");
	var dzz = $("#edit_dzz").textbox("getValue");
	var note = $("#edit_note").textbox("getValue");
	var reg = new RegExp("^[0-9]*$");
	if('' == dd) {
		$.messager.alert("操作提示","日期不能为空！","error");
		return;
	}
	if('' == faCase || !reg.test(faCase)){
		$.messager.alert("操作提示","发货件数只能输入数字！","error");
		return;
	}
	if('' == tuiCase || !reg.test(tuiCase)){
		$.messager.alert("操作提示","退货件数只能输入数字！","error");
		return;
	}
	if('' == zpRej || !reg.test(zpRej)){
		$.messager.alert("操作提示","主配拒收件数只能输入数字！","error");
		return;
	}
	if('' == tuiRej || !reg.test(tuiRej)){
		$.messager.alert("操作提示","退货拒收件数只能输入数字！","error");
		return;
	}
	if('' == sgFa || !reg.test(sgFa)){
		$.messager.alert("操作提示","手工发货件数只能输入数字！","error");
		return;
	}
	if('' == dzz || !reg.test(dzz)){
		$.messager.alert("操作提示","大中专只能输入数字！","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'yb/bZChuHuo_edit.action',
		data:'dd=' + dd + '&faCase=' + faCase + '&tuiCase=' + tuiCase + '&zpRej=' + zpRej + '&tuiRej=' + tuiRej + '&sgFa=' + sgFa + '&dzz=' + dzz + '&id=' + id + '&note=' + note,
		dataType:'json',
		success : function (data){
			$("#data").datagrid('reload');
			$("#w_editInfo").window("close");
			$.messager.alert("操作提示",data,"info");
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
function go(){
	var dd = $("#q_dd").datebox('getValue');

	$("#data").datagrid({
		url:'yb/bZChuHuo_loadData.action?dd=' + dd,
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
			field:"id",
		    title:"编号",
		    checkbox:true,
		    width:50
		},{
			field:"faCase",
			title:"发货件数",
		    width:50
		},{
			field:"tuiCase",
			title:"退货件数",
		    width:50
		},{
			field:"zpRej",
			title:"主配拒收件数",
		    width:50
		},{
			field:"tuiRej",
			title:"退货拒收件数",
		    width:50
		},{
			field:"dzz",
			title:"大中专",
		    width:50
		},{
			field:"sgFa",
			title:"手工发货件数",
		    width:50
		},{
			field:"note",
			title:"备注",
		    width:30
		},{
			field:"dd",
			title:"日期",
		    width:30
		},{
			field:"addwho",
			title:"登记人",
		    width:30
		}]]
	});

}

/**
 * 回车事件
 */
function enterKey(){
	$("#dd").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#faCase').textbox('textbox').focus();
		}
	});
	$("#faCase").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#tuiCase').textbox('textbox').focus();
		}
	});
	$("#tuiCase").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#zpRej').textbox('textbox').focus();
		}
	});
	$("#zpRej").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#tuiRej').textbox('textbox').focus();
		}
	});
	$("#tuiRej").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#sgFa').textbox('textbox').focus();
		}
	});
	$("#sgFa").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#dzz').textbox('textbox').focus();
		}
	});
	$("#dzz").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			var faCase = $("#faCase").textbox("getValue");
			var tuiCase = $("#tuiCase").textbox("getValue");
			var zpRej = $("#zpRej").textbox("getValue");
			var tuiRej = $("#tuiRej").textbox("getValue");
			var sgFa = $("#sgFa").textbox("getValue");
			var dzz = $("#dzz").textbox("getValue");
			
			var total = parseInt(faCase) + parseInt(tuiCase) + parseInt(zpRej) + parseInt(tuiRej) + parseInt(sgFa) + parseInt(dzz); 
			$("#total").textbox('setValue',total);
			
			$('#note').textbox('textbox').focus();
		}
	});
}
$(function(){
	loadData();
	enterKey();
});