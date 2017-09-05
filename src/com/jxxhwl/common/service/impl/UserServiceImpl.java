package com.jxxhwl.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.common.dao.DepartmentDao;
import com.jxxhwl.common.dao.UserDao;
import com.jxxhwl.common.entity.Authority;
import com.jxxhwl.common.entity.Department;
import com.jxxhwl.common.entity.User;
import com.jxxhwl.common.service.UserService;


@Service
@Transactional
public class UserServiceImpl implements UserService{

	//注入dao
	@Resource
	private UserDao userDao;
	@Autowired DepartmentDao departmentDao;
	/**
	 * 登录
	 * @throws Exception 
	 */
	@Override
	public User login(String id, String password) {
		User u = userDao.getUser(id, password);
		return u;
	}
	/**
	 * 获得所有部门信息
	 */
	@Override
	public List<User> getAllDepartments() {
		List<User> list = userDao.getAllDepartments();
		return list;
	}
	/**
	 * 获得用户信息
	 */
	@Override
	public Map<String, Object> loadUser(int currentPage, int pageSize, User user) {
		List<User> list = userDao.getUser(currentPage,pageSize,user);
		int count = userDao.count(user);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", count);
		return map;
	}
	/**
	 * 添加员工
	 */
	@Override
	public String add(User user) {
		int i = userDao.save(user);
		if(i > 0){
			return "员工添加成功";
		}
		return "员工添加失败";
	}
	/**
	 * 修改
	 */
	@Override
	public String edit(User user) {
		int i = userDao.edit(user);
		if(i > 0) {
			return "员工信息修改成功";
		}
		return "员工信息修改失败";
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(User user) {
		int i = userDao.delete(user);
		if(i > 0) {
			return "员工信息删除成功";
		}
		return "员工信息删除失败";
	}
	/**
	 * 查看权限
	 */
	@Override
	public List<String> getAuth(String id) {
		List<String> list = userDao.getAuth(id);
		return list;
	}
	/**
	 * 获得用户列表树
	 */
	@Override
	public List<Authority> getUserTree(String departmentId) {
		List<Department> departs = departmentDao.findAll();
		List<User> users = userDao.getUserTree(departmentId);
		List<Authority> list = new ArrayList<Authority>();
		List<Authority> c = new ArrayList<Authority>();
		Authority a0 = new Authority("0","所有");
		Authority a1 = new Authority("01", "所有人");
		Authority a2 = new Authority("02", "所有主管以上");
		c.add(a1);
		c.add(a2);
		a0.setChildren(c);
		list.add(a0);
		for(Department d : departs ){
			Authority u = new Authority();
			u.setId(String.valueOf(d.getId()));
			u.setText(d.getName());
			u.setState("closed");
			List<Authority> children = new ArrayList<Authority>();
			for(User ur : users ){
				if(String.valueOf(u.getId()).equals(ur.getDepartId())){
					Authority auu = new Authority();
					auu.setId(ur.getId());
					auu.setText(ur.getUsername());
					children.add(auu);
				}
			}
			u.setChildren(children);
			list.add(u);
		}
		return list;
	}
	/**
	 * 获得微信用户列表树
	 */
	@Override
	public List<Authority> getWxUserTree(String departmentId) {
		List<Department> departs = departmentDao.findWx_All();
		List<User> users = userDao.getWxUserTree(departmentId);
		List<Authority> list = new ArrayList<Authority>();
		List<Authority> c = new ArrayList<Authority>();
		Authority a0 = new Authority("0","所有");
		Authority a1 = new Authority("01", "所有人");
		Authority a2 = new Authority("02", "所有主管以上");
		c.add(a1);
		c.add(a2);
		a0.setChildren(c);
		list.add(a0);
		for(Department d : departs ){
			Authority u = new Authority();
			u.setId(String.valueOf(d.getId()));
			u.setText(d.getName());
			u.setState("closed");
			List<Authority> children = new ArrayList<Authority>();
			for(User ur : users ){
				if(String.valueOf(u.getId()).equals(ur.getDepartId())){
					Authority auu = new Authority();
					auu.setId(ur.getId());
					auu.setText(ur.getUsername());
					children.add(auu);
				}
			}
			u.setChildren(children);
			list.add(u);
		}
		return list;
	}
	/**
	 * 修改密码
	 */
	@Override
	public String updatePWD(User user) {
		int i = userDao.updatePWD(user);
		if(i > 0) {
			return "密码修改成功";
		}
		return "密码修改失败";
	}
}
