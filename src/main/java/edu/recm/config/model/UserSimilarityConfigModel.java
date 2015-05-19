package edu.recm.config.model;

/**
 * 用户间的相似度度量方法参数配置模型
 * @author niuzhixiang
 *
 */
public class UserSimilarityConfigModel extends AbstractSimilarityConfigModel {

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

	public UserSimilarityConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserSimilarityConfigModel(String similarityType) {
		super();
		this.similarityType = similarityType;
	}
	
}
