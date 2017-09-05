function f_issuenumber (){
	$("#issuenumber1").combobox({
		onLoadSuccess: function () { //加载完成后,设置选中第一项
	        var val = $(this).combobox("getData");
	        for (var item in val[0]) {
	            if (item == "issuenumber") {
	                $(this).combobox("select", val[0][item]);
	            }
	        }
		}
	});
}

function f_combogrid(){
	var issuenumber = $("#issuenumber1").combobox('getValue');
	var type = $("#type").val();
	$('#t_subcode').datagrid({ 
		url:'loadfenfasubcode.action?issuenumber=' + issuenumber + '&type=' + type
	}); 
	$("#w2").window("open");
}

$(function(){
	f_issuenumber();
//	f_combogrid();
	$("#choosesubcode").click(function(){
		f_combogrid();
	});
	EnterKeyPress();
	$('#dlg').dialog('close');
	$("#subcode1").blur(function(){
		bb();
	});
	
	$("#submitgo").click(function(){
		submitgo();
	});
	
	
	$("#go").click(function(){
		go();
	});
	
	$("#fenfa").click(function(){
		if($(this).linkbutton('options').disabled){
			return;
		}
		var row = $('#t2').datagrid('getSelected');
		if(row == null ){
			$.messager.alert("提示","先选择品种才能分发","error");
			return;
		}
		$("#chosedId").val(row.id);
		$("#spanpack").val(row.pack);
		$("#spansubcode").html(row.subcode);
		$("#spandescr").html(row.descr);
		$("#spanissuenumber").val(row.issuenumber);
//		$("#spanqtyfree").html(row.qtyfree);
		$("#w").window("open");
		$("#fenfacommit").click(function(){
			var reg = new RegExp("^[0-9]*$");
			var fenfaqty = $("#fenfaqty").val();
			var fenfadian = $("#fenfadian").combobox('getValue');
			var chosedid = $("#chosedId").val();
			var pack = $("#spanpack").val();
			var username = $("#username").val();
			var subcode = $("#spansubcode").html();
			var issuenumber = $("#spanissuenumber").val();
			if(fenfadian == null ){
				$.messager.alert("提示","分发店不能为空","error");
				return;
			}
			if(!reg.test(fenfaqty)){
				$.messager.alert("提示","分发数量只能输入数字","error");
				return;
			}
			if(fenfaqty == ""){
				$.messager.alert("提示","分发数量不能为空","error");
				return;
			}
			$.ajax({
				url:'jcfenfacommit.action',
				data:"id=" + chosedid + "&qtyfenfa=" + fenfaqty + "&fenfadian=" + fenfadian + "&username=" + username + "&pack=" + pack + "&subcode=" + subcode + "&issuenumber=" + issuenumber,
				dataType:'json',
				success:function(data){
					$.messager.alert("提示",data,"info");
					$("#fenfaqty").val("");
						//清空combobox
//						$("#fenfadian").combobox('clear');
				},
				error:function(){
					$.messager.alert("提示","数据错误，联系管理员","info");
				}
			});
		});
	});
	
	$("#remove").click(function(){
		if($(this).linkbutton('options').disabled){
			return;
		}
		remove();
	});
	
	$("#yes").click(function(){
		var row = $('#t_subcode').datagrid('getSelected');
		var subcode = row.subcode;
		$("#w2").window("close");
		$("#subcode").val(subcode);
	});
	$("#no").click(function(){
		$("#w2").window("close");
	});
});

function go(){
	var issuenumber = $("#issuenumber1").combobox('getValue');
//	var subcode = $("#subcode").combobox('getValue');
	var subcode = $("#subcode").val();
	var type = $("#type").val();
	if(type == 1 ){
		$("#fenfa").linkbutton("disable");
		$("#remove").linkbutton("enable");
	}else{
		$("#fenfa").linkbutton("enable");
		$("#remove").linkbutton("disable");
	}
	$.ajax({
		type:'post',
		url:"jcquery.action",
		data:"issuenumber="+ issuenumber +"&subcode=" + subcode + "&type=" + type,
		dataType:'json',
		success:function(data){
			$("#t2").datagrid("loadData",data);
		},
		error:function(){
			$.messager.alert("提示","数据错误，联系管理员","info");
		}
	});
}

function bb (){
	var subcode = $.trim($("#subcode1").val());
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

function submitgo(){
	var id = $.trim($("#oldid").val());
	var subcode = $.trim($("#subcode1").val());
	var barcode = $.trim($("#barcode").val());
	var descr = $.trim($("#descr").val());
	var publisher = $.trim($("#publisher").val());
	var price = $.trim($("#price").val());
	var fenfadian = $.trim($("#customer").combobox('getValue'));
	var qtyfenfa = $.trim($("#qtyfenfa").val());
	var addwho = $.trim($("#addwho").val());
	var issuenumber = $.trim($("#issuenumber").val());
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	if(price == null || price == "" ){
		price = 0;
	}
	if(qtyfenfa == null || qtyfenfa == "" ){
		$.messager.alert("操作提示","分发数量不能为空！","error");
		return;
	}
	if(addwho == null || addwho == "" ){
		$.messager.alert("操作提示","收货人不能为空！","error");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	var reg2 = new RegExp("^\\d+(\\.\\d+)?$");
	if(!reg.test(qtyfenfa)){
		$.messager.alert("操作提示","收货数量只能输入数字！","error");
		return;
	}
	if(!reg2.test(price)){
		$.messager.alert("操作提示","定价只能输入数字！","error");
		return;
	}
	var username = $("#username").val();
	var param = "subcode=" + subcode + "&descr=" + descr + "&publisher=" + publisher + "&price=" + price + "&fenfadian=" + fenfadian + "&qtyfenfa=" + qtyfenfa + "&addwho=" + addwho + "&issuenumber=" + issuenumber + "&barcode=" + barcode;
	$('#dlg').dialog('open');
		$.ajax({
			type:'POST',
			url:'jcyufenfacommit.action',
			data:param,
			dataType:'json',
			success:function(data){
				$.messager.alert("操作提示",data,"info");
				$('#dlg').dialog('close');
				$("#qtyfenfa").val("");
				load();
			},
			error:function(){
				$('#dlg').dialog('close');
				$.messager.alert("操作提示","数据错误，联系管理员！","error");
				return;
			}
		});
}

function remove(){
	var row = $('#t2').datagrid('getSelected');
	if(row == null){
		return;
	}
	$.messager.confirm("提示","你确定要删除这条已分发的记录吗？",function(data){
		if (data) {
			var id = row.id;
			$.ajax({
				type:'POST',
				url:'jcremovedistribute.action',
				data:'id=' + id,
				dataType:'json',
				success:function(data){
					$.messager.alert("操作提示",data,"info");
					//刷新datagrid
					//$('#t2').datagrid('reload',{});
					go();
				}
			});
		} else {
			return;
		}
	});
	
}