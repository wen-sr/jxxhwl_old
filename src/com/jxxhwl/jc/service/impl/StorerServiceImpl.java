package com.jxxhwl.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.StorerDao;
import com.jxxhwl.jc.entity.Storer;
import com.jxxhwl.jc.service.StorerService;

@Service
@Transactional
public class StorerServiceImpl implements StorerService{
	@Resource
	private StorerDao storerDao;	
	/**
	 * 获得所有信息
	 */
	@Override
	public Map<String, Object> getInfo(int currentPage, int pageSize) {
		List<Storer> list = storerDao.getInfo(currentPage, pageSize);
		int total = storerDao.countTotal();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", total);
		return map;
	}
	/**
	 * 保存
	 */
	@Override
	public String save(Storer storer) {
		int i = storerDao.save(storer);
		if(i > 0) {
			return "添加成功";
		}
		return "添加失败";
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(String id) {
		int i = storerDao.delete(id);
		if(i > 0) {
			return "删除成功";
		}
		return "删除失败";
	}
	/**
	 * 修改
	 */
	@Override
	public String update(Storer storer) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查询
	 */
	@Override
	public Map<String, Object> queryInfo(Storer storer, int currentPage, int pageSize) {
		List<Storer> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if(!"".equals(storer.getStorerkey())) {
			list = storerDao.queryByStorerkey(storer.getStorerkey());
			map.put("rows", list);
			map.put("total", list.size());
			return map;
		}
		if(!"".equals(storer.getShortname()) && !"".equals(storer.getType())){
			list = storerDao.queryByShortname(storer.getShortname());
			map.put("rows", list);
			map.put("total", list.size());
			return map;
		}
		if(!"".equals(storer.getType())){
			list = storerDao.queryByType(storer.getType(),currentPage,pageSize);
			int i = storerDao.countTotal(storer.getType());
			map.put("rows", list);
			map.put("total", i);
			return map;
		}
		return null;
	}
	/**
	 * 修改
	 */
	@Override
	public String edit(Storer storer) {
		int i = storerDao.edit(storer);
		if(i > 0){
			return "修改成功";
		}
		return "修改失败";
	}
}
