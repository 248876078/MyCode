package com.box.dao;

import java.sql.SQLException;

import com.box.domain.UserBean;

public interface UserDao {

	/**
	 * 执行登录，判断是否登录成功
	 * @param user 执行登录的用户信息
	 * @return 
	 */
	UserBean login(UserBean user) ;
}
