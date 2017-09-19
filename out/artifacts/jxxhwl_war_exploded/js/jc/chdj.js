/**
 * 载入已登记的信息
 */
function loadData(){
	$("#data").datagrid({
		url:'jc/chanLiang_loadData.action',
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
			field:"lingPiaoZong",
			title:"领票总数",
		    width:30
		},{
			field:"yunShuZong",
			title:"运输签收总数",
		    width:30
		},{
			field:"yunShuDang",
			title:"运输当天签收数",
		    width:30
		},{
			field:"weiDao",
			title:"未到数",
		    width:30
		},{
			field:"daBao",
			title:"自己打包",
		    width:30
		},{
			field:"tieBao",
			title:"自己贴包",
		    width:30
		},{
			field:"jiaoCan",
			title:"教参打包",
		    width:30
		},{
			field:"wenJiao",
			title:"文教用品",
		    width:30
		},{
			field:"nanChangShi",
			title:"南昌市店",
		    width:30
		},{
			field:"daiFa",
			title:"代发累计",
		    width:30
		},{
			field:"heJi",
			title:"合计",
		    width:30
		},{
			field:"tui",
			title:"退出版社",
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
	var lingPiaoZong = $("#lingPiaoZong").textbox("getValue");
	var yunShuZong = $("#yunShuZong").textbox("getValue");
	var yunShuDang = $("#yunShuDang").textbox("getValue");
	var weiDao = $("#weiDao").textbox("getValue");
	var daBao = $("#daBao").textbox("getValue");
	var tieBao = $("#tieBao").textbox("getValue");
	var jiaoCan = $("#jiaoCan").textbox("getValue");
	var wenJiao = $("#wenJiao").textbox("getValue");
	var nanChangShi = $("#nanChangShi").textbox("getValue");
	var daiFa = $("#daiFa").textbox("getValue");
	var heJi = $("#heJi").textbox("getValue");
	var tui = $("#tui").textbox("getValue");
	var addwho = $("#addwho").textbox("getValue");
	var note = $("#note").textbox("getValue");
	var issue = $("#issue").textbox("getValue");
	var reg = new RegExp("^[0-9]*$");
	if('' == dd) {
		$.messager.alert("操作提示","日期不能为空！","error");
		return;
	}
	if('' == lingPiaoZong || !reg.test(lingPiaoZong)){
		$.messager.alert("操作提示","领票总数只能输入数字！","error");
		return;
	}
	if('' == yunShuZong || !reg.test(yunShuZong)){
		$.messager.alert("操作提示","运输签收总数只能输入数字！","error");
		return;
	}
	if('' == yunShuDang || !reg.test(yunShuDang)){
		$.messager.alert("操作提示","运输当天签收数只能输入数字！","error");
		return;
	}
	if('' == weiDao || !reg.test(weiDao)){
		$.messager.alert("操作提示","未到数只能输入数字！","error");
		return;
	}
	if('' == tieBao || !reg.test(tieBao)){
		$.messager.alert("操作提示","手工发货件数只能输入数字！","error");
		return;
	}
	if('' == daBao || !reg.test(daBao)){
		$.messager.alert("操作提示","自己打包只能输入数字！","error");
		return;
	}
	if('' == jiaoCan || !reg.test(jiaoCan)){
		$.messager.alert("操作提示","教参打包只能输入数字！","error");
		return;
	}
	if('' == wenJiao || !reg.test(wenJiao)){
		$.messager.alert("操作提示","文教用品只能输入数字！","error");
		return;
	}
	if('' == nanChangShi || !reg.test(nanChangShi)){
		$.messager.alert("操作提示","南昌市店只能输入数字！","error");
		return;
	}
	if('' == daiFa || !reg.test(daiFa)){
		$.messager.alert("操作提示","代发累计只能输入数字！","error");
		return;
	}
	if('' == heJi || !reg.test(heJi)){
		$.messager.alert("操作提示","合计只能输入数字！","error");
		return;
	}
	if('' == tui || !reg.test(tui)){
		$.messager.alert("操作提示","退出版社只能输入数字！","error");
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
		url:'jc/chanLiang_add.action',
		data:'dd=' + dd + '&lingPiaoZong=' + lingPiaoZong + '&yunShuZong=' + yunShuZong + '&yunShuDang=' + yunShuDang + '&weiDao=' + weiDao + '&daBao=' + daBao + '&tieBao=' + tieBao+ '&jiaoCan=' + jiaoCan + '&wenJiao=' + wenJiao + '&nanChangShi=' + nanChangShi+ '&daiFa=' + daiFa + '&heJi=' + heJi + '&tui=' + tui + '&addwho=' + addwho + '&note=' + note + '&issue=' + issue,
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
	var lingPiaoZong = $("#edit_lingPiaoZong").textbox("setValue",row.lingPiaoZong);
	var yunShuZong = $("#edit_yunShuZong").textbox("setValue",row.yunShuZong);
	var yunShuDang = $("#edit_yunShuDang").textbox("setValue",row.yunShuDang);
	var weiDao = $("#edit_weiDao").textbox("setValue",row.weiDao);
	var daBao = $("#edit_daBao").textbox("setValue",row.daBao);
	var tieBao = $("#edit_tieBao").textbox("setValue",row.tieBao);
	var jiaoCan = $("#edit_jiaoCan").textbox("setValue",row.jiaoCan);
	var wenJiao = $("#edit_wenJiao").textbox("setValue",row.wenJiao);
	var nanChangShi = $("#edit_nanChangShi").textbox("setValue",row.nanChangShi);
	var daiFa = $("#edit_daiFa").textbox("setValue",row.daiFa);
	var heJi = $("#edit_heJi").textbox("setValue",row.heJi);
	var tui = $("#edit_tui").textbox("setValue",row.tui);
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
	var lingPiaoZong = $("#edit_lingPiaoZong").textbox("getValue");
	var yunShuZong = $("#edit_yunShuZong").textbox("getValue");
	var yunShuDang = $("#edit_yunShuDang").textbox("getValue");
	var weiDao = $("#edit_weiDao").textbox("getValue");
	var daBao = $("#edit_daBao").textbox("getValue");
	var tieBao = $("#edit_tieBao").textbox("getValue");
	var jiaoCan = $("#edit_jiaoCan").textbox("getValue");
	var wenJiao = $("#edit_wenJiao").textbox("getValue");
	var nanChangShi = $("#edit_nanChangShi").textbox("getValue");
	var daiFa = $("#edit_daiFa").textbox("getValue");
	var heJi = $("#edit_heJi").textbox("getValue");
	var tui = $("#edit_tui").textbox("getValue");
	var addwho = $("#edit_addwho").textbox("getValue");
	var note = $("#edit_note").textbox("getValue");
	var issue = $("#edit_issue").textbox("getValue");
	var reg = new RegExp("^[0-9]*$");
	if('' == dd) {
		$.messager.alert("操作提示","日期不能为空！","error");
		return;
	}
	if('' == lingPiaoZong || !reg.test(lingPiaoZong)){
		$.messager.alert("操作提示","领票总数只能输入数字！","error");
		return;
	}
	if('' == yunShuZong || !reg.test(yunShuZong)){
		$.messager.alert("操作提示","运输签收总数只能输入数字！","error");
		return;
	}
	if('' == yunShuDang || !reg.test(yunShuDang)){
		$.messager.alert("操作提示","运输当天签收数只能输入数字！","error");
		return;
	}
	if('' == weiDao || !reg.test(weiDao)){
		$.messager.alert("操作提示","未到数只能输入数字！","error");
		return;
	}
	if('' == tieBao || !reg.test(tieBao)){
		$.messager.alert("操作提示","手工发货件数只能输入数字！","error");
		return;
	}
	if('' == daBao || !reg.test(daBao)){
		$.messager.alert("操作提示","自己打包只能输入数字！","error");
		return;
	}
	if('' == jiaoCan || !reg.test(jiaoCan)){
		$.messager.alert("操作提示","教参打包只能输入数字！","error");
		return;
	}
	if('' == wenJiao || !reg.test(wenJiao)){
		$.messager.alert("操作提示","文教用品只能输入数字！","error");
		return;
	}
	if('' == nanChangShi || !reg.test(nanChangShi)){
		$.messager.alert("操作提示","南昌市店只能输入数字！","error");
		return;
	}
	if('' == daiFa || !reg.test(daiFa)){
		$.messager.alert("操作提示","代发累计只能输入数字！","error");
		return;
	}
	if('' == heJi || !reg.test(heJi)){
		$.messager.alert("操作提示","合计只能输入数字！","error");
		return;
	}
	if('' == tui || !reg.test(tui)){
		$.messager.alert("操作提示","退出版社只能输入数字！","error");
		return;
	}
	if('' == addwho){
		$.messager.alert("操作提示","修改人不能为空！","error");
		return;
	}
	$.ajax({
		type:'post',
		url:'jc/chanLiang_edit.action',
		data:'dd=' + dd + '&lingPiaoZong=' + lingPiaoZong + '&yunShuZong=' + yunShuZong + '&yunShuDang=' + yunShuDang + '&weiDao=' + weiDao + '&daBao=' + daBao + '&tieBao=' + tieBao+ '&jiaoCan=' + jiaoCan + '&wenJiao=' + wenJiao + '&nanChangShi=' + nanChangShi+ '&daiFa=' + daiFa + '&heJi=' + heJi + '&tui=' + tui + '&addwho=' + addwho + '&id=' + id + '&note=' + note + '&issue=' + issue,
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
		url:'jc/chanLiang_loadData.action?dd=' + dd,
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
			field:"lingPiaoZong",
			title:"领票总数",
		    width:30
		},{
			field:"yunShuZong",
			title:"运输签收总数",
		    width:30
		},{
			field:"yunShuDang",
			title:"运输当天签收数",
		    width:30
		},{
			field:"weiDao",
			title:"未到数",
		    width:30
		},{
			field:"daBao",
			title:"自己打包",
		    width:30
		},{
			field:"tieBao",
			title:"自己贴包",
		    width:30
		},{
			field:"jiaoCan",
			title:"教参打包",
		    width:30
		},{
			field:"wenJiao",
			title:"文教用品",
		    width:30
		},{
			field:"nanChangShi",
			title:"南昌市店",
		    width:30
		},{
			field:"daiFa",
			title:"代发累计",
		    width:30
		},{
			field:"heJi",
			title:"合计",
		    width:30
		},{
			field:"tui",
			title:"退出版社",
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
	$("#lingPiaoZong").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#yunShuZong').textbox('textbox').focus();
		}
	});
	$("#yunShuZong").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#yunShuDang').textbox('textbox').focus();
		}
	});
	$("#yunShuDang").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#weiDao').textbox('textbox').focus();
		}
	});
	$("#weiDao").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			var lingPiaoZong = $("#lingPiaoZong").textbox("getValue");
			var yunShuZong = $("#yunShuZong").textbox("getValue");
			var weiDao = $("#weiDao").textbox("getValue");
			if( parseInt(yunShuZong) + parseInt(weiDao) == parseInt(lingPiaoZong)){
				$('#daBao').textbox('textbox').focus();
			}else{
				$.messager.alert("操作提示","运输总签收与未到数之和不等于总领票！","error",function(){
					$('#weiDao').textbox('textbox').focus();
				});
			}
		}
	});
	$("#daBao").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#tieBao').textbox('textbox').focus();
		}
	});
	$("#tieBao").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#jiaoCan').textbox('textbox').focus();
		}
	});
	$("#jiaoCan").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#wenJiao').textbox('textbox').focus();
		}
	});
	$("#wenJiao").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#nanChangShi').textbox('textbox').focus();
		}
	});
	$("#nanChangShi").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#daiFa').textbox('textbox').focus();
		}
	});
	$("#daiFa").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			var issue = $("#issue").textbox("getValue");
			var lingPiaoZong = $("#lingPiaoZong").textbox("getValue");
			var daBao = $("#daBao").textbox("getValue");
			var tieBao = $("#tieBao").textbox("getValue");
			var jiaoCan = $("#jiaoCan").textbox("getValue");
			var wenJiao = $("#wenJiao").textbox("getValue");
			var nanChangShi = $("#nanChangShi").textbox("getValue");
			var daiFa = $("#daiFa").textbox("getValue");
			var heji = 0;
			if(issue == ''){
				$.messager.alert("操作提示","请先选择期号，再按回车键进行计算！","error");
			}
			$.ajax({
				type:'post',
				url:'jc/chanLiang_getLouxia.action',
				data:'issue=' + issue,
				success:function(data){
					heji += parseInt(data);
					heji += parseInt(lingPiaoZong) + parseInt(daBao) + parseInt(tieBao) + parseInt(jiaoCan) + parseInt(wenJiao) + parseInt(nanChangShi) + parseInt(daiFa) ;
					$('#heJi').textbox('setValue',heji);
				}
			});
			
			$('#tui').textbox('textbox').focus();
		}
	});
	$("#tui").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#note').textbox('textbox').focus();
		}
	});
	$("#issue").textbox('textbox').bind('keydown',function(event){
		if(event.keyCode == "13"){
			$('#lingPiaoZong').textbox('textbox').focus();
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