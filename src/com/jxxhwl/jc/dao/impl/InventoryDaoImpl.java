package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.InventoryDao;
import com.jxxhwl.jc.entity.Inventory;
@Repository("inventoryDao")
public class InventoryDaoImpl extends BaseDao implements InventoryDao {

	/**
	 * 映射类
	 * @author Administrator
	 *
	 */
	public class InventoryRowMapper implements RowMapper{
		@Override
		public Inventory mapRow(ResultSet rs, int arg1) throws SQLException {
			Inventory i = new Inventory();
			i.setId(rs.getString("id"));
			i.setIssuenumber(rs.getString("issuenumber"));
			i.setQtyallocated(rs.getInt("qtyallocated"));
			i.setQtyfree(rs.getInt("qtyfree"));
			i.setQtyreceipt(rs.getInt("qtyreceipt"));
			i.setQtyshipped(rs.getInt("qtyshipped"));
			i.setSubcode(rs.getString("subcode"));
			return i;
		}
		
	}
	/**
	 * 映射类
	 * @author Administrator
	 *
	 */
	public class InventoryRowMapper2 implements RowMapper{
		@Override
		public Inventory mapRow(ResultSet rs, int arg1) throws SQLException {
			Inventory i = new Inventory();
			i.setId(rs.getString("id"));
			i.setIssuenumber(rs.getString("issuenumber"));
			i.setQtyallocated(rs.getInt("qtyallocated"));
			i.setQtyfree(rs.getInt("qtyfree"));
			i.setQtyreceipt(rs.getInt("qtyreceipt"));
			i.setQtyshipped(rs.getInt("qtyshipped"));
			i.setSubcode(rs.getString("subcode"));
			i.setPack(rs.getInt("pack"));
			return i;
		}
		
	}

	/**
	 * 保存
	 */
	@Override
	public int save(Inventory inventory) {
		String sql = "insert into JiaoCaiInventory(issuenumber,subcode,qtyreceipt,qtyfree) values(?,?,?,?)";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{inventory.getIssuenumber(),inventory.getSubcode(),inventory.getQtyreceipt(),inventory.getQtyreceipt()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}

	/**
	 * 增加库存
	 */
	@Override
	public int add(Inventory inventory) {
		String sql = "update JiaoCaiInventory set qtyreceipt=qtyreceipt+?, qtyfree=qtyfree+?  where issuenumber = ? and subcode = ?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{inventory.getQtyreceipt(),inventory.getQtyreceipt(),inventory.getIssuenumber(),inventory.getSubcode()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}

	/**
	 * 减少库存
	 */
	@Override
	public int remove(Inventory inventory) {
		String sql = "update JiaoCaiInventory set qtyreceipt=qtyreceipt-?, qtyfree=qtyfree-? where issuenumber = ? and subcode = ?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{inventory.getQtyreceipt(),inventory.getQtyreceipt(),inventory.getIssuenumber(),inventory.getSubcode()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 根据期号和征订代码查询
	 */
	@Override
	public Inventory findByIssuenumberAndSubcode(Inventory inventory) {
		String sql = "select * from JiaoCaiInventory where issuenumber=? and subcode = ?";
		Inventory i = null;
		try {
			List<Inventory> list = getJdbcTemplate().query(sql, new Object[]{inventory.getIssuenumber(),inventory.getSubcode()}, new InventoryRowMapper());
			if(list.size() > 0){
				i = list.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return i;
	}
	/**
	 * 根据期号和征订代码查询明细
	 */
	@Override
	public Inventory findByIssuenumberAndSubcodeDetail(Inventory inventory) {

		String sql = "select sum(qtyfree) qtyfree from JiaoCaiInventory_detail where issuenumber=? and subcode = ? and pack = ? ";
		Inventory i = null;
		try {
			List<Inventory> list = getJdbcTemplate().query(sql, new Object[]{inventory.getIssuenumber(),inventory.getSubcode(),inventory.getPack()}, new RowMapper() {
				
				@Override
				public Inventory mapRow(ResultSet rs, int arg1) throws SQLException {
					Inventory in = new Inventory();
					in.setQtyfree(rs.getInt("qtyfree"));
					return in;
				}
			});
			if(list.size() > 0){
				i = list.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return i;
	
	}
	/**
	 * 查询
	 */
	@Override
	public List<Inventory> findByPage(int pageSize, int currentPage) {
		String sql = "select top " + pageSize + " a.id,a.issuenumber,a.subcode,b.barcode,b.price,b.descr,b.pack,a.qtyfree,b.publisher,c.shortname,a.qtyallocated from JiaoCaiInventory a,JiaoCaiSku b,JiaoCaiStorer c where a.issuenumber=b.issuenumber and a.subcode=b.subcode and b.publisher=c.storerkey and a.qtyfree > 0 and a.id not in (select top "+ (currentPage-1)*pageSize +" id from jiaocaiinventory where qtyfree > 0)";
		List<Inventory> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Inventory mapRow(ResultSet rs, int arg1) throws SQLException {
					Inventory i = new Inventory();
					i.setId(rs.getString("id"));
					i.setIssuenumber(rs.getString("issuenumber"));
					i.setSubcode(rs.getString("subcode"));
					i.setBarcode(rs.getString("barcode"));
					i.setPrice(rs.getDouble("price"));
					i.setDescr(rs.getString("descr"));
					i.setPack(rs.getInt("pack"));
					i.setQtyfree(rs.getInt("qtyfree"));
					i.setPublisher(rs.getString("publisher"));
					i.setShortname(rs.getString("shortname"));
					i.setQtyallocated(rs.getInt("qtyallocated"));
					return i;
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
	 * 查询库存待分发记录总条数
	 */
	@Override
	public int countTotal() {
		String sql = "select count(*) from jiaocaiinventory where qtyfree > 0";
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql,Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			return i;
		}
	}
	/**
	 * 库存分发修改库存
	 */
	@Override
	public int distributeInventory(Inventory inventory) {
		String sql = "update jiaocaiinventory set qtyallocated=qtyallocated + ? ,qtyfree=qtyfree - ? where issuenumber = ? and subcode = ? ";
		int i = 0;
		try {
			i =getJdbcTemplate().update(sql, new Object[]{inventory.getQtyallocated(),inventory.getQtyallocated(),inventory.getIssuenumber(),inventory.getSubcode()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 更新qtyallocated
	 */
	@Override
	public int updateInventoryChange(Inventory i, int newqty, int oldqty) {
		int inn = 0;
		int in = oldqty - newqty;
		String sql = "update jiaocaiinventory set qtyallocated=qtyallocated - ?,qtyfree=qtyfree + ? where issuenumber=? and subcode=?";
		try {
			inn = getJdbcTemplate().update(sql,new Object[]{in,in,i.getIssuenumber(),i.getSubcode()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return inn;
	}
	/**
	 * 根据期号查询待分发的信息
	 */
	@Override
	public List<Inventory> loadDistributionSubcodeByIssuenumber(
			String issuenumber) {
		String sql = "select a.id,a.issuenumber,a.subcode,b.barcode,b.price,b.descr,b.pack,a.qtyfree,b.publisher,c.shortname,a.qtyallocated from JiaoCaiInventory a,JiaoCaiSku b,JiaoCaiStorer c where a.issuenumber=b.issuenumber and a.subcode=b.subcode and b.publisher=c.storerkey and a.qtyfree > 0";
		if(issuenumber != null && !"".equals(issuenumber)){
			sql += " and a.issuenumber='" + issuenumber + "'";
		}
		List<Inventory> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Inventory mapRow(ResultSet rs, int arg1) throws SQLException {
					Inventory i = new Inventory();
					i.setId(rs.getString("id"));
					i.setIssuenumber(rs.getString("issuenumber"));
					i.setSubcode(rs.getString("subcode"));
					i.setBarcode(rs.getString("barcode"));
					i.setPrice(rs.getDouble("price"));
					i.setDescr(rs.getString("descr"));
					i.setPack(rs.getInt("pack"));
					i.setQtyfree(rs.getInt("qtyfree"));
					i.setPublisher(rs.getString("publisher"));
					i.setShortname(rs.getString("shortname"));
					i.setQtyallocated(rs.getInt("qtyallocated"));
					return i;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return list;
	}
	/**
	 * 根据征订代码和期号查询待分发信息
	 */
	@Override
	public List<Inventory> findByPage(int pageSize, int currentPage, Inventory inventory) {
		String sql = "select top " + pageSize + " a.id,a.issuenumber,a.subcode,b.barcode,b.price,b.descr,a.qtyfree,b.publisher,c.shortname,a.qtyallocated from JiaoCaiInventory a,JiaoCaiSku b,JiaoCaiStorer c where a.issuenumber=b.issuenumber and a.subcode=b.subcode and b.publisher=c.storerkey and a.qtyfree > 0 ";
		String sqladd = "";
		if(inventory.getIssuenumber() != null && !"".equals(inventory.getIssuenumber())){
			sql += " and a.issuenumber = '" + inventory.getIssuenumber() + "'";
			sqladd += " and a.issuenumber = '" + inventory.getIssuenumber() + "'";
		}
		if(inventory.getSubcode() != null && !"".equals(inventory.getSubcode())){
			sql += " and a.subcode = '" + inventory.getSubcode() + "'";
			sqladd += " and a.subcode = '" + inventory.getSubcode() + "'";
		}
		if(inventory.getBarcode() != null && !"".equals(inventory.getBarcode())){
			sql += " and b.barcode = '" + inventory.getBarcode() + "'";
			sqladd += " and b.barcode = '" + inventory.getBarcode() + "'";
		}
		sql += " and a.id not in (select top "+ (currentPage-1)*pageSize +" id from JiaoCaiInventory where qtyfree > 0 " + sqladd +	")";
		List<Inventory> list = null;
		try {
			list = getJdbcTemplate().query(sql, new RowMapper() {
				
				@Override
				public Inventory mapRow(ResultSet rs, int arg1) throws SQLException {
					Inventory i = new Inventory();
					i.setId(rs.getString("id"));
					i.setIssuenumber(rs.getString("issuenumber"));
					i.setSubcode(rs.getString("subcode"));
					i.setBarcode(rs.getString("barcode"));
					i.setPrice(rs.getDouble("price"));
					i.setDescr(rs.getString("descr"));
					i.setQtyfree(rs.getInt("qtyfree"));
					i.setPublisher(rs.getString("publisher"));
					i.setShortname(rs.getString("shortname"));
					i.setQtyallocated(rs.getInt("qtyallocated"));
					return i;
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
	 * 根据期号和征订代码查询总条数
	 */
	@Override
	public int countTotal(Inventory inventory) {
		String sql = "select count(*) from JiaoCaiInventory a,JiaoCaiSku b,JiaoCaiStorer c where a.issuenumber=b.issuenumber and a.subcode=b.subcode and b.publisher=c.storerkey and a.qtyfree > 0 ";
		if(inventory.getIssuenumber() != null && !"".equals(inventory.getIssuenumber())){
			sql += " and a.issuenumber = '" + inventory.getIssuenumber() + "'";
		}
		if(inventory.getSubcode() != null && !"".equals(inventory.getSubcode())){
			sql += " and a.subcode = '" + inventory.getSubcode() + "'";
		}
		if(inventory.getBarcode() != null && !"".equals(inventory.getBarcode())){
			sql += " and b.barcode = '" + inventory.getBarcode() + "'";
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
	 * 查询库存明细
	 */
	@Override
	public List<Inventory> findInventoryDetail(Inventory inventory) {
		String sql = "select * from JiaoCaiInventory_detail ";
		int len = sql.length();
		if(inventory.getIssuenumber() != null && !"".equals(inventory.getIssuenumber())){
			if(sql.length() == len){
				sql += " where issuenumber='" + inventory.getIssuenumber() + "'";
			}else {
				sql += " and  issuenumber='" + inventory.getIssuenumber() + "'";
			}
		}
		if(inventory.getSubcode() != null && !"".equals(inventory.getSubcode())){
			if(sql.length() == len){
				sql += " where subcode='" + inventory.getSubcode() + "'";
			}else {
				sql += " and  subcode='" + inventory.getSubcode() + "'";
			}
		}
		if(inventory.getPack() != 0 && !"".equals(inventory.getPack())){
			if(sql.length() == len){
				sql += " where pack='" + inventory.getPack() + "'";
			}else {
				sql += " and  pack='" + inventory.getPack() + "'";
			}
		}
		List<Inventory> list = null;
		try {
			list = getJdbcTemplate().query(sql, new InventoryRowMapper2());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return list;
	}
	/**
	 * 保存明细
	 */
	@Override
	public int saveDetail(Inventory inventory) {
		String sql = "insert into JiaoCaiInventory_detail(issuenumber,subcode,qtyreceipt,qtyfree,pack) values(?,?,?,?,?)";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql, new Object[]{inventory.getIssuenumber(),inventory.getSubcode(),inventory.getQtyreceipt(),inventory.getQtyreceipt(),inventory.getPack()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	
	/**
	 * 添加库存明细
	 */
	@Override
	public int addDetail(Inventory inventory) {
		String sql = "update JiaoCaiInventory_detail set qtyreceipt=qtyreceipt+?, qtyfree=qtyfree+?  where issuenumber = ? and subcode = ? and pack=?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{inventory.getQtyreceipt(),inventory.getQtyreceipt(),inventory.getIssuenumber(),inventory.getSubcode(),inventory.getPack()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 删除明细
	 */
	@Override
	public int removeDetail(Inventory inventory) {
		String sql = "update JiaoCaiInventory_detail set qtyreceipt=qtyreceipt-?, qtyfree=qtyfree-? where issuenumber = ? and subcode = ? and pack=?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{inventory.getQtyreceipt(),inventory.getQtyreceipt(),inventory.getIssuenumber(),inventory.getSubcode(),inventory.getPack()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 修改收货时更新库存信息
	 */
	@Override
	public int updateReceiptInventory(Inventory inventory, int qty) {
		String sql = "update JiaoCaiInventory set qtyreceipt=qtyreceipt+?, qtyfree=qtyfree+? where issuenumber = ? and subcode = ?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{qty,qty,inventory.getIssuenumber(),inventory.getSubcode()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 修改收货时更新库存明细信息
	 */
	@Override
	public int updateReceiptInventoryDetail(Inventory inventory, int qty) {
		String sql = "update JiaoCaiInventory_detail set qtyreceipt=qtyreceipt+?, qtyfree=qtyfree+? where issuenumber = ? and subcode = ? and pack= ?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{qty,qty,inventory.getIssuenumber(),inventory.getSubcode(),inventory.getPack()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 查询可用库存和该品种的每包捆数
	 */
	@Override
	public Inventory queryInventoryAndBundle(Inventory inventory) {
		String sql = "select a.issuenumber,a.subcode,SUM(qtyfree) qtyfree,(select bundle from JiaoCaiSku b where a.issuenumber=b.issuenumber and a.subcode = b.subcode ) bundle from JiaoCaiInventory_detail a where a.issuenumber = ? and a.subcode = ? ";
		if(inventory.getPack() != 0 && !"".equals(inventory.getPack())){
			sql += " and a.pack='" + inventory.getPack() + "'";
		}
		sql += " group by a.issuenumber,a.subcode ";
		Inventory i = null;
		try {
			List<Inventory> list = getJdbcTemplate().query(sql, new Object[]{inventory.getIssuenumber(),inventory.getSubcode()},new RowMapper() {
				
				@Override
				public Inventory mapRow(ResultSet rs, int arg1) throws SQLException {
					Inventory in = new Inventory();
					in.setIssuenumber(rs.getString("issuenumber"));
					in.setSubcode(rs.getString("subcode"));
					in.setQtyfree(rs.getInt("qtyfree"));
					in.setBundle(rs.getInt("bundle"));
					return in;
				}
			});
			if(list.size() > 0){
				i = list.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}
	/**
	 * 更新库存明细
	 */
	@Override
	public int updateInventoryDetailChange(Inventory i, int newqty, int oldqty) {
		int inn = 0;
		int in = oldqty - newqty;
		String sql = "update JiaoCaiInventory_detail set qtyallocated=qtyallocated - ?,qtyfree=qtyfree + ? where issuenumber=? and subcode=? and pack = ?";
		try {
			inn = getJdbcTemplate().update(sql,new Object[]{in,in,i.getIssuenumber(),i.getSubcode(),i.getPack()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return inn;
	}
	/**
	 * 更新inventor与inventory_detail保持一致
	 * @param inventory
	 */
	@Override
	public int updateInventoryFromInventoryDetail(Inventory inventory) {
		String sql = "update JiaoCaiInventory set qtyallocated=b.qtyallocated,qtyshipped=b.qtyshipped,qtyfree=b.qtyfree from JiaoCaiInventory a,(select issuenumber,subcode,SUM(qtyallocated) qtyallocated,SUM(qtyshipped) qtyshipped,SUM(qtyfree) qtyfree from JiaoCaiInventory_detail group by issuenumber,subcode) b where a.issuenumber=b.issuenumber and a.subcode=b.subcode and a.issuenumber = ? and a.subcode = ?  ";
		return this.getJdbcTemplate().update(sql, inventory.getIssuenumber(), inventory.getSubcode());
	}

	/**
	 * 判断库存是否一致
	 * @param inventory
	 * @return
	 */
	@Override
	public int isEqual(Inventory inventory) {
		String sql = "select count(*) from JiaoCaiInventory a, (select issuenumber,subcode,SUM(qtyallocated) qtyallocated,SUM(qtyshipped) qtyshipped,SUM(qtyfree) qtyfree from JiaoCaiInventory_detail group by issuenumber,subcode) b where a.issuenumber = b.issuenumber and a.subcode = b.subcode " +
				"and a.qtyfree = b.qtyfree and a.issuenumber = ? and a.subcode = ?";
		return this.getJdbcTemplate().queryForObject(sql, new Object[]{inventory.getIssuenumber(), inventory.getSubcode()}, Integer.class);
	}
}
