package com.eeduspace.cibn.persist.enumeration;

import com.eeduspace.cibn.persist.dao.ProductBuyRecordDao;
import com.eeduspace.cibn.persist.enumeration.UserEnum.Sex;

public enum BuyTypeEnum {
	 VIP(0),
	 DIAGNOSTIC(1),
	 PRODUCT(2);
     private final int value;

     public int getValue() {
         return value;
     }
     BuyTypeEnum(int value) {
         this.value = value;
     }
}
