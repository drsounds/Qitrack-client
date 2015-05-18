package com.aleros.tastybean;

import org.json.JSONException;
import org.json.JSONObject;

public class TastyAccessToken {
	private String accessToken;
	private int expires;
	private String refreshToken;
	private String scope;
	public TastyAccessToken(String accessToken, int expires)  {
		this.setExpires(expires);
		this.setAccessToken(accessToken);
	}
	
	public TastyAccessToken(JSONObject obj) throws JSONException {
		this.accessToken = obj.getString("access_token");
		this.expires = obj.getInt("expires_in");
		this.refreshToken = obj.getString("refresh_token");
		
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpires() {
		return expires;
	}
	public void setExpires(int expires) {
		this.expires = expires;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
}
