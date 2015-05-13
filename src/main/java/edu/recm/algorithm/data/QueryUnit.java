package edu.recm.algorithm.data;

import org.apache.lucene.search.BooleanClause.Occur;

/**
 * 表示单个查询条件的POJO类
 * @author niuzhixiang
 *
 */
public class QueryUnit {

	/**
	 * 用户特征字段
	 */
	private String userField;
	
	/**
	 * 项目特征字段
	 */
	private String itemField;
	
	/**
	 * 单个查询条件的逻辑关系
	 */
	private Occur occur;
	
	public String getUserField() {
		return userField;
	}
	public void setUserField(String userField) {
		this.userField = userField;
	}
	public String getItemField() {
		return itemField;
	}
	public void setItemField(String itemField) {
		this.itemField = itemField;
	}
	public Occur getOccur() {
		return occur;
	}
	public void setOccur(Occur occur) {
		this.occur = occur;
	}
	
	public QueryUnit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QueryUnit(String userField, String itemField, Occur occur) {
		super();
		this.userField = userField;
		this.itemField = itemField;
		this.occur = occur;
	}
	
}
