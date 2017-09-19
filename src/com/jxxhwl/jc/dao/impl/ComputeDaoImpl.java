package com.jxxhwl.jc.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.ComputeDao;
import com.jxxhwl.jc.entity.Distribution;

@Repository("computeDao")
public class ComputeDaoImpl extends BaseDao implements ComputeDao{
	
	/**
	 * 分页查询需要配发计算的数据
	 */
	@Override
	public List<Distribution> findNeedCompute(int pageSize, int currentPage, Distribution distribution) {
//		String sql = "select top "+ pageSize +" * from ( select a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher,a.type from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.type=0  and a.qtyallocated<b.qtyfree union select a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher,a.type from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.type=1 ) aa where aa.id not in (select top "+ (currentPage-1)*pageSize +" id from (select a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher,a.type from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.type=0  and a.qtyallocated<b.qtyfree union select a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher,a.type from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.type=1 ) bb order by issuenumber,subcode)order by 2,3";
		String sql = "select a.issuenumber,a.subcode,b.barcode,b.descr,b.price,SUM(a.qtyallocated) qtyallocated,(select SUM(qtyfree) from JiaoCaiInventory_detail c where a.issuenumber=c.issuenumber and a.subcode=c.subcode) qtyfree  from JiaoCaiDistribute a,JiaoCaiSku b where a.issuenumber = b.issuenumber and a.subcode = b.subcode and a.status = 0";
		String sqladd = "";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() + "'"; 
			sqladd += " and a.issuenumber='" + distribution.getIssuenumber() + "'"; 
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() + "'";
			sqladd += " and a.subcode='" + distribution.getSubcode() + "'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and b.barcode='" + distribution.getBarcode() + "'";
			sqladd += " and b.barcode='" + distribution.getBarcode() + "'";
		}
		sql += " group by a.issuenumber,a.subcode,b.barcode,b.descr,b.price";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					if(rs.getInt("qtyfree")  == 0) {
						rs.next();
					}
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setBarcode(rs.getString("barcode"));
					double amt = rs.getInt("qtyallocated") * rs.getDouble("price");
					BigDecimal b = new BigDecimal(amt);  
					double dou = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					d.setAmt(dou);
					d.setQtyfree(rs.getInt("qtyfree"));
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
	 * 统计未配发计算记录总条数
	 */
	@Override
	public int countTotal(Distribution distribution) {
		String sql = "select COUNT(*) from (select distinct a.issuenumber,a.subcode,b.barcode,b.descr,b.price from JiaoCaiDistribute a,JiaoCaiSku b where a.issuenumber = b.issuenumber and a.subcode = b.subcode and a.status = 0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() + "'"; 
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() + "'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getBarcode())){
			sql += " and b.barcode='" + distribution.getBarcode() + "'";
		}
		sql += ") aa";
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
	 * 修改包册数
	 */
	@Override
	public void editPack(String id, int pack) {
		String sql = "update JiaoCaiDistribute set pack=? where id = ? ";
		try {
			getJdbcTemplate().update(sql,new Object[]{pack,id});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据id配发计算
	 */
	@Override
	public int compute(String id) {
		String sql = "update JiaoCaiCompute set caseqty=qtyallocated/(b.bundle*a.pack),odd=((qtyallocated%(b.bundle*a.pack))%a.pack),oddpack=(qtyallocated%(b.bundle*a.pack))/a.pack,status=1 from JiaoCaiCompute a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode = b.subcode and a.id=? and a.status=0 ";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 分页查询已配发的数据
	 */
	@Override
	public List<Distribution> findComputedData(int pageSize, int currentPage, Distribution distribution) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.type,a.caseqty,a.oddpack,a.odd from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status in (1,2,3,4) ";
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
		sql += " and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiCompute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status in (1,2,3,4) ";
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
	 * 统计已配发的记录总条数
	 */
	@Override
	public int countTotal1() {
		String sql = "select COUNT(*) from JiaoCaiCompute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 ";
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
	 * 取消配发计算
	 */
	@Override
	public int cancelCompute(String id) {
		String sql = "delete from JiaoCaiCompute where id = ? and status = 1";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 取消配发单号
	 * @return
	 */
	@Override
	public int updateDistribution (String computeno, String code){
		String sql = "update JiaoCaiDistribute set status = 0,pack=0,computeno=null where computeno = ? and code = ? and status = 1";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{computeno,code});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	
	/**
	 * 根据期号加载需计算的征订代码
	 */
	@Override
	public List<Distribution> loadComputeSubcodeByIssuenumber(String issuenumber) {
		String sql = "select issuenumber,subcode,barcode,descr,price,sum(qtyallocated) qtyallocated,qtyfree,publisher,shortname_publisher from ( select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,sum(a.qtyallocated) qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.type=0  and a.qtyallocated<b.qtyfree group by a.issuenumber,a.subcode,c.barcode,c.descr,c.price,b.qtyfree,c.publisher,d.shortname union select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,sum(a.qtyallocated) qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.type=1 group by a.issuenumber,a.subcode,c.barcode,c.descr,c.price,b.qtyfree,c.publisher,d.shortname ) aa where issuenumber=? group by issuenumber,subcode,barcode,descr,price,qtyfree,publisher,shortname_publisher";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql,new Object[]{issuenumber} ,new RowMapper() {
				
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
					d.setQtyfree(rs.getInt("qtyfree"));
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
	 * 根据征订代码和期号查询需配发的数据
	 */
	@Override
	public List<Distribution> findNeedComputeBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,c.publisher,d.shortname shortname_publisher,a.type from JiaoCaiDistribute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.issuenumber=? and a.subcode=? and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiDistribute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.issuenumber=? and a.subcode=?)";
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
					String type = rs.getString("type");
					if("0".equals(type)){
						d.setType("预分发");
					}else if("1".equals(type)) {
						d.setType("库存分发");
					}
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
	 * 根据期号和征订代码统计总需配发总条数
	 */
	@Override
	public int CountTotal_1(String issuenumber, String subcode) {
		String sql = "select COUNT(*) from JiaoCaiDistribute a,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=0 and a.issuenumber=? and a.subcode=?";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql, new Object[]{issuenumber,subcode},Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return i;
	}
	/**
	 * 根据期号加载已计算的征订代码
	 */
	@Override
	public List<Distribution> loadComputedSubcodeByIssuenumber(
			Distribution distribution) {
		String sql = "select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,sum(a.qtyallocated) qtyallocated,c.publisher,d.shortname shortname_publisher from JiaoCaiDistribute a,JiaoCaiSku c, JiaoCaiStorer d  where a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() + "'";
		}
		sql += "group by a.issuenumber,a.subcode,c.barcode,c.descr,c.price,c.publisher,d.shortname";
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
	 * 根据征订代码和期号查询已配发的数据
	 */
	@Override
	public List<Distribution> findComputedBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode) {
		String sql = "select top "+ pageSize +" a.id,a.issuenumber,a.subcode,c.barcode,c.descr,c.price,a.code,a.shortname shortname_customer,a.pack,a.qtyallocated,b.qtyfree,c.publisher,d.shortname shortname_publisher,a.type,a.caseqty,a.oddpack,a.odd from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and a.issuenumber=? and a.subcode=? and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and a.issuenumber=? and a.subcode=? order by a.issuenumber,a.subcode ) order by 2,3 ";
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
	 * 根据期号和征订代码统计总已配发总条数
	 */
	@Override
	public int CountTotal_2(String issuenumber, String subcode) {
		String sql = "select count(*) from JiaoCaiDistribute a,JiaoCaiInventory b,JiaoCaiSku c, JiaoCaiStorer d where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber=c.issuenumber and a.subcode=c.subcode and c.publisher=d.storerkey and status=1 and a.issuenumber=? and a.subcode=? ";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql, new Object[]{issuenumber,subcode},Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			return i;
		}
	}
	/**
	 * 更新捆扎和配发单号
	 */
	@Override
	public int updatePack(String[] ids, int pack, String addwho, String computeno) {
		StringBuffer zb = new StringBuffer();
		for(String s: ids){
			zb.append("'").append(s).append("',");
		}
		String id = zb.toString();
		id = zb.substring(0, zb.length()-1);
		int i = 0;
		String sql = "update JiaoCaiDistribute set pack=?,status=1,computeno=?,editwho=?,editdate=getdate() where status = 0 and id in (" + id + ")";
		try {
			i = getJdbcTemplate().update(sql, new Object[]{pack,computeno,addwho});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	
	/**
	 * 获取新配发号
	 */
	@Override
	public String getComputeno() {
		String sql = "select computeno from JiaoCaiNumber";
		String s = null;
		try {
			if(callComputeno()){
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
	private boolean callComputeno () {
		boolean s = false;
		try {
			getJdbcTemplate().execute("{call JiaoCaiGetComputeno()}");
			s = true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return s;
	}
	/**
	 * 保存配发信息
	 */
	@Override
	public int save(String[] ids) {
		int i = 0;
		StringBuffer zb = new StringBuffer();
		for(String s: ids){
			zb.append("'").append(s).append("',");
		}
		String id = zb.toString();
		id = zb.substring(0, zb.length()-1);
		String sql = "insert into JiaoCaiCompute (issuenumber,subcode,code,shortname,pack,qtyallocated,status,computedate,computewho,computeno) select issuenumber,subcode,code,shortname,pack,SUM(qtyallocated),'0',GETDATE(),editwho,computeno from  JiaoCaiDistribute where status = 1 and id in ("+ id +") group by issuenumber,subcode,code,shortname,pack,editwho,computeno";
		try {
			i = getJdbcTemplate().update(sql);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 按照配发号查询
	 */
	@Override
	public List<Distribution> getByComputeno(String computeno) {
		List<Distribution> list = null;
		String sql = "select * from JiaoCaiCompute where computeno = ?";
		try {
			list = getJdbcTemplate().query(sql, new Object[]{computeno}, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setId(rs.getString("id"));
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setCode(rs.getString("code"));
					d.setShortname(rs.getString("shortname"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 按照id查
	 */
	@Override
	public Distribution findById(String s) {
		List<Distribution> list = null;
		Distribution dis = null;
		String sql = "select * from JiaoCaiCompute where id = ?";
		try {
			list = getJdbcTemplate().query(sql, new Object[]{s}, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setId(rs.getString("id"));
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setCode(rs.getString("code"));
					d.setShortname(rs.getString("shortname"));
					d.setStatus(rs.getString("status"));
					d.setPack(rs.getInt("pack"));
					d.setComputeno(rs.getString("computeno"));
					return d;
				}
			});
			if(list != null && list.size() > 0){
				dis = list.get(0); 
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dis;
	}

	@Override
	public List<Distribution> findAll(Distribution distribution) {
		String sql = "select a.issuenumber,a.subcode,a.code,b.shortname,a.pack,a.qtyallocated qty1,b.qtyallocated qty2,b.status,c.descr,c.barcode,c.price,c.publisher,(select d.shortname from JiaoCaiStorer d where c.publisher = d.storerkey) publishername from JiaoCaiDistribute a left join JiaoCaiCompute b on a.computeno = b.computeno and a.issuenumber = b.issuenumber and a.subcode = b.subcode and a.code = b.code left join jiaocaisku c on a.issuenumber = c.issuenumber and a.subcode = c.subcode";
		List<Distribution> list = null;
		list = getJdbcTemplate().query(sql, new RowMapper<Distribution>() {
			@Override
			public Distribution mapRow(ResultSet rs, int i) throws SQLException {
				Distribution d = new Distribution();
				d.setIssuenumber(rs.getString("issuenumber"));
				d.setSubcode(rs.getString("subcode"));
				d.setQtyallocated(rs.getInt("qty1"));
				d.setQtyshipped(rs.getInt("qty2"));
				d.setCode(rs.getString("code"));
				d.setShortname(rs.getString("shortname"));
				d.setStatus(rs.getString("status"));
				d.setStorerkey(rs.getString("storerkey"));
				d.setPublisher(rs.getString("shortname_publisher"));
				d.setDescr(rs.getString("descr"));
				d.setPrice(rs.getDouble("price"));
				return d;
			}
		});
		return list;
	}
}
