package com.eeduspace.cibn.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.cibn.model.AnalysisModel;
import com.eeduspace.cibn.model.AnswerResultModel;
import com.eeduspace.cibn.model.DiagnosticReportModel;
import com.eeduspace.cibn.model.DiagnosticResultModel;
import com.eeduspace.cibn.model.KnowledgeModel;
import com.eeduspace.cibn.model.LoginModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.model.WeakKnowledgePointModel;
import com.eeduspace.cibn.util.OrderUtil;
import com.eeduspace.cibn.ws.UserWs;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;

public class intercepterTest {
	private final Logger logger = LoggerFactory.getLogger(UserWs.class);
	
	
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		Gson gson = new Gson();
		 
		
		 /**test  post -------login**/
		// String url="http://localhost:8080/haoxuesheng-tv/cibnws/test/postTest/";
//		 String url="http://localhost:8080/haoxuesheng-tv/cibnws/login/user?requestId=11111";
//		 LoginModel loginModel = new LoginModel();
//		 loginModel.setPassword("123456");
//		 loginModel.setUserCode("b3dbb6ce6acd4035b5108548add0801b");
//		 loginModel.setUserName("iwrong81193653");
//		 //loginModel.setRequestId("1234567890");
//		 System.out.println("result{}---->"+HTTPClientUtils.httpPostRequestJson(url,gson.toJson(loginModel)));
		
		 /**test  post -------register**/
	  	//String registerUrlString="http://218.240.38.106:38081/iwrong-service-v3/api/api-docs/userinfo/create";
//	    System.out.println("~~~~~~~~test~~~~~~intercepter~~~~~~~register~~~~~~");
//		String registerUrlString="http://192.168.1.43:8080/haoxuesheng-tv/cibnws/user/register?token=TK1111111111111111";
//		UserModel infoPo = new UserModel();
//		infoPo.setPassword("1234567890");
//		infoPo.setMobile("13510010900");
//		infoPo.setUserCode("TK111111111111112");
//		//infoPo.setRequestId("1234567890");
//		infoPo.setToken("TK111111112");
//     	HTTPClientUtils httpClientUtils = new HTTPClientUtils();
//		System.out.println(HTTPClientUtils.httpPostRequestJson(registerUrlString, gson.toJson(infoPo)));

		
//		String testUrlString="http://192.168.1.43:8080/haoxuesheng-tv/cibnws/test/postTest";
//		 LoginModel loginModels = new LoginModel();
//		 loginModels.setPassword("123456");
//		 loginModels.setUserCode("b3dbb6ce6acd4035b5108548add0801b");
//		 loginModels.setUserName("iwrong81193653");
//		System.out.println(HTTPClientUtils.httpPostRequestJson(testUrlString, gson.toJson(loginModels)));
 
	
		 /**test  get**/
		 String data1 = "names=aa&abcd=12";
		 String url1="http://localhost:8080/haoxuesheng-tv/cibnws/test/testgets/aa?token=1";
	     System.out.println("result{}---->"+HTTPClientUtils.httpGetRequestJson(url1+data1));
		 
		 /**test  get**/
//		 String data = "name=aa&abc=12&token=tk&userCode=sdasassa";
//		 String url="http://localhost:8080/haoxuesheng-tv/cibnws/test/testget/aa?";
//	     System.out.println("result{}---->"+HTTPClientUtils.httpGetRequestJson(url+data));
	     
	}
}
