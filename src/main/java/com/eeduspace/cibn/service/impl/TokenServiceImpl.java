package com.eeduspace.cibn.service.impl;

import com.eeduspace.cibn.persist.dao.TokenDao;
import com.eeduspace.cibn.persist.po.TokenPo;
import com.eeduspace.cibn.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Inject
    private TokenDao tokenDao;

    @Override
    public TokenPo findByToken(String token) {
        return tokenDao.findByToken(token);
    }

    @Override
    public TokenPo findByUserCode(String userCode) {
        return tokenDao.findByUserCode(userCode);
    }

    @Override
    public TokenPo save(TokenPo tokenPo) {
        return tokenDao.save(tokenPo);
    }

    @Override
    public void delete(Long id) {
        tokenDao.delete(id);
    }
}
