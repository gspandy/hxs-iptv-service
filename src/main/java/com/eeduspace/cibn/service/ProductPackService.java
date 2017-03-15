
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductPackService.java 
	 * 包名:com.eeduspace.cibn.service 
	 * 创建日期:2016年12月20日上午11:35:28 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service;

import java.util.List;

import com.eeduspace.cibn.persist.po.ProductPackPo;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductPackService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月20日 上午11:35:28    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月20日 上午11:35:28    
 * 修改备注：       
 * @version </pre>    
 */

public interface ProductPackService {
	
	public List<ProductPackPo> findByPriceWay(String priceWay) throws Exception;
}

	