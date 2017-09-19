$(function(){
	/*导航跳转效果插件*/
	$(".nav").singlePageNav({
		offset:70
	});
	/*小屏幕导航点击关闭菜单*/
	$(".navbar-collapse a").click(function(){
		$('.navbar-collapse').collapse('hide');
	});

	// 初始化wow插件
	new WOW().init();
});
