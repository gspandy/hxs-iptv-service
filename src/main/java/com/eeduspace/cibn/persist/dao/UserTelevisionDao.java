package com.eeduspace.cibn.persist.dao;

import com.eeduspace.cibn.persist.po.UserTelevisionPo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
public interface UserTelevisionDao extends CrudRepository<UserTelevisionPo, Long> {

    UserTelevisionPo  findByTelevisionCode(String televisionCode);

    @Query("select ut from UserTelevisionPo ut where ut.userPo.userCode=?1")
    List<UserTelevisionPo> findByUserCode(String userCode);

    @Query("select ut from UserTelevisionPo ut where ut.userPo.userCode=?1 and ut.televisionCode=?2")
    UserTelevisionPo findByUserCodeAndTelevision(String userCode,String television);
}
