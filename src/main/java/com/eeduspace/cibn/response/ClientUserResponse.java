package com.eeduspace.cibn.response;

import com.eeduspace.cibn.model.UserInfoModel;

/**
 * Author: dingran
 * Date: 2016/4/27
 * Description:
 */
public class ClientUserResponse extends ClientResponse{

    private String logCode;

    private UserInfoModel userInfo;

    public String getLogCode() {
        return logCode;
    }

    public void setLogCode(String logCode) {
        this.logCode = logCode;
    }

    public UserInfoModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoModel userInfo) {
        this.userInfo = userInfo;
    }
}
