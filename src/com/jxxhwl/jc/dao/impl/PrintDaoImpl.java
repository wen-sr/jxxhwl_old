package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.PrintDao;
import com.jxxhwl.jc.entity.Distribution;
@SuppressWarnings("unchecked")
@Repository
public class PrintDaoImpl extends BaseDao implements PrintDao {
	/**
	 * 加载需要打印的批次号
	 */
	@Override
	public List<Distribution> loadBatchno() {
		String sql = "select distinct batchno from JiaoCaiCompute where status  = 4  order by 1 desc ";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setBatchno(rs.getString(1));
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
	 * 加载需要打印的拣货流水号
	 */
	@Override
	public List<Distribution> loadPickno() {
		String sql = "select distinct pickno from JiaoCaiCompute where status  in (2,3,4) and caseqty=0 and oddpack=0  order by 1 desc ";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setPickno(rs.getString(1));
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
	 * 获取整件发运单头
	 */
	@Override
	public List<Distribution> getWholeCaseListHead(String batchno) {
		String sql = "select a.issuenumber,a.subcode,b.descr,b.publisher,(select shortname from jiaocaistorer c where b.publisher = c.storerkey) shortname,b.price,SUM(a.qtyallocated) qtyallocated,a.pack,SUM(a.qtyallocated)/a.pack caseqty,SUM(a.qtyallocated)%a.pack odd  from JiaoCaiCompute a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode=b.subcode and batchno=? and (oddpack>0 or caseqty>0) group by a.issuenumber,a.subcode,b.descr,b.publisher,b.price,b.bundle,a.pack";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[]{batchno}, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setDescr(rs.getString("descr"));
					d.setPublisher(rs.getString("publisher"));
					d.setShortname(rs.getString("shortname"));
					d.setPrice(rs.getDouble("price"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setPack(rs.getInt("pack"));
					d.setCaseqty(rs.getInt("caseqty"));
					d.setOdd(rs.getInt("odd"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 获取整件发运明细
	 */
	@Override
	public List<Distribution> getWholeCaseListDetail(String batchno) {
		String sql = "select code,shortname,(select phone from JiaoCaiStorer where JiaoCaiCompute.code = JiaoCaiStorer.storerkey) phone,(select contact from JiaoCaiStorer where JiaoCaiCompute.code = JiaoCaiStorer.storerkey) contact,(select address1 from JiaoCaiStorer where JiaoCaiCompute.code = JiaoCaiStorer.storerkey) address1,shipno,qtyallocated,pack,(case when oddpack=1 then caseqty+1 when oddpack=0 then caseqty end) caseqty from JiaoCaiCompute where batchno=? and (oddpack>0 or caseqty>0)";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[]{batchno}, new RowMapper<Distribution>() {
				
				@Override
				public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setCode(rs.getString("code"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setCaseqty(rs.getInt("caseqty"));
					d.setShortname(rs.getString("shortname"));
					d.setShipno(rs.getString("shipno"));
					d.setPack(rs.getInt("pack"));
					d.setPhone(rs.getString("phone")==null?"":rs.getString("phone"));
					d.setContact(rs.getString("contact")==null?"":rs.getString("contact"));
					d.setAddress(rs.getString("address1")==null?"":rs.getString("address1"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 调拨单
	 */
	@Override
	public List<Distribution> getAllocationList(String batchno) {
		String sql = "select a.issuenumber,a.subcode,a.code,a.shortname,b.descr,b.publisher,(select shortname from JiaoCaiStorer d where b.publisher=d.storerkey ) publisher_shortname,b.price,SUM(qtyallocated) qtyallocated,(case when oddpack=1 then caseqty+1 when oddpack=0 then caseqty end) caseqty,a.pack,a.oddpack,a.odd,a.shipno,b.barcode from JiaoCaiCompute a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode = b.subcode and a.batchno = ? group by a.issuenumber,a.subcode,a.code,a.shortname,b.descr,b.publisher,b.price,caseqty,oddpack,odd,b.barcode,a.pack,a.shipno";
		List<Distribution> list = null;
		list = getJdbcTemplate().query(sql, new Object[]{batchno}, new RowMapper() {
			
			@Override
			public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
				Distribution d = new Distribution();
				d.setCode(rs.getString("code"));
				d.setShortname(rs.getString("shortname"));
				d.setDescr(rs.getString("descr"));
				d.setPublisher(rs.getString("publisher_shortname"));
				d.setStorerkey(rs.getString("publisher"));
				d.setPrice(rs.getDouble("price"));
				d.setQtyallocated(rs.getInt("qtyallocated"));
				d.setCaseqty(rs.getInt("caseqty"));
				d.setPack(rs.getInt("pack"));
				d.setOddpack(rs.getInt("oddpack"));
				d.setOdd(rs.getInt("odd"));
				d.setShipno(rs.getString("shipno"));
				d.setBarcode(rs.getString("barcode"));
				d.setSubcode(rs.getString("subcode"));
				d.setIssuenumber(rs.getString("issuenumber"));
				return d;
			}
		});
		return list;
	}
	/**
	 * 票签
	 */
	@Override
	public List<Distribution> getS_ticketList(String batchno) {
		List<Distribution> list = null;
		String sql = "select a.issuenumber,a.batchno,a.subcode,b.descr,a.shortname,(case when oddpack=1 then caseqty+1 when oddpack=0 then caseqty end) caseqty,shipno from JiaoCaiCompute a,JiaoCaiSku b where a.issuenumber = b.issuenumber and a.subcode = b.subcode and (a.caseqty > 0 or a.oddpack > 0) and a.batchno = ?";
		list = getJdbcTemplate().query(sql, new Object[]{batchno}, new RowMapper() {
			
			@Override
			public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
				Distribution d = new Distribution();
				d.setSubcode(rs.getString("subcode"));
				d.setDescr(rs.getString("descr"));
				d.setShortname(rs.getString("shortname"));
				d.setCaseqty(rs.getInt("caseqty"));
				d.setShipno(rs.getString("shipno"));
				d.setBatchno(rs.getString("batchno"));
				d.setIssuenumber(rs.getString("issuenumber"));
				return d;
			}
		});
		return list;
	}
	
	/**
	 * 零件清单明细
	 */
	@Override
	public List<Distribution> getOddCaseList(String batchno) {
		String sql = "select a.issuenumber,a.subcode,b.descr,b.publisher,(select shortname from jiaocaistorer c where b.publisher = c.storerkey) shortname,b.price,SUM(a.qtyallocated) qtyallocated,max(a.pack) pack from JiaoCaiCompute a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode=b.subcode and oddpack=0 and caseqty=0 and batchno=? group by a.issuenumber,a.subcode,b.descr,b.publisher,b.price";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[]{batchno}, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setDescr(rs.getString("descr"));
					d.setPublisher(rs.getString("publisher"));
					d.setShortname(rs.getString("shortname"));
					d.setPrice(rs.getDouble("price"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setPack(rs.getInt("pack"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 零件清单单头
	 */
	@Override
	public List<Distribution> getOddCaseListHead(String batchno) {
		String sql = "select issuenumber,code,shortname,batchno,shipno,(select phone from JiaoCaiStorer where JiaoCaiCompute.code = JiaoCaiStorer.storerkey) phone,(select contact from JiaoCaiStorer where JiaoCaiCompute.code = JiaoCaiStorer.storerkey) contact,(select address1 from JiaoCaiStorer where JiaoCaiCompute.code = JiaoCaiStorer.storerkey) address1,SUM(qtyallocated) qtyallocated,pack from JiaoCaiCompute where caseqty = 0 and oddpack = 0 and batchno = ? group by issuenumber,code,shortname,batchno,shipno,pack";
		List<Distribution> list = null;
		list = getJdbcTemplate().query(sql, new Object[]{batchno}, new RowMapper<Distribution>() {
			
			@Override
			public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
				Distribution d = new Distribution();
				d.setCode(rs.getString("code"));
				d.setShortname(rs.getString("shortname"));
				d.setBatchno(rs.getString("batchno"));
				d.setShipno(rs.getString("shipno"));
				d.setQtyallocated(rs.getInt("qtyallocated"));
				d.setPack(rs.getInt("pack"));
				d.setIssuenumber(rs.getString("issuenumber"));
				d.setPhone(rs.getString("phone")==null?"":rs.getString("phone"));
				d.setContact(rs.getString("contact")==null?"":rs.getString("contact"));
				d.setAddress(rs.getString("address1")==null?"":rs.getString("address1"));
				return d;
			}
		});
		return list;
	}
	/**
	 * 手拣单单头
	 */
	@Override
	public List<Distribution> getPickListHead(String pickno) {
		String sql = "select distinct a.issuenumber,a.subcode,b.barcode,b.publisher,(select shortname from jiaocaistorer c where b.publisher = c.storerkey) shortname,b.price,b.descr,a.pickno from JiaoCaiCompute a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.pickno=? ";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[]{pickno}, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setIssuenumber(rs.getString("issuenumber"));
					d.setSubcode(rs.getString("subcode"));
					d.setBarcode(rs.getString("barcode"));
					d.setDescr(rs.getString("descr"));
					d.setPublisher(rs.getString("publisher"));
					d.setShortname(rs.getString("shortname"));
					d.setPrice(rs.getDouble("price"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 手拣单明细
	 */
	@Override
	public List<Distribution> getPickListDetail(String pickno) {
		String sql = "select a.code,a.shortname,a.qtyallocated,a.pack,a.pickno from JiaoCaiCompute a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.pickno=? ";
		List<Distribution> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[]{pickno}, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Distribution d = new Distribution();
					d.setCode(rs.getString("code"));
					d.setShortname(rs.getString("shortname"));
					d.setQtyallocated(rs.getInt("qtyallocated"));
					d.setPack(rs.getInt("pack"));
					d.setPickno(rs.getString("pickno"));
					return d;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 获得退货票签
	 */
	@Override
	public Distribution getReturnTicket(String subcode) {
		String sql = "select * from JiaoCaiReturnSku where subcode=?";
		List<Distribution> list = getJdbcTemplate().query(sql,new Object[]{ subcode}, new RowMapper<Distribution>() {

			@Override
			public Distribution mapRow(ResultSet rs, int arg1) throws SQLException {
				Distribution d = new Distribution();
				d.setSubcode(rs.getString("subcode"));
				d.setDescr(rs.getString("descr"));
				d.setPrice(rs.getDouble("price"));
				d.setShortname(rs.getString("shortname"));
				d.setPack(rs.getInt("pack"));
				return d;
			}
		});
		if(list != null && list.size() > 0) {
			return list.get(0);
		}else {
			return null;
		}
	}
}
