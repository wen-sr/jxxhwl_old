package com.jxxhwl.jc.dao;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Storer;

public interface StorerDao {
	/**
	 * 获取客户信息
	 * @return
	 */
	public List<Storer> getCustomer();
	/**
	 * 获取供应商信息
	 * @return
	 */
	public List<Storer> getSupplier();
	/**
	 * 根据代码查询客户信息
	 * @param storerkey
	 * @return
	 */
	public Storer findByStorerkey(String storerkey);
	/**
	 * 获取所有信息
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Storer> getInfo(int currentPage, int pageSize);
	/**
	 * 统计记录总条数
	 * @return
	 */
	public int countTotal();
	/**
	 * 保存
	 * @param storer
	 * @return
	 */
	public int save(Storer storer);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(String id);
	/**
	 * 根据storerkey查询
	 * @param storerkey
	 * @return
	 */
	public List<Storer> queryByStorerkey(String storerkey);
	/**
	 * 根据简称查询
	 * @param shortname
	 * @return
	 */
	public List<Storer> queryByShortname(String shortname);
	/**
	 * 根据type查询
	 * @param type
	 * @param pageSize 
	 * @param currentPage 
	 * @return
	 */
	public List<Storer> queryByType(String type, int currentPage, int pageSize);
	/**
	 * 统计总条数
	 * @param type
	 * @return
	 */
	public int countTotal(String type);
	/**
	 * 修改
	 * @param storer
	 * @return
	 */
	public int edit(Storer storer);
}
