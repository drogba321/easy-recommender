package edu.recm.service.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.recm.config.model.IntegralConfigModel;
import edu.recm.config.web.WebParamAnalyzer;
import edu.recm.config.xml.XMLCreator;

/**
 * Web配置模块的后端Controller
 * @author niuzhixiang
 *
 */
@Controller
@RequestMapping("/config")
public class WebConfigController {
	
	static Logger logger = Logger.getLogger(WebConfigController.class);
	
	@RequestMapping(value="/start", method=RequestMethod.GET)
	public ModelAndView showStartPage() {
		return new ModelAndView("startconfig");
	}
	
	@RequestMapping(value="/dataconfig", method=RequestMethod.GET)
	public ModelAndView showDataConfigPage() {
		return new ModelAndView("dataconfig");
	}
	
	@RequestMapping(value="/similarityconfig", method=RequestMethod.GET)
	public ModelAndView showSimilarityConfigPage() {
		return new ModelAndView("similarityconfig");
	}
	
	@RequestMapping(value="/algorithmconfig", method=RequestMethod.GET)
	public ModelAndView showAlgorithmConfigPage() {
		return new ModelAndView("algorithmconfig");
	}
	
	@RequestMapping(value="/evaluatorconfig", method=RequestMethod.GET)
	public ModelAndView showEvaluatorConfigPage() {
		return new ModelAndView("evaluatorconfig");
	}
	
	@RequestMapping(value="/finish", method=RequestMethod.GET)
	public ModelAndView showFinishPage() {
		return new ModelAndView("finishconfig");
	}
	
	/**
	 * 接收前端配置页面传入的参数，创建推荐系统的XML配置文件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/receiveconfig", method=RequestMethod.POST)
	@ResponseBody
	public String receiveConfig(HttpServletRequest request, HttpServletResponse response) {
		String jsonParam = request.getParameter("configJSON").replaceAll("\"", "'");
		logger.info(jsonParam);
		WebParamAnalyzer webParamAnalyzer = new WebParamAnalyzer();
		XMLCreator xmlCreator = new XMLCreator();
		try {
			IntegralConfigModel config = webParamAnalyzer.buildConfigModel(jsonParam);
			xmlCreator.createXMLConfigFile(config);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail:" + e.getMessage();
		}
	}
	
}
