//$(function(){
//	var batchno = $("#batchno").val();
//	$.ajax({
//		type:'POST',
//		url:'jc/print_allocationList.action',
//		data:'batchno=' + batchno,
//		dataType:'json',
//		success:function(data){
//			if(data == null){
//				$.messager.alert("操作提示","没有对应的调拨单","error");
//				return;
//			}
//			
//		},
//		error:function(){
//			alert("数据错误，联系管理员");
//		}
//	});
//});

function doJqprint(){
	$("#d").jqprint();
}