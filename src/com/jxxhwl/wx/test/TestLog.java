package com.jxxhwl.wx.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;



public class TestLog {
	public static Logger log = Logger.getLogger(TestLog.class);
	@Test
	public void testLog(){
		try {
//			int i = 2/0;
//			char c[] = "0a问bc".toCharArray();
//			for (char ch : c){
//				System.out.println(ch);
//			}
//			System.out.println(c[0]);
//			System.out.println(Character.isDigit(c[0]));
//			System.out.println(Character.isLetter(c[2]));
//			int i = (int)c[2];
			//判断是否为中文
//			System.out.println((i>=19968 && i<=171941 ));
			
//			String textContent ="abctest　";
//			textContent = textContent.trim();
//			while (textContent.startsWith("　")) {//这里判断是不是全角空格
//				textContent = textContent.substring(1, textContent.length()).trim();
//			}
//			while (textContent.endsWith("　")) {
//				textContent = textContent.substring(0, textContent.length() - 1).trim();
//			}
//			System.out.println(textContent); 
			//获取系统时间
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			String content ="产量"; 
			String cl = "产量";
			if(content != null && content.indexOf(cl) >= 0 ){
				content = "您的当前的产量：拆包20件，上线1000册。";
			}else {
				content = "你好，这里是江西新华物流，您发送的消息是:" + content + "，我们将马上为您处理";
			}
			System.out.println(content);
		} catch (Exception e) {
			log.error("=======异常信息=======");
			log.error("异常类型:"+e);
			StackTraceElement[] els = e.getStackTrace();
			log.error(els[0]);
		}
	}
}
