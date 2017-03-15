package com.eeduspace.cibn.model;

import org.springframework.data.domain.PageRequest;

import java.util.Date;

import javax.persistence.Column;


/**
 * @author songwei
 *	Date 2016-04-09
 *	Describe 收藏Model
 */
public class CourseFavoritesModel {
	
	//收藏Id
	private Long id;
	//用户code
	private String userCode;
	//创建时间
	private Date createDate = new Date() ;
	private Date updateDate;
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	//课程视频的code
	private String courseId;
	//课程视频名称
	private String courseName = null;
	//收藏状态（0-未收藏  1-已收藏 ）
	private Boolean isDel;
	//收藏uuid
	private String uuid ;
	//分页
	private PageRequest pageable;
	//产生式
	private String production = null;
	//单元code
	private String unitCode = null;

    //TODO 添加新属性
    //学段code
    private String stageCode= null;
    //学年code
    private String gradeCode= null;
    //学科code
    private String subjectCode= null;
    //教材code
    private String bookTypeCode= null;
    /**
	 * 学段名称
	 */
	private String stageName;
	/**
	 * 学年名称
	 */
	private String gradeName;
	/**
	 * 学科名称
	 */
	private String subjectName;
	/**
	 * 教材版本名称
	 */
	private String bookTypeName;
	/**
	 * 教材版本上下册
	 */
    private String bookTypeVersionName;
    public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getBookTypeName() {
		return bookTypeName;
	}
	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	//教材版本上下册code
    private String bookTypeVersionCode= null;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Boolean getIsDel() {
		return isDel;
	}
	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}
	public PageRequest getPageable() {
		return pageable;
	}
	public void setPageable(PageRequest pageable) {
		this.pageable = pageable;
	}
	public String getProduction() {
		return production;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

    public String getStageCode() {
        return stageCode;
    }

    public void setStageCode(String stageCode) {
        this.stageCode = stageCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getBookTypeVersionCode() {
        return bookTypeVersionCode;
    }

    public void setBookTypeVersionCode(String bookTypeVersionCode) {
        this.bookTypeVersionCode = bookTypeVersionCode;
    }
	public String getBookTypeVersionName() {
		return bookTypeVersionName;
	}
	public void setBookTypeVersionName(String bookTypeVersionName) {
		this.bookTypeVersionName = bookTypeVersionName;
	}
}
