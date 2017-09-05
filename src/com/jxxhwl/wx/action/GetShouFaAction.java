package com.jxxhwl.wx.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.wx.entity.FaHuo;
import com.jxxhwl.wx.entity.QueryShouFa;
import com.jxxhwl.wx.entity.ShouHuo;
import com.jxxhwl.wx.entity.Storer;
import com.jxxhwl.wx.service.GetShouFaService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
public class GetShouFaAction extends BaseAction implements ModelDriven<QueryShouFa>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ģ��������Ҫʹ�õĶ���
	QueryShouFa queryShouFa = new QueryShouFa();
	@Override
	public QueryShouFa getModel() {
		return queryShouFa;
	}
	//ע��service
	@Autowired
	private GetShouFaService getShouFaService;
	public void setGetShouFaService(GetShouFaService getShouFaService) {
		this.getShouFaService = getShouFaService;
	}
	//ajax��ȡ������Ϣ�Ĺؼ���
	private String matchInfo;
	public String getMatchInfo() {
		return matchInfo;
	}
	public void setMatchInfo(String matchInfo) {
		this.matchInfo = matchInfo;
	}
	//�ջ�list
//	private List<ShouHuo> list;
//	public List<ShouHuo> getList() {
//		return list;
//	}
//	public void setList(List<ShouHuo> list) {
//		this.list = list;
//	}
	/**
	 * �ջ���ѯ
	 * @return
	 */
	public String shou(){
		if(queryShouFa != null){
			if("0".equals(queryShouFa.getType())){
				List<ShouHuo> list =getShouFaService.getshou(queryShouFa);
				ActionContext.getContext().getValueStack().set("list", list);
				return "shou";
			}else if("1".equals(queryShouFa.getType())){
				fa();
				return "fa";
			}
		}
		return null;
	}
	/**
	 * ������ѯ
	 * @return
	 */
	public String fa(){
		List<FaHuo> list =getShouFaService.getfa(queryShouFa);
		ActionContext.getContext().getValueStack().set("list", list);
		return "fa";
	}
	/**
	 * ��û�����Ϣ
	 * @return
	 */
	public String storer(){
		Storer s = new Storer();
		matchInfo = matchInfo.trim();
		while (matchInfo.startsWith("��")) {//�����ж��ǲ���ȫ�ǿո�
			matchInfo = matchInfo.substring(1, matchInfo.length()).trim();
		}
		while (matchInfo.endsWith("��")) {
			matchInfo = matchInfo.substring(0, matchInfo.length() - 1).trim();
		}
		if("".equals(matchInfo)){
			return "storer";
		}
		List<Storer> list = getShouFaService.getStorer(matchInfo);
		ActionContext.getContext().getValueStack().set("list", list);
		return "storer";
	}
}
