/**
 * 回车事件
 */
function enterKey(){
	$('#boxno').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	var boxno = $("#boxno").textbox('getValue');
        	if(boxno == ""){
        		return;
        	}
        	$("#loc").html('');
			$("#shortname").html('');
			$("#bx").html('');
			$("#qty").html('');
        	$.ajax({
        		type:'post',
        		url:'yb/kt_bingXcha.action',
        		data:'boxno=' + boxno,
        		dataType:'json',
        		success:function(data){
        			if(data){
        				$("#loc").html(data.loc);
        				$("#shortname").html(data.shortname);
        				$("#bx").html(data.boxno);
        				$("#qty").html("总册数：" + data.qty);
        				$("#boxno").textbox('clear');
        			}else{
        				alert("此箱号无记录");
        				$("#boxno").textbox('clear');
        			}
        		},
        		error:function(){
        			$.messager.alert("操作提示","数据错误，联系管理员！","error");
        			return;
        		}
        	});
        }
    });
}

$(function(){
	$('#boxno').textbox('textbox').focus();
	enterKey();
});

