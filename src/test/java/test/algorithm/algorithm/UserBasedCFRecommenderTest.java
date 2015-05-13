package test.algorithm.algorithm;

import java.util.List;

import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.junit.Ignore;
import org.junit.Test;

import edu.recm.algorithm.algorithm.UserBasedCFRecommender;
import edu.recm.algorithm.data.FilePreferenceData;
import edu.recm.algorithm.data.MySQLPreferenceData;

/**
 * 测试基于用户的协同过滤推荐器
 * @author niuzhixiang
 *
 */
public class UserBasedCFRecommenderTest {
	
	/**
	 * 测试输入数据源为MySQL、有偏好值的情况
	 * @throws Exception 
	 */
	@Test
	@Ignore
	public void testdoRecommendMySQLWithPrefValue() throws Exception {
		long preTime = System.currentTimeMillis();
		
		MySQLPreferenceData mySQLPreferenceData = new MySQLPreferenceData();
		mySQLPreferenceData.createDataModelWithPrefValue("localhost", "server", "server", "easyrecdemo", "applylog_pref", "STUDENTID", "JOBID", "PREFERENCE", "APPLYDATE");
		UserBasedCFRecommender recommender = new UserBasedCFRecommender("job-recommender", mySQLPreferenceData, "euclideanDistanceSimilarity", 10, null);
		recommender.doRecommend(21, 10);
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}

	/**
	 * 测试输入数据源为MySQL、无偏好值的情况
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testdoRecommendMySQLWithoutPrefValue() throws Exception {
		long preTime = System.currentTimeMillis();
		
		MySQLPreferenceData mySQLPreferenceData = new MySQLPreferenceData();
		mySQLPreferenceData.createDataModelWithoutPrefValue("localhost", "server", "server", "easyrecdemo", "applylog", "STUDENTID", "JOBID", "APPLYDATE");
		UserBasedCFRecommender recommender = new UserBasedCFRecommender("job-recommender", mySQLPreferenceData, "tanimotoCoefficientSimilarity", 100, null);
		recommender.doRecommend(21, 10);
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}
	
	/**
	 * 测试输入数据源为文件、有偏好值的情况
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testdoRecommendFileWithPrefValue() throws Exception {
		long preTime = System.currentTimeMillis();
		
		FilePreferenceData filePreferenceData = new FilePreferenceData("D:/preferenceData/applylog_pref.csv");
		UserBasedCFRecommender recommender = new UserBasedCFRecommender("job-recommender", filePreferenceData, "pearsonCorrelationSimilarity", 10, null);
		recommender.doRecommend(21, 10);
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}
	
	/**
	 * 测试输入数据源为文件、无偏好值的情况
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testdoRecommendFileWithoutPrefValue() throws Exception {
		long preTime = System.currentTimeMillis();
		
		FilePreferenceData filePreferenceData = new FilePreferenceData("D:/preferenceData/applylog.csv");
		UserBasedCFRecommender recommender = new UserBasedCFRecommender("job-recommender", filePreferenceData, "tanimotoCoefficientSimilarity", null, 0.14);
		recommender.doRecommend(21, 10);
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}
}
