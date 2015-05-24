package edu.recm.algorithm.evaluate;

import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.common.RandomUtils;

import edu.recm.algorithm.algorithm.AbstractCFRecommender;
import edu.recm.algorithm.algorithm.MyRecommender;
import edu.recm.algorithm.algorithm.MyRecommenderBuilder;

/**
 * 推荐算法评估器，可以评估查准率、查全率、预测评分误差等指标（仅限于对协同过滤推荐器进行评估）;<br/>
 * 由于评估方法会进行大量计算与资源访问，容易引起内存溢出和资源泄露等操作系统问题，因此在硬件配置较低的情况下请慎用评估器
 * @author niuzhixiang
 *
 */
public class MyRecommenderEvaluator {
	
	/**
	 * 计算给定推荐器的查准率、查全率等各项指标（仅限于协同过滤推荐器）
	 * @param myRecommender 给定的推荐器
	 * @return 存有查准率、查全率等各项指标的IRStatistics对象
	 * @throws Exception
	 */
	public IRStatistics calculateIRStatistics(MyRecommender myRecommender) throws Exception {
		if (myRecommender instanceof AbstractCFRecommender) {
			RandomUtils.useTestSeed();
			RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
			RecommenderBuilder builder = new MyRecommenderBuilder(((AbstractCFRecommender) myRecommender).getRecommender());
			return evaluator.evaluate(builder, null, ((AbstractCFRecommender) myRecommender).getPreferenceData().getDataModel(), null, 5, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
		} else {
			throw new Exception("推荐器不是协同过滤推荐器，框架无法计算查准率和查全率等指标！");
		}
	}
	
	/**
	 * 计算给定推荐器的预测评分与实际评分的偏差（仅限于协同过滤推荐器）
	 * @param myRecommender 给定的推荐器
	 * @return 预测评分与实际评分的偏差
	 * @throws Exception
	 */
	public double calculateDeviation(MyRecommender myRecommender) throws Exception {
		if (myRecommender instanceof AbstractCFRecommender) {
			RandomUtils.useTestSeed();
			org.apache.mahout.cf.taste.eval.RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
			RecommenderBuilder builder = new MyRecommenderBuilder(((AbstractCFRecommender) myRecommender).getRecommender());
			return evaluator.evaluate(builder, null, ((AbstractCFRecommender) myRecommender).getPreferenceData().getDataModel(), 0.7, 0.3);
		} else {
			throw new Exception("推荐器不是协同过滤推荐器，框架无法计算预测评分误差！");
		}
	}

}
