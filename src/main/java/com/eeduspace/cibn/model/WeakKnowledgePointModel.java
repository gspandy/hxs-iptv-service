package com.eeduspace.cibn.model;

import java.util.List;

public class WeakKnowledgePointModel {
	/**
	 * 知识点名称
	 */
	private String knowledgeName;
	
	private String knowledgeCode;
	/**
	 * 知识点错误次数
	 */
	private Integer errorTimes;
	/**
	 * 推荐视频
	 */
	private List<WebVideoModel> webVideoModel;
	public String getKnowledgeName() {
		return knowledgeName;
	}
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}
	public Integer getErrorTimes() {
		return errorTimes;
	}
	public void setErrorTimes(Integer errorTimes) {
		this.errorTimes = errorTimes;
	}
	public String getKnowledgeCode() {
		return knowledgeCode;
	}
	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}
	public List<WebVideoModel> getWebVideoModel() {
		return webVideoModel;
	}
	public void setWebVideoModel(List<WebVideoModel> webVideoModel) {
		this.webVideoModel = webVideoModel;
	}
	
}
