package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Distribution;

public interface ComputeDao {

	/**
	 * 分页查询需要配发计算的数据
	 * @param pageSize
	 * @param currentPage
	 * @param distribution 
	 * @return
	 */
	public List<Distribution> findNeedCompute(int pageSize, int currentPage, Distribution distribution);
	/**
	 * 统计未配发记录总条数
	 * @param distribution 
	 * @return
	 */
	public int countTotal(Distribution distribution);
	/**
	 * 修改包册数
	 * @param id
	 * @param pack
	 */
	public void editPack(String id, int pack);
	/**
	 * 根据id进行配发计算
	 * @param id
	 */
	public int compute(String id);
	/**
	 * 分页查询已配发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param distribution 
	 * @return
	 */
	public List<Distribution> findComputedData(int pageSize, int currentPage, Distribution distribution);
	/**
	 * 统计已配发的记录总条数
	 * @return
	 */
	public int countTotal1();
	/**
	 * 取消配发计算
	 * @param id
	 * @return 
	 */
	public int cancelCompute(String id);
	/**
	 * 取消配发单号
	 * @param computeno
	 * @param code
	 * @return
	 */
	public int updateDistribution(String computeno, String code);
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
	public List<Distribution> findNeedComputeBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode);
	/**
	 * 根据期号和征订代码统计总需配发总条数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int CountTotal_1(String issuenumber, String subcode);
	/**
	 * 根据期号加载已计算的征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Distribution> loadComputedSubcodeByIssuenumber(
			Distribution distribution);
	/**
	 * 根据征订代码和期号查询已配发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public List<Distribution> findComputedBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode);
	
	/**
	 * 根据期号和征订代码统计总已配发总条数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int CountTotal_2(String issuenumber, String subcode);
	/**
	 * 更新捆扎和配发单号
	 * @param ids
	 * @param pack 
	 * @param computeno 
	 * @return
	 */
	public int updatePack(String[] ids, int pack,String addwho, String computeno);
	/**
	 * 获取新配发号
	 * @return
	 */
	public String getComputeno();
	/**
	 * 保存配发信息
	 * @param ids
	 * @return
	 */
	public int save(String[] ids);
	/**
	 * 按照配发号查询
	 * @param computeno
	 * @return
	 */
	public List<Distribution> getByComputeno(String computeno);
	/**
	 * 根据id查询
	 * @param s
	 * @return
	 */
	public Distribution findById(String s);
	
}
