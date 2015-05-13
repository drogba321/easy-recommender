package edu.recm.algorithm.algorithm;

import java.util.List;
import edu.recm.algorithm.data.ResultBean;

/**
 * 推荐器接口，包括基于内容的推荐器、基于用户和基于项目的协同过滤推荐器均实现这个接口
 * @author niuzhixiang
 *
 */
public interface Recommender {
	
	public List<ResultBean> doRecommend(int userid, int resultNum) throws Exception;

}
