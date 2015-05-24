package edu.recm.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.AbstractDataModel;
import org.dom4j.DocumentException;

import edu.recm.algorithm.algorithm.ContentBasedRecommender;
import edu.recm.algorithm.algorithm.ItemBasedCFRecommender;
import edu.recm.algorithm.algorithm.MyRecommender;
import edu.recm.algorithm.algorithm.UserBasedCFRecommender;
import edu.recm.algorithm.algorithm.WaterfallMixRecommender;
import edu.recm.algorithm.algorithm.WeightedMixRecommender;
import edu.recm.algorithm.data.FilePreferenceData;
import edu.recm.algorithm.data.MySQLContentData;
import edu.recm.algorithm.data.MySQLPreferenceData;
import edu.recm.algorithm.data.QueryUnit;
import edu.recm.config.model.AbstractDataConfigModel;
import edu.recm.config.model.AbstractSimilarityConfigModel;
import edu.recm.config.model.AbstractSingleAlgorithmConfigModel;
import edu.recm.config.model.ContentBasedAlgorithmConfigModel;
import edu.recm.config.model.FilePreferenceDataConfigModel;
import edu.recm.config.model.IntegralAlgorithmConfigModel;
import edu.recm.config.model.IntegralConfigModel;
import edu.recm.config.model.IntegralDataConfigModel;
import edu.recm.config.model.ItemBasedCFAlgorithmConfigModel;
import edu.recm.config.model.ItemSimilarityConfigModel;
import edu.recm.config.model.MySQLContentDataConfigModel;
import edu.recm.config.model.MySQLPreferenceDataConfigModel;
import edu.recm.config.model.QueryUnitConfigModel;
import edu.recm.config.model.UserBasedCFAlgorithmConfigModel;
import edu.recm.config.model.UserSimilarityConfigModel;
import edu.recm.config.model.WaterfallMixAlgorithmConfigModel;
import edu.recm.config.model.WeightedMixAlgorithmConfigModel;
import edu.recm.config.xml.XMLAnalyzer;
import edu.recm.util.MyEntry;

/**
 * 创建推荐器
 * @author niuzhixiang
 *
 */
public class RecommenderBuilder {
	
	/**
	 * 由推荐系统的参数配置模型，创建相应的推荐器
	 * @param configModel 推荐系统的参数配置模型
	 * @return 推荐器
	 * @throws Exception
	 */
	public MyRecommender buildRecommender(IntegralConfigModel configModel) throws Exception {	
		//所有输入数据源的参数配置模型列表
		List<IntegralDataConfigModel> dataList = configModel.getDataList().getDataConfigModelList();
		//特征数据源的参数配置模型列表
		List<IntegralDataConfigModel> contentDataList = new ArrayList<IntegralDataConfigModel>();
		//用户偏好数据源的参数配置模型列表
		List<IntegralDataConfigModel> preferenceList = new ArrayList<IntegralDataConfigModel>();
		for (IntegralDataConfigModel data : dataList) {
			if (data.getDataType().equals("content")) {
				contentDataList.add(data);
			}
			if (data.getDataType().equals("preference")) {
				preferenceList.add(data);
			}
		}
		//所有相似度度量方法的参数配置模型列表
		List<AbstractSimilarityConfigModel> similarityList = configModel.getSimilarityList().getSimilarityConfigModelList();
		//用户间的相似度度量方法的参数配置模型列表
		List<AbstractSimilarityConfigModel> userSimilarityList = new ArrayList<AbstractSimilarityConfigModel>();
		//项目间的相似度度量方法的参数配置模型列表
		List<AbstractSimilarityConfigModel> itemSimilarityList = new ArrayList<AbstractSimilarityConfigModel>();
		if (similarityList != null && similarityList.size() > 0) {
			for (AbstractSimilarityConfigModel similarity : similarityList) {
				if (similarity instanceof UserSimilarityConfigModel) {
					userSimilarityList.add(similarity);
				}
				if (similarity instanceof ItemSimilarityConfigModel) {
					itemSimilarityList.add(similarity);
				}
			}
			if (similarityList.size() != preferenceList.size()) {
				throw new Exception("用户偏好数据源的个数与相似度度量方法的个数不相等，无法一一匹配，因此无法生成协同过滤推荐器！");
			}
		}
	
		String algorithmType = configModel.getAlgorithm().getAlgorithmType();
		//1、推荐系统采用单个基本推荐算法
		if (algorithmType.equals("single")) {
			AbstractSingleAlgorithmConfigModel theAlgorithm = (AbstractSingleAlgorithmConfigModel) configModel.getAlgorithm().getAlgorithmConfigModel();
			AbstractSimilarityConfigModel similarity = null;
			//若该基本推荐算法不是基于内容的推荐算法，说明它是协同过滤推荐算法，则需要使用相似度度量方法
			if (! (theAlgorithm instanceof ContentBasedAlgorithmConfigModel)) {
				similarity = similarityList.get(0);
			}
			return buildSingleRecommender(configModel.getRecommenderName(), dataList.get(0), similarity, theAlgorithm);
		}
		//2、推荐系统采用加权型混合推荐算法
		else if (algorithmType.equals("weighted")) {
			WeightedMixAlgorithmConfigModel theAlgorithm = (WeightedMixAlgorithmConfigModel) configModel.getAlgorithm().getAlgorithmConfigModel();
			List<Entry<AbstractSingleAlgorithmConfigModel, Float>> singleAlgorithmList = theAlgorithm.getList();
			List<Entry<MyRecommender, Float>> recommenderList = new ArrayList<Map.Entry<MyRecommender,Float>>();
			int similarityCount = 0;
			//遍历该加权型混合推荐器中包含的所有基本推荐器
			for (int i = 0; i < singleAlgorithmList.size(); i++) {
				IntegralDataConfigModel theData = dataList.get(i);
				AbstractSingleAlgorithmConfigModel theSingleAlgorithm = singleAlgorithmList.get(i).getKey();
				Float weight = singleAlgorithmList.get(i).getValue();
				AbstractSimilarityConfigModel theSimilarity = null;
				//如果当前单个基本推荐器不是基于内容的推荐，说明它是协同过滤推荐，此时才用得到相似度度量方法
				if (! (theSingleAlgorithm instanceof ContentBasedAlgorithmConfigModel)) {
					theSimilarity = similarityList.get(similarityCount);
					similarityCount++;
				}
				MyRecommender theRecommender = buildSingleRecommender(configModel.getRecommenderName(), theData, theSimilarity, theSingleAlgorithm);	
				Entry<MyRecommender, Float> entry = new MyEntry(theRecommender, weight);
				recommenderList.add(entry);
			}
			return new WeightedMixRecommender(recommenderList);
		}
		//3、推荐系统采用瀑布型混合推荐算法
		else if (algorithmType.equals("waterfall")) {
			WaterfallMixAlgorithmConfigModel theAlgorithm = (WaterfallMixAlgorithmConfigModel) configModel.getAlgorithm().getAlgorithmConfigModel();
			List<Entry<AbstractSingleAlgorithmConfigModel, Integer>> singleAlgorithmList = theAlgorithm.getList();
			List<Entry<MyRecommender, Integer>> recommenderList = new ArrayList<Map.Entry<MyRecommender,Integer>>();
			int similarityCount = 0;
			//遍历该瀑布型混合推荐器中包含的所有基本推荐器
			for (int i = 0; i < singleAlgorithmList.size(); i++) {
				IntegralDataConfigModel theData = dataList.get(i);
				AbstractSingleAlgorithmConfigModel theSingleAlgorithm = singleAlgorithmList.get(i).getKey();
				Integer resultNum = singleAlgorithmList.get(i).getValue();
				AbstractSimilarityConfigModel theSimilarity = null;
				//如果当前单个基本推荐器不是基于内容的推荐，说明它是协同过滤推荐，此时才用得到相似度度量方法
				if (! (theSingleAlgorithm instanceof ContentBasedAlgorithmConfigModel)) {
					theSimilarity = similarityList.get(similarityCount);
					similarityCount++;
				}	
				MyRecommender theRecommender = buildSingleRecommender(configModel.getRecommenderName(), theData, theSimilarity, theSingleAlgorithm);
				Entry<MyRecommender, Integer> entry = new MyEntry(theRecommender, resultNum);
				recommenderList.add(entry);
			}
			return new WaterfallMixRecommender(recommenderList);
		}
		//4、异常情况
		else {
			throw new Exception("推荐算法类型不正确！");
		}
	}
	
	/**
	 * 创建单个基本推荐器
	 * @param recommenderName 推荐系统名称
	 * @param data 输入数据源的参数配置模型
	 * @param similarity 相似度度量方法的参数配置模型（在创建基于内容的推荐器时，该参数会被忽略）
	 * @param singleAlgorithm 单个基本推荐算法的参数配置模型
	 * @return 单个基本推荐器
	 * @throws Exception
	 */
	private MyRecommender buildSingleRecommender(String recommenderName, IntegralDataConfigModel data, AbstractSimilarityConfigModel similarity, AbstractSingleAlgorithmConfigModel singleAlgorithm) throws Exception {
		//1、创建基于内容的推荐器的情形（会忽略参数similarity）
		if (singleAlgorithm instanceof ContentBasedAlgorithmConfigModel) {
			return buildContentBasedRecommender(recommenderName, data);
		}
		//2、创建基于用户的协同过滤推荐器的情形
		else if (singleAlgorithm instanceof UserBasedCFAlgorithmConfigModel) {
			UserBasedCFAlgorithmConfigModel theAlgorithm = (UserBasedCFAlgorithmConfigModel) singleAlgorithm;
			UserSimilarityConfigModel userSimilarity = (UserSimilarityConfigModel) similarity;
			return buildUserBasedCFRecommender(recommenderName, data, userSimilarity, theAlgorithm.getnUser(), theAlgorithm.getThreshold());
		}
		//3、创建基于项目的协同过滤推荐器的情形
		else if (singleAlgorithm instanceof ItemBasedCFAlgorithmConfigModel) {
			ItemSimilarityConfigModel itemSimilarity = (ItemSimilarityConfigModel) similarity;
			return buildItemBasedCFRecommender(recommenderName, data, itemSimilarity);
		}
		//4、异常情况
		else {
			throw new Exception("单个基本推荐器类型错误！");
		}
	}
	
	/**
	 * 创建基于内容的推荐器
	 * @param recommenderName 推荐系统名称
	 * @param contentData 特征数据源的参数配置模型
	 * @return 基于内容的推荐器
	 */
	private ContentBasedRecommender buildContentBasedRecommender(String recommenderName, IntegralDataConfigModel contentData){
		//首先，由特征数据源的参数配置模型生成特征数据源对象
		MySQLContentData mySQLContentData = buildContentData(contentData);
		//然后，由特征数据源对象生成基于内容的推荐器
		return new ContentBasedRecommender(recommenderName, mySQLContentData, null);
	}
	
	/**
	 * 创建基于用户的协同过滤推荐器
	 * @param recommenderName 推荐系统名称
	 * @param preferenceData 用户偏好数据的参数配置模型
	 * @param similarity 用户间相似度度量方法的参数配置模型
	 * @param nUser 固定大小的用户邻域大小
	 * @param threshold 基于阈值的用户邻域阈值
	 * @return 基于用户的协同过滤推荐器
	 * @throws Exception
	 */
	private UserBasedCFRecommender buildUserBasedCFRecommender(String recommenderName, IntegralDataConfigModel preferenceData, UserSimilarityConfigModel similarity, Integer nUser, Double threshold) throws Exception{
		//1、如果用户偏好数据来源于MySQL
		if (preferenceData.getSourceType().equals("mysql")) {
			MySQLPreferenceData mySQLPreferenceData = buildMySQLPreferenceData(preferenceData);
			return new UserBasedCFRecommender(recommenderName, mySQLPreferenceData, similarity.getSimilarityType(), nUser, threshold);
		}
		//2、如果用户偏好数据来源于文件
		else if (preferenceData.getSourceType().equals("file")) {
			FilePreferenceData filePreferenceData = buildFilePreferenceData(preferenceData);
			return new UserBasedCFRecommender(recommenderName, filePreferenceData, similarity.getSimilarityType(), nUser, threshold);
		}
		//3、异常情况
		else {
			throw new Exception("用户偏好数据来源错误！");
		}	
	}
	
	/**
	 * 创建基于项目的协同过滤推荐器
	 * @param recommenderName 推荐系统名称
	 * @param preferenceData 用户偏好数据的参数配置模型
	 * @param similarity 项目间相似度度量方法的参数配置模型
	 * @return 基于项目的协同过滤推荐器
	 * @throws Exception
	 */
	private ItemBasedCFRecommender buildItemBasedCFRecommender(String recommenderName, IntegralDataConfigModel preferenceData, ItemSimilarityConfigModel similarity) throws Exception{
		//1、如果用户偏好数据来源于MySQL
		if (preferenceData.getSourceType().equals("mysql")) {
			MySQLPreferenceData mySQLPreferenceData = buildMySQLPreferenceData(preferenceData);
			return new ItemBasedCFRecommender(recommenderName, mySQLPreferenceData, similarity.getSimilarityType());
		}
		//2、如果用户偏好数据来源于文件
		else if (preferenceData.getSourceType().equals("file")) {
			FilePreferenceData filePreferenceData = buildFilePreferenceData(preferenceData);
			return new ItemBasedCFRecommender(recommenderName, filePreferenceData, similarity.getSimilarityType());
		}
		//3、异常情况
		else {
			throw new Exception("用户偏好数据来源错误！");
		}
	}
	
	/**
	 * 创建来源于MySQL的特征数据源
	 * @param integralData 输入数据源的参数配置模型
	 * @return 来源于MySQL的特征数据源对象
	 */
	private MySQLContentData buildContentData(IntegralDataConfigModel integralData) {
		MySQLContentDataConfigModel data = (MySQLContentDataConfigModel) integralData.getDataConfigModel();
		List<QueryUnit> queryUnitList = new ArrayList<QueryUnit>();
		for (QueryUnitConfigModel queryUnitConfig : data.getQueryUnitConfigModelList()) {
			QueryUnit queryUnit = new QueryUnit();
			queryUnit.setUserField(queryUnitConfig.getUserColumn());
			queryUnit.setItemField(queryUnitConfig.getItemColumn());
			if (queryUnitConfig.getOccur().equals("MUST")) {
				queryUnit.setOccur(Occur.MUST);
			}
			if (queryUnitConfig.getOccur().equals("SHOULD")) {
				queryUnit.setOccur(Occur.SHOULD);
			}
			if (queryUnitConfig.getOccur().equals("MUST_NOT")) {
				queryUnit.setOccur(Occur.MUST_NOT);
			}
			queryUnitList.add(queryUnit);
		}
		MySQLContentData contentData = new MySQLContentData(integralData.getDbConfigModel().getDbServerName(), 
												integralData.getDbConfigModel().getDbUser(), 
												integralData.getDbConfigModel().getDbPassword(), 
												data.getDbDatabaseName(), data.getUserTableName(), data.getItemTableName(), 
												queryUnitList);
		return contentData;
	}
	
	/**
	 * 创建来源于文件的用户偏好数据源
	 * @param integralData 输入数据源的参数配置模型
	 * @return 来源于文件的用户偏好数据源对象
	 */
	private FilePreferenceData buildFilePreferenceData(IntegralDataConfigModel integralData){
		FilePreferenceDataConfigModel data = (FilePreferenceDataConfigModel) integralData.getDataConfigModel();
		FilePreferenceData preferenceData = new FilePreferenceData(data.getFilePath());
		return preferenceData;
	}
	
	/**
	 * 创建来源于MySQL的用户偏好数据源
	 * @param integralData 输入数据源的参数配置模型
	 * @return 来源于MySQL的用户偏好数据源对象
	 * @throws Exception
	 */
	private MySQLPreferenceData buildMySQLPreferenceData(IntegralDataConfigModel integralData) throws Exception{
		MySQLPreferenceDataConfigModel data = (MySQLPreferenceDataConfigModel) integralData.getDataConfigModel();	
		MySQLPreferenceData preferenceData = new MySQLPreferenceData();
		//1、无偏好值的情况
		if (data.getPreferenceColumn() == null || data.getPreferenceColumn().trim().equals("")) {
			preferenceData.createDataModelWithoutPrefValue(integralData.getDbConfigModel().getDbServerName(), 
															integralData.getDbConfigModel().getDbUser(), 
															integralData.getDbConfigModel().getDbPassword(), 
															data.getDbDatabaseName(),
															data.getPreferenceTable(), 
															data.getUserIDColumn(), 
															data.getItemIDColumn(), 
															data.getTimestampColumn());
		}
		//2、有偏好值的情况
		else {
			preferenceData.createDataModelWithPrefValue(integralData.getDbConfigModel().getDbServerName(), 
															integralData.getDbConfigModel().getDbUser(), 
															integralData.getDbConfigModel().getDbPassword(), 
															data.getDbDatabaseName(),
															data.getPreferenceTable(), 
															data.getUserIDColumn(), 
															data.getItemIDColumn(), 
															data.getPreferenceColumn(),
															data.getTimestampColumn());
		}
		return preferenceData;	
	}
	
}
