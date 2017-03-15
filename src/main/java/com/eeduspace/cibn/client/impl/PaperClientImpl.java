package com.eeduspace.cibn.client.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.client.PaperClient;
import com.eeduspace.cibn.model.ExamDataDetailBeanForResponse;
import com.eeduspace.cibn.model.QuestionDataTemp;
import com.eeduspace.cibn.model.request.PaperRequest;
import com.eeduspace.cibn.model.response.AnaModel;
import com.eeduspace.cibn.model.response.PaperResponse;
import com.eeduspace.cibn.ws.PaperWs;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
/**
 * @author zhuchaowei
 * 2016年5月17日
 * Description
 */
@Service
public class PaperClientImpl implements PaperClient {
	@Value("${cibn.get_paper_url}")
	private String getPaperUrl;
	@Value("${cibn.get_paperlist_url}")
	private String getPaperListUrl;
	private Gson gson = new Gson();
	private final Logger logger = LoggerFactory.getLogger(PaperClientImpl.class);
	@Override
	public PaperResponse getPaper(PaperRequest paperRequest) throws ClientProtocolException, IOException,JsonSyntaxException {
		logger.debug("paperRequest:{}",gson.toJson(paperRequest));
		String resultString=HTTPClientUtils.httpPostRequestJson(getPaperListUrl, gson.toJson(paperRequest));
		logger.debug("PaperListInfo:{}",resultString);
		return  gson.fromJson(resultString, PaperResponse.class);
	}

	@Override
	public ExamDataDetailBeanForResponse getPaperInfo(String paperCode) throws JsonSyntaxException{
		logger.debug("paperCode:{}",paperCode);
		String resultString=HTTPClientUtils.httpGetRequestJson(getPaperUrl+paperCode);
		/*if (StringUtils.isNotBlank(resultString)) {
			Map<String,Object> map1 = gson.fromJson(resultString, new TypeToken<Map<String,Object>> () {}.getType());
			Object object = map1.get("questions");
			if (null != object) {
				String json = gson.toJson(object);
				List<Map<String,Object>> list1 = gson.fromJson(resultString, new TypeToken<List<Map<String,Object>>> () {}.getType());
				for (Map<String, Object> map : list1) {
					Object object2 = map.get("analyze");
					if (null != object2) {
						String json2 = gson.toJson(object2);
						List<Map<String,Object>> list2 = gson.fromJson(json2, new TypeToken<List<Map<String,Object>>> () {}.getType());
						for (Map<String, Object> map2 : list2) {
							Object object3 = map2.get("analyzeKey");
							if (null == object3) {
								map2.put("analyzeKey", "本题");
							}
						}
					}
				}
			}
		}*/
		logger.debug("PaperInfo:{}",resultString);
		ExamDataDetailBeanForResponse fromJson = gson.fromJson(resultString, ExamDataDetailBeanForResponse.class);
		logger.info("requestMode ===="  + gson.toJson(fromJson));
		return fromJson;
	}

}
