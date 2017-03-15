
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductPo.java 
	 * 包名:com.eeduspace.cibn.persist.po 
	 * 创建日期:2016年12月19日上午10:18:31 
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

import com.eeduspace.uuims.comm.util.base.UIDGenerator;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductPo    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 上午10:18:31    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 上午10:18:31    
 * 修改备注：       
 * @version </pre>    
 */
@Entity
@Table(name = "cibn_product")
public class ProductPo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	
	@Column(name="stageCode")
	private String stageCode;
	
	@Column(name="stageName")
	private String stageName;
	
	@Column(name="gradeCode")
	private String gradeCode;
	
	@Column(name="gradeName")
	private String gradeName;
	
	@Column(name="subjectCode")
	private String subjectCode;
	
	@Column(name="subjectName")
	private String subjectName;
	
	@Column(name="bookTypeCode")
	private String bookTypeCode;
	
	@Column(name="bookTypeName")
	private String bookTypeName;
	
	@Column(name="ctbCode")
	private String ctbCode;
	
	@Column(name="ctbName")
	private String ctbName;
	
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "createDate")
	private Date createDate = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "modifyDate")
	private Date modifyDate;
	
	@Column(name="productPackType")
	private String productPackType;

	public Long getId() {
	
		return id;
	}

	public void setId(Long id) {
	
		this.id = id;
	}

	public String getStageCode() {
	
		return stageCode;
	}

	public void setStageCode(String stageCode) {
	
		this.stageCode = stageCode;
	}

	public String getStageName() {
	
		return stageName;
	}

	public void setStageName(String stageName) {
	
		this.stageName = stageName;
	}

	public String getGradeCode() {
	
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
	
		this.gradeCode = gradeCode;
	}

	public String getGradeName() {
	
		return gradeName;
	}

	public void setGradeName(String gradeName) {
	
		this.gradeName = gradeName;
	}

	public String getSubjectCode() {
	
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
	
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
	
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
	
		this.subjectName = subjectName;
	}

	public String getBookTypeCode() {
	
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
	
		this.bookTypeCode = bookTypeCode;
	}

	public String getBookTypeName() {
	
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
	
		this.bookTypeName = bookTypeName;
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

	public Date getModifyDate() {
	
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
	
		this.modifyDate = modifyDate;
	}

	public String getCtbCode() {
	
		return ctbCode;
	}

	public void setCtbCode(String ctbCode) {
	
		this.ctbCode = ctbCode;
	}

	public String getCtbName() {
	
		return ctbName;
	}

	public void setCtbName(String ctbName) {
	
		this.ctbName = ctbName;
	}
	
	public String getProductPackType() {
	
		return productPackType;
	}

	public void setProductPackType(String productPackType) {
	
		this.productPackType = productPackType;
	}

	@Override
	public String toString() {
		return "ProductPo [id=" + id + ", stageCode=" + stageCode
				+ ", stageName=" + stageName + ", gradeCode=" + gradeCode
				+ ", gradeName=" + gradeName + ", subjectCode=" + subjectCode
				+ ", subjectName=" + subjectName + ", bookTypeCode="
				+ bookTypeCode + ", bookTypeName=" + bookTypeName
				+ ", ctbCode=" + ctbCode + ", ctbName=" + ctbName + ", uuid="
				+ uuid + ", createDate=" + createDate + ", modifyDate="
				+ modifyDate + ", productPackType=" + productPackType + "]";
	}
	
}

	