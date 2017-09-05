/**
 * 提交收货信息
 */
function addInfo(){
	var receiptno = $("#add_receiptno").textbox("getValue");
	var issuenumber = $("#add_issuenumber").combobox("getValue");
	var subcode = $("#add_subcode").textbox('getValue');
	var qtyunreceipt = $("#add_qtyunreceipt").textbox('getValue');
	var qtyreceipt = $("#add_qtyreceipt").textbox('getValue');
	var pack = $("#add_pack").combobox('getValue');
	var printPlant = $("#add_printPlant").combobox('getValue');
	var addwho = $("#add_addwho").textbox('getValue');
	if(receiptno == null || receiptno == "" ){
		$.messager.alert("操作提示","收货编号不能为空！","error");
		return;
	}
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	if(pack == null || pack == "" ){
		$.messager.alert("操作提示","捆扎数不能为空！","error");
		return;
	}
	if(qtyreceipt == null || qtyreceipt == "" ){
		$.messager.alert("操作提示","收货数量不能为空！","error");
		return;
	}
	if(addwho == null || addwho == "" ){
		$.messager.alert("操作提示","收货人不能为空！","error");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(pack)){
		$.messager.alert("操作提示","捆扎数只能输入数字！","error");
		return;
	}
	if(!reg.test(qtyreceipt)){
		$.messager.alert("操作提示","收货数量只能输入数字！","error");
		return;
	}
	if(parseInt(qtyreceipt) > parseInt(qtyunreceipt)){
		$.messager.alert("操作提示","收货数量不能大于未到数量！","error");
		return;
	}
	if(printPlant == null || printPlant == "" ){
		$.messager.alert("操作提示","印刷厂不能为空！","error");
		return;
	}
	var param ="printPlant="+ printPlant + "&receiptno=" + receiptno + "&subcode=" + subcode + "&pack=" + pack + "&qtyreceipt=" + qtyreceipt + "&addwho=" + addwho + "&issuenumber=" + issuenumber + "&qtyunreceipt=" + qtyunreceipt;
	$.ajax({
		type:'POST',
		url:'jc/receipt_save.action',
		data:param,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info");
				$("#w-addInfo").window("close");
				load();
			}else{
				$.messager.alert("操作提示","修改失败","error");
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 加载收货信息
 */
function load(){
	$("#data").datagrid({
		url:'jc/receipt_loadData.action',
		height:'auto',	
		fitColumns: true,
		striped:true,
		rownumbers:true,
		border:true,
		singleSelect:true,
		pagination:true,
        pageSize:20,
        pageList:[10,15,20],
		showFooter: true,
		toolbar:'#tb',
		columns:[[{
			field:"id",
		    title:"编号",
		    checkbox:true,
		    width:30
		},{
			field:"receiptno",
			title:"收货编号",
		    width:30
		},{
			field:"issuenumber",
			title:"期号",
		    width:30
		},{
			field:"subcode",
			title:"征订代码",
		    width:50
		},{
			field:"barcode",
			title:"条码",
		    width:50
		},{
			field:"descr",
			title:"书名",
		    width:50
		},{
			field:"price",
			title:"定价",
		    width:30
		},{
			field:"pack",
			title:"捆扎",
		    width:30
		},{
			field:"qtyreceipt",
			title:"收货数",
		    width:30
		},{
			field:"storerkey",
			title:"出版社代码",
		    width:30
		},{
			field:"publisher",
			title:"出版社名称",
		    width:30
		},{
			field:"printPlant",
			title:"印刷厂",
		    width:30
		},{
			field:"printPlantName",
			title:"印刷厂名称",
		    width:30
		},{
			field:"addwho",
			title:"收货人",
		    width:30
		},{
			field:"adddate",
			title:"收货时间",
		    width:30
		}]]
	});
	$("#currentType").val("1");
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

/**
 * 提交修改的信息
 * @returns
 */
function editInfo(){
	var id = $("#edit_id").val();
	var issuenumber = $.trim($("#edit_issuenumber").combobox('getValue'));
	if(issuenumber == ""){
		$.messager.alert("操作提示","期号不能为空！","error");
		return;
	}
	var subcode = $.trim($("#edit_subcode").val());
	if(subcode == null || subcode == "" ){
		$.messager.alert("操作提示","征订代码不能为空！","error");
		return;
	}
	var addwho = $.trim($("#edit_addwho").val());
	if(addwho == null || addwho == ""){
		$.messager.alert("操作提示","修改人不能为空！","error");
		return;
	}
	var pack = $("#edit_pack").combobox('getValue');
	if(pack == null || pack == "" ){
		$.messager.alert("操作提示","捆扎不能为空！","error");
		return;
	}
	var qtyreceipt = $.trim($("#edit_qtyreceipt").val());
	if(qtyreceipt == null || qtyreceipt == "" ){
		$.messager.alert("操作提示","收获数量不能为空！","error");
		return;
	}
	var receiptno = $("#edit_receiptno").textbox('getValue');
	if(receiptno == null || receiptno == "" ){
		$.messager.alert("操作提示","收获编号不能为空！","error");
		return;
	}
	var printPlant = $("#edit_printPlant").combobox('getValue');
	if(printPlant == null || printPlant == "" ){
		$.messager.alert("操作提示","印刷厂不能为空！","error");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(pack)){
		$.messager.alert("操作提示","捆扎只能输入数字！","error");
		return;
	}
	if(!reg.test(qtyreceipt)){
		$.messager.alert("操作提示","收货数量只能输入数字！","error");
		return;
	}
	
	var param = "printPlant=" + printPlant + "&id=" + id + "&subcode=" + subcode + "&pack=" + pack + "&qtyreceipt=" + qtyreceipt + "&addwho=" + addwho + "&issuenumber=" + issuenumber + "&receiptno=" + receiptno;      
	$.ajax({
		type:"post",
		url:"jc/receipt_update.action",
		data:param,
		dataType:'json',
		success:function(data){
			if(data){
				$.messager.alert("操作提示",data,"info");
			}
			$("#w-editInfo").window("close");
			$("#data").datagrid('reload');
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}
/**
 * 删除
 * @returns
 */
function removeInfo(){
	var row = $('#data').datagrid('getSelected');
	if(row){
		$.ajax({
			url:'jc/receipt_delete.action',
			data:'id=' + row.id,
			dataType:'json',
			success:function(data){
				$.messager.alert("操作提示",data,"info");
				load();
			},
			error:function(){
				$.messager.alert("提示","数据错误，联系管理员","info");
			}
		});
	}else{
		$.messager.alert("操作提示","没有选中的记录","error");
		return;
	}
}
/**
 * 选择征订代码
 */
function chooseSubcode (){
	var type = $("#type").combobox("getValue");
	var issuenumber = $("#issuenumber").combobox("getValue");
	if(type == "0") {
		$("#c_subcode").datagrid({
			url:'jc/receipt_waitReceiptSubcodeData.action?issuenumber=' + issuenumber,
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			singleSelect:true,
			showFooter: true,
			toolbar:'#tb2',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:50
			},{
				field:"issuenumber",
				title:"期号",
			    width:50
			},{
				field:"subcode",
				title:"征订代码",
			    width:50
			},{
				field:"barcode",
				title:"条码",
			    width:50
			},{
				field:"descr",
				title:"书名",
			    width:50
			},{
				field:"price",
				title:"定价",
			    width:50
			},{
				field:"storerkey",
				title:"出版社代码",
			    width:30
			},{
				field:"publisher",
				title:"出版社",
			    width:30
			}]]
		});
		$("#showSubcode").window("open");
	}else if(type == "1"){
		$("#c_subcode").datagrid({
			url:'jc/receipt_loadReceiptSubcodeInfo.action?issuenumber=' + issuenumber + "&type=0",
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			singleSelect:true,
			showFooter: true,
			toolbar:'#tb2',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:50
			},{
				field:"issuenumber",
				title:"期号",
			    width:50
			},{
				field:"subcode",
				title:"征订代码",
			    width:50
			},{
				field:"barcode",
				title:"条码",
			    width:50
			},{
				field:"descr",
				title:"书名",
			    width:50
			},{
				field:"price",
				title:"定价",
			    width:50
			},{
				field:"pack",
				title:"捆扎",
			    width:50
			},{
				field:"storerkey",
				title:"出版社代码",
			    width:30
			},{
				field:"publisher",
				title:"出版社",
			    width:30
			}]]
		});
		$("#showSubcode").window("open");
		
	}
	
}
/**
 * 查询
 */
function go(){
	var type = $("#type").combobox("getValue");
	var issuenumber = $("#issuenumber").combobox("getValue");
	var subcode = $("#subcode").textbox('getValue');
	var barcode = $("#barcode").textbox('getValue');
	var receiptno = $("#receiptno").textbox('getValue');
	var param = "type=" + type + "&issuenumber=" + issuenumber + "&subcode=" + subcode;
	if(type == 0){
		$("#data").datagrid({
			url:'jc/receipt_waitReceiptSubcodeData.action?issuenumber=' + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode,
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20],
			singleSelect:true,
			showFooter: true,
			toolbar:'#tb',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:50
			},{
				field:"issuenumber",
				title:"期号",
			    width:50
			},{
				field:"subcode",
				title:"征订代码",
			    width:50
			},{
				field:"barcode",
				title:"条码",
			    width:50
			},{
				field:"descr",
				title:"书名",
			    width:50
			},{
				field:"price",
				title:"定价",
			    width:50
			},{
				field:"orderqty",
				title:"采购数",
			    width:30
			},{
				field:"qtyreceipt",
				title:"已收货数",
			    width:30
			},{
				field:"qtyunreceipt",
				title:"未到数",
			    width:30
			},{
				field:"storerkey",
				title:"出版社代码",
			    width:30
			},{
				field:"publisher",
				title:"出版社",
			    width:30
			}]]
		});
		$("#currentType").val("0");
	}else if(type == 1){
		$("#data").datagrid({
			url:'jc/receipt_loadData.action?issuenumber=' + issuenumber + "&subcode=" + subcode + "&barcode=" + barcode + "&receiptno=" + receiptno ,
			height:'auto',
			fitColumns: true,
			striped:true,
			rownumbers:true,
			border:true,
			pagination:true,
	        pageSize:20,
	        pageList:[10,15,20],
			singleSelect:true,
			showFooter: true,
			toolbar:'#tb',
			columns:[[{
				field:"id",
			    title:"编号",
			    checkbox:true,
			    width:30
			},{
				field:"receiptno",
				title:"收货编号",
			    width:30
			},{
				field:"issuenumber",
				title:"期号",
			    width:30
			},{
				field:"subcode",
				title:"征订代码",
			    width:50
			},{
				field:"barcode",
				title:"条码",
			    width:50
			},{
				field:"descr",
				title:"书名",
			    width:50
			},{
				field:"price",
				title:"定价",
			    width:30
			},{
				field:"pack",
				title:"捆扎",
			    width:30
			},{
				field:"qtyreceipt",
				title:"收货数",
			    width:30
			},{
				field:"storerkey",
				title:"出版社代码",
			    width:30
			},{
				field:"publisher",
				title:"出版社名称",
			    width:30
			},{
				field:"printPlant",
				title:"印刷厂",
			    width:30
			},{
				field:"printPlantName",
				title:"印刷厂名称",
			    width:30
			},{
				field:"addwho",
				title:"收货人",
			    width:30
			},{
				field:"adddate",
				title:"收货时间",
			    width:30
			}]]
		});
		 $("#currentType").val("1");
	}
}

/**
 * 工具栏
 */
tool = {
		add : function (){
			var currentType = $("#currentType").val();
			if(currentType != "0"){
				$.messager.alert("操作提示","请先切换到可收货界面才能新增收货单","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#add_issuenumber").combobox("setValue",row.issuenumber);
			$("#add_subcode").textbox('setValue',row.subcode);
			$("#add_barcode").textbox('setValue',row.barcode);
			$("#add_descr").textbox('setValue',row.descr);
			$("#add_publisher").combobox('setValue',row.storerkey);
			$("#add_price").textbox('setValue',row.price);
			$("#add_orderqty").textbox('setValue',row.orderqty);
			$("#add_qtyunreceipt").textbox('setValue',row.qtyunreceipt);
			$("#add_qtyreceipt").textbox('setValue','');
			$("#add_pack").combobox({
				url:'jc/pack_query.action?issuenumber=' + row.issuenumber + "&subcode=" + row.subcode,
				valueField:'pack',
				textField:'pack',
				panelHeight:'auto',
				editable:false
			});
			$("#w-addInfo").window("open");
		},
		edit : function (){
			var currentType = $("#currentType").val();
			if(currentType != "1"){
				$.messager.alert("操作提示","请先切换到已收货界面才能修改","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(row == null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#w-editInfo").window("open");
			$("#edit_id").val(row.id);
			$("#edit_issuenumber").combobox("setValue",row.issuenumber);
			$("#edit_subcode").textbox('setValue',row.subcode);
			$("#edit_barcode").textbox('setValue',row.barcode);
			$("#edit_descr").textbox('setValue',row.descr);
			$("#edit_publisher").combobox('setValue',row.storerkey);
			$("#edit_printPlant").combobox('setValue',row.printPlant);
			$("#edit_price").textbox('setValue',row.price);
			$("#edit_receiptno").textbox('setValue',row.receiptno);
			$("#edit_pack").combobox({
				url:'jc/pack_query.action?issuenumber=' + row.issuenumber + "&subcode=" + row.subcode,
				valueField:'pack',
				textField:'pack',
				panelHeight:'auto',
				editable:false
			});
			$("#edit_pack").textbox('setValue',row.pack);
			$("#edit_qtyreceipt").textbox('setValue',row.qtyreceipt);
		},
		remove : function(){
			var currentType = $("#currentType").val();
			if(currentType != "1"){
				$.messager.alert("操作提示","请先切换到已收货界面才能删除","error");
				return;
			}
			var row = $('#data').datagrid('getSelected');
			if(row== null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$.messager.confirm("操作提示", "您确定要删除这条记录吗？", function (data) {
		         if (data) {
		        	 removeInfo();
		         }else {
			         return;
			     }
		     });
		},
		yes : function (){
			var row = $("#c_subcode").datagrid('getSelected');
			if(row == null){
				$.messager.alert("操作提示","没有选中的记录","error");
				return;
			}
			$("#subcode").textbox('setValue',row.subcode);
			$("#showSubcode").window("close");
		},
		no : function (){
			$("#showSubcode").window("close");
		}
	}

$(function(){
//	load();
//	$("input",$("#subcode").next("span")).blur(function(){
//		querySku();
//	});
	
//	EnterKeyPress();
//	$("#subcode").blur(function(){
//		bb();
//	});
//	$("#go").click(function(){
//		go();
//	});
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

/**
 * 根据期号和征订代码查询资料
 * 
 */
function querySku(){
	var issuenumber = $.trim($("#issuenumber").combobox('getText'));
	if(issuenumber == null || issuenumber == "" ){
		return;
	}
	var subcode = $.trim($("#subcode").textbox('getText'));
	if(subcode == null || subcode == "" ){
		return;
	}
	$.ajax({
		type:"post",
		url:"jc/sku_queryByIssuenumberAndSubcode.action",
		data:"issuenumber=" + issuenumber + "&subcode=" + subcode,
		dataType:"json",
		success:function(data){
			if(data){
				$("#barcode").textbox('setValue',data.barcode);
				$("#descr").textbox('setValue',data.descr);
				$("#publisher").combobox('setValue',data.publisher);
				$("#price").textbox('setValue',data.price);
				if($("#pack")){
					$("#pack").textbox('setValue',data.pack);
				}
			}
		},
		error:function(){
			$.messager.alert("操作提示","数据错误，联系管理员！","error");
			return;
		}
	});
}

