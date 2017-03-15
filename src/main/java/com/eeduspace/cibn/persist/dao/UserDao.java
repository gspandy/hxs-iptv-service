package com.eeduspace.cibn.persist.dao;

import com.eeduspace.cibn.persist.po.UserPo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:
 */
public interface UserDao extends CrudRepository<UserPo, Long> {

	@Query("select u from UserPo u where u.userCode=?1")
	UserPo findByUserCode(String userCode);

    @Query("select u from UserPo u where (u.userName=?1 or u.mobile=?2 or u.email=?3) and u.password=?4")
    UserPo findLoginUser(String userName,String phone,String email,String password);
    
    @Query("select u from UserPo u where u.mobile=?1")
	UserPo findByMobile(String mobile);
	
	/**
	 * 修改密码
	 * @param password
	 * @param mobile
	 */
	@Modifying
	@Query("update UserPo m set m.password = ?1 where m.mobile = ?2")
	void updatePwd(String password,String mobile);
	
	@Query("select u.mobile, u.password from UserPo u")
	List<UserPo> findAll();
}
