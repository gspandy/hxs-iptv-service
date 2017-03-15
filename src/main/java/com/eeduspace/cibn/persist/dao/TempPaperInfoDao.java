package com.eeduspace.cibn.persist.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.TempPaperInfo;

public interface TempPaperInfoDao extends CrudRepository<TempPaperInfo, Long>{
	TempPaperInfo findByUuid(String uuid);
	@Modifying
	@Query(value="DELETE  FROM TempPaperInfo  where uuid =?1")
	void deleteByUUID(String uuid);
}
