$(function(){
	var batchno = $("#batchno").val();
	$.ajax({
		type:'POST',
		url:'jc/print_allocationList.action',
		data:'batchno=' + batchno,
		dataType:'json',
		success:function(data){
			if(data == null){
				$.messager.alert("操作提示","没有对应的调拨单","error");
				return;
			}else{
				var html = "";
				for(var i in data["list"]){
					if(i % 2 == 0){
						html += "<div class='div_left'>" +
			    		"<div class='shortname'>" + data['list'][i].shortname + "</div>" +
			    		"<div class='div_second'>" +
				    		"<div class='descr'>" + data['list'][i].descr + "</div>" +
				    		"<div class='publisher'>" + data['list'][i].publisher + "</div>" +
				    	"</div>" +
				    	"<div class='div_third'>" +
					    	"<div class='price'>" + data['list'][i].price + "</div>" +
					    	"<div class='amt'>" + floatObj.multiply(data['list'][i].price,data['list'][i].qtyallocated,2) + "</div>" +
				    		"<div class='subcode'>" + data['list'][i].issuenumber + data['list'][i].subcode + "</div>" +
				    	"</div>" +
			    		"<div class='div_fouth'>" +
			    			"<div class='qty'>" + data['list'][i].qtyallocated  + "</div>" +
			    			"<div class='caseqty'>" + data['list'][i].caseqty  + "</div>" +
			    			"<div class='pack'>" + data['list'][i].pack*data["list"][i].bundle  + "</div>" +
			    			"<div class='odd'>" + data['list'][i].oddpack  + "捆零  " + data['list'][i].odd  + "  本</div>" +
			    		"</div>" +
			    		
			    		"<div class='div_fifth'>" +
				    		"<div class='shipno'>" + data['list'][i].shipno  + "</div>" +
				    		"<div class='barcode'>" + data['list'][i].barcode  + "</div>" +
			    		"</div>" +
			    	"</div>";
					}else {
						html += "<div class='div_right'>" +
					    		"<div class='shortname'>" + data['list'][i].shortname + "</div>" +
					    		"<div class='div_second'>" +
						    		"<div class='descr'>" + data['list'][i].descr + "</div>" +
						    		"<div class='publisher'>" + data['list'][i].publisher + "</div>" +
						    	"</div>" +
						    	"<div class='div_third'>" +
							    	"<div class='price'>" + data['list'][i].price + "</div>" +
                            		"<div class='amt'>" + floatObj.multiply(data['list'][i].price,data['list'][i].qtyallocated,2) + "</div>" +
						    		"<div class='subcode'>" + data['list'][i].issuenumber + data['list'][i].subcode + "</div>" +
						    	"</div>" +
					    		"<div class='div_fouth'>" +
					    			"<div class='qty'>" + data['list'][i].qtyallocated  + "</div>" +
					    			"<div class='caseqty'>" + data['list'][i].caseqty  + "</div>" +
					    			"<div class='pack'>" + data['list'][i].pack*data["list"][i].bundle  + "</div>" +
					    			"<div class='odd'>" + data['list'][i].oddpack  + "捆零  " + data['list'][i].odd  + "  本</div>" +
					    		"</div>" +
					    		
					    		"<div class='div_fifth'>" +
						    		"<div class='shipno'>" + data['list'][i].shipno  + "</div>" +
						    		"<div class='barcode'>" + data['list'][i].barcode  + "</div>" +
					    		"</div>" +
					    	"</div>";
					}
				}
				$("#d").append(html);
			}
		},
		error:function(){
			alert("数据错误，联系管理员");
		}
	});
});

function doJqprint(){
	$("#d").jqprint();
}

