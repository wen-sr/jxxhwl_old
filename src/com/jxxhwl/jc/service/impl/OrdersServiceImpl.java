package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.OrdersDao;
import com.jxxhwl.jc.dao.SkuDao;
import com.jxxhwl.jc.entity.Orders;
import com.jxxhwl.jc.entity.Sku;
import com.jxxhwl.jc.service.OrdersService;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService{
	
	@Resource
	private OrdersDao ordersDao;
	@Resource
	private SkuDao skuDao;
	/**
	 * 保存
	 */
	@Override
	public String save(Orders orders) {
		int i = ordersDao.save(orders);
		if(i > 0){
			return "新增成功";
		}
		return "新增失败";
	}
	/**
	 * 根据期号查询可采购的征订代码
	 */
	@Override
	public List<Sku> waitPurchaseSku(String issuenumber) {
		Sku sku = new Sku();
		sku.setIssuenumber(issuenumber);
		List<Sku> list = skuDao.query(sku, 1, 100);
		return list;
	}

	/**
	 * 加载可采购商品信息
	 */
	@Override
	public Map<String, Object> loadWaitPurchaseSkuInfo(Orders orders, int currentPage, int pageSize) {
		List<Sku> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		Sku sku = new Sku();
		sku.setIssuenumber(orders.getIssuenumber());
		sku.setSubcode(orders.getSubcode());
		list = skuDao.query(sku, currentPage, pageSize);
		total = skuDao.countTotal(sku);
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 加载已采购的信息
	 */
	@Override
	public Map<String, Object> loadOrdersInfo(Orders orders, int currentPage,
			int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orders> list = ordersDao.loadOrdersInfo(orders, currentPage, pageSize);
		int total = ordersDao.countTotal(orders);
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 查询已采购征订代码
	 */
	@Override
	public List<Sku> loadOrdersSku(String issuenumber, int currentPage, int pageSize) {
		List<Sku> list = ordersDao.loadOrdersSku(issuenumber, currentPage, pageSize);
		return list;
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(String id) {
		int i = ordersDao.delete(id);
		if(i > 0){
			return "删除成功";
		}
		return "删除失败";
	}
	/**
	 * 修改
	 */
	@Override
	public String update(Orders orders) {
		int i = ordersDao.update(orders);
		if(i > 0){
			return "修改成功";
		}
		return "修改失败";
	}
}
