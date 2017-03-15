package com.eeduspace.cibn.model.request;

import java.io.Serializable;

public class UserRequestModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String password;
    private String phone;
	private String openId;
    //注册设备类型
	private String equipmentType;
    //产品类型
	private String productType;
	private String remoteAddress;
	private String token;
	private String refreshToken;
    //用户名（手机号）
	private String userName;
	private String oldPassword;
	private String userAccessKey;
	private String userSecretKey;
	private String type;
	private String email;
	private String userCode;
    private String friendCode;

    //好学生对战版自身PO 实体需要数据
    private String mobile;
    private String channelSource;//市场渠道来源
    private String registerSource;//注册来源 011 好学生_小学版 012 好学生_初中版 013好学生_高中版 009CIBN_好学生 010好学生_TV 014测试用户
    private String systemVersion;//系统版本

    private String school;
    private String headImgUrl;
    private Integer sex;
    private String nickName;
    private String birthday;
    private String grade;
    private String province;
    private String city;
    private String area;
    private String country;
    //ticket令牌
    private String ticket;
    //验证码
    private String code;
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRemoteAddress() {
		return remoteAddress;
	}
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getUserAccessKey() {
		return userAccessKey;
	}
	public void setUserAccessKey(String userAccessKey) {
		this.userAccessKey = userAccessKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserSecretKey() {
		return userSecretKey;
	}
	public void setUserSecretKey(String userSecretKey) {
		this.userSecretKey = userSecretKey;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(String channelSource) {
        this.channelSource = channelSource;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }
	public String getTicket() {
	
		return ticket;
	}
	public void setTicket(String ticket) {
	
		this.ticket = ticket;
	}
	public String getCode() {
	
		return code;
	}
	public void setCode(String code) {
	
		this.code = code;
	}
	@Override
	public String toString() {
		return "UserRequestModel [password=" + password + ", phone=" + phone
				+ ", openId=" + openId + ", equipmentType=" + equipmentType
				+ ", productType=" + productType + ", remoteAddress="
				+ remoteAddress + ", token=" + token + ", refreshToken="
				+ refreshToken + ", userName=" + userName + ", oldPassword="
				+ oldPassword + ", userAccessKey=" + userAccessKey
				+ ", userSecretKey=" + userSecretKey + ", type=" + type
				+ ", email=" + email + ", userCode=" + userCode
				+ ", friendCode=" + friendCode + ", mobile=" + mobile
				+ ", channelSource=" + channelSource + ", registerSource="
				+ registerSource + ", systemVersion=" + systemVersion
				+ ", school=" + school + ", headImgUrl=" + headImgUrl
				+ ", sex=" + sex + ", nickName=" + nickName + ", birthday="
				+ birthday + ", grade=" + grade + ", province=" + province
				+ ", city=" + city + ", area=" + area + ", country=" + country
				+ ", ticket=" + ticket + ", code=" + code + "]";
	}
	
}
