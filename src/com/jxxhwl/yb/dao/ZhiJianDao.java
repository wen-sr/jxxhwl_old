package com.jxxhwl.yb.dao;

import java.util.List;

import com.jxxhwl.yb.entity.ZhiJian;

public interface ZhiJianDao {
	public List<ZhiJian> loadData();

	public int add(ZhiJian zhiJian);

	public int delete(String id);
}
