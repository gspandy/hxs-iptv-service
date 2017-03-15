package com.eeduspace.cibn.ws;

import java.io.IOException;
import java.util.Map;

import com.eeduspace.cibn.client.UserClient;
import com.eeduspace.cibn.client.UserInfoClient;
import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.LoginModel;
import com.eeduspace.cibn.model.TokenModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.UserTelevisionModel;
import com.eeduspace.cibn.model.request.UserRequestModel;
import com.eeduspace.cibn.model.response.UserLoginResponse;
import com.eeduspace.cibn.persist.enumeration.UserEnum;
import com.eeduspace.cibn.persist.po.TokenPo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.redis.RedisClientTemplate;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.response.ClientUserResponse;
import com.eeduspace.cibn.response.LoginResponse;
import com.eeduspace.cibn.service.TokenService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.service.UserTelevisionService;
import com.eeduspace.cibn.util.CommonUtil;
import com.eeduspace.cibn.util.JDBCUtil;
import com.eeduspace.cibn.util.RandomUtils;
import com.eeduspace.uuims.comm.util.base.DateUtils;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
import com.eeduspace.uuims.comm.util.base.ValidateUtils;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;
import com.eeduspace.uuims.comm.util.base.json.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:登录
 */
@Component
@Path(value = "/login")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class LoginWs {
    private final Logger logger = LoggerFactory.getLogger(LoginWs.class);
    private String requestId;
    private Gson gson = new Gson();

    @Value("${cibn.token.expires}")
    private String expires;
    @Value("${cibn.manager.ak}")
    private String ak;
    @Value("${user.confirm.login.timeout}")
    private String timeoutExpires;
    @Inject
    private UserClient userClient;
    @Inject
    private UserService userService;

    @Inject
    private UserTelevisionService userTelevisionService;

    @Inject
    private TokenService tokenService;

    @Inject
    private UserInfoClient userInfoClient;

    @Inject
    private JDBCUtil jdbcUtil;

    @Inject
    private RedisClientTemplate redisClientTemplate;
   
    /**
     * uuims用户登录
     */
    @POST
    @Path("/user")
    public Response login(
            @QueryParam("requestId") String requestId,
            String requestBody,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", this.requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
            LoginModel loginModel = gson.fromJson(requestBody, LoginModel.class);
            UserRequestModel userRequestModel = new UserRequestModel();
            userRequestModel.setPassword(Digest.md5Digest(loginModel.getPassword()));
            userRequestModel.setPhone(loginModel.getUserName());
            BaseResponse baseResponse = new BaseResponse(this.requestId);
            /*ClientUserResponse loginResponse = userInfoClient.login(loginModel);
            if (loginResponse == null || loginResponse.getUserInfo() == null) {
                logger.error("login exception: clientResponse{},", loginResponse);
                return Response.ok(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString()))).build();
            }*/
            BaseResponse userLogin = userClient.userLogin(userRequestModel);
            if (userLogin == null || !"200".equals(userLogin.getHttpCode())) {
                logger.error("register  ValidateMobile Exception：requestId："+  userLogin.getRequestId()+","+ userLogin.getCode() +".mobile");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), userLogin.getCode()))).build();
            }
            
            LoginResponse loginResponse = GsonUtil.fromObjectJson(gson.toJson(userLogin), "result", "userModel", LoginResponse.class);
            UserPo userPo = userService.findByMobile(userRequestModel.getPhone());
            if (userPo == null) {
                userPo = new UserPo();
                userPo.setUserCode(loginResponse.getOpenId());
                userPo.setOpenId(loginResponse.getOpenId());
                userPo.setEmail("");
                userPo.setMobile(loginResponse.getPhone());
                userPo.setPassword(userRequestModel.getPassword());
                userPo.setUserName(userRequestModel.getPhone());
                userPo.setVIP(false);
                userPo.setRegisterSource("15");
                userPo.setMarketChannel("000");
                userService.save(userPo);
            }
            //TODO 是否持久化token
            TokenPo tokenPo = tokenService.findByUserCode(userPo.getUserCode());
            if (tokenPo != null) {
                tokenService.delete(tokenPo.getId());
            }
            tokenPo = new TokenPo();
            tokenPo.setExpires(expires);
            tokenPo.setUserCode(userPo.getUserCode());
            tokenPo.setToken(loginResponse.getToken());
            tokenPo.setRefreshToken(loginResponse.getRefreshToken());
//            po.setTelevisionCode(userRequestModel.getLoginDevice());
            tokenService.save(tokenPo);

            //TODO redis删除原token
            String oldToken=redisClientTemplate.get(userPo.getUserCode());
            if(StringUtils.isNotBlank(oldToken)){
                redisClientTemplate.del(oldToken);
            }
            UserModel userModel = CIBNConvert.fromUserPo(userPo, null);
            userModel.setToken(loginResponse.getToken());
            userModel.setRefreshToken(loginResponse.getRefreshToken());
            userModel.setExpires(expires);
            userModel.setUserCode(userPo.getUserCode());
            redisClientTemplate.setex(loginResponse.getToken(), Integer.parseInt(expires), gson.toJson(userModel));
            redisClientTemplate.setex(userPo.getUserCode(), Integer.parseInt(expires), loginResponse.getToken());
            baseResponse.setResult(userModel);
            return Response.ok(gson.toJson(baseResponse)).build();
        } catch (Exception e) {
            logger.error("login user error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }

    /**
     * 根据设备号返回扫描登录状态或者userInfo
     * 1.当用户未确认登录时 则返回扫描状态
     * 2.当用户已确认登录是 则返回用户基本信息
     *
     * @param request
     * @param response
     * @return
     */
    @POST
    @Path("/status/{televisionCode}")
    public Response process(
            @QueryParam("requestId") String requestId,
            @PathParam("televisionCode") String televisionCode,
            String requestBody,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {

            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", this.requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);

            LoginModel loginModel = gson.fromJson(requestBody, LoginModel.class);
            if (StringUtils.isBlank(loginModel.getAccessKey())) {
                logger.error("login status Exception：requestId：" + this.requestId + "," + ResponseCode.PARAMETER_MISS.toString() + ".accessKey");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "accessKey"))).build();
            }
            logger.debug("______>" + ak);
            logger.debug("______>" + loginModel.getAccessKey());
            if (!loginModel.getAccessKey().equals(ak)) {
                logger.error("login status Exception：requestId：" + this.requestId + "," + ResponseCode.FORBIDDEN_AUTHFAILURE.toString() + ".accessKey");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.FORBIDDEN_AUTHFAILURE.toString(), "accessKey"))).build();
            }
            BaseResponse baseResponse = new BaseResponse(this.requestId);
            //String televisionRedis = redisClientTemplate.get(televisionCode);
            if (StringUtils.isBlank(televisionCode)) {
                logger.debug("requestId：" + this.requestId + "," + "televisionCode is not find.");
                return Response.ok(gson.toJson(baseResponse)).build();
            }
            String televisionRedis = redisClientTemplate.get(televisionCode);
            UserTelevisionModel userTelevisionModel = gson.fromJson(televisionRedis, UserTelevisionModel.class);
            if (userTelevisionModel == null || StringUtils.isBlank(userTelevisionModel.getUserCode())) {
                logger.error("requestId：" + this.requestId + "," + "userTelevisionModel or userTelevisionModel.getUserCode is null.");
                return Response.ok(gson.toJson(baseResponse)).build();
            }
            UserPo userPo = userService.findByUserCode(userTelevisionModel.getUserCode());
            if (userPo == null) {
                logger.error("requestId：" + this.requestId + "," + "userPo.getUserCode(" + userTelevisionModel.getUserCode() + ") is not find.");
                return Response.ok(gson.toJson(baseResponse)).build();
            }
            UserModel userModel = new UserModel();
            if (userTelevisionModel.getScanStatus().equals(UserEnum.ScanStatus.ConfirmLogin)) {
                userModel = CIBNConvert.fromUserPo(userPo, userTelevisionModel.getScanStatus());
                String token_ = redisClientTemplate.get(userPo.getUserCode());
                TokenModel tokenModel = new TokenModel();
                //当redis中不存在token信息时，则重新生成？
                if (StringUtils.isBlank(token_)) {
                    logger.error("requestId：" + this.requestId + "," + "redis.getUserCode(" + userPo.getUserCode() + ") is not find.");
                    return Response.ok(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_AUTHFAILURE.toString()))).build();
                    /*String token="TK"+ Digest.md5Digest16(userPo.getUserCode() + DateUtils.nowTimeMillis() + RandomUtils.getRandom(6)).toUpperCase();
                    String refreshToken= Digest.md5Digest(userPo.getUserCode() + token);
                    tokenModel=new TokenModel();
                    tokenModel.setRefreshToken(refreshToken);
                    tokenModel.setUserCode(userPo.getUserCode());
                    redisClientTemplate.setex(token,Integer.parseInt(expires),gson.toJson(tokenModel));
                    redisClientTemplate.setex(userPo.getUserCode(),Integer.parseInt(expires),token);
                    TokenPo tokenPo=tokenService.findByUserCode(userPo.getUserCode());
                    if(tokenPo!=null){
                        tokenService.delete(tokenPo.getId());
                    }
                    tokenPo=new TokenPo();
                    tokenPo.setExpires(expires);
                    tokenPo.setUserCode(userPo.getUserCode());
                    tokenPo.setToken(token);
                    tokenPo.setRefreshToken(refreshToken);
                    tokenService.save(tokenPo);*/
                }
                String tokenModel_ = redisClientTemplate.get(token_);
                tokenModel = gson.fromJson(tokenModel_, TokenModel.class);
                userModel.setToken(tokenModel.getToken());
                userModel.setRefreshToken(tokenModel.getRefreshToken());
                userModel.setExpires(expires);
//                redisClientTemplate.expire(televisionCode, Integer.parseInt(timeoutExpires));
            } else {
                userModel.setScanStatus(userTelevisionModel.getScanStatus().toString());
            }

            baseResponse.setResult(userModel);
            logger.debug("login get scanInfo by televisionCode response:" + gson.toJson(baseResponse));
            return Response.ok(gson.toJson(baseResponse)).build();
        } catch (Exception e) {
            logger.error("login get scanInfo by televisionCode error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }

    /**
     * 将设备号与用户进行绑定
     * 1.同一时间用户只能登陆一个终端设备
     *
     * @param requestBody
     * @param request
     * @param response
     * @return
     */
    @POST
    @Path("/scan")
    public Response scan(
            @QueryParam("requestId") String requestId,
            String requestBody,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", this.requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
            LoginModel loginModel = gson.fromJson(requestBody, LoginModel.class);
            if (StringUtils.isBlank(loginModel.getTelevisionCode())) {
                logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.PARAMETER_MISS.toString() + ".televisionCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "televisionCode"))).build();
            }
            if (StringUtils.isBlank(loginModel.getUserCode())) {
                logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.PARAMETER_MISS.toString() + ".userCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "userCode"))).build();
            }
            //TODO 当查询不到时，则从好学生进行获取
            UserPo userPo = userService.findByUserCode(loginModel.getUserCode());
            if (userPo == null) {
                userPo = getUserforClient(loginModel);
            }
            if(userPo==null){
                logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".userPo");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.RESOURCE_NOTFOUND.toString(),"userPo:"+loginModel.getUserCode()))).build();
            }

            UserTelevisionModel userTelevisionModel = new UserTelevisionModel();
            userTelevisionModel.setUserCode(userPo.getUserCode());
            userTelevisionModel.setScanStatus(UserEnum.ScanStatus.IsScan);
            userTelevisionModel.setTelevisionCode(loginModel.getTelevisionCode());
            redisClientTemplate.setex(loginModel.getTelevisionCode(), Integer.parseInt(expires), gson.toJson(userTelevisionModel));
            logger.debug("login scan:userTelevisionModel{}", gson.toJson(userTelevisionModel));

            return Response.ok(gson.toJson(new BaseResponse(this.requestId))).build();
        } catch (Exception e) {
            logger.error("login scan error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }

    /**
     * 用户确认登录
     * 1.同一时间用户只能登陆一个终端设备  当用户确认登录后
     *
     * @param requestBody
     * @param request
     * @param response
     * @return
     */
    @POST
    @Path("/confirm")
    public Response confirm(
            @QueryParam("requestId") String requestId,
            String requestBody,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            BaseResponse baseResponse = new BaseResponse(this.requestId);
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", this.requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
            LoginModel loginModel = gson.fromJson(requestBody, LoginModel.class);
            if (StringUtils.isBlank(loginModel.getTelevisionCode())) {
                logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.PARAMETER_MISS.toString() + ".televisionCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "televisionCode"))).build();
            }
            if (StringUtils.isBlank(loginModel.getUserCode())) {
                logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.PARAMETER_MISS.toString() + ".userCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode"))).build();
            }
            //验证用户是否存在
            UserPo userPo = userService.findByUserCode(loginModel.getUserCode());
            if (userPo == null) {
                logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".userPo");
                return Response.ok(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "userPo"))).build();
            }
            //验证设备与用户关联 是否存在
            String televisionRedis = redisClientTemplate.get(loginModel.getTelevisionCode());
            UserTelevisionModel userTelevisionModel = gson.fromJson(televisionRedis, UserTelevisionModel.class);
            if (userTelevisionModel == null || StringUtils.isBlank(userTelevisionModel.getUserCode())) {
                logger.error("requestId：" + this.requestId + "," + "userTelevisionModel or userTelevisionModel.getUserCode is null.");
                return Response.ok(gson.toJson(baseResponse)).build();
            }
            if (!userTelevisionModel.getScanStatus().equals(UserEnum.ScanStatus.IsScan)) {
                logger.error("requestId：" + this.requestId + "," + ResponseCode.FORBIDDEN_NOSCAN.toString());
                return Response.ok(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_NOSCAN.toString()))).build();
            }
            userTelevisionModel.setScanStatus(UserEnum.ScanStatus.ConfirmLogin);
            logger.debug("login confirm:userTelevisionModel{}", gson.toJson(userTelevisionModel));
            //TODO 异步队列 将绑定删除 暂定20秒
            redisClientTemplate.setex(loginModel.getTelevisionCode(), Integer.parseInt(timeoutExpires), gson.toJson(userTelevisionModel));
/*            UserTelevisionPo userTelevisionPo= userTelevisionService.findByTelevisionCode(loginModel.getTelevisionCode());
            if(userTelevisionPo==null){
                logger.error("login scan Exception：requestId：" +  this.requestId + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".userTelevisionPo");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.RESOURCE_NOTFOUND.toString(), "userTelevisionPo"))).build();
            }
            //TODO 当用户确认登录后，则删除用户上次登录的的设备号与用户的关联
//            UserTelevisionPo userTelevisionPo1=userTelevisionService.findByUserCode(userPo.getUserCode());
//            if(userTelevisionPo1!=null){
//                userTelevisionService.delete(userTelevisionPo1);
//            }
            //修改用户扫码状态
            userTelevisionPo.setScanStatus(UserEnum.ScanStatus.ConfirmLogin);
            userTelevisionService.save(userTelevisionPo);*/


            String token = "TK" + Digest.md5Digest16(userPo.getUserCode() + DateUtils.nowTimeMillis() + RandomUtils.getRandom(6)).toUpperCase();
            String refreshToken = Digest.md5Digest(userPo.getUserCode() + token);
            //todo 持久化？
            //TODO 生成 token 当用户原存在token
            TokenPo tokenPo = tokenService.findByUserCode(userPo.getUserCode());
            if (tokenPo != null) {
                tokenService.delete(tokenPo.getId());
            }
            TokenPo po = new TokenPo();
            po.setExpires(expires);
            po.setUserCode(userPo.getUserCode());
            po.setToken(token);
            po.setRefreshToken(refreshToken);
            po.setTelevisionCode(loginModel.getTelevisionCode());
            tokenService.save(po);
            //TODO redis删除原token
            String oldToken=redisClientTemplate.get(userPo.getUserCode());
            if(StringUtils.isNotBlank(oldToken)){
                redisClientTemplate.del(oldToken);
            }
            TokenModel tokenModel = new TokenModel();
            tokenModel.setRefreshToken(refreshToken);
            tokenModel.setUserCode(userPo.getUserCode());
            redisClientTemplate.setex(token, Integer.parseInt(expires), gson.toJson(tokenModel));
            redisClientTemplate.setex(userPo.getUserCode(), Integer.parseInt(expires), token);

            logger.debug("login scan token=" + token);
            logger.debug("login scan refreshToken=" + refreshToken);
            return Response.ok(gson.toJson(baseResponse)).build();
        } catch (Exception e) {
            logger.error("login scan error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }

    /**
     * 用户取消登录
     * 1.删除设备与用户的绑定关系
     *
     * @param requestBody
     * @param request
     * @param response
     * @return
     */
    @POST
    @Path("/cancel")
    public Response cancel(
            @QueryParam("requestId") String requestId,
            String requestBody,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", this.requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
            LoginModel loginModel = gson.fromJson(requestBody, LoginModel.class);
            if (StringUtils.isBlank(loginModel.getTelevisionCode())) {
                logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.PARAMETER_MISS.toString() + ".televisionCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "televisionCode"))).build();
            }
            if (StringUtils.isBlank(loginModel.getUserCode())) {
                logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.PARAMETER_MISS.toString() + ".userCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "userCode"))).build();
            }
            String token = redisClientTemplate.get(loginModel.getUserCode());

            redisClientTemplate.del(loginModel.getUserCode());
            redisClientTemplate.del(loginModel.getTelevisionCode());

            if (StringUtils.isNotBlank(token)) {
                redisClientTemplate.del(token);
                TokenPo tokenPo = tokenService.findByToken(token);
                if (tokenPo != null) {
                    tokenService.delete(tokenPo.getId());
                }
            }
            return Response.ok(gson.toJson(new BaseResponse(this.requestId))).build();
        } catch (Exception e) {
            logger.error("login scan error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }



    UserPo getUserforClient(LoginModel loginModel) {
     /*   UserInfoModel userInfo = jdbcUtil.getUserInfo(loginModel.getUserCode());
        if (userInfo == null) {
            logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".userPo");
            return null;
        }*/
        UserModel userModel=userInfoClient.getUserInfo(loginModel.getUserCode());
        if(userModel==null){
            logger.error("login scan Exception：requestId：" + this.requestId + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".userPo");
            return null;
        }
        UserPo userPo = new UserPo();
        userPo.setUserCode(userModel.getUserCode());
        userPo.setMobile(userModel.getMobile());
        userPo.setPassword(userModel.getPassword());
        userPo.setVIP(false);
        userPo.setUserName(userModel.getUserName());
        userPo.setEmail(userModel.getEmail());
        userPo.setRegisterSource("15");
        userPo.setMarketChannel("000");
        userPo = userService.save(userPo);
        return userPo;
    }
    //通过手机号查询用户信息
    UserPo getUserforClientByPhone(String mobile) {
    	UserRequestModel userRequestModel = new UserRequestModel();
    	if (null != mobile && 0 < mobile.length()) {
    		userRequestModel.setPhone(mobile);
		}
    	UserPo userPo = new UserPo();
			try {
				BaseResponse userInfoByPhone = userClient.getUserInfoByPhone(userRequestModel);
				if (userInfoByPhone.getHttpCode().equals("200")) {
					Object result = userInfoByPhone.getResult();
					Map<String,Object> map = gson.fromJson(gson.toJson(result), new TypeToken<Map<String,Object>>() {}.getType());
					Object object = map.get("userModel");
					String json= gson.toJson(object);
					Map<String,String> map2 = gson.fromJson(json, new TypeToken<Map<String,String>>() {}.getType());
					userPo.setUserCode(map2.get("openId"));
			        userPo.setMobile(mobile);
			        userPo.setPassword(map2.get("password"));
			        userPo.setVIP(false);
			        userPo.setUserName(mobile);
			        userPo.setEmail(null);
			        userPo.setRegisterSource("15");
			        userPo.setMarketChannel("000");
			        userPo = userService.save(userPo);
				}
			} catch (ClientProtocolException e) {
				
					// TODO Auto-generated catch block
					e.printStackTrace();
					
			} catch (IOException e) {
				
					// TODO Auto-generated catch block
					e.printStackTrace();
					
			}
    	return userPo;
    }
    TokenModel getTokenInRedis(String token) {
        TokenModel tokenModel = new TokenModel();
        return tokenModel;
    }
}
