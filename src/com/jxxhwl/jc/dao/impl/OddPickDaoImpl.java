package com.jxxhwl.jc.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.OddPickDao;
import com.jxxhwl.jc.entity.Distribution;

@Repository("oddPickDao")
public class OddPickDaoImpl extends BaseDao implements OddPickDao{
	/**
	 * 加载需零件拣货数据
	 */
	@Override
	public List<Distribution> loadWaitShipData(int pageSize, int currentPage,Distribution distribution) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.caseqty,a.oddpack,a.odd from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and caseqty = 0 and oddpack=0 ";
		String sqladd = "";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
			sqladd += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() +"'";
			sqladd += " and a.subcode='" + distribution.getSubcode() +"'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and c.barcode='" + distribution.getBarcode() +"'";
			sqladd += " and c.barcode='" + distribution.getBarcode() +"'";
		}
		sql += " and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and caseqty = 0 and oddpack=0 ";
		sql += sqladd;
		sql += " order by a.issuenumber,a.subcode ) order by 2,3 ";
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
					d.setCaseqty(rs.getInt("caseqty"));
					d.setOddpack(rs.getInt("oddpack"));
					d.setOdd(rs.getInt("odd"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			return list;
		}
	}
	/**
	 * 统计需零件拣货数据的总条数
	 */
	@Override
	public int countTotal(Distribution distribution) {
		String sql = "select count(*) from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and caseqty = 0 and oddpack=0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() +"'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and c.barcode='" + distribution.getBarcode() +"'";
		}
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 获得pickno
	 */
	@Override
	public String getPickno (){
		String sql = "select pickno from JiaoCaiNumber";
		String s = null;
		try {
			if(callPickno()){
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
	 * 调用存储过程生成pickno
	 * @return
	 */
	public boolean callPickno () {
		
		boolean s = false;
		try {
			getJdbcTemplate().execute("{call JiaoCaiGetPickno()}");
			s = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			return s;
		}
	}
	/**
	 * 生成手拣单
	 */
	@Override
	public int addPickno(String pickno, String id) {
		String sql = "update JiaoCaiCompute set pickno=?,status=2 where id in (" + id + ")";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{pickno});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			return i;
		}
	}
	/**
	 * 加载已发品种
	 */
	@Override
	public List<Distribution> loadShippedData(int pageSize, int currentPage) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher,a.type,a.caseqty,a.oddpack,a.odd,a.pickno from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=2 and caseqty = 0 and oddpack=0 and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=2 and caseqty = 0 and oddpack=0 order by a.issuenumber,a.subcode ) order by 2,3 ";
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
					d.setQtyfree(rs.getInt("qtyfree"));
					String type = rs.getString("type");
					if("0".equals(type)){
						d.setType("预分发");
					}else if("1".equals(type)) {
						d.setType("库存分发");
					}
					d.setCaseqty(rs.getInt("caseqty"));
					d.setOddpack(rs.getInt("oddpack"));
					d.setOdd(rs.getInt("odd"));
					d.setPickno(rs.getString("pickno"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			return list;
		}
	}
	/**
	 * 统计已发品种的总条数
	 */
	@Override
	public int countTotal_1() {
		String sql = "select count(*) from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=2 and caseqty = 0 and oddpack=0";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据期号查询需发品种的征订代码
	 */
	@Override
	public List<Distribution> loadShipSubcodeByIssuenumber(Distribution distribution) {
		String sql = "select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,sum(a.qtyallocated) qtyallocated,c.publisher,d.shortname shortname_publisher from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and caseqty = 0 and oddpack=0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() +"'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and c.barcode='" + distribution.getBarcode() +"'";
		}
		sql += "group by a.issuenumber,a.subcode,c.barcode,c.descr,c.price,c.publisher,d.shortname order by 1,2";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setPublisher(rs.getString("shortname_publisher"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setBarcode(rs.getString("barcode"));
					d.setStorerkey(rs.getString("publisher"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			return list;
		}
	}
	/**
	 * 根据期号查询已发品种的征订代码
	 */
	@Override
	public List<Distribution> loadshippedSubcodeByIssuenumber(String issuenumber) {
		String sql = "select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,sum(a.qtyallocated) qtyallocated,c.publisher,d.shortname shortname_publisher from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=2 and caseqty = 0 and oddpack=0  and a.issuenumber = ? group by a.issuenumber,a.subcode,c.barcode,c.descr,c.price,c.publisher,d.shortname order by 1,2 ";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql,new Object[]{issuenumber}, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setPublisher(rs.getString("shortname_publisher"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setBarcode(rs.getString("barcode"));
					d.setStorerkey(rs.getString("publisher"));
					return d;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			return list;
		}
	}
	/**
	 * 根据期号和正定代码查询需拣记录
	 */
	@Override
	public List<Distribution> loadWaitShipDataBySubcode(int pageSize,
			int currentPage,  Distribution distribution) {
		String sql = "select top "+ pageSize +" a.issuenumber,a.subcode,c.barcode,c.descr,c.price,sum(a.qtyallocated) qtyallocated,c.publisher,d.shortname shortname_publisher from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and caseqty = 0 and oddpack=0 ";
		String sqladd = "";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
			sqladd += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() +"'";
			sqladd += " and a.subcode='" + distribution.getSubcode() +"'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and c.barcode='" + distribution.getBarcode() +"'";
			sqladd += " and c.barcode='" + distribution.getBarcode() +"'";
		}
		sql += " and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and caseqty = 0 and oddpack=0 ";
		sql += sqladd;
		sql += " order by a.issuenumber,a.subcode ) group by a.issuenumber,a.subcode,c.barcode,c.descr,c.price,c.publisher,d.shortname order by 1,2";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql,new Object[]{}, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setPublisher(rs.getString("shortname_publisher"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setBarcode(rs.getString("barcode"));
					d.setStorerkey(rs.getString("publisher"));
					double amt = rs.getInt("qtyallocated") * rs.getDouble("price");
					BigDecimal b = new BigDecimal(amt);  
					double dou = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					d.setAmt(dou);
					return d;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			return list;
		}
	}
	/**
	 * 统计根据期号和正定代码查询需拣记录总条数
	 */
	@Override
	public int CountTotal_2( Distribution distribution) {
		String sql = "select count(*) from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and caseqty = 0 and oddpack=0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() +"'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and c.barcode='" + distribution.getBarcode() +"'";
		}
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据期号和正定代码查询已拣记录
	 */
	@Override
	public List<Distribution> loadShippedDataBySubcode(int pageSize,
			int currentPage, Distribution distribution) {
		String sql = "select top "+ pageSize +" a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.pack,a.code,a.shortname,pickno from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status in (2,3,4) and caseqty = 0 and oddpack=0 ";
		String sqladd = "";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
			sqladd += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() +"'";
			sqladd += " and a.subcode='" + distribution.getSubcode() +"'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and c.barcode='" + distribution.getBarcode() +"'";
			sqladd += " and c.barcode='" + distribution.getBarcode() +"'";
		}
		sql += " and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status in (2,3,4) and caseqty = 0 and oddpack=0 ";
		sql += sqladd;
		sql += " order by a.issuenumber,a.subcode ) order by 1,2";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql,new Object[]{}, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setPublisher(rs.getString("shortname_publisher"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setBarcode(rs.getString("barcode"));
					d.setStorerkey(rs.getString("publisher"));
					double amt = rs.getInt("qtyallocated") * rs.getDouble("price");
					BigDecimal b = new BigDecimal(amt);  
					double dou = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					d.setAmt(dou);
					d.setPack(rs.getInt("pack"));
					d.setCode(rs.getString("code"));
					d.setShortname(rs.getString("shortname"));
					d.setPickno(rs.getString("pickno"));
					return d;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			return list;
		}
	}
	/**
	 * 统计根据期号和正定代码查询已拣记录总条数
	 */
	@Override
	public int CountTotal_3(Distribution distribution) {
		String sql = "select count(*) from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status in (2,3,4) and caseqty = 0 and oddpack=0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() +"'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and c.barcode='" + distribution.getBarcode() +"'";
		}
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
}

