package com.eeduspace.cibn.model.request;

public class PaperRequest {
	/**
	 * 年级code
	 */
	private String gradeCode;
	/**
	 * 学科code
	 */
	private String subjectCode;
	/**
	 * 教材版本
	 */
	private String booktype;
	/**
	 * 单元code
	 */
	private String volume;
	/**
	 * 试卷类型
	 */
	private String type;
	/**
	 * 条数
	 */
	private Integer pageSize;
	/**
	 * 总页数
	 */
	private Integer totalPage;
	/**
	 * 当前页数
	 */
	private Integer cp;
	public PaperRequest(String gradeCode, String subjectCode, String booktype,
			String volume, String type, Integer pageSize, Integer cp) {
		super();
		this.gradeCode = gradeCode;
		this.subjectCode = subjectCode;
		this.booktype = booktype;
		this.volume = volume;
		this.type = type;
		this.pageSize = pageSize;
		this.cp = cp;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCp() {
		return cp;
	}
	public void setCp(Integer cp) {
		this.cp = cp;
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
	public String getBooktype() {
		return booktype;
	}
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}
