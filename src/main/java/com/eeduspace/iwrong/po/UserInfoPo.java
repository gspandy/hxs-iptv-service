package com.eeduspace.iwrong.po;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.UUID;

/**
 * 用户基础类
 * @author libinghua
 *
 */
@Entity
@Table(name="userinfo")
public class UserInfoPo{

    protected Long id;
    protected String code = UUID.randomUUID().toString().replace("-", "");
    protected long createAt = System.currentTimeMillis();
    protected String create_author;
    protected long update_at = System.currentTimeMillis();
    protected String update_author;

	private String email;
	private String mobile;
	private String userName;
	private String nickName;
	private String password;
	private String realName;
	private int sex=0;   //性别 0男1女
	private String provinceCode;
	private String cityCode;
	private String areaCode;
	private String stageCode;
	private String gradeCode;   //年级标识
	private String schoolCode;
	private String classCode;
	private int registerSource;  //注册来源6:web端,7:android端,8:ios,0:开发测试
	private int type;  //用户类别:6自己的用户,7电信用户,8移动用户,0开发测试假数据
	private int status=0;  //0正常,1禁用...
	private int version=0;  //数据版本
	private String imagePath;   //头像路径
	private String cardId;      //身份证号码
	private int bdStatus=0;    //绑定状态0未绑定,1绑定手机,2绑定邮箱,3全部绑定
	
	public UserInfoPo() {
		super();
	}

	public UserInfoPo(String mobile, String password, int registerSource,
                      int type) {
		super();
		this.mobile = mobile;
		this.password = password;
		this.registerSource = registerSource;
		this.type = type;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(nullable=false)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Column(nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	
	@Column(nullable=false)
	public int getRegisterSource() {
		return registerSource;
	}
	public void setRegisterSource(int registerSource) {
		this.registerSource = registerSource;
	}
	
	@Column(nullable=false)
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	
	public int getBdStatus() {
		return bdStatus;
	}

	public void setBdStatus(int bdStatus) {
		this.bdStatus = bdStatus;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false,name="ctb_code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Column(nullable=false,name="ctb_create_at")
    public long getCreateAt() {
        return createAt;
    }
    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    @Column(nullable = false,name="ctb_update_at")
    public long getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(long update_at) {
        this.update_at = update_at;
    }
    @Column(name="ctb_create_author")
    public String getCreate_author() {
        return create_author;
    }

    public void setCreate_author(String create_author) {
        this.create_author = create_author;
    }
    @Column(name="ctb_update_author")
    public String getUpdate_author() {
        return update_author;
    }

    public void setUpdate_author(String update_author) {
        this.update_author = update_author;
    }
}
