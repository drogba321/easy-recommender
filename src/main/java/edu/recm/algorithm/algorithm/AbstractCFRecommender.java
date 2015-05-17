package edu.recm.algorithm.algorithm;

import java.util.List;



import org.apache.mahout.cf.taste.recommender.Recommender;

import edu.recm.algorithm.data.AbstractPreferenceData;
import edu.recm.algorithm.data.ResultBean;

/**
 * 协同过滤推荐器的抽象父类，它有两个实现类：ItemBasedCFRecommender和UserBasedCFRecommender
 * @author niuzhixiang
 *
 */
public abstract class AbstractCFRecommender implements MyRecommender {
	
	/**
	 * 该协同过滤推荐器所属的推荐系统名称
	 */
	private String recommenderSystemName;
	
	/**
	 * 用户偏好数据源
	 */
	private AbstractPreferenceData preferenceData;
	
	/**
	 * 该协同过滤推荐器所使用的相似度度量方法
	 */
	private String similarityType;
	
	/**
	 * Mahout的Recommender
	 */
	private Recommender recommender;

	public String getRecommenderSystemName() {
		return recommenderSystemName;
	}

	public void setRecommenderSystemName(String recommenderSystemName) {
		this.recommenderSystemName = recommenderSystemName;
	}

	public AbstractPreferenceData getPreferenceData() {
		return preferenceData;
	}

	public void setPreferenceData(AbstractPreferenceData preferenceData) {
		this.preferenceData = preferenceData;
	}

	public String getSimilarityType() {
		return similarityType;
	}

	public void setSimilarityType(String similarityType) {
		this.similarityType = similarityType;
	}
	
	public Recommender getRecommender() {
		return recommender;
	}

	public void setRecommender(Recommender recommender) {
		this.recommender = recommender;
	}
	
	public AbstractCFRecommender(String recommenderSystemName,
			AbstractPreferenceData preferenceData, String similarityType) {
		super();
		this.recommenderSystemName = recommenderSystemName;
		this.preferenceData = preferenceData;
		this.similarityType = similarityType;
	}
}
