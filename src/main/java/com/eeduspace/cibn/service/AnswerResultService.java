package com.eeduspace.cibn.service;

import java.util.List;

import com.eeduspace.cibn.model.AnswerResultModel;
import com.eeduspace.cibn.persist.po.AnswerResult;

/**
 * 用户答题结果
 * @author zhuchaowei
 * 2016年5月5日
 * Description
 */
public interface AnswerResultService {
	List<AnswerResultModel> findListByDiagnosticUUID(String diagnosticUUID);
	public AnswerResult save(AnswerResult answerResult);
	public void deleteByDiagnosticUUID(String uuid);
	
}
