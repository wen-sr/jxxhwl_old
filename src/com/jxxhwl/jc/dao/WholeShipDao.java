package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Distribution;

public interface WholeShipDao {

	/**
	 * 加载需整件发货数据
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<Distribution> loadWaitShipData(int pageSize, int currentPage,Distribution distribution);

	/**
	 * 统计需整件发货的记录总条数
	 * @return
	 */
	public int countTotal(Distribution distribution);

	/**
	 * 获取新批次号
	 * @return
	 */
	public String getBatchno();

	/**
	 * 添加批次号信息
	 * @param id
	 * @param batchno
	 * @param addwho 
	 * @return
	 */
	public int addBatchno(String id, String batchno, String addwho);
	/**
	 * 添加运号信息
	 * @param id
	 * @param shipno 
	 */
	public int addShipno(String id, String shipno);
	/**
	 * 添加运号
	 * @param id
	 * @param shipno
	 * @return
	 */
	public int addShipno(Distribution distribution,String batchno, String shipno);
	/**
	 * 获取需要添加运号的id
	 * @param batchno
	 * @return
	 */
	public String getNeedShipnoId(String batchno);

	/**
	 * 获得运号信息
	 * @return
	 */
	public String getShipno();
	/**
	 * 加载已发信息
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<Distribution> loadShippedData(int pageSize, int currentPage);
	/**
	 * 统计已发信息的记录条数
	 * @return
	 */
	public int countTotal_1();
	/**
	 * 根据期号加载需发的征订代码
	 * @param issuenumber
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
	 * 根据征订代码和期号查询需发的数据
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public List<Distribution> loadWaitShipDataBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode);
	/**
	 * 根据征订代码和期号查询需发的数据的记录条数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int CountTotal_2(String issuenumber, String subcode);
	/**
	 * 根据期号和正定代码查询已发记录
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public List<Distribution> loadShippedDataBySubcode(int pageSize,
			int currentPage, Distribution distribution);
	/**
	 * 根据期号和正定代码查询已发记录的总条数
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public int CountTotal_3(Distribution distribution);
	/**
	 * 删除批次
	 * @param batchno
	 * @return
	 */
	public int removeBatchno(String batchno);
	/**
	 * 获得该批次号下的单据状态
	 * @param batchno
	 * @return
	 */
	public List<String> getStatusByBatchno(String batchno);

}
