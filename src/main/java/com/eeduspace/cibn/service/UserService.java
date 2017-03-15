package com.eeduspace.cibn.service;

import java.io.IOException;
import java.util.List;

import com.eeduspace.cibn.model.request.UserRequestModel;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.response.BaseResponse;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:
 */
public interface UserService {

    /**
     * 根据code查找
     * @param userCode
     * @return
     */
    UserPo findByUserCode(String userCode);

    /**
     * 根据用户名密码获取 用户
     * @param user
     * @param password
     * @return
     */
    UserPo findLoginUser(String user,String password);

    /**
     * 保存
     * @param userPo
     * @return
     */
    UserPo save(UserPo userPo);

    
    /**
     * 查询
     * @param userPo
     * @return
     */
	UserPo findOne(Long userId);
	 /**
     * 根据手机号查询
     * @param userPo
     * @return
     */
	UserPo findByMobile(String mobile);

	/**
	 * 修改密码
	 * @param userRequestModel
	 * @return
	 * @throws IOException
	 */
	BaseResponse editPwd(UserRequestModel userRequestModel) throws IOException;

	
	void updatePwdByMobile(String password, String mobile);

	BaseResponse login(UserRequestModel userRequestModel, boolean b) throws IOException;
	
	List<UserPo> findAll();

}
