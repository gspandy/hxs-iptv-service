package com.eeduspace.cibn.model;

import java.util.List;

/**
 * @author songwei
 * 	Date 2016-04-28
 *	Describe 练习题（课后习题）封装类
 */
public class ExaminationModel {
	//产生式code
	private String productCode;
	//练习题数量
	private int selectCount = 1;
	//练习题属性
	//练习题id
	private String id;
	//练习题题干
	private String stem;
	//题干图片路径
	private String stemPicture;
	//练习题标题
	private String title;
	
	//选项图片路径
	private String optionPicture;
	
	//解析图片路径
	private String anayzePicture;
	//答案（单选、多选字符）
	private String answer;
	//试题难度等级（3:难 2:中 1:易）
	private String difficultStar;
	//视频文件路径
	private String videoPath;
	//英语听力文件路径
	private String audioListenPath;
	//音频解析文件路径
	private String audioAnalyzePath;
	//原视频重复播放地址
	private String urlWeVideo;
	//试题选项
	private String quesOption;
	//解析选项
	private String quesAnalyze;
	
	private List<QuesOptionModel> quesOptions;
	private List<QuesAnalyzeModel> quesAnalyzes;
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getSelectCount() {
		return selectCount;
	}
	public void setSelectCount(int selectCount) {
		this.selectCount = selectCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStem() {
		return stem;
	}
	public void setStem(String stem) {
		this.stem = stem;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getDifficultStar() {
		return difficultStar;
	}
	public void setDifficultStar(String difficultStar) {
		this.difficultStar = difficultStar;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getAudioListenPath() {
		return audioListenPath;
	}
	public void setAudioListenPath(String audioListenPath) {
		this.audioListenPath = audioListenPath;
	}
	public String getAudioAnalyzePath() {
		return audioAnalyzePath;
	}
	public void setAudioAnalyzePath(String audioAnalyzePath) {
		this.audioAnalyzePath = audioAnalyzePath;
	}
	public String getUrlWeVideo() {
		return urlWeVideo;
	}
	public void setUrlWeVideo(String urlWeVideo) {
		this.urlWeVideo = urlWeVideo;
	}
	public String getStemPicture() {
		return stemPicture;
	}
	public void setStemPicture(String stemPicture) {
		this.stemPicture = stemPicture;
	}
	public String getOptionPicture() {
		return optionPicture;
	}
	public void setOptionPicture(String optionPicture) {
		this.optionPicture = optionPicture;
	}
	public String getAnayzePicture() {
		return anayzePicture;
	}
	public void setAnayzePicture(String anayzePicture) {
		this.anayzePicture = anayzePicture;
	}
	public String getQuesOption() {
		return quesOption;
	}
	public void setQuesOption(String quesOption) {
		this.quesOption = quesOption;
	}
	public String getQuesAnalyze() {
		return quesAnalyze;
	}
	public void setQuesAnalyze(String quesAnalyze) {
		this.quesAnalyze = quesAnalyze;
	}
	public List<QuesOptionModel> getQuesOptions() {
		return quesOptions;
	}
	public void setQuesOptions(List<QuesOptionModel> quesOptions) {
		this.quesOptions = quesOptions;
	}
	public List<QuesAnalyzeModel> getQuesAnalyzes() {
		return quesAnalyzes;
	}
	public void setQuesAnalyzes(List<QuesAnalyzeModel> quesAnalyzes) {
		this.quesAnalyzes = quesAnalyzes;
	}
	@Override
	public boolean equals(Object obj) {
		ExaminationModel examinationModel = (ExaminationModel) obj;
		boolean b = id.equals(examinationModel.getId());
		return b;
	}
	@Override
	public int hashCode() {
		String in = id; 
		return in.hashCode();
	}

}
