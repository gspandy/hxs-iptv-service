
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:RankMessageController.java 
	 * 包名:com.eeduspace.cibn.controller 
	 * 创建日期:2016年11月24日上午11:51:25 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.controller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eeduspace.cibn.model.ChallengeRequestModel;
import com.eeduspace.cibn.model.TSUserModel;
import com.eeduspace.cibn.model.WeekRankingModel;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.RankMessageService;
import com.eeduspace.cibn.util.DataMap;
import com.eeduspace.cibn.ws.RankMessageWs;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：RankMessageController    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年11月24日 上午11:51:25    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年11月24日 上午11:51:25    
 * 修改备注：       
 * @version </pre>    
 */
@Controller
@RequestMapping("/rankMessageController")
public class RankMessageController {

	private static final Logger logger = LoggerFactory.getLogger(RankMessageController.class);
	Gson gson = new Gson();
	@Inject
	private RankMessageService rankMessageService;
	/**
	 * 生成随机排行榜的请求信息
	 * */
//	@RequestMapping("/getRankMessageWs")
//	@ResponseBody
//	public BaseResponse getRankMessageWs(HttpServletRequest request,HttpServletResponse response,DataMap dataMap,@RequestBody ChallengeRequestModel challengeRequestModel){
//		response.setCharacterEncoding("UTF-8");
//		response.addHeader("Access-Control-Allow-Origin","*");
//		//Map<String, String> map0 = dataMap.getMap(request);
//		BaseResponse baseResponse =new BaseResponse();
//		Random rand = new Random();
////		if (null != map0 && !map0.isEmpty()) {
////			String mobile = map0.get("mobile");
////			
////		}
//		String mobile = challengeRequestModel.getMobile();
//		String nationalTopString = "";
//		List<Object> list = new ArrayList<Object>();
//		Map<String, Object> mapNational = new HashMap<String, Object>();
//		try {
//			List<Map<String, Object>> stageList = rankMessageService.getStage();
//			if (null != stageList && 0 < stageList.size()) {
//				for (Map<String, Object> map : stageList) {
//					if (map.get("stageCode").toString().equals("1")) {
//						List<Map<String, Object>> gradeList = rankMessageService.getGrade(map.get("stageCode").toString());
//						logger.info("gradeList=========" + gradeList);
//						if (null != gradeList && 0 < gradeList.size()) {
//							int i = rand.nextInt(gradeList.size());
//							Map<String, Object> map2 = gradeList.get(i);
//							String gradeCode = map2.get("gradeCode").toString();
//							String gradeName = map2.get("gradeName").toString();
//							//mapNational.put("gradeCode", gradeCode);
//							mapNational.put("gradeName", gradeName);
//							List<Map<String, Object>> subjectList = rankMessageService.getSubject(map.get("stageCode").toString(),gradeCode);
//							logger.info("subjectList=========" + subjectList);
//							if (null != subjectList && 0 < subjectList.size()) {
//								int j = rand.nextInt(subjectList.size());
//								Map<String, Object> map3 = subjectList.get(j);
//								String subjectCode = map3.get("subjectCode").toString();
//								String subjectName = map3.get("subjectName").toString();
//								//mapNational.put("subjectCode", subjectCode);
//								mapNational.put("subjectName", subjectName);
//								//list.add(subjectCode);
//								//list.add(subjectName);
//								nationalTopString = rankMessageService.getNationalTop(map.get("stageCode").toString(),gradeCode,subjectCode,mobile);
//								logger.info("nationalTopString=========" + nationalTopString);
//								if (null != nationalTopString && 0 < nationalTopString.length()) {
//									Map<String,Object> fromJson = gson.fromJson(nationalTopString, new TypeToken<Map<String,Object>>() {}.getType());
//									if (fromJson.get("httpCode").equals("200")) {
//										Object object = fromJson.get("result");
//										if (null != object) {
//											String json = gson.toJson(object);
//											Map<String, Object> fromJson2 = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
//											Object object2 = fromJson2.get("rankingList");
//											if (null != object2) {
//												String json2 = gson.toJson(object2);
//												List<WeekRankingModel> rankingList = gson.fromJson(json2, new TypeToken<List<WeekRankingModel>>() {}.getType());
//												mapNational.put("rankingList", rankingList);
//											}
//											Object user = fromJson2.get("user");
//											if (null != user) {
//												String json3 = gson.toJson(user);
//												WeekRankingModel users = gson.fromJson(json3, WeekRankingModel.class);
//												
//												mapNational.put("user", users);
//											} else {
//												mapNational.put("user", null);
//											}
//											baseResponse.setResult(mapNational);
//										}
//									}
//								}
//							}
//						}
//					}
//					if (null != nationalTopString && 0 < nationalTopString.length()) {
//						Map<String,Object> fromJson = gson.fromJson(nationalTopString, new TypeToken<Map<String,Object>>() {}.getType());
//						if (!fromJson.get("httpCode").equals("200")) {
//							String stageCode = "2";
//							List<Map<String, Object>> gradeList = rankMessageService.getGrade(map.get("stageCode").toString());
//							logger.info("gradeList=========" + gradeList);
//							if (null != gradeList && 0 < gradeList.size()) {
//								int i = rand.nextInt(gradeList.size());
//								Map<String, Object> map2 = gradeList.get(i);
//								String gradeCode = map2.get("gradeCode").toString();
//								String gradeName = map2.get("gradeName").toString();
//								//mapNational.put("gradeCode", gradeCode);
//								mapNational.put("gradeName", gradeName);
//								List<Map<String, Object>> subjectList = rankMessageService.getSubject(map.get("stageCode").toString(),gradeCode);
//								logger.info("subjectList=========" + subjectList);
//								if (null != subjectList && 0 < subjectList.size()) {
//									int j = rand.nextInt(subjectList.size());
//									Map<String, Object> map3 = subjectList.get(j);
//									String subjectCode = map3.get("subjectCode").toString();
//									String subjectName = map3.get("subjectName").toString();
//									//mapNational.put("subjectCode", subjectCode);
//									mapNational.put("subjectName", subjectName);
//									nationalTopString = rankMessageService.getNationalTop(map.get("stageCode").toString(),gradeCode,subjectCode,mobile);
//									logger.info("nationalTopString=========" + nationalTopString);
//									if (null != nationalTopString && 0 < nationalTopString.length()) {
//										Map<String,Object> fromJson0 = gson.fromJson(nationalTopString, new TypeToken<Map<String,Object>>() {}.getType());
//										if (fromJson0.get("httpCode").equals("200")) {
//											Object object = fromJson0.get("result");
//											if (null != object) {
//												String json = gson.toJson(object);
//												Map<String, Object> fromJson2 = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
//												Object object2 = fromJson2.get("rankingList");
//												if (null != object2) {
//													String json2 = gson.toJson(object2);
//													List<WeekRankingModel> rankingList = gson.fromJson(json2, new TypeToken<List<WeekRankingModel>>() {}.getType());
//													
//													mapNational.put("rankingList", rankingList);
//												}
//												Object user = fromJson2.get("user");
//												if (null != user) {
//													String json3 = gson.toJson(user);
//													WeekRankingModel users = gson.fromJson(json3, WeekRankingModel.class);
//													mapNational.put("user", users);
//												} else {
//													mapNational.put("user", null);
//												}
//												baseResponse.setResult(mapNational);
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			} else {
//				BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".stageList");
//			}
//			
//		} catch (Exception e) {
//			
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				
//		}
//		return baseResponse;
//	}
	
	@RequestMapping("/getTestRankMessageWs")
	@ResponseBody
	public BaseResponse getTestRankMessageWs(HttpServletRequest request,HttpServletResponse response,DataMap dataMap,@RequestBody ChallengeRequestModel challengeRequestModel){
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");
		BaseResponse baseResponse =new BaseResponse();
		//Map<String, String> map = dataMap.getMap(request);
		/*if (null != map && !map.isEmpty()) {
			
		}*/
		//String mobile = map.get("mobile");
		String mobile = challengeRequestModel.getMobile();
		Random rand = new Random();
		String nationalTopString = "";
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> mapNational = new HashMap<String, Object>();
		try {
			mapNational.put("subjectName", "数学");
			mapNational.put("gradeName", "一年级");
			nationalTopString = rankMessageService.getNationalTop("1","11","2",mobile);
			logger.info("nationalTopString=========" + nationalTopString);
			if (null != nationalTopString && 0 < nationalTopString.length()) {
				Map<String,Object> fromJson = gson.fromJson(nationalTopString, new TypeToken<Map<String,Object>>() {}.getType());
				if (fromJson.get("httpCode").equals("200")) {
					Object object = fromJson.get("result");
					if (null != object) {
						String json = gson.toJson(object);
						Map<String, Object> fromJson2 = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
						Object object2 = fromJson2.get("rankingList");
						if (null != object2) {
							String json2 = gson.toJson(object2);
							List<WeekRankingModel> rankingList = gson.fromJson(json2, new TypeToken<List<WeekRankingModel>>() {}.getType());
							for (WeekRankingModel weekRankingModel : rankingList) {
								weekRankingModel.setChampionTimes(weekRankingModel.getChampionTimes()==null?null:weekRankingModel.getChampionTimes());
								weekRankingModel.setCurrentRank(weekRankingModel.getCurrentRank()==null?null:weekRankingModel.getCurrentRank());
								weekRankingModel.setWeekFightValueFloat(weekRankingModel.getWeekFightValueFloat()==null?null:weekRankingModel.getWeekFightValueFloat());
								weekRankingModel.setHeadImgUrl(weekRankingModel.getHeadImgUrl()==null?null:weekRankingModel.getHeadImgUrl());
								weekRankingModel.setNickName(weekRankingModel.getNickName()==null?null:weekRankingModel.getNickName());
								weekRankingModel.setUserCode(weekRankingModel.getUserCode()==null?null:weekRankingModel.getUserCode());
								weekRankingModel.setIsChallenge(weekRankingModel.getIsChallenge()==null?null:weekRankingModel.getIsChallenge());
								weekRankingModel.setMobile(weekRankingModel.getMobile()==null?null:weekRankingModel.getMobile());
								weekRankingModel.setIsVip(weekRankingModel.getIsVip()==null?null:weekRankingModel.getIsVip());
								weekRankingModel.setOverdue(weekRankingModel.getOverdue()==null?null:weekRankingModel.getOverdue());
								weekRankingModel.setRanking(weekRankingModel.getRanking()==null?null:weekRankingModel.getRanking());
							}
							mapNational.put("rankingList", rankingList);
						}
						Object user = fromJson2.get("user");
						if (null != user) {
							String json3 = gson.toJson(user);
							WeekRankingModel users = gson.fromJson(json3, WeekRankingModel.class);
							users.setChampionTimes(users.getChampionTimes()==null?null:users.getChampionTimes());
							users.setCurrentRank(users.getCurrentRank()==null?null:users.getCurrentRank());
							users.setWeekFightValueFloat(users.getWeekFightValueFloat()==null?null:users.getWeekFightValueFloat());
							users.setHeadImgUrl(users.getHeadImgUrl()==null?null:users.getHeadImgUrl());
							users.setNickName(users.getNickName()==null?null:users.getNickName());
							users.setUserCode(users.getUserCode()==null?null:users.getUserCode());
							users.setIsChallenge(users.getIsChallenge()==null?null:users.getIsChallenge());
							users.setMobile(users.getMobile()==null?null:users.getMobile());
							users.setIsVip(users.getIsVip()==null?null:users.getIsVip());
							users.setOverdue(users.getOverdue()==null?null:users.getOverdue());
							users.setRanking(users.getRanking()==null?null:users.getRanking());
							mapNational.put("user", users);
						} else {
							mapNational.put("user", null);
						}
						baseResponse.setResult(mapNational);
					}
				}
			}
		} catch (Exception e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		//gson.toJson(baseResponse, writer);
		return baseResponse;
	}
	@RequestMapping("/getRankMessageWs")
	@ResponseBody
	public BaseResponse getRankMessageWs(@RequestBody ChallengeRequestModel challengeRequestModel) {
		BaseResponse baseResponse =new BaseResponse();
		Map<String, String> map = new HashMap<String, String>();
//		{
//			"gradeCode": "12",
//		"subjectCode": "2",
//		"bookType": "人教",
//		"pageNum": "1",
//		"pageSize": "10"
//	}
		if (StringUtils.isNotBlank(challengeRequestModel.getGradeCode())) {
			map.put("gradeCode", challengeRequestModel.getGradeCode());
		} else {
			map.put("gradeCode", "12");
		}
		if (StringUtils.isNotBlank(challengeRequestModel.getSubjectCode())) {
			map.put("subjectCode", challengeRequestModel.getSubjectCode());
		} else {
			map.put("subjectCode", "2");
		}
		if (StringUtils.isNotBlank(challengeRequestModel.getBookType())) {
			map.put("bookType", challengeRequestModel.getBookType());
		} else {
			map.put("bookType", "人教");
		}
		map.put("pageNum", "1");
		map.put("pageSize", "12");
		String json = gson.toJson(map);
		List<WeekRankingModel> rankList = new ArrayList<WeekRankingModel>();
		Map<String, Object> mapNational = new HashMap<String, Object>();
		try {
			String httpResponse = rankMessageService.getAllRank(json);
			logger.info("httpResponse====" + httpResponse);
			if (StringUtils.isNotBlank(httpResponse)) {
				Map<String,Object> mapJson = gson.fromJson(httpResponse, new TypeToken<Map<String,Object>> () {}.getType());
				if ("200".equals(mapJson.get("httpCode"))) {
					if (null != mapJson.get("result")) {
						Map<String,Object> mapJson2 = gson.fromJson(gson.toJson(mapJson.get("result")), new TypeToken<Map<String,Object>> () {}.getType());
						if (null != mapJson2.get("list")) {
							List<TSUserModel> tSList = gson.fromJson(gson.toJson(mapJson2.get("list")), new TypeToken<List<TSUserModel>> () {}.getType());
							for (TSUserModel tsUserModel : tSList) {
								WeekRankingModel wm = new WeekRankingModel();
								wm.setNickName(tsUserModel.getUserName());
								wm.setHeadImgUrl(tsUserModel.getHeadImgUrl());
								wm.setWeekFightValueFloat(tsUserModel.getScore());
								rankList.add(wm);
							}
							WeekRankingModel user = new WeekRankingModel();
							if (StringUtils.isNotBlank(challengeRequestModel.getMobile()) && !challengeRequestModel.getMobile().equals("1")) {
								user.setNickName(challengeRequestModel.getMobile());
							}
							else {
								user.setNickName("游客");
							}
							mapNational.put("rankingList", rankList);
							mapNational.put("gradeName", "二年级");
							mapNational.put("subjectName", "数学");
							mapNational.put("user", user);
							logger.info("mapNational===" + mapNational);
							baseResponse.setResult(mapNational);
						}
					}
				}
			}
			
		} catch (Exception e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		}
		
		return baseResponse;
	}
}

	