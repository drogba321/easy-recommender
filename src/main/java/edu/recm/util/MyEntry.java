package edu.recm.util;

import java.util.Map.Entry;

import edu.recm.algorithm.algorithm.MyRecommender;

/**
 * java.util.Map.Entry<K, V>的自定义实现类
 * @author niuzhixiang
 *
 */
public class MyEntry<K, V> implements Entry<K, V> {

	private final K key;
	private V value;
	
	public MyEntry(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		// TODO Auto-generated method stub
		return this.key;
	}

	public V getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	public V setValue(V value) {
		// TODO Auto-generated method stub
		V old = this.value;
		this.value = value;
		return old;
	}

}
