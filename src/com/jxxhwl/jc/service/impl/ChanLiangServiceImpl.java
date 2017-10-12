package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxxhwl.jc.dao.ChanLiangDao;
import com.jxxhwl.jc.entity.ChanLiang;
import com.jxxhwl.jc.service.ChanLiangService;
import com.jxxhwl.yb.entity.BZChuHuo;

@Service
public class ChanLiangServiceImpl implements ChanLiangService{
	
	@Autowired ChanLiangDao chanLiangDao;
	/**
	 * 查询
	 */
	@Override
	public Map<String, Object> loadData(int currentPage, int pageSize, String dd) {
		List<ChanLiang> list = chanLiangDao.loadData(currentPage,pageSize,dd);
		int total = chanLiangDao.getTotal(dd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}

	/**
	 * 查询
	 */
	@Override
	public Map<String, Object> loadOtherData(int currentPage, int pageSize, String dd) {
		List<ChanLiang> list = chanLiangDao.loadOtherData(currentPage,pageSize,dd);
		int total = chanLiangDao.getTotal(dd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}

	@Override
	public String add(ChanLiang cl) {
		int i = chanLiangDao.add(cl);
		if(i > 0 ){
			return "信息保存成功";
		}
		return "保存失败，联系管理员";
	}
	@Override
	public String addOther(ChanLiang cl) {
		int i = chanLiangDao.addOther(cl);
		if(i > 0 ){
			return "信息保存成功";
		}
		return "保存失败，联系管理员";
	}

	@Override
	public String edit(ChanLiang cl) {
		int i = chanLiangDao.edit(cl);
		if(i > 0 ){
			return "信息修改成功";
		}
		return "修改失败，联系管理员";
	}
	@Override
	public String editOther(ChanLiang cl) {
		int i = chanLiangDao.editOther(cl);
		if(i > 0 ){
			return "信息修改成功";
		}
		return "修改失败，联系管理员";
	}
	/**
	 * 获取楼下汇总
	 */
	@Override
	public int getLouxia(String issue) {
		return chanLiangDao.getLouxia(issue);
	}
	/**
	 * 获取楼下汇总
	 */
	@Override
	public ChanLiang getOtherSum(String issue) {
		return chanLiangDao.getOtherSum(issue);
	}

}
