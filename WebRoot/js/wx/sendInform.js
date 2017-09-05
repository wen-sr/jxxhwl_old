$(function(){
	loadMember();
});

function loadMember(){
	$("#member").tree({
		url:'user_getUserTree.action'
	});
}

function go(){
	var content = $("#content").val();
	var nodes = $('#member').tree('getChecked');
	var member = '';
	for(var i=0; i<nodes.length; i++){
		if (member != '') member += ',';
		member += nodes[i].id;
	}
	$.ajax({
		type:"post",
		url:'wx/wXFunction_sendInform.action',
		data:'content=' + content + '&member=' + member,
		dataType:'json',
		success:function(data){
			alert(data);
			var content = $("#content").val("");
		}
	});
	
}
