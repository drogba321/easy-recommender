package edu.recm.algorithm.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.recm.algorithm.algorithm.Recommender;
import edu.recm.algorithm.data.ResultBean;

/**
 * 加权型混合推荐器
 * @author niuzhixiang
 *
 */
public class WeightedMixRecommender {
	
	private List<Entry<Recommender, Float>> recommenderList;
	
	/**
	 * 执行加权型混合推荐
	 * @param userid
	 * @param resultNum
	 * @return
	 * @throws Exception
	 */
	public List<ResultBean> doRecommend(int userid, int resultNum) throws Exception {
		Map<List<ResultBean>, Float> map = new HashMap<List<ResultBean>, Float>();
		for (Entry<Recommender, Float> recommenderEntry : this.recommenderList) {
			Recommender recommender = recommenderEntry.getKey();
			Float weight = recommenderEntry.getValue();
			//获得每个Recommender的归一化推荐结果列表
			List<ResultBean> normalizedList = this.normalize(recommender.doRecommend(userid, resultNum));
			map.put(normalizedList, weight);		
		}
		//计算综合评分，返回最终的混合推荐结果集
		return this.integratingScore(map, resultNum);
	}

	/**
	 * 归一化单个推荐器的推荐结果评分
	 * @param inputList 原始推荐结果集
	 * @return 归一化评分之后的推荐结果集
	 */
	public List<ResultBean> normalize(List<ResultBean> inputList) {
		if (inputList == null || inputList.size() == 0) {
			return null;
		}
		//推荐结果列表中的最高评分必然是第一个项目的评分（因为生成推荐结果的时候是按评分从高到低返回的）
		float maxScore = inputList.get(0).getScore();
		//依次归一化每个项目的评分，其中评分最高的项目归一化为1，其他项目的评分按比例取值。
		for (ResultBean resultBean : inputList) {
			float newScore = (maxScore == 0) ? 0 : resultBean.getScore() / maxScore;
			resultBean.setScore(newScore);
		}
		return inputList;
	}
	
	/**
	 * 计算综合评分
	 * @param inputMap 输入Map
	 * @param resultNum 最终推荐结果的数目
	 * @return 最终推荐结果集
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ResultBean> integratingScore(Map<List<ResultBean>, Float> inputMap, int resultNum) {
		List<ResultBean> finalResultList = new ArrayList<ResultBean>();
		Iterator iterator = inputMap.entrySet().iterator();
		//对于每一个推荐结果列表
		while (iterator.hasNext()) {
			Map.Entry<List<ResultBean>, Float> entry = (Map.Entry) iterator.next();
			List<ResultBean> normalizedList = entry.getKey();
			Float weight = entry.getValue();
			//依次遍历该推荐列表中的项目（当前项目）
			for (int i = 0; i < normalizedList.size(); i++) {
				ResultBean currentRb = normalizedList.get(i);
				//初始时，最终结果集finalResultList为空，直接将当前项目添加进最终结果集
				if (finalResultList.isEmpty()) {
					BigDecimal b1 = new BigDecimal(Double.toString(currentRb.getScore()));
					BigDecimal b2 = new BigDecimal(Double.toString(weight));
					currentRb.setScore(b1.multiply(b2).setScale(2, RoundingMode.HALF_UP).floatValue());
					finalResultList.add(currentRb);
					continue;
				}
				int currentSize = finalResultList.size();
				//遍历最终结果集finalResultList中的每个项目（即已存在于最终结果集中的项目）
				for (int j = 0; j < currentSize; ) {
					ResultBean existedRb = finalResultList.get(j);
					//若当前项目已存在于最终结果集finalResultList中，则更新该项目的评分
					if (existedRb.getId() == currentRb.getId()) {		
						BigDecimal b1 = new BigDecimal(Double.toString(existedRb.getScore()));
						BigDecimal b2 = new BigDecimal(Double.toString(currentRb.getScore()));
						BigDecimal b3 = new BigDecimal(Double.toString(weight));
						existedRb.setScore(b1.add(b2.multiply(b3)).setScale(2, RoundingMode.HALF_UP).floatValue());
						break;
					}
					++j;
					//否则，当前项目不存在于最终结果集finalResultList中，则直接将当前项目添加到最终结果集中
					if (j >= currentSize) {
						BigDecimal b1 = new BigDecimal(Double.toString(currentRb.getScore()));
						BigDecimal b2 = new BigDecimal(Double.toString(weight));
						currentRb.setScore(b1.multiply(b2).setScale(2, RoundingMode.HALF_UP).floatValue());
						finalResultList.add(currentRb);
					}
				}
			}
		}
		
		//对最终结果集finalResultList按综合评分从大到小排序
		Collections.sort(finalResultList, new Comparator<ResultBean>() {
			public int compare(ResultBean o1, ResultBean o2) {
				// TODO Auto-generated method stub
				if (o1.getScore() < o2.getScore()) {
					return 1;
				} else if (o1.getScore() > o2.getScore()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		
		return finalResultList.size() < resultNum ? finalResultList : finalResultList.subList(0, resultNum);
	}

}
