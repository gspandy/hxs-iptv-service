
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:RankMessageServiceImpl.java 
	 * 包名:com.eeduspace.cibn.service.impl 
	 * 创建日期:2016年11月22日下午1:18:34 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.ws.addressing.MAPAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.client.impl.BaseDataClientImpl;
import com.eeduspace.cibn.service.RankMessageService;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：RankMessageServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年11月22日 下午1:18:34    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年11月22日 下午1:18:34    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class RankMessageServiceImpl implements RankMessageService {
	private final Logger logger = LoggerFactory.getLogger(BaseDataClientImpl.class);
	private Gson gson = new Gson();
	@Value("${hxs.baseresources.stage}")
	private String hxsStageUrl;
	@Value("${hxs.baseresources.grade.xiaoxue}")
	private String hxsGradeXiaoUrl;
	@Value("${hxs.baseresources.grade.chuzhong}")
	private String hxsGradeChuUrl;
	@Value("${hxs.baseresources.subject.xiaoxue}")
	private String hxsSubjectXiaoUrl;
	@Value("${hxs.baseresources.subject.chuzhong}")
	private String hxsSubjectChuUrl;
	@Value("${hxs.baseresources.nationalTop.xiaoxue}")
	private String hxsNationalTopXiaoUrl;
	@Value("${hxs.baseresources.nationalTop.chuzhong}")
	private String hxsNationalTopChuUrl;
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.RankMessageService#getStage()    
	 */
	 
	@Override
	public List<Map<String, Object>> getStage() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> fromJson2 = new ArrayList<Map<String,Object>>();
		logger.info("requestUrl=" + hxsStageUrl);
		String httpPostRequestJson = HTTPClientUtils.httpPostRequestJson(hxsStageUrl, "");
		logger.info("response=" + httpPostRequestJson);
		if (StringUtils.isNotBlank(httpPostRequestJson)) {
			Map<String,Object> fromJson = gson.fromJson(httpPostRequestJson, new TypeToken<Map<String,Object>> () {}.getType());
			Object result = fromJson.get("result");
			if (null != result) {
				String json = gson.toJson(result);
				fromJson2 = gson.fromJson(json, new TypeToken<List<Map<String,Object>>> () {}.getType());
			}
		}
			return fromJson2;
	}
	
	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.RankMessageService#getGrade(java.lang.Object)    
	 */
	@Override
	public List<Map<String, Object>> getGrade(String stageCode)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String, String>();
		List<Map<String, Object>> list = null;
		if (null != stageCode && 0 < stageCode.length()) {
			if (stageCode.equals("1")) {
				map.put("stageCode", stageCode);
				logger.info("requestUrl=" + hxsGradeXiaoUrl);
				String postResponse1 = HTTPClientUtils.httpPostRequestJson(hxsGradeXiaoUrl, gson.toJson(map));
				logger.info("response=" + postResponse1);
				if (StringUtils.isNotBlank(postResponse1)) {
					Map<String, Object> fromJson = gson.fromJson(postResponse1, new TypeToken<Map<String, Object>>() {}.getType());
					Object object = fromJson.get("result");
					if (null != object) {
						String str = gson.toJson(object);
						list = gson.fromJson(str, new TypeToken<List<Map<String, Object>>>() {}.getType());
					}
				}
			}
			if (stageCode.equals("2")) {
				map.put("stageCode", stageCode);
				logger.info("requestUrl=" + hxsGradeChuUrl);
				String postResponse2 = HTTPClientUtils.httpPostRequestJson(hxsGradeChuUrl, gson.toJson(map));
				logger.info("response=" + postResponse2);
				if (StringUtils.isNotBlank(postResponse2)) {
					Map<String, Object> fromJson2 = gson.fromJson(postResponse2, new TypeToken<Map<String, Object>>() {}.getType());
					Object object2 = fromJson2.get("result");
					if (null != object2) {
						String str = gson.toJson(object2);
						list = gson.fromJson(str, new TypeToken<List<Map<String, Object>>>() {}.getType());
					}
				}
			}
		}
		return list;
	}


	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.RankMessageService#getSubject(java.lang.String)    
	 */
		 
	@Override
	public List<Map<String, Object>> getSubject(String stageCode,String gradeCode)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String, String>();
		List<Map<String, Object>> list = null;
		if (null != gradeCode && 0 < gradeCode.length()) {
			if (stageCode.equals("1")) {
				map.put("gradeCode", gradeCode);
				logger.info("requestUrl=" + hxsSubjectXiaoUrl);
				String postResponse1 = HTTPClientUtils.httpPostRequestJson(hxsSubjectXiaoUrl, gson.toJson(map));
				logger.info("response=" + postResponse1);
				if (StringUtils.isNotBlank(postResponse1)) {
					Map<String, Object> fromJson = gson.fromJson(postResponse1, new TypeToken<Map<String, Object>>() {}.getType());
					Object object = fromJson.get("result");
					if (null != object) {
						String str = gson.toJson(object);
						list = gson.fromJson(str, new TypeToken<List<Map<String, Object>>>() {}.getType());
					}
				}
			}
			if (stageCode.equals("2")) {
				map.put("gradeCode", gradeCode);
				logger.info("requestUrl=" + hxsSubjectChuUrl);
				String postResponse2 = HTTPClientUtils.httpPostRequestJson(hxsSubjectChuUrl, gson.toJson(map));
				logger.info("response=" + postResponse2);
				if (StringUtils.isNotBlank(postResponse2)) {
					Map<String, Object> fromJson2 = gson.fromJson(postResponse2, new TypeToken<Map<String, Object>>() {}.getType());
					Object object2 = fromJson2.get("result");
					if (null != object2) {
						String str = gson.toJson(object2);
						list = gson.fromJson(str, new TypeToken<List<Map<String, Object>>>() {}.getType());
					}
				}
			}
			
		}
		return list;
	}


	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.RankMessageService#getNationalTop(java.lang.String, java.lang.String)    
	 */
		 
	@Override
	public String getNationalTop(String stageCode,String gradeCode, String subjectCode,String mobile)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String, String>();
		String nationalTop = null;
		if (null != gradeCode && 0 < gradeCode.length() && null != subjectCode && 0 < subjectCode.length()) {
			if (stageCode.equals("1")) {
				map.put("gradeCode", gradeCode);
				map.put("subjectCode", subjectCode);
				if (null != mobile && !mobile.equals("1")) {
					map.put("mobile", mobile);
				}
				logger.info("requestUrl=" + hxsNationalTopXiaoUrl);
				nationalTop = HTTPClientUtils.httpPostRequestJson(hxsNationalTopXiaoUrl, gson.toJson(map));
				logger.info("response=" + nationalTop);
			}
			if (stageCode.equals("2")) {
				map.put("gradeCode", gradeCode);
				map.put("subjectCode", subjectCode);
				if (null != mobile && !mobile.equals("")) {
					map.put("mobile", mobile);
				}
				logger.info("requestUrl=" + hxsNationalTopChuUrl);
				nationalTop = HTTPClientUtils.httpPostRequestJson(hxsNationalTopChuUrl, gson.toJson(map));
				logger.info("response=" + nationalTop);
			}
		}
		return nationalTop;
	}


	
	 /* (non-Javadoc)    
	 * @see com.eeduspace.cibn.service.RankMessageService#getAllRank(java.lang.String)    
	 */
		 
	@Override
	public String getAllRank(String json) throws Exception {
		// TODO Auto-generated method stub
		//String url = "http://211.157.179.218:8780/hxs_personaltutor_wechat/common/get_all_rank";
		String url = "http://personaltutor.e-edusky.com/hxs_personaltutor_wechat/common/get_all_rank";
		String httpPostRequestJson = HTTPClientUtils.httpPostRequestJson(url, json);
		return httpPostRequestJson;
			
	}
	
}

	