package com.eeduspace.iwrong.dao;

import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.iwrong.po.UserInfoPo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInfoDao extends CrudRepository<UserPo, Long> {

   // UserInfoPo findByCode(String code);

    UserInfoPo findByMobile(String mobile);

    UserInfoPo findByEmail(String email);

    UserInfoPo findByMobileOrEmail(String mobile, String email);

    //登录,根据手机号,邮箱,用户名查找用户
    List<UserInfoPo> findByMobileOrEmailOrUserName(String mobile, String email, String userName);

    UserInfoPo findByUserName(String userName);

    List<UserInfoPo> findByUserNameNot(String username);

   // List<UserInfoPo> findByRegisterSource(int i);
}
