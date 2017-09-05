package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Distribution;

public interface WholeShipService {

	/**
	 * 加载需整件发货数据
	 * @param pageSize
	 * @param currentPage
	 * @param distribution 
	 * @return
	 */
	public Map<String, Object> loadWaitShipData(int pageSize, int currentPage, Distribution distribution);

	/**
	 * 生成批次
	 * @param ids
	 * @param string 
	 * @return
	 */
	public Map<String,Object> addBatchno(String[] ids, String addwho);

	/**
	 * 加载已发信息
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> loadShippedData(int pageSize, int currentPage);
	/**
	 * 根据期号加载需发的征订代码
	 * @param distribution
	 * @return
	 */
	public List<Distribution> loadWaitShipDataTotal(Distribution distribution);
	/**
	 * 根据期号加载已发的征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Distribution> loadshippedSubcodeByIssuenumber(String issuenumber);
	/**
	 * 根据征订代码和期号查询已配发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Map<String, Object> loadWaitShipDataBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode);
	/**
	 * 根据期号和正定代码查询已发记录
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Map<String, Object> loadShippedDataBySubcode(int pageSize,
			int currentPage, Distribution distribution);
	/**
	 * 删除批次
	 * @param batchno
	 * @return
	 */
	public String removeBatchno(String batchno);

}
