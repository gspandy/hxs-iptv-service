package com.eeduspace.cibn.model;
/**
 * @author zhuchaowei
 * 2016年4月20日
 * Description  分页参数对象
 */
public class PageableModel {
	/**
	 * 条目
	 */
	private int size;
	/**
	 * 页数   从0开始  0为第一页
	 */
	private int pageNumber;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
