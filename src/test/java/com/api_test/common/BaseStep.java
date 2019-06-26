package com.api_test.common;

import com.api_test.utill.ApiCalls;
import com.api_test.utill.JpathUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.json.JSONObject;

public class BaseStep {
    _ApiStep api = new _ApiStep();
    public static int RESPONSE_STATUS_CODE_200 = 200;
    public static int RESPONSE_STATUS_CODE_500 = 500;
    public static int RESPONSE_STATUS_CODE_400 = 400;
    public static int RESPONSE_STATUS_CODE_401 = 401;
    public static int RESPONSE_STATUS_CODE_201 = 201;
    CloseableHttpResponse closebaleHttpResponse;
    ApiCalls restClient;
    protected static final String YAML_FOLDER_PATH = "services/";

    /*
     for reading  yaml file and checking conditions and sending api call
      */
    public String loadYAMLDocument(String fileName) throws Exception {
        System.out.println("------" + fileName);
        String response;
        Yaml yaml = new Yaml(new Constructor(YamlService.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(YAML_FOLDER_PATH + fileName + ".yaml");
        YamlService service = yaml.load(inputStream);
        if (service.getRequest().equalsIgnoreCase("GET")) {
            getAPITestWithHeaders(service.getService());
          //  getAPITestWithoutHeaders(service.getService());
        }
        if (service.getRequest().equalsIgnoreCase("POST")) {

            response = restClient.postAPI(service.getJsonPath(), service.getService());

            return response;
        }
        return null;
    }

    /**
     * methods under for get request
     * @param url
     * @throws ClientProtocolException
     * @throws IOException
     */

    public void getAPITestWithoutHeaders(String url) throws ClientProtocolException, IOException {
        restClient = new ApiCalls();
        closebaleHttpResponse = restClient.get(url);

        //a. Status Code:
        int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code--->" + statusCode);

        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        //b. Json String:
        String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API---> " + responseJson);

        //single value assertion:
        //per_page:
        String perPageValue = JpathUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println("value of per page is-->" + perPageValue);
        Assert.assertEquals(Integer.parseInt(perPageValue), 3);

        //total:
        String totalValue = JpathUtil.getValueByJPath(responseJson, "/total");
        System.out.println("value of total is-->" + totalValue);
        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        //c. All Headers
        Header[] headersArray = closebaleHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Headers Array-->" + allHeaders);


    }


    @Test(priority = 2)
    public void getAPITestWithHeaders(String url) throws ClientProtocolException, IOException {
        restClient = new ApiCalls();

        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        closebaleHttpResponse = restClient.get(url, headerMap);

        //a. Status Code:
        int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code--->" + statusCode);

        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        //b. Json String:
        String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API---> " + responseJson);

        //single value assertion:
        //per_page:
        String perPageValue = JpathUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println("value of per page is-->" + perPageValue);
        Assert.assertEquals(Integer.parseInt(perPageValue), 3);

        //total:
        String totalValue = JpathUtil.getValueByJPath(responseJson, "/total");
        System.out.println("value of total is-->" + totalValue);
        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        //c. All Headers
        Header[] headersArray = closebaleHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Headers Array-->" + allHeaders);


    }
}


