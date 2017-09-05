$(function(){
	var batchno = $("#batchno").val();
	$.ajax({
		type:'POST',
		url:'jc/print_s_ticketList.action',
		data:'batchno=' + batchno,
		dataType:'json',
		success:function(data){
			var html = "";
			var c = 0 ;
			for(var i in data["list"] ){
				for(var j=0;j< data["list"][i].caseqty;j++){
					if(data["list"][i].caseqty == 1 ){
						continue;
					}
					if(c % 5 == 0){
						if(j == parseInt(data["list"][i].caseqty)-1){
							//每行第一个为废票
//							html += "<br/>";
							html += "<div class='div_box_left'>" +
					    			"<div class='div_note'>废票</div>" + 
//					    			"<div class='div_descr'>尾包_完_废票</div>" + 
					    			"<div class='div_customer'>"+ data['list'][i].shortname +"</div>" +
					    			"<div class='div_station'>"+ data['list'][i].shortname +"</div>" +
					    			"<div class='div_caseqty'>"+ data['list'][i].caseqty +"</div>" +
					    			"<div class='div_shipno'>"+ data['list'][i].shipno +"</div>" + 
					    			"</div>";
						}else{
							//每行第一个为正常票
//							html += "<br/>";
							html += "<div class='div_box_left'>" +
					    			"<div class='div_subcode'>" + data['list'][i].issuenumber + data['list'][i].subcode +"</div>" + 
					    			"<div class='div_descr'>"+ data['list'][i].descr +"</div>" + 
					    			"<div class='div_customer'>"+ data['list'][i].shortname +"</div>" + 
					    			"<div class='div_station'>"+ data['list'][i].shortname +"</div>" +
					    			"<div class='div_caseqty'>"+ data['list'][i].caseqty +"</div>" +
					    			"<div class='div_shipno'>"+ data['list'][i].shipno +"</div>" + 
					    			"</div>";
						}
						
					}else if(c % 5 == 4 ){
						if(j == parseInt(data["list"][i].caseqty)-1){
							//每行最后一个为废票
							html += "<div class='div_box_right'>" +
			    			"<div class='div_note'>废票</div>" + 
//			    			"<div class='div_descr'>尾包_完_废票</div>" + 
			    			"<div class='div_customer'>"+ data['list'][i].shortname +"</div>" +
			    			"<div class='div_station'>"+ data['list'][i].shortname +"</div>" +
			    			"<div class='div_caseqty'>"+ data['list'][i].caseqty +"</div>" +
			    			"<div class='div_shipno'>"+ data['list'][i].shipno +"</div>" + 
			    			"</div>";
						}else{
							//每行最后一个为正常票
							html += "<div class='div_box_right'>" +
									"<div class='div_subcode'>" + data['list'][i].issuenumber + data['list'][i].subcode +"</div>" + 
					    			"<div class='div_descr'>"+ data['list'][i].descr +"</div>" + 
					    			"<div class='div_customer'>"+ data['list'][i].shortname +"</div>" + 
					    			"<div class='div_station'>"+ data['list'][i].shortname +"</div>" +
					    			"<div class='div_caseqty'>"+ data['list'][i].caseqty +"</div>" +
					    			"<div class='div_shipno'>"+ data['list'][i].shipno +"</div>" + 
					    			"</div>";
						}
					}else{
						if(j == parseInt(data["list"][i].caseqty)-1){
							//每行中部为废票
							html += "<div class='div_box'>" +
			    			"<div class='div_note'>废票</div>" + 
//			    			"<div class='div_descr'>尾包_完_废票</div>" + 
			    			"<div class='div_customer'>"+ data['list'][i].shortname +"</div>" +
			    			"<div class='div_station'>"+ data['list'][i].shortname +"</div>" +
			    			"<div class='div_caseqty'>"+ data['list'][i].caseqty +"</div>" +
			    			"<div class='div_shipno'>"+ data['list'][i].shipno +"</div>" + 
			    			"</div>";
						}else{
							//每行中部为正常票
							html += "<div class='div_box'>" +
									"<div class='div_subcode'>" + data['list'][i].issuenumber + data['list'][i].subcode +"</div>" + 
					    			"<div class='div_descr'>"+ data['list'][i].descr +"</div>" + 
					    			"<div class='div_customer'>"+ data['list'][i].shortname +"</div>" + 
					    			"<div class='div_station'>"+ data['list'][i].shortname +"</div>" +
					    			"<div class='div_caseqty'>"+ data['list'][i].caseqty +"</div>" +
					    			"<div class='div_shipno'>"+ data['list'][i].shipno +"</div>" + 
					    			"</div>";
						}
					}
					c++;
				}
			}
			$("#d").append(html);
			console.log(html);
		},
		error:function(){
			alert("数据错误，联系管理员");
		}
	});
});

function doJqprint(){
	$("#d").jqprint();
}