package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.ComputeDao;
import com.jxxhwl.jc.dao.WholeShipDao;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.service.WholeShipService;

@Service
@Transactional
public class WholeShipServiceImpl implements WholeShipService{
	@Resource
	private WholeShipDao wholeShipDao;
	/**
	 * 加载需整件发货数据
	 */
	@Override
	public Map<String, Object> loadWaitShipData(int pageSize, int currentPage,Distribution distribution) {
		List<Distribution> list = wholeShipDao.loadWaitShipData(pageSize, currentPage,distribution);
		int total = wholeShipDao.countTotal(distribution);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 生成批次
	 */
	@Override
	public Map<String,Object> addBatchno(String[] ids, String addwho) {
		Map<String,Object> map = new HashMap<String, Object>();
		String batchno = wholeShipDao.getBatchno();
		String msg = "生成批次成功，批次号为" + batchno;
		StringBuffer zb = new StringBuffer();
		for(String s: ids){
			zb.append("'").append(s).append("',");
		}
		String id = zb.toString();
		id = zb.substring(0, zb.length()-1);
		int i = wholeShipDao.addBatchno(id,batchno,addwho);
		if(i > 0){
			while(wholeShipDao.getNeedShipnoId(batchno) != null){
				String idd = wholeShipDao.getNeedShipnoId(batchno);
				String shipno = wholeShipDao.getShipno();
				if(wholeShipDao.addShipno(idd,shipno) <= 0){
					msg = "添加运号失败";
					batchno = "";
				}
			}
		}else{
			msg = "添加批次号失败";
			batchno = "";
		}
		map.put("msg", msg);
		map.put("batchno", batchno);
		return map;
	}
	/**
	 * 加载已发信息
	 */
	@Override
	public Map<String, Object> loadShippedData(int pageSize, int currentPage) {
		List<Distribution> list = wholeShipDao.loadShippedData(pageSize, currentPage);
		int total = wholeShipDao.countTotal_1();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 根据期号加载需计算的征订代码
	 */
	@Override
	public List<Distribution> loadWaitShipDataTotal(Distribution distribution) {
		List<Distribution> list = wholeShipDao.loadWaitShipDataTotal(distribution);
		return list;
	}
	/**
	 * 根据期号加载已发的征订代码
	 */
	@Override
	public List<Distribution> loadshippedSubcodeByIssuenumber(String issuenumber) {
		List<Distribution> list = wholeShipDao.loadshippedSubcodeByIssuenumber(issuenumber);
		return list;
	}
	/**
	 * 根据征订代码和期号查询需发的数据
	 */
	@Override
	public Map<String, Object> loadWaitShipDataBySubcode(int pageSize,
			int currentPage, String issuenumber, String subcode) {
		List<Distribution> list = wholeShipDao.loadWaitShipDataBySubcode(pageSize, currentPage, issuenumber, subcode);
		int total = wholeShipDao.CountTotal_2(issuenumber, subcode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 根据期号和正定代码查询已发记录
	 */
	@Override
	public Map<String, Object> loadShippedDataBySubcode(int pageSize,
			int currentPage, Distribution distribution) {
		List<Distribution> list = wholeShipDao.loadShippedDataBySubcode(pageSize, currentPage, distribution);
		int total = wholeShipDao.CountTotal_3(distribution);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 删除批次
	 */
	@Override
	public String removeBatchno(String ids) {
		String[] ss = ids.split(",");
		StringBuffer zb = new StringBuffer();
		for(String s: ss){
			zb.append("'").append(s).append("',");
		}
		String id = zb.toString();
		id = zb.substring(0, zb.length()-1);
		int i = wholeShipDao.removeBatchno(id);
		return "取消批次成功";
	}
}
