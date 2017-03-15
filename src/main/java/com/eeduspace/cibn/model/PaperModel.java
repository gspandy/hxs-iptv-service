package com.eeduspace.cibn.model;
/**
 * @author zhuchaowei
 * 2016年4月20日
 * Description  试卷model
 */
public class PaperModel {
	/**
	 * 试卷code
	 */
	private String id;
	/**
	 * 诊断UUID
	 */
	private String diagnosticReportUUID;
	/**
	 * 试卷名称
	 */
	private String paperName;
	/**
	 * 是否诊断过  true 诊断  false 未诊断
	 */
	private Boolean diagnostic=false;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public Boolean getDiagnostic() {
		return diagnostic;
	}
	public void setDiagnostic(Boolean diagnostic) {
		this.diagnostic = diagnostic;
	}
	public String getDiagnosticReportUUID() {
		return diagnosticReportUUID;
	}
	public void setDiagnosticReportUUID(String diagnosticReportUUID) {
		this.diagnosticReportUUID = diagnosticReportUUID;
	}
}
