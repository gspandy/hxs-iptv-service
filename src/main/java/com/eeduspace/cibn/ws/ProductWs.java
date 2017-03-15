
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductWs.java 
	 * 包名:com.eeduspace.cibn.ws 
	 * 创建日期:2016年12月19日下午2:04:34 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.ws;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.service.ProductBuyRecordService;
import com.eeduspace.cibn.service.ProductService;
import com.google.gson.Gson;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductWs    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 下午2:04:34    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 下午2:04:34    
 * 修改备注：       
 * @version </pre>    
 */
@Component
@Path(value = "/productWs")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class ProductWs {
	private final Logger logger = LoggerFactory.getLogger(AlipayNoticeWs.class);
	private Gson gson=new Gson();
	
	@Inject
	private ProductService productService;
	@Inject
	private ProductBuyRecordService productBuyRecordService;
	
}

	