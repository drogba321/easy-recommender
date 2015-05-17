package edu.recm.algorithm.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * 表示来自文件的用户偏好数据的POJO类
 * @author niuzhixiang
 *
 */
public class FilePreferenceData extends AbstractPreferenceData {
	
	private String filePath;

	/**
	 * 用户偏好数据文件存储路径
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public FilePreferenceData(String filePath) {
		super();
		this.filePath = filePath;
		try {
			this.setDataModel(new FileDataModel(new File(filePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
