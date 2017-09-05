package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Inventory;

public interface DistributionService {
	/**
	 * 保存分发信息
	 * @param distribution
	 */
	public Map<String, Object> save(Distribution distribution);
	
	/**
	 * 获取分发记录条数
	 * @param type 类型（0：预分发，1：库存分发）
	 * @return
	 */
	public int getDistributionTotalRows(Distribution distribution);
	
	/**
	 * 分页获取分发数据
	 * @param pageSize 每页显示条数
	 * @param currentPage 当前页
	 * @param type 类型（0：预分发，1：库存分发）
	 * @return
	 */
	public Map<String,Object> getDistributionLoadData(int pageSize,
			int currentPage, Distribution distribution);

	/**
	 * 删除
	 * @param id
	 * @param type 
	 * @return
	 */
	public String delete(String id, String type);
	/**
	 * 修改
	 * @param distribution
	 * @return
	 */
	public String update(Distribution distribution);

	/**
	 * 加载库存已分发信息
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> loadDistributedData(int pageSize, int currentPage);

	/**
	 * 根据期号查询待分发的征订代码
	 * @param distribution
	 * @return
	 */
	public List<Inventory> loadDistributionSubcodeByIssuenumber(
			Distribution distribution);
	/**
	 * 根据期号查询已分发的征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Distribution> loadDistributedSubcodeByIssuenumber(
			String issuenumber);

	/**
	 * 根据期号和征订代码查询库存已分发信息
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Map<String, Object> loadDistributedDataBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode);
	/**
	 * 查询可预分发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param distribution
	 * @return
	 */
	public Map<String,Object> getWaitPreDistributionLoadData(int pageSize,
			int currentPage, Distribution distribution);
	/**
	 * 查询已预分发的征订代码
	 * @param distribution
	 * @return
	 */
	public List<Distribution> loadDistributedSubcodeByIssuenumber(
			Distribution distribution);
	/**
	 * 查询可库存分发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param distribution
	 * @return
	 */
	public Map<String, Object> loadWaitDistributionData(int pageSize,
			int currentPage, Distribution distribution);
	

}
