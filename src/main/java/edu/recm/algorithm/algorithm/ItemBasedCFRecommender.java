package edu.recm.algorithm.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import edu.recm.algorithm.data.AbstractPreferenceData;
import edu.recm.algorithm.data.ResultBean;
import edu.recm.algorithm.similarity.SimilarityFactory;
import edu.recm.algorithm.similarity.UserNeighborhoodFactory;

/**
 * 基于项目的协同过滤推荐器
 * @author niuzhixiang
 *
 */
public class ItemBasedCFRecommender implements edu.recm.algorithm.algorithm.Recommender {
	
	/**
	 * 该推荐器所属的推荐系统名称
	 */
	private String recommenderSystemName;
	
	/**
	 * 用户偏好数据源
	 */
	private AbstractPreferenceData preferenceData;
	
	/**
	 * 该推荐器所使用的项目相似度度量方法
	 */
	private String itemSimilarityType;
	
	private Recommender recommender;

	public ItemBasedCFRecommender(String recommenderSystemName,
			AbstractPreferenceData preferenceData,
			String itemSimilarityType) throws TasteException {
		super();
		this.recommenderSystemName = recommenderSystemName;
		this.preferenceData = preferenceData;
		this.itemSimilarityType = itemSimilarityType;
		
		DataModel dataModel = this.preferenceData.getDataModel();
		ItemSimilarity itemSimilarity = SimilarityFactory.createItemSimilarity(this.itemSimilarityType, dataModel);
		this.recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
	}

	/**
	 * 为指定用户执行推荐，生成指定数目的推荐结果
	 * @param userid 用户ID
	 * @param resultNum 推荐结果数目
	 * @return
	 * @throws TasteException
	 */
	public List<ResultBean> doRecommend(int userid, int resultNum) throws Exception {
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
