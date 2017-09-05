package com.jxxhwl.jc.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestTransaction {
	
	@Test
	public void test() throws Exception{
		String conf = "/applicationContext-annotation.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		TestTransactionService s = (TestTransactionService) ac.getBean("testTransactionService");
		s.save();
	}
}
