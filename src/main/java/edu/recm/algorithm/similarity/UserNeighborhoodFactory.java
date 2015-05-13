package edu.recm.algorithm.similarity;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserNeighborhoodFactory {

	private static UserNeighborhood createNearestNUserNeighborhood(Integer n, UserSimilarity userSimilarity, DataModel dataModel) throws TasteException {
		return new NearestNUserNeighborhood(n, userSimilarity, dataModel);
	}
	
	private static UserNeighborhood createThresholdUserNeighborhood(Double threshold, UserSimilarity userSimilarity, DataModel dataModel) throws TasteException {
		return new ThresholdUserNeighborhood(threshold, userSimilarity, dataModel);
	}
	
	public static UserNeighborhood createUserNeighborhood(Integer n, Double threshold, UserSimilarity userSimilarity, DataModel dataModel) throws Exception {
		if (n != null && threshold == null) {
			return createNearestNUserNeighborhood(n, userSimilarity, dataModel);
		}
		if (n == null && threshold != null) {
			return createThresholdUserNeighborhood(threshold, userSimilarity, dataModel);
		}
		throw new Exception("创建用户邻域失败！n和threshold中有且仅有一个为null！");
	}
}
