package com.eeduspace.cibn.model;

import java.util.Date;

public class AppUpdateModel {
	
	private Long id;
	private String uuid ;
	private Date createDate = new Date();
	private String appName;
	private Boolean available;
	private int appVersion;
	private String appDescribe;
	private String downUrl;
	private Boolean necessary;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public int getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(int appVersion) {
		this.appVersion = appVersion;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getAppDescribe() {
		return appDescribe;
	}
	public void setAppDescribe(String appDescribe) {
		this.appDescribe = appDescribe;
	}
	public Boolean getNecessary() {
		return necessary;
	}
	public void setNecessary(Boolean necessary) {
		this.necessary = necessary;
	}
	
	
}
