package com.jxxhwl.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.common.dao.DepartmentDao;
import com.jxxhwl.common.entity.Department;
import com.jxxhwl.common.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{
	@Autowired
	private DepartmentDao departmentDao;
	/**
	 * 加载部门信息
	 */
	@Override
	public List<Department> loadDepartment() {
		List<Department> list = departmentDao.findAll();
		return list;
	}
	/**
	 * 保存
	 */
	@Override
	public String save(Department department) {
		int i = departmentDao.save(department);
		if(i > 0 ){
			return "保存成功";
		}
		return "保存失败";
	}
	/**
	 * 修改
	 */
	@Override
	public String edit(Department department) {
		int i = departmentDao.update(department);
		if(i > 0 ){
			return "修改成功";
		}
		return "修改失败";
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(Department department) {
		int i = departmentDao.delete(department);
		if(i > 0 ){
			return "删除成功";
		}
		return "删除失败";
	}
	
}
