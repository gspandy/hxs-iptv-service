
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductServiceImpl.java 
	 * 包名:com.eeduspace.cibn.service.impl 
	 * 创建日期:2016年12月19日上午10:33:26 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.persist.dao.ProductDao;
import com.eeduspace.cibn.persist.po.ProductPo;
import com.eeduspace.cibn.service.ProductService;
import com.eeduspace.cibn.ws.AlipayNoticeWs;
import com.google.gson.Gson;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 上午10:33:26    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 上午10:33:26    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Inject
	private ProductDao productDao;
	
	private final Logger logger = LoggerFactory.getLogger(AlipayNoticeWs.class);
	private Gson gson=new Gson();
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductService#save(com.eeduspace.cibn.persist.po.ProductPo)    
	 */
		 
	@Override
	public void save(ProductPo product) throws Exception {
		// TODO Auto-generated method stub
		productDao.save(product);
	}

	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductService#findByUuid(java.lang.String)    
	 */
		 
	@Override
	public ProductPo findByUuid(String uuid) throws Exception {
		// TODO Auto-generated method stub
		ProductPo findByUuid = productDao.findByUuid(uuid);
			return findByUuid;
			
	}
	
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.ProductService#findByCtbCode(java.lang.String)    
	 */
			 
	@Override
	public ProductPo findByCtbCode(String ctbCode) throws Exception {
		// TODO Auto-generated method stub
		ProductPo findByCtbCode = productDao.findByCtbCode(ctbCode);
		return findByCtbCode;
			
	}
	
}

	