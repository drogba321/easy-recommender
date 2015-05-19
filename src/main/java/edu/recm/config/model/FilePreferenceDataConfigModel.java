package edu.recm.config.model;

/**
 * 来源于文件的用户偏好数据的参数配置模型
 * @author niuzhixiang
 *
 */
public class FilePreferenceDataConfigModel extends AbstractPreferenceDataConfigModel {

	private String filePath;

	/**
	 * 用户偏好数据的文件路径
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public FilePreferenceDataConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FilePreferenceDataConfigModel(String filePath) {
		super();
		this.filePath = filePath;
	}
	
}
