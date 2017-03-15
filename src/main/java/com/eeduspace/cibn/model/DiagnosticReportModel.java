package com.eeduspace.cibn.model;

import java.util.List;
import java.util.Map;

/**
 * @author zhuchaowei 2016年4月20日 Description 诊断报告model
 */
public class DiagnosticReportModel {
	private boolean isBuy;

	private String volumeName;
	private String volumeCode;
	private String stageCode;

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public String getVolumeCode() {
		return volumeCode;
	}

	public void setVolumeCode(String volumeCode) {
		this.volumeCode = volumeCode;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

	/**
	 * 诊断时间
	 */
	private String diagnosticDate;
	private String paperUUID;
	/**
	 * 学段名称
	 */
	private String stageName;
	/**
	 * 唯一标识
	 */
	private String diagnosticReportUUID;
	private String userCode;
	/**
	 * 诊断报告价格
	 */
	private Double diagnosticPrice;
	/**
	 * 分数
	 */
	private Integer score;
	/**
	 * 用时
	 */
	private Long useTime;
	/**
	 * 用户排名
	 */
	private Long userRanking;
	/**
	 * 排名比例
	 */
	private String rankPercentage;
	/**
	 * 试卷code
	 */
	private String paperCode;
	/**
	 * 试卷名称
	 */
	private String paperName;
	private Map<Integer, Object> avgMap;

	public Map<Integer, Object> getAvgMap() {
		return avgMap;
	}

	public void setAvgMap(Map<Integer, Object> avgMap) {
		this.avgMap = avgMap;
	}

	public Map<Integer, Object> getMaxMap() {
		return maxMap;
	}

	public void setMaxMap(Map<Integer, Object> maxMap) {
		this.maxMap = maxMap;
	}

	private Map<Integer, Object> maxMap;
	/**
	 * 学年code
	 */
	private String gradeCode;
	/**
	 * 学课code
	 */
	private String subjectCode;
	/**
	 * 教材版本code
	 */
	private String versionCode;

	/**
	 * 学年名称
	 */
	private String gradeName;
	/**
	 * 学课名称
	 */
	private String subjectName;
	/**
	 * 教材版本名称
	 */
	private String versionName;
	/**
	 * 单元code
	 */
	private String unitCode;
	/**
	 * 诊断结果
	 */
	private List<AnswerResultModel> diagnosticResult;
	/**
	 * 知识点
	 */
	private List<KnowledgeModel> knowledge;
	/**
	 * 知识点code
	 */
	private List<KnowledgeModel> knowledgeCode;
	/**
	 * 推荐课程（课程code和课程名称）
	 */
	private List<WeakKnowledgePointModel> recommendedCourses;
	/**
	 * 产生式实体列表
	 */
	private List<ProducesModel> producesModels;
	/**
	 * 弱项知识点对应提高分值列表
	 */
	private String weak;
	/*** 知识点掌握程度统计 */
	private Map<String, Double> knowledgeMasteryMap;
	/***
	 * 产生式对应能力统计
	 */
	private String productionChartMap;

	/**
	 * 删除标识
	 */
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getUserRanking() {
		return userRanking;
	}

	public void setUserRanking(Long userRanking) {
		this.userRanking = userRanking;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getWeak() {
		return weak;
	}

	public void setWeak(String weak) {
		this.weak = weak;
	}

	public Map<String, Double> getKnowledgeMasteryMap() {
		return knowledgeMasteryMap;
	}

	public void setKnowledgeMasteryMap(Map<String, Double> knowledgeMasteryMap) {
		this.knowledgeMasteryMap = knowledgeMasteryMap;
	}

	public String getProductionChartMap() {
		return productionChartMap;
	}

	public void setProductionChartMap(String productionChartMap) {
		this.productionChartMap = productionChartMap;
	}

	public String getRankPercentage() {
		return rankPercentage;
	}

	public void setRankPercentage(String rankPercentage) {
		this.rankPercentage = rankPercentage;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
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

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public String getPaperCode() {
		return paperCode;
	}

	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}

	public List<AnswerResultModel> getDiagnosticResult() {
		return diagnosticResult;
	}

	public void setDiagnosticResult(List<AnswerResultModel> diagnosticResult) {
		this.diagnosticResult = diagnosticResult;
	}

	public List<WeakKnowledgePointModel> getRecommendedCourses() {
		return recommendedCourses;
	}

	public void setRecommendedCourses(
			List<WeakKnowledgePointModel> recommendedCourses) {
		this.recommendedCourses = recommendedCourses;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getDiagnosticReportUUID() {
		return diagnosticReportUUID;
	}

	public void setDiagnosticReportUUID(String diagnosticReportUUID) {
		this.diagnosticReportUUID = diagnosticReportUUID;
	}

	public List<ProducesModel> getProducesModels() {
		return producesModels;
	}

	public void setProducesModels(List<ProducesModel> producesModels) {
		this.producesModels = producesModels;
	}

	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}

	public List<KnowledgeModel> getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(List<KnowledgeModel> knowledge) {
		this.knowledge = knowledge;
	}

	public List<KnowledgeModel> getKnowledgeCode() {
		return knowledgeCode;
	}

	public void setKnowledgeCode(List<KnowledgeModel> knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}

	public Double getDiagnosticPrice() {
		return diagnosticPrice;
	}

	public void setDiagnosticPrice(Double diagnosticPrice) {
		this.diagnosticPrice = diagnosticPrice;
	}

	public String getDiagnosticDate() {
		return diagnosticDate;
	}

	public void setDiagnosticDate(String diagnosticDate) {
		this.diagnosticDate = diagnosticDate;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getPaperUUID() {
		return paperUUID;
	}

	public void setPaperUUID(String paperUUID) {
		this.paperUUID = paperUUID;
	}

	public boolean isBuy() {
		return isBuy;
	}

	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}

}
