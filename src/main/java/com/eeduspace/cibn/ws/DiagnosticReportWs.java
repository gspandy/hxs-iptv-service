package com.eeduspace.cibn.ws;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.New;
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
import com.eeduspace.cibn.model.AnswerResultModel;
import com.eeduspace.cibn.model.DiagnosticReportModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.model.WxPayResponseModel;
import com.eeduspace.cibn.model.request.VideoRequest;
import com.eeduspace.cibn.model.response.PayResult;
import com.eeduspace.cibn.persist.po.DiagnosticReport;
import com.eeduspace.cibn.persist.po.VipBuyRecord;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.AnswerResultService;
import com.eeduspace.cibn.service.DiagnosticReportService;
import com.eeduspace.cibn.service.LearningAbilityService;
import com.eeduspace.cibn.service.VipBuyRecordService;
import com.eeduspace.cibn.util.CommonUtil;
import com.eeduspace.cibn.util.OrderUtil;
import com.eeduspace.cibn.weixin.WeixinPay;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
/**
 * 诊断报告服务
 * @author zhuchaowei
 * 2016年4月22日
 * Description
 */
@Component
@Path(value = "/diagnostic_report")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class DiagnosticReportWs {
	@Inject
	private LearningAbilityService learningAbilityService;
	/***
	 * 诊断购买微信支付回调通知地址
	 */
	@Value("${cibn.order.prefix}")
	private String orderPrefix;
	@Value("${cibn.weixin.diagnosticbuy.notify_url}")
	private String diagnosticBuyNoticeUrl;
	/**微信统一下单url*/
	@Value("${cibn.weixin.unifiedorder}")
	private  String unifiedorder;
	@Value("${cibn.weixin.appid}")
	private String appId;
	@Value("${cibn.weixin.mch_id}")
	private String mchId;
	@Value("${cibn.weixin.trade_type}")
	private String tradeType;
	@Value("${cibn.weixin.partnerKey}")
	private String partnerKey;
	@Value("${cibn.diagnostic.price}")
	private String diagnosticPrice;
	@Value("${cibn.alipay.diagnosticbuy.notify_url}")
	private String alipayNotifyURL;
	
	@Value("${cibn.alipay.alipayAPPID}")
	private String alipayAPPID;
	@Value("${cibn.alipay.alipayPrivateKey}")
	private String alipayPrivateKey;
	@Value("${cibn.alipay.alipayPublicKey}")
	private String alipayPublicKey;
	@Inject
	private DiagnosticReportService diagnosticReportService;
	@Inject
	private VipBuyRecordService vipBuyRecordService;
	@Inject
	private AnswerResultService answerResultService;
	private Gson gson = new Gson();
	private final Logger logger = LoggerFactory.getLogger(DiagnosticReportWs.class);
	/**
	 * 诊断记录分页列表
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月20日 下午6:06:20
	 * @param userInfo
	 * @param pageable
	 * @return
	 */
	@GET
	@Path("/get_diagnostic_report_pageList/{userCode}/{pageNumber}/{size}")
	public Response diagnosticReportpageList(@Context HttpServletRequest request,@QueryParam("requestId") String requestId,@PathParam("userCode") String userCode,@PathParam("pageNumber") Integer pageNumber,@PathParam("size") Integer size){
		if(StringUtils.isBlank(userCode)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"USERCODE");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "USERCODE"))).build();
		}
		BaseResponse baseResponse=new BaseResponse();
		Pageable p=new PageRequest(pageNumber, size);
		try {
				//我的诊断记录返回所有的    
				Page<DiagnosticReport> pageList=diagnosticReportService.findAll(userCode, false, p);
				List<DiagnosticReport> diagnosticReports=pageList.getContent();
				List<DiagnosticReportModel> modelLists=new ArrayList<>();
				for (DiagnosticReport diagnosticRepor : diagnosticReports) {
					DiagnosticReportModel model=CIBNConvert.fromDiagnosticReport(diagnosticRepor,false);
					modelLists.add(model);
				}
				Page<DiagnosticReportModel> pageModel=new PageImpl<>(modelLists, p, pageList.getTotalElements());
				baseResponse.setResult(pageModel);
		} catch (Exception e) {
			logger.error("requestId：{},createManager Exception：", requestId,e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(),"DIAGNOSTICREPORT"))).build();
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	/**
	 * 获取诊断报告详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 上午11:05:51
	 * @param userInfo
	 * @return
	 */
	@GET
	@Path("/diagnostic_report_detail/{diagnosticReportUUID}")
	public Response diagnosticReportDetail(@Context HttpServletRequest request,@QueryParam("requestId") String requestId,@PathParam("diagnosticReportUUID") String diagnosticReportUUID){
		BaseResponse baseResponse=new BaseResponse();
		if(StringUtils.isBlank(diagnosticReportUUID)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"DiagnosticReportUUID");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "DiagnosticReportUUID"))).build();
		}
		try {
			//学习能力值 统计  一期去掉
//			String learnAbility=diagnosticReport.getProductionChart();
//			Map<Integer, Integer>learnAbilityMap=gson.fromJson(learnAbility, Map.class);
//			Map<Integer, Object> avgMap=new HashMap<>();
//			Map<Integer, Object>maxMap=new HashMap<>();
//			for (Integer key : learnAbilityMap.keySet()) {
//				avgMap.put(key, learningAbilityService.getAve(diagnosticReport.getPaperCode(), LearnAbilityTypeEnum.getEnum(key.intValue())));
//				maxMap.put(key, learningAbilityService.getMax(diagnosticReport.getPaperCode(), LearnAbilityTypeEnum.getEnum(key.intValue())));
//			}
			DiagnosticReportModel diagnosticReportModel=diagnosticReportService.findDiagnosticReport(diagnosticReportUUID);
			baseResponse.setResult(diagnosticReportModel);
		}catch(JsonSyntaxException e){
			logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.RESOURSCEDATA_ERROR.toString(), "DIAGNOSTICREPORT"))).build();
		}
		catch (Exception e) {
			logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "DIAGNOSTICREPORT"))).build();
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	/**
	 * 删除诊断记录
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 上午11:58:08
	 * @param diagnosticReportInfo
	 * @return
	 */
	@POST
	@Path("/diagnostic_report_delete/{diagnosticReportUUID}")
	public Response deleteDiagnosticReport(@Context HttpServletRequest request,@QueryParam("requestId") String requestId,@PathParam("diagnosticReportUUID") String diagnosticReportUUID){
		if(StringUtils.isBlank(diagnosticReportUUID)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"DiagnosticReportUUID");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "DiagnosticReportUUID"))).build();
		}
		try {
			diagnosticReportService.delete(diagnosticReportUUID);
		} catch (Exception e) {
			logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "DIAGNOSTICREPORT"))).build();
		}
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setResult("deleteSuccess");
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	/**
	 * 诊断购买
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月22日 下午2:02:51
	 * @return 
	 */
	@POST
	@Path("/diagonstic_buy")
	public Response buyDiagonsticReport(@QueryParam("requestId") String requestId, String requestBody,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
        logger.info("HttpServletRequest: requestBody:{}", requestBody);
		VipOrderModel vipOrderModel = gson.fromJson(requestBody,VipOrderModel.class);
		UserModel userModel = vipOrderModel.getUserModel();
		if(StringUtils.isBlank(userModel.getUserCode())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"USERCODE");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "USERCODE"))).build();
		}
		if(StringUtils.isBlank(vipOrderModel.getDiagnosticUUID())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"DiagnosticUUID");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "DiagnosticUUID"))).build();
		}
		if(StringUtils.isBlank(vipOrderModel.getPayType())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"paytype");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "paytype"))).build();
		}
		BaseResponse baseResponse = new BaseResponse();
		try {
			String orderSn=OrderUtil.GetOrderNumber("ZD");
			orderSn=orderSn+(vipOrderModel.getMarketChannel()==null?"":vipOrderModel.getMarketChannel());
			vipOrderModel.setOrderSn(orderSn);
			vipOrderModel.setPayType(vipOrderModel.getPayType());
			WxPayResponseModel payResponseModel=new WxPayResponseModel();
			if(vipOrderModel.getPayType().equals("weixinpay")){
				// 支付请求接口    待添加 构造微信统一下单接口  返回支付二维码 url 和 商户订单号给前台
				WeixinPay wxpay=new WeixinPay();
				wxpay.setTotal_fee(OrderUtil.changeY2F(vipOrderModel.getOrderPrice()+""));//支付价格 单位分 
				wxpay.setBody(orderPrefix+"-"+vipOrderModel.getOrderName());
				wxpay.setSpbill_create_ip(userModel.getIp());
				wxpay.setUnifiedorder(unifiedorder);
				wxpay.setOut_trade_no(orderSn);
				wxpay.setAppid(appId);
				wxpay.setMch_id(mchId);
				wxpay.setPartnerKey(partnerKey);
				wxpay.setTrade_type(tradeType);
				wxpay.setNotify_url(diagnosticBuyNoticeUrl);
				payResponseModel=wxpay.submitXmlGetPayResponse();//调用微信 统一下单接口 返回支付二维码和预支付订单号
				payResponseModel.setOutTradeNo(orderSn);
			}else if(vipOrderModel.getPayType().equals("alipay")){
				AlipayBizModel alipayBizModel=new AlipayBizModel();
				alipayBizModel.setOut_trade_no(orderSn);
				alipayBizModel.setSubject(orderPrefix+"-"+vipOrderModel.getOrderName());
				alipayBizModel.setTotal_amount(vipOrderModel.getOrderPrice()+"");
				AlipayTradePrecreateResponse alipayResponse=ToAlipayQrTradePay.qrPay(alipayBizModel, alipayNotifyURL,alipayPublicKey,alipayAPPID,alipayPrivateKey);
				payResponseModel.setCodeUrl(alipayResponse.getQrCode());
				payResponseModel.setOutTradeNo(orderSn);
			}else{
	            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_INVALID.toString(), "paytype"))).build();
			}
			vipOrderModel.setOrderName(orderPrefix+"-"+vipOrderModel.getOrderName());
			vipBuyRecordService.saveDiagnosticBuyRecord(vipOrderModel, userModel);
			baseResponse.setResult(payResponseModel);
		} catch (Exception e) {
			logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "DIAGNOSTICBUY"))).build();
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	/**
	 * 提交诊断
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 下午1:06:19
	 * @return
	 */
	@POST
	@Path("/commit_diagonstic")
	public Response commitDiagonstic(@Context HttpServletRequest request,@QueryParam("requestId") String requestId,String requestBody) {
        logger.info("HttpServletRequest: requestBody:{}", requestBody);
		DiagnosticReportModel deDiagnosticReportModel=gson.fromJson(requestBody, DiagnosticReportModel.class);
		if(StringUtils.isBlank(deDiagnosticReportModel.getUserCode())){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"UserCode");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "UserCode"))).build();
		} 
		try {
			deDiagnosticReportModel=diagnosticReportService.saveDiagnosticReport(deDiagnosticReportModel.getUserCode(), deDiagnosticReportModel);
		}catch (JsonSyntaxException e) {
			logger.error("resource video dataerror：", e);
			 return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.RESOURSCEDATA_ERROR.toString()))).build();
		} catch (Exception e) {
			logger.error("requestId：{},createManager Exception：",requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "DIAGNOSTICBUY"))).build();
		}
		BaseResponse baseResponse = new BaseResponse();
		DiagnosticReportModel returnModel=new DiagnosticReportModel();
		returnModel.setDiagnosticPrice(new Double(diagnosticPrice));
		returnModel.setDiagnosticReportUUID(deDiagnosticReportModel.getDiagnosticReportUUID());
		returnModel.setRankPercentage(deDiagnosticReportModel.getRankPercentage());
		returnModel.setUserRanking(deDiagnosticReportModel.getUserRanking());
		returnModel.setScore(deDiagnosticReportModel.getScore());
		returnModel.setUseTime(deDiagnosticReportModel.getUseTime());
		baseResponse.setResult(returnModel);
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	
	/**
	 * 通知客户端诊断支付结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月25日 下午2:45:35
	 * @param orderSn
	 * @return
	 */
	@GET
	@Path("/find_diagnostic_order/{orderSn}")
	public Response findOrder(@Context HttpServletRequest request,@QueryParam("requestId")String requestId,@PathParam("orderSn") String orderSn){
		if(StringUtils.isBlank(orderSn)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"orderSn");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "orderSn"))).build();
		}
		VipBuyRecord vipBuyRecord=vipBuyRecordService.findByOrderSn(orderSn);
		BaseResponse baseResponse=new BaseResponse();
		PayResult payResult=new PayResult();
		if(vipBuyRecord.isPay()){
			payResult.setPay(true);;
			payResult.setResult(vipBuyRecord.getDiagnositcUUID());
//			if(diagnosticReport!=null){
//				diagnosticReport.setBuy(true);
//				payResult.setResult(diagnosticReport.getUuid());
//				diagnosticReportService.save(diagnosticReport);
//			}else{
//				 return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.RESOURCE_NOTFOUND.toString(), "DiagnositcUUID invalid"))).build();
//			}
		}else{
			payResult.setPay(false);
		}
		baseResponse.setResult(payResult);
		return Response.ok(gson.toJson(baseResponse)).build();
	}
}
