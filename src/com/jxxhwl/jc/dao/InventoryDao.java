package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Inventory;

public interface InventoryDao {
	/**
	 * 保存
	 * @param inventory
	 */
	public int save(Inventory inventory);
	/**
	 * 增加库存
	 * 
	 */
	public int add(Inventory inventory);
	/**
	 * 减少库存
	 * @param inventory
	 */
	public int remove (Inventory inventory);
	/**
	 * 根据期号和征订代码查询
	 * @param inventory
	 * @return
	 */
	public Inventory findByIssuenumberAndSubcode(Inventory inventory);
	/**
	 * 根据期号和征订代码查询
	 * @param inventory
	 * @return
	 */
	public Inventory findByIssuenumberAndSubcodeDetail(Inventory inventory);
	/**
	 * 分页查询
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<Inventory> findByPage(int pageSize, int currentPage);
	/**
	 * 查询记录总条数
	 * @return
	 */
	public int countTotal();
	/**
	 * 库存分发修改库存
	 * @param inventory
	 */
	public int distributeInventory(Inventory inventory);
	/**
	 * 更新qtyallocated
	 * @param i
	 * @param newqty
	 * @param oldqty
	 */
	public int updateInventoryChange(Inventory i,int newqty, int oldqty);
	/**
	 * 根据期号查询征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Inventory> loadDistributionSubcodeByIssuenumber(
			String issuenumber);
	/**
	 * 根据征订代码和期号查询待分发信息
	 * @param pageSize
	 * @param currentPage
	 * @param inventory
	 * @return
	 */
	public List<Inventory> findByPage(int pageSize, int currentPage, Inventory inventory);
	/**
	 * 根据期号和征订代码查询总条数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int countTotal(Inventory inventory);
	/**
	 * 查询库存明细
	 * @param in
	 * @return
	 */
	public List<Inventory> findInventoryDetail(Inventory in);
	/**
	 * 保存明细
	 * @param inventoryDetail
	 * @return
	 */
	public int saveDetail(Inventory inventoryDetail);
	/**
	 * 添加库存明细
	 * @param inventory
	 * @return
	 */
	public int addDetail(Inventory inventory);
	/**
	 * 删除明细
	 * @param ii2
	 */
	public int removeDetail(Inventory ii2);
	
	/**
	 *  修改收货时更新库存信息
	 * @param inventory
	 * @param qty
	 * @return
	 */
	public int updateReceiptInventory(Inventory inventory,int qty);
	/**
	 * 修改收货时更新库存明细信息
	 * @param inventory
	 * @param qty
	 * @return
	 */
	public int updateReceiptInventoryDetail(Inventory inventory,int qty);
	/**
	 * 查询可用库存和该品种的每包捆数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Inventory queryInventoryAndBundle(Inventory inventory);
	/**
	 * 更新库存明细
	 * @param i
	 * @param newqty
	 * @param oldqty
	 */
	public int updateInventoryDetailChange(Inventory i,int newqty, int oldqty);
	/**
	 * 更新inventor与inventory_detail保持一致
	 * @return
	 */
	public int updateInventoryFromInventoryDetail();
	
}
