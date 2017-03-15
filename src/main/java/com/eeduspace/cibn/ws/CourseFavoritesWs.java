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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.model.CourseFavoritesModel;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.response.CourseFavoritesResponse;
import com.eeduspace.cibn.service.CourseFavoritesService;
import com.eeduspace.cibn.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


/**
 * @author songwei
 *	Date 2016-04-20
 *	Describe 个人信息我的收藏
 */
@Component
@Path(value = "/courseFavorites")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class CourseFavoritesWs {
	private final Logger logger = LoggerFactory.getLogger(CourseFavoritesWs.class);
    private Gson gson = new Gson();
    
    @Inject
    private CourseFavoritesService courseFavoritesService;
    
    /**
     * 我的收藏，收藏视频列表
     * */
    @POST
    @Path("/favoriteList")
    public Response favoriteList(@QueryParam("requestId") String requestId,String requestBody, 
    		@Context HttpServletRequest request,@Context HttpServletResponse response){
    	try {
    		logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI());
			CourseFavoritesModel cfModel = gson.fromJson(requestBody, CourseFavoritesModel.class);
			if(StringUtils.isBlank(cfModel.getUserCode())){
				logger.error("favoriteList ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".userCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".userCode"))).build();
			}
			if(org.springframework.util.StringUtils.isEmpty(cfModel.getPageable())){
				logger.error("favoriteList ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + ".pageable");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),".pageable"))).build();
			}
			Sort sort = new Sort(Sort.Direction.DESC, "updateDate");
			PageRequest pageable = new PageRequest(cfModel.getPageable().getPageNumber(),cfModel.getPageable().getPageSize(),sort);
			CourseFavoritesResponse courseFavoritesResponse = courseFavoritesService.getCourseFavoritesModels(cfModel, pageable);
			Page<CourseFavoritesModel> newPage=
					new PageImpl<CourseFavoritesModel>(courseFavoritesResponse.getCourseFavoritesModels(), pageable, courseFavoritesResponse.getTotalCount());
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(newPage);
			return Response.ok(gson.toJson(baseResponse)).build();
    	} catch (JsonSyntaxException e) {
			logger.error("requestId：{},favoriteList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".favoriteList"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},favoriteList Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
    }
    
    /**
     * 收藏视频，或者取消收藏视频
     * */
    @POST
    @Path("/favoriteOrNot")
    public Response favoriteOrNot(@QueryParam("requestId") String requestId,String requestBody,
    		@Context HttpServletRequest request,@Context HttpServletResponse response){
    	try {
    		logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(),requestBody);
			CourseFavoritesModel courseFavoritesModel = gson.fromJson(requestBody, CourseFavoritesModel.class);
			if(StringUtils.isBlank(courseFavoritesModel.getCourseId())){
				logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "courseId");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"courseId"))).build();
			}
			if(StringUtils.isBlank(courseFavoritesModel.getUserCode())){
				logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "userCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"userCode"))).build();
			}
			if(org.springframework.util.StringUtils.isEmpty(courseFavoritesModel.getIsDel())){
				logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "isDel");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"isDel"))).build();
			}
            //TODO 添加学段、学年、学科、教材版本、上下册信息、单元
            if(org.springframework.util.StringUtils.isEmpty(courseFavoritesModel.getStageCode())){
                logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "stageCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"stageCode"))).build();
            }
            if(org.springframework.util.StringUtils.isEmpty(courseFavoritesModel.getGradeCode())){
                logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "gradeCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"gradeCode"))).build();
            }
            if(org.springframework.util.StringUtils.isEmpty(courseFavoritesModel.getSubjectCode())){
                logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "subjectCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"subjectCode"))).build();
            }
            if(org.springframework.util.StringUtils.isEmpty(courseFavoritesModel.getBookTypeCode())){
                logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "bookTypeCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"bookTypeCode"))).build();
            }
            if(org.springframework.util.StringUtils.isEmpty(courseFavoritesModel.getBookTypeVersionCode())){
                logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "bookTypeVersionCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"bookTypeVersionCode"))).build();
            }
/*            if(org.springframework.util.StringUtils.isEmpty(courseFavoritesModel.getUnitCode())){
                logger.error("favoriteOrNot ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString() + "unitCode");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"unitCode"))).build();
            }*/
			CourseFavoritesResponse courseFavoritesResponse = courseFavoritesService.getFavoriteOrNot(courseFavoritesModel);
			
			BaseResponse baseResponse =new BaseResponse();
			baseResponse.setResult(courseFavoritesResponse.getFlag());
			return Response.ok(gson.toJson(baseResponse)).build();
    	} catch (JsonSyntaxException e) {
			logger.error("requestId：{},favoriteOrNot Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURSCEDATA_ERROR.toString(),".favoriteOrNot"))).build();
		} catch (Exception e) {
			logger.error("requestId：{},favoriteOrNot Exception：", requestId, e);
			return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
    }
    
    
}
