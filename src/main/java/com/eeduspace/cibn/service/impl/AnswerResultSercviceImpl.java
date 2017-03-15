package com.eeduspace.cibn.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.AnswerResultModel;
import com.eeduspace.cibn.persist.dao.AnswerResultDao;
import com.eeduspace.cibn.persist.po.AnswerResult;
import com.eeduspace.cibn.service.AnswerResultService;
/**
 * 用户答题结果业务实现
 * @author zhuchaowei
 * 2016年5月5日
 * Description
 */
@Service
public class AnswerResultSercviceImpl implements AnswerResultService{
	@Inject
	private AnswerResultDao answerResultDao;
	@Override
	public List<AnswerResultModel> findListByDiagnosticUUID(
			String diagnosticUUID) {
		List<AnswerResult> answerResults=answerResultDao.findByDiagnosticUUID(diagnosticUUID);
		List<AnswerResultModel> models=new ArrayList<>();
		for (AnswerResult answerResult : answerResults) {
			AnswerResultModel model=new AnswerResultModel();
			model=CIBNConvert.fromAnswerResult(answerResult);
			models.add(model);
		}
		return models;
	}

	@Override
	public AnswerResult save(AnswerResult answerResult) {
		return answerResultDao.save(answerResult);
	}

	@Override
	public void deleteByDiagnosticUUID(String uuid) {
		answerResultDao.deleteAll(uuid);
	}

}
