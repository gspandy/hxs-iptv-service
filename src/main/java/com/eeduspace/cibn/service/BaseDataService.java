package com.eeduspace.cibn.service;

import java.util.List;

import com.eeduspace.cibn.model.BaseData;
import com.google.gson.JsonSyntaxException;

/**
 * @author songwei
 * Date 2016-05-16
 * Describe 资源库基础数据接口
 *
 */
public interface BaseDataService {
	
	public List<BaseData> getStageList() throws JsonSyntaxException;
	
	public List<BaseData> getGradeList(String stageCode) throws JsonSyntaxException;
	
	public List<BaseData> getSubjectList(String gradeCode) throws JsonSyntaxException;
	
	public List<BaseData> getBookTypeList(String gradeCode,String subjectCode) throws JsonSyntaxException;
	
	public List<BaseData> getBookTypeVersion(String gradeCode,String subjectCode,String bookTypeCode) throws JsonSyntaxException, Exception;
	
	public List<BaseData> getUnitList(String bookTypeVersionCode) throws JsonSyntaxException,Exception;
	
}
