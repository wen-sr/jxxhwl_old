package com.jxxhwl.jc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.UserDao;
import com.jxxhwl.jc.entity.User;
import com.jxxhwl.jc.service.UserService;
//@Service("userService")
//@Transactional
public class UserServiceImpl implements UserService{

	//ע��dao
	@Resource(name="userDao")
	private UserDao userDao;
	/**
	 * ��¼
	 * @throws Exception 
	 */
	@Override
	public User login(String id, String password) {
		User u = userDao.getUser(id, password);
		return u;
	}

}
