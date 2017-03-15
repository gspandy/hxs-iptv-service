package com.eeduspace.cibn.persist.dao;

import com.eeduspace.cibn.persist.po.TokenPo;
import org.springframework.data.repository.CrudRepository;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
public interface TokenDao extends CrudRepository<TokenPo, Long> {

    TokenPo findByToken(String token);


    TokenPo findByUserCode(String userCode);
}
