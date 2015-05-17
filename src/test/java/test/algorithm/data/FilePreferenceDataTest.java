package test.algorithm.data;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
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

import edu.recm.algorithm.data.FilePreferenceData;

public class FilePreferenceDataTest {

	/**
	 * 测试有偏好值的情况
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testAccessFilePreferenceWithPrefValue() throws Exception {
		long preTime = System.currentTimeMillis();
		
		FilePreferenceData filePreferenceData = new FilePreferenceData("D:/preferenceData/applylog_pref.csv");
		DataModel dataModel = filePreferenceData.getDataModel();
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
	
	@Test
	@Ignore
	@SuppressWarnings("deprecation")
	public void testAccessFilePreferenceWithoutPrefValue() throws Exception {
		long preTime = System.currentTimeMillis();
		
		FilePreferenceData filePreferenceData = new FilePreferenceData("D:/preferenceData/applylog.csv");
		DataModel dataModel = new GenericBooleanPrefDataModel(filePreferenceData.getDataModel());
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
