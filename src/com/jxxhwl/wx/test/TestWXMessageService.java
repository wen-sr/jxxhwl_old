package com.jxxhwl.wx.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jxxhwl.wx.service.WXMessageService;

public class TestWXMessageService {
	@Test
	public void testService (){
		String conf = "/applicationContext.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		WXMessageService ws = (WXMessageService) ac.getBean("wXMessageService");
		System.out.println(ws.getReturnLocation("a", "b", "125", "56", "225", "25", "5562"));
	}
}	
