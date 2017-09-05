package com.jxxhwl.common.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.common.dao.AccordionDao;
import com.jxxhwl.common.entity.Accordion;
import com.jxxhwl.common.service.AccordionService;


@Service
@Transactional
public class AccordionServiceImpl implements AccordionService{

	@Resource
	private AccordionDao accordionDao;
	/**
	 * 获得菜单列表
	 */
	@Override
	public Map<String,Accordion> getAccordionList(String id){
		List<Accordion> all = accordionDao.list(id);
		Map<String,Accordion> map = new LinkedHashMap<String, Accordion>();;
		for (Accordion a : all) {
			if(a.getFoo_id() == 0){
				map.put(String.valueOf(a.getAccordion_id()), a);
			}
		}
		for (Accordion a : all ){
			String foo_id = String.valueOf(a.getFoo_id());
			if(!"0".equals(foo_id)){
				if(map.get(foo_id).getAccordions() == null){
					List<Accordion> foo_list = new ArrayList<Accordion>();
					foo_list.add(a);
					map.get(foo_id).setAccordions(foo_list);
				}else {
					map.get(foo_id).getAccordions().add(a);
				}
			}
			
		}
		
		return map;
	}
	
}
