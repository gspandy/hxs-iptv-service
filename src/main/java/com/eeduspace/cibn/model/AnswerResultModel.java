package com.eeduspace.cibn.model;

import java.util.List;

import com.eeduspace.cibn.model.response.AnaModel;
import com.eeduspace.cibn.model.response.OptModel;


/**
 * 答题结果类
 * @author zhuchaowei
 * 2016年5月4日
 * Description
 */
public class AnswerResultModel {
	private String uuid;
	/**
	 * 诊断报告UUID
	 */
	private String diagnosticUUID;
	/**
	 * 正确选项
	 */
	private String rightOption;
	/**
	 * 用户选项
	 */
	private String userOption;
	/**
	 * 是否正确  true 正确  false 错误
	 */
	private Boolean isRight; 
	/**
	 * 题干信息
	 */
	private String answerInfo;
	/**
	 * 题目code
	 */
	private String code;
	/**
	 * 题目解析列表
	 */
	private List<AnaModel> answerAnalysis;
	/**
	 * 题目序号
	 */
	private String subjectSn;
	/**
	 * 题目分数
	 */
	private String subjectScore;
	/**
	 * 语音解析
	 */
	private String voiceAnalysis;
	/**
	 * 选项信息
	 */
	private List<OptModel> options;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getDiagnosticUUID() {
		return diagnosticUUID;
	}
	public void setDiagnosticUUID(String diagnosticUUID) {
		this.diagnosticUUID = diagnosticUUID;
	}
	public String getRightOption() {
		return rightOption;
	}
	public void setRightOption(String rightOption) {
		this.rightOption = rightOption;
	}
	public String getUserOption() {
		return userOption;
	}
	public void setUserOption(String userOption) {
		this.userOption = userOption;
	}
	public String getAnswerInfo() {
		return answerInfo;
	}
	public void setAnswerInfo(String answerInfo) {
		this.answerInfo = answerInfo;
	}
	public String getSubjectSn() {
		return subjectSn;
	}
	public void setSubjectSn(String subjectSn) {
		this.subjectSn = subjectSn;
	}
	public String getSubjectScore() {
		return subjectScore;
	}
	public void setSubjectScore(String subjectScore) {
		this.subjectScore = subjectScore;
	}
	public String getVoiceAnalysis() {
		return voiceAnalysis;
	}
	public void setVoiceAnalysis(String voiceAnalysis) {
		this.voiceAnalysis = voiceAnalysis;
	}
	public Boolean getIsRight() {
		return isRight;
	}
	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}
	public List<AnaModel> getAnswerAnalysis() {
		return answerAnalysis;
	}
	public void setAnswerAnalysis(List<AnaModel> answerAnalysis) {
		this.answerAnalysis = answerAnalysis;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<OptModel> getOptions() {
		return options;
	}
	public void setOptions(List<OptModel> options) {
		this.options = options;
	}


	
}
