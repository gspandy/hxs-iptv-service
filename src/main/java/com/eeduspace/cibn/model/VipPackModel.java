package com.eeduspace.cibn.model;


public class VipPackModel {
	private Double vipPrice;
	private String vipType;
	private Integer vipDays;
	private String vipDesc;
	private String 	discountStartDate;
	private String discountEndDate;
	public String getDiscountStartDate() {
		return discountStartDate;
	}
	public void setDiscountStartDate(String discountStartDate) {
		this.discountStartDate = discountStartDate;
	}
	public String getDiscountEndDate() {
		return discountEndDate;
	}
	public void setDiscountEndDate(String discountEndDate) {
		this.discountEndDate = discountEndDate;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Integer getVipDays() {
		return vipDays;
	}
	public void setVipDays(Integer vipDays) {
		this.vipDays = vipDays;
	}
	public String getVipDesc() {
		return vipDesc;
	}
	public void setVipDesc(String vipDesc) {
		this.vipDesc = vipDesc;
	}
	public String getVipType() {
		return vipType;
	}
	public void setVipType(String vipType) {
		this.vipType = vipType;
	}
}
