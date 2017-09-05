package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.IssuenumberDao;
import com.jxxhwl.jc.entity.Issuenumber;

@Repository("issuenumberDao")
public class IssuenumberDaoImpl extends BaseDao implements IssuenumberDao {
	
	/**
	 * Issuenumber 映射类
	 * @author liujie
	 *
	 */
	public class IssuenumberRowMapper implements RowMapper{

		@Override
		public Issuenumber mapRow(ResultSet rs, int arg1) throws SQLException {
			Issuenumber i = new Issuenumber();
			i.setId(rs.getString("id"));
			i.setIssuenumber(rs.getString("issuenumber"));
			if(  "0".equals(rs.getString("status"))){
				i.setStatus("可用");
			}else {
				i.setStatus("不可用");
			}
			i.setNote(rs.getString("note"));
			return i;
		}
		
	}
	/**
	 * 保存期号信息
	 */
	@Override
	public int save(Issuenumber i) {
		String sql = "insert into JiaoCaiIssuenumber(issuenumber,note) values (?,?)";
		//返回的是影响的行数
		return getJdbcTemplate().update(sql, new Object[]{i.getIssuenumber(),i.getNote()});
	}

	/**
	 * 根据id删除期号信息
	 */
	@Override
	public int delete(String id) {
		String sql = "delete from JiaoCaiIssuenumber where id = ?";
		return getJdbcTemplate().update(sql, new Object[]{id});
	}

	/**
	 * 查询所有期号信息
	 */
	@Override
	public List<Issuenumber> getAll() {
		List<Issuenumber> list = new ArrayList<Issuenumber>();
		String sql = "select * from JiaoCaiIssuenumber";
		list = getJdbcTemplate().query(sql, new IssuenumberRowMapper());
		return list;
	}

	/**
	 * 根据id查询期号信息
	 */
	@Override
	public Issuenumber findById(String id) {
		String sql = "select * from JiaoCaiIssuenumber where id = " + id;
		return (Issuenumber) getJdbcTemplate().queryForObject(sql, new IssuenumberRowMapper());
	}

	/**
	 * 根据期号查询
	 */
	@SuppressWarnings("finally")
	@Override
	public Issuenumber findByIssuenumber(String issuenumber) {
		String sql = "select * from JiaoCaiIssuenumber where issuenumber = ?";
		Issuenumber i = null;
		try {
			i = (Issuenumber) getJdbcTemplate().queryForObject(sql,new Object[]{issuenumber}, new IssuenumberRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} finally {
			return i;
		}
	}
	/**
	 * 获取简称
	 */
	@Override
	public List<Issuenumber> getIssue() {
		String sql = "select distinct SUBSTRING(note,1,3) from JiaoCaiIssuenumber";
		List<Issuenumber> list = this.getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public Issuenumber mapRow(ResultSet rs, int arg1) throws SQLException {
				Issuenumber i = new Issuenumber();
				i.setNote(rs.getString(1));
				return i;
			}
		});
		return list;
	}
}
