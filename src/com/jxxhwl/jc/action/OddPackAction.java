package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Storer;
import com.jxxhwl.jc.service.OddPackService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class OddPackAction extends BaseAction implements ModelDriven<Distribution>{
	Distribution distribution = new Distribution();
	@Override
	public Distribution getModel() {
		return distribution;
	}
	@Resource
	private OddPackService oddPackService;
	/**
	 * 加载需要打包的客户
	 * @return
	 */
	public String loadCustomer(){
		List<Storer> list = oddPackService.loadCustomer();
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 加载未刷信息
	 * @return
	 */
	public String loadUnpackData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  oddPackService.loadPackData(pageSize, currentPage,distribution.getIssuenumber(),distribution.getCode(),"2");
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 加载已刷信息
	 * @return
	 */
	public String loadPackedData(){
		//当前页  
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
		//每页显示条数  
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map =  oddPackService.loadPackData(pageSize, currentPage,distribution.getIssuenumber(),distribution.getCode(),"3");
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 扫条码查询打包信息
	 * @return
	 */
	public String findByBarcode(){
		List<Distribution> list = oddPackService.findByBarcode(distribution.getIssuenumber(),distribution.getCode(),distribution.getBarcode());
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 确认打包
	 * @return
	 */
	public String confirmPack(){
		ok = oddPackService.confirmPack(distribution.getId(),distribution.getPackwho());
		return OK;
	}
	/**
	 * 取消打包
	 * @return
	 */
	public String cancelPack(){
		String[] ids = distribution.getId().split(",");
		ok = oddPackService.cancelPack(ids);
		return OK;
	}
	/**
	 * 添加批次
	 * @return
	 */
	public String addBatchno(){
		String[] ids = distribution.getId().split(",");
		Map<String,Object> map =  oddPackService.addBatchno(ids);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
}
