package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Distribution;

public interface OddPickDao {
	/**
	 * 加载需零件拣货数据
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<Distribution> loadWaitShipData(int pageSize, int currentPage,Distribution distribution);
	/**
	 * 统计需零件拣货数据的总条数
	 * @return
	 */
	public int countTotal(Distribution distribution);
	/**
	 * 获得pickno
	 * @return
	 */
	public String getPickno();
	/**
	 * 生成手拣单
	 */
	public int addPickno(String pickno, String id);
	/**
	 * 加载已发品种
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<Distribution> loadShippedData(int pageSize, int currentPage);
	
	/**
	 * 统计已发品种的总条数
	 * @return
	 */
	public int countTotal_1();
	/**
	 * 根据期号查询需发品种的征订代码
	 * @param issuenumber
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
	 * 根据期号和正定代码查询需拣记录
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public List<Distribution> loadWaitShipDataBySubcode(int pageSize,
			int currentPage, Distribution distribution);
	/**
	 * 统计根据期号和正定代码查询需拣记录总条数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int CountTotal_2( Distribution distribution);
	/**
	 * 根据期号和正定代码查询已拣记录
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public List<Distribution> loadShippedDataBySubcode(int pageSize,
			int currentPage, Distribution distribution);
	/**
	 * 统计根据期号和正定代码查询已拣记录的总条数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int CountTotal_3(Distribution distribution);

}
