
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductTest.java 
	 * 包名:com.eeduspace.cibn.product 
	 * 创建日期:2016年12月19日上午10:31:07 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.product;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
import com.eeduspace.cibn.persist.po.ProductPo;
import com.eeduspace.cibn.service.ProductBuyRecordService;
import com.eeduspace.cibn.service.ProductService;
import com.google.gson.Gson;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductTest    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 上午10:31:07    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 上午10:31:07    
 * 修改备注：       
 * @version </pre>    
 */

public class ProductTest extends BaseTest {

	private Gson gson = new Gson();
	@Inject
	private ProductService productService;
	@Inject
	private ProductBuyRecordService productBuyRecordService;
	
	@Test
	public void saveMes() {
		ProductPo productPo = new ProductPo();
		productPo.setBookTypeCode("4a4dcfb584db4e01bac1cb9fde36d11d");
		productPo.setBookTypeName("人教版");
		productPo.setStageCode("3");
		productPo.setStageName("高中");
		productPo.setGradeCode("31");
		productPo.setGradeName("高二");
		productPo.setSubjectCode("5");
		productPo.setSubjectName("化学");
		productPo.setCtbCode("5431");
		productPo.setCtbName("选修五");
		productPo.setModifyDate(new Date());
		productPo.setProductPackType("0");
		try {
			//ProductPo findByUuid = productService.findByUuid("ter");
			productService.save(productPo);
			System.out.println("================================ok");
		} catch (Exception e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		}
	}
	
	@Test
	public void selectProductByCtbCode() {
		try {
			ProductPo findByCtbCode = productService.findByCtbCode("32");
			String uuid = findByCtbCode.getUuid();
			String userCode = "ca866a7d5aae42e5bc0eff8f67072062";
			ProBuyRecordPo findByProductUuid = productBuyRecordService.findByProductUuid(uuid,userCode,new Date());
			System.out.println(gson.toJson(findByProductUuid));
		} catch (Exception e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		}
	}
	
}

	