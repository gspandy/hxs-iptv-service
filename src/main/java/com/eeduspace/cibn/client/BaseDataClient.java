package com.eeduspace.cibn.client;


/**
 * @author songwei
 * Date 2016-05-16
 * Describe 资源库基础数据接口
 *
 */
public interface BaseDataClient {
	
	public String getStageList();
	
	public String getGradeList(String stageCode);
	
	public String getSubjectList(String gradeCode);
	
	public String getBookTypeList(String gradeCode,String subjectCode);
	
	public String getBookTypeVersion(String gradeCode,String subjectCode,String bookTypeCode) throws Exception;
	
	public String getUnitList(String bookTypeVersionCode);
	
}
