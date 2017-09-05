package com.jxxhwl.jc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.SkuDao;
import com.jxxhwl.jc.entity.Sku;
import com.mysql.jdbc.Statement;
@SuppressWarnings("unchecked")
@Repository("skuDao")
public class SkuDaoImpl extends BaseDao implements SkuDao {
	
	/**
	 * sku的映射类
	 * @author liujie
	 *
	 */
	
	private class SkuRowMapper implements RowMapper {

		@Override
		public Sku mapRow(ResultSet rs, int i) throws SQLException {
			Sku sku = new Sku();
			sku.setId(rs.getString("id"));
			sku.setAdddate(rs.getString("adddate"));
			sku.setAddwho(rs.getString("addwho"));
			sku.setBarcode(rs.getString("barcode"));
			sku.setDescr(rs.getString("descr"));
			sku.setEditdate(rs.getString("editdate"));
			sku.setEditwho(rs.getString("editwho"));
			sku.setIssuenumber(rs.getString("issuenumber"));
			sku.setPack(rs.getInt("pack"));
			sku.setPrice(rs.getDouble("price"));
			sku.setPublisher(rs.getString("publisher"));
			sku.setSubcode(rs.getString("subcode"));
			sku.setShortname(rs.getString("shortname"));
			sku.setBundle(rs.getInt("bundle"));
			return sku;
		}
		
	}
	
	/**
	 * 保存图书信息
	 */
	@Override
	public Sku save(final Sku sku) {
		final String sql = "insert into JiaoCaiSku(issuenumber,subcode,barcode,descr,publisher,price,pack,addwho,adddate,editwho,editdate,bundle) values(?,?,?,?,?,?,?,?,getdate(),?,getdate(),?)";
		try{
			//利用这个对象将id带出来
			KeyHolder keyHolder=new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	                ps.setString(1,sku.getIssuenumber());
	                ps.setString(2, sku.getSubcode());
	                ps.setString(3, sku.getBarcode());
	                ps.setString(4, sku.getDescr());
	                ps.setString(5, sku.getPublisher());
	                ps.setDouble(6, sku.getPrice());
	                ps.setInt(7, sku.getPack());
	                ps.setString(8, sku.getAddwho());
	                ps.setString(9, sku.getAddwho());
	                ps.setInt(10, sku.getBundle());
	                return ps;
	            }
	        },keyHolder);
			//获取id
			sku.setId(keyHolder.getKey().toString());
		}catch (Exception e) {
			throw new RuntimeException(e);
		} finally{
			return sku;
		}
	}

	/**
	 * 根据期号和征订号查图书信息 
	 */

	@Override
	public Sku findByIssuenumberAndSubcode(String issuenumber, String subcode) {
		String sql = "select top 1 *,(select shortname from JiaoCaiStorer where JiaoCaiSku.publisher=JiaoCaiStorer.storerkey ) shortname from JiaoCaiSku where issuenumber = ? and subcode = ?";
		Sku sku = null;
		try {
			sku = (Sku) getJdbcTemplate().queryForObject(sql, new Object[]{issuenumber,subcode}, new SkuRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} 
		return sku;
	}
	/**
	 * 查询所有图书资料
	 */
	@Override
	public List<Sku> findAll() {
		String sql = "select *,(select shortname from JiaoCaiStorer where JiaoCaiSku.publisher=JiaoCaiStorer.storerkey ) shortname from JiaoCaiSku ";
		List<Sku> list = null;
		try {
		list = getJdbcTemplate().query(sql, new SkuRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 删除
	 */
	@Override
	public int delete(String id) {
		String sql = "delete from JiaoCaiSku where id = ? ";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Sku> findAllByPage(int pageSize, int currentPage) {
		String sql = "select top "+ pageSize +" id,issuenumber,subcode,barcode,publisher,(select shortname from jiaocaistorer js where publisher=js.storerkey) shortname,descr,price,pack,CONVERT(varchar(12) , adddate, 112 ) adddate,addwho,CONVERT(varchar(12) , editdate, 112 ) editdate,editwho,bundle from JiaoCaiSku c  where id not in (select top " + (currentPage-1)*pageSize + " id from  jiaocaisku ORDER BY adddate DESC, ID DESC) order by adddate desc, ID DESC ";
		List<Sku> list = null;
		try {
			list = getJdbcTemplate().query(sql, new SkuRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return list;
	}
	/**
	 * 总条数
	 */
	@Override
	public int getTotalCount() {
		String sql = "select count(*) from jiaocaisku";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据id修改
	 */
	@Override
	public int update(Sku sku) {
		int i = 0;
		String sql = "update JiaoCaiSku set issuenumber=?,subcode=?,barcode=?,publisher=?,descr=?,price=?,pack=?,editwho=?,editdate=getdate(),bundle=? where id = ? ";
		try {
			i = getJdbcTemplate().update(sql, new Object[]{sku.getIssuenumber(),sku.getSubcode(),sku.getBarcode(),sku.getPublisher(),sku.getDescr(),sku.getPrice(),sku.getPack(),sku.getAddwho(),sku.getBundle(),sku.getId()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据正定代码和期号查询
	 */
	@Override
	public int updateByIssuenumberAndSubcode(Sku sku) {
		int i = 0;
		String sql = "update JiaoCaiSku set barcode=?,publisher=?,descr=?,price=?,pack=?,editwho=?,editdate=getdate() where issuenumber = ? and subcode = ? ";
		try {
			i = getJdbcTemplate().update(sql, new Object[]{sku.getBarcode(),sku.getPublisher(),sku.getDescr(),sku.getPrice(),sku.getPack(),sku.getAddwho(),sku.getIssuenumber(),sku.getSubcode()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 按条件查询
	 */
	@Override
	public List<Sku> query(Sku sku, int currentPage, int pageSize) {
		String sql = "select top "+ pageSize +" *,(select shortname from JiaoCaiStorer where JiaoCaiSku.publisher=JiaoCaiStorer.storerkey ) shortname from JiaoCaiSku ";
		int len = sql.length();
		List<Sku> list = null;
		if(!"".equals(sku.getIssuenumber()) && sku.getIssuenumber() != null){
			if(sql.length() == len){
				sql += "where issuenumber = '" + sku.getIssuenumber() + "'";
			}else{
				sql += "and issuenumber = '" + sku.getIssuenumber() + "'";
			}
		}
		if(!"".equals(sku.getSubcode()) && sku.getSubcode() != null){
			if(sql.length() == len){
				sql += "where subcode like '%" + sku.getSubcode() + "%'";
			}else{
				sql += "and subcode like '%" + sku.getSubcode() + "%'";
			}
		}
		if(!"".equals(sku.getBarcode()) && sku.getBarcode() != null){
			if(sql.length() == len){
				sql += "where barcode like '%" + sku.getBarcode() + "%'";
			}else{
				sql += "and barcode like '%" + sku.getBarcode() + "%'";
			}
		}
		if(!"".equals(sku.getDescr()) && sku.getDescr() != null){
			if(sql.length() == len){
				sql += "where descr like '%" + sku.getDescr() + "%'";
			}else{
				sql += "and descr like '%" + sku.getDescr() + "%'";
			}
		}
		if(!"".equals(sku.getPublisher()) && sku.getPublisher() != null){
			if(sql.length() == len){
				sql += "where publisher = '" + sku.getPublisher() + "'";
			}else{
				sql += "and publisher = '" + sku.getPublisher() + "'";
			}
		}
		if(!"".equals(sku.getAddwho()) && sku.getAddwho() != null){
			if(sql.length() == len){
				sql += "where addwho = '" + sku.getAddwho().toUpperCase() + "'";
			}else{
				sql += "and addwho = '" + sku.getAddwho().toUpperCase() + "'";
			}
		}
		if(!"".equals(sku.getAdddate()) && sku.getAdddate() != null){
			if(sql.length() == len){
				sql += "where CONVERT(varchar(100), adddate, 23) = '" + sku.getAdddate() + "'";
			}else{
				sql += "and CONVERT(varchar(100), adddate, 23) = '" + sku.getAdddate() + "'";
			}
		}
		String sss = sql.substring(len);
		if(sss.length() == 0) {
			sql += " where ";
		}else {
			sql += " and ";
		}
		sql += " id not in (select top "+ (currentPage-1)*pageSize +" id from JiaoCaiSku "+ sss +")";
		try {
			list = getJdbcTemplate().query(sql, new SkuRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 按条件统计
	 */
	@Override
	public int countTotal(Sku sku) {
		String sql = "select count(*) from JiaoCaiSku ";
		int i = 0;
		int len = sql.length();
		if(!"".equals(sku.getIssuenumber()) && sku.getIssuenumber() != null){
			if(sql.length() == len){
				sql += "where issuenumber = '" + sku.getIssuenumber() + "'";
			}else{
				sql += "and issuenumber = '" + sku.getIssuenumber() + "'";
			}
		}
		if(!"".equals(sku.getSubcode()) && sku.getSubcode() != null){
			if(sql.length() == len){
				sql += "where subcode like '%" + sku.getSubcode() + "%'";
			}else{
				sql += "and subcode like '%" + sku.getSubcode() + "%'";
			}
		}
		if(!"".equals(sku.getBarcode()) && sku.getBarcode() != null){
			if(sql.length() == len){
				sql += "where barcode like '%" + sku.getBarcode() + "%'";
			}else{
				sql += "and barcode like '%" + sku.getBarcode() + "%'";
			}
		}
		if(!"".equals(sku.getDescr()) && sku.getDescr() != null){
			if(sql.length() == len){
				sql += "where descr like '%" + sku.getDescr() + "%'";
			}else{
				sql += "and descr like '%" + sku.getDescr() + "%'";
			}
		}
		if(!"".equals(sku.getPublisher()) && sku.getPublisher() != null){
			if(sql.length() == len){
				sql += "where publisher = '" + sku.getPublisher() + "'";
			}else{
				sql += "and publisher = '" + sku.getPublisher() + "'";
			}
		}
		if(!"".equals(sku.getAddwho()) && sku.getAddwho() != null){
			if(sql.length() == len){
				sql += "where addwho = '" + sku.getAddwho().toUpperCase() + "'";
			}else{
				sql += "and addwho = '" + sku.getAddwho().toUpperCase() + "'";
			}
		}
		if(!"".equals(sku.getAdddate()) && sku.getAdddate() != null){
			if(sql.length() == len){
				sql += "where CONVERT(varchar(100), adddate, 23) = '" + sku.getAdddate() + "'";
			}else{
				sql += "and CONVERT(varchar(100), adddate, 23) = '" + sku.getAdddate() + "'";
			}
		}
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 更新期号，征订代码，出版社等业务信息
	 */
	@Override
	public int updateIandSandP(Sku sku) {
		int i = 0;
		String sql = "update JiaoCaiSku set publisher=?,editwho=?,editdate=getdate() where id = ? ";
		i = getJdbcTemplate().update(sql, new Object[]{sku.getPublisher(),sku.getAddwho(),sku.getId()});
		return i;
	}
	/**
	 * 根据id查找
	 */
	@Override
	public Sku findById(String id) {
		String sql = "select *,(select shortname from JiaoCaiStorer where JiaoCaiSku.publisher=JiaoCaiStorer.storerkey ) shortname from JiaoCaiSku where id = ? ";
		List<Sku> list = null;
		list = getJdbcTemplate().query(sql,new Object[]{id}, new SkuRowMapper());
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int saveReturnSku(Sku sku) {
		String sql = "insert into jiaocaiReturnSku(subcode,descr,price,shortname,pack) values(?,?,?,?,?)";
		return this.getJdbcTemplate().update(sql, sku.getSubcode(),sku.getDescr(),sku.getPrice(),sku.getShortname(),sku.getPack());
	}
}
