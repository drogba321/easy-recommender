package edu.recm.config.model;

/**
 * 完整的输入数据源参数配置模型
 * @author niuzhixiang
 *
 */
public class IntegralDataConfigModel {
	
	private String dataType;
	
	private String sourceType;
	
	private DBConfigModel dbConfigModel;
	
	private AbstractDataConfigModel dataConfigModel;

	/**
	 * 输入数据源的类型，取值为“content”或“preference”
	 * @return
	 */
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 输入数据源的来源，取值为“mysql”或“file”（当dataType取值为“content”时只能取值为“mysql”）
	 * @return
	 */
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * MySQL数据源连接配置模型。若该字段为null，说明是文件数据源
	 * @return
	 */
	public DBConfigModel getDbConfigModel() {
		return dbConfigModel;
	}

	public void setDbConfigModel(DBConfigModel dbConfigModel) {
		this.dbConfigModel = dbConfigModel;
	}

	/**
	 * 输入数据源的参数配置模型
	 * @return
	 */
	public AbstractDataConfigModel getDataConfigModel() {
		return dataConfigModel;
	}

	public void setDataConfigModel(AbstractDataConfigModel dataConfigModel) {
		this.dataConfigModel = dataConfigModel;
	}

	public IntegralDataConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IntegralDataConfigModel(String dataType, String sourceType,
			DBConfigModel dbConfigModel, AbstractDataConfigModel dataConfigModel) {
		super();
		this.dataType = dataType;
		this.sourceType = sourceType;
		this.dbConfigModel = dbConfigModel;
		this.dataConfigModel = dataConfigModel;
	}
	
}
