package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.OrdersDao;
import com.jxxhwl.jc.entity.Orders;
import com.jxxhwl.jc.entity.Sku;

@Repository("ordersDao")
public class OrdersDaoImpl extends BaseDao implements OrdersDao {
	
	private class OrdersRowMapper implements RowMapper{

		@Override
		public Orders mapRow(ResultSet rs, int i) throws SQLException {
			Orders o = new Orders();
			o.setAdddate(rs.getString("adddate"));
			o.setAddwho(rs.getString("addwho"));
			o.setDescr(rs.getString("descr"));
			o.setId(rs.getString("id"));
			o.setIssuenumber(rs.getString("issuenumber"));
			o.setOrderqty(rs.getInt("orderqty"));
			o.setPrice(rs.getDouble("price"));
			o.setPublisher(rs.getString("publisher"));
			o.setShortname(rs.getString("shortname"));
			o.setSubcode(rs.getString("subcode"));
			o.setBarcode(rs.getString("barcode"));
			o.setEditwho(rs.getString("editwho"));
			o.setEditdate(rs.getString("editdate"));
			return o;
		}
		
	}
	
	/**
	 * 保存
	 */
	@Override
	public int save(Orders orders) {
		String sql = "insert into JiaoCaiOrders(issuenumber,subcode,orderqty,addwho,adddate) values(?,?,?,?,getdate())";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{orders.getIssuenumber(),orders.getSubcode(),orders.getOrderqty(),orders.getAddwho()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 加载已采购的信息
	 */
	@Override
	public List<Orders> loadOrdersInfo(Orders orders, int currentPage,
			int pageSize) {
		String sql = "select top "+ pageSize +" a.*,b.descr,b.barcode,b.price,b.publisher,(select shortname from JiaoCaiStorer c where b.publisher=c.storerkey) shortname from JiaoCaiOrders a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode = b.subcode ";
		int len = sql.length();
		if(orders.getIssuenumber() != null && !"".equals(orders.getIssuenumber())){
			sql += " and a.issuenumber='" + orders.getIssuenumber() + "'"; 
		}
		if(orders.getSubcode() != null && !"".equals(orders.getSubcode())){
			sql += " and a.subcode='" + orders.getSubcode() + "'";
		}
		String sss = sql.substring(len);
		sql += " and a.id not in (select top "+ (currentPage-1)*pageSize +" a.id from JiaoCaiOrders a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode = b.subcode " + sss + " order by a.id desc) order by a.id desc"; 
		List<Orders> list = null;
		try {
			list = getJdbcTemplate().query(sql, new OrdersRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 统计记录条数
	 */
	@Override
	public int countTotal(Orders orders) {
		String sql = "select count(*) from JiaoCaiOrders a,JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode = b.subcode ";
		if(orders.getIssuenumber() != null && !"".equals(orders.getIssuenumber())){
			sql += " and a.issuenumber='" + orders.getIssuenumber() + "'"; 
		}
		if(orders.getSubcode() != null && !"".equals(orders.getSubcode())){
			sql += " and a.subcode='" + orders.getSubcode() + "'";
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
	 * 加载已采购征订代码
	 */
	@Override
	public List<Sku> loadOrdersSku(String issuenumber, int currentPage,
			int pageSize) {
		String sql = "select distinct a.issuenumber,a.subcode,b.barcode,b.descr,b.price,b.publisher,(select shortname from JiaoCaiStorer c where b.publisher=c.storerkey) shortname from JiaoCaiOrders a,JiaoCaiSku b where a.issuenumber = b.issuenumber and a.subcode = b.subcode ";
		List<Sku> list = null;
		if(issuenumber != null && !"".equals(issuenumber)){
			sql += " and a.issuenumber='" + issuenumber + "'"; 
		}
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Orders mapRow(ResultSet rs, int arg1) throws SQLException {
					Orders o = new Orders();
					o.setDescr(rs.getString("descr"));
					o.setIssuenumber(rs.getString("issuenumber"));
					o.setPrice(rs.getDouble("price"));
					o.setPublisher(rs.getString("publisher"));
					o.setShortname(rs.getString("shortname"));
					o.setSubcode(rs.getString("subcode"));
					o.setBarcode(rs.getString("barcode"));
					return o;
				}
			});
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
		int i = 0;
		String sql = "delete from JiaoCaiOrders where id = ? ";
		try {
			i = getJdbcTemplate().update(sql, new Object[]{id});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 修改
	 */
	@Override
	public int update(Orders orders) {
		int i = 0;
		String sql = "update JiaoCaiOrders set orderqty=?,editwho=?,editdate=getdate() where id=? ";
		try {
			i = getJdbcTemplate().update(sql, new Object[]{orders.getOrderqty(),orders.getAddwho(),orders.getId()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 查询预分发可用数
	 */
	@Override
	public int loadOrdersQty(String issuenumber, String subcode) {
		String sql = "select (orderqty-ISNULL(b.qtyallocated,0)) freeqty from (select issuenumber,subcode,SUM(orderqty) orderqty from JiaoCaiOrders group by issuenumber,subcode) a left join (select issuenumber,subcode,SUM(qtyallocated) qtyallocated from JiaoCaiDistribute where type=0 group by issuenumber,subcode ) b on a.issuenumber=b.issuenumber and a.subcode = b.subcode  left join JiaoCaiSku c on a.issuenumber=c.issuenumber and a.subcode = c.subcode where a.orderqty - ISNULL(b.qtyallocated,0) >0 and a.issuenumber=? and a.subcode =? ";
		int qty = 0;
		try {
			List<Object> list = getJdbcTemplate().query(sql, new Object[]{issuenumber,subcode}, new RowMapper() {
				
				@Override
				public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
					int i = rs.getInt(1);
					return i;
				}
			});
			if(list.size()>0){
				qty = (Integer) list.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return qty;
	}
	
	/**
	 * 加载收货可用数量
	 */
	@Override
	public int loadReceiptOrderQty(String issuenumber, String subcode) {
		String sql = "select (orderqty-ISNULL(b.qtyreceipt,0)) freeqty from (select issuenumber,subcode,SUM(orderqty) orderqty from JiaoCaiOrders group by issuenumber,subcode) a left join (select issuenumber,subcode,SUM(qtyreceipt) qtyreceipt from JiaoCaiReceipt group by issuenumber,subcode ) b on a.issuenumber=b.issuenumber and a.subcode = b.subcode left join JiaoCaiSku c on a.issuenumber=c.issuenumber and a.subcode = c.subcode where a.orderqty - ISNULL(b.qtyreceipt,0) >0 and a.issuenumber=? and a.subcode =?";
		int qty = 0;
		try {
			List<Object> list = getJdbcTemplate().query(sql, new Object[]{issuenumber,subcode}, new RowMapper() {
				
				@Override
				public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
					int i = rs.getInt(1);
					return i;
				}
			});
			if(list.size()>0){
				qty = (Integer) list.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return qty;
	}
}
