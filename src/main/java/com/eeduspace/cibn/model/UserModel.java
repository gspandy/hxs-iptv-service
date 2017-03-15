package com.eeduspace.cibn.model;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description: 
 */
public class UserModel {
	/**
	 * 市场来源
	 */
    private String marketChannel;

	private String password;
	/**
	 * 注册来源  9 表示来源于tv
	 */
	private int registerSource;
	/**
	 * 用户类型  9 表示CIBNTV用户
	 */
	private int type;
	private String oldPassword;
	
	private String registerDeviceType;

	private String newPwd;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * 邮箱
	 */
	private String email;
	private String userCode;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 是否VIP
	 */
	private boolean isVip;
	/**
	 * VIP到期时间
	 */
	private String VIPExpireTime;

    /**
     * VIP到期天数
     */
    private Long VIPExpireDays;
	/**
	 * VIP是否过期  true 过期  false 未过期
	 */
	private boolean isOverdue;

    /**
     * 登录时的扫码状态
     */
    private String scanStatus;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * token 令牌
     */
    private String token;

    /**
     * token 刷新令牌
     */
    private String refreshToken;
    /**
     * token 令牌失效时间
     */
    private String expires;
    /**
     * 请求IP地址
     */
    private String ip;

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isVip() {
		return isVip;
	}
	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

    public String getVIPExpireTime() {
        return VIPExpireTime;
    }

    public void setVIPExpireTime(String VIPExpireTime) {
        this.VIPExpireTime = VIPExpireTime;
    }

    public boolean isOverdue() {
		return isOverdue;
	}
	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public Long getVIPExpireDays() {
        return VIPExpireDays;
    }

    public void setVIPExpireDays(Long VIPExpireDays) {
        this.VIPExpireDays = VIPExpireDays;
    }
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public int getRegisterSource() {
		return registerSource;
	}
	public void setRegisterSource(int registerSource) {
		this.registerSource = registerSource;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMarketChannel() {
		return marketChannel;
	}
	public void setMarketChannel(String marketChannel) {
		this.marketChannel = marketChannel;
	}
	public String getRegisterDeviceType() {
		return registerDeviceType;
	}
	public void setRegisterDeviceType(String registerDeviceType) {
		this.registerDeviceType = registerDeviceType;
	}
	
}
