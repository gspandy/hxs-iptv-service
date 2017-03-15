package com.eeduspace.cibn.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eeduspace.cibn.model.LoginModel;
import com.eeduspace.cibn.model.UserInfoModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VerifyCodeModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.persist.po.DiagnosticReport;
import com.eeduspace.cibn.persist.po.TempPaperInfo;
import com.eeduspace.cibn.service.DiagnosticReportService;
import com.eeduspace.cibn.service.TempPaperInfoService;
import com.eeduspace.cibn.util.OrderUtil;
import com.eeduspace.cibn.weixin.WeixinPay;
import com.eeduspace.cibn.ws.UserWs;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.HttpClientUtil;
import com.google.gson.Gson;

public class VIPTest {
	private final Logger logger = LoggerFactory.getLogger(UserWs.class);
	private Gson gson=new Gson();
	@Inject
	private TempPaperInfoService tempPaperInfoService;
	@Inject
	private DiagnosticReportService diagnosticReportService;
	@Test
	public void vipQr() throws ClientProtocolException, IOException{
		String urlString="http://192.168.1.87:8181/cibnws/vip_buy/save";
		String userCodeString="b3dbb6ce6acd4035b5108548add0801b";
    	VipOrderModel orderModel=new VipOrderModel();
    	UserModel userModel=new UserModel();
    	userModel.setUserCode(userCodeString);
    	userModel.setIp("220.249.22.170");
    	String vIPOrderSnString=OrderUtil.GetOrderNumber("VIP");
    	orderModel.setUserCode(userCodeString);
    	orderModel.setOrderPrice(0.02);
    	orderModel.setUserModel(userModel);
    	orderModel.setOrderName("VIP测试购买"+vIPOrderSnString);
    	orderModel.setVipOrderType("ONE_MONTH");
    	orderModel.setVipDays(30);
    	orderModel.setPayType("alipay");
    	logger.info("result{}---->"+HTTPClientUtils.httpPostRequestJson(urlString, gson.toJson(orderModel)));
	}
	
	@Test
	public void vipList(){
		String userCodeString="b3dbb6ce6acd4035b5108548add0801b";
		String urlString ="http://101.200.155.215:8180/cibnws/vip_buy/get_vip_buy_list/";
		logger.info("result{}---->"+HTTPClientUtils.httpGetRequestJson(urlString+userCodeString+"/0/10"));
	}
	@Test
	public void vipDetail(){
		String orderUUID="688b440aefe54d03b1e5cc1bb9f6dbb3";
		String urlString ="http://101.200.155.215:8180/cibnws/vip_buy/vip_buy_details/";
		logger.info("result{}---->"+HTTPClientUtils.httpGetRequestJson(urlString+orderUUID));
	}
	
	@Test
	public void vipDelete() throws ClientProtocolException, IOException{
		String orderUUID="ff8a054961ca4d0a88f5b7a0db077af5";
		String urlString ="http://192.168.1.12:8080/cibnws/vip_buy/delete/";
		logger.info("result{}---->"+HTTPClientUtils.httpPostRequestJson(urlString+orderUUID, ""));
	}
	
	@Test
	public void vipPack(){
		String url="http://101.200.155.215:8180/cibnws/vip_pack/get_all";
		logger.info("result{}---->"+HTTPClientUtils.httpGetRequestJson(url));
	}
	@Test
	public void testTemPaper(){
		List<TempPaperInfo> list=new ArrayList<>();
		for (TempPaperInfo tempPaperInfo : list) {
			for (int i = 0; i < 1000; i++) {
				DiagnosticReport diagnosticReport=new DiagnosticReport();
				diagnosticReport.setScore(getRandom());
				diagnosticReport.setPaperCode(tempPaperInfo.getUuid());
				diagnosticReport.setUserCode(OrderUtil.GetOrderNumber("TestUser"));
				diagnosticReportService.save(diagnosticReport);
			}
		}
		logger.info("initSuccess");
	}
	
	public int getRandom(){
	    int n = (int)(Math.random()*100);
	    return n;
	}
	
	@Test
	public void testRegister() throws ClientProtocolException, IOException{
		Gson gson=new Gson();
		UserModel userModel=new UserModel();
		userModel.setMobile("18600478609");
		userModel.setPassword("123456");
		String urlString="http://192.168.1.87:8181/cibnws/user/register";
		String ssString=HTTPClientUtils.httpPostRequestJson(urlString, gson.toJson(userModel));
		System.out.println(ssString);
	}
	
	@Test
	public void scan() throws ClientProtocolException, IOException{
		Gson gson=new Gson();
		LoginModel loginModel=new LoginModel();
		loginModel.setTelevisionCode("123123");
		loginModel.setUserCode("1cfa6bb3-b7dc-4cba-a6ab-0989bc7a1c03");
		String urlString="http://192.168.1.87:8181/cibnws/login/scan";
		String ssString=HTTPClientUtils.httpPostRequestJson(urlString, gson.toJson(loginModel));
		System.out.println(ssString);
	}
	@Test
	public void userLogin() throws ClientProtocolException, IOException{
		Gson gson=new Gson();
		LoginModel loginModel=new LoginModel();
		loginModel.setUserName("15642863827");
		loginModel.setPassword("111111");
		String urlString="http://192.168.1.87:8181/haoxuesheng-tv/cibnws/login/user";
		String ssString=HTTPClientUtils.httpPostRequestJson(urlString, gson.toJson(loginModel));
		System.out.println(ssString);
	}
	@Test
	public void updatePwd() throws ClientProtocolException, IOException{
		Gson gson=new Gson();
		UserModel userModel=new UserModel();
		userModel.setUserCode("1cfa6bb3-b7dc-4cba-a6ab-0989bc7a1c03");
		userModel.setToken("TK00043624E8B4FF48");
		userModel.setNewPwd("123123");
		userModel.setOldPassword("123123");
		String urlString="http://192.168.1.12:8080/cibnws/user/pwd/update";
		String ssString=HTTPClientUtils.httpPostRequestJson(urlString, gson.toJson(userModel));
		System.out.println(ssString);
	}
	
	@Test
	public void resetPwd() throws ClientProtocolException, IOException{
		Gson gson=new Gson();
		//15642863827
		UserModel password=new UserModel();
		password.setMobile("15642863827");;
		password.setPassword("111111");
		String urlString="http://192.168.1.12:8080/cibnws/user/pwd/find";
		String ssString=HTTPClientUtils.httpPostRequestJson(urlString, gson.toJson(password));
		System.out.println(ssString);
	}
	
	@Test
	public void getUserByMobile(){
		String serverUrlString="http://218.240.38.106:38081/iwrong-service-v3/api/";
		String mobileString="18600478607";
		String response = null;
		UserInfoModel infoModel = null;
		response = HTTPClientUtils.httpGetRequestJson(serverUrlString+ "userinfo/get/emailormobile/" + mobileString);
		logger.debug("response:{}", response);
		if (StringUtils.isNotBlank(response)) {
			infoModel = gson.fromJson(response, UserInfoModel.class);
		}
		System.out.println(infoModel);
	}
	@Test
	public void findPwd() throws IOException{
		Gson gson=new Gson();
		String serverUrlString="http://218.240.38.106:38081/iwrong-service-v3/api/";
		UserModel userModel=new UserModel();
		userModel.setMobile("18600478607");
		userModel.setNewPwd("000000");
		String reString=HTTPClientUtils.doPut(serverUrlString+"userinfo/pwd/find", gson.toJson(userModel));
		System.out.println(reString);
	}
	
	@Test
	public void code() throws IOException{
		Gson gson=new Gson();
		String serverUrlString="http://192.168.1.87:8181/cibnws/user/verifyCode";
		VerifyCodeModel codeModel=new VerifyCodeModel();
		codeModel.setMobile("18600478607");
		codeModel.setType("reset_password");
		String ssString=HTTPClientUtils.httpPostRequestJson(serverUrlString, gson.toJson(codeModel));
		System.out.println(ssString);
	}
	public static void main(String[] args) {
		
	}
}
