package com.jxxhwl.yb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jxxhwl.yb.dao.KeTuiDao;
import com.jxxhwl.yb.entity.KeTui;

	@Repository
	public class KeTuiDaoImpl extends JdbcDaoSupport implements KeTuiDao {
		@Resource(name="dataSource1")
		public void setMyDatasource (DataSource ds ) {
			super.setDataSource(ds);
		}

		@Override
		public KeTui getBingX(String boxno) {
			String sql = "select a.boxno,a.consk,a.shortname,b.chuwei,sum(a.packqty) qty from pack_case a,ktcw b where a.consk = b.code and a.boxno = ? and a.status <> '9' and ((a.fromtype = 1 and a.type =1) or (a.fromtype = 4 and a.type =2) or (a.fromtype = 0 and a.type = 2))  group by a.boxno, a.consk, a.shortname, b.chuwei";
			List<KeTui> list = this.getJdbcTemplate().query(sql, new Object[]{boxno}, new RowMapper<KeTui>(){

				@Override
				public KeTui mapRow(ResultSet rs, int arg1)
						throws SQLException {
					KeTui kt = new KeTui();
					kt.setBoxno(rs.getString("boxno"));
					kt.setConsk(rs.getString("consk"));
					kt.setLoc(rs.getString("chuwei"));
					kt.setShortname(rs.getString("shortname"));
					kt.setQty(rs.getInt("qty"));
					return kt;
				}
			});
			if(list != null && list.size() > 0){
				return list.get(0);
			}else{
				return null;
			}
		}
}
