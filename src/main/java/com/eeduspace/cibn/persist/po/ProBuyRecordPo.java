
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProBuyRecord.java 
	 * 包名:com.eeduspace.cibn.persist.po 
	 * 创建日期:2016年12月19日上午11:29:19 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.persist.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eeduspace.cibn.interceptor.intercepterTest;
import com.eeduspace.cibn.persist.enumeration.BuyTypeEnum;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProBuyRecord    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 上午11:29:19    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 上午11:29:19    
 * 修改备注：       
 * @version </pre>    
 */
@Entity
@Table(name = "cibn_product_buy_record")
public class ProBuyRecordPo {

	/*
	 * CREATE TABLE `cibn_product_buy_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `discount_difference` double DEFAULT NULL,
  `is_del` tinyint(1) DEFAULT NULL,
  `is_discount` tinyint(1) DEFAULT NULL,
  `order_sn` varchar(255) DEFAULT NULL,
  `user_code` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `product_days` int(11) DEFAULT NULL,
  `product_price` double DEFAULT NULL,
  `product_type` varchar(255) DEFAULT NULL,
  `buy_type` int(11) DEFAULT NULL,
  `is_pay` int(11) DEFAULT NULL,
  `product_uuid` varchar(255) DEFAULT NULL,
  `order_name` varchar(255) DEFAULT NULL,
  `transaction_id` varchar(32) DEFAULT NULL COMMENT '微信生产订单流水号',
  `pay_type` varchar(255) DEFAULT NULL,
  `device_type` varchar(255) DEFAULT NULL,
  `market_channel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_md3tcvamb2f1ntre268mval5x` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1083 DEFAULT CHARSET=utf8
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "create_time")
	private Date createDate = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "expire_time")
	private Date expireDate;
	
	@Column(name = "discount_difference")
	private Double discountDifference;
	
	@Column(name = "is_del")
	private boolean isDel = false;
	
	@Column(name = "is_discount")
	private boolean isDiscount;
	
	@Column(name = "order_sn")
	private String orderSn;
	
	@Column(name = "user_code")
	private String userCode;
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	
	@Column(name = "product_days")
	private int productDays;
	
	@Column(name = "product_price")
	private Double productPrice;
	
	@Column(name = "product_type")
	private String productType;
	
	@Column(name = "buy_type")
	private BuyTypeEnum buyType;
	
	@Column(name = "is_pay")
	private int isPay;
	
	@Column(name = "product_uuid")
	private String productUuid;
	
	@Column(name = "order_name")
	private String orderName;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "pay_type")
	private String payType;
	
	@Column(name = "device_type")
	private String deviceType;
	
	@Column(name = "market_channel")
	private String marketChannel;

	public Long getId() {
	
		return id;
	}

	public void setId(Long id) {
	
		this.id = id;
	}

	public Date getCreateDate() {
	
		return createDate;
	}

	public void setCreateDate(Date createDate) {
	
		this.createDate = createDate;
	}

	public Double getDiscountDifference() {
	
		return discountDifference;
	}

	public void setDiscountDifference(Double discountDifference) {
	
		this.discountDifference = discountDifference;
	}

	public boolean isDel() {
	
		return isDel;
	}

	public void setDel(boolean isDel) {
	
		this.isDel = isDel;
	}

	public boolean isDiscount() {
	
		return isDiscount;
	}

	public void setDiscount(boolean isDiscount) {
	
		this.isDiscount = isDiscount;
	}

	public String getOrderSn() {
	
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
	
		this.orderSn = orderSn;
	}

	public String getUserCode() {
	
		return userCode;
	}

	public void setUserCode(String userCode) {
	
		this.userCode = userCode;
	}

	public String getUuid() {
	
		return uuid;
	}

	public void setUuid(String uuid) {
	
		this.uuid = uuid;
	}

	public int getProductDays() {
	
		return productDays;
	}

	public void setProductDays(int productDays) {
	
		this.productDays = productDays;
	}

	public Double getProductPrice() {
	
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
	
		this.productPrice = productPrice;
	}

	public String getProductType() {
	
		return productType;
	}

	public void setProductType(String productType) {
	
		this.productType = productType;
	}

	

	public BuyTypeEnum getBuyType() {
	
		return buyType;
	}

	public void setBuyType(BuyTypeEnum buyType) {
	
		this.buyType = buyType;
	}

	public int getIsPay() {
	
		return isPay;
	}

	public void setIsPay(int isPay) {
	
		this.isPay = isPay;
	}

	public String getProductUuid() {
	
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
	
		this.productUuid = productUuid;
	}

	public String getOrderName() {
	
		return orderName;
	}

	public void setOrderName(String orderName) {
	
		this.orderName = orderName;
	}

	public String getTransactionId() {
	
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
	
		this.transactionId = transactionId;
	}

	public String getPayType() {
	
		return payType;
	}

	public void setPayType(String payType) {
	
		this.payType = payType;
	}

	public String getDeviceType() {
	
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
	
		this.deviceType = deviceType;
	}

	public String getMarketChannel() {
	
		return marketChannel;
	}

	public void setMarketChannel(String marketChannel) {
	
		this.marketChannel = marketChannel;
	}

	
	public Date getExpireDate() {
	
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
	
		this.expireDate = expireDate;
	}

	@Override
	public String toString() {
		return "ProBuyRecordPo [id=" + id + ", createDate=" + createDate
				+ ", expireDate=" + expireDate + ", discountDifference="
				+ discountDifference + ", isDel=" + isDel + ", isDiscount="
				+ isDiscount + ", orderSn=" + orderSn + ", userCode="
				+ userCode + ", uuid=" + uuid + ", productDays=" + productDays
				+ ", productPrice=" + productPrice + ", productType="
				+ productType + ", buyType=" + buyType + ", isPay=" + isPay
				+ ", productUuid=" + productUuid + ", orderName=" + orderName
				+ ", transactionId=" + transactionId + ", payType=" + payType
				+ ", deviceType=" + deviceType + ", marketChannel="
				+ marketChannel + "]";
	}
	
}

	