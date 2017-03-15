package com.eeduspace.cibn.courseFavorites;

import java.io.IOException;

import org.junit.Test;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.cibn.model.BaseDataModel;
import com.eeduspace.cibn.model.CourseFavoritesModel;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:
 */
public class CourseFavoritesTest extends BaseTest {

	@Test
	public void testList(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/courseFavorites/favoriteList";
		String data = "{'userCode':'ca2c7e79c9504b76b054559bf8360a3e','pageable':{'size':100,'page':0}}";
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug("返回的结果为：" + response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testFavorite(){
		String url = "http://localhost:8080/haoxuesheng-tv/cibnws/courseVideo/course";
		String data = "{'courseCode':'qqqqqqqqqqqqqqqqqqqqqqqqq','userCode':'d8f0ab433a064dba876003f928323e71'}";
		try { 
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug("返回的结果为：" + response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testFavoriteOrNot(){ 
		String url = "http://localhost:8083/haoxuesheng-tv/cibnws/courseFavorites/favoriteOrNot";
//		String url = "http://192.168.1.12:8080/cibnws/courseFavorites/favoriteOrNot";
		CourseFavoritesModel baseDataModel = new CourseFavoritesModel();
		baseDataModel.setUserCode("d8f0ab433a064dba876003f928323e71");
		baseDataModel.setCourseName("测试数据请勿查询");
		baseDataModel.setCourseId("qqqqqqqqqqqqqqqqqqqqqqqqq");
		baseDataModel.setIsDel(false);
		baseDataModel.setStageCode("1");
		baseDataModel.setGradeCode("1");
		baseDataModel.setSubjectCode("1");
		baseDataModel.setBookTypeCode("1");
		baseDataModel.setBookTypeVersionCode("1");
		baseDataModel.setUnitCode("1");
		String data = gson.toJson(baseDataModel);
		try {
			String response = HTTPClientUtils.httpPostRequestJson(url, data);
			logger.debug("返回的结果为：" + response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		CourseFavoritesTest cft = new CourseFavoritesTest();
		cft.testList();
	}


}

