package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.common.entity.User;
import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.ProductionPlan;
import com.jxxhwl.jc.service.ProductionPlanService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ProductionPlanAction extends BaseAction implements ModelDriven<ProductionPlan>{
	ProductionPlan productionPlan = new ProductionPlan();
	@Override
	public ProductionPlan getModel() {
		return productionPlan;
	}
	@Autowired
	ProductionPlanService productionPlanService;
	/**
	 * 分页查询所有生产计划数据
	 * @return
	 */
	public String findAll(){
		int currentPage,pageSize;
		if(switchButton){
			//当前页  
			currentPage = 1;
			//每页显示条数  
			pageSize = 999999999;
		}else{
			//当前页  
			currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);
			//每页显示条数  
			pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		}
        Map<String,Object> map = productionPlanService.findAll(pageSize, currentPage, productionPlan);
        ActionContext.getContext().getValueStack().set("result", map);
        return SUCCESS;
	}
	/**
	 * 获得所有添加人
	 * @return
	 */
	public String getAddwho(){
		List<User> list = productionPlanService.getAddwho();
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 更新数据
	 * @return
	 */
	public String update(){
		ok = productionPlanService.update(productionPlan);
		return OK;
	}
}
