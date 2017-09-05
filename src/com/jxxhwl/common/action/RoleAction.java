package com.jxxhwl.common.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.common.BaseAction;
import com.jxxhwl.common.entity.Authority;
import com.jxxhwl.common.entity.Role;
import com.jxxhwl.common.service.RoleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction implements ModelDriven<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Role role = new Role();
	@Override
	public Role getModel() {
		return role;
	}
	@Autowired RoleService roleService;
	
	/**
	 *  加载部门信息
	 * @return
	 */
	public String loadRole(){
		List<Role> list = roleService.loadRole();
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 添加
	 * @return
	 */
	public String addInfo(){
		ok = roleService.save(role);
		return OK;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String editInfo(){
		ok = roleService.edit(role);
		return OK;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteInfo(){
		ok = roleService.delete(role);
		return OK;
	}
	
	/**
	 * 获取权限列表树
	 * @return
	 */
	public String getAuth(){
		List<Authority> list = roleService.getAuth(role.getId());
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 确认权限
	 * @return
	 */
	public String comfirmAuth(){
		String[] ids = role.getName().split(",");
		ok = roleService.comfirmAuth(role.getId(),ids);
		return OK;
	}
	/**
	 * 提交角色
	 * @return
	 */
	public String updateRole(){
		String[] ids = role.getName().split(",");
		String login_id = String.valueOf(role.getLogin_id());
		ok = roleService.updateRole(login_id,ids);
		return OK;
	}
}
