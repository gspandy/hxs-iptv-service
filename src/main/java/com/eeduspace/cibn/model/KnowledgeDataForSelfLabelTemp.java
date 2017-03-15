package com.eeduspace.cibn.model;

public class KnowledgeDataForSelfLabelTemp {
	
	private String code;//产生式code
	private String name;//产生式名称
	private String typeCode;//知识点类型code
	private String typeName;//知识点类型
	private String parentCode;//父code
	private boolean isSelfLabel=false;//是否是产生式
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public boolean isSelfLabel() {
		return isSelfLabel;
	}
	public void setSelfLabel(boolean isSelfLabel) {
		this.isSelfLabel = isSelfLabel;
	}
	

}
