package com.jxxhwl.jc.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.jxxhwl.wx.test.TestLog;

/**
 * 异常信息记录log文件
 * @author Administrator
 *
 */
@Component
@Aspect
public class JCExceptionToLog {
	
	@Pointcut("within(com.jxxhwl.jc.service..*)")
	public void mypoint(){}
	
	public static Logger log = Logger.getLogger(TestLog.class);
	
	@AfterThrowing(pointcut="mypoint()",throwing="ex")
	public void exceptionToLogFile(Exception ex){
		log.error("=======异常信息=======");
		log.error("异常类型:"+ex);
		StackTraceElement[] els = ex.getStackTrace();
		log.error(els[0]);
	}
}
