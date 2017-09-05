package com.jxxhwl.jc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.PackDao;
import com.jxxhwl.jc.dao.SkuDao;
import com.jxxhwl.jc.entity.Pack;
import com.jxxhwl.jc.service.PackService;

@Service
@Transactional
public class PackServiceImpl implements PackService {
	
	@Resource
	private PackDao packDao;
	/**
	 * 保存
	 */
	@Override
	public String save(Pack pack) {
		Pack pk = packDao.findPackByissuenumberAndSubcode(pack.getIssuenumber(),pack.getSubcode(),pack.getPack());
		if(pk != null){
			return "捆扎信息已存在";
		}
		Pack p = packDao.save(pack);
		if(p != null) {
			return "添加成功";
		}
		return "添加失败";
	}
	/**
	 * 查询
	 */
	@Override
	public List<Pack> query(Pack pack) {
		List<Pack> list = packDao.query(pack);
		return list;
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(Pack pack) {
		//query inventory 
		List<Pack> list = packDao.queryInventory(pack);
		if(list != null && list.size() > 0){
			return "当前捆扎存在库存，不能删除";
		}
		int i = packDao.delete(pack);
		if(i > 0){
			return "删除成功";
		}
		return "删除失败";
	}
	/**
	 * 更新
	 */
	@Override
	public String update(Pack pack) {
		int p = pack.getPack();
		int op = pack.getOldpack();
		pack.setPack(op);
		List<Pack> list = packDao.queryInventory(pack);
		pack.setPack(p);
		if(list != null && list.size() > 0){
//			//更新库存的捆扎
//			int i = packDao.updatePackInventory(pack);
//			if(i == 0){
//				return "更新库存的捆扎信息失败";
//			}
			return "该捆扎已有库存，不允许修改";
		}
		if(packDao.updatePack(pack)>0){
			//更新sku
			int i = packDao.updateSku(pack);
			if(i > 0) {
				return "捆扎更新成功";
			}else{
				return "捆扎更新失败";
			}
		}
		return "捆扎更新失败";
	}
}
