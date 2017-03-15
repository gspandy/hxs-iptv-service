package com.eeduspace.cibn.persist.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eeduspace.uuims.comm.util.base.UIDGenerator;

/**
 * 答题结果类
 * @author zhuchaowei
 * 2016年5月4日
 * Description
 */
@Entity
@Table(name = "cibn_answer_result")
public class AnswerResult {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	// 唯一标识
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	/**
	 * 诊断报告UUID
	 */
	@Column(name = "diagnostic_uuid")
	private String diagnosticUUID;
	/**
	 * 正确选项
	 */
	@Column(name = "right_option")
	private String rightOption;
	/**
	 * 用户选项
	 */
	@Column(name = "user_option")
	private String userOption;
	/**
	 * 是否正确  true 正确  false 错误
	 */
	@Column(name = "is_right")
	private boolean isRight; 
	/**
	 * 题干信息
	 */
	@Column(name = "answerInfo")
	private String answerInfo;
	/**
	 * 题目解析列表
	 */
	@Column(name = "answer_analysis")
	private String answerAnalysis;
	/**
	 * 题目序号
	 */
	@Column(name = "subject_sn")
	private String subjectSn;
	/**
	 * 题目分数
	 */
	@Column(name = "subject_score")
	private String subjectScore;
	/**
	 * 语音解析
	 */
	@Column(name = "voice_analysis")
	private String voiceAnalysis;
	/**
	 * 选项
	 */
	@Column(name="option_info")
	private String optionInfo;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "create_time")
	private Date createDate = new Date();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public boolean isRight() {
		return isRight;
	}
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}
	public String getAnswerInfo() {
		return answerInfo;
	}
	public void setAnswerInfo(String answerInfo) {
		this.answerInfo = answerInfo;
	}
	public String getAnswerAnalysis() {
		return answerAnalysis;
	}
	public void setAnswerAnalysis(String answerAnalysis) {
		this.answerAnalysis = answerAnalysis;
	}
	public String getSubjectSn() {
		return subjectSn;
	}
	public void setSubjectSn(String subjectSn) {
		this.subjectSn = subjectSn;
	}
	public String getOptionInfo() {
		return optionInfo;
	}
	public void setOptionInfo(String optionInfo) {
		this.optionInfo = optionInfo;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	
}
