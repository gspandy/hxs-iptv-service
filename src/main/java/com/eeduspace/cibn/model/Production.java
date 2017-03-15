package com.eeduspace.cibn.model;
/**
 * 产生式实体
 * @author zhuchaowei
 * 2016年4月25日
 * Description
 */
public class Production {
	/**
	 * 产生式名称
	 */
	private String productionName;
	/**
	 * 产生式类型
	 */
	private String productionType;
	/**
	 * 产生式code
	 */
	private String productionCode;
	public Production(String prductionName,String productionCode,String productionType){
		this.productionName=prductionName;
		this.productionCode=productionCode;
		this.productionType=productionType;
	}
	public String getProductionName() {
		return productionName;
	}
	public void setPrductionName(String productionName) {
		this.productionName = productionName;
	}
	public String getProductionCode() {
		return productionCode;
	}
	public void setProductionCode(String productionCode) {
		this.productionCode = productionCode;
	}
	public String getProductionType() {
		return productionType;
	}
	public void setProductionType(String productionType) {
		this.productionType = productionType;
	}
}
