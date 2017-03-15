package com.eeduspace.cibn.persist.po;

import com.eeduspace.cibn.persist.enumeration.UserEnum;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:
 */
@Entity
@Table(name="cibn_user")
public class UserPo  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long id;
    @Column(name = "user_code")
    private String userCode;//用户唯一标识
    private String email;
    private String mobile;
    @Column(name = "user_name")
    private String userName;
    private String password;
    @Column(name = "is_vip")
    private boolean isVIP;//是否是vip
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "vip_expire_time")
    private Date  VIPExpireTime;//vip到期时间
    /**TODO 后期需要按照该标识统一
     * 注册为来源
     011 好学生_小学版
     012 好学生_初中版
     013 好学生_高中版
     009 CIBN_好学生
     010 好学生_TV
     014 测试用户
     015 好学生
     016 UUIMS
     */
    @Column(name="register_source")
    private String registerSource;
    /**
     * 市场渠道
     */
    @Column(name="market_channel")
    private String marketChannel;
    /**
     * 注册设备类型   IOS  WEB ANDroid TV
     */
    @Column(name="register_device_type")
    private String registerDeviceType;
    
    public String getRegisterDeviceType() {
		return registerDeviceType;
	}

	public void setRegisterDeviceType(String registerDeviceType) {
		this.registerDeviceType = registerDeviceType;
	}

	public String getMarketChannel() {
		return marketChannel;
	}

	public void setMarketChannel(String marketChannel) {
		this.marketChannel = marketChannel;
	}

	@Column(name = "login_status")
    private UserEnum.LoginStatus loginStatus;

/*    @Column(name = "scan_status")
    private UserEnum.ScanStatus scanStatus;*/

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false,name = "create_time")
    private Date createDate = new Date();
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateDate = new Date();
    @Column(name = "access_key")
    private String accessKey;
    @Column(name = "secret_key")
	private String secretKey;
	@Column(name = "open_id")
	private String openId;
    
    
    public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getRegisterSource() {
		return registerSource;
	}

	public void setRegisterSource(String registerSource) {
		this.registerSource = registerSource;
	}
	
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }

    public Date getVIPExpireTime() {
        return VIPExpireTime;
    }

    public void setVIPExpireTime(Date VIPExpireTime) {
        this.VIPExpireTime = VIPExpireTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public UserEnum.LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(UserEnum.LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }
    
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
