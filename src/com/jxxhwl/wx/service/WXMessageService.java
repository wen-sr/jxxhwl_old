package com.jxxhwl.wx.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;

import com.jxxhwl.wx.entity.TextMessage;

public interface WXMessageService {

	public String print();
	public Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException;
	public String textMessageToXml(TextMessage textMessage);
	public String getReturnMessage(String toUserName, String fromUserName,String content, String msgType);
	public String getReturnLocation(String toUserName, String fromUserName,String location_X, String location_Y, String scale, String label, String msgType );
}
