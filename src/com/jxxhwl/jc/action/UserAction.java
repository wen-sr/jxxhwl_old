package com.jxxhwl.jc.action;


import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.User;
import com.jxxhwl.jc.service.UserService;

//@Controller
//@Scope("prototype")
public class UserAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String password;
	public void setId(String id) {
		this.id = id;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//注入service
	@Resource
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	/**
	 * 登录
	 * @return
	 * @throws Exception 
	 */
	public String login() {
		User u = userService.login(id,password);
		if(u != null ){
			session.setAttribute("name", u.getUsername());
			session.setAttribute("id", u.getId());
			return "login_success";
		}else{
			this.addActionError("用户名或密码错误");
			return "login_failure";
		}
	}
}
