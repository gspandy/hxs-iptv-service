package com.eeduspace.cibn.courseVideo;

import org.junit.Test;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;

public class CourseVideoTest extends BaseTest {
	
	@Test
	public void testCourses(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/courseVideo/courses";
//		String url ="http://101.200.155.215:8180/cibnws/courseVideo/courses";
		String data = "{'unitCode':'66580001'}";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCourse(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/courseVideo/course";
//		String url ="http://101.200.155.215:8180/cibnws/courseVideo/course";
//		String url = "http://192.168.1.12:8080/cibnws/courseVideo/course";
		String data = "{'courseCode':'2dd8e82d70bb495faad19d23986e4c85','userCode':'d8f0ab433a064dba876003f928323e71'}";
		String response;
		try {
			response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testExaminations(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/courseVideo/examinations";
//		String url = "http://192.168.1.12:8080/cibnws/courseVideo/examinations";
//		String url ="http://101.200.155.215:8180/cibnws/courseVideo/examinations";
		String data = "{'subjectCode':'2','productionCode':'00170004000100050001,0017000400050001,0019000200020001'}";
		String response;
		try {
			response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CourseVideoTest webVideoTest = new CourseVideoTest();
		webVideoTest.testCourse();
	}

}
