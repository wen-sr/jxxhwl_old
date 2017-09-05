package com.jxxhwl.wx.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jxxhwl.wx.dao.WXMessageDao;

public class TestMessageDao {
	@Test
	public void testMessageDao(){
		String conf = "/applicationContext.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		WXMessageDao dao = (WXMessageDao) ac.getBean("wXMessageDao");
		String s = dao.print();
		System.out.println(s);
	}
}
