package edu.recm.algorithm.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mahout.cf.taste.recommender.Recommender;

public class WaterfallMixRecommender {
	
	private List<Entry<Integer, Recommender>> recommenderList;

	public List<Entry<Integer, Recommender>> getRecommenderList() {
		return recommenderList;
	}

	public void setRecommenderList(List<Entry<Integer, Recommender>> recommenderList) {
		this.recommenderList = recommenderList;
	}

	public WaterfallMixRecommender() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WaterfallMixRecommender(
			List<Entry<Integer, Recommender>> recommenderList) {
		super();
		this.recommenderList = recommenderList;
	}

	public List<Integer> doRecommend() {
		return null;
	}
}
