package com.jxxhwl.wx.dao.impl;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JDBCBaseDao extends JdbcDaoSupport {
	//��������ƥ�佫datasourceע��
	@Resource
	public void setMyDataSource(DataSource ds ) {
		super.setDataSource(ds);
	}
}
