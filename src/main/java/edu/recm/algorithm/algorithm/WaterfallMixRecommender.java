package edu.recm.algorithm.algorithm;

import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.util.Version;
import org.apache.mahout.cf.taste.common.NoSuchUserException;

import edu.recm.algorithm.algorithm.MyRecommender;
import edu.recm.algorithm.data.FilePreferenceData;
import edu.recm.algorithm.data.MySQLPreferenceData;
import edu.recm.algorithm.data.ResultBean;
import edu.recm.util.MySQLUtil;

/**
 * 瀑布型混合推荐器
 * @author niuzhixiang
 *
 */
public class WaterfallMixRecommender implements MyRecommender {
	
	static Logger logger = Logger.getLogger(WaterfallMixRecommender.class);
	
	private List<Entry<MyRecommender, Integer>> recommenderList;

	public List<Entry<MyRecommender, Integer>> getRecommenderList() {
		return recommenderList;
	}

	public void setRecommenderList(List<Entry<MyRecommender, Integer>> recommenderList) {
		this.recommenderList = recommenderList;
	}
	
	public final static String TMP_CSV_FILE_DIR = "D:/tempcsv";

	public WaterfallMixRecommender() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WaterfallMixRecommender(
			List<Entry<MyRecommender, Integer>> recommenderList) {
		super();
		this.recommenderList = recommenderList;
	}
	
	public List<ResultBean> doRecommend(int userid, int resultNum) throws Exception {
		List<ResultBean> middleResultList = null;
		List<ResultBean> finalResultList = null;
		//遍历所有Recommender
		for (int i = 0; i < this.recommenderList.size(); i++) {
			Entry<MyRecommender, Integer> recommenderEntry = recommenderList.get(i);
			//若当前是第一个Recommender，则直接执行
			if (i == 0) {
				finalResultList = middleResultList = recommenderEntry.getKey().doRecommend(userid, recommenderEntry.getValue());
			}
			//若当前不是第一个Recommender，则需要在前面Recommender筛选过后的数据源上进行推荐
			else {
				//1、如果当前Recommender是基于内容的推荐，则执行currentIsContentBased()方法，当前Recommender的输出作为中间结果保存在middleResultList中，并作为下一个Recommender的输入
				if (recommenderEntry.getKey() instanceof ContentBasedRecommender) {
					finalResultList = middleResultList = this.currentIsContentBased(recommenderEntry.getKey(), middleResultList, userid, recommenderEntry.getValue());
				}
				//2、如果当前Recommender是协同过滤推荐，则执行currentIsCF()方法，当前Recommender的输出作为中间结果保存在middleResultList中，并作为下一个Recommender的输入
				else if ((recommenderEntry.getKey() instanceof UserBasedCFRecommender) || (recommenderEntry.getKey() instanceof ItemBasedCFRecommender)) {
					finalResultList = middleResultList = this.currentIsCF(recommenderEntry.getKey(), middleResultList, userid, recommenderEntry.getValue());
				} else {
					throw new Exception("Recommender类型不正确！");
				}
			}
		}
		
		//若最终推荐结果集不为空，则打印并返回最终的混合推荐结果集
		if (finalResultList != null && finalResultList.size() > 0) {
			List<ResultBean> returnList = resultNum < finalResultList.size() ? finalResultList.subList(0, resultNum) : finalResultList;
			for (ResultBean resultBean : returnList) {
				logger.info("final - id:" + resultBean.getId() + ", score:" + resultBean.getScore());
			}
			return returnList;
		}
		else {
			return null;
		}
	}
	
	/**
	 * 生成存储中间数据的CSV文件<br/>
	 * 在上一个Recommender是基于内容的推荐、而当前Recommender是基于项目的协同过滤推荐时，将上一个Recommender筛选得到的用户偏好数据
	 * 存入CSV文件中，作为当前Recommender的输入
	 * @return
	 */
	private File generateTmpCSVFile() {
		File dir = new File(TMP_CSV_FILE_DIR);
		if (! dir.exists()) {
			dir.mkdir();
		}
		return new File(TMP_CSV_FILE_DIR + "/" + System.currentTimeMillis() + "_" + Integer.toString(Math.abs(new Random().nextInt())) + ".csv");
	}
	
	/**
	 * 处理“当前Recommender是协同过滤推荐”的情形，生成当前Recommender的推荐结果集
	 * @param recommender 当前的Recommender，它必须是协同过滤推荐器
	 * @param inputList 上一个Recommender的推荐结果集
	 * @param userid 用户ID
	 * @param resultNum 当前Recommender的推荐结果数目
	 * @return 当前Recommender的推荐结果集
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public List<ResultBean> currentIsCF(MyRecommender recommender, List<ResultBean> inputList, int userid, int resultNum) throws Exception {
		if (! (recommender instanceof ItemBasedCFRecommender) && ! (recommender instanceof UserBasedCFRecommender)) {
			throw new Exception("当前推荐器应为协同过滤推荐器！");
		}
		
		AbstractCFRecommender theRecommender = (AbstractCFRecommender) recommender;
		
		if (! (theRecommender.getPreferenceData() instanceof MySQLPreferenceData)) {
			throw new Exception("在瀑布型混合推荐中仅支持来自MySQL数据源的用户偏好数据！");
		}
		
		//若上一个Recommender的推荐结果集为空，则直接令当前Recommender的推荐结果集也为空
		if (inputList == null || inputList.size() < 1) {
			return null;
		}
		
		//获取当前Recommender的原输入（未经过上一个Recommender筛选的用户偏好数据）
		MySQLPreferenceData preferenceData = (MySQLPreferenceData) theRecommender.getPreferenceData();
		Statement statement = MySQLUtil.connectMySQL(preferenceData.getDbServerName(), preferenceData.getDbUser(), preferenceData.getDbPassword(), preferenceData.getDbDatabaseName());
		
		//创建CSV文件，作为当前Recommender的新输入（已经过上一个Recommender筛选的用户偏好数据）
		File tmpCSVfile = this.generateTmpCSVFile();
		FileWriter fileWriter = new FileWriter(tmpCSVfile);
		
		//遍历上一个Recommender的推荐结果集，依次从MySQL中查询结果集中每个项目对应的用户偏好数据，并写入CSV文件中
		for (ResultBean resultBean : inputList) {
			int itemId = resultBean.getId();
			String sql = "select * from " + preferenceData.getPreferenceTable() + " where " + preferenceData.getItemIDColumn() + " = " + itemId;
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Object[] eachPreference = new Object[3];
				//1、一条用户偏好数据中的用户ID
				eachPreference[0] = rs.getObject(preferenceData.getUserIDColumn());
				fileWriter.append(((Integer) eachPreference[0]).toString());
				fileWriter.append(',');
				//2、一条用户偏好数据中的项目ID
				eachPreference[1] = rs.getObject(preferenceData.getItemIDColumn());
				fileWriter.append(((Integer) eachPreference[1]).toString());	
				//3、一条用户偏好数据中的用户偏好值（在有偏好值的情况下）
				if (preferenceData.getPreferenceColumn() != null) {
					fileWriter.append(',');
					eachPreference[2] = rs.getObject(preferenceData.getPreferenceColumn());
					fileWriter.append(((Float) eachPreference[2]).toString());
				}
				fileWriter.append('\n');
			}
		}
		fileWriter.flush();
		fileWriter.close();
		
		//把经过上一个Recommender筛选得到的用户偏好数据写入CSV文件后，将该CSV文件作为新的数据源，执行当前Recommender
		FilePreferenceData filePreferenceData = new FilePreferenceData(tmpCSVfile.getAbsolutePath());
		AbstractCFRecommender recommender2 = null;
		if (theRecommender instanceof ItemBasedCFRecommender) {
			ItemBasedCFRecommender ibr = (ItemBasedCFRecommender) theRecommender;
			recommender2 = new ItemBasedCFRecommender(ibr.getRecommenderSystemName(), filePreferenceData, ibr.getSimilarityType());
		} else {
			UserBasedCFRecommender ubr = (UserBasedCFRecommender) theRecommender;
			recommender2 = new UserBasedCFRecommender(ubr.getRecommenderSystemName(), filePreferenceData, ubr.getSimilarityType(), ubr.getnNearestUser(), ubr.getThreshold());
		}

		//删除CSV文件（不过目前不起作用，应该是Mahout的DataModel占用了该文件的原因）
		tmpCSVfile.delete();
		
		List<ResultBean> resultList = new ArrayList<ResultBean>();
		//获取当前Recommender的推荐结果集
		try {
			//1、若经过上一个Recommender筛选之后的用户偏好数据包含当前用户ID的话，则正常地执行当前基于项目的协同过滤推荐
			resultList = recommender2.doRecommend(Integer.parseInt(userid + ""), resultNum);
		} catch (NoSuchUserException e) {
			//2、若经过上一个Recommender筛选之后的用户偏好数据不包含当前用户，则无法针对当前用户做基于项目的协同过滤推荐，只能将上一个Recommender的推荐结果集截取前一部分作为当前Recommender的推荐结果集
			resultList = inputList.subList(0, resultNum);
			for (ResultBean resultBean : resultList) {
				logger.info("id:" + resultBean.getId() + ", score:" + resultBean.getScore());
			}
			logger.info("========================");
		} finally {		
			return resultList;
		}
	}
	
	/**
	 * 处理“当前Recommender是基于内容的推荐”的情形，生成当前Recommender的推荐结果集
	 * @param recommender 当前的Recommender，它必须是基于内容的推荐器
	 * @param inputList 上一个Recommender的推荐结果集
	 * @param userid 用户ID
	 * @param resultNum 当前Recommender的推荐结果数目
	 * @return 当前Recommender的推荐结果集
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public List<ResultBean> currentIsContentBased(MyRecommender recommender, List<ResultBean> inputList, int userid, int resultNum) throws Exception {
		if (! (recommender instanceof ContentBasedRecommender)) {
			throw new Exception("当前推荐器应为基于内容的推荐器！");
		}
		
		//若上一个Recommender的推荐结果集为空，则直接令当前Recommender的推荐结果集也为空
		if (inputList == null || inputList.size() < 1) {
			return null;
		}
		
		//当前Recommender必须是基于内容的推荐器
		ContentBasedRecommender theRecommender = (ContentBasedRecommender) recommender;
		
		/*采用Lucene的过滤器机制，获取当前Recommender的新输入（具体做法是：把上一个Recommender的推荐结果集中包含的项目
		 * 作为限定范围，对当前Recommender的索引进行过滤，过滤后的索引只包含限定范围中的项目。在过滤之后的索引文档上进行搜索，
		 * 返回当前Recommender的推荐结果集）*/
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		BooleanQuery booleanQuery = new BooleanQuery();
		for (ResultBean resultBean : inputList) {		
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "ID", analyzer);
			Query query = parser.parse(String.valueOf(resultBean.getId()));
			booleanQuery.add(query, Occur.SHOULD);
		}
		Filter filter = new QueryWrapperFilter(booleanQuery);	
		theRecommender.setFilter(filter);
		
		return theRecommender.doRecommend(userid, resultNum);
	}
	
}
