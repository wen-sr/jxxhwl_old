package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.OddPackDao;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Storer;
import com.jxxhwl.jc.service.OddPackService;

@Service
@Transactional
public class OddPackServiceImpl implements OddPackService {
	@Resource
	private OddPackDao oddPackDao;
	
	/**
	 * 加载需要打包的客户
	 */
	@Override
	public List<Storer> loadCustomer() {
		List<Storer> list = oddPackDao.loadCustomer();
		return list;
	}
	/**
	 * 加载未刷信息
	 */
	@Override
	public Map<String, Object> loadPackData(int pageSize, int currentPage,
			String issuenumber, String code, String status) {
		List<Distribution> list = oddPackDao.loadPackData(pageSize, currentPage, issuenumber, code, status);
		int total = oddPackDao.CountTotal(issuenumber, code, status);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 扫条码查询打包信息
	 */
	@Override
	public List<Distribution> findByBarcode(String issuenumber, String code,
			String barcode) {
		List<Distribution> list = oddPackDao.findByBarcode(issuenumber,code,barcode);
		return list;
	}
	/**
	 * 确认打包信息
	 */
	@Override
	public String confirmPack(String id, String addwho) {
		int i = oddPackDao.confirmPack(id,addwho);
		if(i > 0){
			return "确认成功";
		}
		return "确认失败，联系管理员";
	}
	/**
	 * 取消打包信息
	 */
	@Override
	public String cancelPack(String[] ids) {
		StringBuffer zb = new StringBuffer();
		for(String s: ids){
			zb.append("'").append(s).append("',");
		}
		String id = zb.toString();
		id = zb.substring(0, zb.length()-1);
		int i = oddPackDao.cancelPack(id);
		if(i > 0){
			return "取消成功";
		}
		return "取消失败，联系管理员";
	}
	/**
	 * 添加批次
	 */
	@Override
	public Map<String,Object> addBatchno(String[] ids) {
		String batchno = oddPackDao.getBatchno();
		String shipno = oddPackDao.getShipno();
		StringBuffer zb = new StringBuffer();
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		for(String s: ids){
			zb.append("'").append(s).append("',");
		}
		String id = zb.toString();
		id = zb.substring(0, zb.length()-1);
		int i = oddPackDao.addBatchno(id,batchno,shipno);
		if(i > 0){
			msg = "添加批次成功，批次号为:" + batchno; 
		}else{
			msg = "生成批次失败";
			batchno = "";
		}
		map.put("msg", msg);
		map.put("batchno", batchno);
		return map;
	}
}
