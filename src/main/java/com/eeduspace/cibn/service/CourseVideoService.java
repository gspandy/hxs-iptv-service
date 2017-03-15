package com.eeduspace.cibn.service;

import com.eeduspace.cibn.model.BaseDataModel;
import com.google.gson.JsonSyntaxException;

/**
 * @author songwei
 * Date 2016-05-16
 * Describe 课程视频业务数据
 */
public interface CourseVideoService {
	
	/**获取视频列表
	 * @param baseDataModel
	 * @return
	 */
	public BaseDataModel getCousreVideos(BaseDataModel baseDataModel);
	
	/**获取视频详情
	 * @param baseDataModel
	 * @return
	 */
	public BaseDataModel getCousreVideo(BaseDataModel baseDataModel);
	
	/**获取视频内，练习题列表（0-5道题）
	 * @param subjectCode
	 * @param productionCode
	 * @return
	 * @throws JsonSyntaxException
	 * @throws Exception
	 */
	public BaseDataModel getVideoExaminations(String subjectCode, String productionCode) throws JsonSyntaxException,Exception;
	
	
}
