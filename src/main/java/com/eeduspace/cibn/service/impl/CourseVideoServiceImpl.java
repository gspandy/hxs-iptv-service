package com.eeduspace.cibn.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.client.CourseVideoClient;
import com.eeduspace.cibn.model.BaseDataModel;
import com.eeduspace.cibn.model.ExaminationModel;
import com.eeduspace.cibn.model.QuesAnalyzeModel;
import com.eeduspace.cibn.model.QuesOptionModel;
import com.eeduspace.cibn.model.QuestionModel;
import com.eeduspace.cibn.persist.po.CourseFavorites;
import com.eeduspace.cibn.service.CourseFavoritesService;
import com.eeduspace.cibn.service.CourseVideoService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * @author songwei
 * Date 2016-05-16
 * Describe 课程视频业务数据实现
 */
@Service
public class CourseVideoServiceImpl implements CourseVideoService {
	
	private final Logger logger = LoggerFactory.getLogger(CourseVideoServiceImpl.class);
	private Gson gson = new Gson();
	
	@Inject
	private CourseVideoClient courseVideoClient;
	@Inject
	private CourseFavoritesService courseFavoritesService;

	@Override
	public BaseDataModel getCousreVideos(BaseDataModel baseDataModel) throws JsonSyntaxException{
		String gsonResponse =  courseVideoClient.getCousreVideos(baseDataModel);
		BaseDataModel baseDataModel2 = new BaseDataModel();
		if(StringUtils.isNotBlank(gsonResponse)){
			baseDataModel2 = gson.fromJson(gsonResponse, BaseDataModel.class);
			baseDataModel2.setUserCode(baseDataModel.getUserCode());
			baseDataModel2.setCourseCode(baseDataModel.getCourseCode());
			baseDataModel2.setProductionCode(baseDataModel.getProductionCode());
		}
		logger.debug("实现类返回数据：" + gson.toJson(baseDataModel2));
		return baseDataModel2;
	}

	@Override
	public BaseDataModel getCousreVideo(BaseDataModel baseDataModel) {
		String gsonResponse = courseVideoClient.getCousreVideo(baseDataModel); 
		BaseDataModel baseDataModel2 = new BaseDataModel();
		if(StringUtils.isNotBlank(gsonResponse)){
			baseDataModel2 = gson.fromJson(gsonResponse, BaseDataModel.class);
			baseDataModel2.setUserCode(StringUtils.isBlank(baseDataModel.getUserCode()) ? null : baseDataModel.getUserCode());
			baseDataModel2.setCourseCode(baseDataModel.getCourseCode());
			baseDataModel2.setProductionCode(baseDataModel.getProductionCode());
		}
		boolean flag = false;
		CourseFavorites courseFavorites = courseFavoritesService.findByCourseAndUserCode(baseDataModel.getCourseCode(), baseDataModel.getUserCode());
		if (!org.springframework.util.StringUtils.isEmpty(courseFavorites)) {
			flag = courseFavorites.isDel();
			//添加收藏基本教材信息
			baseDataModel2.setStageCode(courseFavorites.getStageCode());
			baseDataModel2.setGradeCode(courseFavorites.getGradeCode());
			baseDataModel2.setSubjectCode(courseFavorites.getSubjectCode());
			baseDataModel2.setBookTypeCode(courseFavorites.getBookTypeCode());
			baseDataModel2.setBookTypeVersionCode(courseFavorites.getBookTypeVersionCode());
		}
		baseDataModel2.setIsDel(flag);
		logger.debug("实现类返回数据：" + gson.toJson(baseDataModel2));
		return baseDataModel2;
	}

	@Override
	public BaseDataModel getVideoExaminations(String subjectCode, String productionCode) throws JsonSyntaxException,Exception {
		String gsonResponse=courseVideoClient.getVideoExaminations(subjectCode, this.strToList(productionCode));
		BaseDataModel dataModel = new BaseDataModel();
		if(StringUtils.isNotBlank(gsonResponse)){
			BaseDataModel baseDataModel2 =  gson.fromJson(gsonResponse, BaseDataModel.class);
			List<QuestionModel> questionModels = baseDataModel2.getQuestionObject();
			List<ExaminationModel> questionList = new ArrayList<ExaminationModel>();
			//重新组装questionList集合，去掉一层json，供前台使用
			for (QuestionModel qm : questionModels) {
				qm.setProductCode(null);
				for (ExaminationModel em : qm.getQuestionList()) {
					em.setUrlWeVideo(baseDataModel2.getUrlWeVideo());
					questionList.add(em);
				}
			}
			//把资源库返回的string类型数据，转成json格式数据，组装后给前台使用
			for (int i = 0; i < questionList.size(); i++) {
				List<QuesOptionModel> qoList =  new ArrayList<QuesOptionModel>();
				List<QuesAnalyzeModel> qaList = new ArrayList<QuesAnalyzeModel>();
				qoList = gson.fromJson(questionList.get(i).getQuesOption(), new TypeToken<List<QuesOptionModel>>(){}.getType());
				qaList = gson.fromJson(questionList.get(i).getQuesAnalyze(), new TypeToken<List<QuesAnalyzeModel>>(){}.getType());
				for (QuesAnalyzeModel quesAnalyzeModel : qaList) {
					if (null == quesAnalyzeModel.getAnalyzeKey() || quesAnalyzeModel.getAnalyzeKey().equals("")) {
						quesAnalyzeModel.setAnalyzeKey("本题");
					}
				}
				questionList.get(i).setQuesOptions(qoList);
				questionList.get(i).setQuesAnalyzes(qaList);
				questionList.get(i).setQuesOption(null);
				questionList.get(i).setQuesAnalyze(null);
			}
			dataModel.setQuestions(new ArrayList<ExaminationModel>(new HashSet<ExaminationModel>(questionList)));
		}
		logger.debug("实现类返回数据：" + gson.toJson(dataModel));
		return dataModel;
	}
	/**
	 * 分离产生式为list集合，重组参数
	 * */
	private List<ExaminationModel> strToList(String productionCode){
		String[] array = productionCode.split(",");
		List<ExaminationModel> examinations = new ArrayList<ExaminationModel>();
		List<String> strs = new ArrayList<String>(Arrays.asList(array));
		if(strs.size() > 0){
			for (int i = 0; i < (strs.size() >= 5 ? 5 : strs.size()); i++) {
				ExaminationModel exam = new ExaminationModel(); 
				exam.setProductCode(strs.get(i));
				if(strs.size() < 2){
					exam.setSelectCount(3);
				}else if(strs.size() < 3){
					exam.setSelectCount(2);
				}else{
					exam.setSelectCount(1);
				}
				examinations.add(exam);
			}
		}
		return examinations;
	}

}
