package com.eeduspace.cibn.model.response;

import java.util.List;

import com.eeduspace.cibn.model.WebVideoModel;

public class VideoByKnowledge {
	private String knowledge;
	private String knowledgeName;
	private List<WebVideoModel> resourceVideoList;
	public String getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}
	public List<WebVideoModel> getResourceVideoList() {
		return resourceVideoList;
	}
	public void setResourceVideoList(List<WebVideoModel> resourceVideoList) {
		this.resourceVideoList = resourceVideoList;
	}
	public String getKnowledgeName() {
		return knowledgeName;
	}
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}
}
