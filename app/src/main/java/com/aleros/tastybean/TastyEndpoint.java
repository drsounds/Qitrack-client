package com.aleros.tastybean;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.os.AsyncTask;

/***
 * Endpoint for a tasty storage
 * @author alecca
 *
 */
public abstract class TastyEndpoint {
	private String appId;
	private String appSecret;
	private String endpoint;
	private String version;
	public abstract TastyUser me() throws ClientProtocolException, IOException, JSONException;
	
	public abstract TastyAccessToken login(String username, String password, String scope) throws ClientProtocolException, IOException;
	public void loginAsync(final String username, final String password, final String scope, final LoginHandler loginHandler) {
		AsyncTask<String, String, TastyUser> process = new AsyncTask<String, String, TastyUser>() {

			@Override
			protected TastyUser doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				TastyAccessToken accessToken;
				try {
					accessToken = login(username, password, scope);
					if (accessToken != null) {
						return me();
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(TastyUser result) {
				// TODO Auto-generated method stub
				if (loginHandler != null) {
					loginHandler.onLoggedIn(result);
				}
				super.onPostExecute(result);
			}
			
		};
		process.execute();
	}
	public TastyEndpoint(String version, String appId, String appSecret, String endpoint) {
		this.setAppId(appId);
		this.setAppSecret(appSecret);
		this.setEndpoint(endpoint);
		this.setVersion(version);
	}
	public abstract TastyResult delete(String resource, TastyObject object) throws ClientProtocolException, IOException, JSONException;
	public abstract TastyObject post(String resource, TastyObject object) throws ClientProtocolException, IOException, JSONException;
	public abstract TastyResult put(String resource, TastyObject object) throws ClientProtocolException, IOException, JSONException;
	public abstract TastyResult get(String resource, String id, String query) throws ClientProtocolException, IOException, JSONException;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}
