package com.jxxhwl.jc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.entity.User;
import com.jxxhwl.jc.common.BaseDao;
import com.jxxhwl.jc.dao.ProductionPlanDao;
import com.jxxhwl.jc.entity.ProductionPlan;

@Repository
public class ProductionPlanDaoImpl extends BaseDao implements ProductionPlanDao {

	private class ProductionPlanRowMapper implements RowMapper<ProductionPlan>{

		@Override
		public ProductionPlan mapRow(ResultSet rs, int arg1) throws SQLException {
			ProductionPlan pp = new ProductionPlan();
			pp.setAdddate(rs.getString("adddate"));
			pp.setAddwho(rs.getString("addwho"));
			pp.setBatchno(rs.getString("batchno"));
			pp.setDeliverDate(rs.getString("deliverDate"));
			pp.setDescr(rs.getString("descr"));
			pp.setHandBagDate(rs.getString("handBagDate"));
			pp.setId(rs.getString("id"));
			pp.setPlantingName(rs.getString("plantingName"));
			pp.setPrice(rs.getDouble("price"));
			pp.setPublisher(rs.getString("publisher"));
			pp.setQtyCase(rs.getInt("qtyCase"));
			pp.setQtyDeliver(rs.getInt("qtyDeliver"));
			pp.setQtyPerItem(rs.getInt("qtyPerItem"));
			pp.setRemark(rs.getString("remark"));
			pp.setStatus(rs.getString("status"));
			pp.setSubcode(rs.getString("subcode"));
			pp.setType(rs.getString("type"));
			return pp;
		}
		
	}
	
	/**
	 * 分页查询所有生产计划数据
	 */
	@Override
	public List<ProductionPlan> findAll(int pageSize, int currentPage,
			ProductionPlan pp) {
		String sql = "select top "+ pageSize +" * from JiaoCaiPlanData ";
		int len = sql.length();
		if(!"".equals(pp.getPlantingName()) && pp.getPlantingName() != null ){
			if(sql.length() == len){
				sql += " where plantingName like '%" + pp.getPlantingName() + "%'";
			}else{
				sql += " and plantingName like '%" + pp.getPlantingName() + "%'";
			}
		}
		if(!"".equals(pp.getSubcode()) && pp.getSubcode() != null ){
			if(sql.length() == len){
				sql += " where subcode like '%" + pp.getSubcode() + "%'";
			}else{
				sql += " and subcode like '%" + pp.getSubcode() + "%'";
			}
		}
		if(!"".equals(pp.getPublisher()) && pp.getPublisher() != null ){
			if(sql.length() == len){
				sql += " where publisher like '%" + pp.getPublisher() + "%'";
			}else{
				sql += " and publisher like '%" + pp.getPublisher() + "%'";
			}
		}
		if(!"".equals(pp.getBatchno()) && pp.getBatchno() != null ){
			if(sql.length() == len){
				sql += " where batchno like '%" + pp.getBatchno() + "%'";
			}else{
				sql += " and batchno like '%" + pp.getBatchno() + "%'";
			}
		}
		if(!"".equals(pp.getDeliverDate()) && pp.getDeliverDate() != null ){
			if(!"".equals(pp.getDeliverDateEnd()) && pp.getDeliverDateEnd() != null){
				if(sql.length() == len){
					sql += " where deliverDate >= '" + pp.getDeliverDate() + "' and deliverDate <= '" + pp.getDeliverDateEnd() + "'";
				}else{
					sql += " and deliverDate >= '" + pp.getDeliverDate() + "' and deliverDate <= '" + pp.getDeliverDateEnd() + "'";
				}
			}else{
				if(sql.length() == len){
					sql += " where deliverDate = '" + pp.getDeliverDate() + "'";
				}else{
					sql += " and deliverDate = '" + pp.getDeliverDate() + "'";
				}
			}
			
		}
		if(!"".equals(pp.getHandBagDate()) && pp.getHandBagDate() != null ){
			if(!"".equals(pp.getHandBagDateEnd()) && pp.getHandBagDateEnd() != null){
				if(sql.length() == len){
					sql += " where handBagDate >= '" + pp.getHandBagDate() + "' and handBagDate <= '" + pp.getHandBagDateEnd() + "'";
				}else{
					sql += " and handBagDate >= '" + pp.getHandBagDate() + "' and handBagDate <= '" + pp.getHandBagDateEnd() + "'";
				}
			}else{
				if(sql.length() == len){
					sql += " where handBagDate = '" + pp.getHandBagDate() + "'";
				}else{
					sql += " and handBagDate = '" + pp.getHandBagDate() + "'";
				}
			}
			
		}
		if(!"".equals(pp.getType()) && pp.getType() != null ){
			if(sql.length() == len){
				sql += " where type = '" + pp.getType() + "'";
			}else{
				sql += " and type = '" + pp.getType() + "'";
			}
		}
		if(!"".equals(pp.getStatus()) && pp.getStatus() != null ){
			if(sql.length() == len){
				sql += " where status = '" + pp.getStatus() + "'";
			}else{
				sql += " and status = '" + pp.getStatus() + "'";
			}
		}
		if(!"".equals(pp.getRemark()) && pp.getRemark() != null ){
			if(sql.length() == len){
				sql += " where remark = '" + pp.getRemark() + "'";
			}else{
				sql += " and remark = '" + pp.getRemark() + "'";
			}
		}
		
		String sss = sql.substring(len);
		if(sss.length() == 0) {
			sql += " where ";
		}else {
			sql += " and ";
		}
		sql += " id not in (select top "+ (currentPage-1)*pageSize +" id from JiaoCaiPlanData "+ sss +" order by deliverDate desc ) order by deliverDate desc ";
		
		return this.getJdbcTemplate().query(sql, new ProductionPlanRowMapper());
		
	}
	/**
	 * 统计总条数
	 */
	@Override
	public int countTotal(ProductionPlan pp) {
		String sql = "select count(*) from JiaoCaiPlanData ";
		int len = sql.length();
		if(!"".equals(pp.getPlantingName()) && pp.getPlantingName() != null ){
			if(sql.length() == len){
				sql += " where plantingName like '%" + pp.getPlantingName() + "%'";
			}else{
				sql += " and plantingName like '%" + pp.getPlantingName() + "%'";
			}
		}
		if(!"".equals(pp.getSubcode()) && pp.getSubcode() != null ){
			if(sql.length() == len){
				sql += " where subcode like '%" + pp.getSubcode() + "%'";
			}else{
				sql += " and subcode like '%" + pp.getSubcode() + "%'";
			}
		}
		if(!"".equals(pp.getPublisher()) && pp.getPublisher() != null ){
			if(sql.length() == len){
				sql += " where publisher like '%" + pp.getPublisher() + "%'";
			}else{
				sql += " and publisher like '%" + pp.getPublisher() + "%'";
			}
		}
		if(!"".equals(pp.getBatchno()) && pp.getBatchno() != null ){
			if(sql.length() == len){
				sql += " where batchno like '%" + pp.getBatchno() + "%'";
			}else{
				sql += " and batchno like '%" + pp.getBatchno() + "%'";
			}
		}
		if(!"".equals(pp.getDeliverDate()) && pp.getDeliverDate() != null ){
			if(!"".equals(pp.getDeliverDateEnd()) && pp.getDeliverDateEnd() != null){
				if(sql.length() == len){
					sql += " where deliverDate >= '" + pp.getDeliverDate() + "' and deliverDate <= '" + pp.getDeliverDateEnd() + "'";
				}else{
					sql += " and deliverDate >= '" + pp.getDeliverDate() + "' and deliverDate <= '" + pp.getDeliverDateEnd() + "'";
				}
			}else{
				if(sql.length() == len){
					sql += " where deliverDate = '" + pp.getDeliverDate() + "'";
				}else{
					sql += " and deliverDate = '" + pp.getDeliverDate() + "'";
				}
			}
			
		}
		if(!"".equals(pp.getHandBagDate()) && pp.getHandBagDate() != null ){
			if(!"".equals(pp.getHandBagDateEnd()) && pp.getHandBagDateEnd() != null){
				if(sql.length() == len){
					sql += " where handBagDate >= '" + pp.getHandBagDate() + "' and handBagDate <= '" + pp.getHandBagDateEnd() + "'";
				}else{
					sql += " and handBagDate >= '" + pp.getHandBagDate() + "' and handBagDate <= '" + pp.getHandBagDateEnd() + "'";
				}
			}else{
				if(sql.length() == len){
					sql += " where handBagDate = '" + pp.getHandBagDate() + "'";
				}else{
					sql += " and handBagDate = '" + pp.getHandBagDate() + "'";
				}
			}
			
		}
		if(!"".equals(pp.getType()) && pp.getType() != null ){
			if(sql.length() == len){
				sql += " where type = '" + pp.getType() + "'";
			}else{
				sql += " and type = '" + pp.getType() + "'";
			}
		}
		if(!"".equals(pp.getStatus()) && pp.getStatus() != null ){
			if(sql.length() == len){
				sql += " where status = '" + pp.getStatus() + "'";
			}else{
				sql += " and status = '" + pp.getStatus() + "'";
			}
		}
		if(!"".equals(pp.getRemark()) && pp.getRemark() != null ){
			if(sql.length() == len){
				sql += " where remark = '" + pp.getRemark() + "'";
			}else{
				sql += " and remark = '" + pp.getRemark() + "'";
			}
		}
		if(!"".equals(pp.getAddwho()) && pp.getAddwho() != null ){
			if(sql.length() == len){
				sql += " where addwho = '" + pp.getAddwho() + "'";
			}else{
				sql += " and addwho = '" + pp.getAddwho() + "'";
			}
		}
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
		
	}
	/**
	 * 获得所有添加人
	 */
	@Override
	public List<User> getAddwho() {
		String sql = "select distinct a.addwho,b.name from JiaoCaiPlanData a,login b where a.addwho = b.id";
		
		return this.getJdbcTemplate().query(sql, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User u = new User();
				u.setId(rs.getString("addwho"));
				u.setUsername(rs.getString("name"));
				return u;
			}
		});
	}
	/**
	 * 更新
	 */
	@Override
	public int update(ProductionPlan p) {
		String sql = "update JiaoCaiPlanData set handBagDate=?,type=?,status=1,remark=?,addwho=?,adddate=getdate() where id = ?";
		return this.getJdbcTemplate().update(sql, p.getHandBagDate(),p.getType(),p.getRemark(),p.getAddwho(),p.getId());
	}
}
