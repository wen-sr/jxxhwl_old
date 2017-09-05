package com.jxxhwl.common.service;

import java.util.Map;

import com.jxxhwl.common.entity.Accordion;


public interface AccordionService {
	public Map<String,Accordion> getAccordionList(String id);
}
