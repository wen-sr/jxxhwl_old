package com.jxxhwl.jc.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.OddPackDao;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Storer;

@Repository("oddPackDao")
public class OddPackDaoImpl extends BaseDao implements OddPackDao {

	/**
	 * 加载需要打包的客户
	 */
	@Override
	public List<Storer> loadCustomer() {
		String sql = "select distinct code,shortname from JiaoCaiCompute where status in ('2','3')";
		List<Storer> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Storer mapRow(ResultSet rs, int arg1) throws SQLException {
					Storer storer = new Storer();
					storer.setStorerkey(rs.getString("code"));
					storer.setShortname(rs.getString("shortname"));
					return storer;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 加载刷书信息
	 */
	@Override
	public List<Distribution> loadPackData(int pageSize, int currentPage,
			String issuenumber, String code, String status) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.caseqty,a.oddpack,a.odd,a.pickno from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status= ? ";
		String sqladd = "";
		if( !"".equals(issuenumber) && issuenumber != null ){
			sql += " and a.issuenumber = '" + issuenumber + "'";
			sqladd += " and a.issuenumber = '" + issuenumber + "'";
		}
		if(!"".equals(code) && code != null ){
			sql += " and a.code = '" + code + "'";
			sqladd += " and a.code = '" + code + "'";
		}
		sql += " and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=? ";
		sql += sqladd;
		sql += " order by a.issuenumber,a.subcode) order by 2,3";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql,new Object[]{status,status}, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setId(rs.getString("id"));
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setPublisher(rs.getString("shortname_publisher"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setPack(rs.getInt("pack"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setBarcode(rs.getString("barcode"));
					d.setShortname(rs.getString("shortname_customer"));
					d.setCode(rs.getString("code"));
					d.setStorerkey(rs.getString("publisher"));
					double amt = rs.getInt("qtyallocated") * rs.getDouble("price");
					BigDecimal b = new BigDecimal(amt);  
					double dou = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					d.setAmt(dou);
					d.setCaseqty(rs.getInt("caseqty"));
					d.setOddpack(rs.getInt("oddpack"));
					d.setOdd(rs.getInt("odd"));
					d.setBatchno(rs.getString("pickno"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 统计已刷信息
	 */
	@Override
	public int CountTotal(String issuenumber, String code, String status) {
		String sql = "select COUNT(*) from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status= ? ";
		if( !"".equals(issuenumber) && issuenumber != null ){
			sql += " and a.issuenumber = '" + issuenumber + "'";
		}
		if(!"".equals(code) && code != null ){
			sql += " and a.code = '" + code + "'";
		}
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,new Object[]{status},Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 扫条码查询打包信息
	 */
	@Override
	public List<Distribution> findByBarcode(String issuenumber, String code,
			String barcode) {
		String sql = "select a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.odd,a.pickno from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=2";
		if( !"".equals(issuenumber) && issuenumber != null ){
			sql += " and a.issuenumber = '" + issuenumber + "'";
		}
		if(!"".equals(code) && code != null ){
			sql += " and a.code = '" + code + "'";
		}
		if(!"".equals(barcode) && barcode != null ){
			sql += " and c.barcode = '" + barcode + "'";
		}
		sql += " union select a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.odd,a.pickno from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=2";
		if( !"".equals(issuenumber) && issuenumber != null ){
			sql += " and a.issuenumber = '" + issuenumber + "'";
		}
		if(!"".equals(code) && code != null ){
			sql += " and a.code = '" + code + "'";
		}
		if(!"".equals(barcode) && barcode != null ){
			sql += " and a.subcode = '" + barcode + "'";
		}
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setId(rs.getString("id"));
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setPublisher(rs.getString("shortname_publisher"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setPack(rs.getInt("pack"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setBarcode(rs.getString("barcode"));
					d.setShortname(rs.getString("shortname_customer"));
					d.setCode(rs.getString("code"));
					d.setStorerkey(rs.getString("publisher"));
					double amt = rs.getInt("qtyallocated") * rs.getDouble("price");
					BigDecimal b = new BigDecimal(amt);  
					double dou = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					d.setAmt(dou);
					d.setOdd(rs.getInt("odd"));
					d.setBatchno(rs.getString("pickno"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 确认打包信息
	 */
	@Override
	public int confirmPack(String id,String addwho) {
		int i = 0;
		String sql = "UPDATE JiaoCaiCompute set status=3,packwho=?,packdate=getdate() where id = ? and status=2";
		try {
			i = getJdbcTemplate().update(sql, new Object[]{addwho,id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 取消打包信息
	 */
	@Override
	public int cancelPack(String id) {
		int i = 0;
		String sql = "UPDATE JiaoCaiCompute set status=2,packwho=null,packdate=null where id in(" + id + ")  and status=3";
		try {
			i = getJdbcTemplate().update(sql);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	
	/**
	 * 获取新批次号
	 */
	@Override
	public String getBatchno() {
		String sql = "select batchno from JiaoCaiNumber";
		String s = null;
		try {
			if(callBatchno()){
				s = (String) getJdbcTemplate().queryForObject(sql, String.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			return s;
		}
	}
	/**
	 * 调用存储过程生成新的批次号
	 * @return
	 */
	private boolean callBatchno () {
		boolean s = false;
		try {
			getJdbcTemplate().execute("{call JiaoCaiGetBatchno()}");
			s = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			return s;
		}
	}
	/**
	 * 调用存储过程生成新的运号
	 * @return
	 */
	private boolean callShipno () {
		boolean s = false;
		try {
			getJdbcTemplate().execute("{call JiaoCaiGetShipno()}");
			s = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			return s;
		}
	}
	
	/**
	 * 获得新运号
	 * @return
	 */
	@Override
	public String getShipno(){
		String sql = "select shipno from JiaoCaiNumber";
		String s = null;
		try {
			if(callShipno()){
				s = (String) getJdbcTemplate().queryForObject(sql, String.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			return s;
		}
	}
	/**
	 * 添加批次信息
	 */
	@Override
	public int addBatchno(String id, String batchno, String shipno) {
		String sql = "UPDATE JiaoCaiCompute set status=4,batchno=?,shipno=?,editdate=getdate() where id in (" + id +") and status=3 ";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{batchno,shipno});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
}
