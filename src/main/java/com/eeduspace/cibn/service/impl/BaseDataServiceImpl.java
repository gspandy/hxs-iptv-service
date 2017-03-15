package com.eeduspace.cibn.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.client.BaseDataClient;
import com.eeduspace.cibn.model.BaseData;
import com.eeduspace.cibn.service.BaseDataService;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Service
public class BaseDataServiceImpl implements BaseDataService {
	private Gson gson = new Gson();
	
	@Inject
	private BaseDataClient baseDataClient;

	@Override
	public List<BaseData> getStageList() throws JsonSyntaxException {
		String gsonResponse = baseDataClient.getStageList();
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		if(StringUtils.isNotBlank(gsonResponse)){
			baseDatas = gson.fromJson(gsonResponse, new TypeToken<List<BaseData>>() { }.getType());
		}
		return baseDatas;
	}

	@Override
	public List<BaseData> getGradeList(String stageCode) throws JsonSyntaxException {
		String gsonResponse = baseDataClient.getGradeList(stageCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		if(StringUtils.isNotBlank(gsonResponse)){
			baseDatas = gson.fromJson(gsonResponse,  new TypeToken<List<BaseData>>() { }.getType());
			int n = 0;
			int m = 0;
			for (int i = 0; i < baseDatas.size(); i++) {
				BaseData baseData = baseDatas.get(i-n);
				if (baseData.getGradeCode().equals("24")) {
					baseDatas.remove(i-n);
					n++;
				}
			}
			for (int j = 0; j < baseDatas.size(); j++) {
				BaseData baseData2 = baseDatas.get(j-m);
				if (baseData2.getGradeCode().equals("33")) {
					baseDatas.remove(j-m);
					m++;
				}
			}
		}
		return baseDatas;
	}

	/*@Override
	public List<BaseData> getSubjectList(String gradeCode) throws JsonSyntaxException {
		String gsonResponse =baseDataClient.getSubjectList(gradeCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		if(StringUtils.isNotBlank(gsonResponse)){
			Map<String, Object> fromJson = gson.fromJson(gsonResponse,  new TypeToken<Map<String, Object>>() { }.getType());
			if (null != fromJson.get("data")) {
				String json = gson.toJson(fromJson.get("data"));
				baseDatas = gson.fromJson(json,  new TypeToken<List<BaseData>>() { }.getType());
				int n = 0;
				for (int i = 0; i < baseDatas.size(); i++) {
					BaseData baseData = baseDatas.get(i-n);
					if (baseData.getSubject_code().equals("1") || baseData.getSubject_name().equals("语文")) {
						baseDatas.remove(i-n);
						n++;
					}
				}
			}
		}
		return baseDatas;
	}*/
	@Override
	public List<BaseData> getSubjectList(String gradeCode) throws JsonSyntaxException {
		String gsonResponse =baseDataClient.getSubjectList(gradeCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		List<BaseData> baseDatas2 = new ArrayList<BaseData>();
		int gradeCodeInt = Integer.parseInt(gradeCode);
		if(StringUtils.isNotBlank(gsonResponse)){
			Map<String, Object> fromJson = gson.fromJson(gsonResponse,  new TypeToken<Map<String, Object>>() { }.getType());
			if (null != fromJson.get("data")) {
				String json = gson.toJson(fromJson.get("data"));
				baseDatas = gson.fromJson(json,  new TypeToken<List<BaseData>>() { }.getType());
				int n = 0;
				int m = 0;
				for (int i = 0; i < baseDatas.size(); i++) {
					BaseData baseData = baseDatas.get(i-n);
					if (baseData.getSubject_code().equals("1") || baseData.getSubject_name().equals("语文")) {
						baseDatas.remove(i-n);
						n++;
					}
				}
				for (int j = 0; j < baseDatas.size(); j++) {
					BaseData baseData = baseDatas.get(j-m);
					if (baseData.getSubject_code().equals("3") || baseData.getSubject_name().equals("英语")) {
						baseDatas.remove(j-m);
						m++;
					}
				}
				/*if (gradeCode.equals("31")) {
					for (BaseData baseData : baseDatas) {
						if (baseData.getSubject_code().equals("2") || baseData.getSubject_code().equals("4") || baseData.getSubject_code().equals("5")) {
							baseDatas2.add(baseData);
							baseDatas = baseDatas2;
						}
					}
				}
				if (gradeCode.equals("32")) {
					for (BaseData baseData : baseDatas) {
						if (baseData.getSubject_code().equals("5")) {
							baseDatas2.add(baseData);
							baseDatas = baseDatas2;
						}
					}
				}*/
				if (11 <= gradeCodeInt && 16 >= gradeCodeInt) {
					for (BaseData baseData : baseDatas) {
						if (baseData.getSubject_code().equals("2")) {
							baseDatas2.add(baseData);
							baseDatas = baseDatas2;
						}
					}
				}
				if (21 <= gradeCodeInt && 23 >= gradeCodeInt) {
					for (BaseData baseData : baseDatas) {
						if (baseData.getSubject_code().equals("5") || baseData.getSubject_code().equals("2") || baseData.getSubject_code().equals("4")) {
							baseDatas2.add(baseData);
							baseDatas = baseDatas2;
						}
					}
				}
				if (31 <= gradeCodeInt && 32 >= gradeCodeInt) {
					for (BaseData baseData : baseDatas) {
						if (baseData.getSubject_code().equals("5")) {
							baseDatas2.add(baseData);
							baseDatas = baseDatas2;
						}
					}
				}
			}
		}
		return baseDatas;
	}
	/*@Override
	public List<BaseData> getSubjectList(String gradeCode) throws JsonSyntaxException {
		String gsonResponse =baseDataClient.getSubjectList(gradeCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		if(StringUtils.isNotBlank(gsonResponse)){
			baseDatas = gson.fromJson(gsonResponse,  new TypeToken<List<BaseData>>() { }.getType());
		}
		return baseDatas;
	}*/

	/*@Override
	public List<BaseData> getBookTypeList(String gradeCode, String subjectCode) throws JsonSyntaxException {
		String gsonResponse = baseDataClient.getBookTypeList(gradeCode, subjectCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		if(StringUtils.isNotBlank(gsonResponse)){
			Map<String, Object> fromJson = gson.fromJson(gsonResponse,  new TypeToken<Map<String, Object>>() { }.getType());
			if (null != fromJson.get("data")) {
				String json = gson.toJson(fromJson.get("data"));
				baseDatas = gson.fromJson(json,  new TypeToken<List<BaseData>>() { }.getType());
			}
		}
		return baseDatas;
	}*/
	@Override
	public List<BaseData> getBookTypeList(String gradeCode, String subjectCode) throws JsonSyntaxException {
		String gsonResponse = baseDataClient.getBookTypeList(gradeCode, subjectCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		List<BaseData> baseDatas2 = new ArrayList<BaseData>();
		int gradeCodeInt = Integer.parseInt(gradeCode);
		if(StringUtils.isNotBlank(gsonResponse)){
			Map<String, Object> fromJson = gson.fromJson(gsonResponse,  new TypeToken<Map<String, Object>>() { }.getType());
			if (null != fromJson.get("data")) {
				String json = gson.toJson(fromJson.get("data"));
				baseDatas = gson.fromJson(json,  new TypeToken<List<BaseData>>() { }.getType());
			}
			/*if (gradeCode.equals("21")) {
				for (BaseData baseData : baseDatas) {
					if (baseData.getBook_type().equals("人教版") || baseData.getBook_type().equals("北师大版") || baseData.getBook_type().equals("京教版")) {
						baseDatas2.add(baseData);
						baseDatas = baseDatas2;
					}
				}
			}
			if (gradeCode.equals("22") || gradeCode.equals("23")) {
				for (BaseData baseData : baseDatas) {
					if (baseData.getBook_type().equals("人教版")) {
						baseDatas2.add(baseData);
						baseDatas = baseDatas2;
					}
				}
			}*/
			if (11 <= gradeCodeInt && 32 >= gradeCodeInt) {
				for (BaseData baseData : baseDatas) {
					if (baseData.getBook_type().equals("人教版") || baseData.getBook_type().equals("人教新课标版")) {
						baseDatas2.add(baseData);
						baseDatas = baseDatas2;
					}
				}
			}
		}
		return baseDatas;
	}
	/*@Override
	public List<BaseData> getBookTypeList(String gradeCode, String subjectCode) throws JsonSyntaxException {
		String gsonResponse = baseDataClient.getBookTypeList(gradeCode, subjectCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		if(StringUtils.isNotBlank(gsonResponse)){
			baseDatas = gson.fromJson(gsonResponse,  new TypeToken<List<BaseData>>() { }.getType());
		}
		return baseDatas;
	}*/

	@Override
	public List<BaseData> getBookTypeVersion(String gradeCode,String subjectCode, String bookTypeCode) throws JsonSyntaxException, Exception {
		String gsonResponse = baseDataClient.getBookTypeVersion(gradeCode, subjectCode, bookTypeCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		if(StringUtils.isNotBlank(gsonResponse)){
			baseDatas = gson.fromJson(gsonResponse,  new TypeToken<List<BaseData>>() { }.getType());
		}
		return baseDatas;
	}

	@Override
	public List<BaseData> getUnitList(String bookTypeVersionCode) throws JsonSyntaxException, Exception {
		String gsonResponse = baseDataClient.getUnitList(bookTypeVersionCode);
		List<BaseData> baseDatas = new ArrayList<BaseData>();
		if(StringUtils.isNotBlank(gsonResponse)){
			baseDatas = gson.fromJson(gsonResponse,  new TypeToken<List<BaseData>>() { }.getType());
		}
		return baseDatas;
	}

}
