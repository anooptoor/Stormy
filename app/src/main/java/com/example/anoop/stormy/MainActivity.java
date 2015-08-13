package com.example.anoop.stormy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private CurrentWeather mCurrentWeather;

    @InjectView(R.id.timeLabel) TextView mTimeLabel;
    @InjectView(R.id.tempratureLabel)TextView mTemperatureLabel;
    @InjectView(R.id.humidityValue) TextView mHumidityValue;
    @InjectView(R.id.percipValue) TextView mPrecipValue;
    @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
    @InjectView(R.id.iconImageView) ImageView mIconImageView;
    @InjectView(R.id.refreshImageView) ImageView mRefreshImageView;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        toggleRefresh(View.INVISIBLE);
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast();
            }
        });

        getForecast();

    }

    private void getForecast() {
        String apiKey = "ba26ff69aa784e668bdfb2ff429787d6";
        double latitude = 37.8267;
        double longitude = -122.423;
        String forecastURL = "https://api.forecast.io/forecast/"+ apiKey
                +"/" + latitude + "," + longitude;
        if(isNetworkAvailable()) {
            toggleRefresh(View.VISIBLE);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);

                            runOnUiThread(new Runnable() {

                                public void run() {
                                    updateDisplay();
                                }
                            });


                        } else {
                            alertUserAboutError();
                        }


                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception Caught: " + e);
                    }
                    catch (JSONException e){
                        Log.e(TAG, "Exception Caught: " + e);
                    }
                }
            });
        }
        else{
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh(int visible) {
        mProgressBar.setVisibility(visible);
    }

    private void updateDisplay() {
       mTemperatureLabel.setText(mCurrentWeather.getmTemprature() + "");
       mTimeLabel.setText(mCurrentWeather.getFormattedTime() + "");
       mHumidityValue.setText(mCurrentWeather.getmHumiditiy() + "");
       mPrecipValue.setText((mCurrentWeather.getmPercipChance() + "%"));
       mSummaryLabel.setText(mCurrentWeather.getmSummary() + "");

       Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
       mIconImageView.setImageDrawable(drawable);


    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);
        JSONObject currently = forecast.getJSONObject("currently");
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setmHumiditiy(currently.getDouble("humidity"));
        currentWeather.setmTime(currently.getLong("time"));
        currentWeather.setmIcon(currently.getString("icon"));
        currentWeather.setmPercipChance(currently.getDouble("precipProbability"));
        currentWeather.setmSummary(currently.getString("summary"));
        currentWeather.setmTemprature(currently.getDouble("temperature"));
        currentWeather.setmTimezone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }


}
