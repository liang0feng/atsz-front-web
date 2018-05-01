package com.atsz.front.controller;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.atsz.common.pojo.DataTableJSONResponse;
import com.atsz.front.service.HttpclientService;
import com.atsz.manager.pojo.Content;
import com.atsz.manager.service.ContentService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

@Controller
public class IndexController {

	private static Logger logger = LogManager.getLogger(IndexController.class
			.getName());
	
	@Autowired
	private HttpclientService httpclientService;
	
	@Value("${ATSZ_FRONT_BIGAD_URL}")
	private String ATSZ_FRONT_BIGAD_URL;
	
	@Value("${ATSZ_FRONT_SMALLAD_URL}")
	private String ATSZ_FRONT_SMALLAD_URL;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 松耦合，使用HttpClient
	 * apiContentController
	 * http://localhost:8081/restful/api/content/{categoryId}
	 */
	
	@RequestMapping("index")
	public ModelAndView contentList(Long categoryid){
		ModelAndView mv = new ModelAndView("index");
		
		String url1 = ATSZ_FRONT_BIGAD_URL;
		String url2 = ATSZ_FRONT_SMALLAD_URL;
		
		try {
			//调用封装的doGet方法http请求查询广告位信息
			String bigADJson = httpclientService.doGet(url1);
			String smallADJson = httpclientService.doGet(url2);
			
			JavaType type = MAPPER.getTypeFactory().constructCollectionType(List.class, Content.class);
			if (bigADJson != null) {
				//将json数据转化为list集合返回页面
				List<Content> bigADList = MAPPER.readValue(bigADJson, type);
				
				//将要返回的content集合放入mv中，可以有页面获取
				mv.addObject("bigADList",bigADList);
			}
			
			if (smallADJson != null) {
				//将json数据转化为list集合返回页面
				List<Content> smallADList = MAPPER.readValue(smallADJson, type);
				
				//将要返回的content集合放入mv中，可以有页面获取
				mv.addObject("smallADList",smallADList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
