package com.jxxhwl.wx.dao.impl;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jxxhwl.wx.dao.WXMessageDao;
@Repository
public class WXMessageDaoImpl extends JdbcDaoSupport implements WXMessageDao{
	//ע��datasource
	@Resource(name="dataSource1")
	public void setMyDataSource(DataSource ds ) {
		super.setDataSource(ds);
	}
	
	@Override
	public String print() {
		String s = (String) super.getJdbcTemplate().queryForObject("select max(virtualloc) from cbpg",String.class);
		return s;
	}
	
}
