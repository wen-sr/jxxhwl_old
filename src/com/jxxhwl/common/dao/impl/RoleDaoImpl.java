package com.jxxhwl.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.BaseDao;
import com.jxxhwl.common.dao.RoleDao;
import com.jxxhwl.common.entity.AuthorityMid;
import com.jxxhwl.common.entity.Role;

@Repository
@SuppressWarnings("unchecked")
public class RoleDaoImpl extends BaseDao implements RoleDao {

	/**
	 * 查询所有部门信息
	 */
	@Override
	public List<Role> findAll() {
		String sql = "select * from role";
		return getJdbcTemplate().query(sql, new RowMapper() {

			@Override
			public Role mapRow(ResultSet rs, int arg1) throws SQLException {
				Role d = new Role();
				d.setId(rs.getLong("id"));
				d.setName(rs.getString("name"));
				return d;
			}
		});
	}
	/**
	 * 保存
	 */
	@Override
	public int save(Role role) {
		String sql = "insert into role(name) values(?)";
		return getJdbcTemplate().update(sql, role.getName());
	}
	/**
	 * 修改
	 */
	@Override
	public int update(Role role) {
		String sql = "update role set name=? where id = ? ";
		return getJdbcTemplate().update(sql,role.getName(),role.getId());
	}
	/**
	 * 删除
	 */
	@Override
	public int delete(Role role) {
		String sql = "delete from role where id = ? ";
		return getJdbcTemplate().update(sql,role.getId());
	}
	/**
	 * 根据角色id获取权限权限列表
	 */
	@Override
	public List<AuthorityMid> roleDao(long id) {
		String sql = "select a.accordion_id,a.accordion_name,a.foo_id,b.role_id  from accordion a left join (select * from role_accordion a,Role b where a.role_id=b.id and b.id = ? ) b on a.accordion_id = b.accordion_id where accordion_flag = 1 ";
		List<AuthorityMid> list= getJdbcTemplate().query(sql, new Object[]{id}, new RowMapper<AuthorityMid>() {

			@Override
			public AuthorityMid mapRow(ResultSet rs, int arg1)
					throws SQLException {
				AuthorityMid a = new AuthorityMid();
				a.setAccordion_id(rs.getInt("accordion_id"));
				a.setAccordion_name(rs.getString("accordion_name"));
				a.setFoo_id(rs.getInt("foo_id"));
				a.setRole_id(rs.getInt("role_id"));
				return a;
			}
		});
		
		return list;
	}
	/**
	 * 删除原来的权限
	 */
	@Override
	public int deleteAuth(long id) {
		String sql = "delete from role_accordion where role_id = ?";
		return this.getJdbcTemplate().update(sql, id);
	}

	/**
	 * 保存权限
	 */
	@Override
	public int saveAuth(long id, String s) {
		String sql = "insert into role_accordion(role_id,accordion_id) values(?,?)";
		return this.getJdbcTemplate().update(sql, id,s);
	}
	/**
	 * 删除原来的的角色
	 */
	@Override
	public void deleteRole(String login_id) {
		String sql = "delete from role_login where login_id = ?";
		this.getJdbcTemplate().update(sql, login_id);
	}
	/**
	 * 保存角色
	 */
	@Override
	public int saveRole(String login_id, String role_id) {
		String sql = "insert into role_login(login_id,role_id) values(?,?)";
		return this.getJdbcTemplate().update(sql, login_id,role_id);
	}
	/**
	 * 查询父节点
	 */
	@Override
	public Set<String> findFather(String str) {
		String sql = "select distinct foo_id from accordion where foo_id <> 0 and accordion_id in (" + str + ")";
		Set<String> set = new HashSet<String>();
		
		List<String> list =  this.getJdbcTemplate().query(sql, new RowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
			
		});
		for(String s : list) {
			set.add(s);
		}
		return set;
	}
}
