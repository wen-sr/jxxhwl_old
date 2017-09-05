package com.jxxhwl.jc.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.ChanLiang;
import com.jxxhwl.jc.service.ChanLiangService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ChanLiangAction extends BaseAction implements ModelDriven<com.jxxhwl.jc.entity.ChanLiang>{
	
	ChanLiang chanLiang = new ChanLiang();
	@Override
	public ChanLiang getModel() {
		return chanLiang;
	}
	
	@Autowired ChanLiangService chanLiangService;
	/**
	 * 加载已登记信息
	 * @return
	 */
	public String loadData(){
		//当前页 
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
		//每页显示条数
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String, Object> map = chanLiangService.loadData(currentPage,pageSize,chanLiang.getDd());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 保存
	 * @return
	 */
	public String add(){
		ok = chanLiangService.add(chanLiang);
		return OK;
	}
	/**
	 * 修改
	 * @return
	 */
	public String edit(){
		ok = chanLiangService.edit(chanLiang);
		return OK;
	}
	/**
	 * 获取楼下汇总
	 * @return
	 */
	public String getLouxia(){
		int i = chanLiangService.getLouxia(chanLiang.getIssue());
		ActionContext.getContext().getValueStack().set("result", i);
		return SUCCESS;
	}
}
