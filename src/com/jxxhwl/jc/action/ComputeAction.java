package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.service.ComputeService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ComputeAction extends BaseAction implements ModelDriven<Distribution>{

	Distribution distribution = new Distribution();
	@Override
	public Distribution getModel() {
		return distribution;
	}
	@Resource
	private ComputeService computeService;
	public void setComputeService(ComputeService computeService) {
		this.computeService = computeService;
	}
	/**
	 * 需配发计算的数据
	 * @return
	 */
	public String loadWaitComputeData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  computeService.findNeedCompute(pageSize, currentPage, distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 修改包册数
	 * @return
	 */
	public String commitpack(){
		ok = computeService.editPack(distribution);
		return OK;
	}
	/**
	 * 计算
	 * @return
	 */
	public String compute(){
		String[] ids = distribution.getId().split(",");
		ok = computeService.compute(ids,distribution.getPack(),distribution.getAddwho());
		return OK;
	}
	/**
	 * 加载已配发的数据
	 * @return
	 */
	public String loadComputedData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  computeService.findComputedData(pageSize, currentPage,distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 取消配发计算
	 * @return
	 */
	public String cancelCompute(){
		ok = computeService.cancelCompute(distribution.getId());
		return OK;
	}
	/**
	 * 根据期号加载需计算的征订代码
	 * @return
	 */
	public String loadComputeSubcodeByIssuenumber(){
		List<Distribution> list = computeService.loadComputeSubcodeByIssuenumber(distribution.getIssuenumber());
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 根据征订代码和期号查询需配发的数据
	 * @return
	 */
	public String loadDataDetail(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  computeService.findNeedComputeBySubcode(pageSize, currentPage,distribution.getIssuenumber(),distribution.getSubcode());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	
	
	/**
	 * 根据期号加载已计算的征订代码
	 * @return
	 */
	public String loadComputedSubcodeByIssuenumber(){
		List<Distribution> list = computeService.loadComputedSubcodeByIssuenumber(distribution);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 根据征订代码和期号查询已配发的数据
	 * @return
	 */
	public String loadComputedDataBySubcode(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  computeService.loadComputedDataBySubcode(pageSize, currentPage,distribution.getIssuenumber(),distribution.getSubcode());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 分书拆分
	 * @return
	 */
	public String split(){
		ok = computeService.split(distribution);
		return OK;
	}

	/**
	 * 所有品种
	 * @return
	 */
	public String loadAll(){
		Map<String,Object> map =  computeService.findAll(distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
}
