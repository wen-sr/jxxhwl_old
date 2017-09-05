package com.jxxhwl.jc.test;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.DatabaseContextHolder;

@Repository
public class TestTransactionDao2 extends JdbcDaoSupport{
	@Resource(name="dynamicDataSource")
	public void setMyDatasource (DataSource ds ) {
		super.setDataSource(ds);
	}
	public void save(String s ){
//		DatabaseContextHolder.setDBType(DatabaseContextHolder.DATA_SOURCE_1);
		String sql = "insert into test2(name) values (?)";
		getJdbcTemplate().update(sql,new Object[]{s});
	}
}
