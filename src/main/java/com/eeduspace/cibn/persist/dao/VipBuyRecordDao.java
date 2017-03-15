package com.eeduspace.cibn.persist.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.VipBuyRecord;

public interface VipBuyRecordDao extends CrudRepository<VipBuyRecord, Long>{
		Page<VipBuyRecord> findByUserCodeAndIsDelAndIsPay(String userCode,boolean isDel,boolean isPay,Pageable pageable);
		VipBuyRecord findByUuid(String uuid);
		VipBuyRecord findByOrderSN(String orderSn);
}
