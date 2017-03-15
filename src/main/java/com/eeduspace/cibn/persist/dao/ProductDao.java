
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductDao.java 
	 * 包名:com.eeduspace.cibn.persist.dao 
	 * 创建日期:2016年12月19日上午10:28:11 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.persist.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.PaperTypePo;
import com.eeduspace.cibn.persist.po.ProductPo;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductDao    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 上午10:28:11    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 上午10:28:11    
 * 修改备注：       
 * @version </pre>    
 */

public interface ProductDao extends CrudRepository<ProductPo, Long> {

	@Query("select pr from ProductPo pr where pr.uuid=?1")
	ProductPo findByUuid(String uuid);
	
	@Query("select pr from ProductPo pr where pr.ctbCode=?1")
	ProductPo findByCtbCode(String ctbCode);
	
}

	