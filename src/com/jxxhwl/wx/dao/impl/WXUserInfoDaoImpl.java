package com.jxxhwl.wx.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jxxhwl.wx.dao.WXUserInfoDao;
import com.jxxhwl.wx.entity.UserInfo;

@Repository
public class WXUserInfoDaoImpl extends JdbcDaoSupport implements WXUserInfoDao {
	
	@Resource(name="dynamicDataSource")
	public void setMyDataSource(DataSource ds ) {
		super.setDataSource(ds);
	}
	/**
	 * save userinfo
	 */
	@Override
	public void save(UserInfo u) {
		String sql = "insert into userinfo(openid,subscribe,subscribeTime,nickname,sex,country,province,city,language,headImgUrl,remark,groupid) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, u.getOpenId(),u.getSubscribe(),u.getSubscribeTime(),u.getNickname(),u.getSex(),u.getCountry(),u.getProvince(),u.getCity(),u.getLanguage(),u.getHeadImgUrl(),u.getRemark(),u.getGroupid());
	}
	/**
	 * delete by openid
	 */
	@Override
	public void deleteByOpenid(String openId) {
		String sql = "delete from userinfo where openid = ?";
		this.getJdbcTemplate().update(sql, openId);
	}
	/**
	 * find by openid
	 */
	@Override
	public UserInfo findByOpenid(String openid) {
		String sql = "select * from userinfo where openid = ? ";
		try {
			List<UserInfo> list =  this.getJdbcTemplate().query(sql, new Object[]{openid},new RowMapper<UserInfo>(){

				@Override
				public UserInfo mapRow(ResultSet rs, int arg1)
						throws SQLException {
					UserInfo u = new UserInfo();
					u.setOpenId(rs.getString("openId"));
					u.setCity(rs.getString("city"));
					u.setCountry(rs.getString("country"));
					u.setGroupid(rs.getString("groupid"));
					u.setHeadImgUrl(rs.getString("headImgUrl"));
					u.setLanguage(rs.getString("language"));
					u.setNickname(rs.getString("nickname"));
					u.setProvince(rs.getString("province"));
					u.setRemark(rs.getString("remark"));
					u.setSex(rs.getInt("sex"));
					u.setSubscribe(rs.getInt("subscribe"));
					u.setSubscribeTime(rs.getString("subscribeTime"));
					u.setLogin_id(rs.getString("login_id"));
					return u;
				}
			});
			if( null != list && list.size() > 0 ){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 工号绑定
	 */
	@Override
	public UserInfo subscribe(UserInfo userInfo) {
		String sql = "select a.id from login a where a.name=? and a.id=UPPER(?)";
		List<UserInfo> list = getJdbcTemplate().query(sql, new Object[]{userInfo.getNickname(),userInfo.getLogin_id()}, new RowMapper() {

			@Override
			public UserInfo mapRow(ResultSet rs, int arg1) throws SQLException {
				UserInfo u = new UserInfo();
				u.setLogin_id(rs.getString("id"));
				return u;
			}
		});
		if(null != list && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 保存绑定信息
	 */
	@Override
	public int saveSubscribe(UserInfo u) {
		String sql = "update userInfo set login_id = ? where openid=?";
		return getJdbcTemplate().update(sql, u.getLogin_id(),u.getOpenId());
	}
	/**
	 * 获得所有已绑定工号的员工
	 */
	@Override
	public List<String> getAllMyUsers() {
		String sql = "select * from userInfo where login_id is not null ";
		return this.getJdbcTemplate().query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				String s = rs.getString("openid");
				return s;
			}
		});
	}
	/**
	 * 获得所有已绑定工号的主管
	 */
	@Override
	public List<String> getAllSupervisor() {
		String sql = "select * from userInfo a,role_login b,Role c where a.login_id=b.login_id and b.role_id=c.id and a.login_id is not null and (c.name like '%主管' or c.name like '%主任' c.name like '%经理') ";
		return this.getJdbcTemplate().query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				String s = rs.getString("openid");
				return s;
			}
		});
	}
	/**
	 * 根据工号查询
	 */
	@Override
	public List<String> getUsersById(String s) {
		String sql = "select * from userInfo where login_id in ("+ s +")";
		return this.getJdbcTemplate().query(sql, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				String s = rs.getString("openid");
				return s;
			}
		});
	}
}
