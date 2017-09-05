package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Distribution;

public interface OddPickService {
	/**
	 * 加载需零件拣货数据
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> loadWaitShipData(int pageSize, int currentPage,Distribution distribution);
	/**
	 * 生成手拣单
	 * @param ids
	 * @return
	 */
	public Map<String, Object> addPickno(String[] ids);
	/**
	 * 加载已拣品种
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> loadShippedData(int pageSize, int currentPage);
	/**
	 * 根据期号查询需发品种的征订代码
	 * @param distribution
	 * @return
	 */
	public List<Distribution> loadShipSubcodeByIssuenumber(Distribution distribution);
	/**
	 * 根据期号查询已发品种的征订代码
	 * @param issuenumber
	 * @return
	 */
	public List<Distribution> loadshippedSubcodeByIssuenumber(String issuenumber);
	
	/**
	 * 根据期号和正定代码查询未拣记录
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Map<String, Object> loadWaitShipDataBySubcode(int pageSize,
			int currentPage, Distribution distribution);
	/**
	 * 根据期号和正定代码查询已拣记录
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Map<String, Object> loadShippedDataBySubcode(int pageSize,
			int currentPage, Distribution distribution);

}
