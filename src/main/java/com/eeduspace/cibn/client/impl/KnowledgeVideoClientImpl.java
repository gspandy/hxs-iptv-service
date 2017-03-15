package com.eeduspace.cibn.client.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.client.KnowledgeVideoClient;
import com.eeduspace.cibn.model.request.VideoRequest;
import com.eeduspace.cibn.model.response.VideoByKnowledge;
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
public class KnowledgeVideoClientImpl implements KnowledgeVideoClient{
	@Value("${cibn.video_by_knowledge}")
	private String videoByKnowledge;
	private Gson gson=new Gson();
	private final  Logger logger = LoggerFactory.getLogger(KnowledgeVideoClientImpl.class);
	@Override
	public List<VideoByKnowledge> getVideo(VideoRequest videoRequest) throws JsonSyntaxException, ClientProtocolException, IOException {
		logger.debug("videoRequest:{}",gson.toJson(videoRequest));
		String resultString=HTTPClientUtils.httpPostRequestJson(videoByKnowledge, gson.toJson(videoRequest));
		logger.debug("getVideo:{}",resultString);
		return gson.fromJson(resultString, new TypeToken<List<VideoByKnowledge>>(){}.getType());
	}

}
