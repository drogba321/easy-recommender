package test.config.xml;

import org.dom4j.DocumentException;
import org.junit.Ignore;
import org.junit.Test;

import edu.recm.config.model.IntegralConfigModel;
import edu.recm.config.xml.XMLAnalyzer;
import edu.recm.config.xml.XMLCreator;

public class XMLAnalyzerTest {
	
	/**
	 * 测试XML配置文件的解析功能
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testAnalyzeXMLConfigFile() throws Exception {
		XMLAnalyzer xmlAnalyzer = new XMLAnalyzer();
		IntegralConfigModel configModel = xmlAnalyzer.analyzeXMLConfigFile("myrecommender");
		
		configModel.setRecommenderName(configModel.getRecommenderName() + "-copy");
		XMLCreator xmlCreator = new XMLCreator();
		xmlCreator.createXMLConfigFile(configModel);
	}

}
