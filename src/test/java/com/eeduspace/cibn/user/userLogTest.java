package com.eeduspace.cibn.user;


import java.io.IOException;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.cibn.persist.dao.UserDao;
import com.eeduspace.cibn.persist.dao.UserLogDao;
import com.eeduspace.cibn.persist.po.UserLogPo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.service.UserLogService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;

import org.junit.Test;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
public class userLogTest extends BaseTest {

  
//    @Inject
//    private UserInfoService userInfoService;

    @Inject
    private UserLogDao userLogDao;
    
    @Test
    public  void test(){
/*
    	Gson gson=new Gson();
    	System.out.println("走junit");
    	UserPo userPo =new UserPo();
    	Long userid = Long.valueOf("1");
    	userPo.setId(userid);
    	UserLogPo findByUserPo = userLogDao.findByUserPo(userPo);
    	System.out.println(findByUserPo.getModule());
    	*/
    	
    	
    //用户获取日志   
    //参数  usercode  size  
   	HTTPClientUtils httpClientUtils = new HTTPClientUtils();
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/log/userlog";
		String data = "{'userCode':'b3dbb6ce6acd4035b5108548add0801b','pageable':{'size':10,'page':0}}";
		try {
			String response = httpClientUtils.httpPostRequestJson(url, data);
			logger.debug("返回的结果为：" + response);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    
    public static void main(String[] args) {
    
    	testss();
    	//testsspost();
    }
    public static void testss(){
    	Gson gson=new Gson();
    	System.out.println("走起get");
    
    } 
    public static void testsspost(){
		String userCode="b3dbb6ce6acd4035b5108548add0801b";

    	Gson gson=new Gson();
    	/*String url="http://localhost:8080/haoxuesheng-tv/cibnws/log/userlog/";
		System.out.println(HTTPClientUtils.httpGetRequestJson(url+"b3dbb6ce6acd4035b5108548add0801b"));
		*/
    	
    }
    
    
 
  
}

