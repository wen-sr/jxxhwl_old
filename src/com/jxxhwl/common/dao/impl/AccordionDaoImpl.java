package com.jxxhwl.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.dao.AccordionDao;
import com.jxxhwl.common.entity.Accordion;
import com.jxxhwl.jc.common.BaseDao;

@SuppressWarnings("unchecked")
@Repository
public class AccordionDaoImpl extends BaseDao implements AccordionDao{
	/**
	 * 查询菜单列表
	 */
	@Override
	public List<Accordion> list(String id) {
//		String sql = "select * from Accordion where accordion_flag = 1 order by accordion_group,order_by ";
		//权限控制的sql
		String sql = "select * from Accordion  where accordion_id in (select accordion_id from role_accordion where role_id in (select role_id from role_login where login_id =upper(?))) and accordion_flag = 1 order by accordion_group,order_by";
		List<Accordion> list = new ArrayList<Accordion>();
		list = super.getJdbcTemplate().query(sql,new Object[]{id}, new RowMapper(){
			Accordion a = null;
			@Override
			public Accordion mapRow(ResultSet rs, int i) throws SQLException {
				a = new Accordion();
				a.setAccordion_id(rs.getLong("accordion_id"));
				a.setAccordion_name(rs.getString("accordion_name"));
				a.setAccordion_flag(rs.getString("accordion_flag"));
				a.setAccordion_group(rs.getString("accordion_group"));
				a.setAccordion_icon(rs.getString("accordion_icon"));
				a.setAccordion_url(rs.getString("accordion_url"));
				a.setFoo_id(rs.getLong("foo_id"));
				a.setOrder_by(rs.getString("order_by"));
				return a;
			}
			
		});
		return list;
	}

}
