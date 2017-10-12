package com.jxxhwl.wx.service;

import java.util.List;

import com.jxxhwl.jc.entity.ChanLiang;

public interface WXDispatcherService {

	public String getContent();

	public String getChuHuo();
	
	public List<ChanLiang> getJiaoCai();

    List<ChanLiang> getOther();
}
