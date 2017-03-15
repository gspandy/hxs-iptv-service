package com.eeduspace.cibn.user;


import java.io.IOException;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.persist.dao.UserLogDao;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;

import org.junit.Test;

import javax.inject.Inject;
public class registerTest extends BaseTest {


//    @Inject
//    private UserInfoService userInfoService;

    @Inject
    private UserLogDao userLogDao;
    
    @Test
    public  void test(){
    	
    	
    	System.out.println("junit走起");
    	Gson gson=new Gson();

    	   	String registerUrlString="http://218.240.38.106:38081/iwrong-service-v3/api/api-docs/userinfo/create";
    
		UserModel infoPo = new UserModel();
    		infoPo.setPassword("1234567890");
    		infoPo.setMobile("13500000117");
     	HTTPClientUtils httpClientUtils = new HTTPClientUtils();
	
		try {
			System.out.println(HTTPClientUtils.httpPostRequestJson(registerUrlString, gson.toJson(infoPo)));
			} catch (IOException e) {
			e.printStackTrace();
		}
 }
    
    
    public static void main(String[] args) {
  	testregister();
    }
    public static void testregister(){
    	System.out.println("main --test走起");
    	Gson gson=new Gson();
    	
        //String registerUrlString="http://218.240.38.106:38081/iwrong-service-v3/api/userinfo/create";

    	String registerUrlString="http://192.168.1.87:8181/cibnws/user/register";
		UserModel infoPo = new UserModel();
		infoPo.setPassword("123456");
		infoPo.setMobile("18600478607");
		try {
			System.out.println(HTTPClientUtils.httpPostRequestJson(registerUrlString, gson.toJson(infoPo)));
			} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void testPaper(){
    	String baseUrlString="http://192.168.1.43:8080/haoxuesheng-tv/cibnws/paper/";
    	String getSubjectUrl=baseUrlString+"get_subject/0730438599f543319a95a36f98556081";
    	System.out.println(getSubjectUrl);
    	System.out.println(HTTPClientUtils.httpGetRequestJson(getSubjectUrl));
    }
    
    public static void testOrder(){
    	String baseUrlString="http://192.168.1.43:8080/haoxuesheng-tv/cibnws/vip_buy/find_order/VIP20160424104107000001";
    	System.out.println(baseUrlString);
    	System.out.println(HTTPClientUtils.httpGetRequestJson(baseUrlString));
    }
    public static void wxNotice(){
    	Gson gson=new Gson();
    	String baseUrlString="http://192.168.1.43:8080/haoxuesheng-tv/cibnws/wx_pay_notice/diagnostic_weixin_pay";
    	HTTPClientUtils.httpGetRequestJson(baseUrlString);
    }
}

