package com.jxxhwl.wx.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jxxhwl.jc.entity.ChanLiang;
import com.jxxhwl.wx.entity.AccessToken;
import com.jxxhwl.wx.entity.Template;
import com.jxxhwl.wx.entity.TemplateParam;
import com.jxxhwl.wx.service.WXDispatcherService;
import com.jxxhwl.wx.test.TestLog;
import com.jxxhwl.wx.util.Constants;
import com.jxxhwl.wx.util.WeiXinUtil;

/**
 * 定时任务
 * @author Administrator
 *
 */
@Component("wXDispatcher")
public class WXDispatcher{
	public static Logger log = Logger.getLogger(TestLog.class);
	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		WXDispatcher.log = log;
	}
	//	public static Map<String, Object> application = new HashMap<String, Object>();
	//注入service
	@Resource
	private WXDispatcherService wXDispatcherService;

	/**
	 * 定时执行的方法
	 */
//	@Scheduled(cron="0 0 17 * * ?") 
//	@Scheduled(cron="0/30 * * * * ?") 
	public String executePerDay(){
		String token = WeiXinUtil.getCurrentAccessToken();
		//收货
		String shouhuo = wXDispatcherService.getContent();
		//出货
		String chuhuo = wXDispatcherService.getChuHuo();
		//教材
		List<ChanLiang> jiaocai = wXDispatcherService.getJiaoCai();
		
		String content = "";
		if( (shouhuo != null && !"0".equals(shouhuo)) || (chuhuo != null && !"0".equals(chuhuo)) || (jiaocai != null && jiaocai.size() > 0)  ){
			content = "新华物流今日工作产量：";
			if((shouhuo != null && !"0".equals(shouhuo))){
				content += "4号库总收货：" + shouhuo + "件";
			}
			if((chuhuo != null && !"0".equals(chuhuo))){
				if(content.length() != 9){
					content += "；总出货：" + chuhuo + "件";
				}else{
					content += "总出货：" + chuhuo + "件";
				}
			}
			if(jiaocai != null && jiaocai.size() > 0){
				if(content.length() != 9){
					content += "；教材：";
				}else{
					content += "教材：";
				}
				for(ChanLiang c : jiaocai ){
					content += c.getIssue() + ":" + "至今总出票:" + c.getHeJi() + "件，其中代发" + c.getDaiFa() +"件;"; 
				}
			}
		}
		
		if("" != content){
			WeiXinUtil.sendInform(token, "oPOAgvx1Utuu0Mg25QTPs5yqDUyw", content, "新华物流", new Date().toLocaleString());
			WeiXinUtil.sendInform(token, "oPOAgv3R6Hw-BQSWj1GA9p-qH2Js", content, "新华物流", new Date().toLocaleString());
			WeiXinUtil.sendInform(token, "oPOAgv9wGB8tArvCQpr4OHr3wXBE", content, "新华物流", new Date().toLocaleString());
			WeiXinUtil.sendInform(token, "oPOAgv1qFSTPPYPuy_E6J0ZmrdBk", content, "新华物流", new Date().toLocaleString());
		}
		return "success";
	}
	//每两个小时刷新token
	@Scheduled(cron="0 0 */2 * * ?")
//	@Scheduled(cron="0 0/1 * * * ?")
	public void getAccessToken(){
		WXAction.application.put("access_token", WeiXinUtil.getAccessToken().getToken());
		log.warn("get accessToken " + new Date().toLocaleString() + ":" + WXAction.application.get("access_token"));
	}
}
