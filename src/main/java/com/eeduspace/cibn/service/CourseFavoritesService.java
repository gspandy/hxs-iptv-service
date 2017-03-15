package com.eeduspace.cibn.service;



import org.springframework.data.domain.PageRequest;

import com.eeduspace.cibn.model.CourseFavoritesModel;
import com.eeduspace.cibn.persist.po.CourseFavorites;
import com.eeduspace.cibn.response.CourseFavoritesResponse;

/**
 * @author songwei
 * DATE 2016-05-16
 * Describe 收藏课程业务
 */
public interface CourseFavoritesService {
	
	public CourseFavoritesResponse getCourseFavoritesModels(CourseFavoritesModel cfModel,PageRequest pageable);
	
	/*public CourseFavoritesResponse getCourseFavoritesModel(CourseFavoritesModel courseFavoritesModel) throws JsonSyntaxException;*/
	
	public CourseFavoritesResponse getFavoriteOrNot(CourseFavoritesModel courseFavoritesModel);
	
	//查询该视频收藏状态(视频列表进入视频详情使用)
//	public Boolean favoriteState(String courseUuid,String userCode);
	//根据视频ID 和 用户 获取收藏视频
	public CourseFavorites findByCourseAndUserCode(String courseUuid,String userCode);
	
}
