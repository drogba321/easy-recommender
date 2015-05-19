package edu.recm.config.model;

import java.util.List;
import java.util.Map.Entry;

/**
 * 瀑布型混合推荐算法的参数配置模型
 * @author niuzhixiang
 *
 */
public class WaterfallMixAlgorithmConfigModel extends AbstractMixAlgorithmConfigModel {

	private List<Entry<AbstractSingleAlgorithmConfigModel, Integer>> list;

	/**
	 * 该List存储瀑布型混合推荐算法中包含的各个基本推荐算法及其相应的筛选数目
	 * @return
	 */
	public List<Entry<AbstractSingleAlgorithmConfigModel, Integer>> getList() {
		return list;
	}

	public void setList(
			List<Entry<AbstractSingleAlgorithmConfigModel, Integer>> list) {
		this.list = list;
	}

	public WaterfallMixAlgorithmConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WaterfallMixAlgorithmConfigModel(
			List<Entry<AbstractSingleAlgorithmConfigModel, Integer>> list) {
		super();
		this.list = list;
	}
}
