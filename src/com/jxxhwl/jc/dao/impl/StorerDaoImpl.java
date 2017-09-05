package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.StorerDao;
import com.jxxhwl.jc.entity.Storer;
@Repository("storerDao")
public class StorerDaoImpl extends BaseDao implements StorerDao{
	/**
	 * 用来将一条记录转换成一个对象
	 * @author Administrator
	 *
	 */
	private class StorerRowMapper implements RowMapper{

        public Storer mapRow(ResultSet rs, int i) throws SQLException{
        	Storer s = new Storer();
        	s.setId(rs.getString("id"));
        	String type = rs.getString("type");
        	if("0".equals(type)){
        		s.setType("客户");
        	}else if("1".equals(type)){
        		s.setType("供商");
        	}else {
        		s.setType("其他");
        	}
        	s.setShortname(rs.getString("shortname"));
        	s.setStorerkey(rs.getString("storerkey"));
        	s.setAddress(rs.getString("address1"));
        	s.setContact(rs.getString("contact"));
        	s.setPhone(rs.getString("phone"));
        	return s;
        }
    }
	
	/**
	 * 获取客户信息
	 */
	@Override
	public List<Storer> getCustomer(){
		String sql = "select * from JiaoCaiStorer where type = 0";
		List<Storer> list = new ArrayList<Storer>();
		list = getJdbcTemplate().query(sql, new StorerRowMapper());
		return list;
	}

	/**
	 * 获取供应商信息
	 */
	@Override
	public List<Storer> getSupplier() {
		String sql = "select * from JiaoCaiStorer where type = 1";
		List<Storer> list = new ArrayList<Storer>();
		list = getJdbcTemplate().query(sql, new StorerRowMapper());
		return list;
	}

	/**
	 * 根据代码查询客户信息
	 */
	@SuppressWarnings("finally")
	@Override
	public Storer findByStorerkey(String storerkey) {
		String sql = "select * from JiaoCaiStorer where storerkey= ?";
		Storer storer = null;
		try {
			storer = (Storer) getJdbcTemplate().queryForObject(sql, new Object[]{storerkey}, new StorerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} finally {
			return storer;
		}
	}
	/**
	 * 获取所有信息
	 */
	@Override
	public List<Storer> getInfo(int currentPage, int pageSize) {
		String sql = "select top "+ pageSize +" * from JiaoCaiStorer where id not in (select top "+ (currentPage-1)*pageSize +" id from JiaoCaiStorer order by id ) order by id ";
		List<Storer> list = null;
		try {
			list = getJdbcTemplate().query(sql, new StorerRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 统计总条数
	 */
	@Override
	public int countTotal() {
		String sql = "select count(*) from JiaoCaiStorer";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 保存
	 */
	@Override
	public int save(Storer storer) {
		String sql = "insert into JiaoCaiStorer(storerkey,shortname,type,address1,contact,phone) values(?,?,?,?,?,?)";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{storer.getStorerkey(),storer.getShortname(),storer.getType(),storer.getAddress(),storer.getContact(),storer.getPhone()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 删除
	 */
	@Override
	public int delete(String id) {
		String sql = "delete from JiaoCaiStorer where id = ?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据storerkey查询
	 */
	@Override
	public List<Storer> queryByStorerkey(String storerkey) {
		List<Storer> list = null;
		String sql = "select * from JiaoCaiStorer where storerkey = ?";
		try {
			list = getJdbcTemplate().query(sql, new Object[]{storerkey}, new StorerRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 根据shortname查询
	 */
	@Override
	public List<Storer> queryByShortname(String shortname) {
		List<Storer> list = null;
		String sql = "select * from JiaoCaiStorer where shortname like ?";
		try {
			list = getJdbcTemplate().query(sql, new Object[]{"%" + shortname + "%"}, new StorerRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 根据type查询
	 */
	@Override
	public List<Storer> queryByType(String type, int currentPage, int pageSize) {
		List<Storer> list = null;
		String sql = "select top "+ pageSize +" * from JiaoCaiStorer where type = ? and id not in (select top "+ (currentPage-1)*pageSize +" id from JiaoCaiStorer where type = ?)";
		try {
			list = getJdbcTemplate().query(sql, new Object[]{type, type}, new StorerRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 统计总条数
	 */
	@Override
	public int countTotal(String type) {
		String sql = "select count(*) from JiaoCaiStorer where type = ?";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,new Object[]{type},Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 修改
	 */
	@Override
	public int edit(Storer storer) {
		int i = 0;
		String sql = "update JiaoCaiStorer set storerkey = ?, shortname= ? ,type = ?, phone= ?, contact = ?, address1 = ? where id = ?";
		try {
			i = getJdbcTemplate().update(sql, new Object[]{storer.getStorerkey(),storer.getShortname(),storer.getType(),storer.getPhone(),storer.getContact(),storer.getAddress(),storer.getId()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
}
