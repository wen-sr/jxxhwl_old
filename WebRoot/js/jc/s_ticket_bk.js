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
						alert(1);
//						continue;
					}
					if(c % 5 == 0){
						if(j == parseInt(data["list"][i].caseqty)-1){
							html+= "<tr height='25px'><td>&nbsp;</td></tr><tr height='25px'><td>&nbsp;</td></tr><tr height='25px'><td>&nbsp;</td></tr>"
							html += "<tr>" +
							"<th>" + 
								"<table width='350px' style='font-size:17px' class='ttt'>" + 
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td colspan='2' align='center'></td></tr>" +
									"<tr rowspan='5'><td>&nbsp;</td><td><span style='font-size:55px;font-weight:bold; '>废票</span></td></tr>" +
									"<tr><td colspan='2' align='center'></td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold; '>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" + 
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td colspan='2' align='center' style='font-size:55px;font-weight:bold; '>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55'><td style='font-size:35px;font-weight:bold; '>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ data['list'][i].caseqty +"</td><td align='center' style='font-size:35px;font-weight:bold; ' >&nbsp;"+ data['list'][i].shipno +"</td></tr>" +
								"</table>" +
							"</th>";
						}else{
							html+= "<tr height='25px'><td>&nbsp;</td></tr><tr height='25px'><td>&nbsp;</td></tr><tr height='25px'><td>&nbsp;</td></tr>"
							html += "<tr>" +
							"<th>" + 
								"<table width='350px' style='font-size:17px' class='ttt'>" + 
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold;'>" + data['list'][i].issuenumber + data['list'][i].subcode +"</td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold;'>&nbsp;"+ data['list'][i].descr +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" + 
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold;'>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" + 
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td colspan='2' align='center' style='font-size:55px;font-weight:bold; '> "+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td style='font-size:35px;font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ data['list'][i].caseqty +"</td><td align='center'  style='font-size:35px;font-weight:bold; '>&nbsp;"+ data['list'][i].shipno +"</td></tr>" +
								"</table>" +
							"</th>";
						}
						
					}else if(c % 5 == 4 ){
						if(j == parseInt(data["list"][i].caseqty)-1){
							html += "<th>"+
									"<table width='350px' style='font-size:17px' class='ttt'>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td colspan='2' align='center'></td></tr>" +
									"<tr rowspan='5'><td>&nbsp;</td><td><span style='font-size:55px;font-weight:bold; '>废票</span></td></tr>" +
									"<tr><td colspan='2' align='center'></td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold; '>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td colspan='2' align='center' style='font-size:55px;font-weight:bold; '>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td align='right' style='font-size:35px;font-weight:bold; '>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ data['list'][i].caseqty +"</td><td align='center' style='font-size:35px;font-weight:bold; ' >&nbsp;"+ data['list'][i].shipno +"</td></tr>" +
									"</table>" +
								"</th>" +
								"</tr>";
						}else{
							html += "<th>"+
									"<table width='350px' style='font-size:17px' class='ttt'>" + 
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold;'>" + data['list'][i].issuenumber + data['list'][i].subcode +"</td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold;'>&nbsp;"+ data['list'][i].descr +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" + 
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold;'>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" + 
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td colspan='2' align='center' style='font-size:55px;font-weight:bold; '> "+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td align='right' style='font-size:35px;font-weight:bold; '>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ data['list'][i].caseqty +"</td><td align='center'  style='font-size:35px;font-weight:bold; '>&nbsp;"+ data['list'][i].shipno +"</td></tr>" +
								"</table>" +
								"</th>" +
								"</tr>";
							}
					}else{
						if(j == parseInt(data["list"][i].caseqty)-1){
							html += "<th>"+
									"<table width='350px' style='font-size:17px' class='ttt'>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td colspan='2' align='center'></td></tr>" +
									"<tr><td>&nbsp;</td><td><span style='font-size:55px;font-weight:bold; '>废票</span></td></tr>" +
									"<tr><td colspan='2' align='center'></td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold; '>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td colspan='2' align='center' style='font-size:55px;font-weight:bold; '>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td align='right' style='font-size:35px;font-weight:bold; '>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ data['list'][i].caseqty +"</td><td align='center' style='font-size:35px;font-weight:bold; ' >&nbsp;"+ data['list'][i].shipno +"</td></tr>" +
									"</table>" +
								"</th>";
						}else{
							html += "<th>"+
									"<table width='350px'  style='font-size:17px' class='ttt'>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold; '>" + data['list'][i].issuenumber + data['list'][i].subcode +"</td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold;'>&nbsp;"+ data['list'][i].descr +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td colspan='2' align='center' style='font-size:25px;font-weight:bold; '>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td colspan='2' align='center' style='font-size:55px;font-weight:bold; '>"+ data['list'][i].shortname +"</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr><td>&nbsp;</td><td>&nbsp;</td></tr>" +
									"<tr height='55px'><td align='right' style='font-size:35px;font-weight:bold; '>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ data['list'][i].caseqty +"</td><td align='center'  style='font-size:35px;font-weight:bold; '>&nbsp;"+ data['list'][i].shipno +"</td></tr>" +
									"</table>" +
								"</th>";
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