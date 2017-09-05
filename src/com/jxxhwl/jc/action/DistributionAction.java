package com.jxxhwl.jc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Inventory;
import com.jxxhwl.jc.service.DistributionService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class DistributionAction extends BaseAction implements ModelDriven<Distribution>{
	private Distribution distribution = new Distribution();

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}
	/**
	 * 模型驱动
	 */
	@Override
	public Distribution getModel() {
		return distribution;
	}
	@Resource
	private DistributionService distributionService;
	public void setDistribuitonService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}
	
	/**
	 * 保存预分发
	 * @return
	 */
	public String predistribution (){
		distribution.setType("0");
		Map<String,Object> map = distributionService.save(distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 加载可预分发数据
	 * @return
	 */
	public String waitPreDistributionData(){
		//当前页 
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
		//每页显示条数
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map = distributionService.getWaitPreDistributionLoadData(pageSize,currentPage,distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 加载已分发数据
	 * @return
	 */
	public String loadDistributedData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
        Map<String,Object> map = distributionService.getDistributionLoadData(pageSize,currentPage,distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 删除预分发
	 * @return
	 */
	public String delete(){
		ok = distributionService.delete(distribution.getId(),"0");
		return OK;
	}
	/**
	 * 删除库存已分发记录
	 * @return
	 */
	public String delete_1(){
		ok = distributionService.delete(distribution.getId(),"1");
		return OK;
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		ok = distributionService.update(distribution);
		return OK;
	}
	/**
	 * 加载库存等待分发信息
	 * @return
	 */
	public String loadWaitDistributionData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map = distributionService.loadWaitDistributionData(pageSize,currentPage,distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 提交库存分发数据
	 * @return
	 */
	public String submitInventoryDistributionData(){
		Map<String,Object> map = distributionService.save(distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 提交修改后的库存分发数据
	 * @return
	 */
	public String submitEditInventoryDistributionData(){
		ok = distributionService.update(distribution);
		return OK;
	}
	/**
	 * 根据期号查询待分发的征订代码
	 * @return
	 */
	public String loadDistributionSubcodeByIssuenumber(){
		List<Inventory> list = distributionService.loadDistributionSubcodeByIssuenumber(distribution);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 根据期号查询已分发的征订代码
	 * @return
	 */
	public String loadDistributedSubcodeByIssuenumber(){
		List<Distribution> list = distributionService.loadDistributedSubcodeByIssuenumber(distribution);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
}
