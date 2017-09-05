package com.jxxhwl.jc.common;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
@Component
public class AuthorityInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
		Object user = ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user != null ){
			return ai.invoke();
		}else{
			return "reLogin";
		}
	}

}
