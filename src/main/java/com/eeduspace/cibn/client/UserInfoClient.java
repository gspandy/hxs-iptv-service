package com.eeduspace.cibn.client;

import com.eeduspace.cibn.model.LoginModel;
import com.eeduspace.cibn.model.UserInfoModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.response.ClientUserResponse;
import com.eeduspace.cibn.response.ClientResponse;

import java.io.IOException;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:
 */
public interface UserInfoClient {

    UserModel getUserInfo(String userCode);


    ClientResponse logout(String userCode);


    ClientUserResponse login(LoginModel loginModel) throws IOException;


    ClientUserResponse register(UserModel userModel) throws IOException;
    
    
    UserInfoModel getUserByMobile(String mobile);
    /**
     * 重置密码
     * Author： zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * 2016年5月30日 上午9:41:17
     * @param mobile
     * @param password
     * @return
     * @throws IOException
     */
    ClientResponse resetPassword(String mobile,String password) throws IOException;
    
    
     
    

}
