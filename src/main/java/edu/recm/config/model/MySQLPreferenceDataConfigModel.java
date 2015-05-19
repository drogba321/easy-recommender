package edu.recm.config.model;

/**
 * 来源于MySQL的用户偏好数据的参数配置模型
 * @author niuzhixiang
 *
 */
public class MySQLPreferenceDataConfigModel extends AbstractPreferenceDataConfigModel {
	
	private String dbDatabaseName;
	
	private String preferenceTable;
	
	private String userIDColumn;
	
	private String itemIDColumn;
	
	private String preferenceColumn;
	
	private String timestampColumn;

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
	 * 用户偏好数据表名
	 * @return
	 */
	public String getPreferenceTable() {
		return preferenceTable;
	}

	public void setPreferenceTable(String preferenceTable) {
		this.preferenceTable = preferenceTable;
	}

	/**
	 * 用户ID字段名
	 * @return
	 */
	public String getUserIDColumn() {
		return userIDColumn;
	}

	public void setUserIDColumn(String userIDColumn) {
		this.userIDColumn = userIDColumn;
	}

	/**
	 * 项目ID字段名
	 * @return
	 */
	public String getItemIDColumn() {
		return itemIDColumn;
	}

	public void setItemIDColumn(String itemIDColumn) {
		this.itemIDColumn = itemIDColumn;
	}

	/**
	 * 偏好值字段名
	 * @return
	 */
	public String getPreferenceColumn() {
		return preferenceColumn;
	}

	public void setPreferenceColumn(String preferenceColumn) {
		this.preferenceColumn = preferenceColumn;
	}

	/**
	 * 时间戳字段名
	 * @return
	 */
	public String getTimestampColumn() {
		return timestampColumn;
	}

	public void setTimestampColumn(String timestampColumn) {
		this.timestampColumn = timestampColumn;
	}

	public MySQLPreferenceDataConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MySQLPreferenceDataConfigModel(String dbDatabaseName,
			String preferenceTable, String userIDColumn, String itemIDColumn,
			String preferenceColumn, String timestampColumn) {
		super();
		this.dbDatabaseName = dbDatabaseName;
		this.preferenceTable = preferenceTable;
		this.userIDColumn = userIDColumn;
		this.itemIDColumn = itemIDColumn;
		this.preferenceColumn = preferenceColumn;
		this.timestampColumn = timestampColumn;
	}

}
