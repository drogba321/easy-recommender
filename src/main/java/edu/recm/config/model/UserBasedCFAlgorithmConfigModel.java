package edu.recm.config.model;

/**
 * 基于用户的协同过滤推荐算法参数配置模型
 * @author niuzhixiang
 *
 */
public class UserBasedCFAlgorithmConfigModel extends
		AbstractSingleAlgorithmConfigModel {
	
	private String userNeighborhoodType;
	
	private Double threshold;
	
	private Integer nUser;

	/**
	 * 用户邻域类型，取值为“nearestN”或“threshold”
	 * @return
	 */
	public String getUserNeighborhoodType() {
		return userNeighborhoodType;
	}

	public void setUserNeighborhoodType(String userNeighborhoodType) {
		this.userNeighborhoodType = userNeighborhoodType;
	}

	/**
	 * 基于阈值的用户邻域的阈值，当userNeighborhoodType字段取值为“threshold”时该字段不为null
	 * @return
	 */
	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	/**
	 * 固定大小的用户邻域的大小，当userNeighborhoodType字段取值为“nearestN”时该字段不为null
	 * @return
	 */
	public Integer getnUser() {
		return nUser;
	}

	public void setnUser(Integer nUser) {
		this.nUser = nUser;
	}

	public UserBasedCFAlgorithmConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBasedCFAlgorithmConfigModel(String userNeighborhoodType,
			Double threshold, Integer nUser) {
		super();
		this.userNeighborhoodType = userNeighborhoodType;
		this.threshold = threshold;
		this.nUser = nUser;
	}

}
