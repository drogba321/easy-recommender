package edu.recm.algorithm.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import edu.recm.algorithm.data.AbstractPreferenceData;
import edu.recm.algorithm.data.ResultBean;
import edu.recm.algorithm.similarity.SimilarityFactory;
import edu.recm.algorithm.similarity.UserNeighborhoodFactory;

/**
 * 基于用户的协同过滤推荐器
 * @author niuzhixiang
 *
 */
public class UserBasedCFRecommender {
	
	/**
	 * 该推荐器所属的推荐系统名称
	 */
	private String recommenderSystemName;
	
	/**
	 * 用户偏好数据源
	 */
	private AbstractPreferenceData preferenceData;
	
	/**
	 * 该推荐器所使用的用户相似度度量方法
	 */
	private String userSimilarityType;
	
	/**
	 * 固定大小的用户邻域大小，该字段为null时表示不使用固定大小的用户邻域，而使用基于阈值的用户邻域
	 */
	private Integer nNearestUser;
	
	/**
	 * 基于阈值的用户邻域阈值，该字段为null时表示不使用基于阈值的用户邻域，而使用固定大小的用户邻域
	 */
	private Double threshold;
	
	private Recommender recommender;

	public UserBasedCFRecommender(String recommenderSystemName, 
			AbstractPreferenceData preferenceData, String userSimilarityType, 
			Integer nNearestUser, Double threshold) 
					throws Exception {
		super();
		this.recommenderSystemName = recommenderSystemName;
		this.preferenceData = preferenceData;
		this.userSimilarityType = userSimilarityType;
		this.nNearestUser = nNearestUser;
		this.threshold = threshold;
		
		DataModel dataModel = this.preferenceData.getDataModel();
		UserSimilarity userSimilarity = SimilarityFactory.createUserSimilarity(this.userSimilarityType, dataModel);
		UserNeighborhood userNeighborhood = UserNeighborhoodFactory.createUserNeighborhood(this.nNearestUser, this.threshold, userSimilarity, dataModel);
		this.recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
	}

	/**
	 * 为指定用户执行推荐，生成指定数目的推荐结果
	 * @param userid 用户ID
	 * @param resultNum 推荐结果数目
	 * @return
	 * @throws TasteException
	 */
	public List<ResultBean> doRecommend(int userid, int resultNum) throws TasteException {
		List<RecommendedItem> recommendedItems = this.recommender.recommend(userid, resultNum);
		List<ResultBean> resultList = new ArrayList<ResultBean>();
		for (RecommendedItem recommendedItem : recommendedItems) {
			ResultBean rb = new ResultBean();
			rb.setId(Integer.parseInt(recommendedItem.getItemID()+""));
			rb.setScore(recommendedItem.getValue());
			System.out.println("id:" + rb.getId() + ", score:" + rb.getScore());
			resultList.add(rb);
		}
		return resultList;
	}

}
