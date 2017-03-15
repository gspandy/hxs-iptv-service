
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:RankMessageWs.java 
	 * 包名:com.eeduspace.cibn.ws 
	 * 创建日期:2016年11月22日上午11:44:20 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.ws;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eeduspace.cibn.model.BaseData;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.WeekRankingModel;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.RankMessageService;
import com.eeduspace.cibn.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：RankMessageWs    
 * 类描述： 生成随机排行榜的请求信息   
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年11月22日 上午11:44:20    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年11月22日 上午11:44:20    
 * 修改备注：       
 * @version </pre>    
 */
@Component
@Path(value = "/rankMessageWs")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class RankMessageWs {

	private static final Logger logger = LoggerFactory.getLogger(RankMessageWs.class);
	Gson gson = new Gson();
	@Inject
	private RankMessageService rankMessageService;
	/**
	 * 生成随机排行榜的请求信息
	 * */
	@GET
	@Path("/getRankMessageWs/{mobile}")
	public Response getRankMessageWs(@PathParam("mobile") String mobile){
		BaseResponse baseResponse =new BaseResponse();
		Random rand = new Random();
		String nationalTopString = "";
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> mapNational = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> stageList = rankMessageService.getStage();
			if (null != stageList && 0 < stageList.size()) {
				for (Map<String, Object> map : stageList) {
					if (map.get("stageCode").toString().equals("1")) {
						List<Map<String, Object>> gradeList = rankMessageService.getGrade(map.get("stageCode").toString());
						logger.info("gradeList=========" + gradeList);
						if (null != gradeList && 0 < gradeList.size()) {
							int i = rand.nextInt(gradeList.size());
							Map<String, Object> map2 = gradeList.get(i);
							String gradeCode = map2.get("gradeCode").toString();
							String gradeName = map2.get("gradeName").toString();
							mapNational.put("gradeCode", gradeCode);
							mapNational.put("gradeName", gradeName);
							List<Map<String, Object>> subjectList = rankMessageService.getSubject(map.get("stageCode").toString(),gradeCode);
							logger.info("subjectList=========" + subjectList);
							if (null != subjectList && 0 < subjectList.size()) {
								int j = rand.nextInt(subjectList.size());
								Map<String, Object> map3 = subjectList.get(j);
								String subjectCode = map3.get("subjectCode").toString();
								String subjectName = map3.get("subjectName").toString();
								mapNational.put("subjectCode", subjectCode);
								mapNational.put("subjectName", subjectName);
								//list.add(subjectCode);
								//list.add(subjectName);
								nationalTopString = rankMessageService.getNationalTop(map.get("stageCode").toString(),gradeCode,subjectCode,mobile);
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
												List<Map<String, Object>> rankingList = gson.fromJson(json2, new TypeToken<List<Map<String, Object>>>() {}.getType());
												mapNational.put("rankingList", rankingList);
											}
											Object user = fromJson2.get("user");
											if (null != user) {
												String json3 = gson.toJson(user);
												Map<String, Object> users = gson.fromJson(json3, new TypeToken<Map<String, Object>>() {}.getType());
												mapNational.put("user", users);
											} else {
												mapNational.put("user", "");
											}
											baseResponse.setResult(mapNational);
										}
									}
								}
							}
						}
					}
					if (null != nationalTopString && 0 < nationalTopString.length()) {
						Map<String,Object> fromJson = gson.fromJson(nationalTopString, new TypeToken<Map<String,Object>>() {}.getType());
						if (!fromJson.get("httpCode").equals("200")) {
							String stageCode = "2";
							List<Map<String, Object>> gradeList = rankMessageService.getGrade(map.get("stageCode").toString());
							logger.info("gradeList=========" + gradeList);
							if (null != gradeList && 0 < gradeList.size()) {
								int i = rand.nextInt(gradeList.size());
								Map<String, Object> map2 = gradeList.get(i);
								String gradeCode = map2.get("gradeCode").toString();
								String gradeName = map2.get("gradeName").toString();
								//mapNational.put("gradeCode", gradeCode);
								mapNational.put("gradeName", gradeName);
								List<Map<String, Object>> subjectList = rankMessageService.getSubject(map.get("stageCode").toString(),gradeCode);
								logger.info("subjectList=========" + subjectList);
								if (null != subjectList && 0 < subjectList.size()) {
									int j = rand.nextInt(subjectList.size());
									Map<String, Object> map3 = subjectList.get(j);
									String subjectCode = map3.get("subjectCode").toString();
									String subjectName = map3.get("subjectName").toString();
									//mapNational.put("subjectCode", subjectCode);
									mapNational.put("subjectName", subjectName);
									nationalTopString = rankMessageService.getNationalTop(map.get("stageCode").toString(),gradeCode,subjectCode,mobile);
									logger.info("nationalTopString=========" + nationalTopString);
									if (null != nationalTopString && 0 < nationalTopString.length()) {
										Map<String,Object> fromJson2 = gson.fromJson(nationalTopString, new TypeToken<Map<String,Object>>() {}.getType());
										if (fromJson2.get("httpCode").equals("200")) {
											Object object = fromJson.get("result");
											if (null != object) {
												String json = gson.toJson(object);
												Map<String, Object> fromJson3 = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
												Object object2 = fromJson3.get("rankingList");
												if (null != object2) {
													String json2 = gson.toJson(object2);
													List<Map<String, Object>> rankingList = gson.fromJson(json2, new TypeToken<List<Map<String, Object>>>() {}.getType());
													mapNational.put("rankingList", rankingList);
												}
												Object user = fromJson2.get("user");
												if (null != user) {
													String json3 = gson.toJson(user);
													Map<String, Object> users = gson.fromJson(json3, new TypeToken<Map<String, Object>>() {}.getType());
													mapNational.put("user", users);
												} else {
													mapNational.put("user", "");
												}
												baseResponse.setResult(mapNational);
											}
										}
									}
								}
							}
						}
					}
				}
			} else {
				BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".stageList");
			}
			
		} catch (Exception e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	
	@GET
	@Path("/getTestRankMessageWs/{mobile}")
	public Response getTestRankMessageWs(@PathParam("mobile") String mobile){
		StringWriter writer = new StringWriter();
		JsonWriter jsonWriter = new JsonWriter(Streams.writerForAppendable(writer));
		jsonWriter.setSerializeNulls(true);   // 这一行是关键
		BaseResponse baseResponse =new BaseResponse();
		
		/*UserModel userModel = gson.fromJson(requestBody, UserModel.class);
		String mobile = userModel.getMobile();*/
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
							/**
							 *  	private String championTimes;
									private String weekFightValueFloat;
									private String HeadImgUrl;
									private String NickName;
									private String UserCode;
									private String CurrentRank;
									
									private Boolean IsChallenge=false;
									private String Mobile;
									private Integer IsVip;
									private Boolean Overdue;
									
									private Long Ranking;//排名
							 */
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
		gson.toJson(baseResponse, writer);
		return Response.ok(writer.toString()).build();
	}
}

	