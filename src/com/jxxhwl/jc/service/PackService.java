package com.jxxhwl.jc.service;

import java.util.List;

import com.jxxhwl.jc.entity.Pack;

public interface PackService {
	/**
	 * 保存
	 * @param pack
	 * @return
	 */
	public String save(Pack pack);
	/**
	 * 查询
	 * @param pack
	 * @return
	 */
	public List<Pack> query(Pack pack);
	/**
	 * 删除
	 * @param pack
	 * @return
	 */
	public String delete(Pack pack);
	/**
	 * 更新
	 * @param pack
	 * @return
	 */
	public String update(Pack pack);
	
}
