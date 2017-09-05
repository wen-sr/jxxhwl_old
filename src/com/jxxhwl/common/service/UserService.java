package com.jxxhwl.common.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.common.entity.Authority;
import com.jxxhwl.common.entity.User;


public interface UserService {
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);
	/**
	 * 获得所有部门信息
	 * @return
	 */
	public List<User> getAllDepartments();
	/**
	 * 获得用户信息
	 * @param currentPage
	 * @param pageSize
	 * @param user
	 * @return
	 */
	public Map<String, Object> loadUser(int currentPage, int pageSize, User user);
	/**
	 * 添加员工
	 * @param user
	 * @return
	 */
	public String add(User user);
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	public String edit(User user);
	/**
	 * 删除
	 * @param user
	 * @return
	 */
	public String delete(User user);
	/**
	 * 查看权限
	 * @param id
	 * @return
	 */
	public List<String> getAuth(String id);
	/**
	 * 获得用户列表树
	 * @return
	 */
	public List<Authority> getUserTree(String postid);
	/**
	 * 获得微信用户列表树
	 * @param postid
	 * @return
	 */
	public List<Authority> getWxUserTree(String postid);
	/**
	 * 修改密码
	 * @param user
	 */
	public String updatePWD(User user);
}
