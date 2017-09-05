package com.jxxhwl.wx.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jxxhwl.wx.dao.GetShouFaDao;
import com.jxxhwl.wx.entity.FaHuo;
import com.jxxhwl.wx.entity.QueryShouFa;
import com.jxxhwl.wx.entity.ShouHuo;

public class TestGetShouFaDao {
	@Test
	public void testGetShouFaDao(){
		String conf = "/applicationContext-annotation.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		GetShouFaDao dao = (GetShouFaDao) ac.getBean("getShouFaDao");
//		GetFaDAOImplAdd dao = (GetFaDAOImplAdd) ac.getBean("getFaDAOImplAdd");
		QueryShouFa queryShouFa = new QueryShouFa();
		queryShouFa.setBegin("20161005");
		queryShouFa.setEnd("201661012");
//		queryShouFa.setCode("361832-5");
		queryShouFa.setCode("ÎÄ»¯");
		List<FaHuo> list = dao.getFa(queryShouFa);
//		List<ShouHuo> list = dao.getShou(queryShouFa);
		for(FaHuo sh : list){
			System.out.println(sh.getCarno());
		}
//		for (ShouHuo sh : list ){
//			System.out.println(sh.getCustomer());
//		}
	}
}
