package com.jxxhwl.wx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.wx.dao.WXUserInfoDao;
import com.jxxhwl.wx.entity.AccessToken;
import com.jxxhwl.wx.entity.Template;
import com.jxxhwl.wx.entity.TemplateParam;
import com.jxxhwl.wx.entity.UserInfo;
import com.jxxhwl.wx.service.WXUserInfoService;
import com.jxxhwl.wx.util.WeiXinUtil;

@Service("wxUserInfoService")
@Transactional
public class WXUserInfoServiceImpl implements WXUserInfoService {
	
	@Resource WXUserInfoDao wxUserInfoDao;
	/**
	 * save userinfo
	 */
	@Override
	public void save(UserInfo u) {
		wxUserInfoDao.save(u);
	}
	/**
	 * delete user
	 */
	@Override
	public void deleteByOpenid(String fromUserName) {
		wxUserInfoDao.deleteByOpenid(fromUserName);
	}
	/**
	 * find by openid 
	 */
	@Override
	public UserInfo findByOpenid(String openid) {
		return wxUserInfoDao.findByOpenid(openid);
	}
	/**
	 * 工号绑定
	 */
	@Override
	public String subscribe(UserInfo userInfo) {
		if(null != wxUserInfoDao.subscribe(userInfo)) {
			int i = wxUserInfoDao.saveSubscribe(userInfo);
			if(i > 0 ){
				return "ok";
			}else{
				return  "数据库后台保存失败，请联系管理员";
			}
		}else{
			return "对不起，绑定失败";
		}
	}
}
