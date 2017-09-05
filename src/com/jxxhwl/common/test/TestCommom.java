package com.jxxhwl.common.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jxxhwl.common.dao.RoleDao;

public class TestCommom {
	
	@Test
	public void test() throws Exception{
		String conf = "/applicationContext-annotation.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		RoleDao roleDao = (RoleDao) ac.getBean("roleService");
		System.out.println(roleDao.findFather(""));
	}
}
