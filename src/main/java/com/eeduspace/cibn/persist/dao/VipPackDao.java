package com.eeduspace.cibn.persist.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.VIPPack;

public interface VipPackDao extends CrudRepository<VIPPack, Long>{
	List<VIPPack> findByIsReleaseOrderByVipDaysAsc(Boolean isRelease);
}
