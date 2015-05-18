package com.aleros.tastybean;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public class Tasty {
	public static TastyEndpoint DefaultEndpoint;
	public static void init(String appId, String appSecret, String endpoint, String version) {
		DefaultEndpoint = new TastyRESTEndpoint(version, appId, appSecret, endpoint);
		
	}
	public static TastyUser login(String username, String password, String scope) throws ClientProtocolException, IOException, JSONException {
		TastyAccessToken accessToken = DefaultEndpoint.login(username, password, scope);
		if (accessToken != null) {
			return DefaultEndpoint.me();
		}
		return null;
	}
	public static void loginAsync(final String username, final String password, final String scope, final LoginHandler loginHandler) {
		DefaultEndpoint.loginAsync(username, password, scope, loginHandler);
	}
}
