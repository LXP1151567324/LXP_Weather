package com.example.Weather;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.json.R;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;
  
public class Wtr_Task extends AsyncTask<String, Void, String> {  
    Context context;  
    TextView tv_result;  
                                    
    public Wtr_Task(Context context, TextView tv_result) {  
        // TODO Auto-generated constructor stub  
        super();  
        this.context = context;  
        this.tv_result = tv_result;   
    }  
    
    @Override  
    protected String doInBackground(String... params) {  
        String cityname = params[0];  
        //cityname
        ArrayList<NameValuePair> headerList = new ArrayList<NameValuePair>();  
        headerList.add(new BasicNameValuePair("Content-Type", "text/html; charset=utf-8"));  
  
        String targetUrl = "http://v.juhe.cn/weather/index";  
  
        ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();  
        paramList.add(new BasicNameValuePair("key", "38d1ebe98e5797336f688ad0975d96ae"));  
        paramList.add(new BasicNameValuePair("cityname", cityname));  
        
        for (int i = 0; i < paramList.size(); i++) {  
            NameValuePair nowPair = paramList.get(i);  
            String value = nowPair.getValue();  
            try {  
                value = URLEncoder.encode(value, "UTF-8");  
            } catch (Exception e) {  
            }  
            if (i == 0) {  
                targetUrl += ("?" + nowPair.getName() + "=" + value);  
            } else {  
                targetUrl += ("&" + nowPair.getName() + "=" + value);  
            }  
        }  
  
        HttpGet httpRequest = new HttpGet(targetUrl);  
        try {  
            for (int i = 0; i < headerList.size(); i++) {  
                httpRequest.addHeader(headerList.get(i).getName(),  
                                        headerList.get(i).getValue());  
            }  
  
            HttpClient httpClient = new DefaultHttpClient();  
  
            HttpResponse httpResponse = httpClient.execute(httpRequest);  
            if (httpResponse.getStatusLine().getStatusCode() == 200) { 
            	
                String strResult = EntityUtils.toString(httpResponse.getEntity());  
                return strResult;  
            } else {  
                return null;  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    @Override    
    protected void onPostExecute(String result) {  
        if (result != null) {  
            try {  
            	
            	 JSONObject jsonObject=new JSONObject(result);    
                 String resultcode=jsonObject.getString("resultcode");    
                 if(resultcode.equals("200")){    
                     JSONObject resultObject=jsonObject.getJSONObject("result");    
                     JSONObject todayObject=resultObject.getJSONObject("today");    
                           
                    String output = context.getString(R.string.city) + ": " + todayObject.getString("city") + "\n"  
                            + context.getString(R.string.date_y) + ": " + todayObject.getString("date_y") + "\n"  
                            + context.getString(R.string.week) + ": " + todayObject.getString("week") + "\n"  
                            + context.getString(R.string.temperature) + ": " + todayObject.getString("temperature") + "\n"  
                            + context.getString(R.string.weather) + ": " + todayObject.getString("weather") + "\n";  
                            tv_result.setText(output);  
                
                } else if (resultcode.equals("202")) {  
                    String reason = jsonObject.getString("reason");  
                    tv_result.setText(reason);  
                } else {  
                    Toast.makeText(context, "查询失败不是202",  
                            Toast.LENGTH_LONG).show();  
                    tv_result.setText("");  
                }  
            } catch (JSONException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        } else {  
            Toast.makeText(context, "查询失败",  
                                    Toast.LENGTH_LONG).show();  
            tv_result.setText("");  
        }  
    }    
    
}
