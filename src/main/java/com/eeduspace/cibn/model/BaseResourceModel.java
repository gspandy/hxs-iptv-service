package com.eeduspace.cibn.model;
/**
 * 资源基础信息model
 * @author zhuchaowei
 * 2016年7月12日
 * Description
 */
public class BaseResourceModel {
	/**
	 * 学科code
	 */
	private String subjectCode;
	/**
	 * 学年code
	 */
	private String gradeCode;
	/**
	 * 学段code
	 */
	private String stageCode;
	/**
	 * 教材上下册code
	 */
	private String volumeCode;
	/**
	 * 单元code
	 */
	private String unitCode;
	/**
	 * 教材版本code
	 */
	private String bookTypeCode;
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getStageCode() {
		return stageCode;
	}
	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}
	public String getVolumeCode() {
		return volumeCode;
	}
	public void setVolumeCode(String volumeCode) {
		this.volumeCode = volumeCode;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
}
