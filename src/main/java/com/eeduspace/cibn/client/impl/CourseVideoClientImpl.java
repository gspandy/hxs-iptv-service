package com.eeduspace.cibn.client.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.client.CourseVideoClient;
import com.eeduspace.cibn.model.BaseDataModel;
import com.eeduspace.cibn.model.ExaminationModel;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author songwei
 * Date 2016-05-16
 * Describe 课程视频业务数据实现
 */
@Service
public class CourseVideoClientImpl implements CourseVideoClient {
	
	private final Logger logger = LoggerFactory.getLogger(CourseVideoClientImpl.class);
	private Gson gson = new Gson();
	@Value("${cibn.courses.url}")
	private String coursesUrl;
	@Value("${cibn.course.url}")
	private String courseUrl;
	@Value("${cibn.examinations.url}")
	private String examinationsUrl;
	
	@Override
	public String getCousreVideos(BaseDataModel baseDataModel) throws JsonSyntaxException{
		String coursesReq = MessageFormat.format(coursesUrl,baseDataModel.getUnitCode());
		String gsonResponse= HTTPClientUtils.httpGetRequestJson(coursesReq);
		logger.debug("资源库返回的数据：" + gsonResponse);
		return gsonResponse;
	}

	@Override
	public String getCousreVideo(BaseDataModel baseDataModel) {
		logger.debug("传给资源库的数据：" + courseUrl + baseDataModel.getCourseCode());
		String courseReq = MessageFormat.format(courseUrl, baseDataModel.getCourseCode());
		String gsonResponse= HTTPClientUtils.httpGetRequestJson(courseReq);
		logger.debug("资源库返回的数据：" + gsonResponse);
		return gsonResponse;
	}

	@Override
	public String getVideoExaminations(String subjectCode, List<ExaminationModel> examinationModels) throws Exception {
		String coursesReq = examinationsUrl;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectFlag", subjectCode);
		map.put("productAndSelectCountBeans", examinationModels);
		String data = gson.toJson(map);
		logger.info("data=====" + data);
		String gsonResponse= HTTPClientUtils.httpPostRequestJson(coursesReq, data);
		logger.debug("资源库返回的数据：" + gsonResponse);
		return gsonResponse;
	}

	
	
}
