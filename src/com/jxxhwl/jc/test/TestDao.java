package com.jxxhwl.jc.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jxxhwl.jc.dao.SkuDao;
import com.jxxhwl.jc.entity.Sku;

public class TestDao {
	@Test
	public void test() throws Exception{
		String conf = "/applicationContext-annotation.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		
	}
}