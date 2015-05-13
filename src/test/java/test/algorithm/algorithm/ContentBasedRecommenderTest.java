package test.algorithm.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause.Occur;
import org.junit.Ignore;
import org.junit.Test;

import edu.recm.algorithm.algorithm.ContentBasedRecommender;
import edu.recm.algorithm.data.MySQLContentData;
import edu.recm.algorithm.data.QueryUnit;

/**
 * 测试基于内容的推荐器
 * @author niuzhixiang
 *
 */
public class ContentBasedRecommenderTest {
	
	/**
	 * 测试基于内容的推荐
	 */
	@Test
	@Ignore
	public void testDorecommend() {
		long preTime = System.currentTimeMillis();
		
		List<QueryUnit> queries = new ArrayList<QueryUnit>();
		queries.add(new QueryUnit("DEGREE", "DEGREE", Occur.MUST));
		queries.add(new QueryUnit("HOMEPLACE", "AREA", Occur.MUST));
		
		MySQLContentData contentData = new MySQLContentData("localhost", "server", "server", "easyrecdemo", "student", "job", queries);
		ContentBasedRecommender recommender = new ContentBasedRecommender("job-recommender", contentData);
		try {
			recommender.doRecommend(4, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}

}
