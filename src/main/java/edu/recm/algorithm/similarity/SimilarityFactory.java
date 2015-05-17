package edu.recm.algorithm.similarity;

import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import edu.recm.algorithm.data.AbstractPreferenceData;

/**
 * 创建相似度度量方法的工厂类
 * @author niuzhixiang
 *
 */
public class SimilarityFactory {
	
	/**
	 * 创建指定类型的用户相似度度量机制
	 * @param similarityType 指定的类型
	 * @param dataModel 数据源
	 * @return 指定类型的用户相似度度量机制实现类
	 * @throws TasteException
	 */
	public static UserSimilarity createUserSimilarity(String similarityType, DataModel dataModel) throws TasteException {
		if (similarityType.toLowerCase().equals("pearsoncorrelationsimilarity")) {
			return new PearsonCorrelationSimilarity(dataModel); 
		}
		if (similarityType.toLowerCase().equals("euclideandistancesimilarity")) {
			return new EuclideanDistanceSimilarity(dataModel);
		}
		if (similarityType.toLowerCase().equals("tanimotocoefficientsimilarity")) {
			return new TanimotoCoefficientSimilarity(dataModel);
		}
		if (similarityType.toLowerCase().equals("spearmancorrelationsimilarity")) {
			return new SpearmanCorrelationSimilarity(dataModel);
		}
		if (similarityType.toLowerCase().equals("loglikelihoodsimilarity")) {
			return new LogLikelihoodSimilarity(dataModel);
		}
		if (similarityType.toLowerCase().equals("cityblocksimilarity")) {
			return new CityBlockSimilarity(dataModel);
		}
		//默认返回皮尔逊相关系数相似度
		return new PearsonCorrelationSimilarity(dataModel);
	}
	
	/**
	 * 创建指定类型的项目相似度度量机制
	 * @param similarityType 指定的类型
	 * @param dataModel 数据源
	 * @return 指定类型的项目相似度度量机制实现类
	 * @throws TasteException
	 */
	public static ItemSimilarity createItemSimilarity(String similarityType, DataModel dataModel) throws TasteException {
		if (similarityType.toLowerCase().equals("pearsoncorrelationsimilarity")) {
			return new PearsonCorrelationSimilarity(dataModel); 
		}
		if (similarityType.toLowerCase().equals("euclideandistancesimilarity")) {
			return new EuclideanDistanceSimilarity(dataModel);
		}
		if (similarityType.toLowerCase().equals("tanimotocoefficientsimilarity")) {
			return new TanimotoCoefficientSimilarity(dataModel);
		}
		if (similarityType.toLowerCase().equals("loglikelihoodsimilarity")) {
			return new LogLikelihoodSimilarity(dataModel);
		}
		if (similarityType.toLowerCase().equals("cityblocksimilarity")) {
			return new CityBlockSimilarity(dataModel);
		}
		//默认返回皮尔逊相关系数相似度
		return new PearsonCorrelationSimilarity(dataModel);
	}
}
