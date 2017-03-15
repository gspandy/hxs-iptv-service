package com.eeduspace.cibn.persist.dao;

import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.AppUpdatePo;

public interface AppUpdatePoDao extends CrudRepository<AppUpdatePo, Long> {
	
	AppUpdatePo findByAvailable(Boolean available);
}
