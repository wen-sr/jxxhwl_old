package com.jxxhwl.common.service;

import java.util.List;

import com.jxxhwl.common.entity.Department;

public interface DepartmentService {
	/**
	 * 加载部门信息
	 * @return
	 */
	public List<Department> loadDepartment();
	/**
	 *  保存
	 * @param department
	 * @return
	 */
	public String save(Department department);
	/**
	 * 修改
	 * @param department
	 * @return
	 */
	public String edit(Department department);
	/**
	 * 删除
	 * @param department
	 * @return
	 */
	public String delete(Department department);

}
