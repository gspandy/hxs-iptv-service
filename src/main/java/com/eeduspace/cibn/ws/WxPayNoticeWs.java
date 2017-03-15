package com.eeduspace.cibn.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.persist.po.DiagnosticReport;
import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.persist.po.VipBuyRecord;
import com.eeduspace.cibn.service.DiagnosticReportService;
import com.eeduspace.cibn.service.ProductBuyRecordService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.service.VipBuyRecordService;
import com.eeduspace.cibn.util.XMLUtil;
import com.eeduspace.cibn.weixin.WXOrderQuery;
import com.eeduspace.uuims.comm.util.base.DateUtils;
import com.google.gson.Gson;
/**
 * 微信支付回调通知WS
 * @author zhuchaowei
 * 2016年4月22日
 * Description
 */
@Component
@Path(value = "/wx_pay_notice")
public class WxPayNoticeWs {
	@Value("${cibn.weixin.partnerKey}")
	private String partnerKey;
	@Inject
	private VipBuyRecordService vipBuyRecordService;
	@Value("${cibn.weixin.orderquery}")
	private  String orderquery;
	@Inject
	private UserService userService;
	@Inject
	private DiagnosticReportService diagnosticReportService;
	@Inject
	private ProductBuyRecordService productBuyRecordService;
	private Gson gson = new Gson();
	private final Logger logger = LoggerFactory.getLogger(WxPayNoticeWs.class);
	/**
	 *VIP购买支付通知地址
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 下午3:49:30
	 * @param orderInfo
	 * @return
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	@POST
	@Path("/vip_weixin_pay")
	public Response vipPayNotice(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException, ParserConfigurationException{
		// 1、根据流水号找到 购买记录    
		//2、更新购买记录的支付状态 
		response.setContentType("text/xml");
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");
		logger.info("微信回调结果result----》{}",result);
		Map<String, String> map = new HashMap<>();
		try {
			map = XMLUtil.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		if(map==null){
			String noticeStr = setXML("FAILED", "FAILED");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		String sn = map.get("out_trade_no");
		logger.info("微信回调结果商户生成的订单 sn----》{}",sn);
		if(sn==null||"".equals(sn)){
			String noticeStr = setXML("FAILED", "FAILED");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		boolean isSucc = reqOrderquery(map);
		//如果查找到已支付订单 则给微信返回结果
		if(isSucc){
			//加锁防止多线程订单状态未更新
			synchronized (this) {
				try {
					VipBuyRecord vipBuyRecord=vipBuyRecordService.findByOrderSn(sn);
					if(vipBuyRecord==null){
						String noticeStr = setXML("FAILED", "FAILED");
						return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
								.forName("UTF-8")))).build();
					}
					
					if(!vipBuyRecord.isPay()){//订单未支付   防止微信多次通知 进行更新
						vipBuyRecord.setPay(true);
						vipBuyRecord.setTransactionId(map.get("transaction_id"));
						vipBuyRecordService.save(vipBuyRecord);
						//更新用户VIP信息
						updateUserInfo(vipBuyRecord);
					}
				} catch (Exception e) {
					logger.error("notice vip save faile",e);
				}
			}
			String noticeStr = setXML("SUCCESS", "OK");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}else{
			String noticeStr = setXML("FAILED", "FAILED");
			
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		
	}
	
	
	
	/**
	 * 诊断购买微信支付回调通知
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 下午3:49:30
	 * @param orderInfo
	 * @return
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	@POST
	@Path("/diagnostic_weixin_pay")
	public Response diagnosticReportNotice(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException, ParserConfigurationException{
		// 1、根据流水号找到 购买记录    
		//2、更新购买记录的支付状态 
		response.setContentType("text/xml");
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");
		logger.info("微信回调结果result----》{}",result);
		Map<String, String> map = new HashMap<>();
		try {
			map = XMLUtil.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		if(map==null){
			String noticeStr = setXML("FAILED", "FAILED");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		String sn = map.get("out_trade_no");
		logger.info("微信回调结果商户生成的订单 sn----》{}",sn);
		if(sn==null||"".equals(sn)){
			String noticeStr = setXML("FAILED", "FAILED");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		boolean isSucc = reqOrderquery(map);
		//如果查找到已支付订单 则给微信返回结果
		if(isSucc){
			VipBuyRecord vipBuyRecord=vipBuyRecordService.findByOrderSn(sn);
			if(vipBuyRecord==null){
				String noticeStr = setXML("FAILED", "FAILED");
				return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
						.forName("UTF-8")))).build();
			}
			
			DiagnosticReport diagnosticReport=diagnosticReportService.findByUUID(vipBuyRecord.getDiagnositcUUID());
			if(diagnosticReport!=null){
				diagnosticReport.setBuy(true);
				diagnosticReportService.save(diagnosticReport);
			}else{
				String noticeStr = setXML("FAILED", "FAILED");
				return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
						.forName("UTF-8")))).build();
			}
			
			vipBuyRecord.setPay(true);
			vipBuyRecord.setTransactionId(map.get("transaction_id"));
			vipBuyRecordService.save(vipBuyRecord);
			String noticeStr = setXML("SUCCESS", "OK");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}else{
			String noticeStr = setXML("FAILED", "FAILED");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
	}
	/**
	 * <pre>productPayNotice(商品的微信支付回调)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月21日 下午1:49:04    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月21日 下午1:49:04    
		 * 修改备注： 
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 * @throws ParserConfigurationException</pre>
	 */
	@POST
	@Path("/product_weixin_pay")
	public Response productPayNotice(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException, ParserConfigurationException{
		// 1、根据流水号找到 购买记录    
		//2、更新购买记录的支付状态 
		response.setContentType("text/xml");
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");
		logger.info("微信回调结果result----》{}",result);
		Map<String, String> map = new HashMap<>();
		try {
			map = XMLUtil.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		if(map==null){
			String noticeStr = setXML("FAILED", "FAILED");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		String sn = map.get("out_trade_no");
		logger.info("微信回调结果商户生成的订单 sn----》{}",sn);
		if(sn==null||"".equals(sn)){
			String noticeStr = setXML("FAILED", "FAILED");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		boolean isSucc = reqOrderquery(map);
		//如果查找到已支付订单 则给微信返回结果
		if(isSucc){
			//加锁防止多线程订单状态未更新
			synchronized (this) {
				try {
					ProBuyRecordPo productBuyRecord=productBuyRecordService.findByOrderSn(sn);
					if(productBuyRecord==null){
						String noticeStr = setXML("FAILED", "FAILED");
						return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
								.forName("UTF-8")))).build();
					}
					
					if(0 == productBuyRecord.getIsPay()){//订单未支付   防止微信多次通知 进行更新
						productBuyRecord.setIsPay(1);
						productBuyRecord.setTransactionId(map.get("transaction_id"));
						productBuyRecordService.save(productBuyRecord);
						//更新用户VIP信息
						//updateUserInfo(vipBuyRecord);
					}
				} catch (Exception e) {
					logger.error("notice product save faile",e);
				}
			}
			String noticeStr = setXML("SUCCESS", "OK");
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}else{
			String noticeStr = setXML("FAILED", "FAILED");
			
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		
	}
	/**
	 * 更新用户的会员信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月29日 下午12:57:44
	 * @param vipBuyRecord
	 */
	public void updateUserInfo(VipBuyRecord vipBuyRecord){
		UserPo userPo=userService.findByUserCode(vipBuyRecord.getUserCode());
		if(userPo==null){
			return;
		}
		//如果是VIP 判断当前日期与VIP到期日期  如果VIP信息过过期 更新最新的到期日期，如果未到期 在到期日期的基础上增加 新购买的日期
		if(userPo.isVIP()){
			//VIP到期日期大于当前日期 未到期  累加续费时间
			if(userPo.getVIPExpireTime().getTime()>new Date().getTime()){
				userPo.setVIPExpireTime(DateUtils.addDay(userPo.getVIPExpireTime(), vipBuyRecord.getVipDays()));
			}else{
				//表明VIP已经到期
				userPo.setVIPExpireTime(DateUtils.addDay(new Date(), vipBuyRecord.getVipDays()));
			}
			userService.save(userPo);
		}else{
			// 第一次成为VIP
			userPo.setVIP(true);
			userPo.setVIPExpireTime(DateUtils.addDay(new Date(), vipBuyRecord.getVipDays()));
			userService.save(userPo);
		}
	}
	/**
	 * 请求订单查询接口
	 * 
	 * @param map
	 * @param accessToken
	 * @return
	 */
	public  boolean reqOrderquery(Map<String, String> map) {
		WXOrderQuery orderQuery = new WXOrderQuery();
		orderQuery.setAppid(map.get("appid"));
		orderQuery.setMch_id(map.get("mch_id"));
		orderQuery.setTransaction_id(map.get("transaction_id"));
		orderQuery.setOut_trade_no(map.get("out_trade_no"));
		orderQuery.setNonce_str(map.get("nonce_str"));
		orderQuery.setOrderquery(orderquery);
		// 此处需要密钥PartnerKey，此处直接写死，自己的业务需要从持久化中获取此密钥，否则会报签名错误
		orderQuery.setPartnerKey(partnerKey);

		Map<String, String> orderMap = orderQuery.reqOrderquery();
		// 此处添加支付成功后，支付金额和实际订单金额是否等价，防止钓鱼
		if (orderMap.get("return_code") != null
				&& orderMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
			if (orderMap.get("trade_state") != null
					&& orderMap.get("trade_state").equalsIgnoreCase("SUCCESS")) {
				String total_fee = orderMap.get("total_fee");
				String order_total_fee = map.get("total_fee");
				if (Integer.parseInt(order_total_fee) >= Integer
						.parseInt(total_fee)) {
					return true;
				}
			}
		}
		return false;
	}
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
}
