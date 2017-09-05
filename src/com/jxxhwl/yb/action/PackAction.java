package com.jxxhwl.yb.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.yb.common.BaseAction;
import com.jxxhwl.yb.entity.Pack;
import com.jxxhwl.yb.service.PackService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("packAction_yb")
@Scope("prototype")
public class PackAction extends BaseAction implements ModelDriven<Pack>{
	Pack pack = new Pack();
	@Override
	public Pack getModel() {
		return pack;
	}
	@Resource(name="packService_yb") PackService packService;
	/**
	 * 输入代码，查询客户信息
	 * @return
	 */
	public String findByCode(){
		List<Pack> list = packService.findByCode(pack.getCode());
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 加载未打包数据
	 * @return
	 */
	public String loadUnpackData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
        Map<String,Object> map = packService.loadUnpackData(currentPage,pageSize,pack);
        ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 加载已打包数据未封箱数据
	 * @return
	 */
	public String loadPackedData(){
		//当前页  
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
		//每页显示条数  
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map = packService.loadPackedData(currentPage,pageSize,pack);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 根据条码和客户查询信息
	 * @return
	 */
	public String findByBarcode(){
		List<Pack> list = packService.findByBarcode(pack);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 打包确认
	 * @return
	 */
	public String confirmPack(){
		ok = packService.confirmPack(pack);
		return OK;
	}
	/**
	 * 取消打包
	 * @return
	 */
	public String cancelPack(){
		ok = packService.cancelPack(pack);
		return OK;
	}
	/**
	 * 获得批次号
	 * @return
	 */
	public String getBatchno(){
		ok = packService.getBatchno();
		return OK;
	}
	
	/**
	 * 封包
	 * @return
	 */
	public String addCaseid(){
		Map<String,Object> map = packService.addCaseid(pack);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
}
