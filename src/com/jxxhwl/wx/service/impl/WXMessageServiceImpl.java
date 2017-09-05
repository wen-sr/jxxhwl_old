package com.jxxhwl.wx.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.wx.dao.WXMessageDao;
import com.jxxhwl.wx.entity.LocationMessage;
import com.jxxhwl.wx.entity.TextMessage;
import com.jxxhwl.wx.service.WXMessageService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
@Transactional
@Service
public class WXMessageServiceImpl implements WXMessageService {
	//ע��message��dao
	private WXMessageDao wXMessageDao;
	public void setwXMessageDao(WXMessageDao wXMessageDao) {
		this.wXMessageDao = wXMessageDao;
	}
	@Override
	public String print() {
		String s = wXMessageDao.print();
		return s;
	}
	
	/**
	 * xmlתΪmap����
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@Override
	public Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		org.dom4j.Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for (Element e : list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	/**
	 * ���ı���Ϣ����תΪxml
	 * @param textMessage
	 * @return
	 */
	@Override
	public String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/**
	 * 回复文本信息
	 */
	@Override
	public String getReturnMessage(String toUserName, String fromUserName,
			String content,String msgType) {
		TextMessage tm = new TextMessage();
		tm.setFromUserName(toUserName);
		tm.setToUserName(fromUserName);
		tm.setContent(content);
		tm.setCreateTime(new Date().getTime());
		tm.setMsgType(msgType);
		return textMessageToXml(tm);
	}
	/**
	 * ��װ�ظ�����λ����Ϣ
	 */
	@Override
	public String getReturnLocation(String toUserName, String fromUserName,
			String location_X, String location_Y, String scale, String label,
			String msgType) {
		LocationMessage lm = new LocationMessage();
		XStream xstream = new XStream();
		lm.setFromUserName(toUserName);
		lm.setToUserName(fromUserName);
		lm.setCreateTime(new Date().getTime());
		lm.setMsgType(msgType);
		lm.setLabel(label);
		lm.setLatitude(location_X);
		lm.setLongitude(location_Y);
		lm.setPrecision(scale);
		xstream.alias("xml", lm.getClass());
		return xstream.toXML(lm);
	}
}
