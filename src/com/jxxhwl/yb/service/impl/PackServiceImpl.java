package com.jxxhwl.yb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.yb.dao.PackDao;
import com.jxxhwl.yb.entity.Pack;
import com.jxxhwl.yb.service.PackService;
import com.opensymphony.xwork2.ActionContext;

@Service("packService_yb")
@Transactional
public class PackServiceImpl implements PackService{
	@Resource(name="packDao_yb") PackDao packDao;
	/**
	 * 根据客户代码查询客户信息
	 */
	@Override
	public List<Pack> findByCode(String code) {
		List<Pack> list = packDao.findByCode(code);
		return list;
	}
	/**
	 * 加载未打包数据
	 */
	@Override
	public Map<String, Object> loadUnpackData(int currentPage, int pageSize,
			Pack pack) {
		List<Pack> rows = packDao.loadPackData(currentPage,pageSize,pack,"0");
		int total = packDao.count(pack,"0");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("total", total);
		return map;
	}
	/**
	 * 加载已打包未封箱数据
	 */
	@Override
	public Map<String, Object> loadPackedData(int currentPage, int pageSize,
			Pack pack) {
		List<Pack> rows = packDao.loadPackData(currentPage,pageSize,pack,"5");
		int total = packDao.count(pack,"5");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("total", total);
		return map;
	}
	/**
	 * 根据客户和条码查询
	 */
	@Override
	public List<Pack> findByBarcode(Pack pack) {
		List<Pack> rows = packDao.findByBarcode(pack);
		return rows;
	}
	/**
	 * 打包确认
	 */
	@Override
	public String confirmPack(Pack pack) {
		int i = packDao.confirmPack(pack);
		if(i > 0){
			return "ok";
		}
		return "确认失败，请确认数量是否正确";
	}
	/**
	 * 取消打包
	 */
	@Override
	public String cancelPack(Pack pack) {
		int i = packDao.cancelPack(pack);
		if(i > 0){
			return "ok";
		}
		return "取消失败，联系管理员";
	}
	/**
	 * 获得新批次号
	 */
	@Override
	public String getBatchno() {
		String batchno = packDao.getBatchno();
		return batchno;
	}
	/**
	 * 封包
	 */
	@Override
	public Map<String, Object> addCaseid(Pack pack) {
		//获得批次号
		//循环处理每一条记录
		String[] ids = pack.getId().split(",");
		for (String id : ids ){
			//判断封包数量与总数量是否一致
			if(packDao.isEnough(id)){
				packDao.addCaseid(id);
			}else{
				
			}
		}
		//封包数量小于总数量时：新增一条未打包的记录，原记录数量改成与封包数量一致，并为该记录添加批次号
		return null;
	}
}
