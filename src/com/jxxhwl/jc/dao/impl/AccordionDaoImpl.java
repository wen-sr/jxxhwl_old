package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.AccordionDao;
import com.jxxhwl.jc.entity.Accordion;

//@Repository("accordionDao")
public class AccordionDaoImpl extends BaseDao implements AccordionDao{
	/**
	 * 查询菜单列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Accordion> list() {
		String sql = "select * from JiaoCaiAccordion where accordion_flag = 1 ";
		List<Accordion> list = new ArrayList<Accordion>();
		list = super.getJdbcTemplate().query(sql, new RowMapper(){
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
