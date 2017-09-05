package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Receipt;

public interface ReceiptDao {

	/**
	 * 保存
	 * @param receipt
	 */
	public Receipt save(Receipt receipt);

	/**
	 * 分页查询数据
	 * @param pageSize
	 * @param currentPage
	 * @param receipt 
	 * @return
	 */
	public List<Receipt> findByPage(int pageSize, int currentPage, Receipt receipt);
	/**
	 * 获得总记录条数
	 * @return
	 */
	public int getTotalCount();

	/**
	 * 修改
	 * @param receipt
	 */
	public int update(Receipt receipt);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Receipt findById(String id);
	/**
	 * 删除
	 * @param id
	 */
	public int delete(String id);
	/**
	 * 选择未收货正定代码
	 * @param receipt
	 * @return
	 */
	public List<Receipt> waitReceiptSubcodeData(Receipt receipt);
	/**
	 * 获得已收货的征订代码
	 * @param receipt
	 * @return
	 */
	public List<Receipt> loadReceiptSubcodeInfo(Receipt receipt);
}
