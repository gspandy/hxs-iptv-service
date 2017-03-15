package com.eeduspace.cibn.user;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eeduspace.cibn.model.request.PaperRequest;
import com.eeduspace.cibn.model.response.PaperResponse;
import com.eeduspace.cibn.ws.UserWs;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;

public class PaperTest {
	private final Logger logger = LoggerFactory.getLogger(UserWs.class);
	@Test
	public void paperList() {
		//paperRequest:{"gradeCode":"14","subjectCode":"2","booktype":"e1fb7696c0ad4431a791a942aba2898e","volume":"66670008","type":"单元测试","pageSize":1,"cp":0}

		//{"gradeCode":"12","subjectCode":"2","booktype":"cec1737076684cfc8104adf75f6b7cd9","volume":"66590008","type":"单元测试","pageSize":1,"cp":0
		//{"gradeCode":"11","subjectCode":"2","booktype":"170a286029b24ed78626c5203fc1c816","volume":"66570002","type":"单元测试","pageSize":1,"cp":0}
		//b3dbb6ce6acd4035b5108548add0801b
		String userCodeString="794f76b7b08a4056ace3ad9c9a4ed2cb";
		String urlString="http://101.200.155.215:8180/cibnws/paper/get_paper/";
		logger.info("result{}---->"+HTTPClientUtils.httpGetRequestJson(urlString+userCodeString+"/14/2/e1fb7696c0ad4431a791a942aba2898e/66670008/0/10"));
	}

	@Test
	public void paperDetail() {
		String paperCodeString="8a2a746854604d34015460ccd59300e3";
		String urlString="http://101.200.155.215:8180/cibnws/paper/get_subject/";
		logger.info("result{}---->"+HTTPClientUtils.httpGetRequestJson(urlString+paperCodeString));
	}
	
	@Test
	public void getPaper(){
		Gson gson=new Gson();
		String getPaperListUrl="http://192.168.1.161:8080/llsfw/paperController/rest/getPapersForIdAndNames";
		PaperRequest paperRequest=new PaperRequest("14", "2", "e1fb7696c0ad4431a791a942aba2898e", "66670008", "单元测试", 1, 0);
		logger.debug("paperRequest:{}",gson.toJson(paperRequest));
		String resultString;
		try {
			resultString = HTTPClientUtils.httpPostRequestJson(getPaperListUrl, gson.toJson(paperRequest));
			logger.debug("PaperListInfo:{}",resultString);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
