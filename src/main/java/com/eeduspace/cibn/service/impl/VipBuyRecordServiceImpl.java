package com.eeduspace.cibn.service.impl;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.persist.dao.VipBuyRecordDao;
import com.eeduspace.cibn.persist.enumeration.BuyTypeEnum;
import com.eeduspace.cibn.persist.enumeration.VipEnum;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.persist.po.VipBuyRecord;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.service.VipBuyRecordService;
import com.eeduspace.uuims.comm.util.base.DateUtils;
/**
 * @author zhuchaowei
 * 2016年4月19日
 * Description VIP 购买记录
 */
@Service
public class VipBuyRecordServiceImpl implements VipBuyRecordService{
    private static final Logger logger = LoggerFactory.getLogger(VipBuyRecordServiceImpl.class);
    @Inject
    private UserService userServiceImpl; 
    @Inject
    private VipBuyRecordDao vipBuyRecordDao;

	@Override
	public VipBuyRecord save(VipBuyRecord vipBuyRecord) {
		return vipBuyRecordDao.save(vipBuyRecord);
	}

	@Override
	public Page<VipBuyRecord> findByUserCodeAndIsDelAndBuyType(String userCode,boolean isDel,boolean isPay,Pageable pageable) {
		return vipBuyRecordDao.findByUserCodeAndIsDelAndIsPay(userCode,isDel,isPay,pageable);
	}

	@Override
	public VipBuyRecord update(VipBuyRecord vipBuyRecord) {
		return vipBuyRecordDao.save(vipBuyRecord);
	}

	@Override
	public UserModel saveVipRecord(VipOrderModel vipOrderModel,UserModel userModel) {
		//先找到用户信息，看用户是否是VIP 如果不是 新增一条购买记录并将用户信息更为VIP
		UserPo userPo=userServiceImpl.findByUserCode(vipOrderModel.getUserCode());
		logger.debug("userPo信息---》"+userPo);
		VipBuyRecord vipBuyRecord=new VipBuyRecord();
		vipBuyRecord.setCreateDate(new Date());
		vipBuyRecord.setDeviceType(vipOrderModel.getDeviceType());
		vipBuyRecord.setOrderSN(vipOrderModel.getOrderSn());//VIP购买标识
		vipBuyRecord.setMarketChannel(vipOrderModel.getMarketChannel()==null?"":vipOrderModel.getMarketChannel().substring(1));
		vipBuyRecord.setBuyType(BuyTypeEnum.VIP);
		vipBuyRecord.setPay(false);//未支付状态
		vipBuyRecord.setPayType(vipOrderModel.getPayType());
		vipBuyRecord.setOrderName(vipOrderModel.getOrderName());
		vipBuyRecord.setUserCode(vipOrderModel.getUserCode());
		vipBuyRecord.setVipDays(VipEnum.VipPackTypeEnum.getValue(vipOrderModel.getVipOrderType()));
		vipBuyRecord.setVipPrice(vipOrderModel.getOrderPrice());
		vipBuyRecord.setVipType(vipOrderModel.getVipOrderType());
		this.save(vipBuyRecord);
		UserModel uModel=new UserModel();
		return uModel;
	}

	@Override
	public VipBuyRecord findById(Long id) {
		return vipBuyRecordDao.findOne(id);
	}

	@Override
	public VipBuyRecord findByUUID(String uuid) {
		return vipBuyRecordDao.findByUuid(uuid);
	}

	@Override
	public VipBuyRecord deleteByUUID(String uuid) {
		VipBuyRecord vipBuyRecord=this.findByUUID(uuid);
		vipBuyRecord.setDel(true);
		vipBuyRecord=this.save(vipBuyRecord);
		return vipBuyRecord;
	}

	@Override
	public VipBuyRecord findByOrderSn(String orderSn) {
		return vipBuyRecordDao.findByOrderSN(orderSn);
	}

	@Override
	public void saveDiagnosticBuyRecord(VipOrderModel vipOrderModel,
			UserModel userModel) {
		VipBuyRecord vipBuyRecord=new VipBuyRecord();
		vipBuyRecord.setCreateDate(new Date());
		vipBuyRecord.setDeviceType(vipOrderModel.getDeviceType());
		vipBuyRecord.setMarketChannel(vipOrderModel.getMarketChannel()==null?"":vipOrderModel.getMarketChannel().substring(1));
		vipBuyRecord.setDiagnositcUUID(vipOrderModel.getDiagnosticUUID());
		vipBuyRecord.setOrderName(vipOrderModel.getOrderName());
		vipBuyRecord.setOrderSN(vipOrderModel.getOrderSn());//VIP购买标识
		vipBuyRecord.setBuyType(BuyTypeEnum.DIAGNOSTIC);
		vipBuyRecord.setPay(false);//未支付状态
		vipBuyRecord.setPayType(vipOrderModel.getPayType());
		vipBuyRecord.setUserCode(userModel.getUserCode());
		vipBuyRecord.setVipPrice(vipOrderModel.getOrderPrice());
		this.save(vipBuyRecord);
	}
 
}
