package test.algorithm.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Ignore;
import org.junit.Test;

import edu.recm.algorithm.algorithm.ContentBasedRecommender;
import edu.recm.algorithm.algorithm.ItemBasedCFRecommender;
import edu.recm.algorithm.algorithm.MyRecommender;
import edu.recm.algorithm.algorithm.UserBasedCFRecommender;
import edu.recm.algorithm.algorithm.WaterfallMixRecommender;
import edu.recm.algorithm.data.FilePreferenceData;
import edu.recm.algorithm.data.MySQLContentData;
import edu.recm.algorithm.data.MySQLPreferenceData;
import edu.recm.algorithm.data.QueryUnit;
import edu.recm.algorithm.data.ResultBean;
import edu.recm.util.MyEntry;

public class WaterfallMixRecommenderTest {

	/**
	 * 测试“上一个Recommender是基于内容的推荐，当前Recommender是基于项目的协同过滤推荐（有偏好值）”的功能
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testPreContentBasedNextCFWithPrefValue() throws Exception {
		WaterfallMixRecommender waterfallMixRecommender = new WaterfallMixRecommender();
		
		//创建“上一个”Recommender（基于内容的推荐器），并获取推荐结果集
		List<QueryUnit> queries = new ArrayList<QueryUnit>();
		queries.add(new QueryUnit("DEGREE", "DEGREE", Occur.MUST));
		queries.add(new QueryUnit("HOMEPLACE", "AREA", Occur.MUST));	
		MySQLContentData contentData = new MySQLContentData("localhost", "server", "server", "easyrecdemo", "student", "job", queries);
		ContentBasedRecommender preRecommender = new ContentBasedRecommender("job-recommender", contentData, null);
		List<ResultBean> inputList = preRecommender.doRecommend(4, 10);
		
		//创建“当前”Recommender（基于项目的协同过滤推荐器）
		MySQLPreferenceData mySQLPreferenceData = new MySQLPreferenceData();
		mySQLPreferenceData.createDataModelWithPrefValue("localhost", "server", "server", "easyrecdemo", "applylog_pref", "STUDENTID", "JOBID", "PREFERENCE", "APPLYDATE");
		ItemBasedCFRecommender currentRecommender = new ItemBasedCFRecommender("job-recommender", mySQLPreferenceData, "pearsoncorrelationsimilarity");	
		
		//执行推荐
		List<ResultBean> list = waterfallMixRecommender.currentIsCF(currentRecommender, inputList, 4, 5);
		for (ResultBean resultBean : list) {
			System.out.println("id:" + resultBean.getId() + ", score:" + resultBean.getScore());
		}
	}
	
	/**
	 * 测试“上一个Recommender是基于内容的推荐，当前Recommender是基于项目的协同过滤推荐（无偏好值）”的功能
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testPreContentBasedNextCFWithoutPrefValue() throws Exception {
		WaterfallMixRecommender waterfallMixRecommender = new WaterfallMixRecommender();
		
		//创建“上一个”Recommender（基于内容的推荐器），并获取推荐结果集
		List<QueryUnit> queries = new ArrayList<QueryUnit>();
		queries.add(new QueryUnit("DEGREE", "DEGREE", Occur.MUST));
		queries.add(new QueryUnit("HOMEPLACE", "AREA", Occur.MUST));	
		MySQLContentData contentData = new MySQLContentData("localhost", "server", "server", "easyrecdemo", "student", "job", queries);
		ContentBasedRecommender preRecommender = new ContentBasedRecommender("job-recommender", contentData, null);
		List<ResultBean> inputList = preRecommender.doRecommend(1, 2000);
		System.out.println(inputList.size());
		
		//创建“当前”Recommender（基于项目的协同过滤推荐器）
		MySQLPreferenceData mySQLPreferenceData = new MySQLPreferenceData();
		mySQLPreferenceData.createDataModelWithoutPrefValue("localhost", "server", "server", "easyrecdemo", "applylog", "STUDENTID", "JOBID", "APPLYDATE");
		//ItemBasedCFRecommender currentRecommender = new ItemBasedCFRecommender("job-recommender", mySQLPreferenceData, "tanimotoCoefficientSimilarity");	
		UserBasedCFRecommender currentRecommender = new UserBasedCFRecommender("job-recommender", mySQLPreferenceData, "tanimotoCoefficientSimilarity", 100, null);
		
		//执行推荐
		List<ResultBean> list = waterfallMixRecommender.currentIsCF(currentRecommender, inputList, 1, 20);
		for (ResultBean resultBean : list) {
			System.out.println("id:" + resultBean.getId() + ", score:" + resultBean.getScore());
		}
	}
	
	/**
	 * 测试“上一个Recommender是协同过滤推荐，当前Recommender是基于内容的推荐”的功能
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testPreCFNextContentBased() throws Exception {
		WaterfallMixRecommender waterfallMixRecommender = new WaterfallMixRecommender();

		//创建“上一个”Recommender（协同过滤推荐器，基于用户或基于项目的协同过滤皆可，有偏好值或无偏好值皆可）
		MySQLPreferenceData mySQLPreferenceData = new MySQLPreferenceData();
		mySQLPreferenceData.createDataModelWithoutPrefValue("localhost", "server", "server", "easyrecdemo", "applylog", "STUDENTID", "JOBID", "APPLYDATE");
		//UserBasedCFRecommender preRecommender = new UserBasedCFRecommender("job-recommender", mySQLPreferenceData, "tanimotocoefficientsimilarity", 100, null);
		ItemBasedCFRecommender preRecommender = new ItemBasedCFRecommender("job-recommender", mySQLPreferenceData, "tanimotocoefficientsimilarity");	
		List<ResultBean> inputList = preRecommender.doRecommend(21, 10);
		
		//创建“当前”Recommender（基于内容的推荐器），并获取推荐结果集
		List<QueryUnit> queries = new ArrayList<QueryUnit>();
		queries.add(new QueryUnit("DEGREE", "DEGREE", Occur.SHOULD));
		queries.add(new QueryUnit("HOMEPLACE", "AREA", Occur.SHOULD));	
		MySQLContentData contentData = new MySQLContentData("localhost", "server", "server", "easyrecdemo", "student", "job", queries);
		ContentBasedRecommender currentRecommender = new ContentBasedRecommender("job-recommender", contentData, null);
	
		List<ResultBean> list = waterfallMixRecommender.currentIsContentBased(currentRecommender, inputList, 21, 5);

	}
	
	/**
	 * 测试瀑布型混合推荐的执行方法
	 * @throws Exception
	 */
	@Test
	//@Ignore
	public void testDoRecommend() throws Exception {
		long preTime = System.currentTimeMillis();
	
		//创建第一个推荐器（基于内容的推荐器）
		List<QueryUnit> queries = new ArrayList<QueryUnit>();
		queries.add(new QueryUnit("DEGREE", "DEGREE", Occur.SHOULD));
		queries.add(new QueryUnit("HOMEPLACE", "AREA", Occur.SHOULD));
		MySQLContentData contentData = new MySQLContentData("localhost", "server", "server", "easyrecdemo", "student", "job", queries);
		ContentBasedRecommender contentBasedRecommender = new ContentBasedRecommender("job-recommender", contentData, null);
		
		//创建第二个推荐器（基于用户的协同过滤推荐器、数据源为MySQL、有偏好值）
		MySQLPreferenceData mySQLPreferenceData1 = new MySQLPreferenceData();
		mySQLPreferenceData1.createDataModelWithPrefValue("localhost", "server", "server", "easyrecdemo", "applylog_pref", "STUDENTID", "JOBID", "PREFERENCE", "APPLYDATE");
		UserBasedCFRecommender cfRecommender1 = new UserBasedCFRecommender("job-recommender", mySQLPreferenceData1, "pearsonCorrelationSimilarity", 10, null);
		
		//创建第三个推荐器（基于用户的协同过滤推荐器、数据源为MySQL、无偏好值）
		MySQLPreferenceData mySQLPreferenceData2 = new MySQLPreferenceData();
		mySQLPreferenceData2.createDataModelWithoutPrefValue("localhost", "server", "server", "easyrecdemo", "applylog", "STUDENTID", "JOBID", "APPLYDATE");
		UserBasedCFRecommender cfRecommender2 = new UserBasedCFRecommender("job-recommender", mySQLPreferenceData2, "tanimotoCoefficientSimilarity", 100, null);
		
		//创建第四个推荐器（基于用户的协同过滤推荐器、数据源为文件、有偏好值）
		FilePreferenceData filePreferenceData1 = new FilePreferenceData("D:/preferenceData/applylog_pref.csv");
		UserBasedCFRecommender cfRecommender3 = new UserBasedCFRecommender("job-recommender", filePreferenceData1, "pearsonCorrelationSimilarity", 10, null);
		
		//创建第五个推荐器（基于用户的协同过滤推荐器、数据源为文件、无偏好值）
		FilePreferenceData filePreferenceData2 = new FilePreferenceData("D:/preferenceData/applylog.csv");
		UserBasedCFRecommender cfRecommender4 = new UserBasedCFRecommender("job-recommender", filePreferenceData2, "tanimotoCoefficientSimilarity", null, 0.14);
		
		//创建第六个推荐器（基于项目的协同过滤推荐器、数据源为MySQL、有偏好值）
		MySQLPreferenceData mySQLPreferenceData3 = new MySQLPreferenceData();
		mySQLPreferenceData3.createDataModelWithPrefValue("localhost", "server", "server", "easyrecdemo", "applylog_pref", "STUDENTID", "JOBID", "PREFERENCE", "APPLYDATE");
		ItemBasedCFRecommender cfRecommender5 = new ItemBasedCFRecommender("job-recommender", mySQLPreferenceData3, "pearsonCorrelationSimilarity");
		
		//创建第七个推荐器（基于项目的协同过滤推荐器、数据源为MySQL、无偏好值）
		MySQLPreferenceData mySQLPreferenceData4 = new MySQLPreferenceData();
		mySQLPreferenceData4.createDataModelWithoutPrefValue("localhost", "server", "server", "easyrecdemo", "applylog", "STUDENTID", "JOBID", "APPLYDATE");
		ItemBasedCFRecommender cfRecommender6 = new ItemBasedCFRecommender("job-recommender", mySQLPreferenceData4, "tanimotoCoefficientSimilarity");
		
		//创建第八个推荐器（基于项目的协同过滤推荐器、数据源为文件、有偏好值）
		FilePreferenceData filePreferenceData3 = new FilePreferenceData("D:/preferenceData/applylog_pref.csv");
		ItemBasedCFRecommender cfRecommender7 = new ItemBasedCFRecommender("job-recommender", filePreferenceData3, "pearsonCorrelationSimilarity");
		
		//创建第九个推荐器（基于项目的协同过滤推荐器、数据源为文件、无偏好值）
		FilePreferenceData filePreferenceData4 = new FilePreferenceData("D:/preferenceData/applylog.csv");
		ItemBasedCFRecommender cfRecommender8 = new ItemBasedCFRecommender("job-recommender", filePreferenceData4, "logLikelihoodSimilarity");
		
		
		//创建瀑布型混合推荐器
		MyEntry<MyRecommender, Integer> entry1 = new MyEntry<MyRecommender, Integer>(contentBasedRecommender, 100);
		MyEntry<MyRecommender, Integer> entry2 = new MyEntry<MyRecommender, Integer>(cfRecommender6, 30);
		
		List<Entry<MyRecommender, Integer>> recommenderList = new ArrayList<Map.Entry<MyRecommender,Integer>>();
		recommenderList.add(entry1);
		recommenderList.add(entry2);
		
		WaterfallMixRecommender waterfallMixRecommender = new WaterfallMixRecommender(recommenderList);
		waterfallMixRecommender.doRecommend(1, 20);
	
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");	
	}

}
