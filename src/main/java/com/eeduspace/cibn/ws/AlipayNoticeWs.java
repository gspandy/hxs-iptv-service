package com.eeduspace.cibn.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.alipay.AlipayNotify;
import com.eeduspace.cibn.persist.po.DiagnosticReport;
import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.persist.po.VipBuyRecord;
import com.eeduspace.cibn.service.DiagnosticReportService;
import com.eeduspace.cibn.service.ProductBuyRecordService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.service.VipBuyRecordService;
import com.eeduspace.uuims.comm.util.base.DateUtils;
import com.google.gson.Gson;

@Component
@Path(value = "/aplipay_notice")
public class AlipayNoticeWs {
	private final Logger logger = LoggerFactory.getLogger(AlipayNoticeWs.class);
	private Gson gson=new Gson();
	@Inject
	private UserService userService;
	@Inject
	private VipBuyRecordService vipBuyRecordService;
	@Inject
	private DiagnosticReportService diagnosticReportService;
	@Inject
	private ProductBuyRecordService productBuyRecordService;
	@Value("${cibn.alipay.partner}")
	private String partner;
	@POST
	@Path("/vip_alipay")
	public Response vipPayNotice(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException, ParserConfigurationException{
		// 1、根据流水号找到 购买记录    
		//2、更新购买记录的支付状态 
		logger.info("HttpServletRequest: trade_no{}",request.getParameter("trade_no"));
		String noticeStr ="success";
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
		Map<String, String> map=getParams(result);
		logger.info("支付宝通知参数---》"+gson.toJson(map));
		//校验是否是支付宝发来请求
		boolean verify= AlipayNotify.verify(map,partner);
		if(verify){
			try {
				String sn = map.get("out_trade_no");
				VipBuyRecord vipBuyRecord=vipBuyRecordService.findByOrderSn(sn);
				if(vipBuyRecord==null){
					noticeStr="failed";
					return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
							.forName("UTF-8")))).build();
				}
				if(!vipBuyRecord.isPay()){//订单未支付   防止微信多次通知 进行更新
					vipBuyRecord.setPay(true);
					vipBuyRecord.setTransactionId(map.get("trade_no"));
					vipBuyRecordService.save(vipBuyRecord);
					//更新用户VIP信息
					updateUserInfo(vipBuyRecord);
				}
			} catch (Exception e) {
				logger.error("notice vip save faile",e);
			}
			logger.info(verify+"校验成功");
			noticeStr="success";
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}else{
			logger.info(verify+"校验失败");
			noticeStr="failed";
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
	}
	
    /**
     * 获取通知请求参数
     * Author： zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * 2016年5月12日 下午1:17:05
     * @param result
     * @return
     * @throws UnsupportedEncodingException 
     */
	public Map<String, String> getParams(String result) throws UnsupportedEncodingException{
		String[] args=result.split("&");
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			map.put(args[i].split("=")[0],  URLDecoder.decode(args[i].split("=")[1], "UTF-8"));
		}
		return map;
	}
	@POST
	@Path("/diagnostic_alipay")
	public Response diagnosticPayNotice(@Context HttpServletRequest request,
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
		Map<String, String> map=getParams(result);
		logger.info("支付宝通知参数---》"+gson.toJson(map));
		//校验是否是支付宝发来请求
		String noticeStr ="success";
		boolean verify= AlipayNotify.verify(map,partner);
		if(verify){
			String sn = map.get("out_trade_no");
			try {
				VipBuyRecord vipBuyRecord=vipBuyRecordService.findByOrderSn(sn);
				if(vipBuyRecord==null){
					noticeStr="failed";
					return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset.forName("UTF-8")))).build();
				}
				DiagnosticReport diagnosticReport=diagnosticReportService.findByUUID(vipBuyRecord.getDiagnositcUUID());
				if(diagnosticReport!=null){
					diagnosticReport.setBuy(true);
					diagnosticReportService.save(diagnosticReport);
				}else{
					noticeStr="failed";
					return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset.forName("UTF-8")))).build();
				}
				vipBuyRecord.setPay(true);
				vipBuyRecord.setTransactionId(map.get("trade_no"));
				vipBuyRecordService.save(vipBuyRecord);
			} catch (Exception e) {
				logger.error("notice vip save faile",e);
			}
			noticeStr="success";
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}else{
			logger.info(verify+"校验失败");
			noticeStr="failed";
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}
		
	}
	/**
	 * <pre>productPayNotice(商品的支付宝回调)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月21日 下午1:50:13    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月21日 下午1:50:13    
		 * 修改备注： 
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 * @throws ParserConfigurationException</pre>
	 */
	@POST
	@Path("/product_alipay")
	public Response productPayNotice(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException, ParserConfigurationException{
		// 1、根据流水号找到 购买记录    
		//2、更新购买记录的支付状态 
		logger.info("HttpServletRequest: trade_no{}",request.getParameter("trade_no"));
		String noticeStr ="success";
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
		Map<String, String> map=getParams(result);
		logger.info("支付宝通知参数---》"+gson.toJson(map));
		//校验是否是支付宝发来请求
		boolean verify= AlipayNotify.verify(map,partner);
		if(verify){
			synchronized (this) {
				try {
					String sn = map.get("out_trade_no");
					ProBuyRecordPo proBuyRecord=productBuyRecordService.findByOrderSn(sn);
					if(proBuyRecord==null){
						noticeStr="failed";
						return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
								.forName("UTF-8")))).build();
					}
					if(0 == proBuyRecord.getIsPay()){//订单未支付   防止微信多次通知 进行更新
						proBuyRecord.setIsPay(1);
						proBuyRecord.setTransactionId(map.get("trade_no"));
						productBuyRecordService.save(proBuyRecord);
						//更新用户VIP信息
						//updateUserInfo(vipBuyRecord);
					}
				} catch (Exception e) {
					logger.error("notice product save faile",e);
				}
			}
			logger.info(verify+"校验成功");
			noticeStr="success";
			return Response.ok(new ByteArrayInputStream(noticeStr.getBytes(Charset
					.forName("UTF-8")))).build();
		}else{
			logger.info(verify+"校验失败");
			noticeStr="failed";
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
}
