package com.eeduspace.cibn.service.impl;

import com.eeduspace.cibn.client.UserClient;
import com.eeduspace.cibn.model.request.UserRequestModel;
import com.eeduspace.cibn.model.response.UserLoginResponse;
import com.eeduspace.cibn.persist.dao.UserDao;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.uuims.comm.util.base.json.GsonUtil;
import com.google.gson.Gson;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:
 */
@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private Gson gson=new Gson();
    @Inject
    private UserDao userDao;

    @Inject
    private UserClient userClient;
    @Override
    public UserPo findByUserCode(String userCode) {
        return userDao.findByUserCode(userCode);
    }

    @Override
    public UserPo findLoginUser(String user, String password) {
        return userDao.findLoginUser(user,user,user,password);
    }

    @Override
	public UserPo save(UserPo userPo) {
		return userDao.save(userPo);
	}

	@Override
	public UserPo findOne(Long userId) {
		return userDao.findOne(userId);
	}

	@Override
	public UserPo findByMobile(String mobile) {
		return userDao.findByMobile(mobile);
	}
	
	@Override
	public void updatePwdByMobile(String password, String mobile){
		userDao.updatePwd(password,mobile);
	}

	@Override
	@Transactional
    public BaseResponse editPwd(UserRequestModel userModel) throws IOException {
        UserPo user = this.findByUserCode(userModel.getUserCode());
//	    System.out.println(user.getOpenId()+"---------------------------------------");
//	    userModel.setOpenId(user.getOpenId());
        userModel.setUserAccessKey(user.getAccessKey());
        userModel.setUserSecretKey(user.getSecretKey());
        userModel.setToken(userModel.getToken());
        BaseResponse clientBaseResponse = userClient.updatePassword(userModel);
        if("Success".equals(clientBaseResponse.getCode())){
            user.setPassword(userModel.getPassword());
            this.updatePwdByMobile(userModel.getPassword(),user.getMobile());
        }
        return clientBaseResponse;
    }

	@Override
	public BaseResponse login(UserRequestModel userRequestModel, boolean b) throws IOException {
		logger.debug("===================================================================>"+userRequestModel.getMobile());
		userRequestModel.setPhone(userRequestModel.getMobile());
		logger.debug("===================================================================>"+userRequestModel);
		
        BaseResponse loginResponse= userClient.userLogin(userRequestModel);
        
        if(loginResponse.getHttpCode().equals("200")){
        	UserLoginResponse userLoginResponse= GsonUtil.fromObjectJson(gson.toJson(loginResponse), "result", "userModel", UserLoginResponse.class);
        	logger.debug("===================================================================>"+userLoginResponse);
	        UserPo userPo = userDao.findByMobile(userLoginResponse.getPhone());
	        logger.debug("===================================================================>"+userPo);
	        BaseResponse baseResponse = new BaseResponse();
	        baseResponse.setResult(userPo);
        return baseResponse;
        }
		return loginResponse;
	}
	
	@Override
	public List<UserPo> findAll() {
		return userDao.findAll();
	}
}
