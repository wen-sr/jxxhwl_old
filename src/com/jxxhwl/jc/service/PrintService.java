package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Distribution;

public interface PrintService {
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
	 * 整件发运清单
	 * @param batchno
	 * @return
	 */
	public Map<String, Object> wholeCaseList(String batchno);
	/**
	 * 零件发运清单
	 * @param batchno
	 * @return
	 */
	public Map<String, Object> oddCaseList(String batchno);
	/**
	 * 手拣单
	 * @param pickno
	 * @return
	 */
	public Map<String, Object> pickList(String pickno);
	/**
	 * 调拨单
	 * @param batchno
	 * @return
	 */
	public Map<String, Object> allocationList(String batchno);
	/**
	 * 票签
	 * @param batchno
	 * @return
	 */
	public Map<String, Object> s_ticketList(String batchno);
	/**
	 * 拼包票签
	 * @param batchno
	 * @return
	 */
	public Distribution oddTicketList(String batchno);
	/**
	 * 退货票签
	 * @param subcode
	 * @return
	 */
	public Distribution returnTicket(String subcode);

}
