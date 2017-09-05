package com.jxxhwl.yb.service;

import java.util.Map;

import com.jxxhwl.yb.entity.BZChuHuo;

public interface BZChuHuoService {
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
	String add(BZChuHuo bZChuHuo);
	/**
	 * 修改
	 * @param bZChuHuo
	 * @return
	 */
	String edit(BZChuHuo bZChuHuo);
	
}
