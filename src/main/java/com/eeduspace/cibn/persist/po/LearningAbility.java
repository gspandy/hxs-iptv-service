package com.eeduspace.cibn.persist.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eeduspace.cibn.persist.enumeration.LearnAbilityTypeEnum;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
/**
 * 学习能力po
 * @author zhuchaowei
 * 2016年4月27日
 * Description
 */
@Entity
@Table(name="cibn_learning_ability")
public class LearningAbility {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	// 唯一标识
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	/**
	 * 人员UUID
	 */
	@Column(name = "user_code")
	private String userCode;
	/**
	 * 试卷code
	 */
	@Column(name = "paper_code")
	private String paperCode;
	/**
	 * 能力分值
	 */
	@Column(name = "ability_score")
	private Double abilityScore;
	/**
	 * 能力类型
	 */
	@Column(name = "ability_type")
	private LearnAbilityTypeEnum abilityType;
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
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPaperCode() {
		return paperCode;
	}
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}
	public LearnAbilityTypeEnum getAbilityType() {
		return abilityType;
	}
	public void setAbilityType(LearnAbilityTypeEnum abilityType) {
		this.abilityType = abilityType;
	}
	public Double getAbilityScore() {
		return abilityScore;
	}
	public void setAbilityScore(Double abilityScore) {
		this.abilityScore = abilityScore;
	}
	
	
	
}
