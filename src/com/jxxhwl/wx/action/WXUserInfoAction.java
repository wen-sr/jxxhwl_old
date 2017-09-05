package com.jxxhwl.wx.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.wx.entity.AccessToken;
import com.jxxhwl.wx.entity.Template;
import com.jxxhwl.wx.entity.TemplateParam;
import com.jxxhwl.wx.entity.UserInfo;
import com.jxxhwl.wx.service.WXUserInfoService;
import com.jxxhwl.wx.util.WeiXinUtil;
import com.opensymphony.xwork2.ModelDriven;

@Controller("wXUserInfoAction")
@Scope("prototype")
public class WXUserInfoAction extends BaseAction implements ModelDriven<UserInfo>{
	UserInfo userInfo = new UserInfo();
	@Override
	public UserInfo getModel() {
		return userInfo;
	}
	@Autowired WXUserInfoService wxUserInfoService;
	/**
	 * 员工工号绑定
	 * @return
	 */
	public String subscribe(){
		ok = wxUserInfoService.subscribe(userInfo);
		//指定输出内容类型和编码  
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        //获取输出流，然后使用  
        try {
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			//输出文本信息  
            out.print("<span style='font-size:20px'>温馨提示：" + ok ); 
            out.print("<br/>");
            out.print("<a href='history.go(-1)' style='text-decoration: none;'>返回</a>");
            out.flush();
            out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
		return OK;
	} 
	
}
