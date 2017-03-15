package com.eeduspace.cibn.ws;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.eeduspace.cibn.alipay.AlipayBizModel;
import com.eeduspace.cibn.alipay.ToAlipayQrTradePay;
import com.eeduspace.cibn.model.LoginModel;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.util.CommonUtil;
import com.eeduspace.cibn.util.OrderUtil;
import com.google.gson.Gson;

@Component
@Path(value = "/test")
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
public class TestWs {
    private final Logger logger = LoggerFactory.getLogger(TestWs.class);
	private Gson gson = new Gson();
	@Value("${cibn.alipay.alipayAPPID}")
	private String alipayAPPID;
	@Value("${cibn.alipay.alipayPrivateKey}")
	private String alipayPrivateKey;
	@Value("${cibn.alipay.alipayPublicKey}")
	private String alipayPublicKey;
	private String  requestId;
	@POST
	public Response processGet() {
		return dispatch();
	}

	
	@GET
	@Path("/testgets/{names}")
	public Response processPosts(@QueryParam("abcd") String jjjj,@PathParam("names")String ss) {
		System.out.println(ss+";;;"+jjjj);
		return dispatch();
	}
	
	
	@GET
	@Path("/testget/{name}")
	public Response processPost(@QueryParam("abc") String jjjj,@PathParam("name")String ss) {
		System.out.println(ss+";;;"+jjjj);
		return dispatch();
	}
	
	@GET
	@Path("/testalipay")
	public Response testAlipay() {
		logger.info("start----");
		AlipayBizModel alipayBizModel=new AlipayBizModel();
		alipayBizModel.setOut_trade_no(OrderUtil.GetOrderNumber("ZFB"));
		alipayBizModel.setSubject("tedtTN");
		alipayBizModel.setTotal_amount("0.01");
		String notifyURL="http://220.249.22.170:8080/cibnws/aplipay_notice/vip_alipay";
		AlipayTradePrecreateResponse alipayResponse=ToAlipayQrTradePay.qrPay(alipayBizModel, notifyURL,alipayPublicKey,alipayAPPID,alipayPrivateKey);
		BaseResponse baseResponse=new BaseResponse();
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	public Response dispatch() {
		// userService.findAll();
		// managerService.findAll();
		// redisClientTemplate.get("test");
		// UserModel userModel=new UserModel();
		// userModel.setCityCode("cityCode");
		// userModel.setAction("action");
		return Response.ok(
				gson.toJson(BaseResponse.setResponse(new BaseResponse(
						"requestId"),"success")))
				.build();

	}
	 
	
	    @POST
	    @Path("/postTest")
	    public Response postTest(
	           // @QueryParam("requestId") String requestId,
	            String requestBody,
	            @Context HttpServletRequest request,
	            @Context HttpServletResponse response) {
            logger.info("requestBody {}",requestBody);
	    	 try {
	    		 this.requestId = requestId; 
	             System.out.println("~~~~~~~~~~~~~~~~~test~~~~~~~~~~~~~~~~requestId="+requestId);
	             logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);
	             LoginModel loginModel=gson.fromJson(requestBody,LoginModel.class);
                 logger.error("~~~~~~~~~~~~~~~~~~loginModel Password="+loginModel.getPassword());
                 logger.error("~~~~~~~~~~~~~~~~~~loginModel UserName="+loginModel.getUserName());
	             return Response.ok(gson.toJson(new BaseResponse(requestId))).build();
	         }catch (Exception e){
	             logger.error("logout  error:", e);
	             return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
	         }

	     }
}
