package edu.recm.algorithm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.lucene.search.similarities.Similarity;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * 表示来自MySQL的用户偏好数据的POJO类
 * @author niuzhixiang
 *
 */
public class MySQLPreferenceData extends AbstractPreferenceData {
	
	private MysqlDataSource mysqlDataSource;
	
	private String dbServerName;
	
	private String dbUser;
	
	private String dbPassword;
	
	private String dbDatabaseName;
	
	private String preferenceTable;
	
	private String userIDColumn;
	
	private String itemIDColumn;
	
	private String preferenceColumn;
	
	private String timestampColumn;

	public MysqlDataSource getMysqlDataSource() {
		return mysqlDataSource;
	}

	public void setMysqlDataSource(MysqlDataSource mysqlDataSource) {
		this.mysqlDataSource = mysqlDataSource;
	}

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
	 * 用户偏好数据表名称
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
	 * 偏好值字段
	 * @return
	 */
	public String getPreferenceColumn() {
		return preferenceColumn;
	}

	public void setPreferenceColumn(String preferenceColumn) {
		this.preferenceColumn = preferenceColumn;
	}

	/**
	 * 时间戳字段
	 * @return
	 */
	public String getTimestampColumn() {
		return timestampColumn;
	}

	public void setTimestampColumn(String timestampColumn) {
		this.timestampColumn = timestampColumn;
	}
	
	/**
	 * 初始化MySQL数据源
	 * @param serverName
	 * @param user
	 * @param password
	 * @param dbName
	 */
	public void initMySQLDataSource(String serverName, String user, String password, String dbName) {
		this.mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setServerName(serverName);
		mysqlDataSource.setUser(user);
		mysqlDataSource.setPassword(password);
		mysqlDataSource.setDatabaseName(dbName);
	}
	
	/**
	 * 生成有偏好值的DataModel
	 * @param dbServerName
	 * @param dbUser
	 * @param dbPassword
	 * @param dbDatabaseName
	 * @param preferenceTable
	 * @param userIDColumn
	 * @param itemIDColumn
	 * @param preferenceColumn
	 * @param timestampColumn
	 * @return 有偏好值的DataModel
	 */
	public DataModel createDataModelWithPrefValue(String dbServerName,
			String dbUser, String dbPassword, String dbDatabaseName,
			String preferenceTable, String userIDColumn, String itemIDColumn,
			String preferenceColumn, String timestampColumn){
		this.dbServerName = dbServerName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
		this.dbDatabaseName = dbDatabaseName;
		this.preferenceTable = preferenceTable;
		this.userIDColumn = userIDColumn;
		this.itemIDColumn = itemIDColumn;
		this.preferenceColumn = preferenceColumn;
		this.timestampColumn = timestampColumn;
		initMySQLDataSource(dbServerName, dbUser, dbPassword, dbDatabaseName);
		this.setDataModel(new MySQLJDBCDataModel(this.mysqlDataSource, preferenceTable, userIDColumn, itemIDColumn, preferenceColumn, timestampColumn));
		return this.getDataModel();
	}
	
	/**
	 * 生成无偏好值的DataModel
	 * @param dbServerName
	 * @param dbUser
	 * @param dbPassword
	 * @param dbDatabaseName
	 * @param preferenceTable
	 * @param userIDColumn
	 * @param itemIDColumn
	 * @param timestampColumn
	 * @return 布尔型偏好的DataModel
	 * @throws TasteException
	 */
	@SuppressWarnings("deprecation")
	public DataModel createDataModelWithoutPrefValue(String dbServerName,
			String dbUser, String dbPassword, String dbDatabaseName,
			String preferenceTable, String userIDColumn, String itemIDColumn,
			String timestampColumn) throws TasteException{
		this.dbServerName = dbServerName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
		this.dbDatabaseName = dbDatabaseName;
		this.preferenceTable = preferenceTable;
		this.userIDColumn = userIDColumn;
		this.itemIDColumn = itemIDColumn;
		this.timestampColumn = timestampColumn;
		initMySQLDataSource(dbServerName, dbUser, dbPassword, dbDatabaseName);
		//这里采用了一个取巧的方法：将itemIDColumn伪装成preferenceColumn传入，反正最后该字段会被布尔型DataModel忽略
		this.setDataModel(new GenericBooleanPrefDataModel(new MySQLJDBCDataModel(this.mysqlDataSource, preferenceTable, userIDColumn, itemIDColumn, itemIDColumn, timestampColumn)));	
		return this.getDataModel();
	}

}
