package com.aleros.tastybean;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Hashtable;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.os.AsyncTask;

public class TastyQuery extends Hashtable<String, String> {
	private TastyEndpoint endpoint = Tasty.DefaultEndpoint;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2557382001852061257L;
	public String resource;
	public TastyQuery(String resource) {
		this.resource = resource;
	}
	public TastyQuery(String resource, TastyEndpoint endpoint) {
		this.endpoint = endpoint;
	}
	public String getQuerystring() {
		while(this.keys().hasMoreElements()) {
			String key= this.keys().nextElement();
			String value = String.valueOf(this.get(key));
			query += key + "=" + URLEncoder.encode(value) + "&";
			
		}
		query += "p=" + String.valueOf(page);
		return query;
	}
	public String getId() {
		if (this.containsKey("id")) {
			return this.get("id");
		} else {
			return null;
		}
	}
	public String getResource() {
		return this.resource;
	}
	private String query;
	@SuppressWarnings("deprecation")
	public TastyResult query() throws ClientProtocolException, IOException, JSONException {
	
		
		
		TastyResult result = endpoint.get("product", this.getId(), this.getQuerystring());
		return result;
	}
	private int page;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	@SuppressWarnings("deprecation")
	public void queryInBackground(final FindCallback callback) {
		
		AsyncTask<String, String, TastyResult> task = new AsyncTask<String, String, TastyResult>() {

			@Override
			protected TastyResult doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
					TastyResult result = endpoint.get(TastyQuery.this.getResource(), TastyQuery.this.getId(), TastyQuery.this.getQuerystring());
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
			protected void onPostExecute(TastyResult result) {
				// TODO Auto-generated method stub
				callback.done(result, null);
				super.onPostExecute(result);
			}
			
		};
		task.execute();
	}
	public TastyResult getNext() throws ClientProtocolException, IOException, JSONException {
		TastyResult result = this.query();
		this.page++;
		return result;
	}
	
	public void getNextBackground(final FindCallback callback) {
		String query = "";
		while(this.keys().hasMoreElements()) {
			String key= this.keys().nextElement();
			String value = String.valueOf(this.get(key));
			query += key + "=" + URLEncoder.encode(value) + "&";
			
		}
		query += "p=" + String.valueOf(page);
		final String query2 = query;
		AsyncTask<String, String, TastyResult> task = new AsyncTask<String, String, TastyResult>() {

			@Override
			protected TastyResult doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
					TastyResult result = endpoint.get(TastyQuery.this.getResource(), TastyQuery.this.getId(), TastyQuery.this.getResource());
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
			protected void onPostExecute(TastyResult result) {
				// TODO Auto-generated method stub
				callback.done(result, null);
				super.onPostExecute(result);
			}
			
		};
		task.execute();
	}
	
	public void whereEqualTo(String key, String val) {
		this.put(key, val);
	}
}
