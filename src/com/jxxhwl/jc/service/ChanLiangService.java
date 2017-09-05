package com.jxxhwl.jc.service;

import java.util.Map;

import com.jxxhwl.jc.entity.ChanLiang;

public interface ChanLiangService {
	/**
	 * 查询已登记信息
	 * @param currentPage
	 * @param pageSize
	 * @param dd
	 * @return
	 */
	Map<String, Object> loadData(int currentPage, int pageSize, String dd);
	/**
	 * 添加
	 * @param bZChuHuo
	 * @return
	 */
	String add(ChanLiang cl);
	/**
	 * 修改
	 * @param bZChuHuo
	 * @return
	 */
	String edit(ChanLiang cl);
	/**
	 * 获取楼下汇总
	 * @param issue
	 * @return
	 */
	int getLouxia(String issue);
}
