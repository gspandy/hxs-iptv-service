package com.eeduspace.cibn.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.AppUpdateModel;
import com.eeduspace.cibn.persist.dao.AppUpdatePoDao;
import com.eeduspace.cibn.persist.po.AppUpdatePo;
import com.eeduspace.cibn.service.AppUpdateService;

@Service
public class AppUpdateServiceImpl implements AppUpdateService {
	
	@Inject
	private AppUpdatePoDao appUpdatePoDao;

	@Override
	public AppUpdateModel getAppUpdateModel() {
		AppUpdatePo aup = appUpdatePoDao.findByAvailable(true);
		return CIBNConvert.toAppUpdateModel(aup);
	}

}
