package com.jxxhwl.jc.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.DistributionDao;
import com.jxxhwl.jc.entity.Distribution;
import com.mysql.jdbc.Statement;

@Repository("distributionDao")
public class DistributionDaoImpl extends BaseDao implements DistributionDao {

	private class DistributionRowMapper implements RowMapper {

		@Override
		public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
			Distribution d = new Distribution();
			d.setAdddate(rs.getString("adddate"));
			d.setAddwho(rs.getString("addwho"));
			d.setBarcode(rs.getString("barcode"));
			d.setBatchno(rs.getString("batchno"));
			d.setCaseqty(rs.getInt("caseqty"));
			d.setCode(rs.getString("code"));
			
			return null;
		}
		
	}
	/**
	 * 保存分发信息
	 */
	@Override
	public Distribution save(final Distribution distribution) {
		try{
		final String sql = "insert into JiaoCaiDistribute(code,shortname,qtyallocated,addwho,adddate,pack,oldpack,subcode,issuenumber,type,editwho,editdate) values(?,(select shortname from jiaocaistorer where storerkey=? ),?,?,getdate(),?,?,?,?,?,?,getdate())";
		KeyHolder keyHolder=new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,distribution.getCode());
                ps.setString(2, distribution.getCode());
                ps.setInt(3, distribution.getQtyallocated());
                ps.setString(4, distribution.getAddwho());
                ps.setInt(5, distribution.getPack());
                ps.setInt(6, distribution.getPack());
                ps.setString(7,distribution.getSubcode());
                ps.setString(8, distribution.getIssuenumber());
                ps.setString(9, distribution.getType());
                ps.setString(10, distribution.getAddwho());
                return ps;
            }
        },keyHolder);
		//获取id
		distribution.setId(keyHolder.getKey().toString());
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return distribution;
	}
	/**
	 * 获取分发总记录条数
	 */
	@Override
	public int getDistributionTotalRows(Distribution distribution) {
		String sql = "select count(*) from jiaocaidistribute b join JiaoCaiSku c on c.subcode=b.subcode and b.issuenumber=c.issuenumber join JiaoCaiStorer d on c.publisher=d.storerkey where b.status=0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and b.issuenumber='" + distribution.getIssuenumber() + "'"; 
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and b.subcode='" + distribution.getSubcode() + "'";
		}
		sql += "and b.type= "  + distribution.getType();
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (DataAccessException e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 分页获取分发数据
	 */
	@Override
	public List<Distribution> getDistributedData(int pageSize,
			int currentPage, Distribution distribution) {
		String sql = "select top "+ pageSize +" b.id,b.issuenumber,b.subcode,b.code,b.shortname shortname_customer,c.barcode,c.publisher,d.shortname shortname_publisher,c.descr,c.price,b.pack,b.qtyallocated,CONVERT(varchar(12) , b.adddate, 112 ) adddate,b.addwho from jiaocaidistribute b join JiaoCaiSku c on c.subcode=b.subcode and b.issuenumber=c.issuenumber join JiaoCaiStorer d on c.publisher=d.storerkey where b.status in (0,1) ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and b.issuenumber='" + distribution.getIssuenumber() + "'"; 
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and b.subcode='" + distribution.getSubcode() + "'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and c.barcode='" + distribution.getBarcode() + "'";
		}
		sql += " and b.type= "  + distribution.getType() + " and b.id not in (select top "+ (currentPage-1)*pageSize +" id from  jiaocaidistribute e where status in (0,1)";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and e.issuenumber='" + distribution.getIssuenumber() + "'"; 
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and e.subcode='" + distribution.getSubcode() + "'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and c.barcode='" + distribution.getBarcode() + "'";
		}
		sql += "and TYPE= " + distribution.getType() + " ORDER BY adddate DESC, ID DESC) order by adddate desc, ID DESC ";
		
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
					d.setAdddate(rs.getString("adddate"));
					d.setAddwho(rs.getString("addwho"));
					d.setBarcode(rs.getString("barcode"));
					d.setShortname(rs.getString("shortname_customer"));
					d.setCode(rs.getString("code"));
					d.setStorerkey(rs.getString("publisher"));
					double amt = rs.getInt("qtyallocated") * rs.getDouble("price");
					BigDecimal b = new BigDecimal(amt);  
					double dou = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					d.setAmt(dou);
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return list;
	}
	/**
	 * 获取分发记录的状态
	 */
	@Override
	public String getDistributionStatusById(String id) {
		String sql = "select status from JiaoCaiDistribute where id = ?";
		String s = null;
		try {
			s = (String) getJdbcTemplate().queryForObject(sql,new Object[]{id}, new RowMapper() {
				
				@Override
				public String mapRow(ResultSet rs, int arg1) throws SQLException {
					return rs.getString("status");
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		} 
		return s;
	}
	/**
	 * 删除
	 */
	@Override
	public int delete(String id) {
		int i = 0;
		String sql = "delete from JiaoCaiDistribute where id = ? and status = 0";
		try {
			i = getJdbcTemplate().update(sql,new Object[]{id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 修改
	 */
	@Override
	public int update(Distribution d) {
		int i = 0;
		String sql = "update JiaoCaiDistribute set code=?,shortname=(select shortname from JiaoCaiStorer js where storerkey=?),qtyallocated=?,subcode=?,issuenumber=?,editwho=?,editdate=getdate() where id = ? and status = 0";
		try {
			i = getJdbcTemplate().update(sql, new Object[]{d.getCode(),d.getCode(),d.getQtyallocated(),d.getSubcode(),d.getIssuenumber(),d.getAddwho(),d.getId()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据id查询
	 */
	@Override
	public Distribution findById(String id) {
		String sql = "select * from JiaoCaiDistribute where id = ?";
		Distribution d = null;
		try {
			d = (Distribution) getJdbcTemplate().queryForObject(sql,new Object[]{id}, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setId(rs.getString("id"));
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setPack(rs.getInt("pack"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setAdddate(rs.getString("adddate"));
					d.setAddwho(rs.getString("addwho"));
					d.setCode(rs.getString("code"));
					d.setShortname(rs.getString("shortname"));
					d.setStatus(rs.getString("status"));
					d.setType(rs.getString("type"));
					return d;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return d;
	}
	/**
	 * 根据期号查询已分发的征订代码
	 */
	@Override
	public List<Distribution> loadDistributedSubcodeByIssuenumber(
			String issuenumber) {
		String sql = "select b.issuenumber,b.subcode,c.barcode,c.publisher,d.shortname shortname_publisher,c.descr,c.price,sum(b.qtyallocated) qtyallocated from jiaocaidistribute b join JiaoCaiSku c on c.subcode=b.subcode and b.issuenumber=c.issuenumber join JiaoCaiStorer d on c.publisher=d.storerkey where b.status=0 and b.type= 1 and b.issuenumber = ? group by b.issuenumber,b.subcode,c.barcode,c.publisher,d.shortname,c.descr,c.price";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[]{issuenumber}, new RowMapper() {
				
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
		} finally{
			return list;
		}
	}
	/**
	 * 根据期号和征订代码查询库存已分发的信息
	 */
	@Override
	public List<Distribution> getDistributionLoadData(int pageSize,
			int currentPage, String issuenumber, String subcode) {
		String type = "1";
		String sql = "select top "+ pageSize +" b.id,b.issuenumber,b.subcode,b.code,b.shortname shortname_customer,c.barcode,c.publisher,d.shortname shortname_publisher,c.descr,c.price,b.pack,b.qtyallocated,CONVERT(varchar(12) , b.adddate, 112 ) adddate,b.addwho from jiaocaidistribute b join JiaoCaiSku c on c.subcode=b.subcode and b.issuenumber=c.issuenumber join JiaoCaiStorer d on c.publisher=d.storerkey where b.status=0 and b.type= "  + type + " and b.issuenumber=? and b.subcode=? and b.id not in (select top "+ (currentPage-1)*pageSize +" id from  jiaocaidistribute where status=0 and b.issuenumber=? and b.subcode=? and TYPE= " + type + " ORDER BY adddate DESC, ID DESC) order by adddate desc, ID DESC ";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[]{issuenumber,subcode,issuenumber,subcode}, new RowMapper() {
				
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
					d.setAdddate(rs.getString("adddate"));
					d.setAddwho(rs.getString("addwho"));
					d.setBarcode(rs.getString("barcode"));
					d.setShortname(rs.getString("shortname_customer"));
					d.setCode(rs.getString("code"));
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
		} finally{
			return list;
		}
	}
	/**
	 * 根据期号和征订代码查询总条数
	 */
	@Override
	public int countTotal(String issuenumber, String subcode) {
		String type = "1";
		String sql = "select count(*) from jiaocaidistribute b join JiaoCaiSku c on c.subcode=b.subcode and b.issuenumber=c.issuenumber join JiaoCaiStorer d on c.publisher=d.storerkey where b.status=0 and b.type= "  + type + " and b.issuenumber=? and b.subcode=?";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,new Object[]{issuenumber,subcode},Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally{
			return i;
		}
	}
	/**
	 * 更新业务预分发记录的包册数
	 */
	@Override
	public int updatePredistributionPack(String issuenumber, String subcode,
			int pack) {
		String sql = "update JiaoCaiDistribute set pack=? where issuenumber = ? and subcode = ? and type = 0 and status = 0";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{pack,issuenumber,subcode});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 查询可预分发的数据
	 */
	@Override
	public List<Distribution> getWaitPreDistributionLoadData(
			Distribution distribution, int pageSize, int currentPage) {
		String sql = "select aa.* from (select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,(orderqty-ISNULL(b.qtyallocated,0)) qtyfree,c.publisher,(select shortname from JiaoCaiStorer where c.publisher=JiaoCaiStorer.storerkey ) shortname from (select issuenumber,subcode,SUM(orderqty) orderqty from JiaoCaiOrders group by issuenumber,subcode) a left join (select issuenumber,subcode,SUM(qtyallocated) qtyallocated from JiaoCaiDistribute where type=0  group by issuenumber,subcode ) b on a.issuenumber=b.issuenumber and a.subcode = b.subcode  left join JiaoCaiSku c on a.issuenumber=c.issuenumber and a.subcode = c.subcode where a.orderqty - ISNULL(b.qtyallocated,0) >0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() + "'"; 
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() + "'";
		}
		sql += " ) aa,(select top "+ (currentPage)*pageSize +" row_number() over(order by a.issuenumber,a.subcode) id, a.issuenumber,a.subcode,c.barcode,c.descr,c.price,(orderqty-ISNULL(b.qtyallocated,0)) qtyfree,c.publisher,(select shortname from JiaoCaiStorer where c.publisher=JiaoCaiStorer.storerkey ) shortname from (select issuenumber,subcode,SUM(orderqty) orderqty from JiaoCaiOrders group by issuenumber,subcode) a left join (select issuenumber,subcode,SUM(qtyallocated) qtyallocated from JiaoCaiDistribute where type=0  group by issuenumber,subcode ) b on a.issuenumber=b.issuenumber and a.subcode = b.subcode  left join JiaoCaiSku c on a.issuenumber=c.issuenumber and a.subcode = c.subcode where a.orderqty - ISNULL(b.qtyallocated,0) >0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() + "'"; 
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() + "'";
		}
		sql += ")bb where aa.issuenumber = bb.issuenumber and aa.subcode = bb.subcode and bb.id > "+ (currentPage-1)*pageSize +" ";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setQtyfree(rs.getInt("qtyfree"));
					d.setPublisher(rs.getString("publisher"));
					d.setShortname(rs.getString("shortname"));
					d.setBarcode(rs.getString("barcode"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return list;
	}
	/**
	 * 统计可预分发的数据
	 */
	@Override
	public int countTotalWaitDistributionData(Distribution distribution) {
		String sql = "select COUNT(*) from (select issuenumber,subcode,SUM(orderqty) orderqty from JiaoCaiOrders group by issuenumber,subcode) a left join (select issuenumber,subcode,SUM(qtyallocated) qtyallocated from JiaoCaiDistribute where type=0  group by issuenumber,subcode ) b on a.issuenumber=b.issuenumber and a.subcode = b.subcode  left join JiaoCaiSku c on a.issuenumber=c.issuenumber and a.subcode = c.subcode where a.orderqty - ISNULL(b.qtyallocated,0) >0 ";
		if(distribution.getIssuenumber() != null && !"".equals(distribution.getIssuenumber())){
			sql += " and a.issuenumber='" + distribution.getIssuenumber() + "'"; 
		}
		if(distribution.getSubcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and a.subcode='" + distribution.getSubcode() + "'";
		}
		if(distribution.getBarcode() != null && !"".equals(distribution.getSubcode())){
			sql += " and c.barcode='" + distribution.getBarcode() + "'";
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
	 * 查询已分发的征订代码
	 */
	@Override
	public List<Distribution> loadDistributedSubcodeByIssuenumber(
			Distribution distribution) {
		String sql = "select distinct a.issuenumber,a.subcode,b.barcode,b.descr,b.price,b.publisher,(select shortname from JiaoCaiStorer c where b.publisher=c.storerkey ) shortname from JiaoCaiDistribute a,JiaoCaiSku b where a.issuenumber = b.issuenumber and a.subcode = b.subcode and type = "+ distribution.getType() +" and status = 0 ";
		if(!"".equals(distribution.getIssuenumber()) && distribution.getIssuenumber() != null){
			sql += " and a.issuenumber = '" + distribution.getIssuenumber() + "'";
		}
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setBarcode(rs.getString("barcode"));
					d.setDescr(rs.getString("descr"));
					d.setPrice(rs.getDouble("price"));
					d.setPublisher(rs.getString("publisher"));
					d.setShortname(rs.getString("shortname"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}
