package test.algorithm.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import edu.recm.algorithm.algorithm.WeightedMixRecommender;
import edu.recm.algorithm.data.ResultBean;

public class WeightedMixRecommenderTest {
	
	
	/**
	 * 测试归一化的方法
	 */
	@Test
	@Ignore
	public void testNormalize() {
		ResultBean rb1 = new ResultBean(1, 0.8f);
		ResultBean rb2 = new ResultBean(2, 0.5f);
		List<ResultBean> inputList = new ArrayList<ResultBean>();
		inputList.add(rb1);
		inputList.add(rb2);
		List<ResultBean> resultBeans = new WeightedMixRecommender().normalize(inputList);
		for (ResultBean resultBean : resultBeans) {
			System.out.println("id:" + resultBean.getId() + ", score:" + resultBean.getScore());
		}
	}
	
	/**
	 * 测试综合评分的方法
	 */
	@Test
	public void testIntegratingScore() {
		Map<List<ResultBean>, Float> inputMap = new HashMap<List<ResultBean>, Float>();
		List<ResultBean> list1 = new ArrayList<ResultBean>();
		list1.add(new ResultBean(1, 0.9f));
		list1.add(new ResultBean(2, 0.5f));
		list1.add(new ResultBean(4, 0.3f));
		inputMap.put(list1, 3f);
		
		List<ResultBean> list2 = new ArrayList<ResultBean>();
		list2.add(new ResultBean(2, 1.2f));
		list2.add(new ResultBean(3, 0.7f));
		list2.add(new ResultBean(5, 0.1f));
		inputMap.put(list2, 5f);
		
		List<ResultBean> list3 = new ArrayList<ResultBean>();
		list3.add(new ResultBean(2, 1.3f));
		list3.add(new ResultBean(5, 0.3f));
		inputMap.put(list3, 2.5f);
		
		List<ResultBean> resultBeans = new WeightedMixRecommender().integratingScore(inputMap, 4);
		for (ResultBean resultBean : resultBeans) {
			System.out.println("id:" + resultBean.getId() + ", score:" + resultBean.getScore());
		}
	}

}
