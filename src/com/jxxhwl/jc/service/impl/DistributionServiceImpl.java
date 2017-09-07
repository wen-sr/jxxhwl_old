package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.DistributionDao;
import com.jxxhwl.jc.dao.InventoryDao;
import com.jxxhwl.jc.dao.IssuenumberDao;
import com.jxxhwl.jc.dao.OrdersDao;
import com.jxxhwl.jc.dao.SkuDao;
import com.jxxhwl.jc.dao.StorerDao;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Inventory;
import com.jxxhwl.jc.entity.Orders;
import com.jxxhwl.jc.entity.Sku;
import com.jxxhwl.jc.entity.Storer;
import com.jxxhwl.jc.service.DistributionService;
import com.jxxhwl.jc.util.ValidateBasicInfo;
@Service
@Transactional
public class DistributionServiceImpl implements DistributionService {

	@Resource
	private DistributionDao distributionDao;
	@Resource
	private IssuenumberDao issuenumberDao;
	@Resource
	private SkuDao skuDao;
	@Resource
	private StorerDao storerDao;
	@Resource
	private ValidateBasicInfo validateBasicInfo;
	@Resource
	private InventoryDao inventoryDao;
	@Resource
	private OrdersDao ordersDao;
	/**
	 * 保存分发信息
	 */
	@Override
	public Map<String, Object> save(Distribution distribution) {
		String s = "保存失败";
		Distribution d = null;
		int i = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		if(!validateBasicInfo.isExistIssuenumber(distribution.getIssuenumber())){
			s = "期号信息不存在";
			map.put("msg", s);
			return map;
		}
		if(!validateBasicInfo.isExistCustomer(distribution.getCode())){
			s = "客户信息不存在";
			map.put("msg", s);
			return map;
		}
		if("0".equals(distribution.getType())){
			if(!validateBasicInfo.isEnoughOrderqty(distribution.getIssuenumber(), distribution.getSubcode(), distribution.getQtyallocated())){
				s = "采购数量不足，无法保存";
				map.put("msg", s);
				return map;
			}
			d = distributionDao.save(distribution);
			int qtyfree = ordersDao.loadOrdersQty(distribution.getIssuenumber(), distribution.getSubcode());
			map.put("qtyfree", qtyfree);
		}
		
		if("1".equals(distribution.getType())){
			Inventory inv = new Inventory(distribution.getIssuenumber(),distribution.getSubcode());
			inv.setQtyallocated(distribution.getQtyallocated());
			if(!validateBasicInfo.isEnoughInventory(inv)){
				s = "库存不足，无法分发";
				map.put("msg", s);
				return map;
			}
			//更新Inventory的库存信息
			if(inventoryDao.distributeInventory(inv) > 0){
				//保存分发信息
				d = distributionDao.save(distribution);
				//查询Inventory库存
				Inventory ity = inventoryDao.findByIssuenumberAndSubcode(inv);
				map.put("qtyfree", ity.getQtyfree());
			}else{
				s = "库存更新失败";
			}
		}
		if(d != null) {
			s = "保存成功";
		}
		map.put("msg", s);
		return map;
	}
	/**
	 * 获得总记录条数
	 */
	@Override
	public int getDistributionTotalRows(Distribution distribution) {
		return distributionDao.getDistributionTotalRows(distribution);
	}
	/**
	 * 分页获取分发数据
	 */
	@Override
	public Map<String,Object> getDistributionLoadData(int pageSize,
			int currentPage, Distribution distribution) {
		List<Distribution> list = distributionDao.getDistributedData(pageSize, currentPage, distribution);
		int total = distributionDao.getDistributionTotalRows(distribution);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(String id, String type) {
		//判断是否已进行配发
		String status = distributionDao.getDistributionStatusById(id);
		if(!"0".equals(status)){
			return "该记录已经开始作业，无法删除！";
		}
		//更新库存的操作放在取消计算那一步，这里就不需要这个操作了
//		if("1".equals(type)){
//			Distribution dd = distributionDao.findById(id);
//			Inventory i = new Inventory(dd.getIssuenumber(),dd.getSubcode());
//			if(inventoryDao.updateInventoryChange(i, 0, dd.getQtyallocated())<=0){
//				return "库存更新失败";
//			}
//		}
		int i = distributionDao.delete(id);
		if(i > 0){
			return "记录删除成功！";
		}
		return "记录删除失败！";
	}
	/**
	 * 修改
	 */
	@Override
	public String update(Distribution distribution) {
		int inn = 0;
		if(!validateBasicInfo.isExistIssuenumber(distribution.getIssuenumber())){
			return "期号信息不存在";
		}
		if(!validateBasicInfo.isExistCustomer(distribution.getCode())){
			return "客户信息不存在";
		}
		Distribution dd = distributionDao.findById(distribution.getId());
		if(!"0".equals(dd.getStatus())){
			return "此单据已配发，无法修改";
		}
		//判断修改后的数量是否大于可用数
		if("0".equals(distribution.getType())){
			if(!validateBasicInfo.isEnoughOrderqty(distribution.getIssuenumber(), distribution.getSubcode(), distribution.getQtyallocated())){
				return "采购数量不足，无法修改";
			}
			inn = distributionDao.update(distribution);
		}
		Inventory in = new Inventory(distribution.getIssuenumber(),distribution.getSubcode());
		Inventory i = inventoryDao.findByIssuenumberAndSubcode(in);
		if("1".equals(distribution.getType())){
			if(i.getQtyfree()+dd.getQtyallocated() < distribution.getQtyallocated()){
				return "库存不足，无法修改";
			}
			if(inventoryDao.updateInventoryChange(i, distribution.getQtyallocated(), dd.getQtyallocated())>0){
				inn = distributionDao.update(distribution);
			}else{
				return "库存更新失败";
			}
		}
		if(inn > 0){
			return "修改信息成功！";
		}
		return "修改信息失败！";
	}
	/**
	 * 加载库存已分发信息
	 */
	@Override
	public Map<String, Object> loadDistributedData(int pageSize, int currentPage) {
		List<Distribution> list = distributionDao.getDistributedData(pageSize, currentPage,new Distribution());
		int total = distributionDao.getDistributionTotalRows(new Distribution());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	/**
	 * 根据期号查询待分发的征订代码
	 */
	@Override
	public List<Inventory> loadDistributionSubcodeByIssuenumber(
			Distribution distribution) {
			List<Inventory> list = inventoryDao.loadDistributionSubcodeByIssuenumber(distribution.getIssuenumber());
			return list;
	}
	/**
	 * 根据期号查询已分发的征订代码
	 */
	@Override
	public List<Distribution> loadDistributedSubcodeByIssuenumber(
			String issuenumber) {
		List<Distribution> list = distributionDao.loadDistributedSubcodeByIssuenumber(issuenumber);
		return list;
	}
	/**
	 * 根据期号和征订代码查询库存已分发信息
	 */
	@Override
	public Map<String, Object> loadDistributedDataBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode) {
		List<Distribution> list = distributionDao.getDistributionLoadData(pageSize, currentPage, issuenumber, subcode);
		int total = distributionDao.countTotal(issuenumber, subcode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	/**
	 * 查询可预分发的数据
	 */
	@Override
	public Map<String,Object> getWaitPreDistributionLoadData(int pageSize,
			int currentPage, Distribution distribution) {
		Orders o = new Orders(distribution.getIssuenumber(), distribution.getSubcode());
		List<Distribution> list = distributionDao.getWaitPreDistributionLoadData(distribution,pageSize, currentPage);
		int total = distributionDao.countTotalWaitDistributionData(distribution);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	/**
	 * 查询已分发的征订代码
	 */
	@Override
	public List<Distribution> loadDistributedSubcodeByIssuenumber(
			Distribution distribution) {
		List<Distribution> list = distributionDao.loadDistributedSubcodeByIssuenumber(distribution);
		return list;
	}
	/**
	 * 查询可库存分发的数据
	 */
	@Override
	public Map<String, Object> loadWaitDistributionData(int pageSize,
			int currentPage, Distribution distribution) {
		Inventory inv = new Inventory(distribution.getIssuenumber(), distribution.getSubcode(), distribution.getBarcode());
		List<Inventory> list = inventoryDao.findByPage(pageSize, currentPage, inv);
		int total = inventoryDao.countTotal(inv);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
}
