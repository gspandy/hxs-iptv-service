package com.eeduspace.cibn.ws;

import java.util.List;

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

import com.eeduspace.cibn.model.BaseData;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.BaseDataService;
import com.eeduspace.cibn.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author songwei
 *	Date 2016-04-21
 *	Descirbe 调用资源库基本接口
 */
@Component
@Path(value = "/baseData")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class BaseDataWs {

	private final Logger logger = LoggerFactory.getLogger(BaseDataWs.class);
	private Gson gson = new Gson();

	/*@Inject
	private GradeService gradeService;*/
	@Inject
	private BaseDataService baseDataService;
	
	/**
	 * 查询学段列表
	 * */
	@POST
	@Path("/stage")
	public Response stageList(@QueryParam("requestId") String requestId, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI());
			List<BaseData> baseDatas = baseDataService.getStageList();
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(baseDatas);
			return Response.ok(gson.toJson(baseResponse)).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},stageList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".stageList"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},stageList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
	}
	
	/**根据学段
	 * 查询学年列表
	 * */
	@POST
	@Path("/grade")
	public Response gradeList(@QueryParam("requestId") String requestId,String requestBody, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
			BaseData baseData = gson.fromJson(requestBody, BaseData.class);
			if(StringUtils.isBlank(baseData.getStageCode())){
				logger.error("gradeList ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".stage");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".stage"))).build();
			}
			List<BaseData> baseDatas = baseDataService.getGradeList(baseData.getStageCode());
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(baseDatas);
			return Response.ok(gson.toJson(baseResponse)).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},gradeList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".gradeList"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},gradeList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
	}
	/**根据学年
	 * 查询学科列表
	 * */
	@POST
	@Path("/subject")
	public Response subjectList(@QueryParam("requestId") String requestId,String requestBody, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
			BaseData baseData = gson.fromJson(requestBody, BaseData.class);
			if(StringUtils.isBlank(baseData.getGradeCode())){
				logger.error("subjectList ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".grade");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".grade"))).build();
			}
			List<BaseData> baseDatas = baseDataService.getSubjectList(baseData.getGradeCode());
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(baseDatas);
			return Response.ok(new String(gson.toJson(baseResponse))).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},gradeList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".gradeList"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},subjectList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
	}
	/**根据学年，学科
	 * 查询教材列表
	 * */
	@POST
	@Path("/bookType")
	public Response bookTypeList(@QueryParam("requestId") String requestId,String requestBody, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
			BaseData baseData = gson.fromJson(requestBody, BaseData.class);
			if(StringUtils.isBlank(baseData.getGradeCode())){
				logger.error("bookTypeList ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".grade");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".grade"))).build();
			}
			if(StringUtils.isBlank(baseData.getSubjectCode())){
				logger.error("bookTypeList ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".subjectCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".subjectCode"))).build();
			}
			List<BaseData> baseDatas = baseDataService.getBookTypeList(baseData.getGradeCode(), baseData.getSubjectCode());
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(baseDatas);
			return Response.ok(gson.toJson(baseResponse)).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},bookTypeList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".bookTypeList"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},bookTypeList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		} 
	}
	
	/**根据学年，学科，教材
	 * 查询教材上下册
	 * */
	@POST
	@Path("/bookTypeVersion")
	public Response bookTypeVersion(@QueryParam("requestId") String requestId,String requestBody, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
			BaseData baseData = gson.fromJson(requestBody, BaseData.class);
			if(StringUtils.isBlank(baseData.getGradeCode())){
				logger.error("bookTypeVersion ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".grade");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".grade"))).build();
			}
			if(StringUtils.isBlank(baseData.getSubjectCode())){
				logger.error("bookTypeVersion ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".subjectCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".subjectCode"))).build();
			}
			if(StringUtils.isBlank(baseData.getBookTypeCode())){
				logger.error("bookTypeVersion ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".bookTypeCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".bookTypeCode"))).build();
			}
			List<BaseData> baseDatas = baseDataService.getBookTypeVersion(baseData.getSubjectCode(), baseData.getGradeCode(), baseData.getBookTypeCode());
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(baseDatas);
			return Response.ok(gson.toJson(baseResponse)).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},bookTypeVersion Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".bookTypeVersion"))).build();
		}catch (Exception e) {
			logger.error("requestId：{},bookTypeVersion Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		} 
	}
	
	/**根据学年，学科，教材
	 * 查询单元知识点（视频）列表
	 * */
	@POST
	@Path("/unit")
	public Response unitList(@QueryParam("requestId") String requestId,String requestBody, 
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		try {
			logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), requestBody);
			BaseData baseData= gson.fromJson(requestBody, BaseData.class);
			if(StringUtils.isBlank(baseData.getBookTypeVersionCode())){
				logger.error("unitList ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".bookTypeVersionCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".bookTypeVersionCode"))).build();
			}
			List<BaseData> baseDatas = baseDataService.getUnitList(baseData.getBookTypeVersionCode());
			
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setResult(baseDatas);
			return Response.ok(gson.toJson(baseResponse)).build();
		} catch (JsonSyntaxException e) {
			logger.error("requestId：{},unitList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".unitList"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},unitList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
		
	}

}
