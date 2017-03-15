
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductPackServiceImpl.java 
	 * 包名:com.eeduspace.cibn.service.impl 
	 * 创建日期:2016年12月20日上午11:36:20 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.persist.dao.ProductPackDao;
import com.eeduspace.cibn.persist.po.ProductPackPo;
import com.eeduspace.cibn.service.ProductPackService;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductPackServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月20日 上午11:36:20    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月20日 上午11:36:20    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class ProductPackServiceImpl implements ProductPackService {

	@Inject
	private ProductPackDao productPackDao;
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductPackService#findByPriceWay(java.lang.String)    
	 */
		 
	@Override
	public List<ProductPackPo> findByPriceWay(String priceWay) throws Exception {
		// TODO Auto-generated method stub
		List<ProductPackPo> findByPriceWay = productPackDao.findByPriceWay(priceWay);
		return findByPriceWay;
			
	}
}

	