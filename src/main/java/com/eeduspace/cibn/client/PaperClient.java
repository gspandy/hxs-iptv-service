package com.eeduspace.cibn.client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.eeduspace.cibn.model.ExamDataDetailBeanForResponse;
import com.eeduspace.cibn.model.request.PaperRequest;
import com.eeduspace.cibn.model.response.PaperResponse;
/**
 * 试卷请求客户端
 * @author zhuchaowei
 * 2016年5月17日
 * Description
 */

public interface PaperClient {
	/**
	 * 请求资源库试卷信
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月17日 下午2:56:35
	 * @param gradeCode 学年
	 * @param subjectCode 学科
	 * @param bookType 教材版本
	 * @param unitCode 单元
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public PaperResponse getPaper(PaperRequest paperRequest) throws ClientProtocolException, IOException;
	/**
	 * 
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月17日 下午2:55:43
	 * @param paperCode 试卷
	 * @return
	 */
	public ExamDataDetailBeanForResponse getPaperInfo(String paperCode);
}
