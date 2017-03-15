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

import com.eeduspace.cibn.persist.enumeration.BuyTypeEnum;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;

/**
 * @author zhuchaowei
 * 2016年4月19日
 * Description 
 */
@Entity
@Table(name = "cibn_vip_buy_record")
public class VipBuyRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	
	/**
	 * 设备类型
	 */
	@Column(name = "device_type")
	private String deviceType;
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	// 唯一标识
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	/**
	 * 人员UUID
	 */
	@Column(name = "user_code")
	private String userCode;
	/**  
	 * 支付类型    weixinpay   alipay
	 */
	@Column(name="pay_type")
	private String payType;
	/**
	 * 诊断报告UUID
	 */
	@Column(name = "diagnostic_uuid")
	private String diagnositcUUID;
	/**
	 * 市场渠道
	 */
	@Column(name="market_channel")
	private String marketChannel;
	public String getMarketChannel() {
		return marketChannel;
	}
	public void setMarketChannel(String marketChannel) {
		this.marketChannel = marketChannel;
	}
	/**
	 * 订单名称
	 */
	@Column(name="order_name")
	private String orderName;
	/**
	 * 订单流水号
	 */
	@Column(name = "order_sn")
	private String orderSN;
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "create_time")
	private Date createDate = new Date();
	/**
	 * VIP价格
	 */
	@Column(name = "vip_price")
	private Double vipPrice;
	/**
	 * VIP类型
	 */
	@Column(name = "vip_type")
	private String vipType;
	/**
	 * 本次购买VIP天数
	 */
	@Column(name = "vip_days")
	private int vipDays;
	/**
	 * 微信订单流水号 微信生成
	 */
	@Column(name = "transaction_id")
	private String transactionId;
	/**
	 * 删除标识  true 删除  false 未删除
	 */
	@Column(name = "is_del")
	private boolean isDel=false;
	/**
	 * 是否优惠 true 优惠  false 不优惠
	 */
	@Column(name = "is_discount")
	private boolean isDiscount;
	/**
	 * 是否支付 true 支付 false 未支付
	 */
	@Column(name="is_pay")
	private boolean isPay;
	/**
	 * 优惠差价
	 */
	@Column(name = "discount_difference")
	private Double discountDifference;
	@Column(name="buy_type")
	private BuyTypeEnum buyType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public String getVipType() {
		return vipType;
	}
	public void setVipType(String vipType) {
		this.vipType = vipType;
	}
	public int getVipDays() {
		return vipDays;
	}
	public void setVipDays(int vipDays) {
		this.vipDays = vipDays;
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
	public BuyTypeEnum getBuyType() {
		return buyType;
	}
	public void setBuyType(BuyTypeEnum buyType) {
		this.buyType = buyType;
	}
	public boolean isPay() {
		return isPay;
	}
	public void setPay(boolean isPay) {
		this.isPay = isPay;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getDiagnositcUUID() {
		return diagnositcUUID;
	}
	public void setDiagnositcUUID(String diagnositcUUID) {
		this.diagnositcUUID = diagnositcUUID;
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
	
}
