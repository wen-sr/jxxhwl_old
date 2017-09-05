package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.ComputeDao;
import com.jxxhwl.jc.dao.DistributionDao;
import com.jxxhwl.jc.dao.InventoryDao;
import com.jxxhwl.jc.dao.PackDao;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Inventory;
import com.jxxhwl.jc.entity.Pack;
import com.jxxhwl.jc.service.ComputeService;
import com.jxxhwl.jc.util.ValidateBasicInfo;

@Service
@Transactional
public class ComputeServiceImpl implements ComputeService{
	
	@Resource
	private ComputeDao computeDao;
	@Resource
	private ValidateBasicInfo validateBasicInfo;
	@Resource
	private PackDao packDao;
	@Resource
	private DistributionDao distributionDao;
	@Resource
	private InventoryDao inventoryDao;
	/**
	 * 分页查询需要配发计算的数据
	 */
	@Override
	public Map<String, Object> findNeedCompute(int pageSize, int currentPage, Distribution distribution) {
		List<Distribution> list = computeDao.findNeedCompute(pageSize, currentPage, distribution);
		int total = computeDao.countTotal(distribution);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 修改包册数
	 */
	@Override
	public String editPack(Distribution distribution) {
		if(!validateBasicInfo.isExistPack(distribution.getIssuenumber(), distribution.getSubcode(), distribution.getPack())){
			Pack pack = new Pack();
			pack.setIssuenumber(distribution.getIssuenumber());
			pack.setSubcode(distribution.getSubcode());
			pack.setPack(distribution.getPack());
			packDao.save(pack);
		}
		computeDao.editPack(distribution.getId(),distribution.getPack());
		return "修改成功";
	}
	
	/**
	 * 根据id进行配发计算
	 */
	@Override
	public String compute(String[] ids, int pack, String addwho) {
		Inventory i = null;
		StringBuffer sb = new StringBuffer();
		
		String computeno = computeDao.getComputeno();
		//update jiaocaidistribution
		
		if(computeDao.updatePack(ids,pack,addwho,computeno) > 0 ){
			//insert into jiaocaicompute 
			if(computeDao.save(ids) <= 0){
				return "已更新分发的捆扎和状态，但是生成配发信息失败";
			}
		}else{
			return "更新分发的捆扎和状态失败";
		}
		
		List<Distribution> list = computeDao.getByComputeno(computeno);
		for(Distribution d : list){
			i = new Inventory(d.getIssuenumber(),d.getSubcode(), 0, d.getQtyallocated(), pack );
			//validate inventorydetail 
			if(!validateBasicInfo.isEnoughInventoryDetail(i)){
				return "此捆扎的库存不足，无法计算";
			}
			
			if(inventoryDao.updateInventoryDetailChange(i, i.getQtyallocated(), 0) > 0){
				if(computeDao.compute(d.getId()) > 0){
					continue;
				}else{
					return (d.getShortname() + "的" + d.getSubcode() + "计算失败；");
				}
			}else{
				return "库存更新失败";
			}
		}
		
		//更新JiaoCaiInventory与JiaoCaiInventory_detail保持一致
		inventoryDao.updateInventoryFromInventoryDetail();
		return sb.append("计算成功").toString();
	}
	/**
	 * 分页查询已配发的数据
	 */
	@Override
	public Map<String, Object> findComputedData(int pageSize, int currentPage,Distribution distribution) {
		List<Distribution> list = computeDao.findComputedData(pageSize, currentPage, distribution);
		int total = computeDao.countTotal1();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 取消配发计算
	 */
	@Override
	public String cancelCompute(String id) {
		String[] ids = id.split(",");
		Distribution d = null;
		for(String s : ids ){
			d = computeDao.findById(s);
			if(!"1".equals(d.getStatus())){
				return d.getShortname() + "的 " + d.getQtyallocated() + " 不是计算完成状态，无法取消计算";
			}
			Inventory i = new Inventory(d.getIssuenumber(),d.getSubcode(),0, d.getQtyallocated(),d.getPack() );
			if(inventoryDao.updateInventoryDetailChange(i, 0, d.getQtyallocated())>0){
				if(computeDao.cancelCompute(s) > 0){
					if(computeDao.updateDistribution(d.getComputeno(), d.getCode()) > 0){
						continue;
					}else{
						return d.getShortname() + "的 " + d.getQtyallocated() + " 取消计算失败";
					}
				}
			}else{
				return "库存更新失败";
			}
		}
		return "取消计算成功";
	}
	/**
	 * 根据期号加载需计算的征订代码
	 */
	@Override
	public List<Distribution> loadComputeSubcodeByIssuenumber(String issuenumber) {
		List<Distribution> list = computeDao.loadComputeSubcodeByIssuenumber(issuenumber);
		return list;
	}
	/**
	 * 根据征订代码和期号查询需配发的数据
	 */
	@Override
	public Map<String, Object> findNeedComputeBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode) {
		List<Distribution> list = computeDao.findNeedComputeBySubcode(pageSize, currentPage, issuenumber, subcode);
		int total = computeDao.CountTotal_1(issuenumber, subcode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 *  根据期号加载已计算的征订代码
	 */
	@Override
	public List<Distribution> loadComputedSubcodeByIssuenumber(
			Distribution distribution) {
		List<Distribution> list = computeDao.loadComputedSubcodeByIssuenumber(distribution);
		return list;
	}
	/**
	 * 根据征订代码和期号查询已配发的数据
	 */
	@Override
	public Map<String, Object> loadComputedDataBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode) {
		List<Distribution> list = computeDao.findComputedBySubcode(pageSize, currentPage, issuenumber, subcode);
		int total = computeDao.CountTotal_2(issuenumber, subcode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 分书拆分
	 */
	@Override
	public String split(Distribution distribution) {
		Distribution d = distributionDao.findById(distribution.getId());
		int qtysplit = distribution.getQtyallocated();
		int qtyallocated = d.getQtyallocated();
		String id = d.getId();
		d.setAddwho(distribution.getAddwho());
		d.setQtyallocated(qtysplit);
		if(distributionDao.save(d) != null){
			d.setId(id);
			d.setQtyallocated(qtyallocated - qtysplit);
			d.setAddwho(distribution.getAddwho());
			if(distributionDao.update(d) > 0){
				return "拆分成功";
			}else{
				return "更新原记录失败";
			}
		}else{
			return "保存拆分记录失败";
		}
	}
}
