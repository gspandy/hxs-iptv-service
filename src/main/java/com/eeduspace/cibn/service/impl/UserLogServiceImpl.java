package com.eeduspace.cibn.service.impl;

import com.eeduspace.cibn.persist.dao.UserLogDao;
import com.eeduspace.cibn.persist.po.UserLogPo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.service.EventOperationService;
import com.eeduspace.cibn.service.UserLogService;
import com.eeduspace.cibn.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class UserLogServiceImpl implements UserLogService{
    private static final Logger logger = LoggerFactory.getLogger(UserLogServiceImpl.class);

    @Inject
    private UserLogDao userLogDao;
    @Inject
    private EventOperationService eventOperationService;
    @Inject
    private UserService userService;

	@Override
	public Page<UserLogPo> getList(UserPo userPo, Pageable pageable) {
		return userLogDao.findByUserPo( userPo,  pageable);

	}


	@Override
	public void create(UserPo userPo, String action, String module,Boolean result, String sourceIp, String requestId) {
		try {
	         if(userPo!=null){
	             UserLogPo userLogPo=new UserLogPo();
	             userLogPo.setUserPo(userPo);
	             userLogPo.setAction(action);
	             userLogPo.setResult(result);
	             userLogPo.setModule(module);
	             userLogPo.setSourceIp(sourceIp);
	             userLogPo.setRequestId(requestId);
	 	         userLogDao.save(userLogPo);

	            // eventOperationService.createUserLogMessage(userLogPo);
	         }
	     }catch (Exception e){
	         logger.error("create logs error:{}",e);
	     }
	}


	@Override
	public UserLogPo create(Long userId, String action, String module,Boolean result, String sourceIp, String requestId) {
		 UserLogPo userLogPo=new UserLogPo();
	        userLogPo.setUserPo(userService.findOne(userId));
	        userLogPo.setAction(action);
	        userLogPo.setResult(result);
	        userLogPo.setModule(module);
	        userLogPo.setRequestId(requestId);
	        return userLogDao.save(userLogPo);
	}
}
