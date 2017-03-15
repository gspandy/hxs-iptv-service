package com.eeduspace.cibn.model;
/**
 * 挑战排行榜榜单信息
 * @author zhuchaowei
 * 2016年7月25日
 * Description
 */
public class WeekRankingModel {
	private String championTimes;
	private String weekFightValueFloat;
	private String headImgUrl;
	private String nickName;
	private String userCode;
	private String currentRank;
	/**
	 * 是否可挑战 false 不可挑战  true 可挑战
	 */
	private String isChallenge="false";
	private String mobile;
	private String isVip;
	private String overdue;
	
	private String ranking;//排名

	public String getChampionTimes() {
	
		return championTimes;
	}

	public void setChampionTimes(String championTimes) {
	
		this.championTimes = championTimes;
	}

	public String getWeekFightValueFloat() {
	
		return weekFightValueFloat;
	}

	public void setWeekFightValueFloat(String weekFightValueFloat) {
	
		this.weekFightValueFloat = weekFightValueFloat;
	}

	public String getHeadImgUrl() {
	
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
	
		this.headImgUrl = headImgUrl;
	}

	public String getNickName() {
	
		return nickName;
	}

	public void setNickName(String nickName) {
	
		this.nickName = nickName;
	}

	public String getUserCode() {
	
		return userCode;
	}

	public void setUserCode(String userCode) {
	
		this.userCode = userCode;
	}

	public String getCurrentRank() {
	
		return currentRank;
	}

	public void setCurrentRank(String currentRank) {
	
		this.currentRank = currentRank;
	}

	public String getIsChallenge() {
	
		return isChallenge;
	}

	public void setIsChallenge(String isChallenge) {
	
		this.isChallenge = isChallenge;
	}

	public String getMobile() {
	
		return mobile;
	}

	public void setMobile(String mobile) {
	
		this.mobile = mobile;
	}

	public String getIsVip() {
	
		return isVip;
	}

	public void setIsVip(String isVip) {
	
		this.isVip = isVip;
	}

	public String getOverdue() {
	
		return overdue;
	}

	public void setOverdue(String overdue) {
	
		this.overdue = overdue;
	}

	public String getRanking() {
	
		return ranking;
	}

	public void setRanking(String ranking) {
	
		this.ranking = ranking;
	}

	@Override
	public String toString() {
		return "WeekRankingModel [championTimes=" + championTimes
				+ ", weekFightValueFloat=" + weekFightValueFloat
				+ ", headImgUrl=" + headImgUrl + ", nickName=" + nickName
				+ ", userCode=" + userCode + ", currentRank=" + currentRank
				+ ", isChallenge=" + isChallenge + ", mobile=" + mobile
				+ ", isVip=" + isVip + ", overdue=" + overdue + ", ranking="
				+ ranking + "]";
	}
	
	
}
