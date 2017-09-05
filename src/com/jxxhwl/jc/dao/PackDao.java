package com.jxxhwl.jc.dao;

import java.util.List;

import com.jxxhwl.jc.entity.Pack;

public interface PackDao {
	/**
	 * 保存
	 * @param pack
	 * @return
	 */
	public Pack save(Pack pack);
	/**
	 * 查询单个对象
	 * @param issuenumber
	 * @param subcode
	 * @param pack
	 * @return
	 */
	public Pack findPackByissuenumberAndSubcode(String issuenumber, String subcode,int pack);
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
	public int delete(Pack pack);
	/**
	 * 查询当前捆扎是否是否有库存
	 * @param pack
	 * @return
	 */
	public List<Pack> queryInventory(Pack pack);
	/**
	 * 更新库存的捆扎
	 * @param pack
	 * @return
	 */
	public int updatePackInventory(Pack pack);
	/**
	 * 更新捆扎
	 * @param pack
	 * @return
	 */
	public int updatePack(Pack pack);
	/**
	 * 更新skku的pack
	 * @param pack
	 * @return
	 */
	public int updateSku(Pack pack);
	
}
