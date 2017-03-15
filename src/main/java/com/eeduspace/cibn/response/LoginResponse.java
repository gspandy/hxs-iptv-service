package com.eeduspace.cibn.response;

import java.io.Serializable;
/**
 * Author: yongqian.chen
 * Date: 2016/9/24
 * Description:
 */
public class LoginResponse implements Serializable{
	private static final long serialVersionUID = 2423424L;
	
	private String token;
	private String refreshToken;
	private String expires;
	private String sessionId;
	private String openId;
	private String phone;
	private String status;
	private String createDate;
	private String sex;
	private String equipmentType;
	private String productType;
	private String productId;
	private String productName;
	private boolean isBandQQ;
	private boolean isBandWX;
	private boolean isBandSina;
	private boolean isBandEmail;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public boolean isBandQQ() {
		return isBandQQ;
	}

	public void setBandQQ(boolean isBandQQ) {
		this.isBandQQ = isBandQQ;
	}

	public boolean isBandWX() {
		return isBandWX;
	}

	public void setBandWX(boolean isBandWX) {
		this.isBandWX = isBandWX;
	}

	public boolean isBandSina() {
		return isBandSina;
	}

	public void setBandSina(boolean isBandSina) {
		this.isBandSina = isBandSina;
	}

	public boolean isBandEmail() {
		return isBandEmail;
	}

	public void setBandEmail(boolean isBandEmail) {
		this.isBandEmail = isBandEmail;
	}
}
