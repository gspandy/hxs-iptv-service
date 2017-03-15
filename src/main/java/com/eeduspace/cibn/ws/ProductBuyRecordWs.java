
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductBuyRecord.java 
	 * 包名:com.eeduspace.cibn.ws 
	 * 创建日期:2016年12月19日下午3:58:18 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.ws;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.eeduspace.cibn.alipay.AlipayBizModel;
import com.eeduspace.cibn.alipay.ToAlipayQrTradePay;
import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.ProductOrderModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.model.WxPayResponseModel;
import com.eeduspace.cibn.model.response.PayResult;
import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
import com.eeduspace.cibn.persist.po.ProductPo;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.ProductBuyRecordService;
import com.eeduspace.cibn.service.ProductService;
import com.eeduspace.cibn.util.OrderUtil;
import com.eeduspace.cibn.weixin.WeixinPay;
import com.google.gson.Gson;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductBuyRecord    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 下午3:58:18    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 下午3:58:18    
 * 修改备注：       
 * @version </pre>    
 */
@Component
@Path(value = "/product_buy")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ProductBuyRecordWs {

	@Value("${cibn.weixin.productbuy.notify_url}")
	private String productbuyNoticeUrl;
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
	
	@Value("${cibn.alipay.productbuy.notify_url}")
	private  String notifyURL;
	
	private final Logger logger = LoggerFactory.getLogger(VipBuyRecordWs.class);
	
	private Gson gson = new Gson();
	
	@Inject
	private ProductBuyRecordService productBuyRecordService;
	@Inject
	private ProductService productService;
	@POST
	@Path("/saveProductBuyRecord")
	public Response saveProductBuyRecord(@QueryParam("requestId") String requestId, String requestBody,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
        logger.info("HttpServletRequest: requestBody:{}", requestBody);
		ProductOrderModel productOrderModel = gson.fromJson(requestBody,ProductOrderModel.class);
		if(StringUtils.isBlank(productOrderModel.getUserCode())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"USERCODE");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "USERCODE"))).build();
		}
		if(StringUtils.isBlank(productOrderModel.getIp())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"USERCODE");
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "IP"))).build();
		}
		if(StringUtils.isBlank(productOrderModel.getCtbCode())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"CtbCode");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "CtbCode"))).build();
		}
		if(StringUtils.isBlank(productOrderModel.getPayType())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"paytype");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "paytype"))).build();
		}
		if(productOrderModel.getOrderPrice()==null){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"ORDERPRICE");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "ORDERPRICE"))).build();
		}
		if(productOrderModel.getProductOrderType()==null){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"ProductOrderType");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "ProductOrderType"))).build();
		}
		if(productOrderModel.getExpireTime()==null){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"ExpireTime");
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "ExpireTime"))).build();
		}
		BaseResponse baseResponse=new BaseResponse();
		try {
			String orderSn=OrderUtil.GetOrderNumber("PRODUCT");
			orderSn=orderSn+(productOrderModel.getMarketChannel()==null?"":productOrderModel.getMarketChannel());
			productOrderModel.setOrderSn(orderSn);
			productOrderModel.setOrderName(orderPrefix+"-"+productOrderModel.getOrderName());
			productOrderModel.setPayType(productOrderModel.getPayType());
			WxPayResponseModel payResponseModel=new WxPayResponseModel();
			if(productOrderModel.getPayType().equals("weixinpay")){
				// 支付请求接口    待添加 构造微信统一下单接口  返回支付二维码 url 和 商户订单号给前台
				WeixinPay wxpay=new WeixinPay();
				wxpay.setTotal_fee(OrderUtil.changeY2F(productOrderModel.getOrderPrice()+""));//支付价格 单位分 
				wxpay.setOut_trade_no(orderSn);
				wxpay.setBody(productOrderModel.getOrderName());
				wxpay.setAppid(appId);
				wxpay.setMch_id(mchId);
				wxpay.setPartnerKey(partnerKey);
				wxpay.setUnifiedorder(unifiedorder);
				wxpay.setTrade_type(tradeType);
				wxpay.setSpbill_create_ip(productOrderModel.getIp());
				wxpay.setNotify_url(productbuyNoticeUrl);
				payResponseModel=wxpay.submitXmlGetPayResponse();//调用微信 统一下单接口 返回支付二维码和预支付订单号
				payResponseModel.setOutTradeNo(orderSn);
			}else if(productOrderModel.getPayType().equals("alipay")){
				AlipayBizModel alipayBizModel=new AlipayBizModel();
				alipayBizModel.setOut_trade_no(orderSn);
				alipayBizModel.setSubject(productOrderModel.getOrderName());
				alipayBizModel.setTotal_amount(productOrderModel.getOrderPrice()+"");
				AlipayTradePrecreateResponse alipayResponse=ToAlipayQrTradePay.qrPay(alipayBizModel, notifyURL,alipayPublicKey,alipayAPPID,alipayPrivateKey);
				payResponseModel.setCodeUrl(alipayResponse.getQrCode());
				payResponseModel.setOutTradeNo(orderSn);
			}else {
	            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_INVALID.toString(), "paytype"))).build();
			}
			baseResponse.setResult(payResponseModel);
			ProductPo findByCtbCode = productService.findByCtbCode(productOrderModel.getCtbCode());
			productOrderModel.setProductUuid(findByCtbCode.getUuid());
			productBuyRecordService.saveProductRecord(productOrderModel);
		} catch (Exception e) {
			logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "VIPBUY"))).build();
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	
	@GET
	@Path("/findByOrderSn/{orderSn}")
	public Response findByOrderSn(@Context HttpServletRequest httpRequest,@QueryParam("requestId")String requestId,@PathParam("orderSn") String orderSn) {
		BaseResponse baseResponse=new BaseResponse();
		logger.info("HttpServletRequest: orderSn:{}", orderSn);
		if (StringUtils.isBlank(orderSn)) {
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"orderSn");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "orderSn"))).build();
		}
		try {
			ProBuyRecordPo findByOrderSn = productBuyRecordService.findByOrderSn(orderSn);
			PayResult payResult=new PayResult();
			if (1 == findByOrderSn.getIsPay()) {
				payResult.setPay(true);
			} else {
				payResult.setPay(false);
			}
			baseResponse.setResult(payResult);
		} catch (Exception e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		}
		return Response.ok(gson.toJson(baseResponse)).build(); 
	}
	
	@GET
	@Path("/selectAllProductOrderList/{userCode}/{pageNumber}/{size}")
	public Response selectAllProductOrderList(@Context HttpServletRequest httpRequest,@PathParam("userCode") String userCode,@PathParam("pageNumber") Integer pageNumber,@PathParam("size") Integer size) {
		logger.info("/productController/selectAllProductOrderList," + "dataMap====" + userCode +"/" + pageNumber + "/" + size);
		BaseResponse baseResponse = new BaseResponse();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			//String userCode = productOrderModel.getUserCode();
			if (StringUtils.isBlank(userCode)) {
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(), ResponseCode.PARAMETER_MISS.toString(), "USERCODE"))).build();
			}
			try {
				Pageable p=new PageRequest(pageNumber, size);
				//List<ProBuyRecordPo> proOrderList = productBuyRecordService.findAllByUserCodeAndIsPay(userCode,1);
				Page<ProBuyRecordPo> pageModel = productBuyRecordService.findByUserCodeAndIsDelAndIsPay(userCode, false, 1, p);
				List<ProBuyRecordPo> proOrderList = pageModel.getContent();
				List<VipOrderModel> proOrderModelList = new ArrayList<VipOrderModel>();
				if (null != proOrderList) {
					logger.info("findByProductUuid===" + gson.toJson(proOrderList));
					for (ProBuyRecordPo proBuyRecordPo : proOrderList) {
						VipOrderModel productBuyRecordProductOrderModel = CIBNConvert.productBuyRecordProductOrderModel(proBuyRecordPo);
						proOrderModelList.add(productBuyRecordProductOrderModel);
					}
				}
				Page<VipOrderModel> newPag=new PageImpl<VipOrderModel>(proOrderModelList, p, Long.valueOf(pageModel.getTotalElements()));
				baseResponse.setMessage("success");
				baseResponse.setResult(newPag);
			} catch (Exception e) {
				baseResponse.setMessage("error");
				baseResponse.setCode("500");
				e.printStackTrace();
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(), ResponseCode.SERVICE_ERROR.toString(), "PRODUCTBUY"))).build();
				// TODO Auto-generated catch block
				
			}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	
	@GET
	@Path("/selectProductOrderList/{userCode}/{pageNumber}/{size}")
	public Response selectProductOrderList(@Context HttpServletRequest httpRequest,@PathParam("userCode") String userCode,@PathParam("pageNumber") Integer pageNumber,@PathParam("size") Integer size) {
		logger.info("/productController/selectProductOrderList," + "userCode====" + userCode);
		BaseResponse baseResponse = new BaseResponse();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日");
			if (StringUtils.isBlank(userCode)) {
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(), ResponseCode.PARAMETER_MISS.toString(), "USERCODE"))).build();
			}
			try {
				//List<ProBuyRecordPo> proOrderList = productBuyRecordService.findAllByUserCode(userCode,new Date());
				List<Map<String, String>> proOrderModelList = new ArrayList<Map<String, String>>();
				Pageable p=new PageRequest(pageNumber, size);
				//List<ProBuyRecordPo> proOrderList = productBuyRecordService.findAllByUserCodeAndIsPay(userCode,1);
				Page<ProBuyRecordPo> pageModel = productBuyRecordService.findByUserCodeAndIsDelAndIsPay(userCode, false, 1, p);
				List<ProBuyRecordPo> proOrderList = pageModel.getContent();
				if (null != proOrderList && 0 < proOrderList.size()) {
					for (ProBuyRecordPo proModel : proOrderList) {
						Map<String, String> productMap = new HashMap<String, String>();
						String productUuid = proModel.getProductUuid();
						ProductPo findByUuid = productService.findByUuid(productUuid);
						Date expireDate = proModel.getExpireDate();
						Long expireTimeLong = expireDate.getTime();
						Long newDateLong = (new Date()).getTime();
						if (expireTimeLong > newDateLong) {
							productMap.put("stageName", findByUuid.getStageName());
							productMap.put("gradeName", findByUuid.getGradeName());
							productMap.put("subjectName", findByUuid.getSubjectName());
							productMap.put("ctbName", findByUuid.getCtbName());
							productMap.put("ctbCode", findByUuid.getCtbCode());
							productMap.put("expireTime", "有效期至" +sim.format(expireDate));
							proOrderModelList.add(productMap);
						}
						
					}
					if (null != proOrderModelList) {
						logger.info("findByProductUuid===" + gson.toJson(proOrderModelList));
					}
					Page<Map<String, String>> newPag=new PageImpl<Map<String, String>>(proOrderModelList, p, Long.valueOf(pageModel.getTotalElements()));
					baseResponse.setMessage("success");
					baseResponse.setResult(newPag);
				}
			} catch (Exception e) {
				baseResponse.setMessage("error");
				baseResponse.setCode("500");
				// TODO Auto-generated catch block
				e.printStackTrace();
					
			}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
}

	