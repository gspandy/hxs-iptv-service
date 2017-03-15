
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:TSUserModel.java 
	 * 包名:com.eeduspace.cibn.model 
	 * 创建日期:2017年2月6日下午7:22:28 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.model;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：TSUserModel    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年2月6日 下午7:22:28    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年2月6日 下午7:22:28    
 * 修改备注：       
 * @version </pre>    
 */

public class TSUserModel {
	private String userId;
	private String userName;
	private String currentRank;
	private String userUseTime;
	private String userGetScore;
	private String headImgUrl;
	private String gradeCode;
	private String subjectCode;
	private String pageNum;
	private String pageSize;
	private String bookType;
	private String ctbCode;
	private String score;
	private String sex;
	private String mobile;
	public String getUserId() {
	
		return userId;
	}
	public void setUserId(String userId) {
	
		this.userId = userId;
	}
	public String getUserName() {
	
		return userName;
	}
	public void setUserName(String userName) {
	
		this.userName = userName;
	}
	public String getCurrentRank() {
	
		return currentRank;
	}
	public void setCurrentRank(String currentRank) {
	
		this.currentRank = currentRank;
	}
	public String getUserUseTime() {
	
		return userUseTime;
	}
	public void setUserUseTime(String userUseTime) {
	
		this.userUseTime = userUseTime;
	}
	public String getUserGetScore() {
	
		return userGetScore;
	}
	public void setUserGetScore(String userGetScore) {
	
		this.userGetScore = userGetScore;
	}
	public String getHeadImgUrl() {
	
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
	
		this.headImgUrl = headImgUrl;
	}
	public String getGradeCode() {
	
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
	
		this.gradeCode = gradeCode;
	}
	public String getSubjectCode() {
	
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
	
		this.subjectCode = subjectCode;
	}
	public String getPageNum() {
	
		return pageNum;
	}
	public void setPageNum(String pageNum) {
	
		this.pageNum = pageNum;
	}
	public String getPageSize() {
	
		return pageSize;
	}
	public void setPageSize(String pageSize) {
	
		this.pageSize = pageSize;
	}
	public String getBookType() {
	
		return bookType;
	}
	public void setBookType(String bookType) {
	
		this.bookType = bookType;
	}
	public String getCtbCode() {
	
		return ctbCode;
	}
	public void setCtbCode(String ctbCode) {
	
		this.ctbCode = ctbCode;
	}
	public String getScore() {
	
		return score;
	}
	public void setScore(String score) {
	
		this.score = score;
	}
	public String getSex() {
	
		return sex;
	}
	public void setSex(String sex) {
	
		this.sex = sex;
	}
	public String getMobile() {
	
		return mobile;
	}
	public void setMobile(String mobile) {
	
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "TSUserModel [userId=" + userId + ", userName=" + userName
				+ ", currentRank=" + currentRank + ", userUseTime="
				+ userUseTime + ", userGetScore=" + userGetScore
				+ ", headImgUrl=" + headImgUrl + ", gradeCode=" + gradeCode
				+ ", subjectCode=" + subjectCode + ", pageNum=" + pageNum
				+ ", pageSize=" + pageSize + ", bookType=" + bookType
				+ ", ctbCode=" + ctbCode + ", score=" + score + ", sex=" + sex
				+ ", mobile=" + mobile + "]";
	}
}

	