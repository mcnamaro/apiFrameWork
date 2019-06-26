package com.api_test.utill;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.api_test.common.BaseStep;
import com.api_test.utill.data_classes.CreateUser;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class ApiCalls extends BaseStep {
	static ApiCalls restClient;
	static CloseableHttpResponse closebaleHttpResponse;

	//1. GET Method without Headers:
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException{
	CloseableHttpClient httpClient = HttpClients.createDefault();
	HttpGet httpget = new HttpGet(url); //http get request
	CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL
	
	return closebaleHttpResponse;
		
	}
	
	//2. GET Method with Headers:
		public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request

		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL
		return closebaleHttpResponse;
			
		}

	/**
	 * for Post request
	 * @param url
	 * @param entityString
	 * @param headerMap
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
		public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url); //http post request
			httppost.setEntity(new StringEntity(entityString)); //for payload
			
			//for headers:
			for(Map.Entry<String,String> entry : headerMap.entrySet()){
				httppost.addHeader(entry.getKey(), entry.getValue());
			}
			
			CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httppost);
			return closebaleHttpResponse;
			
			
		}

	public static String postAPI(String path,String url) throws JsonGenerationException, JsonMappingException, IOException{
		restClient = new ApiCalls();

		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");

		//jackson API:
		ObjectMapper mapper = new ObjectMapper();
		 CreateUser users= new CreateUser("morpheus", "leader"); //expected users obejct
		System.out.println(users.toString());
		//object to json file:
		mapper.writeValue(new File(path), users);

		//java object to json in String:
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);

		closebaleHttpResponse = restClient.post(url, usersJsonString, headerMap); //call the API

		//validate response from API:
		//1. status code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code -----> "+statusCode);
		Assert.assertEquals(statusCode, BaseStep.RESPONSE_STATUS_CODE_201);

		//2. JsonString:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is:"+ responseJson);

		//json to java object:
		CreateUser usersResObj = mapper.readValue(responseString, CreateUser.class); //actual users object
		System.out.println(usersResObj);

		Assert.assertTrue(users.getName().equals(usersResObj.getName()));

		Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));

		System.out.println(usersResObj.getId());
		System.out.println(usersResObj.getCreatedAt());
return responseString;
	}







}
