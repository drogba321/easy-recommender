package edu.recm.config.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.recm.config.model.AbstractAlgorithmConfigModel;
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
import edu.recm.util.ConfigVariables;
import edu.recm.util.MyEntry;

/**
 * 解析推荐系统的XML配置文件
 * @author niuzhixiang
 *
 */
public class XMLAnalyzer {

	/**
	 * 解析推荐系统的XML配置文件，生成对应的参数配置模型
	 * @param recommenderID 推荐系统的唯一名称
	 * @return 推荐系统的参数配置模型
	 * @throws DocumentException
	 */
	public IntegralConfigModel analyzeXMLConfigFile(String recommenderID) throws DocumentException {	
		String configFilePath = ConfigVariables.XML_CONFIG_FILE_DIR + recommenderID + ".xml";
		File file = new File(configFilePath);
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(file);
		Element rootElement = document.getRootElement();
		
		DataListConfigModel dataList = analyzeDataPart(rootElement);
		SimilarityListConfigModel similarityList = analyzeSimilarityPart(rootElement);
		IntegralAlgorithmConfigModel algorithm = analyzeAlgorithmPart(rootElement);
		EvaluatorConfigModel evaluator = analyzeEvaluatePart(rootElement);
		
		IntegralConfigModel configModel = new IntegralConfigModel(recommenderID, dataList, similarityList, algorithm, evaluator);
		
		return configModel;
	}

	/**
	 * 解析XML配置文件中的算法评估部分
	 * @param rootElement XML配置文件的根节点
	 * @return 算法评估部分的参数配置模型
	 */
	private EvaluatorConfigModel analyzeEvaluatePart(Element rootElement) {
		Element evaluateElement = rootElement.element("evaluate");
		Boolean deviation = evaluateElement.elementText("deviation") == null ? true : Boolean.parseBoolean(evaluateElement.elementText("deviation"));
		Boolean precision = evaluateElement.elementText("precision") == null ? true : Boolean.parseBoolean(evaluateElement.elementText("precision"));
		Boolean recall = evaluateElement.elementText("recall") == null ? true : Boolean.parseBoolean(evaluateElement.elementText("recall"));
		Boolean runningTime = evaluateElement.elementText("runningTime") == null ? true : Boolean.parseBoolean(evaluateElement.elementText("runningTime"));
		
		return new EvaluatorConfigModel(deviation, precision, recall, runningTime);
	}

	/**
	 * 解析XML配置文件中的推荐算法处理流程部分
	 * @param rootElement XML配置文件的根节点
	 * @return 推荐算法处理流程的参数配置模型
	 */
	private IntegralAlgorithmConfigModel analyzeAlgorithmPart(Element rootElement) {
		Element algoElement = rootElement.element("algo");
		AbstractAlgorithmConfigModel algorithm = null;
		IntegralAlgorithmConfigModel integralAlgorithm = null;
		//1、若是基本推荐算法，直接调用analyzeSingleAlgorithmPart()方法即可
		if (algoElement.elementText("algorithmType").equals("single")) {
			Element algorithmElement = algoElement.element("algorithm");
			algorithm = analyzeSingleAlgorithmPart(algorithmElement);
			integralAlgorithm = new IntegralAlgorithmConfigModel("single", algorithm);
		}
		//2、若是加权型混合推荐算法
		else if (algoElement.elementText("algorithmType").equals("weighted")) {
			List<Element> algorithmList = algoElement.elements("algorithm");
			List<Entry<AbstractSingleAlgorithmConfigModel, Float>> list = new ArrayList<Map.Entry<AbstractSingleAlgorithmConfigModel,Float>>();
			for (Element algorithmElement : algorithmList) {
				AbstractSingleAlgorithmConfigModel singleAlgorithm = analyzeSingleAlgorithmPart(algorithmElement);
				Entry<AbstractSingleAlgorithmConfigModel, Float> entry = new MyEntry(singleAlgorithm, Float.valueOf(algorithmElement.elementText("weight")));
				list.add(entry);
			}
			algorithm = new WeightedMixAlgorithmConfigModel(list);
			integralAlgorithm = new IntegralAlgorithmConfigModel("weighted", algorithm);
		}
		//3、若是瀑布型混合推荐算法
		else if (algoElement.elementText("algorithmType").equals("waterfall")) {
			List<Element> algorithmList = algoElement.elements("algorithm");
			List<Entry<AbstractSingleAlgorithmConfigModel, Integer>> list = new ArrayList<Map.Entry<AbstractSingleAlgorithmConfigModel,Integer>>();
			for (Element algorithmElement : algorithmList) {
				AbstractSingleAlgorithmConfigModel singleAlgorithm = analyzeSingleAlgorithmPart(algorithmElement);
				Entry<AbstractSingleAlgorithmConfigModel, Integer> entry = new MyEntry(singleAlgorithm, Integer.parseInt(algorithmElement.elementText("resultNum")));
				list.add(entry);
			}
			algorithm = new WaterfallMixAlgorithmConfigModel(list);
			integralAlgorithm = new IntegralAlgorithmConfigModel("waterfall", algorithm);
		}
		//4、异常情况
		else {
			
		}
		return integralAlgorithm;
	}

	/**
	 * 解析XML配置文件中的单个基本推荐算法
	 * @param algorithmElement "algorithm"标签节点
	 * @return 单个基本推荐算法的参数配置模型
	 */
	private AbstractSingleAlgorithmConfigModel analyzeSingleAlgorithmPart(Element algorithmElement) {
		AbstractSingleAlgorithmConfigModel singleAlgorithm = null;	
		//1.1、若是基于内容的推荐算法
		if (algorithmElement.elementText("algorithmName").equals("content-based")) {
			singleAlgorithm = new ContentBasedAlgorithmConfigModel();
		}
		//1.2、若是基于用户的协同过滤推荐算法
		else if (algorithmElement.elementText("algorithmName").equals("user-based-cf")) {
			Double threshold = algorithmElement.elementText("threshold") == null ? null : Double.valueOf(algorithmElement.elementText("threshold"));
			Integer nUser = algorithmElement.elementText("nUser") == null ? null : Integer.parseInt(algorithmElement.elementText("nUser"));
			singleAlgorithm = new UserBasedCFAlgorithmConfigModel(algorithmElement.elementText("userNeighborhood"), threshold, nUser);
		}
		//1.3、若是基于项目的协同过滤推荐算法
		else if (algorithmElement.elementText("algorithmName").equals("item-based-cf")) {
			singleAlgorithm = new ItemBasedCFAlgorithmConfigModel();
		}
		//1.4、异常情况
		else {
			
		}	
		return singleAlgorithm;
	}

	/**
	 * 解析XML配置文件的相似度度量方法部分
	 * @param rootElement XML配置文件的根节点
	 * @return 相似度度量方法的参数配置模型列表
	 */
	private SimilarityListConfigModel analyzeSimilarityPart(Element rootElement) {
		Element similarityElement = rootElement.element("similarity");
		//若存在"similarity"标签，才进行相似度度量方法的解析操作，否则什么都不做
		if (similarityElement != null) {
			List<AbstractSimilarityConfigModel> similarityConfigModelList = new ArrayList<AbstractSimilarityConfigModel>();
			List<Element> similarityElementList = similarityElement.elements();
			if (similarityElementList != null && similarityElementList.size() > 0) {
				for (Element element : similarityElementList) {
					//1、若是用户间的相似度度量方法
					if (element.getName().equals("userSimilarity")) {
						UserSimilarityConfigModel userSimilarityConfigModel = new UserSimilarityConfigModel(element.getText());
						similarityConfigModelList.add(userSimilarityConfigModel);
					}
					//2、若是项目间的相似度度量方法
					else if (element.getName().equals("itemSimilarity")) {
						ItemSimilarityConfigModel itemSimilarityConfigModel = new ItemSimilarityConfigModel(element.getText());
						similarityConfigModelList.add(itemSimilarityConfigModel);
					}
					//3、异常情况
					else {
						
					}
				}
			}
			return new SimilarityListConfigModel(similarityConfigModelList);
		}
		else {
			return new SimilarityListConfigModel(null);
		}	
	}

	/**
	 * 解析XML配置文件的输入数据源部分
	 * @param rootElement XML配置文件的根节点
	 * @return 输入数据源的参数配置模型列表
	 */
	private DataListConfigModel analyzeDataPart(Element rootElement) {
		List<IntegralDataConfigModel> dataList = new ArrayList<IntegralDataConfigModel>();
		List<Element> list = rootElement.elements("data");	
		//当"data"标签存在时，才进行XML解析，否则什么也不做
		if (list != null && list.size() > 0) {
			for (Element dataElement : list) {
				//1、是来自MySQL的特征数据
				if (dataElement.elementText("dataType").equals("content") && dataElement.elementText("sourceType").equals("mysql")) {
					DBConfigModel dbConfig = new DBConfigModel(dataElement.elementText("dbServerName"), dataElement.elementText("dbUser"), dataElement.elementText("dbPassword"));
					Element contentDataElement = dataElement.element("contentData");
					List<QueryUnitConfigModel> queryUnitList = new ArrayList<QueryUnitConfigModel>();
					List<Element> queryList = contentDataElement.elements("query");
					for (Element element : queryList) {
						QueryUnitConfigModel queryUnit = new QueryUnitConfigModel(element.elementText("userColumn"), element.elementText("itemColumn"), element.elementText("occur"));
						queryUnitList.add(queryUnit);
					}
					
					MySQLContentDataConfigModel contentData = new MySQLContentDataConfigModel(contentDataElement.elementText("dbDatabaseName"), contentDataElement.elementText("userTableName"), contentDataElement.elementText("itemTableName"), queryUnitList);
					IntegralDataConfigModel integralData = new IntegralDataConfigModel("content", "mysql", dbConfig, contentData);
					dataList.add(integralData);
				}
				//2、是来自MySQL的用户偏好数据
				else if (dataElement.elementText("dataType").equals("preference") && dataElement.elementText("sourceType").equals("mysql")) {
					DBConfigModel dbConfig = new DBConfigModel(dataElement.elementText("dbServerName"), dataElement.elementText("dbUser"), dataElement.elementText("dbPassword"));
					Element preferenceDataElement = dataElement.element("preferenceData");
					MySQLPreferenceDataConfigModel mySQLPreference = new MySQLPreferenceDataConfigModel(preferenceDataElement.elementText("dbDatabaseName"), preferenceDataElement.elementText("preferenceTable"), preferenceDataElement.elementText("userIDColumn"), preferenceDataElement.elementText("itemIDColumn"), preferenceDataElement.elementText("preferenceColumn"), preferenceDataElement.elementText("timestampColumn"));
					IntegralDataConfigModel integralData = new IntegralDataConfigModel("preference", "mysql", dbConfig, mySQLPreference);
					dataList.add(integralData);
				}
				//3、是来自文件的用户偏好数据
				else if (dataElement.elementText("dataType").equals("preference") && dataElement.elementText("sourceType").equals("file")) {
					FilePreferenceDataConfigModel filePreference = new FilePreferenceDataConfigModel(dataElement.elementText("filePath"));
					IntegralDataConfigModel integralData = new IntegralDataConfigModel("preference", "file", null, filePreference);
					dataList.add(integralData);
				}
				//4、异常情况
				else {
					
				}
			}
		}
		return new DataListConfigModel(dataList);
	}

}
