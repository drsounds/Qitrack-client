package com.aleros.tastybean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class TastyAccessToken {
	private String accessToken;
	private int expires;
	private String refreshToken;
	private String scope;
    private Date issued;
    public Date getIssued() {
        return issued;
    }
	public TastyAccessToken(String accessToken, int expires)  {
		this.setExpires(expires);
		this.setAccessToken(accessToken);
	}

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("access_token", this.accessToken);
            obj.put("expires_in", this.expires);
            obj.put("refresh_token", this.refreshToken);
            obj.put("issued", this.issued.getTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public boolean isValid() {
        return new Date().before(new Date(this.getIssued().getTime() + this.expires));

    }

	public TastyAccessToken(JSONObject obj) throws JSONException {
		this.accessToken = obj.getString("access_token");
		this.expires = obj.getInt("expires_in");
		this.refreshToken = obj.getString("refresh_token");
        if (obj.has("issued"))
            this.issued = new Date(obj.getLong("issued"));
        else
            this.issued = new Date();
		
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
