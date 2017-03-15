package com.eeduspace.cibn.service;

import com.eeduspace.cibn.persist.po.UserLogPo;
import com.eeduspace.uuims.oauth.model.UserModel;

/**
 * Author: dingran
 * Date: 2016/3/29
 * Description:
 */
public interface EventOperationService {

    public void createUserLogMessage(UserLogPo userLogPo);

    public void userLoginMessage(UserModel userModel);
}
