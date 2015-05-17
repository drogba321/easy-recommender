package test.algorithm.data;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.recm.algorithm.data.MySQLPreferenceData;

public class MySQLPreferenceDataTest {
	
	/**
	 * 测试有偏好值的情况
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testAccessMySQLPreferenceWithPrefValue() throws Exception{
		long preTime = System.currentTimeMillis();
		
		MySQLPreferenceData mySQLPreferenceData = new MySQLPreferenceData();
		mySQLPreferenceData.createDataModelWithPrefValue("localhost", "server", "server", "easyrecdemo", "applylog_pref", "STUDENTID", "JOBID", "PREFERENCE", "APPLYDATE");
		DataModel dataModel = mySQLPreferenceData.getDataModel();
		UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, dataModel);
		Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
		List<RecommendedItem> recommendedItems = recommender.recommend(21, 10);
		for (RecommendedItem recommendedItem : recommendedItems) {
			System.out.println(recommendedItem);
		}
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}
	
	/**
	 * 测试无偏好值的情况
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testAccessMySQLPreferenceWithoutPrefValue() throws Exception{
		long preTime = System.currentTimeMillis();
		
		MySQLPreferenceData mySQLPreferenceData = new MySQLPreferenceData();
		mySQLPreferenceData.createDataModelWithoutPrefValue("localhost", "server", "server", "easyrecdemo", "applylog", "STUDENTID", "JOBID", null);
		DataModel dataModel = mySQLPreferenceData.getDataModel();
		UserSimilarity similarity = new TanimotoCoefficientSimilarity(dataModel);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
		Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
		List<RecommendedItem> recommendedItems = recommender.recommend(21, 10);
		for (RecommendedItem recommendedItem : recommendedItems) {
			System.out.println(recommendedItem);
		}
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}

}
