package com.jxxhwl.wx.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.wx.entity.AccessToken;
import com.jxxhwl.wx.entity.Template;
import com.jxxhwl.wx.entity.TemplateParam;
import com.jxxhwl.wx.entity.UserInfo;
import com.jxxhwl.wx.entity.WXObject;
import com.jxxhwl.wx.service.WXMessageService;
import com.jxxhwl.wx.service.WXObjectService;
import com.jxxhwl.wx.service.WXUserInfoService;
import com.jxxhwl.wx.test.TestLog;
import com.jxxhwl.wx.util.WeiXinUtil;
import com.opensymphony.xwork2.ModelDriven;
@Controller("wXAction")
@Scope("prototype")
public class WXAction extends BaseAction implements ModelDriven<WXObject>{
	public static Logger log = Logger.getLogger(TestLog.class);
	//模型驱动需要使用的对象
	private WXObject wXObject = new WXObject();
	@Override
	public WXObject getModel() {
		return wXObject;
	}
	//注入service
	@Resource
	private WXObjectService wXObjectService; 
	//注入message的service
	@Resource
	private WXMessageService wXMessageService;
	@Resource WXUserInfoService wxUserInfoService;
	/**
	 * 微信接入
	 * @return
	 * @throws IOException 
	 */
	public String wxAccess() throws IOException{
		wXObject.setToken(TOKEN);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if("GET".equals(request.getMethod())){
			wXObjectService.check(wXObject);
			if(wXObjectService.check(wXObject)){
	            out.print(wXObject.getEchostr());
			}
		}else if("POST".equals(request.getMethod())){
			try {
				Map<String,String>  map = wXMessageService.xmlToMap(request);
				String fromUserName = map.get("FromUserName");
				String toUserName = map.get("ToUserName");
				String msgType = map.get("MsgType");
				String content = map.get("Content");
				String message = "";
				if(MESSAGE_TEXT.equals(msgType)){
					if(content.indexOf("@") != -1){
						if(isSubscribe(fromUserName)){
							message = wXMessageService.getReturnMessage(toUserName,fromUserName,"您已经成功绑定，无需再次绑定",MESSAGE_TEXT);
						}else{
							String[] arr = content.split("@");
							String login_id = arr[0];
							String name = arr[1];
							UserInfo userinfo = new UserInfo(); 
							userinfo.setOpenId(fromUserName);
							userinfo.setLogin_id(login_id);
							userinfo.setNickname(name);
							content = wxUserInfoService.subscribe(userinfo);
							
							String token = WeiXinUtil.getCurrentAccessToken();
							UserInfo u =  WeiXinUtil.getUserInfo(token, fromUserName, "zh-CN");
							if("ok".equals(content)){
								//发送绑定成功的模板消息
								Template tem=new Template();  
								tem.setTemplateId("DYzLbq8MOWCimVg5MG2-qtmdm5RX7MqF2gAvwE9sIuQ");  
								tem.setTopColor("#00DD00");  
								tem.setToUser(fromUserName);  
								tem.setUrl("");  
								          
								List<TemplateParam> paras=new ArrayList<TemplateParam>();  
								paras.add(new TemplateParam("first","恭喜您，工号和微信账号绑定成功！","#FF3333"));  
								paras.add(new TemplateParam("keyword1",u.getNickname(),"#0044BB"));  
								paras.add(new TemplateParam("keyword2","江西蓝海物流" ,"#0044BB"));
								paras.add(new TemplateParam("keyword3","江西蓝海物流员工" ,"#0044BB"));
								paras.add(new TemplateParam("remark","很荣幸为您服务","#AAAAAA"));  
								          
								tem.setTemplateParamList(paras);  
								          
								WeiXinUtil.sendTemplateMsg(token,tem);
							}else{
								//发送绑定失败的模板消息
								Template tem=new Template();  
								tem.setTemplateId("TikJ2xrlkEXAzx-hBmZZbjfV9HHeOaaPy3vq4jdRpCI");  
								tem.setTopColor("#00DD00");  
								tem.setToUser(fromUserName);  
								tem.setUrl("");  
								          
								List<TemplateParam> paras=new ArrayList<TemplateParam>();  
								paras.add(new TemplateParam("first",content,"#FF3333"));  
								paras.add(new TemplateParam("keyword1",u.getNickname(),"#0044BB"));  
								paras.add(new TemplateParam("keyword2","请确认您的工号或姓名是否填写正确" ,"#0044BB"));
								paras.add(new TemplateParam("remark","很荣幸为您服务，您有任何疑问可以咨询我司客服","#AAAAAA"));  
								          
								tem.setTemplateParamList(paras);  
								          
								WeiXinUtil.sendTemplateMsg(token,tem);
							}
						}
						
					}else {
						//发送客服消息
					}
				}else if(MESSAGE_EVNET.equals(msgType)){
					//事件
					String event = map.get("Event");
					if(MESSAGE_SUBSCRIBE.equals(event)){
						//save user info
						String token = WeiXinUtil.getCurrentAccessToken();
						UserInfo u =  WeiXinUtil.getUserInfo(token, fromUserName, "zh-CN");
						if(null == wxUserInfoService.findByOpenid(fromUserName)){
							wxUserInfoService.save(u);
							log.warn("save user : " + u.getOpenId() + ", username :" + u.getNickname());
						}
						content = "您好，这里是江西蓝海物流科技有限公司官方微信服务号，欢迎您的关注，我们将竭诚为您提供服务";
						message = wXMessageService.getReturnMessage(toUserName, fromUserName, content, MESSAGE_TEXT);
					}else if(MESSAGE_UNSUBSCRIBE.equals(event)){
						//delete user info
						String token = WeiXinUtil.getCurrentAccessToken();
						UserInfo u =  wxUserInfoService.findByOpenid(fromUserName);
						wxUserInfoService.deleteByOpenid(fromUserName);
						log.warn("delete user : " + u.getOpenId() + ", username :" + u.getNickname());
					}else if(MESSAGE_CLICK.equals(event)){
						//点击事件
						String eventKey = map.get("EventKey");
						if("wdcl".equals(eventKey)){
							if(isSubscribe(fromUserName)){
								content = "你好，今天是" + new Date() + "，您的产量为：";
								message = wXMessageService.getReturnMessage(toUserName, fromUserName, content, MESSAGE_TEXT);
							}else{
								message = wXMessageService.getReturnMessage(toUserName, fromUserName, "您还未绑定工号，无法查询产量", MESSAGE_TEXT);
							}
						}if("ghbd".equals(eventKey)){
							content = "您好，本公司员工才能绑定工号，绑定工号非常简单，部分内部功能只有您绑定后才可以使用，请按照以下格式输入即可完成绑定：\"工号 @姓名\"，如\"LH8888888@王小二\"";
							message = wXMessageService.getReturnMessage(toUserName, fromUserName, content, MESSAGE_TEXT);
						}
					}
				}
				out.print(message);
			} catch (DocumentException e) {
				e.printStackTrace();
			} finally{
				out.close();
	            out = null;
			}
		}
		return null;
	}
	
	/**
	 * 判断是否已经绑定工号
	 * @return
	 */
	public boolean isSubscribe(String openid){
		UserInfo ui = wxUserInfoService.findByOpenid(openid);
		if(null != ui.getLogin_id()){
			return true;
		}
		return false;
	}
}
