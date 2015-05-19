package edu.recm.config.model;

import java.util.List;

/**
 * 存储多个相似度度量参数配置模型的列表
 * @author niuzhixiang
 *
 */
public class SimilarityListConfigModel {

	private List<AbstractSimilarityConfigModel> similarityConfigModelList;

	/**
	 * 相似度度量参数配置模型的列表
	 * @return
	 */
	public List<AbstractSimilarityConfigModel> getSimilarityConfigModelList() {
		return similarityConfigModelList;
	}

	public void setSimilarityConfigModelList(
			List<AbstractSimilarityConfigModel> similarityConfigModelList) {
		this.similarityConfigModelList = similarityConfigModelList;
	}

	public SimilarityListConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimilarityListConfigModel(
			List<AbstractSimilarityConfigModel> similarityConfigModelList) {
		super();
		this.similarityConfigModelList = similarityConfigModelList;
	}
	
}
