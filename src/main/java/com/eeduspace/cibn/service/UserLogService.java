package com.eeduspace.cibn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.eeduspace.cibn.persist.po.UserLogPo;
import com.eeduspace.cibn.persist.po.UserPo;

/**
 * 
 * @author zengzhe
 *
 * 上午11:55:54
 */
public interface UserLogService {

	/**
     *用户查看个人日志
     * @param 
     * @return
     */

	Page<UserLogPo> getList(UserPo userPo, Pageable pageable);


	/**
     * 新增/更新
     * @return
     */
    public void create(UserPo userPo, String action,String module, Boolean result,String sourceIp,String requestId) ;

    /**
     * 新增/更新
     * @return
     */
    public UserLogPo create(Long userId, String action, String module, Boolean result,String sourceIp,String requestId);
}
