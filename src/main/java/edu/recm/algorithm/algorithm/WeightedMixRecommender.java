package edu.recm.algorithm.algorithm;

import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.recommender.Recommender;

public class WeightedMixRecommender {
	
	private List<Map<Float, Recommender>> recommenderList;

	public List<Map<Float, Recommender>> getRecommenderList() {
		return recommenderList;
	}

	public void setRecommenderList(List<Map<Float, Recommender>> recommenderList) {
		this.recommenderList = recommenderList;
	}
	
	public WeightedMixRecommender() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeightedMixRecommender(List<Map<Float, Recommender>> recommenderList) {
		super();
		this.recommenderList = recommenderList;
	}

	public List<Integer> doRecommend() {
		return null;
	}

}
