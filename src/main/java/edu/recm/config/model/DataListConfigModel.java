package edu.recm.config.model;

import java.util.List;

/**
 * 存储多个完整的输入数据源参数配置模型的列表
 * @author niuzhixiang
 *
 */
public class DataListConfigModel {
	
	private List<IntegralDataConfigModel> dataConfigModelList;

	/**
	 * 完整的输入数据源参数配置模型的列表
	 * @return
	 */
	public List<IntegralDataConfigModel> getDataConfigModelList() {
		return dataConfigModelList;
	}

	public void setDataConfigModelList(List<IntegralDataConfigModel> dataConfigModelList) {
		this.dataConfigModelList = dataConfigModelList;
	}

	public DataListConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataListConfigModel(List<IntegralDataConfigModel> dataConfigModelList) {
		super();
		this.dataConfigModelList = dataConfigModelList;
	}

}
