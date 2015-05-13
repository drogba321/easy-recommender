package edu.recm.algorithm.data;

/**
 * 表示单个推荐结果的POJO类
 * @author niuzhixiang
 *
 */
public class ResultBean{
	
	/**
	 * 项目id
	 */
	private int id;
	
	/**
	 * 项目评分
	 */
	private float score;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public ResultBean(int id, float score) {
		super();
		this.id = id;
		this.score = score;
	}
	public ResultBean() {
		super();
		// TODO Auto-generated constructor stub
	}
}
