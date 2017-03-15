
	 
	 /** 
	 * <pre>项目名称:hxs-iptv-service 
	 * 文件名称:RankMessageService.java 
	 * 包名:com.eeduspace.cibn.service 
	 * 创建日期:2016年11月22日下午1:18:11 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.eeduspace.cibn.service;

import java.util.List;
import java.util.Map;
	
	 /** 
 * <pre>项目名称：hxs-iptv-service    
 * 类名称：RankMessageService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年11月22日 下午1:18:11    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年11月22日 下午1:18:11    
 * 修改备注：       
 * @version </pre>    
 */

public interface RankMessageService {

		
	/** <pre>getStage(这里用一句话描述这个方法的作用)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年11月22日 下午1:21:34    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年11月22日 下午1:21:34    
	 * 修改备注： 
	 * @return</pre>    
	 */
		 
	public List<Map<String, Object>> getStage() throws Exception;

	
	/** <pre>getGrade(这里用一句话描述这个方法的作用)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年11月22日 下午2:03:58    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年11月22日 下午2:03:58    
	 * 修改备注： 
	 * @param object
	 * @return</pre>    
	 */
		 
	public List<Map<String, Object>> getGrade(String stageCode) throws Exception;


		
	/** <pre>getSubject(这里用一句话描述这个方法的作用)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年11月23日 上午10:40:22    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年11月23日 上午10:40:22    
	 * 修改备注： 
	 * @param gradeCode
	 * @param gradeCode 
	 * @return</pre>    
	 */
			 
	public List<Map<String, Object>> getSubject(String stageCode, String gradeCode) throws Exception;


			
	/** <pre>getNationalTop(这里用一句话描述这个方法的作用)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年11月23日 上午11:15:42    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年11月23日 上午11:15:42    
	 * 修改备注： 
	 * @param gradeCode
	 * @param subjectCode
	 * @param mobile 
	 * @return</pre>    
	 */
				 
	public String getNationalTop(String stageCode,String gradeCode, String subjectCode, String mobile) throws Exception;


	
		/** <pre>getAllRank(这里用一句话描述这个方法的作用)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2017年2月6日 下午6:46:27    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2017年2月6日 下午6:46:27    
		 * 修改备注： 
		 * @param json
		 * @return</pre>    
		 */
		 
	public String getAllRank(String json) throws Exception;

}

	