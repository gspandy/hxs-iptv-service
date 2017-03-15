package com.eeduspace.cibn.persist.enumeration;


public enum LearnAbilityTypeEnum {

	/** 记忆能力 */
	MEMORY(0),
	/**
	 * 分析能力
	 */
	ANALYSIS(1),
	/**创造能力*/
	CREATE(2),
	/**
	 * 应用能力
	 */
	APPLY(3),
	/**
	 * 表达能力
	 */
	EXPRESS(4),
	/**
	 * 思维能力
	 */
	THOUGHT(5)	;
	private final int value;
	public int getValue() {
		return value;
	}
	LearnAbilityTypeEnum(int value) {
		this.value = value;
	}
	
	public static LearnAbilityTypeEnum getEnum(int value){
		for (LearnAbilityTypeEnum an : LearnAbilityTypeEnum.values()) {
            if (an.getValue()==value) {
                return an;
            }
        }
		return null;
	}
}
