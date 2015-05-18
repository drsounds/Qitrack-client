package com.aleros.tastybean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

public class TastyObject extends Hashtable<String, Object> implements Parcelable {
	private TastyEndpoint endpoint = Tasty.DefaultEndpoint;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3978056577172809849L;
	public static Creator<TastyObject> CREATOR = new Creator<TastyObject>() {
		
		@Override
		public TastyObject[] newArray(int size) {
			// TODO Auto-generated method stub
			return new TastyObject[size];
		}
		
		@Override
		public TastyObject createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new TastyObject(source);
		}
	};
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		Iterator<Entry<String, Object>> iterator = this.entrySet().iterator();
		JSONObject jsonObject = new JSONObject();
		while(iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object val = entry.getValue();
			if (val instanceof TastyObject) {
				val = ((TastyObject) val).toJSONObject();
				
			} else if (val instanceof List) {
				JSONArray array = new JSONArray();
				List<Object> list = (List<Object>)val;
				for (Object obj : list) {
					if (obj instanceof TastyObject) {
						obj = ((TastyObject)obj).toJSONObject();
					}
					array.put(obj);
				}
				val = array;
			}
			try {
				if(!key.startsWith("__"))
				jsonObject.put(key, val);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public TastyObject(Parcel parcel) {
		int count = parcel.readInt();
		for (int i = 0; i < count; i++) {
			String k = parcel.readString();
			Object obj = null;
			try {
				obj = parcel.readInt();
			} catch (Exception e) {
				try {
					obj = parcel.readString();
				} catch (Exception e2) {
					try {
						obj = parcel.readParcelable(null);
					} catch (Exception e3) {
						
					}
				}
			}
			this.put(k, obj);
		}
	}
	
	public TastyObject(JSONObject jsonObject) throws JSONException {
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = jsonObject.keys();
		while(iterator.hasNext()) {
			String k = iterator.next();
			Object obj = jsonObject.get(k);
			 if (obj instanceof JSONArray) {
				JSONArray objs = ((JSONArray)obj);
				ArrayList<Object> objects = new ArrayList<Object>();
				obj = (Object)objects;
		
				for (int i = 0 ; i < objs.length(); i++) {
					try {
						Object obj2 = objs.get(i);
						if (obj2 instanceof JSONObject) {
							objects.add((Object)new TastyObject((JSONObject)obj2));
						} else {
							objects.add(obj2);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (obj instanceof JSONObject) {
				obj = new TastyObject((JSONObject)obj);
			
			}
			this.put(k, obj);
		}
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		// TODO Auto-generated method stub
		dest.writeInt(this.size());
		Iterator<Entry<String, Object>> iterator = this.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			dest.writeString(key);
			Object obj = entry.getValue();
			if (obj instanceof Parcelable) {
				dest.writeParcelable((Parcelable)obj, 0);
				
			} else if (obj instanceof Integer) {
				dest.writeInt((Integer)obj);
			} else if (obj instanceof Float) {
				dest.writeFloat((Float)obj);
			} else if (obj instanceof String) {
				dest.writeString((String)obj);
			}
		}
	}
	@SuppressLint("DefaultLocale")
	public TastyObject(String resource, TastyEndpoint endpoint) {
		this.endpoint = endpoint;
		this.put("__type", resource.toLowerCase(Locale.ENGLISH));
	}
	
	@SuppressLint("DefaultLocale")
	public TastyObject(String resource) {
		this.put("__type", resource.toLowerCase(Locale.ENGLISH));
	}
	
	@SuppressLint("DefaultLocale")
	public TastyObject(String resource, TastyRESTEndpoint endpoint) {
		this.endpoint = endpoint;
		this.put("__type", resource.toLowerCase(Locale.ENGLISH));
	}
	public void saveAsync(final RequestHandler handler) {
		AsyncTask<TastyObject, TastyObject, TastyObject> process = new AsyncTask<TastyObject, TastyObject, TastyObject>() {

			@Override
			protected TastyObject doInBackground(TastyObject... params) {
				// TODO Auto-generated method stub
				TastyObject result;
				try {
					result = endpoint.post((String)TastyObject.this.get("__type"), TastyObject.this);
					return result;
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
			protected void onPostExecute(TastyObject result) {
				// TODO Auto-generated method stub
				handler.onFinished(result);
				
				super.onPostExecute(result);
			}
			
		};
		process.execute();
	}
	public void delete() {
		AsyncTask<TastyObject, TastyObject, TastyObject> process = new AsyncTask<TastyObject, TastyObject, TastyObject>() {

			@Override
			protected TastyObject doInBackground(TastyObject... params) {
				// TODO Auto-generated method stub
				TastyObject result = null;
				
				try {
					result = TastyObject.this.endpoint.delete((String)TastyObject.this.get("__type"), TastyObject.this);
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

				return result;
				
			}

			@Override
			protected void onPostExecute(TastyObject result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
		};
		process.execute();
	}
	public TastyObject save() {
		TastyObject result;
		try {
			if (this.containsKey("id")) 
				result = endpoint.put((String)this.get("__type"), this);
			else
				result = endpoint.post((String)TastyObject.this.get("__type"), TastyObject.this);
			return result;
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
	public void saveEventually() {
		AsyncTask<TastyObject, TastyObject, TastyObject> process = new AsyncTask<TastyObject, TastyObject, TastyObject>() {

			@Override
			protected TastyObject doInBackground(TastyObject... params) {
				// TODO Auto-generated method stub
				TastyObject result;
				
				result = TastyObject.this.save();
				return result;
				
			}

			@Override
			protected void onPostExecute(TastyObject result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
		};
		process.execute();
	}

}
