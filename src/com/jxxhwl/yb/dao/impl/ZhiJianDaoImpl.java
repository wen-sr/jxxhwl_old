package com.jxxhwl.yb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.yb.common.BaseDao;
import com.jxxhwl.yb.dao.ZhiJianDao;
import com.jxxhwl.yb.entity.ZhiJian;

@Repository
public class ZhiJianDaoImpl extends BaseDao implements ZhiJianDao {

	@Override
	public List<ZhiJian> loadData() {
		String sql = "select top 10 * from MoreBooksRegister order by adddate desc ";
		return this.getJdbcTemplate().query(sql, new RowMapper<ZhiJian>() {

			@Override
			public ZhiJian mapRow(ResultSet rs, int arg1) throws SQLException {
				ZhiJian zj = new ZhiJian();
				zj.setSku(rs.getString("sku"));
				zj.setBarcode(rs.getString("barcode"));
				zj.setDescr(rs.getString("descr"));
				zj.setPrice(rs.getDouble("price"));
				zj.setId(rs.getString("id"));
				zj.setQty(rs.getInt("qty"));
				zj.setTray(rs.getString("tray"));
				zj.setAddwho(rs.getString("addwho"));
				zj.setAdddate(rs.getString("adddate"));
				return zj;
			}
		});
	}
	
	@Override
	public int add(ZhiJian zhiJian) {
		String sql = "insert into MoreBooksRegister(sku,barcode,descr,price,qty,tray,addwho,adddate) values(?,?,?,?,?,?,?,getdate())";
		return this.getJdbcTemplate().update(sql, zhiJian.getSku(),zhiJian.getBarcode(),zhiJian.getDescr(),zhiJian.getPrice(),zhiJian.getQty(),zhiJian.getTray(),zhiJian.getAddwho());
	}

	@Override
	public int delete(String id) {
		String sql = "delete from MoreBooksRegister where id = ? ";
		return this.getJdbcTemplate().update(sql, id);
	}
}
