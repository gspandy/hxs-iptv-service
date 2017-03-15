package com.eeduspace.cibn.model;
/**
 * 诊断结果 model
 * @author zhuchaowei
 * 2016年4月20日
 * Description
 */
public class DiagnosticResultModel {
	/**
	 * 题目code
	 */
	private String subjectCode;
	/**
	 * 正确选项
	 */
	private String rightOption;
	/**
	 * 用户选项
	 */
	private String userOption;
	/**
	 * 是否正确
	 */
	private Boolean isRight;
	/**
	 * 答案解析
	 */
	private String subjectAnalysis;
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getRightOption() {
		return rightOption;
	}
	public void setRightOption(String rightOption) {
		this.rightOption = rightOption;
	}
	public String getUserOption() {
		return userOption;
	}
	public void setUserOption(String userOption) {
		this.userOption = userOption;
	}
	public Boolean getIsRight() {
		return isRight;
	}
	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}
	public String getSubjectAnalysis() {
		return subjectAnalysis;
	}
	public void setSubjectAnalysis(String subjectAnalysis) {
		this.subjectAnalysis = subjectAnalysis;
	}
	
}
