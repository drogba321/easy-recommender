package edu.recm.algorithm.similarity;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * 创建用户邻域的工厂类
 * @author niuzhixiang
 *
 */
public class UserNeighborhoodFactory {

	/**
	 * 创建固定大小的用户邻域
	 * @param n 用户邻域的大小
	 * @param userSimilarity 用户相似度度量机制
	 * @param dataModel 数据源
	 * @return 固定大小的用户邻域对象
	 * @throws TasteException
	 */
	private static UserNeighborhood createNearestNUserNeighborhood(Integer n, UserSimilarity userSimilarity, DataModel dataModel) throws TasteException {
		return new NearestNUserNeighborhood(n, userSimilarity, dataModel);
	}
	
	/**
	 * 创建基于阈值的用户邻域
	 * @param threshold 阈值
	 * @param userSimilarity 用户相似度度量机制
	 * @param dataModel 数据源
	 * @return 基于阈值的用户邻域对象
	 * @throws TasteException
	 */
	private static UserNeighborhood createThresholdUserNeighborhood(Double threshold, UserSimilarity userSimilarity, DataModel dataModel) throws TasteException {
		return new ThresholdUserNeighborhood(threshold, userSimilarity, dataModel);
	}
	
	/**
	 * 创建指定类型的用户邻域（固定大小的、或基于阈值的）;<br/>
	 * 该方法参数n和threshold有且仅有一个为null
	 * @param n 固定大小的用户邻域的大小，该参数为null时表示应当创建基于阈值的用户邻域
	 * @param threshold 基于阈值的用户邻域的阈值，该参数为null时表示应当创建固定大小的用户邻域
	 * @param userSimilarity 用户相似度度量机制
	 * @param dataModel 数据源
	 * @return 指定类型的（固定大小或基于阈值的）用户邻域对象
	 * @throws Exception
	 */
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
