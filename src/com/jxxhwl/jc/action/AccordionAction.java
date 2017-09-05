package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Accordion;
import com.jxxhwl.jc.service.AccordionService;
import com.opensymphony.xwork2.ActionContext;

//@Controller
//@Scope("prototype")
public class AccordionAction extends BaseAction{
	@Resource
	private AccordionService accordionService;
	public void setAccordionService(AccordionService accordionService) {
		this.accordionService = accordionService;
	}
	private Map<String,Accordion> map;
	public Map<String, Accordion> getMap() {
		return map;
	}
	public void setMap(Map<String, Accordion> map) {
		this.map = map;
	}
	/**
	 * 登录后的主页面
	 * @return
	 */
	public String main(){
		Map<String,Accordion> map = accordionService.getAccordionList();
		String name = (String) session.getAttribute("name");
		String id = (String) session.getAttribute("id");
		ActionContext.getContext().getValueStack().set("map", map);
		ActionContext.getContext().getValueStack().set("name", name);
		ActionContext.getContext().getValueStack().set("id", id);
		return "main";
	}
}
