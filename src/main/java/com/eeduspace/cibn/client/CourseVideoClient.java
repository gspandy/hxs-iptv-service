package com.eeduspace.cibn.client;

import java.util.List;

import com.eeduspace.cibn.model.BaseDataModel;
import com.eeduspace.cibn.model.ExaminationModel;

/**
 * @author songwei
 * Date 2016-05-16
 * Describe 课程视频业务数据
 */
public interface CourseVideoClient {
	
	public String getCousreVideos(BaseDataModel baseDataModel);
	
	public String getCousreVideo(BaseDataModel baseDataModel);
	
	public String getVideoExaminations(String subjectCode, List<ExaminationModel> examinationModels) throws Exception;
	
	
}
