package com.eeduspace.cibn.service;

import com.eeduspace.cibn.persist.po.TempPaperInfo;

public interface TempPaperInfoService {
	TempPaperInfo save(TempPaperInfo info);
	TempPaperInfo findByUUID(String uuid);
	void deleteByUUID(String uuid);
}
