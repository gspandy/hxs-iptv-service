package com.eeduspace.cibn.weixin;

import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.eeduspace.cibn.util.XMLUtil;
import com.eeduspace.uuims.comm.util.base.encrypt.ConfigureEncryptAndDecrypt;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;

/**
 * 支付成功后订单查询
 *
 */
public class WXOrderQuery {
	private final Logger logger = LoggerFactory.getLogger(WXOrderQuery.class);
	private String partnerKey;
	private String appid;
	private String mch_id;
	private String transaction_id;
	private String out_trade_no;
	private String nonce_str;
	private String sign;
	/**微信订单查询url*/
	private  String orderquery;
	//请求订单查询接口
	@SuppressWarnings("unchecked")
	public Map<String, String> reqOrderquery(){
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(orderquery);
		String xml = getPackage();
		StringEntity entity;
		Map<String, String> map = null;
		try {
			entity = new StringEntity(xml, "utf-8");
			httpPost.setEntity(entity);

			HttpResponse httpResponse;
			// post请求
			httpResponse = closeableHttpClient.execute(httpPost);

			// getEntity()
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				String result = EntityUtils.toString(httpEntity, "UTF-8");
				// 过滤
				result = result.replaceAll("<![CDATA[|]]>", "");
				map = XMLUtil.doXMLParse(result);
				logger.info("支付金额：{}",map.get("total_fee"));
				logger.info("微信订单查询返回结果{}",result);
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public String getPackage() {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("appid", this.appid);
		treeMap.put("mch_id", this.mch_id);
		treeMap.put("transaction_id", this.transaction_id);
		treeMap.put("nonce_str", this.nonce_str);
		treeMap.put("out_trade_no", this.out_trade_no);
		
		StringBuilder sb = new StringBuilder();
		for (String key : treeMap.keySet()) {
			sb.append(key).append("=").append(treeMap.get(key)).append("&");
		}
		sb.append("key=" + partnerKey);
		sign=Digest.digest(sb.toString(), ConfigureEncryptAndDecrypt.MD5_ALGORITHM, "utf-8").toUpperCase();
		treeMap.put("sign", sign);

		StringBuilder xml = new StringBuilder();
		xml.append("<xml>\n");

		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
				xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
			} else {
				xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
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
	public void setMch_id(String mchId) {
		mch_id = mchId;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transactionId) {
		transaction_id = transactionId;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String outTradeNo) {
		out_trade_no = outTradeNo;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonceStr) {
		nonce_str = nonceStr;
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

	public String getOrderquery() {
		return orderquery;
	}

	public void setOrderquery(String orderquery) {
		this.orderquery = orderquery;
	}
	
}
