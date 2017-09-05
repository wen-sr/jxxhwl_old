package com.jxxhwl.yb.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.yb.common.BaseDao;
import com.jxxhwl.yb.dao.PackDao;
import com.jxxhwl.yb.entity.Pack;

@Repository("packDao_yb")
public class PackDaoImpl extends BaseDao implements PackDao {

	@Override
	public List<Pack> findByCode(String code) {
		String sql = "select distinct susr7,publisher from kc_pack ";
		if(!"".equals(code) && code != null ){
			sql += " where code = '" + code + "'";
		}
		return this.getJdbcTemplate().query(sql, new RowMapper<Pack>() {

			@Override
			public Pack mapRow(ResultSet rs, int arg1) throws SQLException {
				Pack p = new Pack();
				p.setCode(rs.getString("susr7"));
				p.setShortname(rs.getString("publisher"));
				return p;
			}
		});
	}
	/**
	 * 根据条码获得图书信息
	 */
	@Override
	public List<Pack> findByBarcode(Pack pack) {
		String sql = "select id,barcode,descr,price,sum(qty-isnull(qty_confirm,0)) qqty from kc_pack where barcode=? group by id,barcode,descr,price";
		return this.getJdbcTemplate().query(sql, new Object[]{pack.getBarcode()}, new RowMapper<Pack>() {

			@Override
			public Pack mapRow(ResultSet rs, int arg1) throws SQLException {
				Pack p = new Pack();
				p.setBarcode(rs.getString("barcode"));
				p.setId(rs.getString("id"));
				p.setDescr(rs.getString("descr"));
				p.setPrice(rs.getDouble("price"));
				p.setQty(rs.getInt("qqty"));
				return p;
			}
		});
	}
	/**
	 * 加载未打包数据
	 */
	@Override
	public List<Pack> loadPackData(int currentPage, int pageSize, Pack pack,final String status) {
		String mystatus = status;
		if("0".equals(status)){
			mystatus = "0','5";
		}
		String sql = "select top " + pageSize + " (qty-isnull(qty_confirm,0)) qqty,* from kc_pack where status in ('" + mystatus + "')";
		String sqladd = "";
		if(pack.getCode()!= null && !"".equals(pack.getCode())){
			sql += " and susr7='" + pack.getCode() + "'";
			sqladd += " and susr7='" + pack.getCode() + "'";
		}
		if(pack.getBarcode()!= null && !"".equals(pack.getBarcode())){
			sql += " and barcode='" + pack.getBarcode() + "'";
			sqladd += " and barcode='" + pack.getBarcode() + "'";
		}
		sql += " and id not in (select top "+ (currentPage-1)*pageSize +" id from kc_pack where status in ('" + mystatus + "')";
		sql += sqladd;
		sql += ")";
		return getJdbcTemplate().query(sql, new RowMapper<Pack>() {

			@Override
			public Pack mapRow(ResultSet rs, int arg1) throws SQLException {
				Pack p = new Pack();
				p.setBarcode(rs.getString("barcode"));
				p.setCode(rs.getString("susr7"));
				p.setDescr(rs.getString("descr"));
				p.setId(rs.getString("id"));
				p.setPrice(rs.getDouble("price"));
				if("0".equals(status)){
					p.setQty(rs.getInt("qqty"));
				}else if("5".equals(status)){
					p.setQty(rs.getInt("qty_confirm"));
				}
				p.setShortname(rs.getString("publisher"));
				p.setSku(rs.getString("sku"));
				return p;
			}
		});
	}
	/**
	 * 统计记录条数
	 */
	@Override
	public int count(Pack pack, String status) {
		String sql = "select count(*) from kc_pack where status='" + status + "'";
		if(pack.getCode()!= null && !"".equals(pack.getCode())){
			sql += " and susr7='" + pack.getCode() + "'";
		}
		if(pack.getBarcode()!= null && !"".equals(pack.getBarcode())){
			sql += " and barcode='" + pack.getBarcode() + "'";
		}
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}
	/**
	 * 打包确认
	 */
	@Override
	public int confirmPack(Pack pack) {
		String sql = "update kc_pack set QTY_CONFIRM=isnull(QTY_CONFIRM,0)+?,STATUS=5 where ID=?";
		return this.getJdbcTemplate().update(sql, pack.getQty(),pack.getId());
	}
	
	/**
	 * 获得新批次号
	 */
	@Override
	public String getBatchno() {
		String procedure = "{call kc_pack_add_batchno(?)}";  
		return this.getJdbcTemplate().execute(procedure,new CallableStatementCallback<String>() {

			@Override
			public String doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				 cs.registerOutParameter(1,OracleTypes.VARCHAR);
				 cs.execute();
				return cs.getString(1);
			}
		});
	}
	/**
	 * 取消打包
	 */
	@Override
	public int cancelPack(Pack pack) {
		String sql = "update kc_pack set QTY_CONFIRM=0,STATUS=0 where ID=?";
		return this.getJdbcTemplate().update(sql, pack.getId());
	}
	/**
	 * 判断当前封包数量是否与期望数量相等
	 */
	@Override
	public boolean isEnough(String id) {
		String sql = "select qty,qty_confirm from kc_pack where id = ?";
		return this.getJdbcTemplate().query(sql, new Object[]{id}, new RowMapper<Boolean>() {

			@Override
			public Boolean mapRow(ResultSet rs, int arg1) throws SQLException {
				int qty = rs.getInt("qty");
				int qty_confirm = rs.getInt("qty_confirm");
				if(qty-qty_confirm == 0 ){
					return true;
				}		
				return false;
			}
		}).get(0);
	}
	/**
	 * 封包
	 */
	@Override
	public int addCaseid(String id) {
		String sql;
		return 0;
	}
}
