package com.jxxhwl.yb.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.yb.entity.Pack;

public interface PackService {
	/**
	 * 根据代码查询客户信息
	 * @param code
	 * @return
	 */
	List<Pack> findByCode(String code);
	/**
	 * 加载未打包数据
	 * @param currentPage
	 * @param pageSize
	 * @param pack
	 * @return
	 */
	Map<String, Object> loadUnpackData(int currentPage, int pageSize, Pack pack);
	/**
	 * 加载已打包未封箱数据
	 * @param currentPage
	 * @param pageSize
	 * @param pack
	 * @return
	 */
	Map<String, Object> loadPackedData(int currentPage, int pageSize, Pack pack);
	/**
	 * 根据条码和客户查询
	 * @param pack
	 * @return
	 */
	List<Pack> findByBarcode(Pack pack);
	/**
	 * 打包确认
	 * @param pack
	 * @return
	 */
	String confirmPack(Pack pack);
	/**
	 * 取消打包
	 * @param pack
	 * @return
	 */
	String cancelPack(Pack pack);
	/**
	 * 封包
	 * @param pack
	 * @return
	 */
	Map<String, Object> addCaseid(Pack pack);
	/**
	 * 获得新批次号
	 * @return
	 */
	String getBatchno();

}
