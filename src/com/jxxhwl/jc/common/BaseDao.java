package com.jxxhwl.jc.common;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.jxxhwl.jc.entity.Inventory;

public class BaseDao extends JdbcDaoSupport{
	
	@Resource(name="dynamicDataSource")
	public void setMyDatasource (DataSource ds ) {
		super.setDataSource(ds);
	}
	
}
