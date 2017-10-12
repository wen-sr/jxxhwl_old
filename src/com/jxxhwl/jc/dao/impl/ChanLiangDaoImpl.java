package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.ChanLiangDao;
import com.jxxhwl.jc.entity.ChanLiang;

@Repository
public class ChanLiangDaoImpl extends BaseDao implements ChanLiangDao{

	/**
	 * 查询
	 */
	@Override
	public List<ChanLiang> loadData(int currentPage, int pageSize, String dd) {
		String sql = "select top "+ pageSize +" * from JiaoCaiChanLiang ";
		if(dd != null && !"".equals(dd)){
			sql += " where dd='" + dd + "'" + " and id not in (select top "+ (currentPage-1)*pageSize +" id from JiaoCaiChanLiang where dd='"+ dd +"' order by dd desc ) order by dd desc ";
		}else{
			sql += " where id not in (select top "+ (currentPage-1)*pageSize +" id from JiaoCaiChanLiang order by dd desc ) order by dd desc ";
		}
		List<ChanLiang> list = this.getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public ChanLiang mapRow(ResultSet rs, int arg1) throws SQLException {
				ChanLiang c = new ChanLiang();
				c.setAdddate(rs.getString("adddate"));
				c.setDaBao(rs.getInt("daBao"));
				c.setDaiFa(rs.getInt("daiFa"));
				c.setDd(rs.getString("dd"));
				c.setHeJi(rs.getInt("heJi"));
				c.setId(rs.getString("id"));
				c.setJiaoCan(rs.getInt("jiaoCan"));
				c.setLingPiaoZong(rs.getInt("lingPiaoZong"));
				c.setNanChangShi(rs.getInt("nanChangShi"));
				c.setTieBao(rs.getInt("tieBao"));
				c.setTui(rs.getInt("tui"));
				c.setWeiDao(rs.getInt("weiDao"));
				c.setWenJiao(rs.getInt("wenJiao"));
				c.setYunShuDang(rs.getInt("yunShuDang"));
				c.setYunShuZong(rs.getInt("yunShuZong"));
				c.setAddwho(rs.getString("addwho"));
				c.setNote(rs.getString("note"));
				c.setIssue(rs.getString("issue"));
				return c;
			}
		});
		return list;
	}
	/**
	 * 查询
	 */
	@Override
	public List<ChanLiang> loadOtherData(int currentPage, int pageSize, String dd) {
		String sql = "select top "+ pageSize +" * from OTHER_YIELD ";
		if(dd != null && !"".equals(dd)){
			sql += " where dd='" + dd + "'" + " and id not in (select top "+ (currentPage-1)*pageSize +" id from OTHER_YIELD where dd='"+ dd +"' order by dd desc ) order by dd desc ";
		}else{
			sql += " where id not in (select top "+ (currentPage-1)*pageSize +" id from OTHER_YIELD order by dd desc ) order by dd desc ";
		}
		List<ChanLiang> list = this.getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public ChanLiang mapRow(ResultSet rs, int arg1) throws SQLException {
				ChanLiang c = new ChanLiang();
				c.setAdddate(rs.getString("adddate"));
				c.setDd(rs.getString("dd"));
				c.setId(rs.getString("id"));
				c.setAddwho(rs.getString("addwho"));
				c.setNote(rs.getString("note"));
				c.setIssue(rs.getString("issue"));
				c.setReceiptToday(rs.getInt("receipt_today"));
				c.setReceiptSum(rs.getInt("receipt_sum"));
				c.setDeliverToday(rs.getInt("deliver_today"));
				c.setDeliverSum(rs.getInt("deliver_sum"));
				return c;
			}
		});
		return list;
	}

	/**
	 * 统计
	 */
	@Override
	public int getTotal(String dd) {
		String sql = "select count(*) from JiaoCaiChanLiang";
		if(dd != null && !"".equals(dd)){
			sql += " where dd = '" + dd + "'";
		}
		
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}
	/**
	 * 添加
	 */
	@Override
	public int add(ChanLiang cl) {
		String sql = "insert into JiaoCaiChanLiang(LingPiaoZong,YunShuZong,YunShuDang,WeiDao,DaBao,TieBao,JiaoCan,WenJiao,NanChangShi,DaiFa,Tui,HeJi,dd,adddate,addwho,note,issue) values(?,?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,?,?)";
		return this.getJdbcTemplate().update(sql, cl.getLingPiaoZong(),cl.getYunShuZong(),cl.getYunShuDang(),cl.getWeiDao(),cl.getDaBao(),cl.getTieBao(),cl.getJiaoCan(),cl.getWenJiao(),cl.getNanChangShi(),cl.getDaiFa(),cl.getTui(),cl.getHeJi(),cl.getDd(),cl.getAddwho(),cl.getNote(),cl.getIssue());
	}
	/**
	 * 添加
	 */
	@Override
	public int addOther(ChanLiang cl) {
		String sql = "insert into OTHER_YIELD(RECEIPT_TODAY,RECEIPT_SUM,DELIVER_TODAY,DELIVER_SUM,DD,ADDDATE,ADDWHO,NOTE,ISSUE) values(?,?,?,?,?,getdate(),?,?,?)";
		return this.getJdbcTemplate().update(sql, cl.getReceiptToday(), cl.getReceiptSum(), cl.getDeliverToday(), cl.getDeliverSum(), cl.getDd(), cl.getAddwho(),cl.getNote(),cl.getIssue());
	}
	/**
	 * 修改
	 */
	@Override
	public int edit(ChanLiang cl) {
		String sql = "update JiaoCaiChanLiang set LingPiaoZong=?,YunShuZong=?,YunShuDang=?,WeiDao=?,DaBao=?,TieBao=?,JiaoCan=?,WenJiao=?,NanChangShi=?,DaiFa=?,Tui=?,HeJi=?,dd=?,note=?,issue=? where id = ?";
		return this.getJdbcTemplate().update(sql, cl.getLingPiaoZong(),cl.getYunShuZong(),cl.getYunShuDang(),cl.getWeiDao(),cl.getDaBao(),cl.getTieBao(),cl.getJiaoCan(),cl.getWenJiao(),cl.getNanChangShi(),cl.getDaiFa(),cl.getTui(),cl.getHeJi(),cl.getDd(),cl.getNote(),cl.getIssue(),cl.getId());
	}
	/**
	 * 修改
	 */
	@Override
	public int editOther(ChanLiang cl) {
		String sql = "update OTHER_YIELD set RECEIPT_TODAY=?,RECEIPT_SUM=?,DELIVER_TODAY=?,DELIVER_SUM=?,DD=?,NOTE=?,ISSUE=? where id = ?";
		return this.getJdbcTemplate().update(sql, cl.getReceiptToday(),cl.getReceiptSum(),cl.getDeliverToday(), cl.getDeliverSum(), cl.getDd(), cl.getNote(), cl.getIssue(), cl.getId());
	}
	/**
	 * 获取楼下汇总
	 */
	@Override
	public int getLouxia(String issue) {
		String sql = "select SUM(dabao)+SUM(tiebao)+SUM(jiaocan)+SUM(wenjiao)+SUM(nanChangShi) from jiaocaichanliang where issue = ?";
		try {
			return this.getJdbcTemplate().queryForObject(sql, new Object[]{issue}, Integer.class);
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * 获取楼下汇总
	 */
	@Override
	public ChanLiang getOtherSum(String issue) {
		List<ChanLiang> list = null;
		String sql = "select SUM(RECEIPT_TODAY) RECEIPT_SUM ,SUM(DELIVER_TODAY) DELIVER_SUM from OTHER_YIELD where issue = ?";
		try {
			list = this.getJdbcTemplate().query(sql, new Object[]{issue}, new RowMapper<ChanLiang>() {
				@Override
				public ChanLiang mapRow(ResultSet resultSet, int i) throws SQLException {
					ChanLiang c = new ChanLiang();
					c.setReceiptSum(resultSet.getInt("RECEIPT_SUM"));
					c.setDeliverSum(resultSet.getInt("DELIVER_SUM"));
					return c;
				}
			});
		} catch (Exception e) {

		}

		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

}
