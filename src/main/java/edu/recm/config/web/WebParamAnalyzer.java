package edu.recm.config.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import edu.recm.config.model.AbstractSimilarityConfigModel;
import edu.recm.config.model.AbstractSingleAlgorithmConfigModel;
import edu.recm.config.model.ContentBasedAlgorithmConfigModel;
import edu.recm.config.model.DBConfigModel;
import edu.recm.config.model.DataListConfigModel;
import edu.recm.config.model.EvaluatorConfigModel;
import edu.recm.config.model.FilePreferenceDataConfigModel;
import edu.recm.config.model.IntegralAlgorithmConfigModel;
import edu.recm.config.model.IntegralConfigModel;
import edu.recm.config.model.IntegralDataConfigModel;
import edu.recm.config.model.ItemBasedCFAlgorithmConfigModel;
import edu.recm.config.model.ItemSimilarityConfigModel;
import edu.recm.config.model.MySQLContentDataConfigModel;
import edu.recm.config.model.MySQLPreferenceDataConfigModel;
import edu.recm.config.model.QueryUnitConfigModel;
import edu.recm.config.model.SimilarityListConfigModel;
import edu.recm.config.model.UserBasedCFAlgorithmConfigModel;
import edu.recm.config.model.UserSimilarityConfigModel;
import edu.recm.config.model.WaterfallMixAlgorithmConfigModel;
import edu.recm.config.model.WeightedMixAlgorithmConfigModel;
import edu.recm.config.xml.XMLCreator;
import edu.recm.util.MyEntry;

/**
 * 解析前端配置页面填写的参数，建立相应的推荐系统参数配置模型
 * @author niuzhixiang
 *
 */
public class WebParamAnalyzer {
	
	/**
	 * 根据前端页面传入的表单参数建立整个推荐系统的参数配置模型
	 * @param jsonParam 前端页面传入的参数（JSON格式）
	 * @return 整个推荐系统的参数配置模型
	 * @throws Exception
	 */
	public IntegralConfigModel buildConfigModel(String jsonParam) throws Exception {
		JSONObject integralConfigJson = JSONObject.fromObject(jsonParam);
		
		String recommenderName = integralConfigJson.getString("recommenderName");
		String jsonDataList = integralConfigJson.getString("dataList");
		String jsonSimilarityList = integralConfigJson.getString("similarityList");
		String jsonAlgorithm = integralConfigJson.getString("algorithm");
		String jsonEvaluator = integralConfigJson.getString("evaluator");
		
		DataListConfigModel dataList = buildDataList(jsonDataList);
		SimilarityListConfigModel similarityList = buildSimilarityList(jsonSimilarityList);
		IntegralAlgorithmConfigModel algorithm = buildAlgorithm(jsonAlgorithm);
		EvaluatorConfigModel evaluator = buildEvaluator(jsonEvaluator);
		
		return new IntegralConfigModel(recommenderName, dataList, similarityList, algorithm, evaluator);
	}
	
	/**
	 * 根据前端页面传入的表单参数建立输入数据源的参数配置模型
	 * @param jsonDataList 前端页面传入的输入数据源参数（JSON格式）
	 * @return 输入数据源的参数配置模型
	 * @throws Exception
	 */
	private DataListConfigModel buildDataList(String jsonDataList) throws Exception {
		List<IntegralDataConfigModel> integralDataList = new ArrayList<IntegralDataConfigModel>();
		JSONArray dataArray = JSONArray.fromObject(jsonDataList);
		for (int i = 0; i < dataArray.size(); i++) {
			JSONObject dataObject = JSONObject.fromObject(dataArray.getString(i));
			//1、特征数据源
			if (dataObject.getString("dataType").equals("content") && dataObject.getString("sourceType").equals("mysql")) {
				JSONObject theData = JSONObject.fromObject(dataObject.getString("data"));	
				MySQLContentDataConfigModel contentData = new MySQLContentDataConfigModel();
				DBConfigModel dbConfigModel = new DBConfigModel(theData.getString("dbServerName"), theData.getString("dbUser"), theData.getString("dbPassword"));
				
				List<QueryUnitConfigModel> queryList = new ArrayList<QueryUnitConfigModel>();
				JSONArray queryArray = JSONArray.fromObject(theData.get("queryList"));
				for (int j = 0; j < queryArray.size(); j++) {
					queryList.add(new QueryUnitConfigModel(queryArray.getJSONObject(j).getString("userColumn"), queryArray.getJSONObject(j).getString("itemColumn"), queryArray.getJSONObject(j).getString("occur")));
				}	
				contentData.setQueryUnitConfigModelList(queryList);
				contentData.setDbDatabaseName(theData.getString("dbDatabaseName"));
				contentData.setUserTableName(theData.getString("userTable"));
				contentData.setItemTableName(theData.getString("itemTable"));	
				
				IntegralDataConfigModel integralData = new IntegralDataConfigModel("content", "mysql", dbConfigModel, contentData);
				integralDataList.add(integralData);
			}
			//2、来源于MySQL的用户偏好数据源
			else if (dataObject.getString("dataType").equals("preference") && dataObject.getString("sourceType").equals("mysql")){
				JSONObject theData = JSONObject.fromObject(dataObject.getString("data"));
				MySQLPreferenceDataConfigModel preferenceData = new MySQLPreferenceDataConfigModel();
				DBConfigModel dbConfigModel = new DBConfigModel(theData.getString("dbServerName"), theData.getString("dbUser"), theData.getString("dbPassword"));
				
				preferenceData.setDbDatabaseName(theData.getString("dbDatabaseName"));
				preferenceData.setPreferenceTable(theData.getString("preferenceTable"));
				preferenceData.setUserIDColumn(theData.getString("userIDColumn"));
				preferenceData.setItemIDColumn(theData.getString("itemIDColumn"));
				preferenceData.setPreferenceColumn(theData.getString("preferenceColumn"));
				preferenceData.setTimestampColumn(theData.getString("timestampColumn"));

				IntegralDataConfigModel integralData = new IntegralDataConfigModel("preference", "mysql", dbConfigModel, preferenceData);
				integralDataList.add(integralData);
			}
			//3、来源于文件的用户偏好数据源
			else if (dataObject.getString("dataType").equals("preference") && dataObject.getString("sourceType").equals("file")){
				JSONObject theData = JSONObject.fromObject(dataObject.getString("data"));
				FilePreferenceDataConfigModel filePreferenceData = new FilePreferenceDataConfigModel();
				
				filePreferenceData.setFilePath(theData.getString("filePath"));
				
				IntegralDataConfigModel integralData = new IntegralDataConfigModel("preference", "file", null, filePreferenceData);
				integralDataList.add(integralData);
			}
			//4、异常情况
			else {
				throw new Exception("输入数据源的JSON参数配置格式错误！");
			}
		}
		return new DataListConfigModel(integralDataList);
	}
	
	/**
	 * 根据前端页面传入的表单参数建立相似度度量机制的参数配置模型
	 * @param jsonSimilarityList 前端页面传入的相似度度量机制参数（JSON格式）
	 * @return 相似度度量机制的参数配置模型
	 * @throws Exception
	 */
	private SimilarityListConfigModel buildSimilarityList(String jsonSimilarityList) throws Exception {
		JSONArray similarityArray = JSONArray.fromObject(jsonSimilarityList);
		List<AbstractSimilarityConfigModel> similarityList = new ArrayList<AbstractSimilarityConfigModel>();
		for (int i = 0; i < similarityArray.size(); i++) {
			JSONObject theSimilarity = similarityArray.getJSONObject(i);
			//1、用户间的相似度
			if (theSimilarity.getString("similarity").equals("userSimilarity")) {
				UserSimilarityConfigModel userSimilarity = new UserSimilarityConfigModel(theSimilarity.getString("similarityType"));
				similarityList.add(userSimilarity);
			}
			//2、项目间的相似度
			else if (theSimilarity.getString("similarity").equals("itemSimilarity")) {
				ItemSimilarityConfigModel itemSimilarity = new ItemSimilarityConfigModel(theSimilarity.getString("similarityType"));
				similarityList.add(itemSimilarity);
			}
			//3、异常情况
			else {
				throw new Exception("相似度度量方法的JSON参数配置格式错误！");
			}
		}
		return new SimilarityListConfigModel(similarityList);
	}
	
	/**
	 * 根据前端页面传入的表单参数建立算法处理流程的参数配置模型
	 * @param jsonAlgorithm 前端页面传入的算法处理流程参数（JSON格式）
	 * @return 算法处理流程的参数配置模型
	 * @throws Exception
	 */
	private IntegralAlgorithmConfigModel buildAlgorithm(String jsonAlgorithm) throws Exception {
		IntegralAlgorithmConfigModel integralAlgorithm = null;
		JSONObject algorithmObject = JSONObject.fromObject(jsonAlgorithm);
		//1、单个基本推荐算法
		if (algorithmObject.getString("algorithmType").equals("single")) {
			JSONArray algorithmArray = JSONArray.fromObject(algorithmObject.getString("list"));
			JSONObject theAlgorithm = algorithmArray.getJSONObject(0);
			//1.1、基于内容的推荐
			if (theAlgorithm.getString("name").equals("contentBased")) {
				integralAlgorithm = new IntegralAlgorithmConfigModel("single", new ContentBasedAlgorithmConfigModel());
			}
			//1.2、基于用户的协同过滤
			else if (theAlgorithm.getString("name").equals("userBasedCF")) {
				Double threshold = (! theAlgorithm.containsKey("threshold") || theAlgorithm.getString("threshold").trim().equals("")) ? null : Double.valueOf(theAlgorithm.getString("threshold"));
				Integer nUser = (! theAlgorithm.containsKey("nearestN") || theAlgorithm.getString("nearestN").trim().equals("")) ? null : Integer.parseInt(theAlgorithm.getString("nearestN"));
				integralAlgorithm = new IntegralAlgorithmConfigModel("single", new UserBasedCFAlgorithmConfigModel(theAlgorithm.getString("userNeighborhood"), threshold, nUser));
			}
			//1.3、基于项目的协同过滤
			else if (theAlgorithm.getString("name").equals("itemBasedCF")) {
				integralAlgorithm = new IntegralAlgorithmConfigModel("single", new ItemBasedCFAlgorithmConfigModel());
			}
			//1.4、异常情况
			else {
				throw new Exception("基本推荐算法的JSON参数配置格式错误！");
			}
		}
		//2、加权型混合推荐算法
		else if (algorithmObject.getString("algorithmType").equals("weighted")) {
			JSONArray algorithmArray = JSONArray.fromObject(algorithmObject.getString("list"));
			List<Entry<AbstractSingleAlgorithmConfigModel, Float>> algorithmList = new ArrayList<Map.Entry<AbstractSingleAlgorithmConfigModel,Float>>();
			for (int i = 0; i < algorithmArray.size(); i++) {
				JSONObject theAlgorithm = algorithmArray.getJSONObject(i);
				if (theAlgorithm.getString("name").equals("contentBased")) {
					ContentBasedAlgorithmConfigModel contentBasedAlgorithm = new ContentBasedAlgorithmConfigModel();
					Entry<AbstractSingleAlgorithmConfigModel, Float> entry = new MyEntry(contentBasedAlgorithm, Float.valueOf(theAlgorithm.getString("weight")));
					algorithmList.add(entry);
				}
				else if (theAlgorithm.getString("name").equals("userBasedCF")) {	
					Double threshold = (! theAlgorithm.containsKey("threshold") || theAlgorithm.getString("threshold").trim().equals("")) ? null : Double.valueOf(theAlgorithm.getString("threshold"));
					Integer nUser = (! theAlgorithm.containsKey("nearestN") || theAlgorithm.getString("nearestN").trim().equals("")) ? null : Integer.parseInt(theAlgorithm.getString("nearestN"));
					UserBasedCFAlgorithmConfigModel userBasedCFAlgorithm = new UserBasedCFAlgorithmConfigModel(theAlgorithm.getString("userNeighborhood"), threshold, nUser);
					Entry<AbstractSingleAlgorithmConfigModel, Float> entry = new MyEntry(userBasedCFAlgorithm, Float.valueOf(theAlgorithm.getString("weight")));
					algorithmList.add(entry);
				}
				else if (theAlgorithm.getString("name").equals("itemBasedCF")) {
					ItemBasedCFAlgorithmConfigModel itemBasedCFAlgorithm = new ItemBasedCFAlgorithmConfigModel();
					Entry<AbstractSingleAlgorithmConfigModel, Float> entry = new MyEntry(itemBasedCFAlgorithm, Float.valueOf(theAlgorithm.getString("weight")));
					algorithmList.add(entry);
				}
				else {
					throw new Exception("在创建加权型混合推荐算法时，单个基本推荐算法的JSON参数配置格式错误！");
				}
				integralAlgorithm = new IntegralAlgorithmConfigModel("weighted", new WeightedMixAlgorithmConfigModel(algorithmList));
			}
		}
		//3、瀑布型混合推荐算法
		else if (algorithmObject.getString("algorithmType").equals("waterfall")) {
			JSONArray algorithmArray = JSONArray.fromObject(algorithmObject.getString("list"));
			List<Entry<AbstractSingleAlgorithmConfigModel, Integer>> algorithmList = new ArrayList<Map.Entry<AbstractSingleAlgorithmConfigModel,Integer>>();
			for (int i = 0; i < algorithmArray.size(); i++) {
				JSONObject theAlgorithm = algorithmArray.getJSONObject(i);
				if (theAlgorithm.getString("name").equals("contentBased")) {
					ContentBasedAlgorithmConfigModel contentBasedAlgorithm = new ContentBasedAlgorithmConfigModel();
					Entry<AbstractSingleAlgorithmConfigModel, Integer> entry = new MyEntry(contentBasedAlgorithm, Integer.parseInt(theAlgorithm.getString("resultNum")));
					algorithmList.add(entry);
				}
				else if (theAlgorithm.getString("name").equals("userBasedCF")) {
					Double threshold = (! theAlgorithm.containsKey("threshold") || theAlgorithm.getString("threshold").trim().equals("")) ? null : Double.valueOf(theAlgorithm.getString("threshold"));
					Integer nUser = (! theAlgorithm.containsKey("nearestN") || theAlgorithm.getString("nearestN").trim().equals("")) ? null : Integer.parseInt(theAlgorithm.getString("nearestN"));
					UserBasedCFAlgorithmConfigModel userBasedCFAlgorithm = new UserBasedCFAlgorithmConfigModel(theAlgorithm.getString("userNeighborhood"), threshold, nUser);
					Entry<AbstractSingleAlgorithmConfigModel, Integer> entry = new MyEntry(userBasedCFAlgorithm, Integer.parseInt(theAlgorithm.getString("resultNum")));
					algorithmList.add(entry);
				}
				else if (theAlgorithm.getString("name").equals("itemBasedCF")) {
					ItemBasedCFAlgorithmConfigModel itemBasedCFAlgorithm = new ItemBasedCFAlgorithmConfigModel();
					Entry<AbstractSingleAlgorithmConfigModel, Integer> entry = new MyEntry(itemBasedCFAlgorithm, Integer.parseInt(theAlgorithm.getString("resultNum")));
					algorithmList.add(entry);
				}
				else {
					throw new Exception("在创建瀑布型混合推荐算法时，单个基本推荐算法的JSON参数配置格式错误！");
				}
				integralAlgorithm = new IntegralAlgorithmConfigModel("waterfall", new WaterfallMixAlgorithmConfigModel(algorithmList));
			}
		}
		//4、异常情况
		else {
			throw new Exception("推荐算法JSON参数配置格式错误！");
		}
		return integralAlgorithm;
	}
	
	/**
	 * 根据前端页面传入的表单参数建立算法评估机制的参数配置模型
	 * @param jsonEvaluator 前端页面传入的算法评估机制参数（JSON格式）
	 * @return 算法评估机制的参数配置模型
	 */
	private EvaluatorConfigModel buildEvaluator(String jsonEvaluator) {
		JSONObject evaluator = JSONObject.fromObject(jsonEvaluator);
		return new EvaluatorConfigModel(evaluator.getBoolean("deviation"), evaluator.getBoolean("precision"), evaluator.getBoolean("recall"), evaluator.getBoolean("runningTime"));
	}

}
