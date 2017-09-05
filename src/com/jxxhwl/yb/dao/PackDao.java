package com.jxxhwl.yb.dao;

import java.util.List;

import com.jxxhwl.yb.entity.Pack;

public interface PackDao {
	/**
	 * 根据客户代码查询客户信息
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
	List<Pack> loadPackData(int currentPage, int pageSize, Pack pack,String status);
	/**
	 * 统计记录条数
	 * @param pack
	 * @param string
	 * @return
	 */
	int count(Pack pack, String string);
	/**
	 * 打包确认
	 * @param pack
	 * @return
	 */
	int confirmPack(Pack pack);
	
	List<Pack> findByBarcode(Pack pack);
	/**
	 * 取消打包
	 * @param pack
	 * @return
	 */
	int cancelPack(Pack pack);
	/**
	 * 获得新批次号
	 * @return
	 */
	String getBatchno();
	/**
	 * 判断当前封包数量是否与期望数量相等
	 * @param id
	 * @return
	 */
	boolean isEnough(String id);
	/**
	 * 封包
	 * @param id
	 * @return
	 */
	int addCaseid(String id);

}
