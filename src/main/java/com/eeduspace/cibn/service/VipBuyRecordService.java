package com.eeduspace.cibn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.persist.enumeration.BuyTypeEnum;
import com.eeduspace.cibn.persist.po.VipBuyRecord;

/**
 * @author zhuchaowei
 * 2016年4月19日
 * Description vip 购买记录业务
 */
public interface VipBuyRecordService {
	VipBuyRecord save(VipBuyRecord vipBuyRecord);
	/**
	 * 根据人员userCode获取购买记录列表
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月19日 下午5:28:49
	 * @param userCode 人员userCode
	 * @return vip购买记录列表
	 */
	Page<VipBuyRecord> findByUserCodeAndIsDelAndBuyType(String userCode,boolean isDel,boolean isPay,Pageable pageable);
	
	VipBuyRecord update(VipBuyRecord vipBuyRecord);
	/**
	 * 购买会员
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月20日 上午8:52:19
	 * @param vipOrderModel vip订单
	 * @return
	 */
	UserModel saveVipRecord(VipOrderModel vipOrderModel,UserModel userModel);
	/**
	 * 保存诊断报告购买记录
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月22日 下午4:12:07
	 * @param vipOrderModel
	 * @param userModel
	 * @return
	 */
	void saveDiagnosticBuyRecord(VipOrderModel vipOrderModel,UserModel userModel);

	/**
	 * 查询购买记录
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月20日 上午11:29:32
	 * @param id
	 */
	VipBuyRecord findById(Long id);
	/**
	 * 根据UUID获取信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 上午9:24:39
	 * @param uuid
	 * @return
	 */
	VipBuyRecord findByUUID(String uuid);
	/**
	 * 删除购买记录
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 上午9:46:08
	 * @param uuid
	 * @return
	 */
	VipBuyRecord deleteByUUID(String uuid);
	/**
	 * 根据订单流水号获取
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月22日 上午9:03:36
	 * @param orderSn
	 * @return
	 */
	VipBuyRecord findByOrderSn(String orderSn);
	
}
