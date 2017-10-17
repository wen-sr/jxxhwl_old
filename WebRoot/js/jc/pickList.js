$(function(){
	$(function(){
  		var pickno =  $("#pick").val();
  		$.ajax({
  			type:'Post',
  			url:'jc/print_pick.action',
  			data:'pickno=' + pickno,
  			dataType:'json',
  			success:function(data){
  				if(data){
  					var date = (new Date());
					var month = date.getMonth() + 1;
					var strDate = date.getDate();
					var year = date.getFullYear();
					if (month >= 1 && month <= 9) {
            			month = "0" + month;
        			}
			        if (strDate >= 0 && strDate <= 9) {
			            strDate = "0" + strDate;
			        }
        			var dt = year + "-" + month + "-" + strDate ;
  					var div = $("#d");
                    var html="<table frame=hsides rules='rows' align='left' style='width: 100%' height=80px><tr><td>期号</td><td>征订代码</td><td>商品编号</td><td>版别</td><td>定价</td><td>书名</td><td>列印日期</td></tr>" +

                        "<tr><td>"+ data['dt'].issuenumber +"</td><td>"+ data['dt'].subcode +"</td><td>"+ data['dt'].barcode +"</td><td>"+ data['dt'].shortname +"</td><td>"+ data['dt'].price +"</td><td>"+ data['dt'].descr +"</td><td>"+ dt +"</td></tr>"+
                        "</table>";
                    html += "<br/><br/><br/><br/>"
                    var sumqty = 0;
                    html += "<table id='tt' frame=hsides  rules='rows' style='width: 100%' align='left' padding-top=50px><tr><td>拣货流水号</td><td>客户代码</td><td>客户名称</td><td>配发数量</td><td>捆扎</td></tr>";
                    for(var i in data['mx']){
                        html += "<tr><td>"+ data['mx'][i].pickno +"</td><td>"+ data['mx'][i].code +"</td><td>"+ data['mx'][i].shortname +"</td><td>"+ data['mx'][i].qtyallocated +"</td><td>"+ data['mx'][i].pack +"</td></tr>"
                        sumqty += data['mx'][i].qtyallocated;
                    }
                    html += "<tr><th>总数量："+ sumqty +"</th><th colspan='4'></th></tr></table>"
                    // html += "<br/><br/><br/><br/><div style='width:100%;height:50px;padding-top:25px;' align='center'>拣货人签字：______________&nbsp;&nbsp;&nbsp;&nbsp;完成时间：_______________</div>"
                    div.append(html);
  					// var html="<table frame=hsides rules='rows' align='left' style='width: 100%' height=80px><tr><td>期号</td><td>征订代码</td><td>商品编号</td><td>版别</td><td>定价</td><td>书名</td><td>列印日期</td></tr>" +
  					//
  					// "<tr><td>"+ data['dt'].issuenumber +"</td><td>"+ data['dt'].subcode +"</td><td>"+ data['dt'].barcode +"</td><td>"+ data['dt'].shortname +"</td><td>"+ data['dt'].price +"</td><td>"+ data['dt'].descr +"</td><td>"+ dt +"</td></tr>"+
  					// "</table>";
  					// html += "<br/><br/><br/><br/>"
                    // html += "<table id='tt' frame=hsides  rules='rows' style='width: 100%' align='left' padding-top=50px><tr><td>拣货流水号</td><td>储位</td></td><td>客户代码</td><td>客户名称</td><td>配发数量</td><td>捆扎</td></tr>";
					// for(var x in data['xj']){
  					// 	html += "<tr><td>"+ data['xj'][x].pickno +"</td><td>"+ data['xj'][x].loc +"</td><td>&nbsp;</td><td>&nbsp;</td><td>"+ data['xj'][x].qtyallocated +"</td><td>"+ data['xj'][x].pack +"</td></tr>";
					// }
                    //
  					// var sumqty = 0;
  					// // html += "<table id='tt' frame=hsides  rules='rows' style='width: 100%' align='left' padding-top=50px><tr><td>拣货流水号</td><td>客户代码</td><td>客户名称</td><td>配发数量</td><td>捆扎</td></tr>";
  					// for(var i in data['mx']){
  					// 	html += "<tr><td  colspan='2'>"+ data['mx'][i].loc +"</td><td>"+ data['mx'][i].code +"</td><td>"+ data['mx'][i].shortname +"</td><td>"+ data['mx'][i].qtyallocated +"</td><td>&nbsp;</td></tr>";
  					// 	sumqty += data['mx'][i].qtyallocated;
  					// }
  					// html += "<tr><th>总数量："+ sumqty +"</th><th colspan='5'></th></tr></table>"
  					// // html += "<br/><br/><br/><br/><div style='width:100%;height:50px;padding-top:25px;' align='center'>拣货人签字：______________&nbsp;&nbsp;&nbsp;&nbsp;完成时间：_______________</div>"
					// div.append(html);
  				}
  			},
  			error:function(){
  				alert("数据错误，请联系管理员");
  			}
  		}); 
  	});
});


function doJqprint(){
	$("#d").jqprint();
}