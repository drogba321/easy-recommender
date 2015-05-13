package test.algorithm.algorithm;

import org.junit.Ignore;
import org.junit.Test;

import edu.recm.algorithm.algorithm.ItemBasedCFRecommender;
import edu.recm.algorithm.algorithm.UserBasedCFRecommender;
import edu.recm.algorithm.data.FilePreferenceData;
import edu.recm.algorithm.data.MySQLPreferenceData;

/**
 * 测试基于项目的协同过滤推荐器
 * @author niuzhixiang
 *
 */
public class ItemBasedCFRecommenderTest {
	
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
		ItemBasedCFRecommender recommender = new ItemBasedCFRecommender("job-recommender", mySQLPreferenceData, "pearsonCorrelationSimilarity");
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
		ItemBasedCFRecommender recommender = new ItemBasedCFRecommender("job-recommender", mySQLPreferenceData, "tanimotoCoefficientSimilarity");
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
		ItemBasedCFRecommender recommender = new ItemBasedCFRecommender("job-recommender", filePreferenceData, "pearsonCorrelationSimilarity");
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
		ItemBasedCFRecommender recommender = new ItemBasedCFRecommender("job-recommender", filePreferenceData, "logLikelihoodSimilarity");
		recommender.doRecommend(21, 10);
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}
}
