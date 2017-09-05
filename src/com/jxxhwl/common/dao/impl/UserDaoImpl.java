package com.jxxhwl.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.BaseDao;
import com.jxxhwl.common.dao.UserDao;
import com.jxxhwl.common.entity.Authority;
import com.jxxhwl.common.entity.User;

@SuppressWarnings("unchecked")
@Repository
public class UserDaoImpl extends BaseDao implements UserDao{
	
	/**
	 * 登录
	 */
	@SuppressWarnings("finally")
	@Override
	public User getUser(String id, String password){
		String sql = "select name,id,pwd,postid,py from login where id = ? and pwd = ?";
		
		User user = null;
		try {
			user = ((User) getJdbcTemplate().queryForObject(sql, new Object[]{id,password}, new RowMapper(){

				@Override
				public User mapRow(ResultSet rs, int i) throws SQLException {
					User user = new User();
					user.setId(rs.getString("id"));
					user.setUsername(rs.getString("name"));
					user.setPassword(rs.getString("pwd"));
					user.setDepartId(rs.getString("postid"));
					user.setPy(rs.getString("py"));
					return user;
				}
			}));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} finally{
			return user;
		}
	}
	/**
	 * 获得所有部门信息(废)
	 */

	@Override
	public List<User> getAllDepartments() {
		String sql = "select distinct post from login ";
		List<User> list = getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User u = new User();
				u.setDepartId(rs.getString("post"));
				return u;
			}
		});
		return list;
	}
	/**
	 * 获得用户信息
	 */
	@Override
	public List<User> getUser(int currentPage, int pageSize, User user) {
		String sql = "select top " + pageSize + " * from login where status in(0,1) ";
		String sqladd = "";
		if(user.getId() != null && !"".equals(user.getId())){
				sql += " and id like '%" + user.getId() + "%'";
				sqladd += " and id like '%" + user.getId() + "%'";
		}
		if(user.getUsername() != null && !"".equals(user.getUsername())){
				sql += " and name like '%" + user.getUsername() + "%'";
				sqladd += " and name like '%" + user.getUsername() + "%'";
		}
		if(user.getDepartId() != null && !"".equals(user.getDepartId())){
				sql += " and postid = '" + user.getDepartId() + "'";
				sqladd += " and postid = '" + user.getDepartId() + "'";
		}
		if(user.getStatus() != null && !"".equals(user.getStatus())){
			sql += " and status = '" + user.getStatus() + "'";
			sqladd += " and status = '" + user.getStatus() + "'";
		}
		sql += " and id not in (select top "+ (currentPage-1)*pageSize +" id from login where status in(0,1) " +
				sqladd +
				")";
		List<User> list = getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("pwd"));
				user.setDepartId(rs.getString("postid"));
				user.setDepartname(rs.getString("post"));
				user.setPy(rs.getString("py"));
				String s = rs.getString("status");
				if("0".equals(s)){
					user.setStatus("未激活");
				}else{
					user.setStatus("已激活");
				}
				return user;
			}
		});
		return list;
	}
	/**
	 *  统计总条数
	 */
	@Override
	public int count(User user) {
		String sql = "select count(*) from login where status in(0, 1) ";
		if(user.getId() != null && !"".equals(user.getId())){
				sql += " and id like '%" + user.getId() + "%'";
		}
		if(user.getUsername() != null && !"".equals(user.getUsername())){
				sql += " and name like '%" + user.getUsername() + "%'";
		}
		if(user.getDepartId() != null && !"".equals(user.getDepartId())){
				sql += " and post = '" + user.getDepartId() + "'";
		}
		if(user.getStatus() != null && !"".equals(user.getStatus())){
			sql += " and status = '" + user.getStatus() + "'";
		}
		int i = 0;
		try {
			i = getJdbcTemplate().queryForObject(sql, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}
		return i;
	}
	/**
	 * 添加员工
	 */
	@Override
	public int save(User user) {
		String sql = "insert into login(id,name,post,pwd,py,pyname,status,postid) values (?,?,?,'123456',?,?,1,?)";
		
		try {
			return getJdbcTemplate().update(sql, user.getId(),user.getUsername(),user.getDepartname(),user.getPy().toUpperCase(),user.getPy().toUpperCase()+user.getUsername(),user.getDepartId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 修改
	 */
	@Override
	public int edit(User user) {
		String sql = "update login set name=?,post=?,py=?,pyname=?,status=?,postid=? where id=?";
		return getJdbcTemplate().update(sql, user.getUsername(),user.getDepartname(),user.getPy(),user.getPy()+user.getUsername(),user.getStatus(),user.getDepartId(),user.getId());
	}
	/**
	 * 删除
	 */
	@Override
	public int delete(User user) {
		String sql = "delete from login where id = ? ";
		return getJdbcTemplate().update(sql, user.getId());
	}
	/**
	 * 查看权限
	 */
	@Override
	public List<String> getAuth(String id) {
		String sql = "select * from role_login a,Role b where a.role_id = b.id and login_id = ?";
		List<String> list = getJdbcTemplate().query(sql, new Object[]{id}, new RowMapper() {

			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				String s = rs.getString("name");
				return s;
			}
		});
		if(null != list && list.size() > 0) {
			return list;
		}
		return null;
	}
	/**
	 *  获得用户列表树
	 */
	@Override
	public List<User> getUserTree(String departmentId) {
		String sql = "select a.id,a.name,a.postid,b.name departname from Login a,department b where a.postid=b.id";
		if(!"".equals(departmentId) && null != departmentId){
			sql += " and a.postid='" + departmentId + "'";
		}
		return this.getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User u = new User();
				u.setId(rs.getString("id"));
				u.setUsername(rs.getString("name"));
				u.setDepartId(rs.getString("postid"));
				u.setDepartname(rs.getString("departname"));
				return u;
			}
		});
	}
	/**
	 * 获得微信列表树
	 */
	@Override
	public List<User> getWxUserTree(String departmentId) {
		String sql = "select a.id,a.name,a.postid,b.name departname from Login a,department b, userInfo c where a.id = c.login_id and a.postid=b.id";
		if(!"".equals(departmentId) && null != departmentId){
			sql += " and a.postid='" + departmentId + "'";
		}
		return this.getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User u = new User();
				u.setId(rs.getString("id"));
				u.setUsername(rs.getString("name"));
				u.setDepartId(rs.getString("postid"));
				u.setDepartname(rs.getString("departname"));
				return u;
			}
		});
	}
	/**
	 * 修改密码
	 */
	@Override
	public int updatePWD(User user) {
		String sql = "update login set pwd = ? where id = ?";
		return this.getJdbcTemplate().update(sql, user.getPassword(),user.getId());
	}
}
