package com.jxxhwl.jc.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Inventory;
import com.jxxhwl.jc.service.InventoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
public class InventoryAction extends BaseAction implements ModelDriven<Inventory>{
	/**
	 * 模型驱动
	 */
	private Inventory inventory = new Inventory();
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	@Override
	public Inventory getModel() {
		return inventory;
	}
	@Resource
	private InventoryService inventoryService;
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}
	/**
	 * 查询库存
	 * @return
	 */
	public String queryInventory(){
		List<Inventory> list =  inventoryService.queryInventory(inventory);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 查询可用库存和该品种的每包捆数
	 * @return
	 */
	public String queryInventoryAndBundle(){
		Inventory i = inventoryService.queryInventoryAndBundle(inventory);
		ActionContext.getContext().getValueStack().set("result", i);
		return SUCCESS;
	}
}
