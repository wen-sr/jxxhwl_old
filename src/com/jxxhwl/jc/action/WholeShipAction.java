package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.service.WholeShipService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class WholeShipAction extends BaseAction implements ModelDriven<Distribution>{
	Distribution distribution = new Distribution();
	@Override
	public Distribution getModel() {
		return distribution;
	}
	@Resource
	private WholeShipService wholeShipService;
	public void setWholeShipService(WholeShipService wholeShipService) {
		this.wholeShipService = wholeShipService;
	}
	/**
	 * 加载需整件发货数据
	 * @return
	 */
	public String loadWaitShipData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  wholeShipService.loadWaitShipData(pageSize, currentPage,distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 生成批次
	 * @return
	 */
	public String addBatchno(){
		String[] ids = distribution.getId().split(",");
		Map<String,Object> map = wholeShipService.addBatchno(ids,distribution.getAddwho());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 加载已发信息
	 * @return
	 */
	public String loadShippedData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  wholeShipService.loadShippedData(pageSize, currentPage);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 根据期号查询需发品种的征订代码
	 * @return
	 */
	public String loadWaitShipDataTotal(){
		List<Distribution> list = wholeShipService.loadWaitShipDataTotal(distribution);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 根据期号查询已发品种的征订代码
	 * @return
	 */
	public String loadshippedSubcodeByIssuenumber(){
		List<Distribution> list = wholeShipService.loadshippedSubcodeByIssuenumber(distribution.getIssuenumber());
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 根据期号和正定代码查询需发记录
	 * @return
	 */
	public String loadWaitShipDataBySubcode(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  wholeShipService.loadWaitShipDataBySubcode(pageSize, currentPage,distribution.getIssuenumber(),distribution.getSubcode());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 根据期号和正定代码查询已发记录
	 * @return
	 */
	public String loadShippedDataBySubcode(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  wholeShipService.loadShippedDataBySubcode(pageSize, currentPage,distribution);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 取消批次
	 * @return
	 */
	public String removeBatchno(){
		ok = wholeShipService.removeBatchno(distribution.getId());
		return OK;
	}
}
