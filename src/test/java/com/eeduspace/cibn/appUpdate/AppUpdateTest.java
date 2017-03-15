package com.eeduspace.cibn.appUpdate;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.cibn.client.UserClient;
import com.eeduspace.cibn.model.AppUpdateModel;
import com.eeduspace.cibn.model.request.UserRequestModel;
import com.eeduspace.cibn.persist.po.PaperTypePo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.AppUpdateService;
import com.eeduspace.cibn.service.PaperTypeService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.reflect.TypeToken;

public class AppUpdateTest extends BaseTest {
	
	@Inject
	private AppUpdateService updatePoDao;
	@Inject
	private PaperTypeService paperTypeService;
	@Inject
	private UserClient userClient;
	@Inject
	private UserService userService;
	@Test
	public void testDao(){
		AppUpdateModel app = updatePoDao.getAppUpdateModel();
		System.out.println(app.getAppVersion() + "-----" + app.getAppName() + "-----"+app.getDownUrl());
		//获取自身ip
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();//获得本机IP
			
			System.out.println(ip + ":"+addr.getAddress().toString()+ ":"+ addr.getCanonicalHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testUpdate(){
		String url ="http://192.168.1.87:8181/cibnws/appUpdate/update?123";
//		String url ="http://192.168.1.12:8080/cibnws/appUpdate/update?123";
		String data = "";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			System.out.println("测试response：" + response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpload(){
//		String url ="http://localhost:8080/haoxuesheng-tv/cibnws/appUpdate/fileupload?123";
		String url ="http://192.168.1.12:8070/haoxuesheng-tv/cibnws/appUpdate/fileupload?123";
		String data = "";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			System.out.println("测试response：" + response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		AppUpdateTest baseDataTest = new AppUpdateTest();
		baseDataTest.testUpload();
		
		
	}
	
	@Test
	public void selectPaperType() {
		String name = "单元测试";
		PaperTypePo findByName = paperTypeService.findByName(name);
		System.out.println(findByName.getDiscount());
	}
	
	@Test
	public void selectUserInfo() {
		UserRequestModel userRequestModel = new UserRequestModel();
		String mobile = "15538934416";
		userRequestModel.setPhone("15538934416");
		userRequestModel.setMobile("15538934416");
		try {
			UserPo userPo = new UserPo();
			BaseResponse userInfoByPhone = userClient.getUserInfoByPhone(userRequestModel);
			if (userInfoByPhone.getHttpCode().equals("200")) {
				Object result = userInfoByPhone.getResult();
				Map<String,Object> map = gson.fromJson(gson.toJson(result), new TypeToken<Map<String,Object>>() {}.getType());
				Object object = map.get("userModel");
				String json= gson.toJson(object);
				Map<String,Object> map2 = gson.fromJson(json, new TypeToken<Map<String,Object>>() {}.getType());
				userPo.setUserCode(map2.get("openId").toString());
		        userPo.setMobile(mobile);
		        userPo.setPassword(map2.get("password").toString());
		        userPo.setVIP(false);
		        userPo.setUserName(mobile);
		        userPo.setEmail(null);
		        userPo.setRegisterSource("15");
		        userPo.setMarketChannel("000");
		        userPo = userService.findByMobile(mobile);
		        if (null == userPo) {
		        	userPo = userService.save(userPo);
				}
		        
			}
			logger.info(gson.toJson(userInfoByPhone));
		} catch (ClientProtocolException e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		} catch (IOException e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		}
	}
}
