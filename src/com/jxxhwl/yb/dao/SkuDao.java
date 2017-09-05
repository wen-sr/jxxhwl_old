package com.jxxhwl.yb.dao;

import java.util.List;

import com.jxxhwl.yb.entity.ZhiJian;


public interface SkuDao {
	public List<ZhiJian> findByBarcode(ZhiJian zhiJian);
}
