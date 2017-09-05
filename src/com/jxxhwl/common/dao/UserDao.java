package com.jxxhwl.common.dao;

import java.util.List;

import com.jxxhwl.common.entity.Authority;
import com.jxxhwl.common.entity.User;


public interface UserDao {
	public User getUser(String id,String password);
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
	public List<User> getUser(int currentPage, int pageSize, User user);
	/**
	 * 统计总条数
	 * @param user
	 * @return
	 */
	public int count(User user);
	/**
	 * 添加员工
	 * @param user
	 * @return
	 */
	public int save(User user);
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	public int edit(User user);
	/**
	 * 删除
	 * @param user
	 * @return
	 */
	public int delete(User user);
	/**
	 * 查看权限
	 * @param id
	 * @return
	 */
	public List<String> getAuth(String id);
	/**
	 * 获得列表树
	 * @param departmentId
	 * @return
	 */
	public List<User> getUserTree(String departmentId);
	/**
	 * 获得微信列表树
	 * @param departmentId
	 * @return
	 */
	public List<User> getWxUserTree(String departmentId);
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public int updatePWD(User user);
}
