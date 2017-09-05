package com.jxxhwl.wx.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.wx.dao.WXUserInfoDao;
import com.jxxhwl.wx.util.WeiXinUtil;

@Controller("wXFunctionAction")
@Scope("prototype")
public class WXFunctionAction extends BaseAction {
	
	private String content;
	private String member;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	@Resource WXUserInfoDao wxUserInfoDao;
	@Autowired WXDispatcher wxDispatcher;
	/**
	 * 发送通知
	 * @return
	 */
	public String sendInform(){
		String[] members = member.split(",");
		List<String> list = new ArrayList<String>();
		List<String> users = null;
		for(String s : members){
			list.add(s);
		}
		String token = WeiXinUtil.getCurrentAccessToken();
		if(list.contains("01")){
			//获得所有已绑定工号的员工
			users = wxUserInfoDao.getAllMyUsers();
		}else if(list.contains("02")){
			users = wxUserInfoDao.getAllSupervisor();
		}else{
			String s = "'";
			for(String ss : list){
				s = s + ss + "','";  
			}
			s = s.substring(0, s.length()-2);
			users = wxUserInfoDao.getUsersById(s);
		}
		for(String s : users ){
			WeiXinUtil.sendInform2(token, s, content, "新华物流", "");
		}
		ok = "发送通知成功";
		return OK;
	}
	/**
	 * 发送产量
	 * @return
	 */
	public String sendChanLiang(){
		wxDispatcher.executePerDay();
		ok = "发送通知成功";
		return OK;
	}
}
