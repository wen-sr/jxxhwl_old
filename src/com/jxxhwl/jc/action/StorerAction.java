package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.dao.StorerDao;
import com.jxxhwl.jc.entity.Storer;
import com.jxxhwl.jc.service.StorerService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
public class StorerAction extends BaseAction implements ModelDriven<Storer>{
	Storer storer = new Storer();
	@Override
	public Storer getModel() {
		return storer;
	}
	@Resource
	private StorerDao storerDao;
	public void setStorerDao(StorerDao storerDao) {
		this.storerDao = storerDao;
	}
	@Resource
	private StorerService storerService;
	public void setStorerService(StorerService storerService) {
		this.storerService = storerService;
	}

	/**
	 * 获取客户信息
	 * @return
	 */
	public String getCustomer(){
		List<Storer> list = storerDao.getCustomer();
		ActionContext.getContext().getValueStack().set("list", list);
		return "success";
	}
	
	/**
	 * 获取供应商信息
	 * @return
	 */
	public String getSupplier(){
		List<Storer> list = storerDao.getSupplier();
		ActionContext.getContext().getValueStack().set("list", list);
		return "success";
	}
	/**
	 * 获取所有信息
	 */
	public String info (){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map = storerService.getInfo(currentPage,pageSize);
		ActionContext.getContext().getValueStack().set("map", map);
		return "info";
	}
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		ok = storerService.save(storer);
		return "save";
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		ok = storerService.delete(storer.getId());
		return "delete";
	}
	/**
	 * 修改
	 * @return
	 */
	public String update (){
		ok = storerService.update(storer);
		return "update";
	}
	/**
	 * 查询
	 * @return
	 */
	public String queryInfo(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String, Object> map = storerService.queryInfo(storer,currentPage,pageSize);
		ActionContext.getContext().getValueStack().set("map", map);
		return "queryInfo";
	}
	/**
	 * 修改
	 * @return
	 */
	public String edit(){
		ok = storerService.edit(storer);
		return "edit";
	}
}
