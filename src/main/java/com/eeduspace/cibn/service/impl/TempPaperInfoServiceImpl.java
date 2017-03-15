package com.eeduspace.cibn.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.persist.dao.TempPaperInfoDao;
import com.eeduspace.cibn.persist.po.TempPaperInfo;
import com.eeduspace.cibn.service.TempPaperInfoService;
@Service
public class TempPaperInfoServiceImpl implements TempPaperInfoService{
	@Inject
	private TempPaperInfoDao tempPaperInfoDao;
	
	@Override
	public TempPaperInfo save(TempPaperInfo info) {
		return tempPaperInfoDao.save(info);
	}

	@Override
	public TempPaperInfo findByUUID(String uuid) {
		return tempPaperInfoDao.findByUuid(uuid);
	}

	@Override
	public void deleteByUUID(String uuid) {
		tempPaperInfoDao.deleteByUUID(uuid);
	}

}
