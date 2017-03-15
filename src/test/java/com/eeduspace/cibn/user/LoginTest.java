package com.eeduspace.cibn.user;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.cibn.model.LoginModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VerifyCodeModel;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import org.junit.Test;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Author: dingran
 * Date: 2016/4/21
 * Description:
 */
public class LoginTest extends BaseTest {

    @Inject
    private DataSource iwrongDataSource;
    @Inject
    private UserService userService;

    @Test
    public  void testStatus(){
        String response = null;
//        String url = "http://localhost:8080/cibnws/login/status/"+"test1234567";
//        String url = "http://192.168.1.12:8080/cibnws/login/status/"+"test1234567";
        String url = "http://101.200.155.215:8180/cibnws/login/status/"+"test1234567";
        LoginModel userModel=new LoginModel();
        userModel.setAccessKey("6ba2f7618ce69aab97e12f851ea6b7cb");
        try {
            response= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("response:{}",response);
    }


    @Test
    public  void testScan(){
        String response = null;
//        String url = "http://localhost:8080/cibnws/login/scan";
        String url = "http://101.200.155.215:8180/cibnws/login/scan";
//        String url = "http://192.168.1.12:8080/cibnws/login/scan";
        LoginModel loginModel=new LoginModel();
        loginModel.setUserCode("b3dbb6ce6acd4035b5108548add0801b");
        loginModel.setTelevisionCode("test1234567");

        try {
            response= HTTPClientUtils.httpPostRequestJson(url,gson.toJson(loginModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("response:{}",response);
    }

    @Test
    public  void testConfirm(){
        String response = null;
//        String url = "http://localhost:8080/cibnws/login/confirm";
//        String url = "http://192.168.1.12:8080/cibnws/login/confirm";
        String url = "http://101.200.155.215:8180/cibnws/login/confirm";
        LoginModel loginModel=new LoginModel();
        loginModel.setUserCode("b3dbb6ce6acd4035b5108548add0801b");
        loginModel.setTelevisionCode("test1234567");

        try {
            response= HTTPClientUtils.httpPostRequestJson(url,gson.toJson(loginModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("response:{}",response);
    }

    @Test
    public void testLogin(){

        String response = null;
//        String url = "http://localhost:8080/cibnws/login/user";
        String url = "http://192.168.1.87:8181/cibnws/login/user";
//        String url = "http://101.200.155.215:8180/cibnws/login/user";
        LoginModel loginModel=new LoginModel();
        loginModel.setPassword("123456");
        loginModel.setIp("test");
        loginModel.setUserName("18600478607");
        loginModel.setLoginSystem("test");
        loginModel.setLoginDevice("6ba2f7618ce69aab97e12f851ea6b7cb");
//        loginModel.setTelevisionCode("6ba2f7618ce69aab97e12f851ea6b7cb");
        try {
            response= HTTPClientUtils.httpPostRequestJson(url,gson.toJson(loginModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("response:{}",response);
       /* ClientUserResponse clientResponse=null;
        String url ="http://218.240.38.106:38081/iwrong-service-v3/api/userinfo/login";

        LoginModel loginModel=new LoginModel();
        loginModel.setPassword("123456");
        loginModel.setIp("test");
        loginModel.setUserName("13311335930");
        loginModel.setLoginSystem("test");
        loginModel.setLoginDevice("test");
        String response= null;
        try {
            response = HTTPClientUtils.httpPostRequestJson(url, gson.toJson(loginModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientResponse = gson.fromJson(response, ClientUserResponse.class);
        logger.debug(gson.toJson(clientResponse.getUserInfo()));
        logger.debug(clientResponse.getUserInfo().getMobile());
        logger.debug(gson.toJson(clientResponse.getLogCode()));*/

    }

    @Test
    public  void testUserInfo(){
        String response = null;
//        String url = "http://localhost:8080/cibnws/user/info/b3dbb6ce6acd4035b5108548add0801b";
        String url = "http://192.168.1.12:8080/cibnws/user/info/b3dbb6ce6acd4035b5108548add0801b";
//        LoginModel loginModel=new LoginModel();
//        loginModel.setUserCode("b3dbb6ce6acd4035b5108548add0801b");
//        loginModel.setTelevisionCode("test1234567");


        response= HTTPClientUtils.httpGetRequestJson(url);

        logger.debug("response:{}",response);
    }

    @Test
    public  void testLogout(){
        String response = null;
//        String url = "http://localhost:8080/cibnws/login/status/"+"test1234567";
        String url = "http://localhost:8080/cibnws/user/logout";
        LoginModel userModel=new LoginModel();
        userModel.setTelevisionCode("6ba2f7618ce69aab97e12f851ea6b7cb");
        userModel.setUserCode("b3dbb6ce6acd4035b5108548add0801b");
        try {
            response= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("response:{}",response);
    }
    @Test
    public  void verifycode(){
        String response = null;
//        String url = "http://localhost:8080/cibnws/login/status/"+"test1234567";
        String url = "http://192.168.1.12:8080/cibnws/user/verifyCode";
        VerifyCodeModel verifyCodeModel=new VerifyCodeModel();
        verifyCodeModel.setType("register");
        verifyCodeModel.setMobile("13311335930");
        try {
            response= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(verifyCodeModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("response:{}",response);
    }
    @Test
    public  void register(){
        String response = null;
//        String url = "http://localhost:8080/cibnws/login/status/"+"test1234567";
//        String url = "http://localhost:8080/cibnws/user/register";
        String url = "http://192.168.1.12:8080/cibnws/user/register";
//        String url = "http://101.200.155.215:8180/cibnws/user/register";
        UserModel userModel=new UserModel();
        userModel.setPassword("123456");
        userModel.setMobile("13311335691");
        try {
            response= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("response:{}",response);
    }
}
