package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Orders;
import com.jxxhwl.jc.entity.Sku;
import com.jxxhwl.jc.service.OrdersService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class OrdersAction extends BaseAction implements ModelDriven<Orders>{
	Orders orders = new Orders();
	@Override
	public Orders getModel() {
		return orders;
	}
	@Resource
	private OrdersService ordersService;
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		ok = ordersService.save(orders);
		return "ok";
	}
	/**
	 * 根据期号查询可采购的征订代码
	 * @return
	 */
	public String waitPurchaseSku(){
		List<Sku> list = ordersService.waitPurchaseSku(orders.getIssuenumber());
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 加载可采购商品信息
	 * @return
	 */
	public String loadWaitPurchaseSkuInfo(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map = ordersService.loadWaitPurchaseSkuInfo(orders,currentPage,pageSize);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 加载已采购信息
	 * @return
	 */
	public String info (){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
        Map<String,Object> map = ordersService.loadOrdersInfo(orders,currentPage,pageSize);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 查询已采购征订代码
	 * @return
	 */
	public String loadOrdersSku(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		List<Sku> list = ordersService.loadOrdersSku(orders.getIssuenumber(),currentPage,pageSize);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete (){
		ok = ordersService.delete(orders.getId());
		return "ok";
	}
	/**
	 * 修改
	 * @return
	 */
	public String edit(){
		ok = ordersService.update(orders);
		return "ok";
	}
}
