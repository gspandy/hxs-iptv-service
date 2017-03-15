package com.eeduspace.cibn.service.impl;

import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.CourseFavoritesModel;
import com.eeduspace.cibn.persist.dao.CourseFavoritesDao;
import com.eeduspace.cibn.persist.po.CourseFavorites;
import com.eeduspace.cibn.response.CourseFavoritesResponse;
import com.eeduspace.cibn.service.CourseFavoritesService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author songwei
 * Date 2016-05-16
 * Describe 收藏课程视频业务实现
 */
@Service
public class CourseFavoritesServiceIpml implements CourseFavoritesService {
	private final Logger logger = LoggerFactory.getLogger(CourseFavoritesServiceIpml.class);
	private Gson gson = new Gson();

	@Inject
	private CourseFavoritesDao courseFavoritesDao;


	@Override
	public CourseFavoritesResponse getCourseFavoritesModels(CourseFavoritesModel cfModel, PageRequest pageable) {
		CourseFavoritesResponse courseFavoritesResponse = new CourseFavoritesResponse();
		boolean isDel = true;
		Page<CourseFavorites> courseFavoritesPage = courseFavoritesDao.findByIsDelAndUserCode(isDel,cfModel.getUserCode(),pageable);
		List<CourseFavorites> list=courseFavoritesPage.getContent();
		List<CourseFavoritesModel> cfModels = new ArrayList<CourseFavoritesModel>();
		if(list.size() > 0){
			for (CourseFavorites cfm : list) {
				cfModels.add(CIBNConvert.toCourseFavoritesModel(cfm));
			}
		}
		courseFavoritesResponse.setCourseFavoritesModels(cfModels);
		courseFavoritesResponse.setTotalPage(Long.valueOf(courseFavoritesPage.getTotalPages()));
		courseFavoritesResponse.setCp(courseFavoritesPage.getNumber());
		courseFavoritesResponse.setPageSize(courseFavoritesPage.getSize());
		courseFavoritesResponse.setTotalCount(courseFavoritesPage.getTotalElements());
		logger.debug("实现类返回数据：" + gson.toJson(courseFavoritesResponse));
		return courseFavoritesResponse;
	}

	/*@Override
	public CourseFavoritesResponse getCourseFavoritesModel(CourseFavoritesModel courseFavoritesModel) throws JsonSyntaxException {
		CourseFavoritesResponse courseFavoritesResponse = new CourseFavoritesResponse();
		BaseDataModel baseDataModel2 = new BaseDataModel();
		CourseFavorites courseFavorites =  this.findByCourseAndUserCode(courseFavoritesModel.getCourseId(), courseFavoritesModel.getUserCode());
		if(!org.springframework.util.StringUtils.isEmpty(courseFavorites)){
			//去资源库查询该视频的详情
			BaseDataModel baseDataModel = new BaseDataModel();
			baseDataModel.setCourseCode(courseFavorites.getCourseId());
			String gsonResponse =courseVideoClient.getCousreVideo(baseDataModel);
			if(StringUtils.isNotBlank(gsonResponse)){
				baseDataModel2 = gson.fromJson(gsonResponse, BaseDataModel.class);
				//组装收藏状态
				baseDataModel2.setIsDel(courseFavorites.isDel());
				//添加收藏基本教材信息
				baseDataModel2.setStageCode(courseFavorites.getStageCode());
				baseDataModel2.setGradeCode(courseFavorites.getGradeCode());
				baseDataModel2.setSubjectCode(courseFavorites.getSubjectCode());
				baseDataModel2.setBookTypeCode(courseFavorites.getBookTypeCode());
				baseDataModel2.setBookTypeVersionCode(courseFavorites.getBookTypeVersionCode());
			}
		}
		courseFavoritesResponse.setBaseDataModel(baseDataModel2);
		logger.debug("实现类返回数据：" +gson.toJson(courseFavoritesResponse));
		return courseFavoritesResponse;
	}*/

	@Override
	public CourseFavoritesResponse getFavoriteOrNot(CourseFavoritesModel courseFavoritesModel) {
		CourseFavoritesResponse courseFavoritesResponse = new CourseFavoritesResponse();
		if(!org.springframework.util.StringUtils.isEmpty(courseFavoritesModel)){
			CourseFavoritesModel courseFavoritesModel2 = new CourseFavoritesModel();
			CourseFavorites courseFavorites1= courseFavoritesDao.findByCourseIdAndUserCode(courseFavoritesModel.getCourseId(),courseFavoritesModel.getUserCode());
			CourseFavorites courseFavorites2 = new CourseFavorites();
			if(null==courseFavorites1){
				courseFavoritesModel.setIsDel(courseFavoritesModel.getIsDel());
                courseFavoritesModel.setUpdateDate(new Date());
				courseFavorites2=courseFavoritesDao.save(CIBNConvert.toCourseFavorites(courseFavoritesModel));
			}else{
				courseFavorites1.setDel(courseFavoritesModel.getIsDel());
				courseFavorites1.setUpdateDate(new Date());
				courseFavorites2 = courseFavoritesDao.save(courseFavorites1);
			}
			courseFavoritesModel2 = CIBNConvert.toCourseFavoritesModel(courseFavorites2);
			Boolean flag  = false;
			if (null!=courseFavoritesModel2.getIsDel()) {
				flag = courseFavoritesModel2.getIsDel();
			}
			courseFavoritesResponse.setFlag(flag);
		}
		logger.debug("实现类返回数据：" + gson.toJson(courseFavoritesResponse));
		return courseFavoritesResponse;
	}

	@Override
	public CourseFavorites findByCourseAndUserCode(String courseUuid, String userCode) {
		return courseFavoritesDao.findByCourseIdAndUserCode(courseUuid,userCode);
	}

}
