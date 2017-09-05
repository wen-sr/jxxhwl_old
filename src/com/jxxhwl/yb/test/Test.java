package com.jxxhwl.yb.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jxxhwl.wx.action.WXDispatcher;
import com.jxxhwl.wx.dao.WXDispatcherDao;
import com.jxxhwl.yb.service.BZChuHuoService;

public class Test {
	
	@org.junit.Test
	public void test(){
		String conf = "/applicationContext-annotation.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
//		WXDispatcherDao dao = (WXDispatcherDao) ac.getBean("wXDispatcherDao");
//		System.out.println(dao.getChuHuo());
		
		WXDispatcher wx = (WXDispatcher) ac.getBean("wXDispatcher");
		wx.executePerDay();
	}
}
