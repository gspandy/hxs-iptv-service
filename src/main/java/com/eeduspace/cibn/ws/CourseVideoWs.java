package com.eeduspace.cibn.ws;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

import com.eeduspace.cibn.model.BaseDataModel;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.CourseVideoService;
import com.eeduspace.cibn.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * @author songwei
 *	Date 2016-04-21
 * Describe 知识点，课程视频
 */
@Component
@Path("/courseVideo")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class CourseVideoWs {

	private final Logger logger = LoggerFactory.getLogger(CourseVideoWs.class);
	private Gson gson = new Gson();

	@Inject
	private CourseVideoService courseVideoService;

	/**
	 * 根据学年，学科，教材，单元等条件查询课程列表
	 * 根据单元code获取视频code和视频名称
	 * */
	@POST
	@Path("/courses")
	public Response getCousres(@QueryParam("requestId") String requestId,String requestBody, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
			BaseDataModel baseDataModel = gson.fromJson(requestBody, BaseDataModel.class);
			if(StringUtils.isBlank(baseDataModel.getUnitCode())){
				logger.error("getCousres ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".unitCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".unitCode"))).build();
			}
			BaseDataModel baseDataModel2 = courseVideoService.getCousreVideos(baseDataModel);
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(baseDataModel2);
			return Response.ok(gson.toJson(baseResponse)).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},getCousres Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".getCousres"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},getCousres Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
	}
	/**
	 * 课程视频详情
	 * 根据视频code单元code获得视频其他属性和对应产生式和返回的单元code
	 * */
	@POST
	@Path("/course")
	public Response getCousre(@QueryParam("requestId") String requestId,String requestBody, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
			BaseDataModel baseDataModel = gson.fromJson(requestBody, BaseDataModel.class);
			if(StringUtils.isBlank(baseDataModel.getCourseCode())){
				logger.error("getCousres ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".courseCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".courseCode"))).build();
			}
			BaseDataModel baseDataModel2 = courseVideoService.getCousreVideo(baseDataModel);
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(baseDataModel2);
			return Response.ok(gson.toJson(baseResponse)).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},getCousre Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".getCousre"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},getCousre Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
	}

	/**
	 * 通过产生式获取练习题(包含题干，答案和音频解析)
	 * */
	@POST
	@Path("/examinations")
	public Response getExaminations (@QueryParam("requestId") String requestId,String requestBody, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
			BaseDataModel baseDataModel = gson.fromJson(requestBody, BaseDataModel.class);
			if(StringUtils.isBlank(baseDataModel.getSubjectCode())){
				logger.error("getExaminations ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".subjectCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".subjectCode"))).build();
			}
			if(StringUtils.isBlank(baseDataModel.getProductionCode())){
				logger.error("getExaminations ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".courseCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".courseCode"))).build();
			}
			BaseDataModel dataModel = courseVideoService.getVideoExaminations(baseDataModel.getSubjectCode(), baseDataModel.getProductionCode());
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(dataModel);
			return Response.ok(gson.toJson(baseResponse)).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},getExaminations Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".getExaminations"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},getExaminations Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
	}

	
}
