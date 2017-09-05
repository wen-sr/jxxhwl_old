package com.jxxhwl.wx.service;

import java.util.List;

import com.jxxhwl.wx.entity.FaHuo;
import com.jxxhwl.wx.entity.QueryShouFa;
import com.jxxhwl.wx.entity.ShouHuo;
import com.jxxhwl.wx.entity.Storer;

public interface GetShouFaService {

	List<ShouHuo> getshou(QueryShouFa queryShouFa);

	List<FaHuo> getfa(QueryShouFa queryShouFa);

	List<Storer> getStorer(String matchInfo);

}
