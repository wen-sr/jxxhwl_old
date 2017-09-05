package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.common.entity.User;
import com.jxxhwl.jc.entity.ProductionPlan;

public interface ProductionPlanService {
	/**
	 * 分页查询
	 * @param pageSize
	 * @param currentPage
	 * @param pp
	 * @return
	 */
	public Map<String,Object> findAll(int pageSize, int currentPage,ProductionPlan pp);
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
	public String update(ProductionPlan productionPlan);
}
