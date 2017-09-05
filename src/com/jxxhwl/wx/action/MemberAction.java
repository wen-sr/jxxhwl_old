package com.jxxhwl.wx.action;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.wx.entity.AccessToken;
import com.jxxhwl.wx.entity.SNSUserInfo;
import com.jxxhwl.wx.entity.UserInfo;
import com.jxxhwl.wx.entity.WeixinOauth2Token;
import com.jxxhwl.wx.test.TestLog;
import com.jxxhwl.wx.util.WeiXinUtil;
@Controller
@Scope("prototype")
public class MemberAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(TestLog.class);
	//网页授权code
	private String code;
	//网页授权信息对象
	private WeixinOauth2Token token;
	//网页授权获得的用户信息
	private SNSUserInfo userInfo;
	//用户的基本信息
	private UserInfo wxuser;
	public UserInfo getWxuser() {
		return wxuser;
	}
	public void setWxuser(UserInfo wxuser) {
		this.wxuser = wxuser;
	}
	public SNSUserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(SNSUserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getCode() {
		return code;
	}
	public WeixinOauth2Token getToken() {
		return token;
	}
	public void setToken(WeixinOauth2Token token) {
		this.token = token;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void getOauthCode() throws IOException{
		String redirectUrl="http://www.jx56.com/jxxhwl/wx/member_login";
		String url = WeiXinUtil.getRequestCodeUrl(redirectUrl);
		response.sendRedirect(url);
	}
	/**
	 * 员工登陆
	 * @return
	 * @throws IOException 
	 */
	public String login() throws IOException{
		token = WeiXinUtil.getOauth2AccessToken(code);
		userInfo = WeiXinUtil.getSNSUserInfo(token.getAccessToken(), token.getOpenId());
		AccessToken token2 = WeiXinUtil.getAccessToken();
		wxuser = WeiXinUtil.getUserInfo(token2.getToken(), userInfo.getOpenId(), "zh_CN");
		if("0".equals(wxuser.getGroupid())){
			return "loginFailure";
		}
		return "loginSuccess";
	}
}
