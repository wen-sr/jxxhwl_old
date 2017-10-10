$(function(){
	var batchno = $("#batchno").val();
	$.ajax({
		type:'POST',
		url:'jc/print_oddCaseList.action',
		data:'batchno=' + batchno,
		dataType:'json',
		success:function(data){
			if(data == null){
				$.messager.alert("操作提示","没有对应的零件清单,请确定是否是零件发运","error");
				return;
			}
			var tab = $("#t1");
			var html = "";
			var sumqty = 0;
			for (var i in data["mx"]){
				html += "<tr><td>"+ data["mx"][i].issuenumber + data["mx"][i].subcode +"</td><td>"+ data["mx"][i].descr +"</td><td>"+ data["mx"][i].publisher +"</td><td>"+ data["mx"][i].price +"</td><td>"+ data["mx"][i].qtyallocated +"</td><td>"+ data["mx"][i].pack +"</td></tr>";
				sumqty += data["mx"][i].qtyallocated;
			}
			html += "<tr><td>合计</td><td>"+ data["mx"].length +"</td><td></td><td></td><td>"+ sumqty +"</td><td></td></tr>"
			tab.append(html);
			
			var html_head = "";
			for(var i in data["dt"]){
				html_head += "<tr><td>"+ data["dt"][i].code +"</td><td>"+ data["dt"][i].shortname +"</td><td>"+ data["dt"][i].shipno +"</td><td>"+ data["dt"][i].qtyallocated +"</td><td>&nbsp;</td><td>" + 1 + "</td></tr>";
				html_head += "<tr><td class='td2'>&nbsp;</td><td class='td2'>"+ data["dt"][i].contact +"</td><td class='td2'>"+ data["dt"][i].phone +"</td><td colspan='3' class='td2'>"+ data["dt"][i].address +"</td></tr>"
			}
			$("#t2").append(html_head);
		},
		error:function(){
			alert("数据错误，联系管理员");
		}
	});
});


function doJqprint(){
	$("#d").jqprint();
}