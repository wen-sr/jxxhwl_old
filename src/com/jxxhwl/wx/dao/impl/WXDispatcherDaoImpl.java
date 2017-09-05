package com.jxxhwl.wx.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.DatabaseContextHolder;
import com.jxxhwl.jc.entity.ChanLiang;
import com.jxxhwl.wx.dao.WXDispatcherDao;
import com.jxxhwl.wx.entity.ShouToday;
@Repository("wXDispatcherDao")
public class WXDispatcherDaoImpl extends JDBCBaseDao implements WXDispatcherDao{
	
	@Resource(name="dynamicDataSource")
	@Override
	public void setMyDataSource(DataSource ds ) {
		super.setDataSource(ds);
	}
	/**
	 * 
	 */
	@Override
	public List<ShouToday> getShouToday() {
		DatabaseContextHolder.clearDBType();
		System.out.println(DatabaseContextHolder.getDBType());
		DatabaseContextHolder.setDBType(DatabaseContextHolder.DATA_SOURCE_1);
		System.out.println(DatabaseContextHolder.getDBType());
		String sql = "select (case when type=0 and ship_type='01' then '01' when type=0 and ship_type='02' then '02' when type=0 and ship_type='03' then '03' when type=0 and ship_type='04' then '04' when type=0 and ship_type='05' then '05' when type=1 and ship_type='01' then '06' when type=1 and ship_type='02' then '07' else '08' end) type,sum(receive_qty) qty from xsogroup where to_char(adddate,'yyyymmdd') = to_char(sysdate,'yyyymmdd') group by type,ship_type";
		List<ShouToday> list = new ArrayList<ShouToday>();
		list = getJdbcTemplate().query(sql, new RowMapper(){
			ShouToday s = null;
			@Override
			public ShouToday mapRow(ResultSet rs, int i) throws SQLException {
				s = new ShouToday();
				s.setType(rs.getString("type"));
				s.setQty(rs.getInt("qty"));
				return s;
			}
			
		});
		return list;
	}
	/**
	 * 出货数据
	 */
	@Override
	public int getChuHuo() {
		DatabaseContextHolder.clearDBType();
		DatabaseContextHolder.setDBType(DatabaseContextHolder.DATA_SOURCE_3);
		String sql = "select isnull(faCase,0) + ISNULL(tuiCase,0) + ISNULL(zpRej,0) + ISNULL(tuiRej,0) + ISNULL(sgFa,0) + ISNULL(dzz,0)   from BZChuHuo where dd = CONVERT(varchar(100), GETDATE(), 23)";
		List<Integer> list = this.getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getInt(1);
			}
		});
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return 0;
	}
	/**
	 * 教材
	 */
	@Override
	public List<ChanLiang> getJiaoCai() {
		DatabaseContextHolder.clearDBType();
		DatabaseContextHolder.setDBType(DatabaseContextHolder.DATA_SOURCE_3);
		String sql = "select HeJi,DaiFa,issue from JiaoCaiChanLiang where dd = CONVERT(varchar(100), GETDATE(), 23)";
		return this.getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public ChanLiang mapRow(ResultSet rs, int arg1) throws SQLException {
				ChanLiang c = new ChanLiang();
				c.setHeJi(rs.getInt("HeJi"));
				c.setDaiFa(rs.getInt("daiFa"));
				c.setIssue(rs.getString("issue"));
				return c;
			}
		});
	}
	
}
