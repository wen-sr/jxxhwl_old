package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Distribution;

public interface DistributionDao {
	/**
	 * 保存分发信息
	 * @param distribution
	 * @return
	 */
	public Distribution save(Distribution distribution);
	/**
	 * 获取分发总记录条数
	 * @param type
	 * @return
	 */
	public int getDistributionTotalRows(Distribution distribution);
	/**
	 * 获取分发数据
	 * @param pageSize
	 * @param currentPage
	 * @param type
	 * @return
	 */
	public List<Distribution> getDistributedData(int pageSize,
			int currentPage, Distribution distribution);
	/**
	 * 获取分发记录的状态
	 * @param id
	 * @return
	 */
	public String getDistributionStatusById(String id);
	/**
	 * 删除
	 * @param id
	 */
	public int delete(String id);
	/**
	 * 修改
	 * @param d
	 */
	public int update(Distribution d);
	/**
	 * 根据id查询
	 * @param id
	 */
	public Distribution findById(String id);
	/**
	 * 根据期号查询已分发的征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Distribution> loadDistributedSubcodeByIssuenumber(
			String issuenumber);
	/**
	 * 根据期号和征订代码查询库存已分发的信息
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public List<Distribution> getDistributionLoadData(int pageSize,
			int currentPage, String issuenumber, String subcode);
	/**
	 * 根据期号和征订代码查询总条数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int countTotal(String issuenumber, String subcode);
	/**
	 * 更新业务预分发记录的包册数
	 * @param issuenumber
	 * @param subcode
	 * @param pack
	 */
	public int updatePredistributionPack(String issuenumber, String subcode, int pack);
	/**
	 * 查询可预分发的数据
	 * @param distribution
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<Distribution> getWaitPreDistributionLoadData(
			Distribution distribution, int pageSize, int currentPage);
	/**
	 * 统计可预分发的数据
	 * @param distribution
	 * @return
	 */
	public int countTotalWaitDistributionData(Distribution distribution);
	/**
	 * 查询已预分发的征订代码
	 * @param distribution
	 * @return
	 */
	public List<Distribution> loadDistributedSubcodeByIssuenumber(
			Distribution distribution);
}
