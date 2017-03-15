package com.eeduspace.cibn.client.impl;

import com.eeduspace.cibn.client.UserInfoClient;
import com.eeduspace.cibn.model.LoginModel;
import com.eeduspace.cibn.model.UserInfoModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.response.ClientUserResponse;
import com.eeduspace.cibn.response.ClientResponse;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
@Service
public class UserInfoClientImpl implements UserInfoClient {

    protected final Logger logger = LoggerFactory.getLogger(UserInfoClient.class);

    private Gson gson=new Gson();
	@Value("${cibn.uuims.server.url}")
	private String serverUrl;
	
	@Value("${cibn.accessKey}")
	private String accessKey;
	@Value("${cibn.secretKey}")
	private String secretKey;
	@Value("${cibn.productType}")
	private String productType;

    @Value("${iwrong.url}")
    private String iwrongUrl;

    @Value("${iwrong.user.register.url}")
    private String registerUrl;

    @Override
    public UserModel getUserInfo(String code) {
        String response = null;
        UserInfoModel infoModel = null;
        UserModel userModel = null;
        String url = iwrongUrl + "userinfo/get/" + code;

        response = HTTPClientUtils.httpGetRequestJson(url);
        logger.debug("response:{}", response);
        if (StringUtils.isNotBlank(response)) {
            infoModel = gson.fromJson(response, UserInfoModel.class);
        }
        if (infoModel != null) {
            userModel = new UserModel();
            userModel.setUserCode(infoModel.getCode());
            userModel.setMobile(infoModel.getMobile());
            userModel.setPassword(infoModel.getPassword());
            userModel.setVip(false);
            userModel.setUserName(infoModel.getUserName());
            userModel.setEmail(infoModel.getEmail());
        }

        return userModel;
    }

    @Override
    public ClientResponse logout(String userCode) {
        String response = null;
        ClientResponse clientResponse = null;
        String url = iwrongUrl + "userinfo/" + userCode + "/logout";

        response = HTTPClientUtils.httpGetRequestJson(url);
        logger.debug("response:{}", response);
        clientResponse = gson.fromJson(response, ClientResponse.class);
        return clientResponse;
    }

    @Override
    public ClientUserResponse login(LoginModel loginModel) throws IOException {
        String response = null;
        ClientUserResponse clientResponse = null;
        String url = iwrongUrl + "userinfo/login";

        response = HTTPClientUtils.httpPostRequestJson(url, gson.toJson(loginModel));
        logger.debug("ClientUserResponse:{}", response);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        clientResponse = gson.fromJson(response, ClientUserResponse.class);
        return clientResponse;
    }

    @Override
    public ClientUserResponse register(UserModel userModel) throws IOException {
        String response = null;
        ClientUserResponse clientResponse = null;
        //  String url =iwrongUrl +"userinfo/create";
        String url = registerUrl + "userinfo/createForUserData";
        logger.debug("UserInfoClientImpl   register  url:{}",url);
        response = HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userModel));
        if (StringUtils.isBlank(response)) {
            return null;
        }
        clientResponse = gson.fromJson(response, ClientUserResponse.class);
        return clientResponse;
    }

	@Override
	public UserInfoModel getUserByMobile(String mobile) {
		String response = null;
		UserInfoModel infoModel = null;
		logger.debug("response:{}", response);
		response = HTTPClientUtils.httpGetRequestJson(iwrongUrl+ "userinfo/get/emailormobile/" + mobile);
		if (StringUtils.isNotBlank(response)) {
			infoModel = gson.fromJson(response, UserInfoModel.class);
		}
		return infoModel;
	}

	@Override
	public ClientResponse resetPassword(String mobile, String password) throws IOException {
		UserModel userModel=new UserModel();
		userModel.setMobile(mobile);
		userModel.setNewPwd(password);
		String response=null;
		ClientResponse clientResponse = null;
		response=HTTPClientUtils.doPut(iwrongUrl+"userinfo/pwd/find", gson.toJson(userModel));
		if (StringUtils.isBlank(response)) {
            return null;
        }
		clientResponse = gson.fromJson(response, ClientResponse.class);
        return clientResponse;
	}
}
