package com.jxxhwl.jc.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.jc.common.BaseAction;
import com.jxxhwl.jc.entity.Receipt;
import com.jxxhwl.jc.service.ReceiptService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
public class ReceiptAction extends BaseAction implements ModelDriven<Receipt>{

	/**
	 * 模型驱动
	 */
	private Receipt receipt = new Receipt(); 
	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}
	@Override
	public Receipt getModel() {
		return receipt;
	}
	@Resource
	private ReceiptService receiptService;
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		ok = receiptService.save(receipt);
		return OK;
	}
	/**
	 * 加载数据
	 * @return
	 */
	public String loadData(){
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "20":rows);
		Map<String,Object> map = receiptService.loadData(pageSize,currentPage,receipt);
		ActionContext.getContext().getValueStack().set("result", map);
		return SUCCESS;
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		ok = receiptService.update(receipt);
		return OK;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		ok = receiptService.delete(receipt.getId());
		return OK;
	}
	/**
	 * 选择未收货征订代码
	 * @return
	 */
	public String waitReceiptSubcodeData(){
		List<Receipt> list = receiptService.waitReceiptSubcodeData(receipt);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 选择已收货的征订代码
	 * @return
	 */
	public String loadReceiptSubcodeInfo(){
		List<Receipt> list = receiptService.loadReceiptSubcodeInfo(receipt);
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}

}
