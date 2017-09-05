package com.jxxhwl.jc.dao.impl;

import java.math.BigDecimal;
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
import com.jxxhwl.jc.dao.ReceiptDao;
import com.jxxhwl.jc.entity.Receipt;
import com.mysql.jdbc.Statement;
@Repository
public class ReceiptDaoImpl extends BaseDao implements ReceiptDao {
	/**
	 * 映射类
	 * @author Administrator
	 *
	 */
	public class ReceiptRowMapper implements RowMapper{

		@Override
		public Receipt mapRow(ResultSet rs, int arg1) throws SQLException {
			Receipt r = new Receipt();
			r.setAdddate(rs.getString("adddate"));
			r.setAddwho(rs.getString("addwho"));
			r.setBarcode(rs.getString("barcode"));
			r.setDescr(rs.getString("descr"));
			r.setEditdate(rs.getString("editdate"));
			r.setEditwho(rs.getString("editwho"));
			r.setId(rs.getString("id"));
			r.setIssuenumber(rs.getString("issuenumber"));
			r.setPack(rs.getInt("pack"));
			r.setPrice(rs.getDouble("price"));
			r.setPublisher(rs.getString("publisher"));
			r.setQtyreceipt(rs.getInt("qtyreceipt"));
			r.setStatus(rs.getString("status"));
			r.setStorerkey(rs.getString("storerkey"));
			r.setSubcode(rs.getString("subcode"));
			r.setReceiptno(rs.getString("receiptno"));
			return r;
		}
		
	}
	/**
	 * 保存
	 */
	@Override
	public Receipt save(final Receipt receipt) {
		int i = 0;
		final String sql = "insert into jiaocaireceipt(adddate,addwho,editdate,editwho,issuenumber,pack,qtyreceipt,status,subcode,receiptno,printPlant) values(getdate(),?,getdate(),?,?,?,?,0,?,?,?)";
		try {
			KeyHolder keyHolder=new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	                ps.setString(1,receipt.getAddwho());
	                ps.setString(2,receipt.getAddwho());
	                ps.setString(3,receipt.getIssuenumber());
	                ps.setInt(4,receipt.getPack());
	                ps.setInt(5,receipt.getQtyreceipt());
	                ps.setString(6,receipt.getSubcode());
	                ps.setString(7,receipt.getReceiptno());
	                ps.setString(8,receipt.getPrintPlant());
	                return ps;
	            }
	        },keyHolder);
			//获取id
			receipt.setId(keyHolder.getKey().toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return receipt;
	}
	/**
	 * 分页查询
	 */
	@Override
	public List<Receipt> findByPage(int pageSize, int currentPage, Receipt receipt) {
		String sql = "select top "+ pageSize +" jr.id,jr.issuenumber,jr.subcode,jcs.barcode,jcs.descr,jcs.price,jr.pack,jr.qtyreceipt,(jr.qtyreceipt*jcs.price) amt,jcs.publisher,jcss.shortname,CONVERT(varchar(12) , jr.adddate, 112 ) adddate,jr.addwho,CONVERT(varchar(12) , jr.adddate, 112 ) editdate,jr.editwho,jr.status,jr.receiptno,jr.printPlant,(select shortname from JiaoCaiStorer where storerkey=jr.printPlant) printPlantName from JiaoCaiReceipt jr,JiaoCaiSku jcs,JiaoCaiStorer jcss where jr.issuenumber=jcs.issuenumber and jr.subcode =  jcs.subcode and jcs.publisher=jcss.storerkey ";
		String sqladd = "";
		if(receipt.getIssuenumber() != null && !"".equals(receipt.getIssuenumber())){
			sql += " and jr.issuenumber = '" + receipt.getIssuenumber() + "'";
			sqladd += " and jr.issuenumber = '" + receipt.getIssuenumber() + "'";
		}
		if(receipt.getSubcode() != null && !"".equals(receipt.getSubcode())){
			sql += " and jr.subcode = '" + receipt.getSubcode() + "'";
			sqladd += " and jr.subcode = '" + receipt.getSubcode() + "'";
		}
		if(receipt.getReceiptno() != null && !"".equals(receipt.getReceiptno())){
			sql += " and jr.receiptno = '" + receipt.getReceiptno() + "'";
			sqladd += " and jr.receiptno = '" + receipt.getReceiptno() + "'";
		}
		if(receipt.getBarcode() != null && !"".equals(receipt.getBarcode())){
			sql += " and jcs.barcode = '" + receipt.getBarcode() + "'";
			sqladd += " and jcs.barcode = '" + receipt.getBarcode() + "'";
		}
		
		sql += "and jr.ID not in (select top "+ (currentPage-1)*pageSize +" jr.id from jiaocaireceipt jr,JiaoCaiSku jcs,JiaoCaiStorer jcss where jr.issuenumber=jcs.issuenumber and jr.subcode =  jcs.subcode and jcs.publisher=jcss.storerkey " + sqladd;
		sql += " order by jr.Id desc ) order by jr.Id desc ";
		List<Receipt> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Receipt mapRow(ResultSet rs, int arg1) throws SQLException {
					Receipt r = new Receipt();
					r.setAdddate(rs.getString("adddate"));
					r.setAddwho(rs.getString("addwho"));
					r.setBarcode(rs.getString("barcode"));
					r.setDescr(rs.getString("descr"));
					r.setEditdate(rs.getString("editdate"));
					r.setEditwho(rs.getString("editwho"));
					r.setId(rs.getString("id"));
					r.setIssuenumber(rs.getString("issuenumber"));
					r.setPack(rs.getInt("pack"));
					r.setPrice(rs.getDouble("price"));
					r.setPublisher(rs.getString("shortname"));
					r.setQtyreceipt(rs.getInt("qtyreceipt"));
					r.setStatus(rs.getString("status"));
					r.setStorerkey(rs.getString("publisher"));
					r.setSubcode(rs.getString("subcode"));
					double amt = rs.getDouble("amt");
					BigDecimal b = new BigDecimal(amt);  
					double d = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					r.setAmt(d);
					r.setReceiptno(rs.getString("receiptno"));
					r.setPrintPlant(rs.getString("printPlant"));
					r.setPrintPlantName(rs.getString("printPlantName"));
					return r;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			return list;
		}
	}
	/**
	 * 获取总记录条数
	 */
	@Override
	public int getTotalCount() {
		String sql = "select count(*) from JiaoCaiReceipt where status=0";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 修改
	 */
	@Override
	public int update(Receipt receipt) {
		String sql = "update jiaocaireceipt set issuenumber=?,subcode=?,pack=?,qtyreceipt=?,editwho=?,editdate=getdate(),receiptno=?,printPlant=? where id=?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{receipt.getIssuenumber(),receipt.getSubcode(),receipt.getPack(),receipt.getQtyreceipt(),receipt.getAddwho(),receipt.getReceiptno(),receipt.getPrintPlant(),receipt.getId()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据id查询
	 */
	@Override
	public Receipt findById(String id) {
		String sql = "select * from JiaoCaiReceipt where id= ? ";
		Receipt r = null;
		try {
			r = (Receipt) getJdbcTemplate().queryForObject(sql, new Object[]{id}, new RowMapper() {
				
				@Override
				public Receipt mapRow(ResultSet rs, int arg1) throws SQLException {
					Receipt rr = new Receipt();
					rr.setId(rs.getString("id"));
					rr.setQtyreceipt(rs.getInt("qtyreceipt"));
					rr.setIssuenumber(rs.getString("issuenumber"));
					rr.setSubcode(rs.getString("subcode"));
					rr.setQtyreceipt(rs.getInt("qtyreceipt"));
					rr.setPack(rs.getInt("pack"));
					return rr;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		} finally{
			return r;
		}
		
	}
	/**
	 * 删除
	 */
	@Override
	public int delete(String id) {
		String sql = "delete from JiaoCaiReceipt where id= ?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 选择未收货征订代码
	 */
	@Override
	public List<Receipt> waitReceiptSubcodeData(Receipt receipt) {
		List<Receipt> list = null;
		String sql = "select a.issuenumber,a.subcode,c.barcode,c.descr,c.price,c.publisher,(select shortname from JiaoCaiStorer d where c.publisher=d.storerkey ) shortname,a.orderqty,isnull(b.qtyreceipt,0) qtyreceipt,a.orderqty-isnull(b.qtyreceipt,0) qty from (select issuenumber,subcode,SUM(orderqty) orderqty from JiaoCaiOrders group by issuenumber,subcode) a left join (select issuenumber,subcode,SUM(qtyreceipt) qtyreceipt  from JiaoCaiReceipt group by issuenumber,subcode ) b on a.issuenumber = b.issuenumber and a.subcode = b.subcode left join JiaoCaiSku c on a.issuenumber = c.issuenumber and a.subcode = c.subcode where a.orderqty-isnull(b.qtyreceipt,0) > 0 ";
		if(receipt.getIssuenumber() != null && !"".equals(receipt.getIssuenumber())){
			sql += " and a.issuenumber = '" + receipt.getIssuenumber() + "'";
		}
		if(receipt.getSubcode() != null && !"".equals(receipt.getSubcode())){
			sql += " and a.subcode = '" + receipt.getSubcode() + "'";
		}
		if(receipt.getBarcode() != null && !"".equals(receipt.getBarcode())){
			sql += " and c.barcode = '" + receipt.getBarcode() + "'";
		}
		
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Receipt mapRow(ResultSet rs, int arg1) throws SQLException {
					Receipt r = new Receipt();
					r.setIssuenumber(rs.getString("issuenumber"));
					r.setSubcode(rs.getString("subcode"));
					r.setBarcode(rs.getString("barcode"));
					r.setDescr(rs.getString("descr"));
					r.setPrice(rs.getDouble("price"));
					r.setPublisher(rs.getString("shortname"));
					r.setStorerkey(rs.getString("publisher"));
					r.setQtyreceipt(rs.getInt("qtyreceipt"));
					r.setOrderqty(rs.getInt("orderqty"));
					r.setQtyunreceipt(rs.getInt("qty"));
					return r;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 获得已收货的征订代码
	 */
	@Override
	public List<Receipt> loadReceiptSubcodeInfo(Receipt receipt) {
		String sql = "select a.issuenumber,a.subcode,b.barcode,b.descr,b.price,a.pack,b.publisher,(select shortname from JiaoCaiStorer c where b.publisher=c.storerkey) shortname,SUM(qtyreceipt) qtyreceipt from JiaoCaiReceipt a,JiaoCaiSku b where a.issuenumber = b.issuenumber and a.subcode = b.subcode and a.status = 0 ";
		if(receipt.getIssuenumber() != null && !"".equals(receipt.getIssuenumber())){
			sql += " and a.issuenumber = '" + receipt.getIssuenumber() + "'";
		}
		if(receipt.getSubcode() != null && !"".equals(receipt.getSubcode())){
			sql += " and a.subcode = '" + receipt.getSubcode() + "'";
		}
		sql += "group by a.issuenumber,a.subcode,b.barcode,b.descr,b.price,a.pack,b.publisher";
		List<Receipt> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Receipt mapRow(ResultSet rs, int arg1) throws SQLException {
					Receipt r = new Receipt();
					r.setBarcode(rs.getString("barcode"));
					r.setDescr(rs.getString("descr"));
					r.setIssuenumber(rs.getString("issuenumber"));
					r.setPack(rs.getInt("pack"));
					r.setPrice(rs.getDouble("price"));
					r.setPublisher(rs.getString("shortname"));
					r.setQtyreceipt(rs.getInt("qtyreceipt"));
					r.setStorerkey(rs.getString("publisher"));
					r.setSubcode(rs.getString("subcode"));
					return r;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}
