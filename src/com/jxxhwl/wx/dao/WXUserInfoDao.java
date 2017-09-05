package com.jxxhwl.wx.dao;

import java.util.List;

import com.jxxhwl.wx.entity.UserInfo;

public interface WXUserInfoDao {
	/**
	 * save userinfo
	 * @param u
	 */
	void save(UserInfo u);
	/**
	 * delete 
	 * @param openId
	 */
	void deleteByOpenid(String openId);
	/**
	 * find by openid 
	 * @param openid
	 * @return
	 */
	UserInfo findByOpenid(String openid);
	/**
	 * 工号绑定
	 * @param userInfo
	 * @return
	 */
	UserInfo subscribe(UserInfo userInfo);
	/**
	 * 保存绑定信息
	 * @param u
	 * @return
	 */
	int saveSubscribe(UserInfo u);
	/**
	 * 获得所有已绑定工号的员工
	 * @return
	 */
	List<String> getAllMyUsers();
	/**
	 * 获得所有已绑定工号的主管
	 * @return
	 */
	List<String> getAllSupervisor();
	/**
	 * 根据工号查询
	 * @param s
	 * @return
	 */
	List<String> getUsersById(String s);

}
