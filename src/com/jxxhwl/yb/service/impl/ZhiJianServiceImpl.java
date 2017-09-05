package com.jxxhwl.yb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.yb.dao.ZhiJianDao;
import com.jxxhwl.yb.entity.ZhiJian;
import com.jxxhwl.yb.service.ZhiJianService;

@Service
@Transactional
public class ZhiJianServiceImpl implements ZhiJianService {

	@Autowired ZhiJianDao zhiJianDao;
	@Override 
	public List<ZhiJian> loadData() {
		List<ZhiJian> list = zhiJianDao.loadData(); 
		return list;
	}
	@Override
	public String add(ZhiJian zhiJian) {
		int i = zhiJianDao.add(zhiJian);
		if(i > 0) {
			return "ok";
		}
		return "添加失败";
	}
	
	@Override
	public String delete(String id) {
		int i = zhiJianDao.delete(id);
		if(i > 0) {
			return "ok";
		}
		return "删除失败";
	}
}
