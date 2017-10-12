package com.jxxhwl.wx.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxxhwl.jc.entity.ChanLiang;
import com.jxxhwl.wx.dao.GetShouFaDao;
import com.jxxhwl.wx.dao.WXDispatcherDao;
import com.jxxhwl.wx.entity.ShouToday;
import com.jxxhwl.wx.service.WXDispatcherService;
@Service("wXDispatcherService")
public class WXDispatcherServiceImpl implements WXDispatcherService{
	@Resource
	private WXDispatcherDao wXDispatcherDao;
	@Autowired GetShouFaDao getShouFaDao;
	/**
	 * 收货信息
	 */
	@Override
	public String getContent() {
		List<ShouToday> list = getShouFaDao.getShouToday();
		int sumqty = 0;
		String content = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(new Date());
		int yb = 0;
		int dzz = 0;
		int gp = 0;
		int njsw = 0;
		int brxx = 0 ;
		int ybt = 0;
		int dzzt = 0;
		int qt = 0;
		for(ShouToday s : list ){
			sumqty += s.getQty();
			if("01".equals(s.getType())){
				yb += s.getQty();
			}else if("02".equals(s.getType())){
				dzz += s.getQty();
			}else if("03".equals(s.getType())){
				gp += s.getQty();
			}else if("04".equals(s.getType())){
				njsw += s.getQty();
			}else if("05".equals(s.getType())){
				brxx += s.getQty();
			}else if("06".equals(s.getType())){
				ybt += s.getQty();
			}else if("07".equals(s.getType())){
				dzzt += s.getQty();
			}else if("08".equals(s.getType())){
				qt += s.getQty();
			}
		}
//		if(sumqty > 0){
//			content = "4号库今日总收货：" + sumqty + "件";
//		}
//		if(yb > 0 || dzz > 0 || gp > 0 || njsw > 0 || brxx > 0 || ybt > 0 || dzzt > 0 || qt > 0 ){
//			content += "其中";
//		}
//		if(yb > 0 ){
//			content += "一般图书" + yb + "件，";
//		}
//		if(dzz > 0 ){
//			content += "大中专" + dzz + "件，";
//		}
//		if(gp > 0 ){
//			content += "馆配" + gp + "件，";
//		}
//		if(njsw > 0 ){
//			content += "农家书屋" + njsw + "件，";
//		}
//		if(brxx > 0 ){
//			content += "薄弱学校" + brxx + "件，";
//		}
//		if(ybt > 0 ){
//			content += "一般图书退货" + ybt + "件，";
//		}
//		if(dzzt > 0 ){
//			content += "大中专退货" + dzzt + "件，";
//		}
//		if(qt > 0 ){
//			content += "其他" + qt + "件，";
//		}
//		if(content.endsWith("，")){
//			content = content.substring(0, content.length() -1 );
//			content += "。";
//		}
		return String.valueOf(sumqty);
	}
	
	/**
	 * 出货
	 */
	@Override
	public String getChuHuo() {
		int i = wXDispatcherDao.getChuHuo();
		
		return String.valueOf(i);
	} 
	/**
	 * 教材
	 */
	@Override
	public List<ChanLiang> getJiaoCai() {
		List<ChanLiang> list = wXDispatcherDao.getJiaoCai();
		return list;
	}
	@Override
	public List<ChanLiang> getOther() {
		List<ChanLiang> list = wXDispatcherDao.getOther();
		return list;
	}
}
