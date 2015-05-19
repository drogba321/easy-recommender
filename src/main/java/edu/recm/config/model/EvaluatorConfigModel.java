package edu.recm.config.model;

/**
 * 推荐算法评估机制的参数配置模型
 * @author niuzhixiang
 *
 */
public class EvaluatorConfigModel {
	
	private Boolean deviation;
	
	private Boolean precision;
	
	private Boolean recall;
	
	private Boolean runningTime;

	/**
	 * 是否评估预测评分差（仅在推荐算法为基本推荐算法、并且是协同过滤推荐算法时该值才有意义）
	 * @return
	 */
	public Boolean getDeviation() {
		return deviation;
	}

	public void setDeviation(Boolean deviation) {
		this.deviation = deviation;
	}

	/**
	 * 是否评估查准率（仅在推荐算法为基本推荐算法、并且是协同过滤推荐算法时该值才有意义）
	 * @return
	 */
	public Boolean getPrecision() {
		return precision;
	}

	public void setPrecision(Boolean precision) {
		this.precision = precision;
	}

	/**
	 * 是否评估查全率（仅在推荐算法为基本推荐算法、并且是协同过滤推荐算法时该值才有意义）
	 * @return
	 */
	public Boolean getRecall() {
		return recall;
	}

	public void setRecall(Boolean recall) {
		this.recall = recall;
	}

	/**
	 * 是否评估算法运行时间
	 * @return
	 */
	public Boolean getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Boolean runningTime) {
		this.runningTime = runningTime;
	}

	public EvaluatorConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EvaluatorConfigModel(Boolean deviation, Boolean precision,
			Boolean recall, Boolean runningTime) {
		super();
		this.deviation = deviation;
		this.precision = precision;
		this.recall = recall;
		this.runningTime = runningTime;
	}
	
}
