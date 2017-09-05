package com.jxxhwl.yb.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.yb.common.BaseAction;
import com.jxxhwl.yb.dao.SkuDao;
import com.jxxhwl.yb.entity.ZhiJian;
import com.jxxhwl.yb.service.ZhiJianService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ZhiJianAction extends BaseAction implements ModelDriven<ZhiJian>{

	ZhiJian zhiJian = new ZhiJian();
	@Override
	public ZhiJian getModel() {
		return zhiJian;
	}
	@Resource(name="skuDao_yb") SkuDao skuDao;
	@Autowired ZhiJianService zhiJianService;
	/**
	 * 根据条码查询书号信息
	 * @return
	 */
	public String findByBarcode() {
		List<ZhiJian> list = skuDao.findByBarcode(zhiJian);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 加载信息
	 * @return
	 */
	public String loadData(){
		List<ZhiJian> list =  zhiJianService.loadData();
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	
	public String add(){
		ok = zhiJianService.add(zhiJian);
		return OK;
	}
	
	public String delete (){
		ok = zhiJianService.delete(zhiJian.getId());
		return OK;
	}
}
