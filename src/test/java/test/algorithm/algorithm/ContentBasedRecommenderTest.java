package test.algorithm.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
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
	 * @throws ParseException 
	 */
	@Test
	@Ignore
	public void testDorecommend() throws ParseException {
		long preTime = System.currentTimeMillis();
		
		List<QueryUnit> queries = new ArrayList<QueryUnit>();
		queries.add(new QueryUnit("DEGREE", "DEGREE", Occur.MUST));
		queries.add(new QueryUnit("HOMEPLACE", "AREA", Occur.MUST));
		
		MySQLContentData contentData = new MySQLContentData("localhost", "server", "server", "easyrecdemo", "student", "job", queries);
		
		ContentBasedRecommender recommender = new ContentBasedRecommender("job-recommender", contentData, null);
		try {
			recommender.doRecommend(1, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long afterTime = System.currentTimeMillis();
		System.out.println("last:" + (afterTime - preTime)/1000 + "s");
	}

}
