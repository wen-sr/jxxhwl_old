package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Storer;

public interface StorerService {
	/**
	 * 获取所有信息
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getInfo(int currentPage, int pageSize);
	/**
	 * 保存
	 * @param storer
	 * @return
	 */
	String save(Storer storer);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	String delete(String id);
	/**
	 * 修改
	 * @param storer
	 * @return
	 */
	String update(Storer storer);
	/**
	 * 查询
	 * @param storer
	 * @param pageSize 
	 * @param currentPage 
	 * @return
	 */
	public Map<String, Object> queryInfo(Storer storer, int currentPage, int pageSize);
	/**
	 * 修改
	 * @param storer
	 * @return
	 */
	public String edit(Storer storer);
	
}
