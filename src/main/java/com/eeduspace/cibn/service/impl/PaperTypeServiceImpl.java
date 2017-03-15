
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:PaperTypeServiceImpl.java 
	 * 包名:com.eeduspace.cibn.service.impl 
	 * 创建日期:2016年12月12日下午8:18:17 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service.impl;

import java.text.DecimalFormat;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.persist.dao.PaperTypeDao;
import com.eeduspace.cibn.persist.po.PaperTypePo;
import com.eeduspace.cibn.service.PaperTypeService;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：PaperTypeServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月12日 下午8:18:17    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月12日 下午8:18:17    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class PaperTypeServiceImpl implements PaperTypeService {

	@Inject
	private PaperTypeDao paperTypeDao;
		
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.PaperTypeService#findByName(java.lang.String)    
	 */
			 
	@Override
	public PaperTypePo findByName(String name){
		// TODO Auto-generated method stub
		PaperTypePo paperType = paperTypeDao.findByName(name);
		String prices = paperType.getPrice();
		double price = Double.parseDouble(prices);
		double discount = paperType.getDiscount();
		double a = price*discount;
		DecimalFormat df = new DecimalFormat("######0.00");
		String format = df.format(a);
		paperType.setPrice(format);
		return paperType;
			
	}
}

	