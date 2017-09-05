package com.jxxhwl.jc.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Pack;
import com.jxxhwl.jc.service.PackService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class PackAction extends BaseAction implements ModelDriven<Pack>{
	Pack pack = new Pack();
	@Override
	public Pack getModel() {
		return pack;
	}
	@Resource
	private PackService packService;
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		ok = packService.save(pack);
		return "ok";
	}
	/**
	 * 查询
	 * @return
	 */
	public String query() {
		List<Pack> list = packService.query(pack);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete (){
		ok = packService.delete(pack);
		return OK;
	}
	/**
	 * 更新
	 * @return
	 */
	public String updatePack(){
		ok = packService.update(pack);
		return OK;
	}
}
