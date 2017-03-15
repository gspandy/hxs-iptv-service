
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductBuyRecordService.java 
	 * 包名:com.eeduspace.cibn.service 
	 * 创建日期:2016年12月19日下午1:38:53 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eeduspace.cibn.model.ProductOrderModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
import com.eeduspace.cibn.persist.po.VipBuyRecord;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductBuyRecordService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 下午1:38:53    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 下午1:38:53    
 * 修改备注：       
 * @version </pre>    
 */

public interface ProductBuyRecordService {

	public ProBuyRecordPo findByProductUuid(String uuid,String UserCode,Date timeDate) throws Exception;

	
		/** <pre>saveProductRecord(这里用一句话描述这个方法的作用)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月19日 下午4:11:42    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月19日 下午4:11:42    
		 * 修改备注： 
		 * @param vipOrderModel
		 * @param userModel</pre>    
		 */
		 
	public void saveProductRecord(ProductOrderModel productOrderModel) throws Exception;


		
	/** <pre>findByOrderSn(这里用一句话描述这个方法的作用)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年12月19日 下午4:55:14    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年12月19日 下午4:55:14    
	 * 修改备注： 
	 * @param sn
	 * @return</pre>    
	 */
			 
	public ProBuyRecordPo findByOrderSn(String sn) throws Exception;


	
		/** <pre>save(这里用一句话描述这个方法的作用)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月19日 下午5:02:48    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月19日 下午5:02:48    
		 * 修改备注： 
		 * @param productBuyRecord</pre>    
		 */
		 
	public void save(ProBuyRecordPo productBuyRecord) throws Exception;


		
	/** <pre>findAllByUserCode(这里用一句话描述这个方法的作用)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年12月19日 下午5:41:50    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年12月19日 下午5:41:50    
	 * 修改备注： 
	 * @param userCode
	 * @param date
	 * @return</pre>    
	 */
			 
	public List<ProBuyRecordPo> findAllByUserCode(String userCode, Date date) throws Exception;
	
	public List<ProBuyRecordPo> findAllByUserCodeAndIsPay(String userCode,int isPay) throws Exception;
	
	public List<ProBuyRecordPo> findByProductUuidOverdue(String uuid,String UserCode,int isPay,Date timeDate) throws Exception;
	
	public Page<ProBuyRecordPo> findByUserCodeAndIsDelAndIsPay(String userCode,boolean isDel,int isPay,Pageable pageable) throws Exception;
}

	