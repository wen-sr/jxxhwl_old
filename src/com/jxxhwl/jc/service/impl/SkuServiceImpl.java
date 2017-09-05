package com.jxxhwl.jc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.common.PageBean;
import com.jxxhwl.jc.dao.IssuenumberDao;
import com.jxxhwl.jc.dao.SkuDao;
import com.jxxhwl.jc.dao.StorerDao;
import com.jxxhwl.jc.entity.Issuenumber;
import com.jxxhwl.jc.entity.Pack;
import com.jxxhwl.jc.entity.Sku;
import com.jxxhwl.jc.entity.Storer;
import com.jxxhwl.jc.service.PackService;
import com.jxxhwl.jc.service.SkuService;
import com.jxxhwl.jc.util.ValidateBasicInfo;
@Service
@Transactional
public class SkuServiceImpl implements SkuService{

	@Resource
	private SkuDao skuDao;
	@Resource
	private IssuenumberDao issuenumberDao;
	@Resource
	private StorerDao storerDao;
	@Resource
	private ValidateBasicInfo validateBasicInfo;
	@Resource
	private PackService packService;
	/**
	 * 查询图书信息
	 */
	@Override
	public List<Sku> findAll() {
		return skuDao.findAll();
	}
	/**
	 * 保存
	 */
	@Override
	public String save(Sku sku) {
		Issuenumber i = issuenumberDao.findByIssuenumber(sku.getIssuenumber());
		if(i == null){
			return "期号信息不存在";
		}
		if(sku.getId() != null && !"".equals(sku.getId())){
			if(skuDao.findById(sku.getId()) != null ){
				if(skuDao.updateIandSandP(sku) > 0){
					return "更新书号信息成功";
				}else{
					return "更新书号信息失败";
				}
			}
		}
		Sku s = skuDao.findByIssuenumberAndSubcode(sku.getIssuenumber(), sku.getSubcode());
		if(s != null ){
			return "书号信息已存在";
		}
		Storer publisher = storerDao.findByStorerkey(sku.getPublisher());
		if(publisher == null ){
			return "出版社信息不存在";
		}
		if(sku.getPack()>0){
			Pack pack = new Pack(sku.getIssuenumber(), sku.getSubcode(), sku.getPack());
			packService.save(pack);
		}
		Sku ss = skuDao.save(sku);
		if( ss.getId() == null ){
			return "保存信息失败";
		}
		return "保存信息成功";
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(String id) {
		Sku sku = skuDao.findById(id);
		if(sku.getPack() > 0){
			return "该信息已维护，不能删除";
		}
		int i = skuDao.delete(id);
		if( i > 0){
			return "删除信息成功";
		}
		return "删除信息失败";
	}
	/**
	 * 分页查询
	 */
	@Override
	public Map<String,Object> findAllByPage(int pageSize, int currentPage) {
		PageBean<Sku> pageBean = new PageBean<Sku>();
		//封装当前页
		pageBean.setCurrentPage(currentPage);
		//封装每页条数
		pageBean.setPageSize(pageSize);
		//封装记录总条数
		int totalCount = skuDao.getTotalCount();
		pageBean.setTotalCount(totalCount);
		//封装总页数
		double tc = totalCount;
		Double totalPage = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(totalPage.intValue());
		//封装需要显示的数据信息
		List<Sku> list = skuDao.findAllByPage(pageSize, currentPage);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("total", totalCount);
		map.put("rows", list);
		return map;
	}
	/**
	 * 修改
	 */
	@Override
	public String update(Sku sku) {
		if(!validateBasicInfo.isExistCustomer(sku.getPublisher())){
			return "出版社信息不存在";
		}
		if(!validateBasicInfo.isExistIssuenumber(sku.getIssuenumber())){
			return "期号信息不存在";
		}
		if(sku.getPack()>0){
			Pack pack = new Pack(sku.getIssuenumber(), sku.getSubcode(), sku.getPack());
			packService.save(pack);
		}
		int i = skuDao.update(sku);
		if(i > 0) {
			return "修改信息成功";
		}
		return "修改信息失败";
	}
	/**
	 * 根据征订代码和期号查询
	 */
	@Override
	public Sku findByIssuenumberAndSubcode(String issuenumber, String subcode) {
		return skuDao.findByIssuenumberAndSubcode(issuenumber, subcode);
	}
	/**
	 * 查询
	 */
	@Override
	public Map<String, Object> query(Sku sku, int pageSize, int currentPage) {
		List<Sku> list = new ArrayList<Sku>();
		Map<String, Object> map = new HashMap<String, Object>();
		list = skuDao.query(sku,currentPage, pageSize);
		map.put("rows", list);
		map.put("total", skuDao.countTotal(sku));
		return map;
	}
	/**
	 * 获取征订代码
	 */
	@Override
	public List<Sku> getSubcode() {
		List<Sku> list = skuDao.findAll();
		return list;
	}
	
	@Override
	public String saveReturnSku(Sku sku) {
		int i = skuDao.saveReturnSku(sku);
		if(i > 0 ){
			return "添加成功";
		}
		return "添加失败";
	}
}
