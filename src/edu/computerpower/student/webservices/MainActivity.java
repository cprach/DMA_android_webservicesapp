package edu.computerpower.student.webservices;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import com.google.gson.Gson;

import edu.computerpower.student.webservicesapp.R;
import edu.computerpower.student.webservicesapp.weatherobjectz.RootObject;

public class MainActivity extends ActionBarActivity {

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

	public boolean checkWebConnection() {

		ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mgr.getActiveNetworkInfo();

		if (info.isConnectedOrConnecting()) {
			if (info.isConnected()) {
				return true;
			}
			return false;
		}
		return false;
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

		boolean val = this.checkWebConnection();

		if (val == true) {

			TextView txtEnterCityName = (TextView)findViewById(R.id.txtEnterCityName);
			String cityName = txtEnterCityName.getText().toString();

			if (cityName != "") {

				ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.pbProgress);
				//				progressBar.setVisibility(View.VISIBLE);

				ForecastForCityTask forecastTask = new ForecastForCityTask(progressBar);
				forecastTask.execute(txtEnterCityName.getText().toString());

			} else {

				Toast.makeText(this, "Please enter the name of a city", Toast.LENGTH_LONG).show();
			}

		} else {

			Toast.makeText(MainActivity.this, " No Internet Connection, please try again shortly",Toast.LENGTH_LONG).show();

		}

	}

	private class ForecastForCityTask extends AsyncTask<String, Integer, String> {

		private final String SERVICE_ADDRESS_START = "http://openweathermap.org/data/2.5/weather?q=";
		private final String SERVICE_ADDRESS_END = "&units=metric";

		ProgressBar progressBar;

		public ForecastForCityTask(ProgressBar progressBar) {
			this.progressBar = progressBar;
			progressBar.setProgress(0);
		}

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {

			// "http://openweathermap.org/data/2.5/weather?q=melbourne,AU&units=metric";

			String serviceAddress = SERVICE_ADDRESS_START + params[0] + SERVICE_ADDRESS_END;

			StringBuilder response = new StringBuilder();
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(serviceAddress);
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
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
			if (this.progressBar != null) {
				progressBar.setProgress(progress[0]);
			}
		}

		@Override
		protected void onPostExecute(String result) {

			TextView txtForecast = (TextView)findViewById(R.id.txtForecast);
			Gson gson = new Gson();
			RootObject rootObject = gson.fromJson(result, RootObject.class);
			String temp = "Temperature: " + rootObject.getMain().getTemp().toString() + "\n";
			String min_temp = "Minimum temperature: " + rootObject.getMain().getTempMin().toString() + "\n";
			String max_temp = "Maximum temperature: " + rootObject.getMain().getTempMax().toString() + "\n";
			String displayString = temp + min_temp + max_temp;
			txtForecast.setText(displayString);
			
			progressBar.setProgress(0);
			progressBar.setVisibility(-1);

		}
	}

}






//TextView txtForecast = (TextView)findViewById(R.id.txtForecast);
//txtForecast.setText(result);
