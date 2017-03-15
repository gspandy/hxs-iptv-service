package com.eeduspace.cibn.persist.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
@Entity
@Table(name="cibn_token")
public class TokenPo  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long id;
    //令牌
    @Column(nullable = false,length = 1000)
    private String token;
    //刷新令牌
    @Column(nullable = false,name = "refresh_token",length = 1000)
    private String refreshToken;
    //用户标识
    @Column(nullable = false,name = "user_code",length = 64,unique = true)
    private String userCode;
    //有效时间
    @Column(nullable = false)
    private String expires;
    //作用域
    private String scope;
    //设备号
    @Column(name = "television_code")
    private String televisionCode;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false,name = "create_time")
    private Date createDate = new Date();
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateDate = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public String getTelevisionCode() {
        return televisionCode;
    }

    public void setTelevisionCode(String televisionCode) {
        this.televisionCode = televisionCode;
    }
}
