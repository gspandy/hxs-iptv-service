package com.eeduspace.cibn.persist.dao;

import com.eeduspace.cibn.persist.po.UserLogPo;
import com.eeduspace.cibn.persist.po.UserPo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface UserLogDao extends CrudRepository<UserLogPo, Long> {
	
	Page<UserLogPo> findByUserPo(UserPo userPo, Pageable pageable);
	
	
//	UserLogPo findByUserPo(UserPo userPo);
}
