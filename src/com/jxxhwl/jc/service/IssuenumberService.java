package com.jxxhwl.jc.service;

import java.util.List;

import com.jxxhwl.jc.entity.Issuenumber;

public interface IssuenumberService {

	/**
	 * 获取期号信息
	 * @return
	 */
	public List<Issuenumber> getAll();

	/**
	 * 保存期号信息
	 * @param issuenumber
	 */
	public int save(Issuenumber issuenumber);

	/**
	 * 删除期号信息
	 * @param id
	 */
	public int delete(String id);
	/**
	 * 获取简称
	 * @return
	 */
	public List<Issuenumber> getIssue();

}
