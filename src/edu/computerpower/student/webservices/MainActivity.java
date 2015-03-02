package edu.computerpower.student.webservices;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import edu.computerpower.student.webservicesapp.R;
import edu.computerpower.student.webservicesapp.weatherobjects.RootObject;

public class MainActivity extends ActionBarActivity {

	//	private final static String SERVICE_ADDRESS = "http://api.geonames.org/findNearbyPlaceNameJSON?";
	//	private final static String SERVICE_USERNAME = "&username=cprach";
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

		TextView txtEnterCityName = (TextView)findViewById(R.id.txtEnterCityName);
		String cityName = txtEnterCityName.getText().toString();

		if (cityName != "") {
			final ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.prog);
			progressBar.setVisibility(View.VISIBLE);
			getInfo gi = new getInfo(progressBar);
			gi.execute(txtEnterCityName.getText().toString());
		}
	}

	private class getInfo extends AsyncTask<String, Integer, String> {

		private final static String SERVICE_ADDRESS = "http://openweathermap.org/data/2.5/weather?q=";
		private final static String ADDRESS_END = "&units=metric";


		ProgressBar progressBar;

		public getInfo(ProgressBar progressBar) {
			this.progressBar = progressBar;
			progressBar.setProgress(0);
		}

		@Override
		protected String doInBackground(String... strings) {

			// "http://openweathermap.org/data/2.5/weather?q=melbourne,AU&units=metric";

			String url = SERVICE_ADDRESS + strings[0] + ADDRESS_END;
			Log.d("Service url >>> ", url);

			StringBuilder response = new StringBuilder();
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
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
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			if (this.progressBar != null) {
				progressBar.setProgress(values[0]);
			}
		}

		@Override
		protected void onPostExecute(String result) {

			if (result != null | result != "") {

				// 1.
				TextView txtForecast = (TextView)findViewById(R.id.txtForecast);
				txtForecast.setText(result);

				// 2.
//				Gson gson = new Gson();
//				RootObject rootObject = gson.fromJson(result, RootObject.class);
//				String temp = "Temperature: " + rootObject.getMain().getTemp().toString() + "\n";
//				String min_temp = "Minimum temperature: " + rootObject.getMain().getTempMin().toString() + "\n";
//				String max_temp = "Maximum temperature: " + rootObject.getMain().getTempMax().toString() + "\n";
//				String displayString = temp + min_temp + max_temp;
//				txtForecast.setText(displayString);

			}



		}
	}

}
