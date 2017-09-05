package com.jxxhwl.jc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.IssuenumberDao;
import com.jxxhwl.jc.entity.Issuenumber;
import com.jxxhwl.jc.service.IssuenumberService;

@Service
@Transactional
public class IssuenumberServiceImpl implements IssuenumberService {

	@Resource
	private IssuenumberDao issuenumberDao;
	/**
	 * 获取期号信息
	 */
	@Override
	public List<Issuenumber> getAll() {
		return issuenumberDao.getAll();
	}
	/**
	 * 保存期号信息
	 */
	@Override
	public int save(Issuenumber issuenumber) {
		return issuenumberDao.save(issuenumber);
		
	}
	/**
	 * 删除期号信息
	 */
	@Override
	public int delete(String id) {
		return issuenumberDao.delete(id);
	}
	/**
	 * 获取简称
	 */
	@Override
	public List<Issuenumber> getIssue() {
		List<Issuenumber> list = issuenumberDao.getIssue();
		return list;
	}
}
