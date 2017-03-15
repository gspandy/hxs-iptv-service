package com.eeduspace.cibn.service;

import com.eeduspace.cibn.persist.po.TokenPo;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
public interface TokenService {

    /**
     *查找
     * @param token
     * @return
     */
    TokenPo findByToken(String token);

    /**
     * 查找
     * @param userCode
     * @return
     */
    TokenPo findByUserCode(String userCode);

    /**
     * 保存
     * @param tokenPo
     * @return
     */
    TokenPo save(TokenPo tokenPo);

    /**
     *
     * @param id
     */
    void delete(Long id);
}
