package com.eeduspace.cibn.persist.enumeration;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
public class UserEnum {

    private Status status;
    private LoginStatus loginStatus;
    private Sex sex;
    private CreateType createType;
    private ScanStatus scanStatus;
    private VerifyType verifyType;

    public enum Status {
        NoActive(0),
        Disable(1),
        Enable(2),
        IsDelete(3);
        private final int value;

        public int getValue() {
            return value;
        }

        Status(int value) {
            this.value = value;
        }
    }

    public enum EquipmentType  {
        Test ("Test"),
        Web ("Web"),
        Android("Android"),
        Ios("Ios"),
        Tv("Tv");
        private final String value;

        public String getValue() {
            return value;
        }

        EquipmentType(String value) {
            this.value = value;
        }
    }
    
    public enum LoginStatus {
        UnKnow(0),
        IsLogout(1),
        IsLogin(2);
        private final int value;

        public int getValue() {
            return value;
        }

        LoginStatus(int value) {
            this.value = value;
        }
    }

    public enum Sex {
        Man(0),
        Woman(1),
        UnKnow(2);
        private final int value;

        public int getValue() {
            return value;
        }

        Sex(int value) {
            this.value = value;
        }
    }

    public enum ScanStatus {
        NoScan(0),//未扫码
        IsScan(1),//已扫码
        ConfirmLogin(2),//确认登录
        CancelLogin(3);//取消登录
        private final int value;

        public int getValue() {
            return value;
        }

        ScanStatus(int value) {
            this.value = value;
        }
    }


    /**
     * 创建类型： 0：用户注册 1：管理员创建
     */
    public enum CreateType {
        ManagerAdd(0),//管理员新增方式
        TemplateAdd(1);//模板新增方式

        private final int value;

        public int getValue() {
            return value;
        }

        CreateType(int value) {
            this.value = value;
        }
    }

    public enum VerifyType {
        register(0),
        teacher_certificate(1),
        reset_password(2),
        edit_password(3);
        private final int value;

        public int getValue() {
            return value;
        }

        VerifyType(int value) {
            this.value = value;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public CreateType getCreateType() {
        return createType;
    }

    public void setCreateType(CreateType createType) {
        this.createType = createType;
    }

    public ScanStatus getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(ScanStatus scanStatus) {
        this.scanStatus = scanStatus;
    }

    public VerifyType getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(VerifyType verifyType) {
        this.verifyType = verifyType;
    }
}
