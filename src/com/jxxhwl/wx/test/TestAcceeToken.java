package com.jxxhwl.wx.test;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.jxxhwl.wx.entity.AccessToken;
import com.jxxhwl.wx.util.WeiXinUtil;

public class TestAcceeToken {
	public static Logger log = Logger.getLogger(TestLog.class);
	
	@Test
	public void testAccessToken(){
		AccessToken token = WeiXinUtil.getAccessToken();
		System.out.println(token.getToken());
		System.out.println(token.getExpiresIn());
//		log.info("1234");
		//
		String menu = JSONObject.fromObject(WeiXinUtil.initMenu()).toString();
		int result = WeiXinUtil.createMenu(token.getToken(), menu);
		System.out.println(result);
		if(result == 0){
			System.out.println("创建成功");
		}else{
			System.out.println("创建失败");
		}
		//
//		System.out.println(WeiXinUtil.createUserGroup(token.getToken()));
		//
//		System.out.println(WeiXinUtil.getUserGroup(token.getToken()));
//		log.info(WeiXinUtil.getUserGroup(token.getToken()));
//		
		//ɾ���û�����
//		System.out.println(WeiXinUtil.deleteUserGroup(token.getToken()));
		//�޸��û�������Ϣ
//		System.out.println(WeiXinUtil.modifyUserGroupName(token.getToken()));
		//��ѯ�û����ڷ���
//		System.out.println(WeiXinUtil.findUserGroup(token.getToken()));
		//�ƶ��û�����
//		System.out.println(WeiXinUtil.moveUserToGroup(token.getToken()));
//		System.out.println(WeiXinUtil.moveUsersToGroup(token.getToken(), null));
//		System.out.println(WeiXinUtil.getAllUsers(token.getToken()));
		
//		System.out.println(WeiXinUtil.groupSendByOpenID(token.getToken()));
	}
}
