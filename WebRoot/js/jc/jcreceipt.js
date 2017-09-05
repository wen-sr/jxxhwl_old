function go(){
	var id = $.trim($("#oldid").val());
	var subcode = $.trim($("#subcode").val());
	var barcode = $.trim($("#barcode").val());
	var descr = $.trim($("#descr").val());
	var publisher = $.trim($("#publisher").val());
	var price = $.trim($("#price").val());
	var pack = $.trim($("#pack").val());
	var qtyshou = $.trim($("#qtyshou").val());
	var addwho = $.trim($("#addwho").val());
	var issuenumber = $.trim($("#issuenumber").val());
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	if(price == null || price == "" ){
		$.messager.alert("操作提示","定价不能为空！","error");
		return;
	}
	if(pack == null || pack == "" ){
		$.messager.alert("操作提示","捆扎数不能为空！","error");
		return;
	}
	if(qtyshou == null || qtyshou == "" ){
		$.messager.alert("操作提示","收货数量不能为空！","error");
		return;
	}
	if(addwho == null || addwho == "" ){
		$.messager.alert("操作提示","收货人不能为空！","error");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	var reg2 = new RegExp("^\\d+(\\.\\d+)?$");
	if(!reg.test(pack)){
		$.messager.alert("操作提示","捆扎数只能输入数字！","error");
		return;
	}
	if(!reg.test(qtyshou)){
		$.messager.alert("操作提示","收货数量只能输入数字！","error");
		return;
	}
	if(!reg2.test(price)){
		$.messager.alert("操作提示","定价只能输入数字！","error");
		return;
	}
	var username = $("#username").val();
	var param = "subcode=" + subcode + "&descr=" + descr + "&publisher=" + publisher + "&price=" + price + "&pack=" + pack + "&qtyshou=" + qtyshou + "&addwho=" + addwho + "&issuenumber=" + issuenumber + "&barcode=" + barcode;
	if(!(id == "" || id == null)){
		$.ajax({
			type:'POST',
			url:'receiptmodify.action',
			data:param += "&id=" + id + "&editwho=" + username,
			dataType:'json',
			success:function(data){
			if(data == 'ok'){
				$.messager.alert("操作提示","修改成功","info");
				$("#qtyshou").val("");
				$("#oldid").val("");
				$("#t").datagrid("reload");
			}else{
				$.messager.alert("操作提示","修改失败","error");
			}
			},
			error:function(){
				$.messager.alert("操作提示","数据错误，联系管理员！","error");
				return;
			}
		});
		
	}else{
		$.ajax({
			type:'POST',
			url:'jcreceipt.action',
			data:param,
			dataType:'json',
			success:function(data){
				if(data == "ok"){
					$.messager.alert("操作提示","收货成功！","info");
//					$("#qtyshou").val("");
					$("#f1")[0].reset();
					load();
				}else{
					$.messager.alert("操作提示","收货失败！","error");
				}
			},
			error:function(){
				$.messager.alert("操作提示","数据错误，联系管理员！","error");
				return;
			}
		});
	}
	
}

function bb (){
	var subcode = $.trim($("#subcode").val());
	$.ajax({
		type:'POST',
		url:'jcreceipt_search.action',
		data:'subcode=' + subcode,
		dataType:'json',
		success:function (data){
//			$("#f1")[0].reset();//重置表单
			if(data == null || data.length == 0 ){
				$("#barcode").focus();
			}else{
				$("#issuenumber").val(data[2]);
				$("#descr").val(data[0]);
				$("#publisher").val(data[1]);
				$("#pack").val(data[4]);
				$("#price").val(data[3]);
				$("#barcode").val(data[5]);
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
	
}

function load(){
	$("#t").datagrid({
//		title:"",
		url:'load_receipt.action',
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		pagination:true,
        pageSize:20,
        pageList:[10,15,20],
//		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"id",
		    title:"编号",
		    checkbox:true,
		    width:50
		},{
			field:"issuenumber",
			title:"期号",
		    width:60
		},{
			field:"subcode",
			title:"征订代码",
		    width:100
		},{
			field:"barcode",
			title:"条码",
		    width:100
		},{
			field:"publisher",
			title:"出版社",
		    width:60
		},{
			field:"descr",
			title:"书名",
		    width:100
		},{
			field:"price",
			title:"定价",
		    width:50
		},{
			field:"pack",
			title:"包册数",
		    width:50
		},{
			field:"qtyshou",
			title:"收货数量",
		    width:50
		},{
			field:"qtyallocated",
			title:"已分配数量",
		    width:50
		},{
			field:"qtyfree",
			title:"可用库存",
		    width:50
		},{
			field:"receiptwho",
			title:"收货人",
		    width:50
		},{
			field:"receiptdate",
			title:"收货时间",
		    width:50
		}]]
	});
	
	 //设置分页控件  

    var p = $('#t').datagrid('getPager');  
    $(p).pagination({  
//        pageSize: 20,//每页显示的记录条数，默认为10  
//        pageList: [10,15,20],//可以设置每页记录条数的列表  
        beforePageText: '第',//页数文本框前显示的汉字  
        afterPageText: '页    共 {pages} 页',  
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
        onBeforeRefresh:function(){ 
            $(this).pagination('loading'); 
            $(this).pagination('loaded'); 
        } 
    });  
}

function EnterKeyPress(){
	 $("form[name='f1'] input:text").keypress(function(e) {
		    if (e.which == 13) {// 判断所按是否回车键  
		        var inputs = $("form[name='f1']").find(":text"); // 获取表单中的所有输入框  
		        var idx = inputs.index(this); // 获取当前焦点输入框所处的位置  
		        if (idx == inputs.length - 1) {// 判断是否是最后一个输入框  
		        	inputs[0].focus();
		        	inputs[0].select();
//		            if (confirm("确认提交?")) // 用户确认  
//		            $("form[name='contractForm']").submit(); // 提交表单  
		        } else {  
		            inputs[idx + 1].focus(); // 设置焦点  
		            inputs[idx + 1].select(); // 选中文字  
		        }  
		        return false;// 取消默认的提交行为  
		    } 
	 });
}

tool = {
		edit : function (){
			var rows = $('#t').datagrid('getSelections');
			if(rows.length > 0){
				$("#oldid").val(rows[0].id);
				$("#issuenumber").val(rows[0].issuenumber);
				$("#subcode").val(rows[0].subcode);
				$("#descr").val(rows[0].descr);
				$("#publisher").val(rows[0].publisher);
				$("#pack").val(rows[0].pack);
				$("#price").val(rows[0].price);
				$("#qtyshou").val(rows[0].qtyshou);
				$("#addwho").val(rows[0].receiptwho);
				$("#addwho").attr("readonly","readonly");
				$("#subcode").attr("readonly","readonly");
				$("#barcode").val(rows[0].barcode);
			}else{
				return;
			}
		},
		remove : function(){
			$.messager.confirm("操作提示", "您确定要删除这些记录吗？", function (data) {
		         if (data) {
		        	 var ss = [];
		 			var rows = $('#t').datagrid('getSelections');
		 			if(rows.length == 0){
		 				$.messager.alert("提示","没有选中要删除的记录","error");
		 				return;
		 			}
		 			for(var i=0; i<rows.length; i++){
		 				var row = rows[i];
		 				ss.push(row.id);
		 			}
		 			if(ss.length > 0){
		 				$.ajax({
		 					url:'jcreceipt_delete',
		 					data:'ids=' + ss,
		 					dataType:'json',
		 					success:function(data){
		 						$("#t").datagrid("reload");
		 					},
		 					error:function(){
		 						$.messager.alert("提示","数据错误，联系管理员","info");
		 					}
		 				});
		 			}else{
		 				return;
		 			}
		         }
		         else {
		            return;
		         }
		     });
		}
	}

$(function(){
	load();
//	EnterKeyPress();
	$("#subcode").blur(function(){
		bb();
	});
	$("#go").click(function(){
		go();
	});
	//防止退格建返回上一个页面
	$(document).keydown(function(e){   
       var keyEvent;   
       if(e.keyCode==8){   
           var d=e.srcElement||e.target;   
            if(d.tagName.toUpperCase()=='INPUT'||d.tagName.toUpperCase()=='TEXTAREA'){   
                keyEvent=d.readOnly||d.disabled;   
            }else{   
                keyEvent=true;   
            }   
        }else{   
            keyEvent=false;   
        }   
        if(keyEvent){   
            e.preventDefault();   
        }   
	});
});



