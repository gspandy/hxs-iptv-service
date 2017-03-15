package com.eeduspace.cibn.model;


public class ChallengeRequestModel extends BaseResourceModel {
	/**
	 * 被挑战者战斗力
	 */
	private String challengerCombatValue;
	/**
	 * 被挑战者战斗记录UUID
	 */
	private String challengerFightUUID;
	/**
	 * 用户用时
	 */
	private Long userUseTime;
	/**
	 * 用户得分
	 */
	private String getScore;
	/**
	 * 用户当前排名
	 */
	private Long userRank;
	/**
	 * 用户战斗记录
	 */
	private String userFightUUID;
	/**
	 * 试卷code
	 */
	private String paperUUID;
	/**
	 * 被挑战者code
	 */
	private String challengerCode;
	private String challengerName;
	/**
	 * 挑战类型
	 */
	private String challengeType;
	/**
	 * 滑动方向
	 */
	private String order;
	/**
	 * 查询开始游标
	 */
	private Long   start;
	/**
	 * 显示条目
	 */
	private Long item;
	/**
	 * 用户code
	 */
	private String userCode;
	/**
	 * 挑战排行榜类型  frined 好友榜    other 全国榜
	 */
	private String rankType;
	
	/**
	 * 用户手机号
	 */
	private String mobile;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public Long getItem() {
		return item;
	}
	public void setItem(Long item) {
		this.item = item;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getRankType() {
		return rankType;
	}
	public void setRankType(String rankType) {
		this.rankType = rankType;
	}
	public String getPaperUUID() {
		return paperUUID;
	}
	public void setPaperUUID(String paperUUID) {
		this.paperUUID = paperUUID;
	}
	public String getChallengerCode() {
		return challengerCode;
	}
	public void setChallengerCode(String challengerCode) {
		this.challengerCode = challengerCode;
	}
	public String getChallengeType() {
		return challengeType;
	}
	public void setChallengeType(String challengeType) {
		this.challengeType = challengeType;
	}
	public String getChallengerCombatValue() {
		return challengerCombatValue;
	}
	public void setChallengerCombatValue(String challengerCombatValue) {
		this.challengerCombatValue = challengerCombatValue;
	}
	public String getChallengerFightUUID() {
		return challengerFightUUID;
	}
	public void setChallengerFightUUID(String challengerFightUUID) {
		this.challengerFightUUID = challengerFightUUID;
	}
	public String getGetScore() {
		return getScore;
	}
	public void setGetScore(String getScore) {
		this.getScore = getScore;
	}
	public Long getUserRank() {
		return userRank;
	}
	public void setUserRank(Long userRank) {
		this.userRank = userRank;
	}
	public String getUserFightUUID() {
		return userFightUUID;
	}
	public void setUserFightUUID(String userFightUUID) {
		this.userFightUUID = userFightUUID;
	}
	public Long getUserUseTime() {
		return userUseTime;
	}
	public void setUserUseTime(Long userUseTime) {
		this.userUseTime = userUseTime;
	}
	public String getChallengerName() {
		return challengerName;
	}
	public void setChallengerName(String challengerName) {
		this.challengerName = challengerName;
	}
//	map.put("gradeCode", "12");
//	map.put("subjectCode", "2");
//	map.put("bookType", "人教");
	private String gradeCode;
	private String subjectCode;
	private String bookType;

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
	public String getBookType() {
	
		return bookType;
	}
	public void setBookType(String bookType) {
	
		this.bookType = bookType;
	}
}
