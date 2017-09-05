package com.jxxhwl.jc.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jxxhwl.jc.dao.InventoryDao;
import com.jxxhwl.jc.dao.IssuenumberDao;
import com.jxxhwl.jc.dao.OrdersDao;
import com.jxxhwl.jc.dao.PackDao;
import com.jxxhwl.jc.dao.SkuDao;
import com.jxxhwl.jc.dao.StorerDao;
import com.jxxhwl.jc.entity.Inventory;
import com.jxxhwl.jc.entity.Issuenumber;
import com.jxxhwl.jc.entity.Pack;
import com.jxxhwl.jc.entity.Sku;
import com.jxxhwl.jc.entity.Storer;

@Component
public class ValidateBasicInfo {
	
	@Resource
	private IssuenumberDao issuenumberDao;
	public void setIssuenumberDao(IssuenumberDao issuenumberDao) {
		this.issuenumberDao = issuenumberDao;
	}
	@Resource
	private SkuDao skuDao;
	public void setSkuDao(SkuDao skuDao) {
		this.skuDao = skuDao;
	}
	@Resource
	private StorerDao storerDao;
	public void setStorerDao(StorerDao storerDao) {
		this.storerDao = storerDao;
	}
	@Resource
	private PackDao packDao;
	public void setPackDao(PackDao packDao) {
		this.packDao = packDao;
	}
	@Resource
	private InventoryDao inventoryDao;
	public void setInventoryDao(InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}
	@Resource
	private OrdersDao ordersDao;
	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	/**
	 * 判断期号是否存在
	 * @param issuenumber
	 * @return
	 */
	public boolean isExistIssuenumber(String issuenumber){
		Issuenumber i = issuenumberDao.findByIssuenumber(issuenumber);
		if(i == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 判断客户是否存在
	 * @param customer
	 * @return
	 */
	public boolean isExistCustomer(String customer){
		Storer s = storerDao.findByStorerkey(customer);
		if(s == null ){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 判断书号是否存在
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public boolean isExistSku(String issuenumber, String subcode){
		Sku s = skuDao.findByIssuenumberAndSubcode(issuenumber, subcode);
		if(s == null ){
			return false;
		}else{
			return true;
		}
	}	
	/**
	 * 判断捆扎信息是否存在
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public boolean isExistPack(String issuenumber, String subcode, int pack){
		Pack p = packDao.findPackByissuenumberAndSubcode(issuenumber, subcode, pack);
		if(p == null) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 判断是否有库存记录
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public boolean isExistInventory(Inventory in){
		Inventory i = inventoryDao.findByIssuenumberAndSubcode(in);
		if(i == null) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 判断是否有库存明细记录
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public boolean isExistInventoryDetail(Inventory in){
		List<Inventory> list = inventoryDao.findInventoryDetail(in);
		if(list != null && list.size() > 0){
			Inventory i = inventoryDao.findInventoryDetail(in).get(0);
			if(i == null) {
				return false;
			}else {
				return true;
			}
		}else{
			return false;
		}
		
	}
	/**
	 * 判断是否有库存记录
	 * @param issuenumber
	 * @param subcode
	 * @param pack
	 * @return
	 */
	public boolean isExistInventory(String issuenumber, String subcode, int pack){
		Inventory in = new Inventory();
		in.setIssuenumber(issuenumber);
		in.setSubcode(subcode);
		Inventory i = inventoryDao.findByIssuenumberAndSubcode(in);
		if(i == null) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 判断分发库存是否足够
	 * @param inventory
	 * @return
	 */
	public boolean isEnoughInventory(Inventory inventory){
		Inventory i = inventoryDao.findByIssuenumberAndSubcode(inventory);
		if(i.getQtyfree()<inventory.getQtyallocated()){
			return false;
		}
		return true;
	}
	/**
	 * 判断分发库存明细是否足够
	 * @param inventory
	 * @return
	 */
	public boolean isEnoughInventoryDetail(Inventory inventory){
		Inventory i = inventoryDao.findByIssuenumberAndSubcodeDetail(inventory);
		if(i.getQtyfree()<inventory.getQtyallocated()){
			return false;
		}
		return true;
	}
	/**
	 * 判断采购分发数是否足够
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public boolean isEnoughOrderqty(String issuenumber, String subcode, int qtyallocated){
		int qty = ordersDao.loadOrdersQty(issuenumber, subcode);
		if(qty < qtyallocated){
			return false;
		}
		return true;
	}
	/**
	 * 判断收货数是否足够
	 * @param issuenumber
	 * @param subcode
	 * @param qtyallocated
	 * @return
	 */
	public boolean isEnoughReceiptOrderqty(String issuenumber, String subcode, int qtyallocated){
		int qty = ordersDao.loadReceiptOrderQty(issuenumber, subcode);
		if(qty < qtyallocated){
			return false;
		}
		return true;
	}
}
