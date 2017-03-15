package com.eeduspace.cibn.model;

/**
 * Author: dingran
 * Date: 2016/5/11
 * Description:
 */
public class VerifyCodeModel {
    private String mobile;
    private String type;
    private String code;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
