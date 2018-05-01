package com.atsz.front.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HttpclientService {

	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	private RequestConfig requestConfig;

	/**
	 * 不带 参数的doget方法封装
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String doGet(String url) throws ClientProtocolException, IOException {

		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);

		CloseableHttpResponse response = null;
		try {
			// 执行get请
			response = httpClient.execute(httpGet);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				// 请求正常完成
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				// 关闭响应资源
				response.close();
			}
			// httpClient资源关闭交给线程处理
		}
		return null;
	}

	/**
	 * 带参数的doget方法的封装
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 */
	public String doGetParam(String url, Map<String, Object> map)
			throws ClientProtocolException, IOException, URISyntaxException {

		URIBuilder uriBuilder = new URIBuilder();

		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entity : map.entrySet()) {
				//
				uriBuilder.setParameter(entity.getKey(), entity.getValue()
						.toString());
			}
		}
		URI uri = uriBuilder.build();

		HttpGet httpGet = new HttpGet(uri);
		httpGet.setConfig(requestConfig);
		
		CloseableHttpResponse response = null;
		try {
			// 执行get请
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				// 请求正常完成
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				// 关闭响应资源
				response.close();
			}
			// httpClient资源关闭交给线程处理
		}
		return null;
	}

	/**
	 * 带参数的post请求封装
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String doPost(String url, Map<String, Object> map)
			throws URISyntaxException, ClientProtocolException, IOException {

		HttpPost httpPost = new HttpPost(url);
		// 遍历取出map中的请求参数
		ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			//使用请求参数的list集合，构造一个form表单实体
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(paramList, "UTF-8");
			httpPost.setEntity(urlEncodedFormEntity);
		}
		//设置请求的配置
		httpPost.setConfig(requestConfig);
		
		CloseableHttpResponse response = null;
		try {

			response = httpClient.execute(httpPost);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally{
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
	
	
	/**
	 * httpcli的post不带参数方法的封装
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */
	public String doPost(String url) throws ClientProtocolException, URISyntaxException, IOException{
		return doPost(url,null);
	}

}
