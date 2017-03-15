package com.eeduspace.cibn.model;

import java.util.List;

public class KnowledgeModel {
	/**
	 * 题目分值
	 */
	private Double score;

	/** 所有的知识点 */
	private List<WeakKnowledgePointModel> knowledges;

	/** 知识点对应的分数 */
	private Double knowlegesScore;

	/** 是否正确 */
	private Boolean isRight;
	/**
	 * 
	 * @param testScore 题目分值 
	 * @param isRight 是否答对
	 * @param knowledges 包含的知识点
	 */
	
	public KnowledgeModel(Double score, Boolean isRight, List<WeakKnowledgePointModel> knowledges) {
		this.isRight = isRight;
		this.setScore(score);
		this.knowledges = knowledges;
		if(knowledges.size()==0){
			this.knowlegesScore =0.0;
		}else{
			this.knowlegesScore = score / knowledges.size();
		}
	}


	public List<WeakKnowledgePointModel> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<WeakKnowledgePointModel> knowledges) {
		this.knowledges = knowledges;
	}

	public Double getKnowlegesScore() {
		return knowlegesScore;
	}

	public void setKnowlegesScore(Double knowlegesScore) {
		this.knowlegesScore = knowlegesScore;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}


	public Double getScore() {
		return score;
	}


	public void setScore(Double score) {
		this.score = score;
	}

}
