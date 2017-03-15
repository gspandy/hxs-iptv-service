package com.eeduspace.cibn.weixin;

import com.eeduspace.cibn.model.WxPayResponseModel;
import com.eeduspace.cibn.util.OrderUtil;
import com.eeduspace.uuims.comm.util.base.encrypt.ConfigureEncryptAndDecrypt;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

public class WeixinPay {
	/**微信统一下单url*/
	private  String unifiedorder;
	/** 公众账号ID */
	private String appid;
	/** 商户号 */
	private String mch_id;
	/** 交易类型 JSAPI，NATIVE，APP */
	private String trade_type;
	/** 通知地址 */
	private String notify_url;
	private String partnerKey;
	/** 随机字符串 */
	private String nonce_str = OrderUtil.CreateNoncestr();
	/** 商品描述 */
	private String body;
	/** 商户订单号 */
	private String out_trade_no;
	/** 总金额 */
	private String total_fee;
	/** 终端IP */
	private String spbill_create_ip;
	/** 签名 */
	private String sign;
	// private String openid;
	// 预支付订单号
	private String prepay_id;
	// 商户数据包
	private String attach;

	private final Logger logger = LoggerFactory.getLogger(WeixinPay.class);

	/**
	 * 生成预支付订单
	 * 
	 * @return
	 */
	public WxPayResponseModel submitXmlGetPayResponse() {
		WxPayResponseModel wxPayResponseModel = new WxPayResponseModel();
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(unifiedorder);
		String xml = getPackage();
		logger.info("request xml:{}",xml);
		StringEntity entity;
		try {
			entity = new StringEntity(xml, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// post请求
			httpResponse = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				String result = EntityUtils.toString(httpEntity, "UTF-8");
				logger.info("请求支付二维码微信返回结果---》{}",result);
				// 过滤
				result = result.replaceAll("<![CDATA[|]]>", "");
				String prepay_id = Jsoup.parse(result).select("prepay_id")
						.html();
				String qrUrl = Jsoup.parse(result).select("code_url").html();
				wxPayResponseModel.setPrepayId(prepay_id);
				wxPayResponseModel.setCodeUrl(qrUrl);
				this.prepay_id = prepay_id;
				if (prepay_id != null)
					return wxPayResponseModel;
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wxPayResponseModel;
	}

	public String getPackage() {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("appid", this.appid);
		treeMap.put("mch_id", this.mch_id);
		treeMap.put("nonce_str", this.nonce_str);
		treeMap.put("body", this.body);
		treeMap.put("out_trade_no", this.out_trade_no);
		treeMap.put("total_fee", this.total_fee);
		treeMap.put("spbill_create_ip", this.spbill_create_ip);
		treeMap.put("trade_type", this.trade_type);
		treeMap.put("notify_url", this.notify_url);
		// treeMap.put("openid", this.openid);
		// treeMap.put("attach", this.attach);
		StringBuilder sb = new StringBuilder();
		for (String key : treeMap.keySet()) {
			sb.append(key).append("=").append(treeMap.get(key)).append("&");
		}
		sb.append("key=" + partnerKey);
		logger.info("sign---->{}", sign);
		sign = Digest.digest(sb.toString(),
				ConfigureEncryptAndDecrypt.MD5_ALGORITHM, "utf-8")
				.toUpperCase();
		treeMap.put("sign", sign);
		StringBuilder xml = new StringBuilder();
		xml.append("<xml>\n");
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
				xml.append("<" + entry.getKey() + "><![CDATA[")
						.append(entry.getValue())
						.append("]]></" + entry.getKey() + ">\n");
			} else {
				xml.append("<" + entry.getKey() + ">").append(entry.getValue())
						.append("</" + entry.getKey() + ">\n");
			}
		}
		xml.append("</xml>");
		return xml.toString();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	// public String getOpenid() {
	// return openid;
	// }
	// public void setOpenid(String openid) {
	// this.openid = openid;
	// }
	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getUnifiedorder() {
		return unifiedorder;
	}

	public void setUnifiedorder(String unifiedorder) {
		this.unifiedorder = unifiedorder;
	}
}
