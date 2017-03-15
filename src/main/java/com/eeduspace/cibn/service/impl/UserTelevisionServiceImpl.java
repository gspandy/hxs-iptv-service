package com.eeduspace.cibn.service.impl;

import com.eeduspace.cibn.persist.dao.UserTelevisionDao;
import com.eeduspace.cibn.persist.po.UserTelevisionPo;
import com.eeduspace.cibn.service.UserTelevisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
@Service
public class UserTelevisionServiceImpl implements UserTelevisionService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserTelevisionDao userTelevisionDao;

    @Override
    public List<UserTelevisionPo> findByUserCode(String userCode) {
        return userTelevisionDao.findByUserCode(userCode);
    }

    @Override
    public UserTelevisionPo findByTelevisionCode(String televisionCode) {
        return userTelevisionDao.findByTelevisionCode(televisionCode);
    }

    @Override
    public UserTelevisionPo findByUserCodeAndTelevision(String userCode, String televisionCode) {
        return userTelevisionDao.findByUserCodeAndTelevision(userCode,televisionCode);
    }

    @Override
    public UserTelevisionPo save(UserTelevisionPo userTelevisionPo) {
        return userTelevisionDao.save(userTelevisionPo);
    }

    @Override
    public void delete(Long id) {
        userTelevisionDao.delete(id);
    }

    @Override
    public void delete(UserTelevisionPo userTelevisionPo) {
        userTelevisionDao.delete(userTelevisionPo);
    }
}
