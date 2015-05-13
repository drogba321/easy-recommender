package edu.cuc.dmt.demo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class RecommenderDemo {

	public static void main(String[] args) throws Exception {
		long pre = System.currentTimeMillis();
		recommendDemo();
		long after = System.currentTimeMillis();
		System.out.println("time last:" + (after-pre));
		evaluatePrecisionAndRecall();
	}

	/**
	 * 一个简单而完整的推荐Demo
	 * @throws Exception
	 */
	public static void recommendDemo() throws Exception {
		//读取数据集
		DataModel model = new FileDataModel(new File(RecommenderDemo.class.getClassLoader().getResource("demo/intro.csv").toURI()));
		//定义相似度计算方法，此处使用皮尔逊相似度
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		//定义相似用户，此处使用最近的N个用户作为相似用户
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
		//使用前面定义的相似度计算方法和相似用户建立一个Recommender
		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		//使用Recommender进行推荐，此处为id=1的用户推荐1个物品
		List<RecommendedItem> recommendations = recommender.recommend(1, 1);
		//打印推荐结果
		for (RecommendedItem recommendedItem : recommendations) {
			System.out.println(recommendedItem);
		}
	}
	
	/**
	 * 评估推荐准确率（Precision）和召回率（Recall）的Demo
	 * @throws Exception
	 */
	public static void evaluatePrecisionAndRecall() throws Exception {
		//生成可重复的结果，也就是说保证每次调用时的训练数据和测试数据都是不变的，从而每次评估的结果也都是不变的
		RandomUtils.useTestSeed();
		//读取数据集
		DataModel model = new FileDataModel(new File(RecommenderDemo.class.getClassLoader().getResource("demo/intro.csv").toURI()));
		//定义评估器，用于评估Recommender的性能，包括准确率（Precision）和召回率（Recall）
		RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
		//评估器不能直接使用Recommender作为参数，而是需要一个RecommenderBuilder作为参数
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			//RecommenderBuilder接口中的方法，用于创建一个Recommender
			public Recommender buildRecommender(DataModel model)
					throws TasteException {
				UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood, similarity);
			}
		};
		//进行评估
		IRStatistics stats = evaluator.evaluate(recommenderBuilder, null, model, null, 2, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);	
		//打印评估结果中的准确率(Precision)和召回率（Recall）
		System.out.println(stats.getPrecision());
		System.out.println(stats.getRecall());
	}

}
