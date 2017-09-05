package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Orders;
import com.jxxhwl.jc.entity.Sku;

public interface OrdersDao {
	/**
	 * 保存
	 * @param orders
	 * @return
	 */
	public int save(Orders orders);
	/**
	 * 加载已采购的信息
	 * @param orders
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Orders> loadOrdersInfo(Orders orders, int currentPage,
			int pageSize);
	/**
	 * 统计总条数
	 * @param orders
	 * @return
	 */
	public int countTotal(Orders orders);
	/**
	 * 加载已采购征订代码
	 * @param issuenumber
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Sku> loadOrdersSku(String issuenumber, int currentPage,
			int pageSize);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(String id);
	/**
	 * 修改
	 * @param orders
	 * @return
	 */
	public int update(Orders orders);
	/**
	 * 加载预分发可用数量
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int loadOrdersQty(String issuenumber, String subcode);
	/**
	 * 加载收货可用数量
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int loadReceiptOrderQty(String issuenumber, String subcode);
}
