$(function(){
	var batchno = $("#batchno").val();
	$.ajax({
		type:'POST',
		url:'jc/print_wholeCaseList.action',
		data:'batchno=' + batchno,
		dataType:'json',
		success:function(data){
			if(data == null){
				$.messager.alert("操作提示","没有对应的整件清单,请确定是否是整件发运","error");
				return;
			}
			var html_head = "";
			for(var i in data["dt"]){
				html_head += "<tr><td>"+ data["dt"][i].issuenumber + data["dt"][i].subcode +"</td><td>"+ data["dt"][i].descr +"</td><td>"+ data["dt"][i].shortname +"</td><td>"+ data["dt"][i].price +"</td><td>"+ data["dt"][i].qtyallocated +"</td><td>"+ data["dt"][i].pack +"</td><td>" + "共" + data["dt"][i].caseqty + "捆零 " + data["dt"][i].odd + "本" + "</td></tr>";
			}
			$("#t1").append(html_head);
			var tab2 = document.getElementById("t2");
			var html = "";
			var sumqty = 0;
			var sumcase = 0;
			for (var i in data["mx"]){
				html += "<tr><td>" + data["mx"][i].code + "</td><td>"+ data["mx"][i].shortname +"</td><td>"+ data["mx"][i].shipno +"</td><td>"+ data["mx"][i].qtyallocated +"</td><td>"+ data["mx"][i].pack +"</td><td>"+ data["mx"][i].caseqty +"</td></tr>";
				html += "<tr><td class='td2'>&nbsp;</td><td class='td2'>"+ data["mx"][i].contact +"</td><td class='td2'>"+ data["mx"][i].phone +"</td><td colspan='3' class='td2'>"+ data["mx"][i].address +"</td></tr>"
				sumqty += data["mx"][i].qtyallocated;
				sumcase += data["mx"][i].caseqty;
			}
			html += "<tr><td class='td2'>合计:</td><td class='td2'></td><td class='td2'></td><td class='td2'>"+ sumqty +"</td><td class='td2'></td><td class='td2'>"+ sumcase +"</td></tr>"
			$("#t2").append(html);
		},
		error:function(){
			alert("数据错误，联系管理员");
		}
	});
});

function doJqprint(){
	$("#d").jqprint();
}