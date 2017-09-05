package com.jxxhwl.wx.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.jxxhwl.wx.action.WXAction;
import com.jxxhwl.wx.entity.AccessToken;
import com.jxxhwl.wx.entity.Button;
import com.jxxhwl.wx.entity.ClickButton;
import com.jxxhwl.wx.entity.Group;
import com.jxxhwl.wx.entity.Menu;
import com.jxxhwl.wx.entity.SNSUserInfo;
import com.jxxhwl.wx.entity.Template;
import com.jxxhwl.wx.entity.TemplateParam;
import com.jxxhwl.wx.entity.UserGroup;
import com.jxxhwl.wx.entity.UserInfo;
import com.jxxhwl.wx.entity.ViewButton;
import com.jxxhwl.wx.entity.WeixinOauth2Token;
import com.jxxhwl.wx.test.TestLog;

/**
 * 工具类
 * @author Administrator
 *
 */
public class WeiXinUtil {
	public static Logger log = Logger.getLogger(TestLog.class);
//	private static final String APPID="wxa77d1ad158f5eef7";
//	private static final String APPSECRET = "3fadea5fae3f2aa98fe146ad9ed5d99e";d4385e0a24f0b9428957581f4be0e8ea
	private static final String APPID="wx5993aae7e1692dd7";
	private static final String APPSECRET = "d4385e0a24f0b9428957581f4be0e8ea";
	private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String CREATE_USERGROUP="https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	private static final String GET_WX_USERINFO="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	private static final String GET_USERGROUP="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	private static final String DELETE_USERGROUP="https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
	private static final String MODIFY_USERGROUP_NAME="https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	private static final String MOVE_USER_TO_GROUP="https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	private static final String MOVE_USERS_TO_GROUP="https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";
	private static final String FIND_USER_GROUP="https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	private static final String GET_ALL_USERS="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	private static final String GROUP_SEND_BY_OPENID="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	private static final String TEMPLAGE_MESSAGE="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	private static final String SERVICE_MESSAGE="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		Logger logger = Logger.getLogger(WeiXinUtil.class);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity =  httpResponse.getEntity();
			if(httpEntity != null){
				String result = EntityUtils.toString(httpEntity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * post请求
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject doPostStr(String url, String outStr){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		Logger logger = Logger.getLogger(WeiXinUtil.class);
		try {
			httpost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response = client.execute(httpost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * 获得新accesstoken
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	/**
	 * 获得当前access_token
	 * @return
	 */
	public static String getCurrentAccessToken(){
		String token = (String) WXAction.application.get("access_token");
		if(token == null ){
			token = WeiXinUtil.getAccessToken().getToken();
			WXAction.application.put("access_token",token);
		}
		return token;
	}
	
	
	/**
	 * 初始化菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ViewButton shoufa = new ViewButton();
		shoufa.setName("新华物流收发货");
		shoufa.setType("view");
		shoufa.setUrl("http://www.jx56.com/jxxhwl/wx/shoufa.action");
		
		ViewButton whp = new ViewButton();
		whp.setName("危化品运输");
		whp.setType("view");
		whp.setUrl("http://www.jx56.com/jxxhwl/wx/shoufa.action");
		
		ViewButton xhky = new ViewButton();
		xhky.setName("新华快运");
		xhky.setType("view");
		xhky.setUrl("http://www.jx56.com/jxxhwl/wx/shoufa.action");
		
		Button ywcx = new Button();
		ywcx.setName("业务查询");
		ywcx.setSub_button(new Button[]{xhky,whp,shoufa});
		
		ClickButton yw = new ClickButton();
		yw.setName("业务");
		yw.setType("click");
		yw.setKey("yw");
		
		Button ywbl = new Button();
		ywbl.setName("业务办理");
		ywbl.setSub_button(new Button[]{yw});
		
		
		
		ViewButton yijian = new ViewButton();
		yijian.setType("view");
		yijian.setName("意见反馈");
		yijian.setUrl("http://www.jx56.com/jxxhwl/wx/advice.action");
		
		ClickButton kf = new ClickButton();
		kf.setName("联系客服");
		kf.setType("click");
		kf.setKey("kf");
		
		ClickButton yg = new ClickButton();
		yg.setName("工号绑定");
		yg.setType("click");
		yg.setKey("ghbd");
		
		Button aboutUs = new Button();
		aboutUs.setName("联系我们");
		aboutUs.setSub_button(new Button[]{kf,yijian,yg});
		
		menu.setButton(new Button[]{ywcx,ywbl,aboutUs});
		
		return menu;
	}
	/**
	 * 创建菜单
	 * @param token
	 * @param menu
	 * @return
	 */
	public static int createMenu(String token,String menu){
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url,menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	/**
	 *
	 * @param redirectUrl
	 * @return
	 */
	public static String getRequestCodeUrl(String redirectUrl){
		redirectUrl = URLEncoder.encode(redirectUrl);
		return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                APPID, redirectUrl, "snsapi_userinfo", "login");
	}
	
	/**
     * 微信授权登录
     * @param appId 
     * @param appSecret
     * @param code
     * @return WeixinAouth2Token
     */
    public static WeixinOauth2Token getOauth2AccessToken(String code) {
        WeixinOauth2Token wat = null;
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", APPID);
        requestUrl = requestUrl.replace("SECRET", APPSECRET);
        requestUrl = requestUrl.replace("CODE", code);
        JSONObject jsonObject = doGetStr(requestUrl);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("errcode:{} errmsg:{}"+  errorCode+ "," +  errorMsg);
            }
        }
        return wat;
    }
    
    /**
     * 
     * @param accessToken
     * @param openId
     * @return SNSUserInfo
     */
    @SuppressWarnings( { "deprecation", "unchecked" })
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        JSONObject jsonObject = doGetStr(requestUrl);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // �û��ı�ʶ
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // �ǳ�
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // �Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
                snsUserInfo.setSex(jsonObject.getInt("sex"));
                // �û����ڹ��
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // �û�����ʡ��
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // �û����ڳ���
                snsUserInfo.setCity(jsonObject.getString("city"));
                // �û�ͷ��
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // �û���Ȩ��Ϣ
                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("errcode:{} errmsg:{}" + errorCode + "," + errorMsg);
            }
        }
        return snsUserInfo;
    }
	/**
	 * 获得用户信息
	 * @param token
	 * @param openId
	 * @param lang
	 * @return
	 */
    public static UserInfo getUserInfo(String token,String openId, String lang){
    	UserInfo userInfo = null;
    	String url = GET_WX_USERINFO.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
    	JSONObject jsonObject = doGetStr(url);
    	if(null != jsonObject){
    		userInfo = new UserInfo();
    		userInfo.setOpenId(jsonObject.getString("openid"));
    		userInfo.setNickname(jsonObject.getString("nickname"));
    		userInfo.setSex(jsonObject.getInt("sex"));
    		userInfo.setCountry(jsonObject.getString("country"));
    		userInfo.setProvince(jsonObject.getString("province"));
    		userInfo.setCity(jsonObject.getString("city"));
    		userInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
    		userInfo.setGroupid(jsonObject.getString("groupid"));
    		userInfo.setLanguage(jsonObject.getString("language"));
    		userInfo.setSubscribe(jsonObject.getInt("subscribe"));
    		userInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
    		userInfo.setRemark(jsonObject.getString("remark"));
    	}
    	return userInfo;
    }
    
	/**
	 * 创建用户分组
	 * @param token
	 * @return
	 */
	public static JSONObject createUserGroup (String token){
		String url = CREATE_USERGROUP.replace("ACCESS_TOKEN", token);
		UserGroup ug = new UserGroup();
		
		Group xh = new Group();
		xh.setName("xh");
		
		Group ld = new Group();
		ld.setName("ld");
		
		ug.setGroup(ld);
		String g = JSONObject.fromObject(ug).toString();
		
		String data = "{'group':{'name':'xhadmin'}}";
		JSONObject jsonObject = doPostStr(url, data);
		return jsonObject;
	}
	
	/**
	 * 获得用户所在组
	 * @param token
	 * @return
	 */
	public static JSONObject getUserGroup(String token){
		String url = GET_USERGROUP.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	/**
	 * 修改用户所在组
	 * @param token
	 * @return
	 */
	public static JSONObject modifyUserGroupName(String token){
//		String data = "{\"group\":{\"id\":100,\"name\":\"admin\"}}";
//		String data = "{\"group\":{\"id\":101,\"name\":\"xinhua\"}}";
//		String data = "{\"group\":{\"id\":102,\"name\":\"customer\"}}";
//		String data = "{\"group\":{\"id\":103,\"name\":\"supplier\"}}";
		String data = "{\"group\":{\"id\":105,\"name\":\"partner\"}}";
		String url = MODIFY_USERGROUP_NAME.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url,data);
		return jsonObject;
	}
	
	/**
	 * 删除分组
	 * @param token
	 * @param groupid
	 * @return
	 */
	public static JSONObject deleteUserGroup(String token,String groupid){
		String url = DELETE_USERGROUP.replace("ACCESS_TOKEN", token);
//		String data = "{\"group\":{\"id\":103}}";
		String data = "{\"group\":{\"id\":103}}";
		JSONObject jsonObject = doPostStr(url, data);
		return jsonObject;
	}
	/**
	 * 查询用户所在组
	 * @param token
	 * @return
	 */
	public static JSONObject findUserGroup(String token){
		String url = FIND_USER_GROUP.replace("ACCESS_TOKEN", token);
		String data = "{\"openid\":\"oQwDMwFq_l2XkR2SbqK-G6Lapd8U\"}";
		JSONObject jsonObject = doPostStr(url, data);
		return jsonObject;
	}
	/**
	 * 移动用户到某个分组
	 * @param token
	 * @return
	 */
	public static JSONObject moveUserToGroup(String token){
		String url = MOVE_USER_TO_GROUP.replace("ACCESS_TOKEN", token);
		String data = "{\"openid\":\"oQwDMwFq_l2XkR2SbqK-G6Lapd8U\",\"to_groupid\":100}";
		JSONObject jsonObject = doPostStr(url, data);
		return jsonObject; 
	}
	/**
	 * 批量移动用户到某个分组
	 * @param token
	 * @param list
	 * @return
	 */
	public static JSONObject moveUsersToGroup(String token,List<String> list){
		String url = MOVE_USERS_TO_GROUP.replace("ACCESS_TOKEN", token);
		String data = "{\"openid_list\":[\"oQwDMwFz49cW5lkVi3loqnhKhNe8\",\"oQwDMwLC-FHU2o30R_703eo-xldQ\"],\"to_groupid\":101}";
		JSONObject jsonObject = doPostStr(url, data);
		return jsonObject; 
	}
	/**
	 * 获得所有用户
	 * @param token
	 * @return
	 */
	public static JSONObject getAllUsers(String token) {
		String url = GET_ALL_USERS.replace("ACCESS_TOKEN", token).replace("NEXT_OPENID", "");
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	/**
	 * 向指定的openid发送消息
	 * @param token
	 * @return
	 */
	public static JSONObject groupSendByOpenID(String token,String content){
		String url = GROUP_SEND_BY_OPENID.replace("ACCESS_TOKEN", token);
//		String data = "{\"touser\":[\"oQwDMwFq_l2XkR2SbqK-G6Lapd8U\",\"oQwDMwD3s1XP2zgskzP2ZfsREuZQ\"],\"msgtype\": \"text\",\"text\": { \"content\": \"���ڵ�ʱ���ǣ�"+ (new Date()) +"�������Ǹ������Ӱ���\"}}";
//		String data = "{\"touser\":[\"oQwDMwFq_l2XkR2SbqK-G6Lapd8U\",\"oQwDMwD3s1XP2zgskzP2ZfsREuZQ\",\"oQwDMwFz49cW5lkVi3loqnhKhNe8\",\"oQwDMwLC-FHU2o30R_703eo-xldQ\"],\"msgtype\": \"text\",\"text\": { \"content\": \""+ content +"\"}}";
		String data = "{\"touser\":[\"oPOAgvx1Utuu0Mg25QTPs5yqDUyw\",\"oPOAgvx1Utuu0Mg25QTPs5yqDUyw\"],\"msgtype\": \"text\",\"text\": { \"content\": \""+ content +"\"}}";
		JSONObject jsonObject = doPostStr(url, data);
		return jsonObject;
	}
	/**
	 * 发送模板消息
	 * @param token
	 * @param template
	 * @return
	 */
	public static boolean sendTemplateMsg(String token, Template template) {

		boolean flag = false;

		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

		JSONObject jsonResult = doPostStr(requestUrl,template.toJSON());
		if (jsonResult != null) {
			int errorCode = jsonResult.getInt("errcode");
			String errorMessage = jsonResult.getString("errmsg");
			if (errorCode == 0) {
				flag = true;
			} else {
				log.error("模板消息发送失败:" + errorCode + "," + errorMessage);
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * 发送产量消息
	 */
	public static void sendInform(String token,String toUser, String first, String keyword1, String keyword2){
		Template tem=new Template();  
		tem.setTemplateId("j3uSiEeYMGG1UrMmU_AZlIJ_xwdTChiCGkKCpbTr2sk");  
		tem.setTopColor("#00DD00");  
		tem.setToUser(toUser);  
		tem.setUrl("");  
		          
		List<TemplateParam> paras=new ArrayList<TemplateParam>();  
		paras.add(new TemplateParam("first",first,"#FF3333"));  
		paras.add(new TemplateParam("keyword1",keyword1,"#0044BB"));  
		paras.add(new TemplateParam("keyword2",(new Date().toLocaleString()) ,"#0044BB"));
		paras.add(new TemplateParam("remark","很荣幸为您服务，如有任何疑问请联系技术人员","#AAAAAA"));  
		          
		tem.setTemplateParamList(paras);  
		          
		WeiXinUtil.sendTemplateMsg(token,tem);
	}
	/**
	 * 发送通知消息
	 */
	public static void sendInform2(String token,String toUser, String first, String keyword1, String keyword2){
		Template tem=new Template();  
		tem.setTemplateId("j3uSiEeYMGG1UrMmU_AZlIJ_xwdTChiCGkKCpbTr2sk");  
		tem.setTopColor("#00DD00");  
		tem.setToUser(toUser);  
		tem.setUrl("");  
		
		List<TemplateParam> paras=new ArrayList<TemplateParam>();  
		paras.add(new TemplateParam("first",first,"#0044BB"));  
		paras.add(new TemplateParam("keyword1",keyword1,"#0044BB"));  
		paras.add(new TemplateParam("keyword2",(new Date().toLocaleString()) ,"#0044BB"));
		paras.add(new TemplateParam("remark","很荣幸为您服务，如有任何疑问请联系技术人员","#AAAAAA"));  
		
		tem.setTemplateParamList(paras);  
		
		WeiXinUtil.sendTemplateMsg(token,tem);
	}
	
}

