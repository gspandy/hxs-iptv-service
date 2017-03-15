package com.eeduspace.cibn.service;

import com.eeduspace.cibn.persist.po.UserTelevisionPo;

import java.util.List;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
public interface UserTelevisionService {


    /**
     * 统一时间用户可以多设备登录
     * @param userCode
     * @return
     */
    List<UserTelevisionPo> findByUserCode(String userCode);

    /**
     * 同一个时间设备只能登录一个账户
     * @param televisionCode
     * @return
     */
    UserTelevisionPo findByTelevisionCode(String televisionCode);

    UserTelevisionPo findByUserCodeAndTelevision(String userCode,String televisionCode);
    /**
     * 保存
     * @param userTelevisionPo
     * @return
     */
    UserTelevisionPo save(UserTelevisionPo userTelevisionPo);



    void delete(Long id);

    void delete(UserTelevisionPo userTelevisionPo);
}
