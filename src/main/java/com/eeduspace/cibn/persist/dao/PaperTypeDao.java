
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:PaperTypeDao.java 
	 * 包名:com.eeduspace.cibn.persist.dao 
	 * 创建日期:2016年12月12日下午8:03:08 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.persist.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.PaperTypePo;
import com.eeduspace.cibn.persist.po.UserTelevisionPo;

	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：PaperTypeDao    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月12日 下午8:03:08    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月12日 下午8:03:08    
 * 修改备注：       
 * @version </pre>    
 */

public interface PaperTypeDao extends CrudRepository<PaperTypePo, Long> {
	@Query("select pt from PaperTypePo pt where pt.name=?1")
	PaperTypePo findByName(String name);

}

	