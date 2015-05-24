package test.config.web;

import org.junit.Ignore;
import org.junit.Test;

import edu.recm.config.model.IntegralConfigModel;
import edu.recm.config.web.WebParamAnalyzer;
import edu.recm.config.xml.XMLCreator;

public class WebParamAnalyzerTest {

	/**
	 * 测试“解析前端配置页面填写的参数并建立相应的推荐系统参数配置模型”的功能
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testBuildConfigModel() throws Exception {
		String inputString = "{'recommenderName':'c','dataList':[{'dataType':'content','sourceType':'mysql','data':{'dbServerName':'c','dbUser':'c','dbPassword':'c','dbDatabaseName':'c','userTable':'c','itemTable':'c','queryList':[{'userColumn':'c','itemColumn':'c','occur':'MUST_NOT'},{'userColumn':'c','itemColumn':'c','occur':'SHOULD'}]}},{'dataType':'preference','sourceType':'mysql','data':{'dbServerName':'c','dbUser':'c','dbPassword':'c','dbDatabaseName':'c','preferenceTable':'c','userIDColumn':'c','itemIDColumn':'c','preferenceColumn':'c','timestampColumn':'c'}},{'dataType':'preference','sourceType':'file','data':{'filePath':'C:/fakepath/applylog.csv'}}],'similarityList':[{'similarity':'userSimilarity','similarityType':'euclideanDistanceSimilarity'},{'similarity':'itemSimilarity','similarityType':'logLikelihoodSimilarity'}],'algorithm':{'algorithmType':'weighted','list':[{'name':'contentBased','weight':'2'},{'name':'userBasedCF','weight':'3','userNeighborhoodType':'nearestN','nearestN':'2'},{'name':'itemBasedCF','weight':'4'}]},'evaluator':{'deviation':'false','precision':'false','recall':'false','runningTime':'false'}}";
		String inputString2 = "{'recommenderName':'c-single','dataList':[{'dataType':'content','sourceType':'mysql','data':{'dbServerName':'c','dbUser':'c','dbPassword':'c','dbDatabaseName':'c','userTable':'c','itemTable':'c','queryList':[{'userColumn':'c','itemColumn':'c','occur':'MUST'},{'userColumn':'c','itemColumn':'c','occur':'MUST_NOT'}]}}],'similarityList':[],'algorithm':{'algorithmType':'single','list':[{'name':'contentBased'}]},'evaluator':{'deviation':'false','precision':'false','recall':'false','runningTime':'false'}}";
		
		String dataListString = "[{'dataType':'content','sourceType':'mysql','data':{'dbServerName':'c','dbUser':'c','dbPassword':'c','dbDatabaseName':'c','userTable':'c','itemTable':'c','queryList':[{'userColumn':'c','itemColumn':'c','occur':'MUST_NOT'},{'userColumn':'c','itemColumn':'c','occur':'SHOULD'}]}},{'dataType':'preference','sourceType':'mysql','data':{'dbServerName':'c','dbUser':'c','dbPassword':'c','dbDatabaseName':'c','preferenceTable':'c','userIDColumn':'c','itemIDColumn':'c','preferenceColumn':'c','timestampColumn':'c'}},{'dataType':'preference','sourceType':'file','data':{'filePath':'C:\fakepathapplylog.csv'}}]";
		String similarityString = "[{'similarity':'userSimilarity','similarityType':'euclideanDistanceSimilarity'},{'similarity':'itemSimilarity','similarityType':'logLikelihoodSimilarity'}]";
		String algorithmString = "{'algorithmType':'weighted','list':[{'name':'contentBased','weight':'2'},{'name':'userBasedCF','weight':'3','userNeighborhoodType':'nearestN','nearestN':'2'},{'name':'itemBasedCF','weight':'4'}]}";
		String evaluatorString = "{'deviation':'false','precision':'false','recall':'false','runningTime':'false'}";
		
		WebParamAnalyzer webParamAnalyzer = new WebParamAnalyzer();
		IntegralConfigModel config = webParamAnalyzer.buildConfigModel(inputString);
		
		XMLCreator xmlCreator = new XMLCreator();
		xmlCreator.createXMLConfigFile(config);
	}
}
