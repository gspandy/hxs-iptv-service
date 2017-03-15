package com.eeduspace.cibn.ws;

import java.util.ArrayList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.eeduspace.cibn.alipay.AlipayBizModel;
import com.eeduspace.cibn.alipay.ToAlipayQrTradePay;
import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.model.WxPayResponseModel;
import com.eeduspace.cibn.model.response.PayResult;
import com.eeduspace.cibn.persist.enumeration.BuyTypeEnum;
import com.eeduspace.cibn.persist.po.VipBuyRecord;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.VipBuyRecordService;
import com.eeduspace.cibn.util.OrderUtil;
import com.eeduspace.cibn.weixin.WeixinPay;
import com.eeduspace.uuims.rescode.ResourceName;
import com.google.gson.Gson;
/**
 * VIP服务
 * @author zhuchaowei
 * 2016年4月22日
 * Description
 */
@Component
@Path(value = "/vip_buy")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class VipBuyRecordWs {
	private Gson gson = new Gson();
	/***
	 * VIP购买微信支付回调通知地址
	 */
	@Value("${cibn.weixin.vipbuy.notify_url}")
	private String vipbuyNoticeUrl;
	@Value("${cibn.order.prefix}")
	private String orderPrefix;
	@Value("${cibn.weixin.appid}")
	private String appId;
	@Value("${cibn.weixin.mch_id}")
	private String mchId;
	@Value("${cibn.weixin.trade_type}")
	private String tradeType;
	@Value("${cibn.weixin.partnerKey}")
	private String partnerKey;
	
	
	@Value("${cibn.alipay.alipayAPPID}")
	private String alipayAPPID;
	@Value("${cibn.alipay.alipayPrivateKey}")
	private String alipayPrivateKey;
	@Value("${cibn.alipay.alipayPublicKey}")
	private String alipayPublicKey;
	/**微信统一下单url*/
	@Value("${cibn.weixin.unifiedorder}")
	private  String unifiedorder;
	
	@Value("${cibn.alipay.vipbuy.notify_url}")
	private  String notifyURL;
	
	private final Logger logger = LoggerFactory.getLogger(VipBuyRecordWs.class);
	@Inject
	private VipBuyRecordService vipBuyRecordService;
	/**
	 * 1、保存VIP购买  APP请求 把购买信息传送过来  先保存支付信息  支付状态为 未支付
	 * 2、平台去请求支付参数  返回给APP产生支付的二维码
	 * 3、用户支付成功后 回调平台 ，平台收到信息后找到 保存的数据  更新支付状态
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月20日 下午4:27:43
	 * @param orderInfo  VIP订单信息
	 * @param userInfo   用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Path("/save")
	public Response saveVipBuyRecord(@QueryParam("requestId") String requestId, String requestBody,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
        logger.info("HttpServletRequest: requestBody:{}", requestBody);
		VipOrderModel vipOrderModel = gson.fromJson(requestBody,VipOrderModel.class);
		UserModel userModel = vipOrderModel.getUserModel();
		if(StringUtils.isBlank(userModel.getUserCode())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"USERCODE");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "USERCODE"))).build();
		}
		if(StringUtils.isBlank(vipOrderModel.getPayType())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"paytype");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "paytype"))).build();
		}
		if(vipOrderModel.getOrderPrice()==null){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"ORDERPRICE");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "ORDERPRICE"))).build();
		}
		if(vipOrderModel.getVipOrderType()==null){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"VIPORDERTYPE");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "ORDERTYPE"))).build();
		}
		BaseResponse baseResponse=new BaseResponse();
		try {
			String orderSn=OrderUtil.GetOrderNumber("VIP");
			orderSn=orderSn+(vipOrderModel.getMarketChannel()==null?"":vipOrderModel.getMarketChannel());
			vipOrderModel.setOrderSn(orderSn);
			vipOrderModel.setOrderName(orderPrefix+"-"+vipOrderModel.getOrderName());
			vipOrderModel.setPayType(vipOrderModel.getPayType());
			WxPayResponseModel payResponseModel=new WxPayResponseModel();
			if(vipOrderModel.getPayType().equals("weixinpay")){
				// 支付请求接口    待添加 构造微信统一下单接口  返回支付二维码 url 和 商户订单号给前台
				WeixinPay wxpay=new WeixinPay();
				wxpay.setTotal_fee(OrderUtil.changeY2F(vipOrderModel.getOrderPrice()+""));//支付价格 单位分 
				wxpay.setOut_trade_no(orderSn);
				wxpay.setBody(vipOrderModel.getOrderName());
				wxpay.setAppid(appId);
				wxpay.setMch_id(mchId);
				wxpay.setPartnerKey(partnerKey);
				wxpay.setUnifiedorder(unifiedorder);
				wxpay.setTrade_type(tradeType);
				wxpay.setSpbill_create_ip(userModel.getIp());
				wxpay.setNotify_url(vipbuyNoticeUrl);
				payResponseModel=wxpay.submitXmlGetPayResponse();//调用微信 统一下单接口 返回支付二维码和预支付订单号
				payResponseModel.setOutTradeNo(orderSn);
			}else if(vipOrderModel.getPayType().equals("alipay")){
				AlipayBizModel alipayBizModel=new AlipayBizModel();
				alipayBizModel.setOut_trade_no(orderSn);
				alipayBizModel.setSubject(vipOrderModel.getOrderName());
				alipayBizModel.setTotal_amount(vipOrderModel.getOrderPrice()+"");
				AlipayTradePrecreateResponse alipayResponse=ToAlipayQrTradePay.qrPay(alipayBizModel, notifyURL,alipayPublicKey,alipayAPPID,alipayPrivateKey);
				payResponseModel.setCodeUrl(alipayResponse.getQrCode());
				payResponseModel.setOutTradeNo(orderSn);
			}else {
	            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_INVALID.toString(), "paytype"))).build();
			}
			baseResponse.setResult(payResponseModel);
			vipBuyRecordService.saveVipRecord(vipOrderModel, userModel);
		} catch (Exception e) {
			logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "VIPBUY"))).build();
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	/**
	 * 购买记录分页列表
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月20日 下午6:06:20
	 * @param userInfo
	 * @param pageable
	 * @return
	 */
	@GET
	@Path("/get_vip_buy_list/{userCode}/{pageNumber}/{size}")
	public Response vipBuyRecordpageList(@Context HttpServletRequest httpRequest,@QueryParam("requestId") String requestId,@PathParam("userCode") String userCode,@PathParam("pageNumber") Integer pageNumber,@PathParam("size") Integer size){
		if(StringUtils.isBlank(userCode)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"USERCODE");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "USERCODE"))).build();
		}
		BaseResponse baseResponse=new BaseResponse();
		try {
			Pageable p=new PageRequest(pageNumber, size);
			Page<VipBuyRecord> page=vipBuyRecordService.findByUserCodeAndIsDelAndBuyType(userCode, false,true, p);
			List<VipBuyRecord> vipBuyRecords=page.getContent();
			List<VipOrderModel> vipOrderModels=new ArrayList<>();
			for (VipBuyRecord vipBuyRecord : vipBuyRecords) {
				VipOrderModel vipOrderModel= CIBNConvert.vipBuyRecord2VipOrderModel(vipBuyRecord);
				vipOrderModels.add(vipOrderModel);
			}
			Page<VipOrderModel> newPag=new PageImpl<VipOrderModel>(vipOrderModels, p, Long.valueOf(page.getTotalElements()));
			baseResponse.setResult(newPag);
		} catch (Exception e) {
			logger.error("requestId：{},createManager Exception：", requestId,e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "VIPBUY"))).build();
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	/**
	 * 删除购买记录
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 上午9:22:59
	 * @param orderInfo 订单信息model
	 * @return
	 */
	@POST
	@Path("/delete/{orderUUID}")
	public Response deleteVipBuyRecord(@Context HttpServletRequest httpRequest,@QueryParam("requestId") String requestId,@PathParam("orderUUID")String  orderUUID,@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		if(StringUtils.isBlank(orderUUID)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"ORDERUUID");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "ORDERUUID"))).build();
		}
		try {
			vipBuyRecordService.deleteByUUID(orderUUID);
		} catch (Exception e) {
			 logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), ResourceName.MANAGER.toString()))).build();
		}
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setResult("deleteSuccess");
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	@GET
	@Path("/vip_buy_details/{orderUUID}")
	public Response vipBuyRecordDetail(@Context HttpServletRequest httpRequest, @QueryParam("requestId") String requestId,@Context HttpServletRequest request,
            @Context HttpServletResponse response ,@PathParam("orderUUID")String orderUUID){
		if(StringUtils.isBlank(orderUUID)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"ORDERUUID");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "ORDERUUID"))).build();
		}
		VipOrderModel vipOrderModel=new VipOrderModel();
		try {
			VipBuyRecord vipBuyRecord=vipBuyRecordService.findByUUID(orderUUID);
			vipOrderModel=CIBNConvert.vipBuyRecord2VipOrderModel(vipBuyRecord);
		} catch (Exception e) {
			logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "VIPBUY"))).build();
		}
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setResult(vipOrderModel);
		return Response.ok(gson.toJson(baseResponse)).build(); 
	}
	

	
	/**
	 * 通知客户端支付结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月25日 下午2:45:35
	 * @param orderSn
	 * @return
	 */
	@GET
	@Path("/find_order/{orderSn}")
	public Response findOrder(@Context HttpServletRequest httpRequest,@QueryParam("requestId")String requestId,@PathParam("orderSn") String orderSn){
		if(StringUtils.isBlank(orderSn)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"orderSn");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "orderSn"))).build();
		}
		VipBuyRecord vipBuyRecord=vipBuyRecordService.findByOrderSn(orderSn);
		BaseResponse baseResponse=new BaseResponse();
		PayResult payResult=new PayResult();
		if(vipBuyRecord.isPay()){
			payResult.setPay(true);
		}else{
			payResult.setPay(false);
		}
		baseResponse.setResult(payResult);
		return Response.ok(gson.toJson(baseResponse)).build();
	}
}
