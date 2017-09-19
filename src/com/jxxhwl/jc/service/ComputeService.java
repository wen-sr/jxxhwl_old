package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Distribution;

public interface ComputeService {

	/**
	 * 分页查询需要配发计算的数据
	 * @param pageSize
	 * @param currentPage
	 * @param distribution 
	 * @return
	 */
	public Map<String, Object> findNeedCompute(int pageSize, int currentPage, Distribution distribution);

	/**
	 * 修改包册数
	 * @param distribution
	 * @return
	 */
	public String editPack(Distribution distribution);

	/**
	 * 根据id进行配发计算
	 * @param ids
	 * @param pack 
	 * @return
	 */
	public String compute(String[] ids, int pack, String addwho);

	/**
	 * 分页查询已配发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param distribution 
	 * @return
	 */
	public Map<String, Object> findComputedData(int pageSize, int currentPage, Distribution distribution);

	/**
	 * 取消配发计算
	 * @param id
	 * @return
	 */
	public String cancelCompute(String id);

	/**
	 * 根据期号加载需计算的征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Distribution> loadComputeSubcodeByIssuenumber(String issuenumber);

	/**
	 * 根据征订代码和期号查询需配发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Map<String, Object> findNeedComputeBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode);

	/**
	 *  根据期号加载已计算的征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Distribution> loadComputedSubcodeByIssuenumber(Distribution distribution);
	/**
	 * 根据征订代码和期号查询已配发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Map<String, Object> loadComputedDataBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode);
	/**
	 * 分书拆分
	 * @param distribution
	 * @return
	 */
	public String split(Distribution distribution);

	/**
	 * 所有品种
	 * @param distribution
	 * @return
	 */
    Map<String,Object> findAll(Distribution distribution);
}
