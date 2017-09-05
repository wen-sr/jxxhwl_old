package com.jxxhwl.common.action;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.common.entity.Authority;
import com.jxxhwl.common.entity.User;
import com.jxxhwl.common.service.UserService;
import com.jxxhwl.jc.common.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction implements ModelDriven<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	User user = new User();
	@Override
	public User getModel() {
		return user;
	}
	

	//注入service
	@Resource
	private UserService userService;
	/**
	 * 登录
	 * @return
	 * @throws Exception 
	 */
	public String login() {
		User u = userService.login(user.getId(),user.getPassword());
		if(u != null ){
			session.setAttribute("name", u.getUsername());
			session.setAttribute("id", u.getId());
			session.setAttribute("user", u);
			return "login_success";
		}else{
			this.addActionError("用户名或密码错误");
			return "login_failure";
		}
	}
	/**
	 * 安全退出
	 * @return
	 */
	public String logout(){
		session.removeAttribute("name");
		session.removeAttribute("id");
		session.removeAttribute("user");
		return "logout";
	}
	/**
	 * 修改密码
	 * @return
	 */
	public String pwd (){
		user.setId((String) session.getAttribute("id"));
		ok = userService.updatePWD(user);
		return OK;
	}
	/**
	 * 获得所有部门信息
	 * @return
	 */
	public String department(){
		List<User> list = userService.getAllDepartments();
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 查询员工
	 * @return
	 */
	public String loadUser(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map = userService.loadUser(currentPage,pageSize,user);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 添加员工
	 * @return
	 */
	public String addInfo(){
		ok = userService.add(user);
		return OK;
	}
	
	public String editInfo(){
		ok = userService.edit(user);
		return OK;
	}
	
	public String deleteInfo(){
		ok = userService.delete(user);
		return OK;
	}
	/**
	 * 查看当前权限
	 * @return
	 */
	public String getAuth(){
		List<String> list = userService.getAuth(user.getId());
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 获取人员列表树
	 * @return
	 */
	public String getUserTree(){
		List<Authority> list = userService.getUserTree(user.getDepartId());
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
}
