
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:PaperTypeService.java 
	 * 包名:com.eeduspace.cibn.service 
	 * 创建日期:2016年12月12日下午8:17:46 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service;

import com.eeduspace.cibn.persist.po.PaperTypePo;

	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：PaperTypeService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月12日 下午8:17:46    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月12日 下午8:17:46    
 * 修改备注：       
 * @version </pre>    
 */

public interface PaperTypeService {
	PaperTypePo findByName(String name);
}

	