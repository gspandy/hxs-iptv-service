
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductService.java 
	 * 包名:com.eeduspace.cibn.service 
	 * 创建日期:2016年12月19日上午10:32:19 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service;

import com.eeduspace.cibn.persist.po.ProductPo;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 上午10:32:19    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 上午10:32:19    
 * 修改备注：       
 * @version </pre>    
 */

public interface ProductService {

	public void save(ProductPo product) throws Exception;
	
	public ProductPo findByUuid(String uuid) throws Exception;
	
	public ProductPo findByCtbCode(String ctbCode) throws Exception;
}

	