package edu.recm.config.model;

import java.util.List;

/**
 * 来源于MySQL的用户及项目特征数据的参数配置模型
 * @author niuzhixiang
 *
 */
public class MySQLContentDataConfigModel extends AbstractDataConfigModel {

	private String dbDatabaseName;
	
	private String userTableName;
	
	private String itemTableName;

	private List<QueryUnitConfigModel> queryUnitConfigModelList;

	/**
	 * 数据库名
	 * @return
	 */
	public String getDbDatabaseName() {
		return dbDatabaseName;
	}

	public void setDbDatabaseName(String dbDatabaseName) {
		this.dbDatabaseName = dbDatabaseName;
	}

	/**
	 * 用户表名
	 * @return
	 */
	public String getUserTableName() {
		return userTableName;
	}

	public void setUserTableName(String userTableName) {
		this.userTableName = userTableName;
	}

	/**
	 * 项目表名
	 * @return
	 */
	public String getItemTableName() {
		return itemTableName;
	}

	public void setItemTableName(String itemTableName) {
		this.itemTableName = itemTableName;
	}

	/**
	 * 查询条件参数配置模型的列表
	 * @return
	 */
	public List<QueryUnitConfigModel> getQueryUnitConfigModelList() {
		return queryUnitConfigModelList;
	}

	public void setQueryUnitConfigModelList(
			List<QueryUnitConfigModel> queryUnitConfigModelList) {
		this.queryUnitConfigModelList = queryUnitConfigModelList;
	}

	public MySQLContentDataConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MySQLContentDataConfigModel(String dbDatabaseName, String userTableName,
			String itemTableName,
			List<QueryUnitConfigModel> queryUnitConfigModelList) {
		super();
		this.dbDatabaseName = dbDatabaseName;
		this.userTableName = userTableName;
		this.itemTableName = itemTableName;
		this.queryUnitConfigModelList = queryUnitConfigModelList;
	}

}
