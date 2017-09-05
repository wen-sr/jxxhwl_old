package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.common.entity.User;
import com.jxxhwl.jc.dao.ProductionPlanDao;
import com.jxxhwl.jc.entity.ProductionPlan;
import com.jxxhwl.jc.service.ProductionPlanService;

@Service
@Transactional
public class ProductionPlanServiceImpl implements ProductionPlanService {

	@Autowired
	ProductionPlanDao poductionPlanDao;
	/**
	 * 分页查询所有生产计划数据
	 */
	@Override
	public Map<String,Object> findAll(int pageSize, int currentPage,
			ProductionPlan pp) {
		List<ProductionPlan> list = poductionPlanDao.findAll(pageSize, currentPage, pp);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", poductionPlanDao.countTotal(pp));
		return map;
	}
	/**
	 * 获得所有添加人
	 */
	public List<User> getAddwho() {
		List<User> list = poductionPlanDao.getAddwho();
		return list;
	}
	/**
	 * 更新
	 */
	@Override
	public String update(ProductionPlan productionPlan) {
		int i = poductionPlanDao.update(productionPlan);
		if(i > 0 ){
			return "数据更新成功";
		}
		return "数据更新失败";
	}
}
