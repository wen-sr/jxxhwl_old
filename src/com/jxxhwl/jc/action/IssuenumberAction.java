package com.jxxhwl.jc.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Issuenumber;
import com.jxxhwl.jc.service.IssuenumberService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class IssuenumberAction extends BaseAction implements ModelDriven<Issuenumber>{
	/**
	 * 模型驱动
	 */
	Issuenumber issuenumber = new Issuenumber();
	@Override
	public Issuenumber getModel() {
		return issuenumber;
	}
	@Resource
	private IssuenumberService issuenumberService;
	/**
	 * 查询期号信息
	 * @return
	 */
	public String info(){
		List<Issuenumber> list = issuenumberService.getAll();
		ActionContext.getContext().getValueStack().set("list", list);
		return "info";
	}
	/**
	 * 保存期号信息
	 * @return
	 */
	public String save(){
		if(issuenumberService.save(issuenumber) > 0){
			ok = "ok";
		}else {
			ok = "no";
		}
		return "save";
	}
	/**
	 * 删除期号信息
	 * @return
	 */
	public String delete(){
		if(issuenumberService.delete(issuenumber.getId())>0){
			ok = "ok";
		}else {
			ok = "no";
		}
		return "delete";
	}
	/**
	 * 查询期号简称
	 */
	public String getIssue() {
		List<Issuenumber> list = issuenumberService.getIssue();
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	
}
