package edu.recm.algorithm.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
public class UserBasedCFRecommender extends AbstractCFRecommender {
	
	static Logger logger = Logger.getLogger(UserBasedCFRecommender.class);
	
	/**
	 * 固定大小的用户邻域大小，该字段为null时表示不使用固定大小的用户邻域，而使用基于阈值的用户邻域
	 */
	private Integer nNearestUser;
	
	/**
	 * 基于阈值的用户邻域阈值，该字段为null时表示不使用基于阈值的用户邻域，而使用固定大小的用户邻域
	 */
	private Double threshold;

	public Integer getnNearestUser() {
		return nNearestUser;
	}

	public void setnNearestUser(Integer nNearestUser) {
		this.nNearestUser = nNearestUser;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public UserBasedCFRecommender(String recommenderSystemName, 
			AbstractPreferenceData preferenceData, String similarityType, 
			Integer nNearestUser, Double threshold) 
					throws Exception {
		super(recommenderSystemName, preferenceData, similarityType);
		this.nNearestUser = nNearestUser;
		this.threshold = threshold;
		
		DataModel dataModel = this.getPreferenceData().getDataModel();
		UserSimilarity userSimilarity = SimilarityFactory.createUserSimilarity(this.getSimilarityType(), dataModel);
		UserNeighborhood userNeighborhood = UserNeighborhoodFactory.createUserNeighborhood(this.nNearestUser, this.threshold, userSimilarity, dataModel);
		this.setRecommender(new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity));
	}

	public List<ResultBean> doRecommend(int userid, int resultNum) throws Exception {
		List<RecommendedItem> recommendedItems = this.getRecommender().recommend(userid, resultNum);
		List<ResultBean> resultList = new ArrayList<ResultBean>();
		for (RecommendedItem recommendedItem : recommendedItems) {
			ResultBean rb = new ResultBean();
			rb.setId(Integer.parseInt(recommendedItem.getItemID()+""));
			rb.setScore(recommendedItem.getValue());
			logger.info("id:" + rb.getId() + ", score:" + rb.getScore());
			resultList.add(rb);
		}
		logger.info("========================");
		return resultList;
	}
}
