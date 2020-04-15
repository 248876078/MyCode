package com.box.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.handlers.BeanHandler;

import com.box.dao.UserDao;
import com.box.domain.UserBean;
import com.box.util.JDBCTools;
//import com.box.util.JDBCUtil02;

public class UserDaoImpl implements UserDao {
	Connection connection=null;
	PreparedStatement prepareStatement=null;
	ResultSet resultSet=null;
	@Override
	public UserBean login(UserBean user)  {
//		boolean flag=false;
		
		try {
//			获取连接
			connection = new JDBCTools().getConnection();
//			定义sql
			String sql="select * from user where username=? and password=?";
//			执行sql
			 prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, user.getUsername());
			prepareStatement.setString(2, user.getPassword());
			 resultSet = prepareStatement.executeQuery();
//			操作结果集
			while(resultSet.next()) {
				user.setId( resultSet.getInt(1));
//				String username = resultSet.getString(2);
//				String password = resultSet.getString(3);
//				flag=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTools.release(resultSet, prepareStatement, connection);
		}
		
		return user;
	
	}

}
