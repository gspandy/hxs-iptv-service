package com.eeduspace.cibn.response;

import java.util.List;

import com.eeduspace.cibn.model.BaseDataModel;
import com.eeduspace.cibn.model.CourseFavoritesModel;

public class CourseFavoritesResponse {
	List<CourseFavoritesModel> courseFavoritesModels;
	Long totalCount;
	Long totalPage;
	int pageSize;
	int cp;
	BaseDataModel baseDataModel;
	Boolean flag;
	public List<CourseFavoritesModel> getCourseFavoritesModels() {
		return courseFavoritesModels;
	}
	public void setCourseFavoritesModels(
			List<CourseFavoritesModel> courseFavoritesModels) {
		this.courseFavoritesModels = courseFavoritesModels;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public BaseDataModel getBaseDataModel() {
		return baseDataModel;
	}
	public void setBaseDataModel(BaseDataModel baseDataModel) {
		this.baseDataModel = baseDataModel;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public Long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	
	
	

}
