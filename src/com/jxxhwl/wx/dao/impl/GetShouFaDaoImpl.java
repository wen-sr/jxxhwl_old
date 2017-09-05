package com.jxxhwl.wx.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.DatabaseContextHolder;
import com.jxxhwl.wx.dao.GetShouFaDao;
import com.jxxhwl.wx.entity.FaHuo;
import com.jxxhwl.wx.entity.QueryShouFa;
import com.jxxhwl.wx.entity.ShouHuo;
import com.jxxhwl.wx.entity.ShouToday;
import com.jxxhwl.wx.entity.Storer;
@Repository("getShouFaDao")
public class GetShouFaDaoImpl extends JdbcDaoSupport implements GetShouFaDao {
	//datasource
	@Resource(name="dataSource1")
	public void setMyDataSource(DataSource ds ) {
		super.setDataSource(ds);
	}
	
	//getFaDAOImplAdd
	@Resource
	private GetFaDAOImplAdd getFaDAOImplAdd;

	/**
	 * shou
	 */
	@Override
	public List<ShouHuo> getShou(QueryShouFa queryShouFa) {
		List<ShouHuo> list = new ArrayList<ShouHuo>();
		String code = queryShouFa.getCode();
		char[] c = code.toCharArray();
		int i = (int)c[0];
		String sql = null;
		if((i>=19968 && i<=171941 )){
			sql = "select xsogroupkey,ship_no,from_vendor,s.description,receive_qty,to_char(a.adddate,'yyyymmdd hh24:mi') from xsogroup a,storer s where a.from_vendor=s.storerkey and to_char(a.adddate,'yyyymmdd') between '" + queryShouFa.getBegin() + "' and '" + queryShouFa.getEnd() + "' and s.description like '%";
			for(char cc : c){
				sql += ( cc +"%");
			}
			sql += "' order by 1";
		}else{
			sql = "select xsogroupkey,ship_no,from_vendor,(select description from storer s where a.from_vendor=s.storerkey ) descr,receive_qty,to_char(adddate,'yyyymmdd hh24:mi') from xsogroup a where to_char(adddate,'yyyymmdd') between '" + queryShouFa.getBegin() + "' and '" + queryShouFa.getEnd() + "' and from_vendor='" + queryShouFa.getCode() + "' ";
		}
		list = getJdbcTemplate().query(sql, new RowMapper(){
			ShouHuo sh= null;
			@Override
			public ShouHuo mapRow(ResultSet rs, int rowNum) throws SQLException {
				sh = new ShouHuo();
				sh.setRegister(rs.getString(1));
				sh.setShipno(rs.getString(2));
				sh.setCode(rs.getString(3));
				sh.setCustomer(rs.getString(4));
				sh.setQty(rs.getInt(5));
				sh.setDate(rs.getString(6));
				return sh;
			}
		});
		return list;
	}

	/**
	 * fa
	 */
	@Override
	public List<FaHuo> getFa(QueryShouFa queryShouFa) {
		List<FaHuo> list = getFaDAOImplAdd.getFa(queryShouFa);
		return list;
	}

	/**
	 * kehu
	 */
	@Override
	public List<Storer> getStorer(String matchInfo) {
		List<Storer> list =new ArrayList<Storer>();
		String sql = null;
		char c[] = matchInfo.toCharArray();
		int i = (int)c[0];
		if((i>=19968 && i<=171941 )){
			sql = "select storerkey,description from storer where description like '%";
			for(char ch : c){
				sql += (ch +"%");
			}
			sql += "' order by 2";
		} else if(Character.isDigit(c[0]) || Character.isLetter(c[0])){
			sql = "select storerkey,description from storer where storerkey like '%";
			for(char ch : c){
				sql += (ch +"%");
			}
			sql += "' order by 1";
		}else{
		}
		
		list = getJdbcTemplate().query(sql, new RowMapper(){
			Storer storer = null;
			@Override
			public Storer mapRow(ResultSet rs, int i) throws SQLException {
				storer = new Storer();
				storer.setStorerkey(rs.getString(1));
				storer.setDescription(rs.getString(2));
				return storer;
			}
		});
		return list;
	}
	/**
	 * 收货产量
	 * @return
	 */
	@Override
	public List<ShouToday> getShouToday() {
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
	
}
