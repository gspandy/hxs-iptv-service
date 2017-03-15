
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductBuyRecordDao.java 
	 * 包名:com.eeduspace.cibn.persist.dao 
	 * 创建日期:2016年12月19日下午1:35:49 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.persist.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductBuyRecordDao    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 下午1:35:49    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 下午1:35:49    
 * 修改备注：       
 * @version </pre>    
 */

public interface ProductBuyRecordDao extends CrudRepository<ProBuyRecordPo, Long> {
	/**
	 * <pre>findByProductUuid(通过商品uuid、用户code、已支付、未过期查询订单)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月21日 上午9:26:38    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月21日 上午9:26:38    
		 * 修改备注： 
		 * @param uuid
		 * @param UserCode
		 * @param isPay
		 * @param timeDate
		 * @return</pre>
	 */
	@Query("select pr from ProBuyRecordPo pr where pr.productUuid=?1 and pr.userCode=?2 and pr.isPay =?3 and  pr.expireDate > ?4")
	ProBuyRecordPo findByProductUuid(String uuid,String UserCode,int isPay,Date timeDate);
	/**
	 * <pre>findByProductUuid(通过商品uuid、用户code、已支付、未过期查询订单)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年12月21日 上午9:26:38    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年12月21日 上午9:26:38    
	 * 修改备注： 
	 * @param uuid
	 * @param UserCode
	 * @param isPay
	 * @param timeDate
	 * @return</pre>
	 */
	@Query("select pr from ProBuyRecordPo pr where pr.productUuid=?1 and pr.userCode=?2 and pr.isPay =?3 and  pr.expireDate < ?4")
	List<ProBuyRecordPo> findByProductUuidOverdue(String uuid,String UserCode,int isPay,Date timeDate);

	/** <pre>findByOrderSn(通过订单编号查询)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年12月19日 下午4:58:33    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年12月19日 下午4:58:33    
	 * 修改备注： 
	 * @param sn
	 * @return</pre>    
	 */
	@Query("select pr from ProBuyRecordPo pr where pr.orderSn=?1")	 
	ProBuyRecordPo findByOrderSn(String sn);

	
		
	/** <pre>findAllByUserCode(由用户code、已支付、未过期查询)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年12月19日 下午5:54:06    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年12月19日 下午5:54:06    
	 * 修改备注： 
	 * @param userCode
	 * @param date
	 * @return</pre>    
	 */
	@Query("select pr from ProBuyRecordPo pr where pr.userCode=?1 and pr.isPay=?2 and pr.expireDate > ?3")		 
	List<ProBuyRecordPo> findAllByUserCode(String userCode,int isPay, Date date);
	
	@Query("select pr from ProBuyRecordPo pr where pr.userCode=?1 and pr.isPay=?2")		 
	List<ProBuyRecordPo> findAllByUserCodeAndIsPay(String userCode,int isPay);
	
	Page<ProBuyRecordPo> findByUserCodeAndIsDelAndIsPay(String userCode,boolean isDel,int isPay,Pageable pageable);
}

	