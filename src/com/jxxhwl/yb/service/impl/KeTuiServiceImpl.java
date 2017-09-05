package com.jxxhwl.yb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.yb.dao.KeTuiDao;
import com.jxxhwl.yb.entity.KeTui;
import com.jxxhwl.yb.service.KetuiService;

@Service
@Transactional
public class KeTuiServiceImpl implements KetuiService {

	@Autowired KeTuiDao keTuiDao;
	@Override
	public KeTui getBingX(String boxno) {
		return keTuiDao.getBingX(boxno);
	}

}
