package com.jxxhwl.yb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.yb.dao.SkuDao;
import com.jxxhwl.yb.entity.ZhiJian;

@Repository("skuDao_yb")
@Transactional
public class SkuDaoImpl extends JdbcDaoSupport implements SkuDao{
	
	@Resource(name="dataSource1")
	public void setMyDatasource (DataSource ds ) {
		super.setDataSource(ds);
	}
	/**
	 * 根据条码查询书号信息
	 */
	@Override
	public List<ZhiJian> findByBarcode(ZhiJian zhiJian) {
		String sql = "select storerkey,sku,barcode,descr,price from sku where barcode = ?";
		return this.getJdbcTemplate().query(sql, new Object[]{zhiJian.getBarcode()}, new RowMapper<ZhiJian>() {

			@Override
			public ZhiJian mapRow(ResultSet rs, int arg1) throws SQLException {
				ZhiJian zj = new ZhiJian();
				zj.setStorerkey(rs.getString("storerkey"));
				zj.setSku(rs.getString("sku"));
				zj.setBarcode(rs.getString("barcode"));
				zj.setDescr(rs.getString("descr"));
				zj.setPrice(rs.getDouble("price"));
				return zj;
			}
		});
	}

}
