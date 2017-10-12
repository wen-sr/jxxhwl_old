package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.ChanLiang;

public interface ChanLiangDao {
	/**
	 * 查询已登记信息
	 * @param currentPage
	 * @param pageSize
	 * @param dd
	 * @return
	 */
	List<ChanLiang> loadData(int currentPage, int pageSize, String dd);
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
	int add(ChanLiang cl);
	/**
	 * 修改
	 * @param bZChuHuo
	 * @return
	 */
	int edit(ChanLiang cl);
	/**
	 * 获取楼下汇总
	 * @param issue
	 * @return
	 */
	int getLouxia(String issue);

    List<ChanLiang> loadOtherData(int currentPage, int pageSize, String dd);

	int addOther(ChanLiang cl);

	int editOther(ChanLiang cl);

	ChanLiang getOtherSum(String issue);
}
