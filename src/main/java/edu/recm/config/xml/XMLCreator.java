package edu.recm.config.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import edu.recm.config.model.AbstractAlgorithmConfigModel;
import edu.recm.config.model.AbstractDataConfigModel;
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
 * 生成推荐系统的XML配置文件
 * @author niuzhixiang
 *
 */
public class XMLCreator {
	
	static Logger logger = Logger.getLogger(XMLCreator.class);
	
	/**
	 * 解析推荐系统的参数配置模型，生成对应的XML配置文件
	 * @param configModel 推荐系统的参数配置模型
	 * @throws IOException
	 */
	public void createXMLConfigFile(IntegralConfigModel configModel) throws IOException {
		String recommenderID = configModel.getRecommenderName();
		DataListConfigModel datalist = configModel.getDataList();
		SimilarityListConfigModel similarityList = configModel.getSimilarityList();
		IntegralAlgorithmConfigModel algorithm = configModel.getAlgorithm();
		EvaluatorConfigModel evaluator = configModel.getEvaluator();
		
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("recommender");
		rootElement.addAttribute("recommenderID", recommenderID);
		
		createXMLDataPart(datalist, rootElement);
		createXMLSimilarityPart(similarityList, rootElement);	
		createXMLAlgorithmPart(algorithm, rootElement);
		createXMLEvaluatePart(evaluator, rootElement);
		
		writeToXMLConfigFile(recommenderID, document);
	}

	/**
	 * 写入XML配置文件的IO操作
	 * @param recommenderID 推荐系统的唯一名称
	 * @param document 要写入XML配置文件的Document对象
	 * @throws IOException
	 */
	private void writeToXMLConfigFile(String recommenderID, Document document)
			throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setIndent(true);
		format.setNewlines(true);
		
		logger.info(document.asXML());
		
		String configFilePath = ConfigVariables.XML_CONFIG_FILE_DIR + recommenderID + ".xml";
		File file = new File(configFilePath);
		if (file.exists()) {
			file.delete();
		}
		Writer fileWriter = new FileWriter(file);
		XMLWriter xmlWriter = new XMLWriter(fileWriter, format);
		xmlWriter.write(document);
		xmlWriter.flush();
		xmlWriter.close();
	}

	/**
	 * 生成XML配置文件中的推荐算法评估部分
	 * @param evaluator 推荐算法评估机制的参数配置模型
	 * @param rootElement 要写入的XML文档根元素
	 */
	private void createXMLEvaluatePart(EvaluatorConfigModel evaluator,
			Element rootElement) {
		Element evaluateElement = rootElement.addElement("evaluate");
		
		Element deviationElement = evaluateElement.addElement("deviation");
		deviationElement.setText(evaluator.getDeviation().toString());
		
		Element precisionElement = evaluateElement.addElement("precision");
		precisionElement.setText(evaluator.getPrecision().toString());
		
		Element recallElement = evaluateElement.addElement("recall");
		recallElement.setText(evaluator.getRecall().toString());
		
		Element runningTimeElement = evaluateElement.addElement("runningTime");
		runningTimeElement.setText(evaluator.getRunningTime().toString());
	}

	/**
	 * 生成XML配置文件中的推荐算法处理流程部分
	 * @param algorithm 推荐算法处理流程的参数配置模型
	 * @param rootElement 要写入的XML文档根元素
	 */
	private void createXMLAlgorithmPart(IntegralAlgorithmConfigModel algorithm,
			Element rootElement) {
		Element algoElement = rootElement.addElement("algo");
		
		Element algorithmTypeElement = algoElement.addElement("algorithmType");
		algorithmTypeElement.setText(algorithm.getAlgorithmType());
		//1、若是基本推荐算法，则直接调用createXMLSingleAlgorithmPart()方法
		if (algorithm.getAlgorithmType().toLowerCase().equals("single")) {
			createXMLSingleAlgorithmPart((AbstractSingleAlgorithmConfigModel)(algorithm.getAlgorithmConfigModel()), algoElement);
		}
		//2、若是加权型混合推荐算法
		else if (algorithm.getAlgorithmType().toLowerCase().equals("weighted")) {
			WeightedMixAlgorithmConfigModel weightedMixAlgorithm = (WeightedMixAlgorithmConfigModel) (algorithm.getAlgorithmConfigModel());	
			for (Entry<AbstractSingleAlgorithmConfigModel, Float> entry : weightedMixAlgorithm.getList()) {
				Element weightElement = createXMLSingleAlgorithmPart(entry.getKey(), algoElement).addElement("weight");
				weightElement.setText(entry.getValue().toString());
			}
		}
		//3、若是瀑布型混合推荐算法
		else if (algorithm.getAlgorithmType().toLowerCase().equals("waterfall")) {
			WaterfallMixAlgorithmConfigModel waterfallMixAlgorithm = (WaterfallMixAlgorithmConfigModel)(algorithm.getAlgorithmConfigModel());
			for (Entry<AbstractSingleAlgorithmConfigModel, Integer> entry : waterfallMixAlgorithm.getList()) {
				Element resultNumElement = createXMLSingleAlgorithmPart(entry.getKey(), algoElement).addElement("resultNum");
				resultNumElement.setText(entry.getValue().toString());
			}
		}
		//4、异常情况
		else {
			
		}
	}

	/**
	 * 生成XML配置文件中的基本推荐算法部分
	 * @param singleAlgorithm 基本推荐算法的参数配置模型
	 * @param algoElement 要写入的"algo"标签元素
	 * @return 描述基本推荐算法的"algorithm"标签
	 */
	private Element createXMLSingleAlgorithmPart(
			AbstractSingleAlgorithmConfigModel singleAlgorithm, Element algoElement) {
		Element algorithmElement = algoElement.addElement("algorithm");
		//1、是基于内容的推荐算法
		if (singleAlgorithm instanceof ContentBasedAlgorithmConfigModel) {
			Element algorithmNameElement = algorithmElement.addElement("algorithmName");
			algorithmNameElement.setText("content-based");
		}
		//2、是基于用户的协同过滤推荐算法
		else if (singleAlgorithm instanceof UserBasedCFAlgorithmConfigModel) {
			UserBasedCFAlgorithmConfigModel theAlgorithm = (UserBasedCFAlgorithmConfigModel) singleAlgorithm;
			
			Element algorithmNameElement = algorithmElement.addElement("algorithmName");
			algorithmNameElement.setText("user-based-cf");
			
			Element userNeighborhoodElement = algorithmElement.addElement("userNeighborhood");
			userNeighborhoodElement.setText(theAlgorithm.getUserNeighborhoodType());
			
			if (theAlgorithm.getnUser() != null && theAlgorithm.getThreshold() == null) {
				Element nUserElement = algorithmElement.addElement("nUser");
				nUserElement.setText(theAlgorithm.getnUser().toString());
			}
			if (theAlgorithm.getnUser() == null && theAlgorithm.getThreshold() != null) {
				Element thresholdElement = algorithmElement.addElement("threshold");
				thresholdElement.setText(theAlgorithm.getThreshold().toString());
			}
		}
		//3、是基于项目的协同过滤推荐算法
		else if (singleAlgorithm instanceof ItemBasedCFAlgorithmConfigModel) {
			Element algorithmNameElement = algorithmElement.addElement("algorithmName");
			algorithmNameElement.setText("item-based-cf");
		} 
		//4、异常情况
		else {
			
		}
		return algorithmElement;
	}

	/**
	 * 生成XML配置文件中的相似度度量方法部分
	 * @param similarityList 相似度度量方法参数配置模型的列表
	 * @param rootElement 要写入的XML文档根元素
	 */
	private void createXMLSimilarityPart(
			SimilarityListConfigModel similarityList, Element rootElement) {
		//仅在相似度度量方法列表不为空的情况下，才进行XML写入操作，否则什么也不做
		if (similarityList.getSimilarityConfigModelList() != null && similarityList.getSimilarityConfigModelList().size() > 0) {
			Element similarityElement = rootElement.addElement("similarity");
			for (AbstractSimilarityConfigModel similarity : similarityList.getSimilarityConfigModelList()) {	
				//1、是用户间相似度度量方法的情况
				if (similarity instanceof UserSimilarityConfigModel) {
					UserSimilarityConfigModel theSimilarity = (UserSimilarityConfigModel) similarity;
					
					Element userSimilarityElement = similarityElement.addElement("userSimilarity");
					userSimilarityElement.setText(theSimilarity.getSimilarityType());
				}
				//2、是项目间相似度度量方法的情况
				else if (similarity instanceof ItemSimilarityConfigModel) {
					ItemSimilarityConfigModel theSimilarity = (ItemSimilarityConfigModel) similarity;
					
					Element itemSimilarityElement = similarityElement.addElement("itemSimilarity");
					itemSimilarityElement.setText(theSimilarity.getSimilarityType());
				}
				//3、异常情况
				else {
					
				}
			}
		}
	}

	/**
	 * 生成XML配置文件中的输入数据源部分
	 * @param datalist 输入数据源参数配置模型的列表
	 * @param rootElement 要写入的XML文档根元素
	 */
	private void createXMLDataPart(DataListConfigModel datalist,
			Element rootElement) {
		//仅在输入数据源列表不为空的情况下，才进行XML写入操作，否则什么也不做
		if (datalist.getDataConfigModelList() != null && datalist.getDataConfigModelList().size() > 0) {
			for (IntegralDataConfigModel integralData : datalist.getDataConfigModelList()) {
				AbstractDataConfigModel dataConfig = integralData.getDataConfigModel();
				//1、是特征数据的情况
				if (dataConfig instanceof MySQLContentDataConfigModel) {
					Element dataElement = rootElement.addElement("data");
					
					MySQLContentDataConfigModel theDataConfig = (MySQLContentDataConfigModel) dataConfig;
					Element dataTypeElement = dataElement.addElement("dataType");
					dataTypeElement.setText("content");
					
					Element sourceTypeElement = dataElement.addElement("sourceType");
					sourceTypeElement.setText("mysql");
					
					Element dbServerNameElement = dataElement.addElement("dbServerName");
					dbServerNameElement.setText(integralData.getDbConfigModel().getDbServerName());
					
					Element dbUserElement = dataElement.addElement("dbUser");
					dbUserElement.setText(integralData.getDbConfigModel().getDbUser());
					
					Element dbPasswordElement = dataElement.addElement("dbPassword");
					dbPasswordElement.setText(integralData.getDbConfigModel().getDbPassword());
					
					Element contentDataElement = dataElement.addElement("contentData");
					
					Element dbDatabaseNameElement = contentDataElement.addElement("dbDatabaseName");
					dbDatabaseNameElement.setText(theDataConfig.getDbDatabaseName());
					
					Element userTableNameElement = contentDataElement.addElement("userTableName");
					userTableNameElement.setText(theDataConfig.getUserTableName());
					
					Element itemTableNameElement = contentDataElement.addElement("itemTableName");
					itemTableNameElement.setText(theDataConfig.getItemTableName());
					
					if (theDataConfig.getQueryUnitConfigModelList() != null && theDataConfig.getQueryUnitConfigModelList().size() > 0) {
						for (QueryUnitConfigModel queryUnit : theDataConfig.getQueryUnitConfigModelList()) {
							Element queryElement = contentDataElement.addElement("query");
							
							Element userColumnElement = queryElement.addElement("userColumn");
							userColumnElement.setText(queryUnit.getUserColumn());
							
							Element itemColumnElement = queryElement.addElement("itemColumn");
							itemColumnElement.setText(queryUnit.getItemColumn());
							
							Element occurElement = queryElement.addElement("occur");
							occurElement.setText(queryUnit.getOccur());
						}
					}	
				}
				//2、是来源于MySQL的用户偏好数据
				else if (dataConfig instanceof MySQLPreferenceDataConfigModel) {
					Element dataElement = rootElement.addElement("data");
					
					MySQLPreferenceDataConfigModel theDataConfig = (MySQLPreferenceDataConfigModel) dataConfig;
					Element dataTypeElement = dataElement.addElement("dataType");
					dataTypeElement.setText("preference");
					
					Element sourceTypeElement = dataElement.addElement("sourceType");
					sourceTypeElement.setText("mysql");
					
					Element dbServerNameElement = dataElement.addElement("dbServerName");
					dbServerNameElement.setText(integralData.getDbConfigModel().getDbServerName());
					
					Element dbUserElement = dataElement.addElement("dbUser");
					dbUserElement.setText(integralData.getDbConfigModel().getDbUser());
					
					Element dbPasswordElement = dataElement.addElement("dbPassword");
					dbPasswordElement.setText(integralData.getDbConfigModel().getDbPassword());
					
					Element preferenceDataElement = dataElement.addElement("preferenceData");
					
					Element dbDatabaseNameElement = preferenceDataElement.addElement("dbDatabaseName");
					dbDatabaseNameElement.setText(theDataConfig.getDbDatabaseName());
					
					Element preferenceTableElement = preferenceDataElement.addElement("preferenceTable");
					preferenceTableElement.setText(theDataConfig.getPreferenceTable());
					
					Element userIDElement = preferenceDataElement.addElement("userIDColumn");
					userIDElement.setText(theDataConfig.getUserIDColumn());
					
					Element itemIDElement = preferenceDataElement.addElement("itemIDColumn");
					itemIDElement.setText(theDataConfig.getItemIDColumn());
					
					if (theDataConfig.getPreferenceColumn() != null && ! theDataConfig.getPreferenceColumn().trim().equals("")) {
						Element preferenceColumnElement = preferenceDataElement.addElement("preferenceColumn");
						preferenceColumnElement.setText(theDataConfig.getPreferenceColumn());
					}
					if (theDataConfig.getTimestampColumn() != null && ! theDataConfig.getTimestampColumn().trim().equals("")) {
						Element timestampElement = preferenceDataElement.addElement("timestampColumn");
						timestampElement.setText(theDataConfig.getTimestampColumn());
					}		
				}
				//3、是来源于文件的用户偏好数据
				else if (dataConfig instanceof FilePreferenceDataConfigModel) {
					Element dataElement = rootElement.addElement("data");
					
					FilePreferenceDataConfigModel theDataConfig = (FilePreferenceDataConfigModel) dataConfig;
					Element dataTypeElement = dataElement.addElement("dataType");
					dataTypeElement.setText("preference");
					
					Element sourceTypeElement = dataElement.addElement("sourceType");
					sourceTypeElement.setText("file");
					
					Element filePathElement = dataElement.addElement("filePath");
					filePathElement.setText(theDataConfig.getFilePath());
				}
				//4、异常情况
				else {
					
				}
			}
		}
	}
	
}
