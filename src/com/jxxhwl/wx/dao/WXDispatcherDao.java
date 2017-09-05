package com.jxxhwl.wx.dao;

import java.util.List;

import com.jxxhwl.jc.entity.ChanLiang;
import com.jxxhwl.wx.entity.ShouToday;

public interface WXDispatcherDao {
	public List<ShouToday> getShouToday();

	public int getChuHuo();

	public List<ChanLiang> getJiaoCai();
}
