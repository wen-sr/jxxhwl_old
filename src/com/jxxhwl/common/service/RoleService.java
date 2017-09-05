package com.jxxhwl.common.service;

import java.util.List;

import com.jxxhwl.common.entity.Authority;
import com.jxxhwl.common.entity.Department;
import com.jxxhwl.common.entity.Role;

public interface RoleService {
	/**
	 * 加载部门信息
	 * @return
	 */
	public List<Role> loadRole();
	/**
	 *  保存
	 * @param department
	 * @return
	 */
	public String save(Role role);
	/**
	 * 修改
	 * @param department
	 * @return
	 */
	public String edit(Role role);
	/**
	 * 删除
	 * @param department
	 * @return
	 */
	public String delete(Role role);
	/**
	 * 根据角色id获取权限权限列表
	 * @param id
	 * @return
	 */
	public List<Authority> getAuth(long id);
	/**
	 * 确认权限
	 * @param id
	 * @param ids
	 */
	public String comfirmAuth(long id, String[] ids);
	
	/**
	 * 确认角色
	 * @param login_id
	 * @param ids
	 * @return
	 */
	public String updateRole(String login_id, String[] ids);

}
