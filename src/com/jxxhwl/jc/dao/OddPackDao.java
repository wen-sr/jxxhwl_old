package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.entity.Storer;

public interface OddPackDao {
	/**
	 * 加载需要打包的客户
	 * @param s
	 * @return
	 */
	public List<Storer> loadCustomer();
	/**
	 * 根据状态加载刷书信息
	 * @param pageSize
	 * @param currentPage
	 * @param issuenumber
	 * @param code
	 * @return
	 */
	public List<Distribution> loadPackData(int pageSize, int currentPage,
			String issuenumber, String code, String status);
	/**
	 * 根据状态统计已刷信息
	 * @param issuenumber
	 * @param code
	 * @return
	 */
	public int CountTotal(String issuenumber, String code, String status);
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
	public int confirmPack(String id, String addwho);
	/**
	 * 取消打包信息
	 * @param id
	 * @return
	 */
	public int cancelPack(String id);
	/**
	 * 获得新批次号
	 * @return
	 */
	public String getBatchno();
	/**
	 * 获得新运号
	 * @return
	 */
	public String getShipno();
	/**
	 * 添加批次
	 * @param id
	 * @param batchno
	 * @param shipno
	 * @return
	 */
	public int addBatchno(String id, String batchno, String shipno);

}
