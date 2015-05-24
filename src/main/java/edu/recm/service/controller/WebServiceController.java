package edu.recm.service.controller;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.recm.algorithm.algorithm.AbstractCFRecommender;
import edu.recm.algorithm.algorithm.MyRecommender;
import edu.recm.algorithm.data.ResultBean;
import edu.recm.algorithm.evaluate.MyRecommenderEvaluator;
import edu.recm.config.model.IntegralConfigModel;
import edu.recm.config.xml.XMLAnalyzer;
import edu.recm.service.common.RecommenderBuilder;

/**
 * 提供推荐服务的Controller
 * @author niuzhixiang
 *
 */
@Controller
@RequestMapping("/")
public class WebServiceController {
	
	static Logger logger = Logger.getLogger(WebServiceController.class);
	
	/**
	 * 提供RESTful推荐服务
	 * @param recommenderName 推荐系统唯一名称
	 * @param userid 用户ID
	 * @param resultNum 推荐数目
	 * @return 包含推荐结果和一些相关信息的JSON数据
	 */
	@RequestMapping(value="/{recommenderName}", method=RequestMethod.GET)
	@ResponseBody
	public String recommendService(@PathVariable("recommenderName") String recommenderName, @RequestParam("uid") Integer userid, @RequestParam("num") Integer resultNum) {	
		XMLAnalyzer xmlAnalyzer = new XMLAnalyzer();
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder();
		JSONObject finalResult = new JSONObject();
		try {
			IntegralConfigModel config = xmlAnalyzer.analyzeXMLConfigFile(recommenderName);
			MyRecommender recommender = recommenderBuilder.buildRecommender(config);
			JSONArray itemListJson = new JSONArray();
			
			//获取推荐结果，并计算推荐算法的执行时间
			long preTime = System.currentTimeMillis();
			
			List<ResultBean> resultBeanList = recommender.doRecommend(userid, resultNum);
			
			long afterTime = System.currentTimeMillis();
			String runningTime = (afterTime - preTime) + "ms";
			
			for (int i = 0; i < resultBeanList.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("itemid", resultBeanList.get(i).getId());
				jsonObject.put("score", resultBeanList.get(i).getScore());
				itemListJson.add(jsonObject);
			}
			
			//如果是协同过滤推荐器，计算预测评分差、查准率、查全率
			JSONObject evaluatorJson = new JSONObject();
			if (recommender instanceof AbstractCFRecommender) {
				MyRecommenderEvaluator evaluator = new MyRecommenderEvaluator();
				if (config.getEvaluator().getDeviation() == true) {
					Double deviation = evaluator.calculateDeviation(recommender);
					evaluatorJson.put("deviation", deviation);
				}
				if (config.getEvaluator().getPrecision() == true || config.getEvaluator().getRecall() == true) {
					IRStatistics irStatistics = evaluator.calculateIRStatistics(recommender);
					if (config.getEvaluator().getPrecision() == true) {
						evaluatorJson.put("precision", irStatistics.getPrecision());
					}
					if (config.getEvaluator().getRecall() == true) {
						evaluatorJson.put("recall", irStatistics.getRecall());
					}
				}
			}
			
			if (config.getEvaluator().getRunningTime() == true) {
				evaluatorJson.put("runningTime", runningTime);
			}
			
			finalResult.put("status", "success");
			finalResult.put("userid", userid);
			finalResult.put("result", itemListJson);
			finalResult.put("evaluate", evaluatorJson);
			
			return finalResult.toString();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			finalResult.put("status", "fail");
			finalResult.put("reason", "XML config file not found");
			
			return finalResult.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			finalResult.put("status", "fail");
			finalResult.put("reason", e.getClass().getName());
			
			return finalResult.toString();
		} 
	}

}
