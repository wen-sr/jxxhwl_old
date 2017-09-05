package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Receipt;

public interface ReceiptService {
	/**
	 * 保存
	 * @param receipt
	 */
	public String save(Receipt receipt);

	/**
	 * 加载数据
	 * @param pageSize
	 * @param currentPage
	 * @param receipt 
	 * @return
	 */
	public Map<String, Object> loadData(int pageSize, int currentPage, Receipt receipt);

	/**
	 * 修改
	 * @param receipt
	 * @return
	 */
	public String update(Receipt receipt);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public String delete(String id);
	/**
	 * 选择未收货征订代码
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
