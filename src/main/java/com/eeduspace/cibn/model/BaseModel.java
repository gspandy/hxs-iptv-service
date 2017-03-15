package com.eeduspace.cibn.model;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
public class BaseModel {

    private String remoteAddress;//来源地址:用户登录的浏览器或者app的唯一标识
    private String equipmentType;//来源设备

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }
}
