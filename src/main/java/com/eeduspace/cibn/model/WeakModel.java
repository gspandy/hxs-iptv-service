package com.eeduspace.cibn.model;
/**
 * @author zhuchaowei
 * 2016年4月20日
 * Description  弱项
 */
public class WeakModel {
	/**
	 * 知识点code
	 */
	private String knowledegCode;
	/**
	 * 知识点名称
	 */
	private String knowledegName;
	/**
	 * 知识点对应的错题数
	 */
	private int errorCount;
	public String getKnowledegCode() {
		return knowledegCode;
	}
	public void setKnowledegCode(String knowledegCode) {
		this.knowledegCode = knowledegCode;
	}
	public String getKnowledegName() {
		return knowledegName;
	}
	public void setKnowledegName(String knowledegName) {
		this.knowledegName = knowledegName;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	
}
