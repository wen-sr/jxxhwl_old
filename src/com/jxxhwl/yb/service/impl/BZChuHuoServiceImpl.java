package com.jxxhwl.yb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.wx.action.WXDispatcher;
import com.jxxhwl.wx.util.WeiXinUtil;
import com.jxxhwl.yb.dao.BZChuHuoDao;
import com.jxxhwl.yb.entity.BZChuHuo;
import com.jxxhwl.yb.service.BZChuHuoService;

@Service("bZChuHuoService")
@Transactional
public class BZChuHuoServiceImpl implements BZChuHuoService{
	@Autowired BZChuHuoDao bZChuHuoDao;
	@Autowired WXDispatcher wXDispatcher;
	/**
	 * 查询已登记信息
	 */
	@Override
	public Map<String, Object> loadData(int currentPage, int pageSize, String dd) {
		List<BZChuHuo> list = bZChuHuoDao.loadData(currentPage,pageSize,dd);
		int total = bZChuHuoDao.getTotal(dd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 添加
	 */
	@Override
	public String add(BZChuHuo bZChuHuo) {
		int i = bZChuHuoDao.add(bZChuHuo);
		if(i > 0 ){
			return "信息保存成功";
		}
		return "保存失败，联系管理员";
	}
	/**
	 * 修改
	 */
	@Override
	public String edit(BZChuHuo bZChuHuo) {
		int i = bZChuHuoDao.edit(bZChuHuo);
		if(i > 0 ){
			return "信息修改成功";
		}
		return "修改失败，联系管理员";
	}
	
}
