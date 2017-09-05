package com.jxxhwl.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.BaseDao;
import com.jxxhwl.common.dao.DepartmentDao;
import com.jxxhwl.common.entity.Department;

@Repository
@SuppressWarnings("unchecked")
public class DepartmentDaoImpl extends BaseDao implements DepartmentDao {

	/**
	 * 查询所有部门信息
	 */
	@Override
	public List<Department> findAll() {
		String sql = "select * from department";
		return getJdbcTemplate().query(sql, new RowMapper<Department>() {

			@Override
			public Department mapRow(ResultSet rs, int arg1) throws SQLException {
				Department d = new Department();
				d.setId(rs.getLong("id"));
				d.setName(rs.getString("name"));
				return d;
			}
		});
	}
	
	@Override
	public List<Department> findWx_All() {
		String sql="select distinct b.* from Login a,department b, userInfo c where a.id = c.login_id and a.postid=b.id";
		return getJdbcTemplate().query(sql, new RowMapper<Department>() {

			@Override
			public Department mapRow(ResultSet rs, int arg1) throws SQLException {
				Department d = new Department();
				d.setId(rs.getLong("id"));
				d.setName(rs.getString("name"));
				return d;
			}
		});
	}
	/**
	 * 保存
	 */
	@Override
	public int save(Department department) {
		String sql = "insert into department(name) values(?)";
		return getJdbcTemplate().update(sql, department.getName());
	}
	/**
	 * 修改
	 */
	@Override
	public int update(Department department) {
		String sql = "update department set name=? where id = ? ";
		return getJdbcTemplate().update(sql,department.getName(),department.getId());
	}
	/**
	 * 删除
	 */
	@Override
	public int delete(Department department) {
		String sql = "delete from department where id = ? ";
		return getJdbcTemplate().update(sql,department.getId());
	}

}
