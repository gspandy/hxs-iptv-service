package com.eeduspace.cibn.ws;


import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eeduspace.cibn.client.UserClient;
import com.eeduspace.cibn.client.UserInfoClient;
import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.LoginModel;
import com.eeduspace.cibn.model.UserInfoModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VerifyCodeModel;
import com.eeduspace.cibn.model.request.UserRequestModel;
import com.eeduspace.cibn.model.response.UserInfoResponse;
import com.eeduspace.cibn.persist.po.TokenPo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.redis.RedisClientTemplate;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.response.ClientResponse;
import com.eeduspace.cibn.response.ClientUserResponse;
import com.eeduspace.cibn.service.TokenService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.service.UserTelevisionService;
import com.eeduspace.cibn.util.CommonUtil;
import com.eeduspace.cibn.util.SMSUtil;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
import com.eeduspace.uuims.comm.util.base.ValidateUtils;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;
import com.eeduspace.uuims.comm.util.base.json.GsonUtil;
import com.google.gson.Gson;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:用户管理
 */
@Component
@Path(value = "/user")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class UserWs {
    private final Logger logger = LoggerFactory.getLogger(UserWs.class);
    private String requestId;
    private Gson gson = new Gson();
    
    @Inject
    private UserClient userClient;
    @Inject
    private UserService userService;

    @Inject
    private UserInfoClient userInfoClient;
    
    @Inject
    private UserTelevisionService userTelevisionService;

    @Inject
    private RedisClientTemplate redisClientTemplate;

    @Inject
    private TokenService tokenService;

    @Inject
    private SMSUtil SMSUtil;

    /**
     * 获取用户详情
     * @param userCode
     * @param requestBody
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/info/{userCode}")
    public Response getInfo(
            @QueryParam("requestId") String requestId,
            @PathParam("userCode") String userCode,
           // String requestBody,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {

        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
           // logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",this.requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);
            //UserModel userModel= userInfoClient.getUserInfo(userCode);
            //TODO 从资源总库获取
            UserPo userPo= userService.findByUserCode(userCode);
            if(userPo==null){
                logger.error("info user Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_MISS.toString(), "userCode"))).build();
            }

            BaseResponse baseResponse=new BaseResponse(this.requestId);
            baseResponse.setResult(CIBNConvert.fromUserPo(userPo,null));
            logger.debug("info user response:"+gson.toJson(baseResponse));
            return Response.ok(gson.toJson(baseResponse)).build();
        }catch (Exception e){
            logger.error("info user error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }


    /**
     * 用户登出
     * 1.清除token
     * 2.清除终端设备与用户关联
     * @param requestId
     * @param request
     * @param response
     * @return
     */
    @POST
    @Path("/logout")
    public Response logout(
            @QueryParam("requestId") String requestId,
            String requestBody,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",this.requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);

            LoginModel loginModel=gson.fromJson(requestBody,LoginModel.class);
            if(StringUtils.isBlank(loginModel.getTelevisionCode())){
                logger.error("logout Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".televisionCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "televisionCode"))).build();
            }
            if(StringUtils.isBlank(loginModel.getUserCode())){
                logger.error("logout Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "userCode"))).build();
            }
            String token = redisClientTemplate.get(loginModel.getUserCode());
            
            redisClientTemplate.del(loginModel.getUserCode());
            redisClientTemplate.del(loginModel.getTelevisionCode());
            
            if(StringUtils.isNotBlank(token)){
                redisClientTemplate.del(token);
            	 TokenPo tokenPo= tokenService.findByToken(token);
                 if(tokenPo!=null){
                     tokenService.delete(tokenPo.getId());
                 }
            
            }

            return Response.ok(gson.toJson(new BaseResponse(this.requestId))).build();
        }catch (Exception e){
            logger.error("logout  error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }

    }
    
    /**
     * 向UUIMS中增加注册
     * 用户添加与注册
     * 1.获取参数  验证
     * 2.调用client入库 UUIMS
     * 3.成功  执行入库cibn 否则 return
     * @param requestId
     * @return
     */
    @SuppressWarnings("unused")
	@POST
    @Path("/register")
    public Response registerOrCreateForUUIMS(
    		@QueryParam("requestId") String requestId,
    		String requestBody, 
    		@Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",this.requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);
            UserModel userModel=gson.fromJson(requestBody,UserModel.class);
            if(StringUtils.isBlank(userModel.getPassword())){
                logger.error("register Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "password"))).build();
            }
            if (StringUtils.isBlank(userModel.getMobile())) {
                logger.error("register Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_MISS.toString(), "mobile"))).build();
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userModel.getMobile())) {
            	 logger.error("register ValidateMobile Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_INVALID.toString()+".mobile");
                 return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "mobile"))).build();
            }
            BaseResponse baseResponse=new BaseResponse(this.requestId);
            //cibn验证手机号唯一
          //验证手机号唯一 去UUIMS 验证 当该手机号未被使用时则返回200
            UserRequestModel userRequestModelValidate = new UserRequestModel();
            userRequestModelValidate.setMobile(userModel.getMobile());
            BaseResponse clientBaseResponse = userClient.validateByMobile(userRequestModelValidate);
            if (clientBaseResponse == null || !"200".equals(clientBaseResponse.getHttpCode())) {
                logger.error("register  ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_DUPLICATE.toString()+".mobile");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "mobile");
            }
			/** ----------UUIMS注册用户----------- **/
			UserRequestModel userRequestModel = new UserRequestModel();
			userRequestModel.setPassword(Digest.md5Digest(userModel.getPassword()));
			userRequestModel.setPhone(userModel.getMobile());
			BaseResponse registerResponse = userClient.userRegister(userRequestModel);
			logger.debug("registerResponse {}:", gson.toJson(registerResponse));
			//UUIMS注册返回成功
			if (registerResponse.getHttpCode().equals("200")) {
				logger.debug("===================================================================> 成功 :" + gson.toJson(registerResponse));
				UserPo userPo = userService.findByMobile(userModel.getMobile());
				UserInfoResponse userInfoResponse = GsonUtil.fromObjectJson(gson.toJson(registerResponse), "result", "userModel",UserInfoResponse.class);
				// 判断本地存不存在用户
				if (userPo == null) {
					userPo = new UserPo();
				}
				userPo.setUserCode(userInfoResponse.getOpenId());
				userPo.setAccessKey(userInfoResponse.getAccessKey());
				userPo.setSecretKey(userInfoResponse.getSecretKey());
				userPo.setMobile(userRequestModel.getPhone());
				userPo.setUserName("cibn"+userRequestModel.getPhone().substring(3));
				userPo.setPassword(userRequestModel.getPassword());
				userPo.setVIP(false);
                //userPo.setMarketChannel("000");
				userPo.setMarketChannel(userModel.getMarketChannel()==null?"":userModel.getMarketChannel().substring(1));
                //userPo.setRegisterSource("9");
                //userPo.setRegisterSource("10");
                userPo.setRegisterSource(String.valueOf(userModel.getRegisterSource()));
                userPo.setRegisterDeviceType(userModel.getRegisterDeviceType());
                userPo.setOpenId(userInfoResponse.getOpenId());
				userService.save(userPo);
				baseResponse.setResult(CIBNConvert.fromUserPo(userPo, null));
				return Response.ok(gson.toJson(baseResponse)).build();
			} else {
				return Response.ok(gson.toJson(registerResponse)).build();
			}
        }catch (Exception e){
            logger.error("register  error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }


    /**
     * 获取注册验证码
     * @param requestId
     * @param requestBody
     * @param request
     * @param response
     * @return
     */

    @POST
    @Path("/verifyCode")
    public Response verifycode(
            @QueryParam("requestId") String requestId,
            String requestBody,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",this.requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);
            VerifyCodeModel verifyCodeModel=gson.fromJson(requestBody,VerifyCodeModel.class);
            if (StringUtils.isBlank(verifyCodeModel.getMobile())) {
                logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "mobile"))).build();
            }
            if (StringUtils.isBlank(verifyCodeModel.getType())) {
                logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".type");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "type"))).build();
            }
            
         /*   //cibn验证手机号唯一
            UserPo userPo = userService.findByMobile(verifyCodeModel.getMobile());
            if (userPo != null) {
                logger.error("register  ValidateMobile Exception：requestId："+ this.requestId+","+ ResponseCode.RESOURCE_InUSED.toString()+".mobile");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.RESOURCE_InUSED.toString(), "mobile"))).build();
            }*/
            
            
            //TODO 验证类型
            //TODO 验证手机号 是否已经存在
            String code= SMSUtil.send(verifyCodeModel.getMobile(),verifyCodeModel.getType());
            verifyCodeModel=new VerifyCodeModel();
            verifyCodeModel.setCode(code);
            BaseResponse baseResponse=new BaseResponse(this.requestId);
            baseResponse.setResult(verifyCodeModel);
            return Response.ok(gson.toJson(baseResponse)).build();
        }catch (Exception e){
            logger.error("logout  error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }
	
	 /**
     * uuims修改密码
     */
    @POST
    @Path("/pwd/edit")
    public Response editPwd(
    		@QueryParam("requestId") String requestId,
    		String requestBody, 
    		@Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            BaseResponse editPwdResponse= userService.editPwd(userRequestModel);
            editPwdResponse.setRequestId(baseResponse.getRequestId());
            return Response.ok(gson.toJson(editPwdResponse)).build();
        } catch (Exception e) {
            logger.error("editPwd error:{}", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }
    
	/**
	 * uuims 后台
	 * 找回密码
	 */
	@POST
	@Path("/pwd/find")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uuimsFindPwd(String requestBody) {
		BaseResponse baseResponse = new BaseResponse();
		UserModel userModel = gson.fromJson(requestBody, UserModel.class);
		try {
			UserPo userInfo=userService.findByMobile(userModel.getMobile());
			if(userInfo==null){
				return Response.ok(BaseResponse.setResponse(new BaseResponse(),ResponseCode.RESOURCE_NOTFOUND.toString())).build();
			}
			UserRequestModel userRequestModel = new UserRequestModel();
			userRequestModel.setOpenId(userInfo.getOpenId());
			userRequestModel.setMobile(userModel.getMobile());
			userRequestModel.setPhone(userModel.getMobile());
			userRequestModel.setPassword(Digest.md5Digest(userModel.getPassword()));
			BaseResponse returnResponse=userClient.resetPwd(userRequestModel);
			if(returnResponse.getHttpCode().equals("200")){
				logger.debug("======================================================>   returnResponse :  "+returnResponse);
				userInfo.setPassword(Digest.md5Digest(userInfo.getPassword()));
				userService.save(userInfo);
				userModel.setUserCode(userInfo.getUserCode());
				userModel.setPassword(null);
				baseResponse.setResult(userModel);
				return Response.ok(gson.toJson(baseResponse)).build();
			}else{
				logger.debug("======================================================>   returnResponse :  "+"     失败");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), returnResponse.getCode()))).build();
			}
		} catch (Exception e) {
			logger.error("findPwd error:{}", e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString()))).build();
		}
	}
	
		/**
		 * 找回密码手机号验证
		 * @param requestBody
		 * @return
		 */
	 	@GET
	    @Path("/verifyPhone/{mobile}")
	    public Response verifyPhone(
	    		@QueryParam("requestId") String requestId,
	            @PathParam("mobile") String mobile,
	            @Context HttpServletRequest request,
	            @Context HttpServletResponse response) {
            if (StringUtils.isBlank(mobile)) {
                logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "mobile"))).build();
            }
			try {
				this.requestId = requestId;
	            if (StringUtils.isBlank(this.requestId)) {
	                this.requestId = UIDGenerator.getUUID();
	            }
	            logger.info("HttpServletRequest: requestId:{},ContextPath:{},RequestURI:{},mobile:{}",this.requestId,request.getContextPath(),request.getRequestURI(),mobile);
	            BaseResponse baseResponse = new BaseResponse();
	            UserRequestModel userRequestModelValidate = new UserRequestModel();
	            userRequestModelValidate.setMobile(mobile);
	            BaseResponse clientBaseResponse = userClient.validateByMobile(userRequestModelValidate);
	            Boolean flag = true;
	            if (clientBaseResponse == null || !"200".equals(clientBaseResponse.getHttpCode())) {
	            	flag = false;
	            }
	            baseResponse.setResult(flag);
	            
				return Response.ok(gson.toJson(baseResponse)).build();
					
			} catch (Exception e) {
				logger.error("verifyPhone error:{}", e);
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString()))).build();
			}
	    }
	 	
	 	@SuppressWarnings("unused")
		@POST
		@Path("/transfer")
		public Response transfer(@QueryParam("requestId") String requestId, String requestBody,
				@Context HttpServletRequest request, @Context HttpServletResponse response) {
			List<UserPo> userPoList = userService.findAll();
			for(UserPo userPo : userPoList) {
				BaseResponse baseResponse=new BaseResponse(this.requestId);
				try {
					//cibn验证手机号唯一
					//验证手机号唯一 去UUIMS 验证 当该手机号未被使用时则返回200
		            UserRequestModel userRequestModelValidate = new UserRequestModel();
		            userRequestModelValidate.setMobile(userPo.getMobile());
		            BaseResponse clientBaseResponse = userClient.validateByMobile(userRequestModelValidate);
		            if (clientBaseResponse == null || !"200".equals(clientBaseResponse.getHttpCode())) {
		                logger.error("register  ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_DUPLICATE.toString()+".mobile");
		                //return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "mobile");
		               continue;
		            }
					/** ----------UUIMS注册用户----------- **/
					UserRequestModel userRequestModel = new UserRequestModel();
					userRequestModel.setPassword(Digest.md5Digest(userPo.getPassword()));
					userRequestModel.setPhone(userPo.getMobile());
					BaseResponse registerResponse = userClient.userRegister(userRequestModel);
					logger.debug("registerResponse {}:", gson.toJson(registerResponse));
					//UUIMS注册返回成功
					if (registerResponse.getHttpCode().equals("200")) {
						logger.debug("===================================================================> 成功 :" + gson.toJson(registerResponse));
						UserInfoResponse userInfoResponse = GsonUtil.fromObjectJson(gson.toJson(registerResponse), "result", "userModel",UserInfoResponse.class);
						userPo.setUserCode(userInfoResponse.getOpenId());
						userPo.setAccessKey(userInfoResponse.getAccessKey());
						userPo.setSecretKey(userInfoResponse.getSecretKey());
						userPo.setMobile(userRequestModel.getPhone());
						userPo.setUserName("cibn"+userRequestModel.getPhone().substring(3));
						userPo.setPassword(userRequestModel.getPassword());
						userPo.setVIP(false);
		                userPo.setMarketChannel("010");
		                userPo.setRegisterSource("9");
						userService.save(userPo);
						baseResponse.setResult(CIBNConvert.fromUserPo(userPo, null));
					}
				} catch (Exception e){
		            logger.error("register  error:", e);
		            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		        }
			}
			return null;
		}
	 	
	 	public static void  main(String args[]) {
	 		String passwd = "123456";
	 		String md5Passwd = Digest.md5Digest(passwd);
	 		System.out.println(md5Passwd);
	 	}
	 	/**
	     * 调用uuims给用户发送验证码
	     * @param requestId
	     * @param requestBody
	     * @param request
	     * @param response
	     * @return
	     */

	    @POST
	    @Path("/sendSms")
	    public Response sendSms(
	            @QueryParam("requestId") String requestId,
	            String requestBody,
	            @Context HttpServletRequest request,
	            @Context HttpServletResponse response) {
	        try {
	            this.requestId = requestId;
	            if (StringUtils.isBlank(this.requestId)) {
	                this.requestId = UIDGenerator.getUUID();
	            }
	            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",this.requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);
	            UserRequestModel userRequestModel=gson.fromJson(requestBody,UserRequestModel.class);
	            if (StringUtils.isBlank(userRequestModel.getMobile())) {
	                logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
	                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "mobile"))).build();
	            }
	            //调用uuims给用户发送验证码
	            userRequestModel.setPhone(userRequestModel.getMobile());
	            BaseResponse sendSms = userClient.sendSms(userRequestModel);
	            sendSms.setRequestId(this.requestId);
	            return Response.ok(gson.toJson(sendSms)).build();
	        }catch (Exception e){
	            logger.error("sendSms  error:", e);
	            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
	        }
	    } 	
	    /**
	     * 调用uuims校验验证码
	     * @param requestId
	     * @param requestBody
	     * @param request
	     * @param response
	     * @return
	     */

	    @POST
	    @Path("/validateSms")
	    public Response validateSms(
	            @QueryParam("requestId") String requestId,
	            String requestBody,
	            @Context HttpServletRequest request,
	            @Context HttpServletResponse response) {
	        try {
	            this.requestId = requestId;
	            if (StringUtils.isBlank(this.requestId)) {
	                this.requestId = UIDGenerator.getUUID();
	            }
	            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",this.requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);
	            UserRequestModel userRequestModel=gson.fromJson(requestBody,UserRequestModel.class);
	            if (StringUtils.isBlank(userRequestModel.getMobile())) {
	                logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
	                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "mobile"))).build();
	            }
	            if (StringUtils.isBlank(userRequestModel.getCode())) {
	            	logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".code");
	            	return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "code"))).build();
	            }
	            if (StringUtils.isBlank(userRequestModel.getTicket())) {
	            	logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".ticket");
	            	return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "ticket"))).build();
	            }
	            //调用uuims给用户发送验证码
	            userRequestModel.setPhone(userRequestModel.getMobile());
	            BaseResponse sendSms = userClient.validateSms(userRequestModel);
	            sendSms.setRequestId(this.requestId);
	            return Response.ok(gson.toJson(sendSms)).build();
	        }catch (Exception e){
	            logger.error("validateSms  error:", e);
	            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
	        }
	    } 
	    /**
	     * 调用uuims修改密码
	     * @param requestId
	     * @param requestBody
	     * @param request
	     * @param response
	     * @return
	     */

	    @POST
	    @Path("/resetPassword")
	    public Response resetPassword(
	            @QueryParam("requestId") String requestId,
	            String requestBody,
	            @Context HttpServletRequest request,
	            @Context HttpServletResponse response) {
	        try {
	            this.requestId = requestId;
	            if (StringUtils.isBlank(this.requestId)) {
	                this.requestId = UIDGenerator.getUUID();
	            }
	            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",this.requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);
	            UserRequestModel userRequestModel=gson.fromJson(requestBody,UserRequestModel.class);
	            //UserModel userModel=gson.fromJson(requestBody,UserModel.class);
	            if (StringUtils.isBlank(userRequestModel.getMobile())) {
	                logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
	                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "mobile"))).build();
	            }
	            if (StringUtils.isBlank(userRequestModel.getPassword())) {
	            	logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
	            	return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "password"))).build();
	            }
	            if (StringUtils.isBlank(userRequestModel.getTicket())) {
	            	logger.error("verifyCode Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".ticket");
	            	return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "ticket"))).build();
	            }
	            //在本地库查询用户信息
	            UserPo userInfo=userService.findByMobile(userRequestModel.getMobile());
	            if (null == userInfo) {
	            	return Response.ok(BaseResponse.setResponse(new BaseResponse(),ResponseCode.RESOURCE_NOTFOUND.toString())).build();
				}
	            userInfo.setPassword(Digest.md5Digest(userRequestModel.getPassword()));
	            //调用uuims给用户发送验证码
	            userRequestModel.setPhone(userRequestModel.getMobile());
	            userRequestModel.setPassword(Digest.md5Digest(userRequestModel.getPassword()));
	            BaseResponse sendSms = userClient.resetPassword(userRequestModel);
	            if (sendSms.getHttpCode().equals("200")) {
	            	userService.save(userInfo);
	            	sendSms.setRequestId(this.requestId);
	            	UserModel userModel = new UserModel();
	            	userModel.setMobile(userRequestModel.getMobile());
	            	userModel.setPassword(null);
	            	userModel.setUserCode(userInfo.getUserCode());
	            	sendSms.setResult(userModel);
				}
	            return Response.ok(gson.toJson(sendSms)).build();
	        }catch (Exception e){
	            logger.error("validateSms  error:", e);
	            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
	        }
	    } 
}
