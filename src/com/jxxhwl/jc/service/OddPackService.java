package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Storer;

public interface OddPackService {
	/**
	 * 加载需要打包的客户
	 * @param string
	 * @return
	 */
	public List<Storer> loadCustomer();
	/**
	 * 加载未刷信息
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param code
	 * @return
	 */
	public Map<String, Object> loadPackData(int pageSize, int currentPage,
			String issuenumber, String code, String status);
	/**
	 * 扫条码查询打包信息
	 * @param issuenumber
	 * @param code
	 * @param barcode
	 * @return
	 */
	public List<Distribution> findByBarcode(String issuenumber, String code,
			String barcode);
	/**
	 * 确认打包信息
	 * @param addwho 
	 * @param issuenumber
	 * @return
	 */
	public String confirmPack(String id, String addwho);
	/**
	 * 取消打包信息
	 * @param ids
	 * @return
	 */
	public String cancelPack(String[] ids);
	/**
	 * 添加批次
	 * @param ids
	 * @return
	 */
	public Map<String,Object> addBatchno(String[] ids);

}
