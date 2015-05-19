package edu.recm.config.model;

/**
 * 项目间的相似度度量方法参数配置模型
 * @author niuzhixiang
 *
 */
public class ItemSimilarityConfigModel extends AbstractSimilarityConfigModel {

	private String similarityType;

	/**
	 * 相似度度量方法的类型
	 * @return
	 */
	public String getSimilarityType() {
		return similarityType;
	}

	public void setSimilarityType(String similarityType) {
		this.similarityType = similarityType;
	}

	public ItemSimilarityConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemSimilarityConfigModel(String similarityType) {
		super();
		this.similarityType = similarityType;
	}

}
