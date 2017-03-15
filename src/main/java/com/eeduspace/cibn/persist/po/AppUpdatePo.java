package com.eeduspace.cibn.persist.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eeduspace.uuims.comm.util.base.UIDGenerator;

@Entity
@Table(name = "cibn_app_update")
public class AppUpdatePo {
	/**
	 * id
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	/**
	 * uuid
	 * */
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	/**
	 * 加入时间
	 * */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "create_time")
	private Date createDate = new Date();
	/**
	 * app名称
	 * */
	@Column(name = "app_name")
	private String appName;
	/**
	 * app当前版本是否可用
	 * */
	@Column(name = "available")
	private Boolean available;
	/**
	 * app版本
	 * */
	@Column(name = "app_version")
	private int appVersion;
	/**
	 * app描述
	 * */
	@Lob
	@Column(name = "app_describe")
	private String appDescribe;
	/**
	 * app现在地址
	 * */
	@Column(name = "down_url")
	private String downUrl;
	/**
	 * app是否必须更新
	 * */
	@Column(name = "necessary")
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
	public String getAppDescribe() {
		return appDescribe;
	}
	public void setAppDescribe(String appDescribe) {
		this.appDescribe = appDescribe;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public Boolean getNecessary() {
		return necessary;
	}
	public void setNecessary(Boolean necessary) {
		this.necessary = necessary;
	}
	
	
}
