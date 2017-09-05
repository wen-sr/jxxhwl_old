package com.jxxhwl.wx.service;

import com.jxxhwl.wx.entity.UserInfo;

public interface WXUserInfoService {
	
	/**
	 * save userinfo
	 * @param u
	 */
	void save(UserInfo u);
	/**
	 * delete user
	 * @param fromUserName
	 */
	void deleteByOpenid(String fromUserName);
	
	UserInfo findByOpenid(String openid);
	/**
	 * 工号绑定
	 * @param userInfo
	 * @return
	 */
	String subscribe(UserInfo userInfo);
}
