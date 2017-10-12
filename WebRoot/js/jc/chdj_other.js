/**
 * 载入已登记的信息
 */
function loadData(){
	$("#data").datagrid({
		url:'jc/chanLiang_loadOtherData.action',
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
		    width:30
		},{
			field:"issue",
			title:"期号",
		    width:30
		},{
			field:"receiptToday",
			title:"今日收货",
		    width:30
		},{
			field:"receiptSum",
			title:"收货总件数",
		    width:30
		},{
			field:"deliverToday",
			title:"今日发货",
		    width:30
		},{
			field:"deliverSum",
			title:"发货总件数",
		    width:30
		},{
			field:"addwho",
			title:"添加人",
		    width:30
		},{
			field:"note",
			title:"备注",
		    width:30
		},{
			field:"dd",
			title:"日期",
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
	var receiptToday = $("#receiptToday").textbox("getValue");
	var receiptSum = $("#receiptSum").textbox("getValue");
	var deliverToday = $("#deliverToday").textbox("getValue");
	var deliverSum = $("#deliverSum").textbox("getValue");
	var addwho = $("#addwho").textbox("getValue");
	var note = $("#note").textbox("getValue");
	var issue = $("#issue").textbox("getValue");
	var reg = new RegExp("^[0-9]*$");
	if('' == dd) {
		$.messager.alert("操作提示","日期不能为空！","error");
		return;
	}
	if('' == receiptToday || !reg.test(receiptToday)){
		$.messager.alert("操作提示","今日收货只能输入数字！","error");
		return;
	}
	if('' == receiptSum || !reg.test(receiptSum)){
		$.messager.alert("操作提示","总收货只能输入数字！","error");
		return;
	}
	if('' == deliverToday || !reg.test(deliverToday)){
		$.messager.alert("操作提示","今日发货只能输入数字！","error");
		return;
	}
	if('' == deliverSum || !reg.test(deliverSum)){
		$.messager.alert("操作提示","总发货只能输入数字！","error");
		return;
	}
	if('' == addwho){
		$.messager.alert("操作提示","添加人不能为空！","error");
		return;
	}
	if('' == issue){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'jc/chanLiang_addOther.action',
		data:'dd=' + dd + '&receiptToday=' + receiptToday + '&receiptSum=' + receiptSum + '&deliverToday=' + deliverToday + '&deliverSum=' + deliverSum +  '&addwho=' + addwho + '&note=' + note + '&issue=' + issue,
		dataType:'json',
		success : function (data){
			$("#data").datagrid('reload');
			$("#w_addInfo").window("close");
			$.messager.alert("操作提示",data,"info");
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
	var dd = $("#edit_dd").datebox("setValue",row.dd);
	var lingPiaoZong = $("#edit_receiptToday").textbox("setValue",row.receiptToday);
	var yunShuZong = $("#edit_receiptSum").textbox("setValue",row.receiptSum);
	var yunShuDang = $("#edit_deliverToday").textbox("setValue",row.deliverToday);
	var weiDao = $("#edit_deliverSum").textbox("setValue",row.deliverSum);
	var note = $("#edit_note").textbox("setValue",row.note);
	var issue = $("#edit_issue").textbox("setValue",row.issue);
	$("#w_editInfo").window("open");
}
/**
 * 提交修改后的信息
 */
function editInfo(){
	var id = $("#oldId").val();
    var dd = $("#edit_dd").datebox('getValue');
    var receiptToday = $("#edit_receiptToday").textbox("getValue");
    var receiptSum = $("#edit_receiptSum").textbox("getValue");
    var deliverToday = $("#edit_deliverToday").textbox("getValue");
    var deliverSum = $("#edit_deliverSum").textbox("getValue");
    var addwho = $("#edit_addwho").textbox("getValue");
    var note = $("#edit_note").textbox("getValue");
    var issue = $("#edit_issue").textbox("getValue");
	var reg = new RegExp("^[0-9]*$");
    if('' == dd) {
        $.messager.alert("操作提示","日期不能为空！","error");
        return;
    }
    if('' == receiptToday || !reg.test(receiptToday)){
        $.messager.alert("操作提示","今日收货只能输入数字！","error");
        return;
    }
    if('' == receiptSum || !reg.test(receiptSum)){
        $.messager.alert("操作提示","总收货只能输入数字！","error");
        return;
    }
    if('' == deliverToday || !reg.test(deliverToday)){
        $.messager.alert("操作提示","今日发货只能输入数字！","error");
        return;
    }
    if('' == deliverSum || !reg.test(deliverSum)){
        $.messager.alert("操作提示","总发货只能输入数字！","error");
        return;
    }
    if('' == addwho){
        $.messager.alert("操作提示","添加人不能为空！","error");
        return;
    }
    if('' == issue){
        $.messager.alert("操作提示","期号不能为空！","error");
        return;
    }
	$.ajax({
		type:'post',
		url:'jc/chanLiang_editOther.action',
		data:'id=' + id + '&dd=' + dd + '&receiptToday=' + receiptToday + '&receiptSum=' + receiptSum + '&deliverToday=' + deliverToday + '&deliverSum=' + deliverSum +  '&addwho=' + addwho + '&note=' + note + '&issue=' + issue,
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
		url:'jc/chanLiang_loadOtherData.action?dd=' + dd,
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
            width:30
        },{
            field:"issue",
            title:"期号",
            width:30
        },{
            field:"receiptToday",
            title:"今日收货",
            width:30
        },{
            field:"receiptSum",
            title:"收货总件数",
            width:30
        },{
            field:"deliverToday",
            title:"今日发货",
            width:30
        },{
            field:"deliverSum",
            title:"发货总件数",
            width:30
        },{
            field:"addwho",
            title:"添加人",
            width:30
        },{
            field:"note",
            title:"备注",
            width:30
        },{
            field:"dd",
            title:"日期",
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
			$('#issue').textbox('textbox').focus();
		}
	});
	$("#receiptToday").textbox('textbox').bind('keydown',function(event){
        if(event.keyCode == "13"){
            var issue = $("#issue").textbox("getValue");
            if(issue == ''){
                $.messager.alert("操作提示","请先选择期号，再按回车键进行计算！","error");
            }
            $.ajax({
                type:'post',
                url:'jc/chanLiang_getOtherSum.action',
                data:'issue=' + issue,
                success:function(data){
                    var r = $("#receiptToday").textbox("getValue");
                	if(data.receiptSum == 0){
                        $('#receiptSum').textbox('setValue', r);
					}else{
                        $('#receiptSum').textbox('setValue', (data.receiptSum + parseInt(r)));
                        $('#deliverSum').textbox('setValue',data.deliverSum);
					}

                }
            });

            $('#deliverToday').textbox('textbox').focus();
        }
	});
	$("#deliverToday").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
            var d = $("#deliverToday").textbox("getValue")
            var deliverSum = $("#deliverSum").textbox("getValue");
			if( deliverSum == 0 || deliverSum == undefined){
                $('#deliverSum').textbox('setValue', d);
			}else{
                $('#deliverSum').textbox('setValue',(parseInt(deliverSum) + parseInt(d)));
			}

			$('#note').textbox('textbox').focus();
		}
	});
	$("#note").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#submitgo').textbox('textbox').focus();
		}
	});
	
}

$(function(){
	loadData();
	enterKey();
});