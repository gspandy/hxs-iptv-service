
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductOrderModel.java 
	 * 包名:com.eeduspace.cibn.model 
	 * 创建日期:2016年12月19日下午4:14:52 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.model;

import com.eeduspace.cibn.persist.enumeration.BuyTypeEnum;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductOrderModel    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 下午4:14:52    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 下午4:14:52    
 * 修改备注：       
 * @version </pre>    
 */

public class ProductOrderModel {
	private UserModel userModel;
	/**
	 * 订单名称
	 */
	private String orderName;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**唯一标识*/
	private String orderUUID;
	private BuyTypeEnum buyType;
	/**
	 * 诊断报告的UUID
	 */
	private String productUuid;
	private String userCode;
	/**
	 * 市场渠道
	 */
	private String marketChannel;
	/**
	 * 二维码url
	 */
	private String payQRUrl;
	/**
	 * 订单流水号
	 */
	private String orderSn;
	/**
	 * 订单价格
	 */
	private Double orderPrice;
	/**
	 * product类型
	 */
	private String productOrderType;
	/**
	 * 是否优惠 true 优惠 falas 不优惠
	 */
	private boolean isDiscount;
	/**
	 * 优惠差价
	 */
	private Double discountDifference;
	/**
	 * 商品持续时间
	 */
	private String expireTime;
	/**
	 * 购买日期
	 */
	private String buyDate;
	/**
	 * 支付类型
	 */
	private String payType;
	
	private String ctbCode;
	
	private String ip;
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public boolean isDiscount() {
		return isDiscount;
	}
	public void setDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}
	public Double getDiscountDifference() {
		return discountDifference;
	}
	public void setDiscountDifference(Double discountDifference) {
		this.discountDifference = discountDifference;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getOrderUUID() {
		return orderUUID;
	}
	public void setOrderUUID(String orderUUID) {
		this.orderUUID = orderUUID;
	}
	public String getPayQRUrl() {
		return payQRUrl;
	}
	public void setPayQRUrl(String payQRUrl) {
		this.payQRUrl = payQRUrl;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	public BuyTypeEnum getBuyType() {
		return buyType;
	}
	public void setBuyType(BuyTypeEnum buyType) {
		this.buyType = buyType;
	}
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getMarketChannel() {
		return marketChannel;
	}
	public void setMarketChannel(String marketChannel) {
		this.marketChannel = marketChannel;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getProductOrderType() {
	
		return productOrderType;
	}
	public void setProductOrderType(String productOrderType) {
	
		this.productOrderType = productOrderType;
	}
	public String getProductUuid() {
	
		return productUuid;
	}
	public void setProductUuid(String productUuid) {
	
		this.productUuid = productUuid;
	}
	public String getCtbCode() {
	
		return ctbCode;
	}
	public void setCtbCode(String ctbCode) {
	
		this.ctbCode = ctbCode;
	}
	public String getExpireTime() {
	
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
	
		this.expireTime = expireTime;
	}
	public String getIp() {
	
		return ip;
	}
	public void setIp(String ip) {
	
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "ProductOrderModel [userModel=" + userModel + ", orderName="
				+ orderName + ", deviceType=" + deviceType + ", orderUUID="
				+ orderUUID + ", buyType=" + buyType + ", productUuid="
				+ productUuid + ", userCode=" + userCode + ", marketChannel="
				+ marketChannel + ", payQRUrl=" + payQRUrl + ", orderSn="
				+ orderSn + ", orderPrice=" + orderPrice
				+ ", productOrderType=" + productOrderType + ", isDiscount="
				+ isDiscount + ", discountDifference=" + discountDifference
				+ ", expireTime=" + expireTime + ", buyDate=" + buyDate
				+ ", payType=" + payType + ", ctbCode=" + ctbCode + ", ip="
				+ ip + "]";
	}
	
}

	