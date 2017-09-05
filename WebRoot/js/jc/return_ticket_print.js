$(function(){
	var subcode = $("#subcode").val();
	$.ajax({
		type:'post',
		url:'jc/print_returnTicket.action',
		data:'subcode=' + subcode,
		dataType:'json',
		success:function(data){
			var html = "";
			$("#data").empty();
			html += "<div class='d_subcode'>征订代码："+ data.subcode +"</div>";
			html += "<div class='d_descr'>书名："+ data.descr +"</div>";
			html += "<div class='d_price'>定价："+ data.price +"</div>";
			html += "<div class='d_pack'>捆扎："+ data.pack +"</div>";
			html += "<div class='d_shortname'>收件方："+ data.shortname +"</div>";
			html += "<div class='d_note'><span style='font-size:25px;font-weight:bold;padding:1px;border:1px solid #000;'>退</span></td> </div>";
			html += "<div class='d_buttom'><h2>江西新华物流有限公司</h2></div>";
			$("#data").append(html);
		},
		error:function(){
			alert("数据错误，联系管理员");
		}
	});
});

function doJqprint(){
	$("#data").jqprint();
}