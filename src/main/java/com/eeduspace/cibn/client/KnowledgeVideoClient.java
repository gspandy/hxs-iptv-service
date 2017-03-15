package com.eeduspace.cibn.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.eeduspace.cibn.model.request.VideoRequest;
import com.eeduspace.cibn.model.response.VideoByKnowledge;
import com.google.gson.JsonSyntaxException;
/**
 * 获取知识点视频信息
 * @author zhuchaowei
 * 2016年5月17日
 * Description
 */
public interface KnowledgeVideoClient {
	public List<VideoByKnowledge> getVideo(VideoRequest videoRequest) throws JsonSyntaxException, ClientProtocolException, IOException;
}
