package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Orders;
import com.jxxhwl.jc.entity.Sku;

public interface OrdersService {
	/**
	 * 保存
	 * @param orders
	 * @return
	 */
	public String save(Orders orders);
	/**
	 * 根据期号查询可采购的征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Sku> waitPurchaseSku(String issuenumber);
	/**
	 * 加载可采购商品信息
	 * @param orders
	 * @param pageSize 
	 * @param currentPage 
	 * @return
	 */
	public Map<String, Object> loadWaitPurchaseSkuInfo(Orders orders, int currentPage, int pageSize);
	/**
	 * 加载采购单信息	
	 * @param orders
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> loadOrdersInfo(Orders orders, int currentPage,
			int pageSize);
	/**
	 * 查询已采购征订代码
	 * @param issuenumber
	 * @param pageSize 
	 * @param currentPage 
	 * @return
	 */
	public List<Sku> loadOrdersSku(String issuenumber, int currentPage, int pageSize);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public String delete(String id);
	/**
	 * 修改
	 * @param orders
	 * @return
	 */
	public String update(Orders orders);

}
