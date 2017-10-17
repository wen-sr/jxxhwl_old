package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.PrintDao;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.service.PrintService;

@Service
@Transactional
public class PrintServiceImpl implements PrintService {
	
	@Resource
	private PrintDao printDao;
	/**
	 * 加载需要打印的批次号
	 */
	@Override
	public List<Distribution> loadBatchno() {
		List<Distribution> list = printDao.loadBatchno();
		return list;
	}
	/**
	 * 加载需要打印的拣货流水号
	 */
	@Override
	public List<Distribution> loadPickno() {
		List<Distribution> list = printDao.loadPickno();
		return list;
	}
	/**
	 * 整件发运清单
	 */
	@Override
	public Map<String, Object> wholeCaseList(String batchno) {
		List<Distribution> head = printDao.getWholeCaseListHead(batchno);
		List<Distribution> detail = printDao.getWholeCaseListDetail(batchno);
		Map<String, Object> map = new HashMap<String, Object>();
		if(head != null && head.size() > 0 ){
			map.put("dt", head);
			map.put("mx", detail);
			return map;
		}
		return null;
	}
	/**
	 * 调拨单
	 */
	@Override
	public Map<String, Object> allocationList(String batchno) {
		List<Distribution> list = printDao.getAllocationList(batchno);
		Map<String, Object> map = new HashMap<String, Object>();
		if(list != null && list.size() > 0){
			map.put("list", list);
			map.put("batchno", batchno);
		}
		return map;
	}
	/**
	 * 票签
	 */
	@Override
	public Map<String, Object> s_ticketList(String batchno) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Distribution> list = printDao.getS_ticketList(batchno);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 零件发运清单
	 */
	@Override
	public Map<String, Object> oddCaseList(String batchno) {
		List<Distribution> detail = printDao.getOddCaseList(batchno);
		List<Distribution> head = printDao.getOddCaseListHead(batchno);
		Map<String, Object> map = new HashMap<String, Object>();
		if(head != null && head.size() > 0 ){
			map.put("dt", head);
			map.put("mx", detail);
			return map;
		}
		return null;
	}
	/**
	 * 拼包票签
	 */
	@Override
	public Distribution oddTicketList(String batchno) {
		List<Distribution> head = printDao.getOddCaseListHead(batchno);
		if(head != null && head.size() > 0){
			return head.get(0);
		}
		return null;
	}
	/**
	 * 手拣单
	 */
	@Override
	public Map<String, Object> pickList(String pickno) {
		List<Distribution> head = printDao.getPickListHead(pickno);
		List<Distribution> detail = printDao.getPickListDetail(pickno);

		//储位小计
		//List<Distribution> xiaoji = printDao.getPickXiaoJi(pickno);

		Map<String, Object> map = new HashMap<String, Object>();
		//if(head != null && head.size() > 0 && xiaoji.size() > 0 ){
		if(head != null && head.size() > 0 ){
			map.put("dt", head.get(0));
			map.put("mx", detail);
			//map.put("xj", xiaoji);
			return map;
		}
		return null;
	}
	/**
	 * 退货票签
	 */
	@Override
	public Distribution returnTicket(String subcode) {
		Distribution d = printDao.getReturnTicket(subcode);
		return d;
	}
}
