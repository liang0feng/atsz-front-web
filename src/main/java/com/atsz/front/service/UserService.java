package com.atsz.front.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {

	@Value("${ATSZ_SSO}")
	private String ATSZ_SSO;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	HttpclientService httpclientService;

	public String doLogin(String username, String password) {
		
		HashMap<String,Object> hashMap = new HashMap<String, Object>();
		hashMap.put("username", username);
		hashMap.put("password", password);
		
		String url = ATSZ_SSO + "/user/login"; 
		
		try {
			//rpc运程登录
			String resultJson = httpclientService.doPost(url, hashMap);
			
			//将mapJson转换为map对象
			HashMap map = MAPPER.readValue(resultJson, HashMap.class);
			String ticket = (String) map.get("data");
			return ticket;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
