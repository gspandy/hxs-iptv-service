package com.eeduspace.cibn.persist.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eeduspace.uuims.comm.util.base.UIDGenerator;
/**
 * 试卷临时信息
 * @author zhuchaowei
 * 2016年5月9日
 * Description
 */
@Entity
@Table(name = "cibn_temp_paper_info")
public class TempPaperInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;
	// 唯一标识
	@Column(unique = true)
	private String uuid = UIDGenerator.getUUID().toString().replace("-", "");
	/**
	 * 试卷信息 json格式
	 */
	@Column(name="paper_info")
	private String paperInfo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPaperInfo() {
		return paperInfo;
	}
	public void setPaperInfo(String paperInfo) {
		this.paperInfo = paperInfo;
	}
	
}
