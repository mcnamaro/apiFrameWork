package com.api_test.common;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class _ApiStep {


    public static String readJson(String path){
        String result="";
        try {
            BufferedReader file = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = file.readLine();
            while (line != null) {
                sb.append(line);
                line = file.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;

    }


    public static JSONObject verifyResponse(String response){
        JSONObject apiResponse = new JSONObject(response);
        return apiResponse;
    }
}
