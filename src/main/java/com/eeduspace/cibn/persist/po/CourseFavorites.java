package com.eeduspace.cibn.persist.po;

import com.eeduspace.uuims.comm.util.base.UIDGenerator;

import javax.persistence.*;
import java.util.Date;
/**
 * @author zhuchaowei
 * 2016年4月19日
 * Description 课程收藏
 */
@Entity
@Table(name = "cibn_course_favorites")
public class CourseFavorites {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	// 唯一标识
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	/**
	 * 人员UUID
	 */
	@Column(name = "user_code")
	private String userCode;
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "create_time")
	private Date createDate = new Date();
	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, name = "update_time")
	private Date updateDate = new Date();	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 课程ID
	 */
	@Column(name = "course_id")
	private String courseId;
	/**
	 * 课程名称
	 */
	@Column(name = "course_name")
	private String courseName;
	/**
	 * 删除标识
	 */
	@Column(name = "is_del")
	private boolean isDel=false;
	/**
	 * 产生式
	 * */
	@Column(name = "production")
	private String production;
	/**
	 * 单元code
	 * */
	@Column(name = "unit_code")
	private String unitCode;


    //TODO 添加新属性
	/**
	 * 学段名称
	 */
	@Column(name = "stage_name")
	private String stageName;
	/**
	 * 学年名称
	 */
	@Column(name = "grade_name")
	private String gradeName;
	/**
	 * 学科名称
	 */
	@Column(name = "subject_name")
	private String subjectName;
	/**
	 * 教材版本名称
	 */
	@Column(name = "book_type_name")
	private String bookTypeName;
    /**
     * 学段code
     * */
    @Column(name = "stage_code")
    private String stageCode;
    /**
     * 学年code
     * */
    @Column(name = "grade_code")
    private String gradeCode;
    /**
     * 学科code
     * */
    @Column(name = "subject_code")
    private String subjectCode;
    /**
     * 教材code
     * */
    @Column(name = "book_type_code")
    private String bookTypeCode;
    /**
     * 教材版本上下册code
     * */
    @Column(name = "book_type_version_code")
    private String bookTypeVersionCode;
    @Column(name = "book_type_version_name")
    private String bookTypeVersionName;
    public String getBookTypeVersionName() {
		return bookTypeVersionName;
	}
	public void setBookTypeVersionName(String bookTypeVersionName) {
		this.bookTypeVersionName = bookTypeVersionName;
	}
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public boolean isDel() {
		return isDel;
	}
	public void setDel(boolean isDel) {
		this.isDel = isDel;
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
}
