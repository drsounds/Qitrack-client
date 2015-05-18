package com.aleros.tastybean;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

public class TastyResult extends TastyObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -70225805195260001L;
	public TastyResult(JSONObject jsonObject) throws JSONException {
		super(jsonObject);
		// TODO Auto-generated constructor stub
	}
	public TastyResult(Parcel parcel) {
		super(parcel);
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("unchecked")
	public List<TastyObject> objects() {
		return (List<TastyObject>) this.get("objects");
	}
}
