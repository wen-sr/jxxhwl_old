package com.jxxhwl.yb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.yb.common.BaseDao;
import com.jxxhwl.yb.dao.BZChuHuoDao;
import com.jxxhwl.yb.entity.BZChuHuo;

@Repository("bZChuHuoDao")
public class BZChuHuoDaoImpl extends BaseDao implements BZChuHuoDao{
	/**
	 * 查询已登记信息
	 */
	@Override
	public List<BZChuHuo> loadData(int currentPage, int pageSize, String dd) {
		String sql = "select top "+ pageSize +" * from BZChuHuo ";
		if(dd != null && !"".equals(dd)){
			sql += " where dd='" + dd + "'" + " and id not in (select top "+ (currentPage-1)*pageSize +" id from BZChuHuo where dd='"+ dd +"' order by dd desc ) order by dd desc ";
		}else{
			sql += " where id not in (select top "+ (currentPage-1)*pageSize +" id from BZChuHuo order by dd desc ) order by dd desc ";
		}
		List<BZChuHuo> list = this.getJdbcTemplate().query(sql, new RowMapper<BZChuHuo>(){

			@Override
			public BZChuHuo mapRow(ResultSet rs, int arg1)
					throws SQLException {
				BZChuHuo bz = new BZChuHuo();
				bz.setId(rs.getString("id"));
				bz.setAdddate(rs.getString("adddate"));
				bz.setDd(rs.getString("dd"));
				bz.setDzz(rs.getInt("dzz"));
				bz.setFaCase(rs.getInt("faCase"));
				bz.setSgFa(rs.getInt("sgFa"));
				bz.setTuiCase(rs.getInt("tuiCase"));
				bz.setTuiRej(rs.getInt("tuiRej"));
				bz.setZpRej(rs.getInt("zpRej"));
				bz.setAddwho(rs.getString("addwho"));
				bz.setNote(rs.getString("note"));
				return bz;
			}
			
		});
		return list;
	}
	/**
	 * 统计总条数
	 */
	@Override
	public int getTotal(String dd) {
		String sql = "select count(*) from BZChuHuo";
		if(dd != null && !"".equals(dd)){
			sql += " where dd = '" + dd + "'";
		}
		
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}
	/**
	 * 保存
	 */
	@Override
	public int add(BZChuHuo bZChuHuo) {
		String sql = "insert into BZChuHuo(faCase,tuiCase,zpRej,tuiRej,sgFa,dzz,dd,adddate,addwho,note) values(?,?,?,?,?,?,?,getdate(),?,?)";
		return this.getJdbcTemplate().update(sql, bZChuHuo.getFaCase(),bZChuHuo.getTuiCase(),bZChuHuo.getZpRej(),bZChuHuo.getTuiRej(),bZChuHuo.getSgFa(),bZChuHuo.getDzz(),bZChuHuo.getDd(),bZChuHuo.getAddwho(),bZChuHuo.getNote());
	}
	/**
	 * 修改
	 */
	@Override
	public int edit(BZChuHuo bZChuHuo) {
		String sql = "update BZChuHuo set faCase=?,tuiCase=?,zpRej=?,tuiRej=?,sgFa=?,dzz=?,dd=?,note=? where id=?";
		return this.getJdbcTemplate().update(sql, bZChuHuo.getFaCase(),bZChuHuo.getTuiCase(),bZChuHuo.getZpRej(),bZChuHuo.getTuiRej(),bZChuHuo.getSgFa(),bZChuHuo.getDzz(),bZChuHuo.getDd(),bZChuHuo.getNote(),bZChuHuo.getId());
	}
	
}
