package com.eeduspace.cibn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.event.UserLogEvent;
import com.eeduspace.cibn.event.UserLoginEvent;
import com.eeduspace.cibn.persist.po.UserLogPo;
import com.eeduspace.cibn.service.EventOperationService;
import com.eeduspace.uuims.oauth.model.UserModel;

/**
 * Author: dingran
 * Date: 2016/3/29
 * Description:
 */

@Component
public class EventOperationServiceImpl implements EventOperationService{
    @Autowired
    private ApplicationContext applicationContext;

    @Async
    public void createUserLogMessage(UserLogPo userLogPo) {
        applicationContext.publishEvent(new UserLogEvent(userLogPo));
    }

    @Async
    public void userLoginMessage(UserModel userModel) {
        applicationContext.publishEvent(new UserLoginEvent(userModel));
    }
}
