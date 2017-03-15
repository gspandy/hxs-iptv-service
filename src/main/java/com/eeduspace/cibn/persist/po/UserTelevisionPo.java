package com.eeduspace.cibn.persist.po;

import com.eeduspace.cibn.persist.enumeration.UserEnum;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
@Entity
@Table(name="cibn_user_television")
public class UserTelevisionPo   implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    @org.hibernate.annotations.ForeignKey(name = "none")
    @NotFound(action= NotFoundAction.IGNORE)
    private UserPo userPo;

    @Column(name = "television_code",unique = true)
    private String televisionCode;

    @Column(name = "scan_status")
    private UserEnum.ScanStatus scanStatus;

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

    public String getTelevisionCode() {
        return televisionCode;
    }

    public void setTelevisionCode(String televisionCode) {
        this.televisionCode = televisionCode;
    }

    public UserEnum.ScanStatus getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(UserEnum.ScanStatus scanStatus) {
        this.scanStatus = scanStatus;
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

    public UserPo getUserPo() {
        return userPo;
    }

    public void setUserPo(UserPo userPo) {
        this.userPo = userPo;
    }
}
