package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.dao.UserDao;
import com.jxxhwl.jc.entity.User;

//@Repository("userDao")
public class UserDaoImpl extends JdbcDaoSupport implements UserDao{
	
	@Resource(name="dataSource3")
	public void setMyDatasource (DataSource ds ) {
		super.setDataSource(ds);
	}
	/**
	 * 登录
	 */
	@SuppressWarnings("finally")
	@Override
	public User getUser(String id, String password){
		String sql = "select name,id,pwd from login where id = ? and pwd = ?";
		
		User user = null;
		try {
			user = ((User) getJdbcTemplate().queryForObject(sql, new Object[]{id,password}, new RowMapper(){

				@Override
				public User mapRow(ResultSet rs, int i) throws SQLException {
					User user = new User();
					user.setId(rs.getString("id"));
					user.setUsername(rs.getString("name"));
					user.setPassword(rs.getString("pwd"));
					return user;
				}
			}));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} finally{
			return user;
		}
	}
	

}
