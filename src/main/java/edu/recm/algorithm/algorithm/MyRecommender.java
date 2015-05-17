package edu.recm.algorithm.algorithm;

import java.util.List;
import edu.recm.algorithm.data.ResultBean;

/**
 * 推荐器接口，包括基于内容的推荐器、基于用户和基于项目的协同过滤推荐器均实现这个接口
 * @author niuzhixiang
 *
 */
public interface MyRecommender {
	
	/**
	 * 为指定的用户进行推荐
	 * @param userid 用户ID
	 * @param resultNum 推荐结果数目
	 * @return 推荐结果集
	 * @throws Exception
	 */
	public List<ResultBean> doRecommend(int userid, int resultNum) throws Exception;

}
