package com.eeduspace.cibn.model;

import java.util.List;




public class BaseDataModel {

	//学段code
	private String stageCode;
	//学年code
	private String gradeCode;
	//学科code
	private String subjectCode;
	//教材code
	private String bookTypeCode;
	//教材版本上下册code
	private String bookTypeVersionCode;
	//单元code
	private String unitCode;
	//获取练习题的个数
	private Integer count;
	//其他返回code
	private String ctbCode;
	//用户code
	private String userCode;
	//课程code
	private String courseCode;
	//产生式code
	private String productionCode;
	//视频对象集合
	private List<CourseVideoModel> reponseVedio;
	//是否收藏标识
	private Boolean isDel;
	//练习题集合(从资源库取值转化使用)
	private List<ExaminationModel> productAndSelectCountBeans;
	//试题集合(从资源库取值转化使用)
	private List<QuestionModel> questionObject ;
	//视频播放地址
	private String urlWeVideo;
	//练习题集合(传给前台的组装参数)
	private List<ExaminationModel> questions;

	private int version=0;
	
	public List<CourseVideoModel> getReponseVedio() {
		return reponseVedio;
	}
	public void setReponseVedio(List<CourseVideoModel> reponseVedio) {
		this.reponseVedio = reponseVedio;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Boolean getIsDel() {
		return isDel;
	}
	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCtbCode() {
		return ctbCode;
	}
	public void setCtbCode(String ctbCode) {
		this.ctbCode = ctbCode;
	}
	public List<ExaminationModel> getProductAndSelectCountBeans() {
		return productAndSelectCountBeans;
	}
	public void setProductAndSelectCountBeans(
			List<ExaminationModel> productAndSelectCountBeans) {
		this.productAndSelectCountBeans = productAndSelectCountBeans;
	}
	public List<QuestionModel> getQuestionObject() {
		return questionObject;
	}
	public void setQuestionObject(List<QuestionModel> questionObject) {
		this.questionObject = questionObject;
	}
	public String getUrlWeVideo() {
		return urlWeVideo;
	}
	public void setUrlWeVideo(String urlWeVideo) {
		this.urlWeVideo = urlWeVideo;
	}
	public List<ExaminationModel> getQuestions() {
		return questions;
	}
	public void setQuestions(List<ExaminationModel> questions) {
		this.questions = questions;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getProductionCode() {
		return productionCode;
	}
	public void setProductionCode(String productionCode) {
		this.productionCode = productionCode;
	}
	public String getStageCode() {
		return stageCode;
	}
	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
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
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
	public String getBookTypeVersionCode() {
		return bookTypeVersionCode;
	}
	public void setBookTypeVersionCode(String bookTypeVersionCode) {
		this.bookTypeVersionCode = bookTypeVersionCode;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}





}
