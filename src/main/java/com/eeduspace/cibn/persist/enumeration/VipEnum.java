package com.eeduspace.cibn.persist.enumeration;

public class VipEnum{
private VipPackTypeEnum vipPackTypeEnum;	
public enum VipPackTypeEnum {
	 ONE_MONTH(30),
	 TWO_MONTH(60),
	 THREE_MONTH(90),
	 FOUR_MONTH(120),
	 FIVE_MONTH(150),
	 SIX_MONTH(180),
	 SEVEN_MONTH(210),
	 EIGHT_MONTH(240),
	 NINE_MONTH(270),
	 TEN_MONTH(300),
	 ELEVEN_MONTH(330),
	 ONE_YEAR(365);
     private final int value;

     public int getValue() {
         return value;
     }
     VipPackTypeEnum(int value) {
         this.value = value;
     }
     
     public static int getValue(String en){
    	 for (VipPackTypeEnum type : VipPackTypeEnum.values()) {
			if(type.toString().equals(en)){
				return type.getValue(); 
			}
		}
    	 return 0;
     }
}
public VipPackTypeEnum getVipPackTypeEnum() {
	return vipPackTypeEnum;
}
public void setVipPackTypeEnum(VipPackTypeEnum vipPackTypeEnum) {
	this.vipPackTypeEnum = vipPackTypeEnum;
}
}
