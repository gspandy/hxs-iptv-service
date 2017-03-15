
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:ProductController.java 
	 * 包名:com.eeduspace.cibn.controller 
	 * 创建日期:2016年12月19日下午2:08:48 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.ProductOrderModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
import com.eeduspace.cibn.persist.po.ProductPackPo;
import com.eeduspace.cibn.persist.po.ProductPo;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.ProductBuyRecordService;
import com.eeduspace.cibn.service.ProductPackService;
import com.eeduspace.cibn.service.ProductService;
import com.eeduspace.cibn.util.DataMap;
import com.eeduspace.cibn.ws.AlipayNoticeWs;
import com.google.gson.Gson;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：ProductController    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月19日 下午2:08:48    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月19日 下午2:08:48    
 * 修改备注：       
 * @version </pre>    
 */
@Controller
@RequestMapping("/productController")
public class ProductController {
	private final Logger logger = LoggerFactory.getLogger(AlipayNoticeWs.class);
	private Gson gson=new Gson();
	
	@Inject
	private ProductService productService;
	@Inject
	private ProductBuyRecordService productBuyRecordService;
	@Inject
	private ProductPackService productPackService;
	
	/*@RequestMapping("/selectProductStatusByCtbCode")
	@ResponseBody
	public BaseResponse selectProductStatusByCtbCode(HttpServletRequest request,HttpServletResponse response,@RequestBody ProductOrderModel productOrderModel,DataMap dataMap) {
		logger.info("/productController/selectProductStatusByCtbCode," + "dataMap====" + gson.toJson(dataMap));
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");
		BaseResponse baseResponse = new BaseResponse();
		Map<String,String> map = dataMap.getMap(request);
		if (null != map && !map.isEmpty()) {
			String ctbCode = map.get("ctbCode");
			String userCode = map.get("userCode");
			if (StringUtils.isBlank(ctbCode)) {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".ctbCode");
			}
			if (StringUtils.isBlank(userCode)) {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".userCode");
			}
			try {
				ProductPo findByCtbCode = productService.findByCtbCode(ctbCode);
				String uuid = findByCtbCode.getUuid();
				String productPackType = findByCtbCode.getProductPackType();
				ProBuyRecordPo findByProductUuid = productBuyRecordService.findByProductUuid(uuid,userCode,new Date());
				if (null != findByProductUuid) {
					logger.info("findByProductUuid===" + gson.toJson(findByProductUuid));
					baseResponse.setMessage("该用户已购买过产品，未到期");
					baseResponse.setCode("0000");
					baseResponse.setResult(productPackType);
				} else {
					List<ProBuyRecordPo> findByProductUuidOverdue = productBuyRecordService.findByProductUuidOverdue(uuid, userCode, 1, new Date());
					if (null != findByProductUuidOverdue && 0 < findByProductUuidOverdue.size()) {
						baseResponse.setMessage("该用户购买过产品，已到期");
						baseResponse.setCode("2222");
					} else {
						baseResponse.setMessage("该用户未购买过产品");
						baseResponse.setCode("1111");
					}
				}
			} catch (Exception e) {
				baseResponse.setHttpCode("500");
				baseResponse.setMessage("The request service process error.");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".map");
		}
		return baseResponse;
	}*/
	
	@RequestMapping("/selectProductStatusByCtbCode")
	@ResponseBody
	public BaseResponse selectProductStatusByCtbCode(HttpServletRequest request,HttpServletResponse response,@RequestBody ProductOrderModel productOrderModel) {
		logger.info("/productController/selectProductStatusByCtbCode," + "dataMap====" + gson.toJson(productOrderModel));
		/*response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");*/
		BaseResponse baseResponse = new BaseResponse();
		String ctbCode = productOrderModel.getCtbCode();
		String userCode = productOrderModel.getUserCode();
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(ctbCode)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".ctbCode");
		}
		if (StringUtils.isBlank(userCode)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".userCode");
		}
		try {
			ProductPo findByCtbCode = productService.findByCtbCode(ctbCode);
			String uuid = findByCtbCode.getUuid();
			String productOrderType = findByCtbCode.getProductPackType();
			ProBuyRecordPo findByProductUuid = productBuyRecordService.findByProductUuid(uuid,userCode,new Date());
			if (null != findByProductUuid) {
				logger.info("findByProductUuid===" + gson.toJson(findByProductUuid));
				map.put("productOrderType", productOrderType);
				baseResponse.setMessage("该用户已购买过产品，未到期");
				baseResponse.setCode("0000");
				baseResponse.setResult(map);
			} else {
				List<ProBuyRecordPo> findByProductUuidOverdue = productBuyRecordService.findByProductUuidOverdue(uuid, userCode, 1, new Date());
				if (null != findByProductUuidOverdue && 0 < findByProductUuidOverdue.size()) {
					map.put("productOrderType", productOrderType);
					baseResponse.setMessage("该用户购买过产品，已到期");
					baseResponse.setCode("2222");
					baseResponse.setResult(map);
				} else {
					map.put("productOrderType", productOrderType);
					baseResponse.setMessage("该用户未购买过产品");
					baseResponse.setCode("1111");
					baseResponse.setResult(map);
				}
			}
		} catch (Exception e) {
			baseResponse.setHttpCode("500");
			baseResponse.setMessage("The request service process error.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseResponse;
	}
	/**
	 * <pre>selectProductOrderList(not expire)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月20日 上午11:48:52    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月20日 上午11:48:52    
		 * 修改备注： 
		 * @param request
		 * @param response
		 * @param dataMap
		 * @return</pre>
	 */
	@RequestMapping("/selectProductOrderList")
	@ResponseBody
	public BaseResponse selectProductOrderList(HttpServletRequest request,HttpServletResponse response,@RequestBody ProductOrderModel productOrderModel) {
		logger.info("/productController/selectProductOrderList," + "dataMap====" + gson.toJson(productOrderModel));
		BaseResponse baseResponse = new BaseResponse();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日");
			String userCode = productOrderModel.getUserCode();
			if (StringUtils.isBlank(userCode)) {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".userCode");
			}
			try {
				List<ProBuyRecordPo> proOrderList = productBuyRecordService.findAllByUserCode(userCode,new Date());
				List<Map<String, String>> proOrderModelList = new ArrayList<Map<String, String>>();
				
				if (null != proOrderList && 0 < proOrderList.size()) {
					for (ProBuyRecordPo proModel : proOrderList) {
						Map<String, String> productMap = new HashMap<String, String>();
						String productUuid = proModel.getProductUuid();
						ProductPo findByUuid = productService.findByUuid(productUuid);
						productMap.put("stageName", findByUuid.getStageName());
						productMap.put("gradeName", findByUuid.getGradeName());
						productMap.put("subjectName", findByUuid.getSubjectName());
						productMap.put("ctbName", findByUuid.getCtbName());
						productMap.put("ctbCode", findByUuid.getCtbCode());
						productMap.put("bookTypeName", findByUuid.getBookTypeName());
						productMap.put("bookTypeCode", findByUuid.getBookTypeCode());
						productMap.put("expireTime", "有效期至" + sim.format(proModel.getExpireDate()));
						proOrderModelList.add(productMap);
					}
					if (null != proOrderModelList) {
						logger.info("findByProductUuid===" + gson.toJson(proOrderModelList));
					}
					baseResponse.setMessage("success");
					baseResponse.setResult(proOrderModelList);
				}
			} catch (Exception e) {
				baseResponse.setMessage("error");
				baseResponse.setCode("500");
				// TODO Auto-generated catch block
				e.printStackTrace();
					
			}
		return baseResponse;
	}
	/*@RequestMapping("/selectAllProductOrderList/{userCode}/{pageNumber}/{size}")
	@ResponseBody
	public BaseResponse selectAllProductOrderList(HttpServletRequest request,HttpServletResponse response,@PathParam("userCode") String userCode,@PathParam("pageNumber") Integer pageNumber,@PathParam("size") Integer size) {
		logger.info("/productController/selectAllProductOrderList," + "dataMap====" + userCode);
		BaseResponse baseResponse = new BaseResponse();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			//String userCode = productOrderModel.getUserCode();
			if (StringUtils.isBlank(userCode)) {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".userCode");
			}
			try {
				Pageable p=new PageRequest(pageNumber, size);
				//List<ProBuyRecordPo> proOrderList = productBuyRecordService.findAllByUserCodeAndIsPay(userCode,1);
				Page<ProBuyRecordPo> pageModel = productBuyRecordService.findByUserCodeAndIsDelAndIsPay(userCode, false, 1, p);
				List<ProBuyRecordPo> proOrderList = pageModel.getContent();
				List<ProductOrderModel> proOrderModelList = new ArrayList<ProductOrderModel>();
				if (null != proOrderList) {
					logger.info("findByProductUuid===" + gson.toJson(proOrderList));
					for (ProBuyRecordPo proBuyRecordPo : proOrderList) {
						ProductOrderModel productOrderModel = CIBNConvert.productBuyRecordProductOrderModel(proBuyRecordPo);
						proOrderModelList.add(productOrderModel);
					}
				}
				Page<ProductOrderModel> newPag=new PageImpl<ProductOrderModel>(proOrderModelList, p, Long.valueOf(pageModel.getTotalElements()));
				baseResponse.setMessage("success");
				baseResponse.setResult(newPag);
			} catch (Exception e) {
				baseResponse.setMessage("error");
				baseResponse.setCode("500");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return baseResponse;
	}*/
	@RequestMapping("/selectProductPrice")
	@ResponseBody
	public BaseResponse selectProductPrice(HttpServletRequest request,HttpServletResponse response,@RequestBody ProductOrderModel productOrderModel) {
		logger.info("/productController/selectProductPrice," + "dataMap====" + gson.toJson(productOrderModel));
		BaseResponse baseResponse = new BaseResponse();
			String priceWay = productOrderModel.getProductOrderType();
			if (StringUtils.isBlank(priceWay)) {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".priceWay");
			}
			try {
				List<ProductPackPo> findByPriceWay = productPackService.findByPriceWay(priceWay);
				if (null != findByPriceWay) {
					logger.info("findByProductUuid===" + gson.toJson(findByPriceWay));
				}
				baseResponse.setMessage("success");
				baseResponse.setResult(findByPriceWay);
			} catch (Exception e) {
				baseResponse.setHttpCode("500");
				baseResponse.setMessage("The request service process error.");
				// TODO Auto-generated catch block
				e.printStackTrace();
					
			}
		return baseResponse;
	}
}

	