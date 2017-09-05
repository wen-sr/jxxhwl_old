package com.jxxhwl.wx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.wx.dao.GetShouFaDao;
import com.jxxhwl.wx.entity.FaHuo;
import com.jxxhwl.wx.entity.QueryShouFa;
import com.jxxhwl.wx.entity.ShouHuo;
import com.jxxhwl.wx.entity.Storer;
import com.jxxhwl.wx.service.GetShouFaService;
@Transactional
@Service("getShouFaService")
public class GetShouFaServiceImpl implements GetShouFaService {
	//注入dao
	@Resource
	private GetShouFaDao getShouFaDao;
	public void setGetShouFaDao(GetShouFaDao getShouFaDao) {
		this.getShouFaDao = getShouFaDao;
	}
	/**
	 * 收货
	 */
	@Override
	public List<ShouHuo> getshou(QueryShouFa queryShouFa) {
		List<ShouHuo> list = getShouFaDao.getShou(queryShouFa);
		return list;
	}
	/**
	 * 发货
	 */
	@Override
	public List<FaHuo> getfa(QueryShouFa queryShouFa) {
		List<FaHuo> list = getShouFaDao.getFa(queryShouFa);
		return list;
	}
	/**
	 * 货主信息
	 */
	@Override
	public List<Storer> getStorer(String matchInfo) {
		List<Storer> list = getShouFaDao.getStorer(matchInfo);
		return list;
	}
}
