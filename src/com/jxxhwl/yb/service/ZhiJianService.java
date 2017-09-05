package com.jxxhwl.yb.service;

import java.util.List;
import java.util.Map;

import com.jxxhwl.yb.entity.ZhiJian;

public interface ZhiJianService {
	public List<ZhiJian> loadData();

	public String add(ZhiJian zhiJian);

	public String delete(String id);
}
