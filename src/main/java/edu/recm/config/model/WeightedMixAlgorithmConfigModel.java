package edu.recm.config.model;

import java.util.List;
import java.util.Map.Entry;

/**
 * 加权型混合推荐算法的参数配置模型
 * @author niuzhixiang
 *
 */
public class WeightedMixAlgorithmConfigModel extends
		AbstractMixAlgorithmConfigModel {
	
	private List<Entry<AbstractSingleAlgorithmConfigModel, Float>> list;

	/**
	 * 该List存储加权型混合推荐算法中包含的各个基本推荐算法及其相应的权重
	 * @return
	 */
	public List<Entry<AbstractSingleAlgorithmConfigModel, Float>> getList() {
		return list;
	}

	public void setList(List<Entry<AbstractSingleAlgorithmConfigModel, Float>> list) {
		this.list = list;
	}

	public WeightedMixAlgorithmConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeightedMixAlgorithmConfigModel(
			List<Entry<AbstractSingleAlgorithmConfigModel, Float>> list) {
		super();
		this.list = list;
	}

}
