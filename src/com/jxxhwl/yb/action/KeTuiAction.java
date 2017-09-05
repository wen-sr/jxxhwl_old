package com.jxxhwl.yb.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.yb.common.BaseAction;
import com.jxxhwl.yb.entity.KeTui;
import com.jxxhwl.yb.service.KetuiService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class KeTuiAction extends BaseAction implements ModelDriven<KeTui>{
	KeTui keTui = new KeTui();
	@Override
	public KeTui getModel() {
		return keTui;
	}
	@Autowired KetuiService ketuiService;
	public String bingXcha(){
		keTui = ketuiService.getBingX(keTui.getBoxno());
		ActionContext.getContext().getValueStack().set("result", keTui);
		return SUCCESS;
	}
}
