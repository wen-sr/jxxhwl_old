package com.jxxhwl.jc.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.jc.dao.ComputeDao;

@Service
@Transactional
public class TestTransactionService {
	
	@Resource
	private TestTransactionDao testTransactionDao;
	public void setTestTransactionDao(TestTransactionDao testTransactionDao) {
		this.testTransactionDao = testTransactionDao;
	}
	@Resource
	private TestTransactionDao2 testTransactionDao2;
	public void setTestTransactionDao2(TestTransactionDao2 testTransactionDao2) {
		this.testTransactionDao2 = testTransactionDao2;
	}


	public void save(){
		testTransactionDao.save("aa");
		int i = 1/0;
		testTransactionDao2.save("bb");
	}
}
