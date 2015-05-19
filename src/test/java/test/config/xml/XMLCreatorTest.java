package test.config.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;

import edu.recm.config.model.AbstractSimilarityConfigModel;
import edu.recm.config.model.AbstractSingleAlgorithmConfigModel;
import edu.recm.config.model.ContentBasedAlgorithmConfigModel;
import edu.recm.config.model.DBConfigModel;
import edu.recm.config.model.DataListConfigModel;
import edu.recm.config.model.EvaluatorConfigModel;
import edu.recm.config.model.IntegralAlgorithmConfigModel;
import edu.recm.config.model.IntegralConfigModel;
import edu.recm.config.model.IntegralDataConfigModel;
import edu.recm.config.model.ItemSimilarityConfigModel;
import edu.recm.config.model.MySQLContentDataConfigModel;
import edu.recm.config.model.MySQLPreferenceDataConfigModel;
import edu.recm.config.model.QueryUnitConfigModel;
import edu.recm.config.model.SimilarityListConfigModel;
import edu.recm.config.model.UserBasedCFAlgorithmConfigModel;
import edu.recm.config.model.UserSimilarityConfigModel;
import edu.recm.config.model.WaterfallMixAlgorithmConfigModel;
import edu.recm.config.xml.XMLCreator;
import edu.recm.util.MyEntry;

public class XMLCreatorTest {

	/**
	 * 测试XML配置文件的创建功能
	 * @throws IOException
	 */
	@Test
	@Ignore
	public void testCreateXMLConfigFile() throws IOException {
	
		//1、输入数据源的参数配置模型
		//1.1、特征数据的参数配置模型
		QueryUnitConfigModel queryUnit1 = new QueryUnitConfigModel("major", "occupation_name", "SHOULD");
		QueryUnitConfigModel queryUnit2 = new QueryUnitConfigModel("expect-salary", "salary", "MUST");
		QueryUnitConfigModel queryUnit3 = new QueryUnitConfigModel("expect-address", "address", "MUST");
		
		List<QueryUnitConfigModel> queryUnitList = new ArrayList<QueryUnitConfigModel>();
		queryUnitList.add(queryUnit1);
		queryUnitList.add(queryUnit2);
		queryUnitList.add(queryUnit3);
		
		MySQLContentDataConfigModel contentData = new MySQLContentDataConfigModel("ucareer", "resume", "occupation", queryUnitList);
		DBConfigModel dbConfigModel = new DBConfigModel("127.0.0.1", "root", "root");
		IntegralDataConfigModel integralData1 = new IntegralDataConfigModel("content", "mysql", dbConfigModel, contentData);
		
		//1.2、用户偏好数据的参数配置模型
		MySQLPreferenceDataConfigModel preferenceData = new MySQLPreferenceDataConfigModel("ucareer", "applylog", "userId", "itemId", null, null);
		IntegralDataConfigModel integralData2 = new IntegralDataConfigModel("preference", "mysql", dbConfigModel, preferenceData);
		
		List<IntegralDataConfigModel> integralDataList = new ArrayList<IntegralDataConfigModel>();
		integralDataList.add(integralData1);
		integralDataList.add(integralData2);
		
		DataListConfigModel dataList = new DataListConfigModel(integralDataList);
		
		//2、相似度度量方法的参数配置模型
		ItemSimilarityConfigModel itemSimilarity = new ItemSimilarityConfigModel("tanimotoCoefficientSimilarity");
		List<AbstractSimilarityConfigModel> theSimilarityList = new ArrayList<AbstractSimilarityConfigModel>();
		theSimilarityList.add(itemSimilarity);
		SimilarityListConfigModel similarityList = new SimilarityListConfigModel(theSimilarityList);
		
		//3、算法处理流程的参数配置模型
		ContentBasedAlgorithmConfigModel contentBasedAlgorithm = new ContentBasedAlgorithmConfigModel();
		UserBasedCFAlgorithmConfigModel userBasedCFAlgorithm = new UserBasedCFAlgorithmConfigModel("threshold", 0.1, null);
		Entry<AbstractSingleAlgorithmConfigModel, Integer> entry1 = new MyEntry(contentBasedAlgorithm, 100);
		Entry<AbstractSingleAlgorithmConfigModel, Integer> entry2 = new MyEntry(userBasedCFAlgorithm, 30);
		
		List<Entry<AbstractSingleAlgorithmConfigModel, Integer>> algorithmList = new ArrayList<Map.Entry<AbstractSingleAlgorithmConfigModel,Integer>>();
		algorithmList.add(entry1);
		algorithmList.add(entry2);
		
		WaterfallMixAlgorithmConfigModel waterfallMixAlgorithm = new WaterfallMixAlgorithmConfigModel(algorithmList);	
		IntegralAlgorithmConfigModel algorithm = new IntegralAlgorithmConfigModel("waterfall", waterfallMixAlgorithm);
		
		//4、算法评估机制的参数配置模型
		EvaluatorConfigModel evaluator = new EvaluatorConfigModel(false, false, false, true);
		
		//5、组装整个推荐系统的参数配置模型
		IntegralConfigModel configModel = new IntegralConfigModel("myrecommender", dataList, similarityList, algorithm, evaluator);
		
		XMLCreator xmlProcessor = new XMLCreator();
		xmlProcessor.createXMLConfigFile(configModel);
	
	}
}
