package com.jxxhwl.wx.dao;

import java.util.List;

import com.jxxhwl.wx.entity.FaHuo;
import com.jxxhwl.wx.entity.QueryShouFa;
import com.jxxhwl.wx.entity.ShouHuo;
import com.jxxhwl.wx.entity.ShouToday;
import com.jxxhwl.wx.entity.Storer;

public interface GetShouFaDao {

	List<ShouHuo> getShou(QueryShouFa queryShouFa);

	List<FaHuo> getFa(QueryShouFa queryShouFa);

	List<Storer> getStorer(String matchInfo);

	List<ShouToday> getShouToday();
	
}
