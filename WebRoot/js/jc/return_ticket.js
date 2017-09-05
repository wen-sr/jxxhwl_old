function go(){
	var subcode = $("#subcode").textbox('getValue');
	if(subcode == "" || subcode == null) {
		$.messager.alert("操作提示","征订代码不能为空","error");
		return;
	}
	window.open('jc/print_returnTicketMid.action?subcode='+subcode,'_blank')
	$("#subcode").textbox('setValue','');
}

function doJqprint(){
	$("#data").jqprint();
}