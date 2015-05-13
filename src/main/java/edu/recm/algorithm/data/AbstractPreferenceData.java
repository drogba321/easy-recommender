package edu.recm.algorithm.data;

import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;

/**
 * 表示用户偏好数据的抽象父类
 * @author niuzhixiang
 *
 */
public abstract class AbstractPreferenceData {
	
	private DataModel dataModel;

	public DataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

}
