
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductBuyRecordServiceImpl.java 
	 * 包名:com.eeduspace.cibn.service.impl 
	 * 创建日期:2016年12月19日下午1:40:05 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.model.ProductOrderModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.persist.dao.ProductBuyRecordDao;
import com.eeduspace.cibn.persist.enumeration.BuyTypeEnum;
import com.eeduspace.cibn.persist.enumeration.VipEnum;
import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
import com.eeduspace.cibn.persist.po.VipBuyRecord;
import com.eeduspace.cibn.service.ProductBuyRecordService;
import com.eeduspace.cibn.ws.AlipayNoticeWs;
import com.google.gson.Gson;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductBuyRecordServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 下午1:40:05    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 下午1:40:05    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class ProductBuyRecordServiceImpl implements ProductBuyRecordService {
	private final Logger logger = LoggerFactory.getLogger(AlipayNoticeWs.class);
	private Gson gson=new Gson();
	
	@Inject
	private ProductBuyRecordDao productBuyRecordDao;

	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductBuyRecordService#findByProductUuid(java.lang.String)    
	 */
		 
	@Override
	public ProBuyRecordPo findByProductUuid(String uuid,String userCode,Date timeDate) throws Exception {
		// TODO Auto-generated method stub
		ProBuyRecordPo findByProductUuid = productBuyRecordDao.findByProductUuid(uuid,userCode,1,new Date());
		return findByProductUuid;
			
	}
	
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductBuyRecordService#saveProductRecord(com.eeduspace.cibn.model.VipOrderModel, com.eeduspace.cibn.model.UserModel)    
	 */
			 
	@Override
	public void saveProductRecord(ProductOrderModel productOrderModel) throws Exception {
		// TODO Auto-generated method stub
		ProBuyRecordPo proBuyRecord=new ProBuyRecordPo();
		proBuyRecord.setCreateDate(new Date());
		proBuyRecord.setDeviceType(productOrderModel.getDeviceType());
		proBuyRecord.setOrderSn(productOrderModel.getOrderSn());//VIP购买标识
		proBuyRecord.setMarketChannel(productOrderModel.getMarketChannel()==null?"":productOrderModel.getMarketChannel().substring(1));
		proBuyRecord.setBuyType(BuyTypeEnum.PRODUCT);
		proBuyRecord.setIsPay(0);//未支付状态
		proBuyRecord.setPayType(productOrderModel.getPayType());
		proBuyRecord.setOrderName(productOrderModel.getOrderName());
		proBuyRecord.setUserCode(productOrderModel.getUserCode());
		if (productOrderModel.getExpireTime().equals("12")) {
			proBuyRecord.setProductDays(365);
		}else {
			proBuyRecord.setProductDays(Integer.parseInt(productOrderModel.getExpireTime())*30);
		}
		proBuyRecord.setProductPrice(productOrderModel.getOrderPrice());
		proBuyRecord.setProductType(productOrderModel.getProductOrderType());
		proBuyRecord.setProductUuid(productOrderModel.getProductUuid());
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.MONTH, Integer.parseInt(productOrderModel.getExpireTime()));
		proBuyRecord.setExpireDate(calendar.getTime());
		productBuyRecordDao.save(proBuyRecord);
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int flag =6;
		calendar.add(calendar.MONTH, flag);
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sim.format(calendar.getTime());
		System.out.println(format);
		System.out.println(calendar.getTime());
	}
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductBuyRecordService#findByOrderSn(java.lang.String)    
	 */
		 
	@Override
	public ProBuyRecordPo findByOrderSn(String sn) throws Exception {
		// TODO Auto-generated method stub
		ProBuyRecordPo proOrderPo = productBuyRecordDao.findByOrderSn(sn);	
		return proOrderPo;
			
	}
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductBuyRecordService#save(com.eeduspace.cibn.persist.po.ProBuyRecordPo)    
	 */
			 
	@Override
	public void save(ProBuyRecordPo productBuyRecord) throws Exception {
		// TODO Auto-generated method stub
		productBuyRecordDao.save(productBuyRecord);
	}
	
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductBuyRecordService#findAllByUserCode(java.lang.String, java.util.Date)    
	 */
			 
	@Override
	public List<ProBuyRecordPo> findAllByUserCode(String userCode, Date date) throws Exception {
		// TODO Auto-generated method stub
		List<ProBuyRecordPo> proOrderList = productBuyRecordDao.findAllByUserCode(userCode,1,date);
		return proOrderList;
			
	}
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductBuyRecordService#findAllByUserCodeAndIsPay(java.lang.String, int)    
	 */
		 
	@Override
	public List<ProBuyRecordPo> findAllByUserCodeAndIsPay(String userCode,
			int isPay) throws Exception {
		// TODO Auto-generated method stub
		List<ProBuyRecordPo> findAllByUserCodeAndIsPay = productBuyRecordDao.findAllByUserCodeAndIsPay(userCode, isPay);
		return findAllByUserCodeAndIsPay;
			
	}
	
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductBuyRecordService#findByProductUuidOverdue(java.lang.String, java.lang.String, int, java.util.Date)    
	 */
		 
	@Override
	public List<ProBuyRecordPo> findByProductUuidOverdue(String uuid,
			String UserCode, int isPay, Date timeDate) throws Exception {
		// TODO Auto-generated method stub
		List<ProBuyRecordPo> findByProductUuidOverdue = productBuyRecordDao.findByProductUuidOverdue(UserCode, UserCode, isPay, timeDate);
		return findByProductUuidOverdue;
	}
	
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductBuyRecordService#findByUserCodeAndIsDelAndIsPay(java.lang.String, boolean, boolean, org.springframework.data.domain.Pageable)    
	 */
			 
	@Override
	public Page<ProBuyRecordPo> findByUserCodeAndIsDelAndIsPay(
			String userCode, boolean isDel, int isPay, Pageable pageable)
			throws Exception {
		// TODO Auto-generated method stub
		Page<ProBuyRecordPo> findByUserCodeAndIsDelAndIsPay = productBuyRecordDao.findByUserCodeAndIsDelAndIsPay(userCode, isDel, isPay, pageable);
		return findByUserCodeAndIsDelAndIsPay;
			
	}
}

	