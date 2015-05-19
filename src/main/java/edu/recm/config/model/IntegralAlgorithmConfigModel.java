package edu.recm.config.model;

/**
 * 完整的推荐算法流程参数配置模型
 * @author niuzhixiang
 *
 */
public class IntegralAlgorithmConfigModel {

	private String algorithmType;
	
	private AbstractAlgorithmConfigModel algorithmConfigModel;

	/**
	 * 推荐算法类型，取值为“single”、“weighted”或“waterfall”
	 * @return
	 */
	public String getAlgorithmType() {
		return algorithmType;
	}

	public void setAlgorithmType(String algorithmType) {
		this.algorithmType = algorithmType;
	}

	/**
	 * 推荐算法流程的参数配置模型
	 * @return
	 */
	public AbstractAlgorithmConfigModel getAlgorithmConfigModel() {
		return algorithmConfigModel;
	}

	public void setAlgorithmConfigModel(
			AbstractAlgorithmConfigModel algorithmConfigModel) {
		this.algorithmConfigModel = algorithmConfigModel;
	}

	public IntegralAlgorithmConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IntegralAlgorithmConfigModel(String algorithmType,
			AbstractAlgorithmConfigModel algorithmConfigModel) {
		super();
		this.algorithmType = algorithmType;
		this.algorithmConfigModel = algorithmConfigModel;
	}
	
}
