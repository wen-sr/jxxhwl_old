package com.jxxhwl.jc.dao;

import com.jxxhwl.jc.entity.User;

public interface UserDao {
	public User getUser(String id,String password);
}
