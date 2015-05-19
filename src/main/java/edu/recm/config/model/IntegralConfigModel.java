package edu.recm.config.model;

/**
 * 整个推荐系统的参数配置模型
 * @author niuzhixiang
 *
 */
public class IntegralConfigModel {

	private String recommenderName;
	
	private DataListConfigModel dataList;
	
	private SimilarityListConfigModel similarityList;
	
	private IntegralAlgorithmConfigModel algorithm;
	
	private EvaluatorConfigModel evaluator;

	/**
	 * 推荐系统的唯一名称
	 * @return
	 */
	public String getRecommenderName() {
		return recommenderName;
	}

	public void setRecommenderName(String recommenderName) {
		this.recommenderName = recommenderName;
	}

	/**
	 * 输入数据源参数配置模型的列表
	 * @return
	 */
	public DataListConfigModel getDataList() {
		return dataList;
	}

	public void setDataList(DataListConfigModel dataList) {
		this.dataList = dataList;
	}

	/**
	 * 相似度度量方法参数配置模型的列表
	 * @return
	 */
	public SimilarityListConfigModel getSimilarityList() {
		return similarityList;
	}

	public void setSimilarityList(SimilarityListConfigModel similarityList) {
		this.similarityList = similarityList;
	}

	/**
	 * 推荐算法处理流程参数配置模型
	 * @return
	 */
	public IntegralAlgorithmConfigModel getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(IntegralAlgorithmConfigModel algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * 推荐算法评估机制参数配置模型
	 * @return
	 */
	public EvaluatorConfigModel getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(EvaluatorConfigModel evaluator) {
		this.evaluator = evaluator;
	}

	public IntegralConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IntegralConfigModel(String recommenderName,
			DataListConfigModel dataList,
			SimilarityListConfigModel similarityList,
			IntegralAlgorithmConfigModel algorithm,
			EvaluatorConfigModel evaluator) {
		super();
		this.recommenderName = recommenderName;
		this.dataList = dataList;
		this.similarityList = similarityList;
		this.algorithm = algorithm;
		this.evaluator = evaluator;
	}
	
}
