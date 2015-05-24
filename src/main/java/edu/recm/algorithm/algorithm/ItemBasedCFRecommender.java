package edu.recm.algorithm.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

/**
 * 基于项目的协同过滤推荐器
 * @author niuzhixiang
 *
 */
public class ItemBasedCFRecommender extends AbstractCFRecommender {
	
	static Logger logger = Logger.getLogger(ItemBasedCFRecommender.class);

	public ItemBasedCFRecommender(String recommenderSystemName,
			AbstractPreferenceData preferenceData,
			String similarityType) throws TasteException {
		super(recommenderSystemName, preferenceData, similarityType);
		
		DataModel dataModel = this.getPreferenceData().getDataModel();
		ItemSimilarity itemSimilarity = SimilarityFactory.createItemSimilarity(this.getSimilarityType(), dataModel);
		this.setRecommender(new GenericItemBasedRecommender(dataModel, itemSimilarity));
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
