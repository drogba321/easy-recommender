package edu.recm.algorithm.algorithm;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.recm.algorithm.data.MySQLContentData;
import edu.recm.algorithm.data.QueryUnit;
import edu.recm.algorithm.data.ResultBean;
import edu.recm.util.MySQLUtil;

/**
 * 基于内容的推荐器（采用变种形式的算法）
 * @author niuzhixiang
 *
 */
public class ContentBasedRecommender implements MyRecommender {
	
	static Logger logger = Logger.getLogger(ContentBasedRecommender.class);
	
	/**
	 * 该推荐器所属的推荐系统名称
	 */
	private String recommenderSystemName;
	
	/**
	 * 索引的根路径
	 */
	private static String INDEX_PATH = "D:/lucene_index/";
	
	/**
	 * 用户与项目特征数据源
	 */
	private MySQLContentData contentData;
	
	/**
	 * MySQL Statement对象
	 */
	private Statement statement;
	
	/**
	 * 过滤器，使得searcher在经过过滤之后的索引文档上进行搜索
	 */
	private Filter filter;
	
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public ContentBasedRecommender(String recommenderSystemName, MySQLContentData contentData, Filter filter) {
		super();
		this.recommenderSystemName = recommenderSystemName;
		this.contentData = contentData;
		this.filter = filter;
		this.statement = MySQLUtil.connectMySQL(contentData.getDbServerName(), contentData.getDbUser(), contentData.getDbPassword(), contentData.getDbDatabaseName());
	}

	/**
	 * 建立索引的内部类
	 * @author niuzhixiang
	 *
	 */
	class IndexBuilder{
		
		private IndexWriter indexWriter = null;
		private Analyzer analyzer = null;
		private IndexWriterConfig config = null;
			
		/**
		 * 初始化索引器indexWriter
		 * @throws IOException
		 */
		@SuppressWarnings("deprecation")
		public void initIndexWriter() throws IOException {
			analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
			config = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
			indexWriter = new IndexWriter(FSDirectory.open(new File(ContentBasedRecommender.INDEX_PATH + ContentBasedRecommender.this.recommenderSystemName)), config);
		}
		
		/**
		 * 为项目特征数据建立索引
		 * @throws IOException
		 * @throws SQLException
		 */
		public void buildIndex() throws IOException, SQLException {
			indexWriter.deleteAll();
			List<QueryUnit> queries = ContentBasedRecommender.this.contentData.getQueryList();
			String sql = "select * from " + ContentBasedRecommender.this.contentData.getItemTableName();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Document document = new Document();
				Field idField = new TextField("ID", String.valueOf(rs.getInt("ID")), Store.YES);
				document.add(idField);
				for (QueryUnit queryUnit : queries) {
					document.add(new TextField(queryUnit.getItemField(), rs.getString(queryUnit.getItemField()), Store.NO));
				}
				indexWriter.addDocument(document);
			}
			indexWriter.commit();
			indexWriter.close();
		}

	}
	
	/**
	 * 组装查询语句的内部类
	 * @author niuzhixiang
	 *
	 */
	class QueryBuilder{
		
		private BooleanQuery booleanQuery = new BooleanQuery();
		
		public BooleanQuery getBooleanQuery() {
			return booleanQuery;
		}

		/**
		 * 为指定用户组装复杂查询语句
		 * @param userid 用户ID
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public void buildQuery(int userid) throws Exception {
			List<QueryUnit> queries = ContentBasedRecommender.this.contentData.getQueryList();
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
			List<String> userFeatures = new ArrayList<String>();

			String sql = "select * from " + ContentBasedRecommender.this.contentData.getUserTableName() + " where ID = " + userid;
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				for (QueryUnit queryUnit : queries) {
					userFeatures.add((String) (rs.getObject(queryUnit.getUserField())));
				}
			}
			
			if(queries.size() != userFeatures.size()){
				analyzer.close();
				throw new Exception("QueryList与userFeatures长度不一致！");
			}
			
			for (int i = 0; i < userFeatures.size(); i++) {
				logger.info("userFeatureName:" + queries.get(i).getUserField() + ", userFeatureData:" + userFeatures.get(i) + ", itemFeatureName:" + queries.get(i).getItemField());
				QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, queries.get(i).getItemField(), analyzer);
				Query query = parser.parse(userFeatures.get(i));
				this.booleanQuery.add(query, queries.get(i).getOccur());
			}
		}
	}
	
	/**
	 * 进行搜索的内部类
	 * @author niuzhixiang
	 *
	 */
	class Searcher{
		
		private IndexSearcher searcher;
		
		public IndexSearcher getSearcher() {
			return searcher;
		}
		
		/**
		 * 在索引上进行搜索，返回搜索结果ScoreDoc数组
		 * @param query
		 * @param resultNum 搜索结果数目
		 * @return
		 * @throws IOException
		 */
		public ScoreDoc[] search(Query query, int resultNum, Filter filter) throws IOException {
			IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(ContentBasedRecommender.INDEX_PATH + ContentBasedRecommender.this.recommenderSystemName)));
			this.searcher = new IndexSearcher(indexReader);
			return this.searcher.search(query, filter, resultNum).scoreDocs;
		}
	}
	
	public List<ResultBean> doRecommend(int userid, int resultNum) throws Exception {
		//1、创建索引内部类
		IndexBuilder indexBuilder = this.new IndexBuilder();
		//初始化索引器indexWriter
		indexBuilder.initIndexWriter();
		//建立索引
		indexBuilder.buildIndex();
		
		//2、创建查询语句内部类
		QueryBuilder queryBuilder = this.new QueryBuilder();
		//组装复杂查询语句
		queryBuilder.buildQuery(userid);
		
		//3、创建搜索内部类
		Searcher searcher = this.new Searcher();
		//执行搜索，获取搜索结果
		ScoreDoc[] hits = searcher.search(queryBuilder.getBooleanQuery(), resultNum, this.filter);
		
		//4、组装搜索结果
		List<ResultBean> resultList = new ArrayList<ResultBean>();
		for (ScoreDoc scoreDoc : hits) {
			int id = Integer.parseInt(searcher.getSearcher().doc(scoreDoc.doc).get("ID"));
			float score = scoreDoc.score;
			logger.info("id:" + id + ", score:" + score);
			ResultBean rb = new ResultBean(id, score);
			resultList.add(rb);
		}
		logger.info("========================");
		return resultList;
	}
}
