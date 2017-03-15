package com.eeduspace.cibn.model;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
public class LoginModel extends BaseModel {

    private String userCode;
    private String televisionCode;
    private String accessKey;

    private String password;
    private String userName;


    private String loginDevice;    //登录设备
    private String loginSystem;    //登录使用的操作系统
    private String ip;             //登录ip

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTelevisionCode() {
        return televisionCode;
    }

    public void setTelevisionCode(String televisionCode) {
        this.televisionCode = televisionCode;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
    }

    public String getLoginSystem() {
        return loginSystem;
    }

    public void setLoginSystem(String loginSystem) {
        this.loginSystem = loginSystem;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
