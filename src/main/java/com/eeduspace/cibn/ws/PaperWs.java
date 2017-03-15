package com.eeduspace.cibn.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.client.PaperClient;
import com.eeduspace.cibn.model.ExamDataDetailBeanForResponse;
import com.eeduspace.cibn.model.PaperModel;
import com.eeduspace.cibn.model.QuestionDataTemp;
import com.eeduspace.cibn.model.WeakKnowledgePointModel;
import com.eeduspace.cibn.model.request.PaperRequest;
import com.eeduspace.cibn.model.response.AnaModel;
import com.eeduspace.cibn.model.response.OptModel;
import com.eeduspace.cibn.model.response.PaperResponse;
import com.eeduspace.cibn.persist.po.DiagnosticReport;
import com.eeduspace.cibn.persist.po.TempPaperInfo;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.DiagnosticReportService;
import com.eeduspace.cibn.service.TempPaperInfoService;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.json.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * 试卷ws
 * 
 * @author zhuchaowei 2016年4月24日 Description
 */
@Component
@Path(value = "/paper")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
public class PaperWs {
	@Inject
	private PaperClient paperClient;
	private Gson gson = new Gson();
	private final Logger logger = LoggerFactory.getLogger(PaperWs.class);
	@Inject
	private DiagnosticReportService diagnosticReportService;
	@Inject
	private TempPaperInfoService tempPaperInfoService;
	
	/**
	 * 获取试卷列表 Author： zhuchaowei e-mail:zhuchaowei@e-eduspace.com 2016年4月24日
	 * 下午3:22:39
	 * 
	 * @param userCode
	 * @param pageNumber
	 * @param size
	 * @return
	 */
	@GET
	@Path("/get_paper/{userCode}/{gradeCode}/{subjectCode}/{bookType}/{unitCode}/{pageNumber}/{size}")
	public Response getPaperList(@Context HttpServletRequest httpRequest,@QueryParam("requestId") String requestId,@PathParam("gradeCode") String gradeCode,
			@PathParam("subjectCode") String subjectCode,
			@PathParam("bookType") String bookType,
			@PathParam("unitCode") String unitCode,
			@PathParam("userCode") String userCode,
			@PathParam("pageNumber") Integer pageNumber,
			@PathParam("size") Integer size) {
	
		if(StringUtils.isBlank(subjectCode)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"subjectCode");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "subjectCode"))).build();
		}
		if(StringUtils.isBlank(bookType)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"bookType");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "bookType"))).build();
		}
		if(StringUtils.isBlank(unitCode)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"unitCode");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "unitCode"))).build();
		}
		if(StringUtils.isBlank(userCode)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"userCode");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "userCode"))).build();
		}
		if(StringUtils.isBlank(gradeCode)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"gradeCode");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "gradeCode"))).build();
		}
		if(pageNumber==null){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"pageNumber");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "pageNumber"))).build();
		}
		if(size==null){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"size");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "size"))).build();
		}
		PaperRequest paperRequest=new PaperRequest(gradeCode, subjectCode, bookType, unitCode, "单元测试", size, pageNumber);
		BaseResponse baseResponse = new BaseResponse();
		try {
			PaperResponse paperResponse =paperClient.getPaper(paperRequest);
			List<PaperModel> paperModels=new ArrayList<>();
			if(paperResponse.getPaperDatas()!=null){
				paperModels=paperResponse.getPaperDatas();
				for (PaperModel paperModel : paperModels) {
					List<DiagnosticReport> list=diagnosticReportService.findByUserCodeAndPaperCodeAndUnitCode(userCode, paperModel.getId(),unitCode);
					if(list.size()>0){
						DiagnosticReport dReport=list.get(0);
						if(dReport.isBuy()){
							paperModel.setDiagnostic(true);//将诊断标记为 已诊断
						}else{
							paperModel.setDiagnostic(false);
						}
						paperModel.setDiagnosticReportUUID(dReport.getUuid());//
					}
				}
			}
			paperResponse.setPaperDatas(paperModels);
			baseResponse.setResult(paperResponse);
		}catch(JsonSyntaxException e){
			logger.error("JsonSyntaxException :", e);
			 return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.RESOURSCEDATA_ERROR.toString()))).build();
		}catch (ClientProtocolException e) {
			logger.error("getPaperList error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		} catch (IOException e) {
			logger.error("getPaperList error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}

	/**
	 * 获取试题 Author： zhuchaowei e-mail:zhuchaowei@e-eduspace.com 2016年4月24日
	 * 下午3:22:53
	 * 
	 * @param userCode
	 * @param pageNumber
	 * @param size
	 * @return
	 * @throws IOException 
	 */
	@GET
	@Path("/get_subject/{paperCode}")
	public Response getSubject(@Context HttpServletRequest  httpRequest,
			@Context HttpServletResponse httpResponse, @QueryParam("requestId") String requestId,@PathParam("paperCode") String paperCode) throws IOException {
		BaseResponse baseResponse = new BaseResponse();
		if(StringUtils.isBlank(paperCode)){
			logger.error(ResponseCode.PARAMETER_MISS.toString()+"."+"paperCode");
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(), "paperCode"))).build();
		}
		try {
			ExamDataDetailBeanForResponse	dataDetailBeanForResponse=paperClient.getPaperInfo(paperCode);
			List<QuestionDataTemp> lists=dataDetailBeanForResponse.getQuestions();
			if(lists!=null){
				for (QuestionDataTemp questionDataTemp : lists) {
					List<AnaModel> anaModels=gson.fromJson(questionDataTemp.getAnalyze(), new TypeToken<List<AnaModel>>() {}.getType());
					for (AnaModel anaModel : anaModels) {
						if (null == anaModel.getAnalyzeKey() || anaModel.getAnalyzeKey().equals("")) {
							anaModel.setAnalyzeKey("本题");
						}
					}
					List<OptModel> optionModels=gson.fromJson(questionDataTemp.getOption(), new TypeToken<List<OptModel>>() {}.getType());
					questionDataTemp.setAnalyzeModels(anaModels);
					questionDataTemp.setOptionModels(optionModels);
					questionDataTemp.setAnalyze(null);
					questionDataTemp.setOption(null);
				}
				dataDetailBeanForResponse.setQuestions(lists);
			}
			// 如果试卷code存在
			if(dataDetailBeanForResponse.getCode()!=null&&!"".equals(dataDetailBeanForResponse.getCode())){
				TempPaperInfo info=new TempPaperInfo();
				info.setPaperInfo(gson.toJson(dataDetailBeanForResponse));
				info=tempPaperInfoService.save(info);
				dataDetailBeanForResponse.setPaperUUID(info.getUuid());
			}
			baseResponse.setResult(dataDetailBeanForResponse);
		}catch(JsonSyntaxException e){
			logger.error("JsonSyntaxException :", e);
			 return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.RESOURSCEDATA_ERROR.toString()))).build();
		}catch (Exception e) {
			logger.error("save TempPaperInfo error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
		}
		return Response.ok(gson.toJson(baseResponse)).build();
	}
}
