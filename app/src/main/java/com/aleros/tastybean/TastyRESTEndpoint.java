package com.aleros.tastybean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class TastyRESTEndpoint extends TastyEndpoint {
	
	
	
	public TastyUser me() throws ClientProtocolException, IOException, JSONException {
		String result = "";
		String url = this.getEndpoint() + "/" + this.getVersion() + "/me/?format=json&username=" + getAppId() + "&api_key=" + this.getAppSecret() + "&bearer_token=" + getAccessToken().getAccessToken();
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpPost = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpPost);
	    
		InputStream inputStream = httpResponse.getEntity().getContent();

        if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        
        TastyUser user = new TastyUser(new JSONObject(result));
        return user;
	}
	public TastyAccessToken login(String username, String password, String scope) throws ClientProtocolException, IOException {
		String result = "";
		String url = this.getEndpoint().replace("/api", "") + "/oauth2/access_token";
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		String data = "client_id=" + this.getAppId() + "&client_secret=" + this.getAppSecret() + "&scope=" + scope + "&grant_type=password&username=" + username + "&password=" + password;
		StringEntity se = new StringEntity(data);
		httpPost.setEntity(se);

        HttpResponse httpResponse = httpClient.execute(httpPost);

        InputStream inputStream = httpResponse.getEntity().getContent();

        if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        try {
        	JSONObject obj = new JSONObject(result);
        	TastyAccessToken accessToken = new TastyAccessToken(obj);
        	this.setAccessToken(accessToken);
        	return accessToken;
        } catch (Exception e) {
        	return null;
        }
	}
	private TastyAccessToken accessToken;
	
	/**
	 * Creates a new tasty endpoint
	 * @param appId
	 * @param appSecret
	 * @param endpoint
	 */
	public TastyRESTEndpoint(String version, String appId, String appSecret, String endpoint) {
		super(version, appId, appSecret, endpoint);
	}
	
	public String getUrl(String resource, String id) {
		String url = this.getEndpoint() + "/" + this.getVersion() + "/" + resource + "/" + (id != null ? id + "/" : "") + "?format=json&username=" + this.getAppId() + "&api_key=" + this.getAppSecret();
		return url;
	}
	
	/**
	 * Made with help from {@link http://hmkcode.com/android-send-json-data-to-server/ }
	 * @param resource
	 * @param object
	 * @throws org.apache.http.client.ClientProtocolException
	 * @throws java.io.IOException
	 * @throws org.json.JSONException
	 */
	@Override
	public TastyObject post(String resource, TastyObject object) throws ClientProtocolException, IOException, JSONException {
		String result = "";
		String url = this.getUrl(resource, null);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		String json = object.toJSONObject().toString();
		StringEntity se = new StringEntity(json);
		httpPost.setEntity(se);
		httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        if (getAccessToken() != null) {
			httpPost.setHeader("Authorization", "OAuth " + getAccessToken().getAccessToken());
			
		} else {
			httpPost.setHeader("Authorization", "ApiKey " + this.getAppId() + ":" + this.getAppSecret());
		}
        HttpResponse httpResponse = httpClient.execute(httpPost);
     // 9. receive response as inputStream
        InputStream inputStream = httpResponse.getEntity().getContent();

        // 10. convert inputstream to string
        if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        
        TastyObject resultObject = new TastyObject(new JSONObject(result));
        return resultObject;
	}
	
	@Override
	public TastyResult put(String resource, TastyObject object) throws ClientProtocolException, IOException, JSONException {
		String result = "";
		String url = this.getUrl(resource, (String)object.get("id"));HttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPost = new HttpPut(url);
		String json = object.toJSONObject().toString();
		StringEntity se = new StringEntity(json);
		httpPost.setEntity(se);
		httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        if (getAccessToken() != null) {
			httpPost.setHeader("Authorization", "OAuth " + getAccessToken().getAccessToken());
			
		}
        HttpResponse httpResponse = httpClient.execute(httpPost);
     // 9. receive response as inputStream
        InputStream inputStream = httpResponse.getEntity().getContent();

        // 10. convert inputstream to string
        if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        
        TastyResult resultObject = new TastyResult(new JSONObject(result));
        return resultObject;
	}
	
	public TastyResult get(String resource, String id, String query) throws ClientProtocolException, IOException, JSONException {
		String result = "";
		String url = this.getUrl(resource, id);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		if (getAccessToken() != null) {
			httpGet.setHeader("Authorization", "OAuth " + getAccessToken().getAccessToken());
		}
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
	    
		InputStream inputStream = httpResponse.getEntity().getContent();

        if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        
        TastyResult resultObject = new TastyResult(new JSONObject(result));
        return resultObject;
	}
	
	public TastyResult delete(String resource, TastyObject object) throws ClientProtocolException, IOException, JSONException {
		String result = "";

		String url = this.getUrl(resource, (String)object.get("id"));
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpPost = new HttpDelete(url);
		HttpResponse httpResponse = httpClient.execute(httpPost);
	    InputStream inputStream = httpResponse.getEntity().getContent();

        if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        
        TastyResult resultObject = new TastyResult(new JSONObject(result));
        return resultObject;
	}
	
	/**
	 * @from http://www.mkyong.com/java/how-to-convert-inputstream-to-string-in-java/
	 * @param is
	 * @return
	 */
	private static String convertInputStreamToString(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
	public TastyAccessToken getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(TastyAccessToken accessToken) {
		this.accessToken = accessToken;
	}
}
