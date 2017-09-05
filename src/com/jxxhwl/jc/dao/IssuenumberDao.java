package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Issuenumber;

public interface IssuenumberDao {
	/**
	 * 保存期号信息
	 * @param i
	 * @return
	 */
	public int save(Issuenumber i);
	/**
	 * 删除期号信息
	 * @param id
	 */
	public int delete(String id);
	/**
	 * 获取期号信息
	 * @return
	 */
	public List<Issuenumber> getAll();
	/**
	 * 根据id查询期号信息
	 * @param id
	 * @return
	 */
	public Issuenumber findById(String id);
	/**
	 * 根据期号查询
	 * @param issuenumber
	 * @return
	 */
	public Issuenumber findByIssuenumber(String issuenumber);
	public List<Issuenumber> getIssue();
}
