package com.eeduspace.cibn.basedata;

import java.io.IOException;

import org.junit.Test;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;

public class BaseDataTest extends BaseTest {
	
	@Test
	public void testStage(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/baseData/stage";
//		String url = "http://101.200.155.215:8180/cibnws/baseData/stage";
		String data = "";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGrade(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/baseData/grade";
//		String url ="http://101.200.155.215:8180/cibnws/baseData/grade";
		String data = "{'stageCode':'1'}";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testSubject(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/baseData/subject";
//		String url ="http://101.200.155.215:8180/cibnws/baseData/subject";
		String data = "{'gradeCode':'11'}";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testBookType(){
//		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/baseData/bookType";
//		String url ="http://101.200.155.215:8180/cibnws/baseData/bookType";
		String url = "http://192.168.1.12:8080/cibnws/baseData/bookType"; 
		String data = "{'gradeCode':'11','subjectCode':'2'}";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testBookTypeVersion(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/baseData/bookTypeVersion";
//		String url ="http://101.200.155.215:8180/cibnws/baseData/bookTypeVersion";
		String data = "{'gradeCode':'11','subjectCode':'2','bookTypeCode':'170a286029b24ed78626c5203fc1c816'}";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testUnit(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/baseData/unit";
//		String url ="http://101.200.155.215:8180/cibnws/baseData/unit";
		String data = "{'bookTypeVersionCode':'6658'}";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		BaseDataTest baseDataTest = new BaseDataTest();
		baseDataTest.testBookType();
	}
}
