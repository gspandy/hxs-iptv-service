package com.eeduspace.cibn.client.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.client.BaseDataClient;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;

/**
 * @author songwei
 * Date 2016-05-16
 * Describe 资源库基础数据接口实现
 *
 */
@Service
public class BaseDataClientImpl implements BaseDataClient {
	private final Logger logger = LoggerFactory.getLogger(BaseDataClientImpl.class);
	private Gson gson = new Gson();
	@Value("${cibn.stage.url}")
	private String stageUrl;
	@Value("${cibn.grade.url}")
	private String gradeUrl;
	@Value("${cibn.subject.url}")
	private String subjectUrl;
	@Value("${cibn.bookType.url}")
	private String bookTypeUrl;
	@Value("${cibn.bookTypeVersion.url}")
	private String bookTypeVersionUrl;
	@Value("${cibn.unit.url}")
	private String unitUrl;

	@Override
	public String getStageList(){
		String urlReq = stageUrl;
		String gsonResponse = HTTPClientUtils.httpGetRequestJson(urlReq);
		logger.debug("资源库返回数据：" + gsonResponse);
		return gsonResponse;
	}

	@Override
	public String getGradeList (String stageCode) {
		String urlReq = MessageFormat.format(gradeUrl, stageCode);
		String gsonResponse = HTTPClientUtils.httpGetRequestJson(urlReq);
		logger.debug("资源库返回数据：" + gsonResponse);
		return gsonResponse;
	}

	@Override
	public String getSubjectList (String gradeCode) {
		String urlReq = MessageFormat.format(subjectUrl,gradeCode);
		String gsonResponse = HTTPClientUtils.httpGetRequestJson(urlReq);
		logger.debug("资源库返回数据：" + gsonResponse);
		return gsonResponse;
	}

	@Override
	public String getBookTypeList (String gradeCode, String subjectCode) {
		String urlReq = MessageFormat.format(bookTypeUrl, gradeCode, subjectCode); 
		String gsonResponse = HTTPClientUtils.httpGetRequestJson(urlReq);
		logger.debug("资源库返回数据：" + gsonResponse);
		return gsonResponse;
	}

	@Override
	public String getBookTypeVersion (String gradeCode, String subjectCode, String bookTypeCode) throws Exception {
		String urlReq = bookTypeVersionUrl; 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectCode", gradeCode);
		map.put("gradeCode", subjectCode);
		map.put("booktypeCode", bookTypeCode);
		String gsonResponse = HTTPClientUtils.httpPostRequestJson(urlReq, gson.toJson(map));
		logger.debug("资源库返回数据：" + gsonResponse);
		return gsonResponse;
	}

	@Override
	public String getUnitList (String bookTypeVersionCode){
		String urlReq = MessageFormat.format(unitUrl, bookTypeVersionCode);
		String gsonResponse = HTTPClientUtils.httpGetRequestJson(urlReq);
		logger.debug("资源库返回数据：" + gsonResponse);
		return gsonResponse;
	}

}
