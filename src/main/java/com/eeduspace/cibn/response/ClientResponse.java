package com.eeduspace.cibn.response;

/**
 * Author: dingran
 * Date: 2016/4/21
 * Description:
 */
public class ClientResponse {

    private String code;
    private String msg;
    private Long id;
    private String error;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
