package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Sku;
import com.jxxhwl.jc.service.SkuService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class SkuAction extends BaseAction implements ModelDriven<Sku>{

	//模型驱动
	Sku sku = new Sku();
	@Override
	public Sku getModel() {
		return sku;
	}
	@Resource
	private SkuService skuService;

	/**
	 * 查询
	 * @return
	 */
	public String info (){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
        Map<String,Object> map = skuService.findAllByPage(pageSize, currentPage);
		ActionContext.getContext().getValueStack().set("map", map);
		return "info";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		ok = skuService.save(sku);
		return "save";
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		ok = skuService.delete(sku.getId());
		return "delete";
	}
	/**
	 * 修改
	 * @return
	 */
	public String update (){
		ok = skuService.update(sku);
		return "update";
	}
	/**
	 * 根据期号和征订代码查询
	 * @return
	 */
	public String queryByIssuenumberAndSubcode(){
		Sku s = skuService.findByIssuenumberAndSubcode(sku.getIssuenumber(), sku.getSubcode());
		ActionContext.getContext().getValueStack().set("sku", s);
		return "queryByIssuenumberAndSubcode";
	}
	/**
	 * 按条件查询
	 * @return
	 */
	public String query(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
        Map<String,Object> map = skuService.query(sku, pageSize, currentPage);
		ActionContext.getContext().getValueStack().set("map", map);
		return "query";
	}
	/**
	 * 获取征订代码
	 * @return
	 */
	public String getSubcode(){
		List<Sku> list = skuService.getSubcode();
		ActionContext.getContext().getValueStack().set("list", list);
		return "getSubcode";
	}
	/**
	 * 增加退货资料
	 * @return
	 */
	public String savaReturnSku(){
		ok = skuService.saveReturnSku(sku);
		return OK;
	}
}
