package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Sku;

public interface SkuDao {
	/**
	 * 保存书号信息
	 * @param sku
	 * @return
	 */
	public Sku save(Sku sku);
	/**
	 * 根据正定代码和期号查询图书信息
	 * @param issuenumber
	 * @param subcode
	 * @return
	 */
	public Sku findByIssuenumberAndSubcode(String issuenumber, String subcode);
	/**
	 * 查询所有图书资料
	 * @return
	 */
	public List<Sku> findAll();
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete (String id);
	/**
	 * 分页查询
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<Sku> findAllByPage(int pageSize,int currentPage);
	/**
	 * 查询总条数
	 * @return
	 */
	public int getTotalCount();
	/**
	 * 根据id修改
	 * @param sku
	 */
	public int update(Sku sku);
	/**
	 * 根据期号和征订代码修改
	 * @param s
	 */
	public int updateByIssuenumberAndSubcode(Sku s);
	/**
	 * 按条件查询
	 * @param sku
	 * @param pageSize 
	 * @param currentPage 
	 * @return
	 */
	public List<Sku> query(Sku sku, int currentPage, int pageSize);
	/**
	 * 按条件统计
	 * @param sku
	 * @return
	 */
	public int countTotal(Sku sku);
	/**
	 * 更新期号，征订代码，出版社等业务信息
	 * @param sku
	 * @return
	 */
	public int updateIandSandP(Sku sku);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public Sku findById(String id);
	public int saveReturnSku(Sku sku);
	
}
