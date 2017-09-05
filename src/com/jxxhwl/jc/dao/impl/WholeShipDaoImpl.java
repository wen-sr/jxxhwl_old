package com.jxxhwl.jc.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.WholeShipDao;
import com.jxxhwl.jc.entity.Distribution;

@Repository("wholeShipDao")
public class WholeShipDaoImpl extends BaseDao implements WholeShipDao{
	/**
	 * 加载需整件发货数据
	 */
	@Override
	public List<Distribution> loadWaitShipData(int pageSize, int currentPage,Distribution distribution) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.bundle,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.type,a.caseqty,a.oddpack,a.odd from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and (caseqty > 0  or (caseqty=0 and oddpack=1) )";
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
		sql += " and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) ";
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
					String type = rs.getString("type");
					if("0".equals(type)){
						d.setType("预分发");
					}else if("1".equals(type)) {
						d.setType("库存分发");
					}
					d.setCaseqty(rs.getInt("caseqty"));
					d.setOddpack(rs.getInt("oddpack"));
					d.setOdd(rs.getInt("odd"));
					if(rs.getInt("oddpack") > 0){
						d.setTotalCase(rs.getInt("caseqty") + 1);
					}else{
						d.setTotalCase(rs.getInt("caseqty"));
					}
					d.setBundle(rs.getInt("bundle"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return list;
	}
	/**
	 * 统计需整件发货的记录总条数
	 */
	@Override
	public int countTotal(Distribution distribution) {
		String sql = "select count(*) from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) ";
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
			e.printStackTrace();
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
			throw new RuntimeException(e);
		} 
		return s;
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
	 * 添加批次号信息
	 */
	@Override
	public int addBatchno(String id, String batchno, String addwho) {
		String sql = "update JiaoCaiCompute set batchno=?,status=4,shipwho=?,shipdate=getdate() where id in (" + id + ")";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{batchno,addwho});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			return i;
		}
	}
	/**
	 * 添加运号信息
	 */
	@Override
	public int addShipno(String id, String shipno) {
		String sql = "update JiaoCaiCompute set shipno=? where id = ?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{shipno,id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 添加运号信息
	 */
	@Override
	public int addShipno(Distribution distribution, String batchno,String shipno) {
		String sql = "update JiaoCaiCompute set shipno=? where id = ";
		int i = 0;
//		try {
//			i = getJdbcTemplate().update(sql, new Object[]{shipno,distribution.getCode(),batchno});
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
		return i;
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
	 * 获取需要添加运号的id
	 */
	@Override
	public String getNeedShipnoId(String batchno) {
		String sql = "select min(id) from JiaoCaiCompute where batchno=? and shipno is null ";
		String s = null;
		try {
			if(callBatchno()){
				s = (String) getJdbcTemplate().queryForObject(sql,new Object[]{batchno}, String.class);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return s;
	}
	/**
	 * 加载已发信息
	 */
	@Override
	public List<Distribution> loadShippedData(int pageSize, int currentPage) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher,a.type,a.caseqty,a.oddpack,a.odd,a.batchno,a.shipno from JiaoCaiCompute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=4 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=4 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) order by a.issuenumber,a.subcode ) order by 2,3 ";
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
					d.setBatchno(rs.getString("batchno"));
					d.setShipno(rs.getString("shipno"));
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
	 * 统计已发信息的记录条数
	 */
	@Override
	public int countTotal_1() {
		String sql = "select count(*) from JiaoCaiCompute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=4 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) ";
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
	 * 根据期号加载需发的征订代码
	 */
	@Override
	public List<Distribution> loadWaitShipDataTotal(Distribution distribution) {
		String sql = "select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,sum(a.qtyallocated) qtyallocated,c.publisher,d.shortname shortname_publisher from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d  where a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) ";
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
		sql += " and a.id not in (select top 0 a.id from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and (caseqty > 0  or (caseqty=0 and oddpack=1) )"; 
		sql += sqladd;
		sql += " )group by a.issuenumber,a.subcode,c.barcode,c.descr,c.price,c.publisher,d.shortname";
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
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		return list;
	}
	/**
	 * 根据期号加载已发的征订代码
	 */
	@Override
	public List<Distribution> loadshippedSubcodeByIssuenumber(String issuenumber) {
		String sql = "select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,sum(a.qtyallocated) qtyallocated,c.publisher,d.shortname shortname_publisher from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=4 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) ";
		if(issuenumber != null && !"".equals(issuenumber)){
			sql += " and a.issuenumber='" + issuenumber + "'";
		}
		sql += " group by a.issuenumber,a.subcode,c.barcode,c.descr,c.price,c.publisher,d.shortname";
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
		}
		return list;
	}
	/**
	 * 根据征订代码和期号查询需发的数据
	 */
	@Override
	public List<Distribution> loadWaitShipDataBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher,a.type,a.caseqty,a.oddpack,a.odd from JiaoCaiCompute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) and a.issuenumber=? and a.subcode=? and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) and a.issuenumber=? and a.subcode=? order by a.issuenumber,a.subcode ) order by 2,3 ";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql,new Object[]{issuenumber,subcode,issuenumber,subcode}, new RowMapper() {
				
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
	 * 根据征订代码和期号查询需发的数据的记录条数
	 */
	@Override
	public int CountTotal_2(String issuenumber, String subcode) {
		String sql = "select count(*) from JiaoCaiCompute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and (caseqty > 0  or (caseqty=0 and oddpack=1) ) and a.issuenumber=? and a.subcode=? ";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,new Object[]{issuenumber,subcode},Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据期号和正定代码查询已发记录
	 */
	@Override
	public List<Distribution> loadShippedDataBySubcode(int pageSize,
			int currentPage, Distribution distribution) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.bundle,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.type,a.caseqty,a.oddpack,a.odd,a.batchno,a.shipno from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d  where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=4 ";
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
		if(distribution.getBatchno() != null && !"".equals(distribution.getBatchno())){
			sql += " and a.batchno='" + distribution.getBatchno() +"'";
			sqladd += " and a.batchno='" + distribution.getBatchno() +"'";
		}
		sql += sqladd;
		sql += "and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=4 ";
		sql += sqladd;
		sql += " order by a.issuenumber,a.subcode,a.batchno,a.shipno ) order by issuenumber,subcode,batchno,shipno ";
		
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql,new Object[]{}, new RowMapper() {
				
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
					String type = rs.getString("type");
					if("0".equals(type)){
						d.setType("预分发");
					}else if("1".equals(type)) {
						d.setType("库存分发");
					}
					d.setCaseqty(rs.getInt("caseqty"));
					d.setOddpack(rs.getInt("oddpack"));
					d.setOdd(rs.getInt("odd"));
					d.setBatchno(rs.getString("batchno"));
					d.setShipno(rs.getString("shipno"));
					if(rs.getInt("oddpack") > 0){
						d.setTotalCase(rs.getInt("caseqty") + 1);
					}else{
						d.setTotalCase(rs.getInt("caseqty"));
					}
					d.setBundle(rs.getInt("bundle"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return list;
	}
	/**
	 * 根据期号和正定代码查询已发记录的总条数
	 */
	@Override
	public int CountTotal_3( Distribution distribution) {
		String sql = "select count(*) from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d  where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=4 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() +"'";
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() +"'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and c.barcode='" + distribution.getBarcode() +"'";
		}if(distribution.getBatchno() != null && !"".equals(distribution.getBatchno())){
			sql += " and a.batchno='" + distribution.getBatchno() +"'";
		}
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,new Object[]{},Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 删除批次
	 */
	@Override
	public int removeBatchno(String id) {
		String sql = "update JiaoCaiCompute set status = 1,batchno = null,shipno=null where id in (" + id + " ) and status = 4 ";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 获得该批次号下的单据状态
	 */
	@Override
	public List<String> getStatusByBatchno(String batchno) {
		List<String> list = null;
		String sql = "select distinct status from JiaoCaiCompute where batchno = ?";
		try {
			list = getJdbcTemplate().query(sql, new Object[]{batchno}, new RowMapper() {
				
				@Override
				public String mapRow(ResultSet rs, int arg1) throws SQLException {
					String s = rs.getString("status");
					return s;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}


