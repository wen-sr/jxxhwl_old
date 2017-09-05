package com.jxxhwl.common.dao;

import java.util.List;
import java.util.Set;

import com.jxxhwl.common.entity.AuthorityMid;
import com.jxxhwl.common.entity.Role;

public interface RoleDao {
	/**
	 * 查询所有部门信息
	 * @return
	 */
	public List<Role> findAll();
	/**
	 * 保存
	 * @param department
	 * @return
	 */
	public int save(Role role);
	/**
	 * 修改
	 * @param department
	 * @return
	 */
	public int update(Role role);
	/**
	 * 
	 * @param department
	 * @return
	 */
	public int delete(Role role);
	/**
	 * 根据角色id获取权限权限列表
	 * @param id
	 * @return
	 */
	public List<AuthorityMid> roleDao(long id);
	/**
	 * 删除原来的权限
	 * @param id
	 * @return
	 */
	public int deleteAuth(long id);
	/**
	 * 保存权限
	 * @param id
	 * @param s
	 */
	public int saveAuth(long id, String s);
	/**
	 * 删除原来的角色
	 * @param login_id
	 */
	public void deleteRole(String login_id);
	/**
	 * 保存角色
	 * @param login_id
	 * @param role_id
	 * @return
	 */
	public int saveRole(String login_id,String role_id);
	/**
	 * 查询父节点
	 * @param str
	 * @return
	 */
	public Set<String> findFather(String str);
}
