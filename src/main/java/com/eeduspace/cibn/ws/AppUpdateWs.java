package com.eeduspace.cibn.ws;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.model.AppUpdateModel;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.AppUpdateService;
import com.eeduspace.cibn.util.CommonUtil;
import com.google.gson.Gson;

@Component
@Path("/appUpdate")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
public class AppUpdateWs {
	@Value("${cibn.server.url}")
	private String address;
	private final Logger logger = LoggerFactory.getLogger(AppUpdateWs.class);
	private Gson gson = new Gson();
	
	@Inject
	private AppUpdateService appUpdateService;
	
	@GET
	@Path("/update")
	public Response update(@QueryParam("requestId") String requestId,@Context HttpServletRequest request,@Context HttpServletResponse response){
		
		logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(),null);
		AppUpdateModel appUpdateModel = appUpdateService.getAppUpdateModel();
		//String address = "http://" + request.getLocalAddr() + ":" + request.getLocalPort();
		String path =address + appUpdateModel.getDownUrl();
		logger.debug("path地址为：" + path);
		BaseResponse baseResponse = new BaseResponse();
		appUpdateModel.setDownUrl(path);
		baseResponse.setResult(appUpdateModel);
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	
	@POST
	@Path("/fileupload")
	public Response upload(@QueryParam("requestId") String requestId,
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(),null);
		
		String path = "/opt/upload/";
		logger.debug(path);
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String path2 = path + "cibn.apk";
//		String path2 = path + "123.apk";
		file = new File(path2);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			logger.debug(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setResult("true");
//		baseResponse.setResult(meg);
		
		return Response.ok(gson.toJson(baseResponse)).build();
	}
	
}
