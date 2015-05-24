package test.service;

import org.dom4j.DocumentException;
import org.junit.Ignore;
import org.junit.Test;

import edu.recm.algorithm.algorithm.MyRecommender;
import edu.recm.config.xml.XMLAnalyzer;
import edu.recm.service.common.RecommenderBuilder;

public class RecommenderBuilderTest {
	
	/**
	 * 测试推荐器的创建功能
	 */
	@Test
	@Ignore
	public void testBuildRecommender() {
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder();
		try {
			MyRecommender recommender = recommenderBuilder.buildRecommender(new XMLAnalyzer().analyzeXMLConfigFile("myrecommender-weighted"));
			recommender.doRecommend(1, 10);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
