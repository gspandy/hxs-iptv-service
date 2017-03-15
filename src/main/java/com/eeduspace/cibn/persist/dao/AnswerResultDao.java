package com.eeduspace.cibn.persist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eeduspace.cibn.persist.po.AnswerResult;

public interface AnswerResultDao extends CrudRepository<AnswerResult, Long>{
	/**
	 * 根据诊断报告ID获取所有题目信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月5日 上午9:15:14
	 * @param diagnositcUUID
	 * @return
	 */
    List<AnswerResult> findByDiagnosticUUID(String diagnositcUUID);
    @Modifying
    @Query(value="delete from AnswerResult a where a.diagnosticUUID=?1")
    public  void deleteAll(String diagnositcUUID);
}
