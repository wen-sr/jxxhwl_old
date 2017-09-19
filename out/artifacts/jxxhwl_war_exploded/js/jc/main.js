var _menus = {"menus":[
						{"menuid":"1","icon":"icon-sys","menuname":"控件使用",
							"menus":[
									{"menuid":"12","menuname":"疯狂秀才","icon":"icon-add","url":"http://www.mycodes.net"},
									{"menuid":"13","menuname":"用户管理","icon":"icon-users","url":"demo2.html"},
									{"menuid":"14","menuname":"角色管理","icon":"icon-role","url":"demo2.html"},
									{"menuid":"15","menuname":"权限设置","icon":"icon-set","url":"demo.html"},
									{"menuid":"16","menuname":"系统日志","icon":"icon-log","url":"demo1.html"}
								]
						},{"menuid":"8","icon":"icon-sys","menuname":"员工管理",
							"menus":[{"menuid":"21","menuname":"员工列表","icon":"icon-nav","url":"demo.html"},
									{"menuid":"22","menuname":"视频监控","icon":"icon-nav","url":"demo1.html"}
								]
						},{"menuid":"56","icon":"icon-sys","menuname":"部门管理",
							"menus":[{"menuid":"31","menuname":"添加部门","icon":"icon-nav","url":"demo1.html"},
									{"menuid":"32","menuname":"部门列表","icon":"icon-nav","url":"demo2.html"}
								]
						},{"menuid":"28","icon":"icon-sys","menuname":"财务管理",
							"menus":[{"menuid":"41","menuname":"收支分类","icon":"icon-nav","url":"demo.html"},
									{"menuid":"42","menuname":"报表统计","icon":"icon-nav","url":"demo1.html"},
									{"menuid":"43","menuname":"添加支出","icon":"icon-nav","url":"demo2.html"}
								]
						},{"menuid":"39","icon":"icon-sys","menuname":"商城管理",
							"menus":[{"menuid":"51","menuname":"商品分类","icon":"icon-nav","url":"demo.html"},
									{"menuid":"52","menuname":"商品列表","icon":"icon-nav","url":"demo1.html"},
									{"menuid":"53","menuname":"商品订单","icon":"icon-nav","url":"demo2.html"}
								]
						}
				]};
        //设置修改密码窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 200,
                resizable:false
            });
        }
        //关闭修改密码窗口
        function closePwd() {
            $('#w').window('close');
        }

        

        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })
            
        }

        $(function() {

            openPwd();

            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
            })

			$('#btnCancel').click(function(){closePwd();})

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = '/ajax/loginout.ashx';
                    }
                });
            })
        });