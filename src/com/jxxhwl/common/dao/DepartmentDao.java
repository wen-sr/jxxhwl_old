package com.jxxhwl.common.dao;

import java.util.List;

import com.jxxhwl.common.entity.Department;

public interface DepartmentDao {
	/**
	 * 查询所有部门信息
	 * @return
	 */
	public List<Department> findAll();
	/**
	 * 绑定微信的部门
	 * @return
	 */
	public List<Department> findWx_All();
	/**
	 * 保存
	 * @param department
	 * @return
	 */
	public int save(Department department);
	/**
	 * 修改
	 * @param department
	 * @return
	 */
	public int update(Department department);
	/**
	 * 
	 * @param department
	 * @return
	 */
	public int delete(Department department);

}
