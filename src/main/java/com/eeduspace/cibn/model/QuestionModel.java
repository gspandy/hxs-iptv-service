package com.eeduspace.cibn.model;

import java.util.List;

public class QuestionModel {
	private String productCode;
	private List<ExaminationModel> questionList;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public List<ExaminationModel> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<ExaminationModel> questionList) {
		this.questionList = questionList;
	}
}
