package edu.recm.algorithm.algorithm;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

/**
 * Mahout中RecommenderBuilder的自定义实现类，在算法评估模块中使用
 * @author niuzhixiang
 *
 */
public class MyRecommenderBuilder implements RecommenderBuilder {
	
	private Recommender recommender;

	public Recommender getRecommender() {
		return recommender;
	}

	public void setRecommender(Recommender recommender) {
		this.recommender = recommender;
	}

	public MyRecommenderBuilder(Recommender recommender) {
		super();
		this.recommender = recommender;
	}

	public Recommender buildRecommender(DataModel dataModel)
			throws TasteException {
		// TODO Auto-generated method stub
		return this.getRecommender();
	}

}
