package com.eeduspace.cibn.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eeduspace.cibn.model.DiagnosticReportModel;
import com.eeduspace.cibn.persist.po.DiagnosticReport;
import com.google.gson.JsonSyntaxException;
/**
 * 诊断报告serviece
 * @author zhuchaowei
 * 2016年4月21日
 * Description
 */
public interface DiagnosticReportService {
	DiagnosticReport findById(Long id);
	/**
	 * 返回我的诊断分页信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月20日 上午11:56:24
	 * @param userCode 用户code
	 * @param isDel 是否删除
	 * @param pageable 分页对象
	 */
	Page<DiagnosticReport> findAll(String userCode,boolean isDel,Pageable pageable);
	DiagnosticReport delete(String uuid);
	/**
	 * 根据UUID获取信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 上午11:13:55
	 * @param uuid
	 * @return
	 */
	DiagnosticReport findByUUID(String uuid);
	/**
	 * 保存
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 下午1:15:41
	 * @param uuid 用户code
	 * @param diagnosticReportModel 诊断信息
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JsonSyntaxException 
	 */
	DiagnosticReportModel saveDiagnosticReport(String uuid,DiagnosticReportModel diagnosticReportModel) throws JsonSyntaxException, ClientProtocolException, IOException;
	/**
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 下午6:29:54
	 * @param paperCode
	 * @param score
	 * @return
	 */
	Long countRank(String paperCode,Integer score);
	/**
	 * 统计所有 根据试卷code 
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 下午6:53:25
	 * @param paperCode
	 * @return
	 */
	Long count(String paperCode);
	DiagnosticReport save(DiagnosticReport diagnosticReport);
	/**
	 * 根据试题获取诊断报告
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月27日 下午1:05:14
	 * @param userCode
	 * @param paperCode
	 * @return
	 */
	 List<DiagnosticReport> findByUserCodeAndPaperCodeAndUnitCode(String userCode,String paperCode,String unitCode);
	 /**
	  * 查询诊断详情
	  * Author： zhuchaowei
	  * e-mail:zhuchaowei@e-eduspace.com
	  * 2016年5月18日 下午2:11:01
	  * @param uuid
	  * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JsonSyntaxException 
	  */
	 DiagnosticReportModel findDiagnosticReport(String uuid) throws JsonSyntaxException, ClientProtocolException, IOException;
	 
	
}
