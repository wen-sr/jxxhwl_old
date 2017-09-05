package com.jxxhwl.wx.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jxxhwl.wx.action.WXDispatcher;

public class TestDispatcher {
	@Test
	public void testDispatcher(){
		String conf = "/applicationContext-annotation.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		WXDispatcher d = (WXDispatcher) ac.getBean("wXDispatcher");
		d.executePerDay();
	}
}
