package com.jxxhwl.jc.service;

import java.util.List;

import com.jxxhwl.jc.entity.Inventory;

public interface InventoryService {
	/**
	 * 查询库存
	 * @param inventory
	 * @return
	 */
	public List<Inventory> queryInventory(Inventory inventory);
	/**
	 * 查询可用库存和该品种的每包捆数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Inventory queryInventoryAndBundle(Inventory inventory);

	/**
	 * 查询库存是否一致
	 * @param inventory
	 * @return
	 */
    int isEqual(Inventory inventory);
}
