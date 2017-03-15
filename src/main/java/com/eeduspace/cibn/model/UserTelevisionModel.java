package com.eeduspace.cibn.model;

import com.eeduspace.cibn.persist.enumeration.UserEnum;

/**
 * Author: dingran
 * Date: 2016/4/28
 * Description:
 */
public class UserTelevisionModel {

    private String userCode;
    private UserEnum.ScanStatus scanStatus;
    private String televisionCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public UserEnum.ScanStatus getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(UserEnum.ScanStatus scanStatus) {
        this.scanStatus = scanStatus;
    }

    public String getTelevisionCode() {
        return televisionCode;
    }

    public void setTelevisionCode(String televisionCode) {
        this.televisionCode = televisionCode;
    }
}
