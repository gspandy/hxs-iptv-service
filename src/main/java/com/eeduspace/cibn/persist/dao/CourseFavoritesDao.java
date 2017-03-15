package com.eeduspace.cibn.persist.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.CourseFavorites;

/**
 * @author songwei
 *	Date 2016-04-19
 *	Describe 收藏dao层
 */
public interface CourseFavoritesDao extends CrudRepository<CourseFavorites, Long> {
	
	/**根据用户code查找收藏视频列表
	 * @param isDel 收藏状态0-已收藏  1-未收藏
	 * @param userCode 用户Code
	 * @param pageable 分页
	 * @return
	 */
	Page<CourseFavorites> findByIsDelAndUserCode(boolean isDel,String userCode,Pageable pageable);
	
	/**根据视频code和用户code查询收藏课程详情
	 * @param courseId 课程UUID
	 * @param userCode 用户Code
	 * @return
	 */
	CourseFavorites findByCourseIdAndUserCode(String courseId,String userCode);
	
	/**根据视频code，用户code和收藏状态来添加更新收藏视频
	 * @param courseId  课程UUID
	 * @param userCode  用户Code
	 * @param isDel  视频收藏状态
	 * @return
	 */
	CourseFavorites findByCourseIdAndUserCodeAndIsDel(String courseId,String userCode,boolean isDel);
	
}