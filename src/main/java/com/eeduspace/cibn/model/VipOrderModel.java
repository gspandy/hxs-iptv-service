package com.eeduspace.cibn.model;

import com.eeduspace.cibn.persist.enumeration.BuyTypeEnum;

/**
 * @author zhuchaowei
 * 2016年4月20日
 * Description VIP订单 model
 */
public class VipOrderModel {
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
	private String diagnosticUUID;
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
	 * VIP类型
	 */
	private String vipOrderType;
	/**
	 * 是否优惠 true 优惠 falas 不优惠
	 */
	private boolean isDiscount;
	/**
	 * 优惠差价
	 */
	private Double discountDifference;
	/**
	 * VIP天数
	 */
	private Integer vipDays;
	/**
	 * 购买日期
	 */
	private String buyDate;
	/**
	 * 支付类型
	 */
	private String payType;
	
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
	public Integer getVipDays() {
		return vipDays;
	}
	public void setVipDays(Integer vipDays) {
		this.vipDays = vipDays;
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
	public String getVipOrderType() {
		return vipOrderType;
	}
	public void setVipOrderType(String vipOrderType) {
		this.vipOrderType = vipOrderType;
	}
	public void setPayQRUrl(String payQRUrl) {
		this.payQRUrl = payQRUrl;
	}
	public String getDiagnosticUUID() {
		return diagnosticUUID;
	}
	public void setDiagnosticUUID(String diagnosticUUID) {
		this.diagnosticUUID = diagnosticUUID;
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
	
}
