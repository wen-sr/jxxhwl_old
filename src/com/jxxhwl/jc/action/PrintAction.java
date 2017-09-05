package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Distribution;
import com.jxxhwl.jc.service.PrintService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class PrintAction extends BaseAction implements ModelDriven<Distribution> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Distribution distribution = new Distribution();
	@Override
	public Distribution getModel() {
		return distribution;
	}
	@Resource
	private PrintService printService;
	
	/**
	 * 加载需要打印的批次号
	 * @return
	 */
	public String loadBatchno(){
		List<Distribution> list = printService.loadBatchno();
		ActionContext.getContext().getValueStack().set("list", list);
		return "loadBatchno";
	}
	/**
	 * 加载需要打印的拣货流水号
	 * @return
	 */
	public String loadPickno(){
		List<Distribution> list = printService.loadPickno();
		ActionContext.getContext().getValueStack().set("list", list);
		return "loadPickno";
	}
	/**
	 * 整件发运清单
	 * @return
	 */
	public String wholeCaseList(){
		Map<String,Object> map = printService.wholeCaseList(distribution.getBatchno());
		ActionContext.getContext().getValueStack().set("map", map);
		return "wholeCaseList";
	}
	/**
	 * 整件发运清单过度页面
	 * @return
	 */
	public String wholeCaseListMid(){
		ActionContext.getContext().getValueStack().set("batchno", distribution.getBatchno());
		return "wholeCaseListMid";
	}
	/**
	 * 调拨单过度页面
	 * @return
	 */
	public String allocationListMid(){
		ActionContext.getContext().getValueStack().set("batchno", distribution.getBatchno());
		return "allocationListMid";
	}
	/**
	 * 调拨单页面
	 * @return
	 */
	public String allocationList(){
		Map<String,Object> map = printService.allocationList(distribution.getBatchno());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 票签过度
	 * @return
	 */
	public String s_ticketListMid(){
		ActionContext.getContext().getValueStack().set("batchno", distribution.getBatchno());
		return "s_ticketList";
	}
	/**
	 * 票签
	 * @return
	 */
	public String s_ticketList(){
		Map<String,Object> map = printService.s_ticketList(distribution.getBatchno());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 尾包票签
	 * @return
	 */
	public String t_ticketList(){
		Map<String,Object> map = printService.s_ticketList(distribution.getBatchno());
		ActionContext.getContext().getValueStack().set("map", map);
		return "t_ticketList";
	}
	
	/**
	 *  零件发运清单过度页面
	 * @return
	 */
	public String oddCaseListMid(){
		ActionContext.getContext().getValueStack().set("batchno", distribution.getBatchno());
		return "oddCaseListMid";
	}
	/**
	 * 零件发运清单
	 * @return
	 */
	public String oddCaseList(){
		Map<String, Object> map = printService.oddCaseList(distribution.getBatchno());
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 拼包票签
	 * @return
	 */
	public String oddTicketList(){
		Distribution d = printService.oddTicketList(distribution.getBatchno());
		ActionContext.getContext().getValueStack().set("d", d);
		return "oddTicketList";
	}
	/**
	 *  手拣单过度页面
	 * @return
	 */
	public String pickMid(){
		ActionContext.getContext().getValueStack().set("pickno", distribution.getPickno());
		return "pickMid";
	}
	/**
	 * 手拣单
	 * @return
	 */
	public String pick(){
		Map<String,Object> map = printService.pickList(distribution.getPickno());
		ActionContext.getContext().getValueStack().set("map", map);
		return "wholeCaseList";
	}
	/**
	 * 退货票签过度
	 * @return
	 */
	public String returnTicketMid(){
		ActionContext.getContext().getValueStack().set("subcode", distribution.getSubcode());
		return "returnTicketMid";
	}
	
	/**
	 * 退货票签
	 * @return
	 */
	public String returnTicket(){
		Distribution d = printService.returnTicket(distribution.getSubcode());
		ActionContext.getContext().getValueStack().set("result", d);
		return SUCCESS;
	}
}
