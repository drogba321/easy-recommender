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

public class SimilarityFactory {
	
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
