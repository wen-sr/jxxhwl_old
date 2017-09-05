package com.jxxhwl.common.action;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.common.entity.Accordion;
import com.jxxhwl.common.service.AccordionService;
import com.jxxhwl.jc.common.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class AccordionAction extends BaseAction{
	@Resource
	private AccordionService accordionService;
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
		String name = (String) session.getAttribute("name");
		String id = (String) session.getAttribute("id");
		Map<String,Accordion> map = accordionService.getAccordionList(id);
		ActionContext.getContext().getValueStack().set("map", map);
		ActionContext.getContext().getValueStack().set("name", name);
		ActionContext.getContext().getValueStack().set("id", id);
		
		return "main";
	}
}
