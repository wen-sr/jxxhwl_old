package com.jxxhwl.wx.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.jxxhwl.wx.entity.AccessToken;
import com.jxxhwl.wx.entity.Template;
import com.jxxhwl.wx.entity.TemplateParam;
import com.jxxhwl.wx.util.WeiXinUtil;

public class TestTemplateMessage {
	@Test
	public void test(){
		AccessToken token = WeiXinUtil.getAccessToken();
		Template tem=new Template();  
		tem.setTemplateId("j3uSiEeYMGG1UrMmU_AZlIJ_xwdTChiCGkKCpbTr2sk");  
		tem.setTopColor("#00DD00");  
		tem.setToUser("oPOAgvx1Utuu0Mg25QTPs5yqDUyw");  
		tem.setUrl("");  
		          
		List<TemplateParam> paras=new ArrayList<TemplateParam>();  
		paras.add(new TemplateParam("first","新华物流产量报告：2017年2月24日新华物流出货流水：发货1069件；退货40件；手工单退货20件,手工发货0件；大中专70件。合计：1199件。包装组产量：一般图书发货30830册,810件。一般图书退货132册,2件。手单发货0册，0件。合计:30962册812件。够包3132册,259件. 够包退货：1824册，38件。够包合计:4956册,297件。","#FF3333"));  
		paras.add(new TemplateParam("keyword1","新华物流","#0044BB"));  
		paras.add(new TemplateParam("keyword2",(new Date().toLocaleString()) ,"#0044BB"));
		paras.add(new TemplateParam("remark","很荣幸为您服务","#AAAAAA"));  
		          
		tem.setTemplateParamList(paras);  
		          
		boolean result=WeiXinUtil.sendTemplateMsg(token.getToken(),tem);  
	}
}
