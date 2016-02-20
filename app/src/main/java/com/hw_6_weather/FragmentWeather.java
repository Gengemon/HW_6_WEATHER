package com.hw_6_weather;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;

public class FragmentWeather extends Fragment implements View.OnClickListener {

    private View viewFragment;
    private WeatherData weatherData;
    private OnFragmentInteractionListenerWeather onFragmentInteractionListenerWeather;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFragment = inflater.inflate(R.layout.fragment_weather, container, false);

        Button buttonGoWeatherWeb = (Button) viewFragment.findViewById(R.id.button_go_weather_web);
        buttonGoWeatherWeb.setOnClickListener(this);
        try {
            onFragmentInteractionListenerWeather = (OnFragmentInteractionListenerWeather) this.getActivity();
            Log.i("onFragmentInteraction", " (OnFragmentInteractionListenerWeather) activity");
        } catch (ClassCastException e) {
            throw new ClassCastException(this.getActivity().toString()
                    + " The MainActivity activity must " +
                    "implement OnContactSelectedListener");
        }
        //updateViewData(0);
        return viewFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        // (0) get arguments +
        Bundle args = getArguments();
        if (args != null) {
            long cityId = args.getLong(MainActivity.CITY_ID);
            Log.i("weather", "cityId = "+cityId);
            // (1) test internet connection
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // (2) get URL city XML +
                String urlCityXML = ParserXML.getUrlCityXML(cityId);
                Log.i("weather", "getLinkCityXML = " + urlCityXML);
                // (3) load XML from internet +
                XmlPullParser parser = null;
                GetXMLData task;
                String xmlData = null;
                try {
                    task = new GetXMLData();
                    task.execute(urlCityXML);
                    Log.i("weather", "task.get()");
                    xmlData = task.get();
                    Log.i("weather", "task.get() = " + xmlData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // (4) parse XML +
                weatherData = ParserXML.getWeatherInfo(xmlData);
                // (5) set data to view +
                updateViewData(weatherData);
            } else {
                //Toast.makeText(getActivity(), R.string.internet_connect_error, Toast.LENGTH_SHORT).show();
                Dialog.showAlert(viewFragment.getContext(), R.string.connection_error_title, R.string.connection_error_message);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_go_weather_web){
            onFragmentInteractionListenerWeather.onFragmentInteractionWeather(weatherData.getLink());
        }
    }

    public class GetXMLData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... url) {
            String loadXML;
            try {
                Log.i("weather.doInBackground", "url =" + url[0]);
                loadXML = Loader.downloadData(url[0]);
                Log.i("weather.downloadData", "url =" + url[0]);
                //mUrl = getLinkToSiteForCity(res);
                //Log.i(LOG_TAG, "url " + " after pull parser" + mUrl);
                return loadXML;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("weather", "onPostExecute");
            //if (mIsWebViewAvailable) getWebView().loadUrl(mUrl);
            //else Log.w("weather", "WebView cannot be found. Check the view and fragment have been loaded.");
        }
    }
    private void updateViewData(WeatherData weatherData){
        TextView textCity = (TextView)  viewFragment.findViewById(R.id.weather_city);
        textCity.setText(weatherData.getCity());
        Log.i("weather", "weather_city = " + weatherData.getCity());

        TextView textLink = (TextView) viewFragment.findViewById(R.id.weather_link);
        textLink.setText(weatherData.getLink());

        TextView textTemperature = (TextView) viewFragment.findViewById(R.id.weather_temperature);
        textTemperature.setText(weatherData.getTemperature().toString());

        int color = Color.parseColor("#"+weatherData.getTemperatureColor());
        textTemperature.setTextColor(color);

        TextView textWeatherType = (TextView) viewFragment.findViewById(R.id.weather_weather_type);
        textWeatherType.setText(weatherData.getWeatherType());

        TextView textWindSpeed = (TextView) viewFragment.findViewById(R.id.weather_wind_speed);
        textWindSpeed.setText(weatherData.getWindSpeed().toString());
    }

    public interface OnFragmentInteractionListenerWeather {

        public void onFragmentInteractionWeather(String urlWeatherWeb);
    }
}
