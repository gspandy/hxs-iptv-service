package com.eeduspace.cibn.persist.enumeration;

/**
 * 服务器状态枚举类
 * @author huyanguang
 *
 */
public enum ServerEnum {

		FIRST("one");
		/**
		 * 根据name从数据库中查询对应的Value值
		 */
	  	private final String name;
	
	     public String getName() {
	         return name;
	     }
	     ServerEnum(String name) {
	         this.name = name;
	     }
}
