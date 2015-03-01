package edu.computerpower.student.webservicesapp;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {
	
	private final static String SERVICE_ADDRESS = "http://api.geonames.org/findNearbyPlaceNameJSON?";
	private final static String SERVICE_USERNAME = "&username=cprach";
//	private final static String MELBOURNE_COORDS = "lat=47.3&lng=9";
	private final static String MELBOURNE_COORDS = "lat=47.3&lng=9";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void GetInfo(View v){
		String s = "http://openweathermap.org/data/2.5/weather?q=melbourne,AU&units=metric";
		String serviceURL = SERVICE_ADDRESS + MELBOURNE_COORDS + SERVICE_USERNAME;
		Log.d(">>>>>>>>>> serviceURL", serviceURL);
		final ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.prog);
		progressBar.setVisibility(View.VISIBLE);
		getInfo gi = new getInfo(progressBar);
		gi.execute(serviceURL);
	}
	
	private class getInfo extends AsyncTask<String, Integer, String> {

		ProgressBar progressBar;

		public getInfo(ProgressBar progressBar) {
			this.progressBar = progressBar;
			progressBar.setProgress(0);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			if (this.progressBar != null) {
				progressBar.setProgress(values[0]);
			}
		}

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder response = new StringBuilder();
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urls[0]);
			try {
				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
				String bufferString = "";
				while ((bufferString = buffer.readLine()) != null) {
					response.append(bufferString);
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
			return response.toString();
		}

		@Override
		protected void onPostExecute(String result) {


			try{
				JSONObject jsonObject = new JSONObject(result);
				JSONArray arr = new JSONArray(jsonObject.getString("geonames"));
				Log.d(">>>>>>>>>> length of return array",Integer.toString(arr.length()));
				for(int a = 0; a<arr.length(); a++){
					JSONObject element = arr.getJSONObject(a);
					Toast.makeText(getBaseContext(),
							element.getString("countryCode")+ ("\n") +
							element.getString("countryName"),Toast.LENGTH_SHORT).
							show();
				}
				progressBar.setProgress(0);
				progressBar.setVisibility(-1);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
