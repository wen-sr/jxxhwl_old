package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.common.entity.User;
import com.jxxhwl.jc.entity.ProductionPlan;

public interface ProductionPlanDao {
	/**
	 * 分页查询所有生产计划数据
	 * @param pageSize
	 * @param currentPage
	 * @param pp
	 * @return
	 */
	public List<ProductionPlan> findAll(int pageSize, int currentPage,ProductionPlan pp);
	/**
	 * 统计总条数
	 * @param pp
	 * @return
	 */
	public int countTotal(ProductionPlan pp);
	/**
	 * 获得所有添加人
	 * @return
	 */
	public List<User> getAddwho();
	/**
	 * 更新
	 * @param productionPlan
	 * @return
	 */
	public int update(ProductionPlan productionPlan);
}
