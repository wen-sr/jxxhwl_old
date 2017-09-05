package com.jxxhwl.jc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.PackDao;
import com.jxxhwl.jc.entity.Pack;
import com.mysql.jdbc.Statement;

@Repository("packDao")
public class PackDaoImpl extends BaseDao implements PackDao {
	
	/**
	 * pack的映射类
	 * @author liujie
	 *
	 */
	private class PackRowMapper implements RowMapper{

		@Override
		public Pack mapRow(ResultSet rs, int i) throws SQLException {
			Pack pack = new Pack();
			pack.setId(rs.getString("id"));
			pack.setIssuenumber(rs.getString("issuenumber"));
			pack.setSubcode(rs.getString("subcode"));
			pack.setPack(rs.getInt("pack"));
			return pack;
		}
		
	}

	/**
	 * 保存捆扎信息
	 */
	@Override
	public Pack save(final Pack pack) {
		final String sql = "insert into JiaoCaiPack(issuenumber,subcode,pack) values(?,?,?)";
		//利用这个对象将id带出来
		KeyHolder keyHolder=new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,pack.getIssuenumber());
                ps.setString(2, pack.getSubcode());
                ps.setInt(3, pack.getPack());
                return ps;
            }
        },keyHolder);
		//获取id
        pack.setId(keyHolder.getKey().toString());
        
        return pack;
	}

	/**
	 * 根据期号和征订代码查询捆扎信息
	 */
	@Override
	public Pack findPackByissuenumberAndSubcode(String issuenumber,
			String subcode,int pack) {
		String sql = "select * from JiaoCaiPack where issuenumber = ? and subcode = ? and pack = ?";
		Pack p = null;
		try {
			List<Pack> list = getJdbcTemplate().query(sql, new Object[]{issuenumber,subcode,pack}, new PackRowMapper());
			if(list.size() > 0){
				p = list.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return p;
	}
	/**
	 * 查询
	 */
	@Override
	public List<Pack> query(Pack pack) {
		String sql = "select * from JiaoCaiPack ";
		int len = sql.length();
		List<Pack> list = null;
		if(pack.getIssuenumber() != null && !"".equals(pack.getIssuenumber())){
			if(sql.length() == len){
				sql += " where issuenumber='" + pack.getIssuenumber() + "'"; 
			}else {
				sql += " and issuenumber = '" + pack.getIssuenumber() + "'";
			}
		}
		if(pack.getSubcode() != null && !"".equals(pack.getSubcode())){
			if(sql.length() == len){
				sql += " where subcode='" + pack.getSubcode() + "'"; 
			}else {
				sql += " and subcode = '" + pack.getSubcode() + "'";
			}
		}
		try {
			list = getJdbcTemplate().query(sql, new PackRowMapper());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 删除
	 */
	@Override
	public int delete(Pack pack) {
		String sql = "delete from JiaoCaiPack where issuenumber = ? and subcode = ? and pack = ?";
		int i = 0;
		try {
			i = getJdbcTemplate().update(sql,new Object[]{pack.getIssuenumber(),pack.getSubcode(),pack.getPack()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return i;
	}
	/**
	 * 查询当前捆扎是否是否有库存
	 */
	@Override
	public List<Pack> queryInventory(Pack pack) {
		List<Pack> list = null;
		String sql = "select * from JiaoCaiInventory_detail where issuenumber = ? and subcode = ? and pack = ? and qtyfree > 0"; 
		try {
			list = getJdbcTemplate().query(sql, new Object[]{pack.getIssuenumber(),pack.getSubcode(),pack.getPack()}, new RowMapper() {
				
				@Override
				public Pack mapRow(ResultSet rs, int arg1) throws SQLException {
					Pack p = new Pack();
					p.setIssuenumber(rs.getString("issuenumber"));
					p.setSubcode(rs.getString("subcode"));
					p.setPack(rs.getInt("pack"));
					return p;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 * 更新库存的捆扎
	 */
	@Override
	public int updatePackInventory(Pack pack) {
		String sql = "update JiaoCaiInventory_detail set pack=? where issuenumber = ? and subcode = ? ";
		int i = getJdbcTemplate().update(sql,new Object[]{pack.getPack(),pack.getIssuenumber(),pack.getSubcode()});
		return i;
	}
	/**
	 * 更新捆扎
	 */
	@Override
	public int updatePack(Pack pack) {
		String sql = "update JiaoCaiPack set pack= ? where id = ? ";
		return getJdbcTemplate().update(sql, pack.getPack(),pack.getId());
	}
	/**
	 * 更新sku的pack
	 */
	@Override
	public int updateSku(Pack pack) {
		String sql = "update JiaoCaiSku set pack= ? where issuenumber = ? and subcode = ? ";
		return getJdbcTemplate().update(sql, pack.getPack(),pack.getIssuenumber(),pack.getSubcode());
	}
}
