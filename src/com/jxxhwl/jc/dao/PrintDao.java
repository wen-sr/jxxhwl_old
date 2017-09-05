package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Distribution;

public interface PrintDao {
	/**
	 * 加载需要打印的批次号
	 * @return
	 */
	public List<Distribution> loadBatchno();
	/**
	 * 加载需要打印的拣货流水号
	 * @return
	 */
	public List<Distribution> loadPickno();
	/**
	 * 获取整件发运单头
	 * @param batchno
	 * @return
	 */
	public List<Distribution> getWholeCaseListHead(String batchno);
	/**
	 * 获取整件发运明细
	 * @param batchno
	 * @return
	 */
	public List<Distribution> getWholeCaseListDetail(String batchno);
	/**
	 * 零件发运
	 * @param batchno
	 * @return
	 */
	public List<Distribution> getOddCaseList(String batchno);
	/**
	 * 手拣单单头
	 * @param pickno
	 * @return
	 */
	public List<Distribution> getPickListHead(String pickno);
	/**
	 * 手拣单明细
	 * @param pickno
	 * @return
	 */
	public List<Distribution> getPickListDetail(String pickno);
	/**
	 * 调拨单
	 * @param batchno
	 * @return
	 */
	public List<Distribution> getAllocationList(String batchno);
	/**
	 * 票签
	 * @param batchno
	 * @return
	 */
	public List<Distribution> getS_ticketList(String batchno);
	/**
	 * 获得零件清单单头
	 * @param batchno
	 * @return
	 */
	public List<Distribution> getOddCaseListHead(String batchno);
	/**
	 * 获得退货票签
	 * @param subcode
	 * @return
	 */
	public Distribution getReturnTicket(String subcode);

}
