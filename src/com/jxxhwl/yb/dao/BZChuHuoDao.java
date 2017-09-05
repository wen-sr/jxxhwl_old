package com.jxxhwl.yb.dao;

import java.util.List;

import com.jxxhwl.yb.entity.BZChuHuo;

public interface BZChuHuoDao {
	/**
	 * 查询已登记信息
	 * @param currentPage
	 * @param pageSize
	 * @param dd
	 * @return
	 */
	List<BZChuHuo> loadData(int currentPage, int pageSize, String dd);
	/**
	 * 统计总条数
	 * @param dd
	 * @return
	 */
	int getTotal(String dd);
	/**
	 * 保存
	 * @param bZChuHuo
	 * @return
	 */
	int add(BZChuHuo bZChuHuo);
	/**
	 * 修改
	 * @param bZChuHuo
	 * @return
	 */
	int edit(BZChuHuo bZChuHuo);

}
