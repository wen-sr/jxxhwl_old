package com.jxxhwl.jc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.InventoryDao;
import com.jxxhwl.jc.entity.Inventory;
import com.jxxhwl.jc.service.InventoryService;
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{
	
	@Resource
	private InventoryDao inventoryDao;
	/**
	 * 查询库存
	 */
	@Override
	public List<Inventory> queryInventory(Inventory inventory) {
		List<Inventory> list = inventoryDao.findInventoryDetail(inventory);
		return list;
	}
	/**
	 * 查询可用库存和该品种的每包捆数
	 */
	@Override
	public Inventory queryInventoryAndBundle(Inventory inventory) {
		Inventory i = inventoryDao.queryInventoryAndBundle(inventory);
		return i;
	}
}
