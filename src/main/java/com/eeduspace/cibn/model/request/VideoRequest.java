package com.eeduspace.cibn.model.request;

import java.util.List;

public class VideoRequest {
	/**
	 * 产生式CODE集合
	 */
	private List<String>list;
	private String gradeCode;
	private String subjectCode;
	private String bookTypeCode;
	private List<String> knowledges; 
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

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public List<String> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<String> knowledges) {
		this.knowledges = knowledges;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
}
