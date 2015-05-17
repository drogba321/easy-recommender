package edu.recm.algorithm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 表示来自MySQL的用户与项目特征数据的POJO类
 * @author niuzhixiang
 *
 */
public class MySQLContentData {

	private String dbServerName;
	
	private String dbUser;
	
	private String dbPassword;
	
	private String dbDatabaseName;
	
	private String userTableName;
	
	private String itemTableName;
	
	private List<QueryUnit> queryList;
	
	/**
	 * 数据库主机名
	 * @return
	 */
	public String getDbServerName() {
		return dbServerName;
	}
	
	public void setDbServerName(String dbServerName) {
		this.dbServerName = dbServerName;
	}
	
	/**
	 * 数据库用户名
	 * @return
	 */
	public String getDbUser() {
		return dbUser;
	}
	
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	
	/**
	 * 数据库密码
	 * @return
	 */
	public String getDbPassword() {
		return dbPassword;
	}
	
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	/**
	 * 数据库名称
	 * @return
	 */
	public String getDbDatabaseName() {
		return dbDatabaseName;
	}
	
	public void setDbDatabaseName(String dbDatabaseName) {
		this.dbDatabaseName = dbDatabaseName;
	}
	
	/**
	 * 用户特征表
	 * @return
	 */
	public String getUserTableName() {
		return userTableName;
	}
	
	public void setUserTableName(String userTableName) {
		this.userTableName = userTableName;
	}
	
	/**
	 * 项目特征表
	 * @return
	 */
	public String getItemTableName() {
		return itemTableName;
	}
	
	public void setItemTableName(String itemTableName) {
		this.itemTableName = itemTableName;
	}
	
	/**
	 * 查询条件列表
	 * @return
	 */
	public List<QueryUnit> getQueryList() {
		return queryList;
	}
	
	public void setQueryList(List<QueryUnit> queryList) {
		this.queryList = queryList;
	}
	
	public MySQLContentData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MySQLContentData(String dbServerName, String dbUser,
			String dbPassword, String dbDatabaseName, String userTableName,
			String itemTableName, List<QueryUnit> queryList) {
		super();
		this.dbServerName = dbServerName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
		this.dbDatabaseName = dbDatabaseName;
		this.userTableName = userTableName;
		this.itemTableName = itemTableName;
		this.queryList = queryList;
	}
	
}
