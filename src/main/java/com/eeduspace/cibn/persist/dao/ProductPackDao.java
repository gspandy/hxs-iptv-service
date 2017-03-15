
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductPackDao.java 
	 * 包名:com.eeduspace.cibn.persist.dao 
	 * 创建日期:2016年12月20日上午11:32:41 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.persist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.ProductPackPo;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductPackDao    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月20日 上午11:32:41    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月20日 上午11:32:41    
 * 修改备注：       
 * @version </pre>    
 */

public interface ProductPackDao extends CrudRepository<ProductPackPo,Long>{

	@Query("select pp from ProductPackPo pp where pp.priceWay=?1")
	List<ProductPackPo> findByPriceWay(String priceWay);
}

	