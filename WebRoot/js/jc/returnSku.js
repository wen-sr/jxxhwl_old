
/**
 * 添加
 */
function addInfo(){
	var subcode = $("#subcode").textbox('getValue');
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var descr = $.trim($("#descr").val());
	if(descr == null || descr == "" ){
		$.messager.alert("操作提示","书名不能为空！","error");
		return;
	}
	var shortname = $.trim($("#shortname").val());
	if(shortname == null || shortname == "" ){
		$.messager.alert("操作提示","出版社不能为空！","error");
		return;
	}
	var price = $.trim($("#price").val());
	if(price == null || price == "" ){
		price = 0;
	}
	var pack = $.trim($("#pack").val());
	var reg = new RegExp("^[0-9]*$");
	var reg2 = new RegExp("^\\d+(\\.\\d+)?$");
	if(!reg.test(pack)){
		$.messager.alert("操作提示","捆扎只能输入数字！","error");
		return;
	}
	if(!reg2.test(price)){
		$.messager.alert("操作提示","定价只能输入数字！","error");
		return;
	}
	var param ="subcode=" + subcode + "&descr=" + descr + "&price=" + price + "&shortname=" + shortname + "&pack=" + pack;      
	$.ajax({
		type:"post",
		url:"jc/sku_savaReturnSku.action",
		data:param,
		dataType:'json',
		success:function(data){
			$.messager.alert("操作提示",data,"info");
			$("#subcode").textbox('setValue','');
			$("#descr").textbox('setValue','');
			$("#shortname").textbox('setValue','');
			$("#price").textbox('setValue','');
			$("#pack").textbox('setValue','');
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
	
}

$(function(){
});