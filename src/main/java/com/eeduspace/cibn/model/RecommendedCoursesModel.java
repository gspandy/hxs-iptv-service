package com.eeduspace.cibn.model;
/**
 * @author zhuchaowei
 * 2016年4月20日
 * Description  推荐课程
 */
public class RecommendedCoursesModel {
	/**
	 * 推荐课程网址
	 */
	private String video_url;
	/**
	 * 推荐课程名字
	 */
	private String video_name;
	/**
	 * 课程视频ID
	 */
	private String courseId;
	
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getVideo_name() {
		return video_name;
	}
	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}
