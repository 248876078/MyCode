package com.box.util;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//import com.mysql.jdbc.PreparedStatement;

/**
 * 操作 JDBC 的工具类. 其中封装了一些工具方法 Version 1
 */
public class JDBCTools {
//有三个参数的关闭连接的方法，是因为，有resultSet类，只有在查询的时候才会出现它，

	public static void release(ResultSet rs, 
			PreparedStatement prepareStatement, Connection conn) {
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if (prepareStatement != null) {
			try {
				prepareStatement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭 Statement 和 Connection
	 * @param statement
	 * @param conn
	 * 	只有2个参数的关闭连接方法，适用于增删改 ，这时候是没有结果集的。
	 */
	public static void release(Statement statement, Connection conn) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 1. 获取连接的方法. 通过读取配置文件从数据库服务器获取一个连接.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		// 1. 准备连接数据库的 4 个字符串.
			// 1). 创建 Properties 对象
		Properties properties = new Properties();

			// 2). 获取 jdbc.properties 对应的输入流
		InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream(
				"jdbc.properties");

			// 3). 加载 2） 对应的输入流
		properties.load(in);

			// 4). 具体决定 user, password 等4 个字符串.
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String url = properties.getProperty("url");
		String driver = properties.getProperty("driver");
//		System.out.println(user);
//		System.out.println(password);
//		System.out.println(url);
//		System.out.println(driver);
		// 2. 加载数据库驱动程序(对应的 Driver 实现类中有注册驱动的静态代码块.)
		Class.forName(driver);

		// 3. 通过 DriverManager 的 getConnection() 方法获取数据库连接.
		return DriverManager.getConnection(url, user, password);
		
//		DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456")
	}

}

