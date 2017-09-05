package com.jxxhwl.yb.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.yb.common.BaseAction;
import com.jxxhwl.yb.entity.BZChuHuo;
import com.jxxhwl.yb.service.BZChuHuoService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 包装出货
 * @author Administrator
 *
 */
@Controller("bZChuHuoAction")
@Scope("prototype")
public class BZChuHuoAction extends BaseAction implements ModelDriven<BZChuHuo>{

	BZChuHuo bZChuHuo = new BZChuHuo();
	@Override
	public BZChuHuo getModel() {
		return bZChuHuo;
	}
	@Autowired BZChuHuoService bZChuHuoService;
	
	/**
	 * 加载已登记信息
	 * @return
	 */
	public String loadData(){
		//当前页 
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
		//每页显示条数
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String, Object> map = bZChuHuoService.loadData(currentPage,pageSize,bZChuHuo.getDd());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 保存
	 * @return
	 */
	public String add(){
		ok = bZChuHuoService.add(bZChuHuo);
		return OK;
	}
	
	public String edit(){
		ok = bZChuHuoService.edit(bZChuHuo);
		return OK;
	}
	
}
