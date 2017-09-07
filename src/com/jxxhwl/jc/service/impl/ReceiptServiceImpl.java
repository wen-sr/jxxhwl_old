package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.DistributionDao;
import com.jxxhwl.jc.dao.InventoryDao;
import com.jxxhwl.jc.dao.PackDao;
import com.jxxhwl.jc.dao.ReceiptDao;
import com.jxxhwl.jc.dao.SkuDao;
import com.jxxhwl.jc.entity.Inventory;
import com.jxxhwl.jc.entity.Pack;
import com.jxxhwl.jc.entity.Receipt;
import com.jxxhwl.jc.entity.Sku;
import com.jxxhwl.jc.service.InventoryService;
import com.jxxhwl.jc.service.ReceiptService;
import com.jxxhwl.jc.util.ValidateBasicInfo;
@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {
	@Resource
	private ValidateBasicInfo validateBasicInfo;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private InventoryDao inventoryDao;
	/**
	 * 保存
	 */
	@Override
	public String save(Receipt receipt) {
		if(!validateBasicInfo.isExistIssuenumber(receipt.getIssuenumber())){
			return "期号信息不存在";
		}
		Sku s = new Sku(receipt.getIssuenumber(),receipt.getSubcode());
		if(!validateBasicInfo.isExistSku(receipt.getIssuenumber(), receipt.getSubcode())){
			return "图书资料不存在，请先录入图书资料";
		}
		if(!validateBasicInfo.isExistPack(receipt.getIssuenumber(), receipt.getSubcode(), receipt.getPack())){
			return "捆扎信息不存在，请先添加捆扎信息";
			
		}
		Receipt r = receiptDao.save(receipt);
		int j = 0;
		if(r.getId() != null){
			//增加库存
			Inventory inventory = new Inventory(receipt.getIssuenumber(),receipt.getSubcode(),receipt.getQtyreceipt(),0);
			Inventory inventoryDetail = new Inventory(receipt.getIssuenumber(),receipt.getSubcode(),receipt.getQtyreceipt(),receipt.getPack(),r.getId());
			if(!validateBasicInfo.isExistInventory(inventory)){
				j = inventoryDao.save(inventory);
			}else {
				j = inventoryDao.add(inventory);
				
			}
			if(j > 0){
				if(!validateBasicInfo.isExistInventoryDetail(inventoryDetail)){
					j+= inventoryDao.saveDetail(inventoryDetail);
				}else{
					j+= inventoryDao.addDetail(inventoryDetail);
				}
			}
			//更新当前品种在业务预分发记录的包册数
//			j += distributionDao.updatePredistributionPack(receipt.getIssuenumber(), receipt.getSubcode(), receipt.getPack());
		}else{
			return "保存信息失败";
		}
		if(j > 0){
			return "保存信息成功";
		}
		return "保存信息失败";
	}
	/**
	 * 加载数据
	 */
	@Override
	public Map<String, Object> loadData(int pageSize, int currentPage, Receipt receipt) {
		List<Receipt> list = receiptDao.findByPage(pageSize,currentPage, receipt);
		int total = receiptDao.getTotalCount();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	/**
	 * 修改
	 */
	@Override
	public String update(Receipt receipt) {
		if(!validateBasicInfo.isExistIssuenumber(receipt.getIssuenumber())){
			return "期号信息不存在";
		}
		if(!validateBasicInfo.isExistSku(receipt.getIssuenumber(), receipt.getSubcode())){
			return "图书资料不存在，请先录入图书资料";
		}
		if(!validateBasicInfo.isExistPack(receipt.getIssuenumber(), receipt.getSubcode(), receipt.getPack())){
			return "捆扎信息不存在";
		}
		
		//修改库存
		Receipt rr = receiptDao.findById(receipt.getId());
		int qty = receipt.getQtyreceipt() - rr.getQtyreceipt();
		if(!validateBasicInfo.isEnoughReceiptOrderqty(receipt.getIssuenumber(), receipt.getSubcode(), qty)){
			return "采购数不足，无法修改";
		}
		Inventory ii = new Inventory(rr.getIssuenumber(),rr.getSubcode(),rr.getQtyreceipt(),0);
		Inventory ii2 = new Inventory(receipt.getIssuenumber(),receipt.getSubcode(),receipt.getQtyreceipt(),0,receipt.getPack());
		//修改库存
		if(inventoryDao.updateReceiptInventory(ii, qty) > 0){
			if(inventoryDao.updateReceiptInventoryDetail(ii2, qty) > 0){
				if(receiptDao.update(receipt)>0){
					return "修改成功";
				}
			}else{
				return "库存更新失败";
			}
		}else{
			return "库存更新失败";
		}
		return "修改失败";
//		System.out.println(4/0); TODO  事物管理失效
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(String id) {
		Receipt r = receiptDao.findById(id);
		//判断是否已分发
		//查询可用库存是否足够
		Inventory in = new Inventory(r.getIssuenumber(),r.getSubcode());
		in.setPack(r.getPack());
		in = inventoryDao.findByIssuenumberAndSubcodeDetail(in);
		if(r.getQtyreceipt() > in.getQtyfree()){
			return "库存已经分发，无法删除";
		}
		//上一步就已经判断了有库存且库存数够
//		in.setPack(r.getPack());
//		if(inventoryDao.findInventoryDetail(in) != null){
//			in = inventoryDao.findInventoryDetail(in).get(0);
//			if(r.getQtyreceipt() > in.getQtyfree()){
//				return "库存已经分发，无法删除";
//			}
//		}else{
//			return "库存已经分发，无法删除";
//		}
		//减去库存
		Inventory i = new Inventory(r.getIssuenumber(),r.getSubcode(),r.getQtyreceipt(),0);
		i.setPack(r.getPack());
		if(inventoryDao.remove(i) > 0){
			if(inventoryDao.removeDetail(i) > 0){
				if(receiptDao.delete(id) >0){
					return "删除成功";
				}else{
					return "记录删除失败";
				}
			}else{
				return "库存更新失败";
			}

		}else{
			return "库存更新失败";
		}
	}
	/**
	 * 选择未收货征订代码
	 */
	@Override
	public List<Receipt> waitReceiptSubcodeData(Receipt receipt) {
		List<Receipt> list = receiptDao.waitReceiptSubcodeData(receipt);
		return list;
	}
	/**
	 * 获得已收货的征订代码
	 */
	@Override
	public List<Receipt> loadReceiptSubcodeInfo(Receipt receipt) {
		List<Receipt> list = receiptDao.loadReceiptSubcodeInfo(receipt);
		return list;
	}
}
