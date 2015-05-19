package edu.recm.config.model;

/**
 * MySQL数据源连接配置模型
 * @author niuzhixiang
 *
 */
public class DBConfigModel {
	
	private String dbServerName;
	
	private String dbUser;
	
	private String dbPassword;

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

	public DBConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBConfigModel(String dbServerName, String dbUser, String dbPassword) {
		super();
		this.dbServerName = dbServerName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}

}
