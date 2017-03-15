package com.eeduspace.cibn.model.response;

public class PayResult {
private boolean  pay;
private String result;
public String getResult() {
	return result;
}
public boolean isPay() {
	return pay;
}
public void setPay(boolean pay) {
	this.pay = pay;
}
public void setResult(String result) {
	this.result = result;
}
}
