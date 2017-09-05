package com.jxxhwl.wx.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jxxhwl.wx.entity.UserInfo;
import com.jxxhwl.wx.service.WXDispatcherService;
import com.jxxhwl.wx.service.WXUserInfoService;

public class TestActionException {
	
	@Test
	public void testActionException() throws IOException{
		String conf = "/applicationContext-annotation.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
//		GetShouFaAction action = (GetShouFaAction) ac.getBean("getShouFaAction");
//		action.shou();
//		WXAction aa = (WXAction) ac.getBean("wXAction");
		WXDispatcherService aa = (WXDispatcherService) ac.getBean("wXDispatcherService");
		String s = aa.getContent();
		System.out.println(s);
	}
}
