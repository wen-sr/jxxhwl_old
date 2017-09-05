package com.jxxhwl.wx.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.jxxhwl.wx.test.TestLog;

/**
 * 异常信息记录到log文件中
 * @author Administrator
 *
 */
@Component
@Aspect
public class WXExceptionToLog {
	
	@Pointcut("within(com.jxxhwl.wx.action..*)")
	public void mypoint(){}
	
	public static Logger log = Logger.getLogger(TestLog.class);
	
	@AfterThrowing(pointcut="mypoint()",throwing="ex")
	public void exceptionToLogFile(Exception ex){
		log.error("=======异常信息=======");
		log.error("异常类型:"+ex);
		StackTraceElement[] els = ex.getStackTrace();
		for(StackTraceElement st : els){
			log.error(st);
		}
	}
}
