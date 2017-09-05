package com.jxxhwl.wx.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.jxxhwl.wx.util.Constants;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements Constants,ServletRequestAware,ServletResponseAware,ApplicationAware{

	protected HttpServletRequest request;//请求对象
	protected HttpServletResponse response;//相应对象
	protected HttpSession session;//会话对象
	public static Map<String, Object> application = new HashMap<String, Object>();//全局对象
	public final String OK = "ok";
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		this.session=request.getSession();
	}
	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
		
	}
	
	/**
	 * 返回页面的信息
	 */
	public String ok;
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	/**
	 * 分页信息
	 */
	public String page;
	public String rows;
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
}

