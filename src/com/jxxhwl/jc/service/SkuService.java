package com.jxxhwl.jc.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.jc.entity.Sku;

public interface SkuService {
	/**
	 * 查询所有图书资料
	 * @return
	 */
	public List<Sku> findAll();

	/**
	 * 保存图书信息
	 * @param sku
	 * @return
	 */
	public String save(Sku sku);

	/**
	 * 删除图书信息
	 * @param id
	 * @return
	 */
	public String delete(String id);
	/**
	 * 分页查询
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Map<String,Object> findAllByPage(int pageSize, int currentPage);

	/**
	 * 修改
	 * @param sku
	 * @return
	 */
	public String update(Sku sku);
	/**
	 * 根据征订代码和期号查询
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Sku findByIssuenumberAndSubcode(String issuenumber, String subcode);
	/**
	 * 查询
	 * @param sku
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> query(Sku sku, int pageSize, int currentPage);
	/**
	 * 获取征订代码
	 * @return
	 */
	public List<Sku> getSubcode();

	public String saveReturnSku(Sku sku);
	
}
