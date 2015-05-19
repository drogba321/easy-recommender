package edu.recm.config.model;

/**
 * 表示单个查询对象的参数配置模型
 * @author niuzhixiang
 *
 */
public class QueryUnitConfigModel {
	
	private String userColumn;
	
	private String itemColumn;
	
	private String occur;

	/**
	 * 用户特征字段
	 * @return
	 */
	public String getUserColumn() {
		return userColumn;
	}

	public void setUserColumn(String userColumn) {
		this.userColumn = userColumn;
	}

	/**
	 * 项目特征字段
	 * @return
	 */
	public String getItemColumn() {
		return itemColumn;
	}

	public void setItemColumn(String itemColumn) {
		this.itemColumn = itemColumn;
	}

	/**
	 * 单个查询条件的逻辑关系
	 * @return
	 */
	public String getOccur() {
		return occur;
	}

	public void setOccur(String occur) {
		this.occur = occur;
	}

	public QueryUnitConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryUnitConfigModel(String userColumn, String itemColumn,
			String occur) {
		super();
		this.userColumn = userColumn;
		this.itemColumn = itemColumn;
		this.occur = occur;
	}
	
}
