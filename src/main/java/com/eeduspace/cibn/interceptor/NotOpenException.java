package com.eeduspace.cibn.interceptor;

/**
 * Author: dingran
 * Date: 2016/5/4
 * Description:
 */
public class NotOpenException extends RuntimeException{

    private final String classN;

    public NotOpenException(String message) {
        super(message);
        this.classN = NotOpenException.class.getName();
    }


    public NotOpenException(String message, Throwable e) {
        super(message, e);
        this.classN = e.getClass().getName();
    }

    public NotOpenException(Throwable e) {
        super(e.getMessage());
        this.classN = e.getClass().getName();
    }

    public String getClassN() {
        return classN;
    }


}
