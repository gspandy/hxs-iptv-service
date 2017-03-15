package com.eeduspace.cibn.client.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.client.UserClient;
import com.eeduspace.cibn.model.request.UserRequestModel;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;
import com.google.gson.Gson;
@Service
public class UserClientImpl implements UserClient{
	private final Logger logger = LoggerFactory.getLogger(UserClient.class);
	private Gson gson=new Gson();
	@Value("${cibn.uuims.server.url}")
	private String serverUrl;
	
	@Value("${cibn.accessKey}")
	private String accessKey;
	@Value("${cibn.secretKey}")
	private String secretKey;
	@Value("${cibn.productType}")
	private String productType;
	
	
	@Override
    public BaseResponse validateByMobile(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
        userRequestModel.setType("phone");
        userRequestModel.setPhone(userRequestModel.getMobile());
        String action="validate";
        String timestamp=System.currentTimeMillis()+"";
        String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
        String signature="";
        try {
            signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("validate request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("validate reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
    }
	@Override
	public BaseResponse userRegister(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="create";
		String timestamp=System.currentTimeMillis()+"";
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
        return baseResponse;
	}
	@Override
	public BaseResponse updatePassword(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		userRequestModel.setOldPassword(userRequestModel.getOldPassword());
		userRequestModel.setPassword(userRequestModel.getPassword());
		String action="edit_password";
		String timestamp=System.currentTimeMillis()+"";
		String userAccessKey=userRequestModel.getUserAccessKey();
		String token=userRequestModel.getToken();
		String userSecretKey=userRequestModel.getUserSecretKey();
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((userAccessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), userSecretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+userAccessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature+"&token="+token;
        String url=serverUrl.concat(urlInfo);
        logger.info("updatePwd request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.info("updatePwd reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
	}
	@Override
	public BaseResponse userLogin(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		userRequestModel.setProductType(productType);
		if(userRequestModel.getEquipmentType()==null||userRequestModel.getEquipmentType().equals("")){
			userRequestModel.setEquipmentType("Web");
		}
		String action="login";
		String timestamp=System.currentTimeMillis()+"";
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		/*String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}*/
		String urlInfo="login?action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5;
	    String url=serverUrl.concat(urlInfo);
        logger.info("userLogin request:{}",url);
	    String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.info("userLogin reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
	}
	@Override
	public BaseResponse resetPwd(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="reset_password";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.info("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.info("register reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
		return baseResponse;
	}
	@Override
	public BaseResponse checkPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="validate";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
		return baseResponse;
	}
	@Override
	public BaseResponse userActivation(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="activation";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
		return baseResponse;
	}
	@Override
	public BaseResponse updateUserInfo(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="update";
		String timestamp=System.currentTimeMillis()+"";
		String userAccessKey=userRequestModel.getUserAccessKey();
		String token=userRequestModel.getToken();
		String userSecretKey=userRequestModel.getUserSecretKey();
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((userAccessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), userSecretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+userAccessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature+"&token="+token;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updatePwd request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updatePwd reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
        return baseResponse;
	}
	@Override
	public BaseResponse getUserInfo(UserRequestModel userRequestModel)throws ClientProtocolException, IOException {
		String action="describe";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
		return baseResponse;
	}

	
	 /* 发送验证码    
	 * @see com.eeduspace.cibn.client.UserClient#sendSms(java.lang.String)    
	 */
		 
	@Override
	public BaseResponse sendSms(UserRequestModel userRequestModel) throws ClientProtocolException,
			IOException {
		String action="send_sms";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user/restPwd?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
		return baseResponse;
			
	}
	
	
	 /* 校验验证码    
	 * @see com.eeduspace.cibn.client.UserClient#validateSms(com.eeduspace.cibn.model.request.UserRequestModel)    
	 */
		 
	@Override
	public BaseResponse validateSms(UserRequestModel userRequestModel)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		String action="validate_code";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user/restPwd?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
		return baseResponse;
			
	}
	
	 /* 重置密码    
	 * @see com.eeduspace.cibn.client.UserClient#resetPassword(com.eeduspace.cibn.model.request.UserRequestModel)    
	 */
		 
	@Override
	public BaseResponse resetPassword(UserRequestModel userRequestModel)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		String action="reset_password";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user/restPwd?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
		return baseResponse;	
	}
	
	@Override
	public BaseResponse getUserInfoByPhone(UserRequestModel userRequestModel)throws ClientProtocolException, IOException {
		String action="describe_by_phone";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
		return baseResponse;
	}
}
