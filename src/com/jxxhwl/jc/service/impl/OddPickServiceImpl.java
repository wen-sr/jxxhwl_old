package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.OddPickDao;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.service.OddPickService;

@Service
@Transactional
public class OddPickServiceImpl implements OddPickService{
	@Resource
	private OddPickDao oddPickDao;

	/**
	 * 加载需零件拣货数据
	 */
	@Override
	public Map<String, Object> loadWaitShipData(int pageSize, int currentPage,Distribution distribution) {
		List<Distribution> list = oddPickDao.loadWaitShipData(pageSize, currentPage,distribution);
		int total = oddPickDao.countTotal(distribution);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 生成手拣单
	 */
	@Override
	public Map<String, Object> addPickno(String[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String pickno = oddPickDao.getPickno();
		StringBuffer zb = new StringBuffer();
		for(String s: ids){
			zb.append("'").append(s).append("',");
		}
		String id = zb.toString();
		id = zb.substring(0, zb.length()-1);
		int i = oddPickDao.addPickno(pickno,id);
		if(i > 0 ){
			msg = "生成手拣单成功，拣货流水号为：" + pickno;
			
		}else {
			msg = "生成手拣单失败，请联系管理员";
			pickno = "";
		}
		map.put("msg", msg);
		map.put("pickno", pickno);
		return map;
	}
	
	/**
	 * 加载已拣品种
	 */
	@Override
	public Map<String, Object> loadShippedData(int pageSize, int currentPage) {
		List<Distribution> list = oddPickDao.loadShippedData(pageSize, currentPage);
		int total = oddPickDao.countTotal_1();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 根据期号查询需发品种的征订代码
	 */
	@Override
	public List<Distribution> loadShipSubcodeByIssuenumber(Distribution distribution) {
		List<Distribution> list = oddPickDao.loadShipSubcodeByIssuenumber(distribution);
		return list;
	}
	/**
	 * 根据期号查询已发品种的征订代码
	 */
	@Override
	public List<Distribution> loadshippedSubcodeByIssuenumber(String issuenumber) {
		List<Distribution> list = oddPickDao.loadshippedSubcodeByIssuenumber(issuenumber);
		return list;
	}
	/**
	 * 根据期号和正定代码查询需拣记录
	 */
	@Override
	public Map<String, Object> loadWaitShipDataBySubcode(int pageSize,
			int currentPage, Distribution distribution) {
		List<Distribution> list = oddPickDao.loadWaitShipDataBySubcode(pageSize, currentPage, distribution);
		int total = oddPickDao.CountTotal_2(distribution);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 根据期号和正定代码查询已拣记录
	 */
	@Override
	public Map<String, Object> loadShippedDataBySubcode(int pageSize,
			int currentPage,Distribution distribution) {
		List<Distribution> list = oddPickDao.loadShippedDataBySubcode(pageSize, currentPage, distribution);
		int total = oddPickDao.CountTotal_3(distribution);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
}
