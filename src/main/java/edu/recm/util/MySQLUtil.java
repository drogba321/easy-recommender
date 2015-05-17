package edu.recm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 处理MySQL相关操作的工具类
 * @author niuzhixiang
 *
 */
public class MySQLUtil {

	/**
	 * 连接MySQL
	 * @param serverName 数据库主机名
	 * @param user 数据库用户名
	 * @param password 数据库密码
	 * @param dbName 数据库名称
	 * @return 用于执行SQL查询的Statement对象
	 */
	public static Statement connectMySQL(String serverName, String user, String password, String dbName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://" + serverName + ":3306/" + dbName, user, password);
			Statement statement = connection.createStatement();
			return statement;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
