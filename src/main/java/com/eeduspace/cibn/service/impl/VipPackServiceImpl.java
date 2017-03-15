package com.eeduspace.cibn.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.persist.dao.VipPackDao;
import com.eeduspace.cibn.persist.po.VIPPack;
import com.eeduspace.cibn.service.VipPackService;
@Service
public class VipPackServiceImpl implements VipPackService{
	@Inject
	private VipPackDao vipPackDao;
	@Override
	public List<VIPPack> findAll() {
		return  vipPackDao.findByIsReleaseOrderByVipDaysAsc(true);
	}

}
