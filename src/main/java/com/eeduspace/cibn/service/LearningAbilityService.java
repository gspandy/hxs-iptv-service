package com.eeduspace.cibn.service;

import com.eeduspace.cibn.persist.enumeration.LearnAbilityTypeEnum;
import com.eeduspace.cibn.persist.po.LearningAbility;

public interface LearningAbilityService {
	LearningAbility savePo(LearningAbility learningAbility);
	/**
	 * 获取学习能力平均数值
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月27日 下午6:17:08
	 * @param userCode
	 * @param paperCode
	 * @return
	 */
	Object getAve(String paperCode,LearnAbilityTypeEnum lTypeEnum);
	/**
	 * 获取学习能力最大值
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月27日 下午6:16:57
	 * @param userCode
	 * @param paperCode
	 * @return
	 */
	Object getMax(String paperCode,LearnAbilityTypeEnum lTypeEnum);
}
