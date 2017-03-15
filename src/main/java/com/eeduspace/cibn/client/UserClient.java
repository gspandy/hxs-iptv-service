package com.eeduspace.cibn.client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.eeduspace.cibn.model.request.UserRequestModel;
import com.eeduspace.cibn.response.BaseResponse;
/**
 * 访问UUIMS客户端
 * @author zhuchaowei
 * 2016年5月24日
 * Description
 */
public interface UserClient {
	/**
	 * 用户注册
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月24日 下午3:27:21
	 * @param userRequestModel
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public BaseResponse userRegister(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 密码修改
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月24日 下午3:27:28
	 * @param userRequestModel
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BaseResponse updatePassword(UserRequestModel userRequestModel ) throws ClientProtocolException, IOException;
	/**
	 * 用户登录
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月24日 下午3:36:44
	 * @param userRequestModel
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BaseResponse userLogin(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 找回密码
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月25日 上午10:54:34
	 * @param userRequestModel
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BaseResponse resetPwd(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 校验手机号
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月25日 上午11:52:14
	 * @param userRequestModel
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BaseResponse checkPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 用户激活
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月25日 下午3:02:10
	 * @param userRequestModel
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BaseResponse userActivation(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 修改手机号
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月25日 下午5:35:28
	 * @param userRequestModel
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BaseResponse updateUserInfo(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 获取用户详情  管理员权限
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月27日 上午10:12:22
	 * @param userRequestModel
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public BaseResponse getUserInfo(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	
	
	/**
	 * 校验手机号
	 */
	public BaseResponse validateByMobile(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	
	/**
	 * <pre>sendSms(发送验证码)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年11月17日 下午5:36:30    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年11月17日 下午5:36:30    
		 * 修改备注： 
		 * @return
		 * @throws ClientProtocolException
		 * @throws IOException</pre>
	 */
	public BaseResponse sendSms(UserRequestModel userRequestModel) throws ClientProtocolException, IOException; 
	
	/**
	 * <pre>validateSms(校验验证码)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年11月17日 下午6:09:15    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年11月17日 下午6:09:15    
		 * 修改备注： 
		 * @param userRequestModel
		 * @return
		 * @throws ClientProtocolException
		 * @throws IOException</pre>
	 */
	public BaseResponse validateSms(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * <pre>validateSms(重置密码)   
	 * 创建人：王亮 wanglmir@163.com   
	 * 创建时间：2016年11月17日 下午6:09:15    
	 * 修改人：王亮 wanglmir@163.com     
	 * 修改时间：2016年11月17日 下午6:09:15    
	 * 修改备注： 
	 * @param userRequestModel
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException</pre>
	 */
	public BaseResponse resetPassword(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * <pre>getUserInfoByPhone(在umms中通过手机号查询用户信息)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月16日 上午11:56:08    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月16日 上午11:56:08    
		 * 修改备注： 
		 * @param userRequestModel
		 * @return
		 * @throws ClientProtocolException
		 * @throws IOException</pre>
	 */
	public BaseResponse getUserInfoByPhone(UserRequestModel userRequestModel)throws ClientProtocolException, IOException;
}
