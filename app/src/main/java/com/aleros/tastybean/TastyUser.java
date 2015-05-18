package com.aleros.tastybean;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

public class TastyUser extends TastyObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TastyUser() {
		super("user");
	}
	public TastyUser(TastyEndpoint endpoint) {
		super("user", endpoint);
	}
	public static TastyUser login(String username, String password, String scope) throws ClientProtocolException, IOException, JSONException {
		TastyAccessToken token = Tasty.DefaultEndpoint.login(username, password, scope);
		if (token != null) {
			return Tasty.DefaultEndpoint.me();
		}
		return null;
	}
	public TastyUser(JSONObject jsonObject) throws JSONException {
		super(jsonObject);
		// TODO Auto-generated constructor stub
	}
	public TastyUser(Parcel parcel) {
		super(parcel);
		// TODO Auto-generated constructor stub
	}

}
